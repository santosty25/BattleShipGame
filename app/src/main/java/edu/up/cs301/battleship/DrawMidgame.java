package edu.up.cs301.battleship;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import java.util.ArrayList;

import edu.up.cs301.game.GameFramework.Game;
import edu.up.cs301.game.GameFramework.animation.Animator;
import edu.up.cs301.game.R;


/**
 * DrawMidgame - A class that represents what is drawn during the
 * mid game/battle phase and creates an animation for a rocket shooting
 * sown wherever the player tapped.
 *
 * @author Keoni Han
 * @author Austen Furutani
 * @author Tyler Santos
 * @author Steven Lee
 * @version Spring 2022 - 4/21/22
 */
public class DrawMidgame implements Animator {
    private Paint blackPaint = new Paint();
    private Paint clear = new Paint();
    public ArrayList<TapValues> tapValues = new ArrayList<TapValues>();
    private int count = 0; //how many pixels the rocket moves
    public int playerID; //reference to playerID

    protected BattleShipGameState state = new BattleShipGameState(); //reference to gamestate
    protected int flashColor = Color.BLACK;
    protected Resources resources = null;

    //bitmaps for the GUI, ships, and hit markers
    private Bitmap fivehp = null;
    private float fivehpLeft = 1814.0f;
    private float fivehpTop = 108.0f;
    private Bitmap fourhp1 = null;
    private float fourhp1Left = 1684.0f;
    private float fourhp1Top = 70.0f;
    private Bitmap fourhp2 = null;
    private float fourhp2Left = 1677.0f;
    private float fourhp2Top = 393.0f;
    private Bitmap threehp1 = null;
    private float threehp1Left = 1828.0f;
    private float threehp1Top = 500.0f;
    private Bitmap threehp2 = null;
    private float threehp2Left = 1830.0f;
    private float threehp2Top = 760.0f;
    private Bitmap twohp = null;
    private float twohpLeft = 1680.0f;
    private float twohpTop = 807.0f;
    private Bitmap xSink = null;
    private Bitmap background = null;
    private Bitmap grid = null;
    private Bitmap remainingShips = null;
    private Bitmap redMarker = null;
    private Bitmap whiteMarker = null;
    private Bitmap userSelection = null;
    private Bitmap enemyRedMarker = null;
    private Bitmap enemyWhiteMarker = null;
    private Bitmap playersGrid = null;
    private Bitmap missile = null;

    private boolean willDraw; //boolean for hether the rocket will be drawn or not
    private Activity activity; //reference to activity in BattleShipHumanPlayer
    private TextView xCoord;
    private TextView yCoord;
    private int playerNum; //a reference to the player number
    private Game game; //a reference to the game
    private BattleShipHumanPlayer reference; //a reference to the human player
    //boolean for if the ships are rotated
    private boolean rotFiveHp = true;
    private boolean rotFourHp1 = true;
    private boolean rotFourHp2 = true;
    private boolean rotThreeHp1 = true;
    private boolean rotThreeHp2 = true;
    private boolean rotTwoHP = true;
    private float xTouch; //the x value where the player touched
    private float yTouch; //the y value where the player touched
    private int drawRot = 0; //instance variable for created rotated bitmaps once


    public DrawMidgame(Activity activity, BattleShipHumanPlayer player) {
        //init instance variables for on touch
        this.activity = activity;
        this.reference = player;
        this.resources = this.activity.getResources();
        this.xCoord = this.activity.findViewById(R.id.textView);
        this.yCoord = this.activity.findViewById(R.id.textView2);
        this.playerNum = player.getPlayerNum();
        this.game = player.getGame();
        //init bitmaps
        fivehp = BitmapFactory.decodeResource(this.resources, R.drawable.fivehpbs);
        fourhp1 = BitmapFactory.decodeResource(this.resources, R.drawable.fourhpbs);
        fourhp2 = BitmapFactory.decodeResource(this.resources, R.drawable.fourhpbs);
        threehp1 = BitmapFactory.decodeResource(this.resources, R.drawable.threehpbs);
        threehp2 = BitmapFactory.decodeResource(this.resources, R.drawable.threehpbs);
        twohp = BitmapFactory.decodeResource(this.resources, R.drawable.twohpbs);
        xSink = BitmapFactory.decodeResource(this.resources, R.drawable.red_cross);
        background = BitmapFactory.decodeResource(this.resources, R.drawable.battleshipbackground);
        grid = BitmapFactory.decodeResource(this.resources, R.drawable.updatedgrid);
        remainingShips = BitmapFactory.decodeResource(this.resources, R.drawable.ships);
        redMarker = BitmapFactory.decodeResource(this.resources, R.drawable.hitmarker);
        whiteMarker = BitmapFactory.decodeResource(this.resources, R.drawable.missmarker);
        userSelection = BitmapFactory.decodeResource(this.resources, R.drawable.tagetselector);

        Matrix matrix = new Matrix();
        matrix.postRotate(270);
        missile = BitmapFactory.decodeResource(this.resources, R.drawable.missile);
        missile = Bitmap.createScaledBitmap(missile,  216, 60, false);
        missile = Bitmap.createBitmap(missile, 0, 0 , missile.getWidth(), missile.getHeight(),
                matrix, true);

        //Draws the board for the use will use to select and play their move
        grid =  Bitmap.createScaledBitmap(grid, 1000, 1000, false);
        playersGrid =  Bitmap.createScaledBitmap(grid, 400, 400, false);

        remainingShips = Bitmap.createScaledBitmap(remainingShips, 150, 1000, false);

        //When user hits a ship a red marker will be placed
        redMarker =  Bitmap.createScaledBitmap(redMarker, 300, 250, false);
        enemyRedMarker = Bitmap.createScaledBitmap(redMarker, 100, 83, false);

        //A missed shot will be indicated with a white marker
        whiteMarker =  Bitmap.createScaledBitmap(whiteMarker, 300, 250, false);
        enemyWhiteMarker =  Bitmap.createScaledBitmap(whiteMarker, 100, 83, false);

        //When the user selects their move the COOR will be identified with a target
        userSelection =  Bitmap.createScaledBitmap(userSelection, 200, 150, false);

        //creates bitmap for x on sidebar ships
        xSink = Bitmap.createScaledBitmap(xSink, 100, 100, false);

        //creates colors
        this.blackPaint.setColor(0xFF00FF00);
        this.blackPaint.setStyle(Paint.Style.FILL);
        this.clear.setColor(0x00000000);
        this.clear.setStyle(Paint.Style.STROKE);
    }

    /**
     * The time interval (in milliseconds) between animation frames. Thus, for
     * example, to draw a frame 20 times per second, you would return 50. This
     * method is called once at the beginning of the animation, so changing the
     * value during the animation will have no effect.
     *
     * @return the time interval (in milliseconds) between calls to this class'
     *         "tick" method.
     */
    public int interval() {
        return 30;
    }

    /**
     * The background color with which to paint the canvas before the animation
     * frame is drawn. This method is called at each tick, so the background
     * color can change dynamically by having this method return different
     * values.
     *
     * @return the desired background color
     */
    public int backgroundColor() {
        return Color.TRANSPARENT;
    }

    /**
     * Tells whether the animation should be paused.
     *
     * @return a true/false value that says whether the animation should be
     *         paused.
     */
    public boolean doPause() {
        return false;
    }

    /**
     * Tells whether the animation should be stopped.
     *
     * @return true/false value that tells whether to terminate the animation.
     */
    public boolean doQuit() {
        return false;
    }

    /**
     * Called once every clock tick (frequency specified by the "interval"
     * method) to draw the next animation-frame. Typically this is used to
     * update the animation's data to reflect the passage of time (e.g., to
     * modify an instance variable that gives the position of an object) before
     * the frame is drawn.
     *
     * @param canvas
     *            the Canvas object on which to draw the animation-frame.
     */
    public void tick(Canvas canvas){
        background = Bitmap.createScaledBitmap(background, canvas.getWidth(),
                canvas.getHeight(), false);
        Matrix matrix = new Matrix();
        matrix.postRotate(90);

        /** Draws everything to surface view */
        //sets flash
        if(this.flashColor == Color.BLACK) {
            canvas.drawBitmap(background, 0.0f, 0.0f, blackPaint);
        }
        else {
            Paint tempPaint = new Paint();
            tempPaint.setColor(this.flashColor);
            canvas.drawRect(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), tempPaint);
        }
        //draw GUI
        canvas.drawBitmap(grid, 550.0f, 25.0f, blackPaint);
        canvas.drawBitmap(playersGrid, 50.0f, 600.0f, blackPaint);
        canvas.drawBitmap(remainingShips, 1800.0f, 25.0f, blackPaint);
        for(TapValues tap : tapValues){
            canvas.drawBitmap(whiteMarker, tap.getX(), tap.getY(), blackPaint);
        }

        if(this.drawRot == 0) {
            fivehp = Bitmap.createScaledBitmap(fivehp, 155, 28, false);
            if (rotFiveHp) {
                fivehp = Bitmap.createBitmap(fivehp, 0, 0, fivehp.getWidth(),
                        fivehp.getHeight(), matrix, true);
            }
            fourhp1 = Bitmap.createScaledBitmap(fourhp1, 123, 28, false);
            if (rotFourHp1) {
                fourhp1 = Bitmap.createBitmap(fourhp1, 0, 0, fourhp1.getWidth(),
                        fourhp1.getHeight(), matrix, true);
            }
            fourhp2 = Bitmap.createScaledBitmap(fourhp2, 123, 25, false);
            if (rotFourHp2) {
                fourhp2 = Bitmap.createBitmap(fourhp2, 0, 0, fourhp2.getWidth(),
                        fourhp2.getHeight(), matrix, true);
            }
            threehp1 = Bitmap.createScaledBitmap(threehp1, 94, 24, false);
            if (rotThreeHp1) {
                threehp1 = Bitmap.createBitmap(threehp1, 0, 0, threehp1.getWidth(),
                        threehp1.getHeight(), matrix, true);
            }
            threehp2 = Bitmap.createScaledBitmap(threehp2, 94, 24, false);
            if (rotThreeHp2) {
                threehp2 = Bitmap.createBitmap(threehp2, 0, 0, threehp2.getWidth(),
                        threehp2.getHeight(), matrix, true);
            }
            twohp = Bitmap.createScaledBitmap(twohp, 61, 24, false);
            if (rotTwoHP) {
                twohp = Bitmap.createBitmap(twohp, 0, 0, twohp.getWidth(),
                        twohp.getHeight(), matrix, true);
            }
        }//creates rotated bitmap once
        this.drawRot++;

        if (state == null) {
            Log.i("State is Null", "onDraw: NULL");
            return;
        }
        int enemyID; //the enemy's player number

        if(this.playerID == 0){
            enemyID = 1;
        }
        else{
            enemyID = 0;
        }

        //Draw on enemies board
        float xVal = 0;
        float yVal = 0;
        GameBoard drawBoard = this.state.getBoard(enemyID);
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Log.i("NOT NULL", "");
                if(drawBoard.getCoordHit(row, col)){
                    Coordinates[][] board = drawBoard.getCurrentBoard();
                    float xDrift = 1.5f * (float)row;
                    float yDrift = 0.7f * (float)col;
                    yVal = state.middleYOfCoord(board[row][col]) - (195.0f - yDrift) ;
                    xVal = state.middleXOfCoord(board[row][col]) - (226.0f - xDrift);
                    if(board[row][col].getHasShip()){
                        canvas.drawBitmap(redMarker, xVal, yVal,blackPaint);
                    }
                    else {
                        canvas.drawBitmap(whiteMarker, xVal, yVal, blackPaint);
                    }

                }
            }
        }

        //draw missile from the top of the screen
        if((float)count < this.yTouch && this.willDraw == true) {
            canvas.drawBitmap(missile, this.xTouch - 20.0f, count, blackPaint);
            this.count+= 40;
        }
        else if ((float)count > this.yTouch - 20.0f) {
            this.willDraw = false;
            doQuit();
            this.count = 0;
        }

        /**Draw on your board*/
        BattleshipObj[][] playerFleet = new BattleshipObj[2][6];
        for (int i = 0;  i < 2; i++) {
            for (int j =0; j < 6; j++){
                if (state.getPlayersFleet()[i][j] != null) {
                    playerFleet[i][j] = new BattleshipObj(state.getPlayersFleet()[i][j]);
                }
            }
        }


        //Draws the ships according to that user's board taken from the game state
        Coordinates toPlace = new Coordinates(false,false,0,0);

        for (int i = 0; i < playerFleet[playerID].length; i++) {
            if (playerFleet[playerID][i].getSize() == 5) {
                toPlace = playerFleet[playerID][i].getFirstCoord();
                fivehpLeft = state.middleXOfEnemyBoard(toPlace) - 21.5f;
                fivehpTop = state.middleYOfEnemyBoard(toPlace) - 16;
                if (toPlace.getY() <= 3) {
                    fivehpTop = state.middleYOfEnemyBoard(toPlace) - 13;
                }
            }
            else if (playerFleet[playerID][i].getSize() == 4) {
                if (i == 1) {
                    toPlace = playerFleet[playerID][i].getFirstCoord();
                    fourhp1Left = state.middleXOfEnemyBoard(toPlace) - 19.5f;
                    if (toPlace.getX() > 7) {
                        fourhp1Left = state.middleXOfEnemyBoard(toPlace) - 21.5f;
                    }
                    else if (toPlace.getX() < 4) {
                        fourhp1Left = state.middleXOfEnemyBoard(toPlace) - 17.5f;
                    }
                    fourhp1Top = state.middleYOfEnemyBoard(toPlace) - 16;
                }
                else {
                    toPlace = playerFleet[playerID][i].getFirstCoord();
                    fourhp2Left = state.middleXOfEnemyBoard(toPlace) - 19.5f;
                    if (toPlace.getX() > 7) {
                        fourhp2Left = state.middleXOfEnemyBoard(toPlace) - 21.5f;
                    }
                    else if (toPlace.getX() < 4) {
                        fourhp2Left = state.middleXOfEnemyBoard(toPlace) - 17.5f;
                    }
                    fourhp2Top = state.middleYOfEnemyBoard(toPlace) - 16;
                }
            }
            else if (playerFleet[playerID][i].getSize() == 3) {
                if (i == 3) {
                    toPlace = playerFleet[playerID][i].getFirstCoord();
                    threehp1Left = state.middleXOfEnemyBoard(toPlace) - 17.5f;
                    if (toPlace.getX() > 7) {
                        threehp1Left = state.middleXOfEnemyBoard(toPlace) - 23f;
                    }
                    else if (toPlace.getX() < 4) {
                        threehp1Left = state.middleXOfEnemyBoard(toPlace) - 17.5f;
                    }
                    threehp1Top= state.middleYOfEnemyBoard(toPlace) - 16;
                    if (toPlace.getY() <= 3) {
                        threehp1Top = state.middleYOfEnemyBoard(toPlace) - 13;
                    }
                }
                else {
                    toPlace = playerFleet[playerID][i].getFirstCoord();
                    threehp2Left = state.middleXOfEnemyBoard(toPlace) - 17.5f;
                    if (toPlace.getX() > 7) {
                        threehp2Left = state.middleXOfEnemyBoard(toPlace) - 23f;
                    }
                    else if (toPlace.getX() < 4) {
                        threehp2Left = state.middleXOfEnemyBoard(toPlace) - 17.5f;
                    }

                    threehp2Top = state.middleYOfEnemyBoard(toPlace) - 16;

                    if (toPlace.getY() <= 3) {
                        threehp2Top = state.middleYOfEnemyBoard(toPlace) - 13;
                    }

                }
            }
            else if (playerFleet[playerID][i].getSize() == 2) {
                toPlace = playerFleet[0][i].getFirstCoord();
                twohpLeft = state.middleXOfEnemyBoard(toPlace) - 20;
                if (toPlace.getX() > 7) {
                    twohpLeft = state.middleXOfEnemyBoard(toPlace) - 21.5f;
                }
                else if (toPlace.getX() < 4) {
                    twohpLeft = state.middleXOfEnemyBoard(toPlace) - 17.5f;
                }

                twohpTop = state.middleYOfEnemyBoard(toPlace) - 16;
                if (toPlace.getY() <= 3) {
                    twohpTop = state.middleYOfEnemyBoard(toPlace) - 13;
                }

            }

        }

        if (rotFiveHp) {
            canvas.drawBitmap(fivehp, fivehpLeft, fivehpTop, blackPaint);
        }
        else {
            canvas.drawBitmap(fivehp, fivehpLeft, fivehpTop - 7, blackPaint);
        }

        if (rotFourHp1) {
            canvas.drawBitmap(fourhp1, fourhp1Left, fourhp1Top, blackPaint);
        }
        else {
            canvas.drawBitmap(fourhp1, fourhp1Left, fourhp1Top - 9, blackPaint);
        }

        if (rotFourHp2) {
            canvas.drawBitmap(fourhp2, fourhp2Left, fourhp2Top, blackPaint);
        }
        else {
            canvas.drawBitmap(fourhp2, fourhp2Left, fourhp2Top - 9, blackPaint);
        }

        if (rotThreeHp1) {
            canvas.drawBitmap(threehp1, threehp1Left, threehp1Top, blackPaint);
        }
        else {
            canvas.drawBitmap(threehp1, threehp1Left, threehp1Top - 7, blackPaint);
        }

        if (rotThreeHp2) {
            canvas.drawBitmap(threehp2, threehp2Left, threehp2Top, blackPaint);
        }
        else {
            canvas.drawBitmap(threehp2, threehp2Left, threehp2Top - 7, blackPaint);
        }

        if (rotTwoHP) {
            canvas.drawBitmap(twohp, twohpLeft, twohpTop, blackPaint);
        }
        else {
            canvas.drawBitmap(twohp, twohpLeft, twohpTop - 7, blackPaint);
        }

        // Draw red x over sunk ships
        int enemy = 0;
        if(playerID == 0){
            enemy = 1;
        }
        state.getPlayersFleet()[enemy][5].getSunk();

        if (state.getPlayersFleet()[enemy][5].getSunk()) {
            canvas.drawBitmap(xSink, 1825.0f, 880.0f, blackPaint);
        }
        if (state.getPlayersFleet()[enemy][4].getSunk()) {
            canvas.drawBitmap(xSink, 1825.0f, 760.0f , blackPaint);
        }
        if (state.getPlayersFleet()[enemy][3].getSunk()) {
            canvas.drawBitmap(xSink, 1825.0f, 630.0f, blackPaint);
        }
        if (state.getPlayersFleet()[enemy][2].getSunk()) {
            canvas.drawBitmap(xSink, 1825.0f, 480.0f, blackPaint);
        }
        if (state.getPlayersFleet()[enemy][1].getSunk()) {
            canvas.drawBitmap(xSink, 1825.0f, 300.0f, blackPaint);
        }
        if (state.getPlayersFleet()[enemy][0].getSunk()) {
            canvas.drawBitmap(xSink, 1825.0f, 110.0f, blackPaint);
        }

        // Draws players board
        GameBoard drawEnemyBoard = this.state.getBoard(playerID);
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Log.i("NOT NULL", "");
                if(drawEnemyBoard.getCoordHit(row, col)){
                    Coordinates[][] board = drawEnemyBoard.getCurrentBoard();
                    float xDrift =  1.8f * (float)row;
                    float yDrift =  1.75f * (float)col;
                    yVal = state.middleYOfEnemyBoard(board[row][col])  - (75 + yDrift);
                    xVal = state.middleXOfEnemyBoard(board[row][col]) - (82 + xDrift);
                    if(board[row][col].getHasShip()){
                        canvas.drawBitmap(enemyRedMarker, xVal, yVal, blackPaint);
                    }
                    else {
                        canvas.drawBitmap(enemyWhiteMarker, xVal, yVal, blackPaint);
                    }
                }
            }
        }
    }

    /**
     * Called whenever the user touches the AnimationSurface so that the
     * animation can respond to the event.
     *
     * @param motionEvent a MotionEvent describing the touch
     */
    public void onTouch(MotionEvent motionEvent) {
        this.willDraw = true;
        float xC = motionEvent.getX();
        float yC = motionEvent.getY();
        String letter = "";
        boolean inBounds = true;
        if(xC >= 710 && xC <= 1460) {
            this.setXTouch(xC);
        }
        if (yC >= 180 && yC <= 930) {
            this.setYTouch(yC);
        }

        // X-Coordinates
        if (xC < 710 || xC > 1460) {
            xC = 0;
            letter = "";
            inBounds = false;
        }

       xC = this.setX(xC); //sets the x value for the textview

        // Y-Coordinates
        if (yC < 180 || yC > 930) {
            letter = "";
            xC = 0;
        }
        if (inBounds == true) {
            letter = this.setLetter(yC);
        }

        //set text views
        if (!(xC == 0)) {
            xCoord.setText("X: " + (int) xC);
        } else {
            xCoord.setText("X: ");
        }
        yCoord.setText("Y: " + letter);

        //sends fire action
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        Log.d("In midGame", "Coords: " + x + ", " + y);
        Log.i("Players Turn", "" + state.getPlayersTurn());
        if (state.getPlayersTurn() == playerNum) {
            Coordinates sendFireto = state.xyToCoordMidGame(x, y);
            if (sendFireto != null) {
                Log.i("Touch", "onTouch: sending fire ");
                game.sendAction(new Fire(reference, sendFireto, playerNum));
            }
        }
    }


    public void setPlayerID(int num) {
        this.playerID = num;
    }
    public void setState(BattleShipGameState state) {
        this.state = new BattleShipGameState(state);
    }
    //sets rotation of ships
    public void setRotFiveHp(boolean newVal) {rotFiveHp = newVal;}
    public void setRotFourHp1(boolean newVal) {rotFourHp1 = newVal;}
    public void setRotFourHp2(boolean newVal) {rotFourHp2 = newVal;}
    public void setRotThreeHp1(boolean newVal) {rotThreeHp1 = newVal;}
    public void setRotThreeHp2(boolean newVal) {rotThreeHp2 = newVal;}
    public void setRotTwoHP(boolean newVal) {rotTwoHP = newVal;}
    public void setFlashColor(int color) {
        this.flashColor = color;
    }

    /**
     * setXTouch - Sets the x value where the player touched so the rocket can be
     * drawn properly.
     * @param x - the given x value
     */
    public void setXTouch(float x) {
        if (x > 710 && x < 785) {
            this.xTouch = 747.5f;
        } else if (x > 785 && x < 860) {
            this.xTouch = 822.5f;
        } else if (x > 860 && x < 935) {
            this.xTouch = 897.5f;
        } else if (x > 935 && x < 1010) {
            this.xTouch = 972.5f;
        } else if (x > 1010 && x < 1085) {
            this.xTouch = 10475.f;
        } else if (x > 1085 && x < 1160) {
            this.xTouch = 1122.5f;
        } else if (x > 1160 && x < 1235) {
            this.xTouch = 1197.5f;
        } else if (x > 1235 && x < 1310) {
            this.xTouch = 1272.5f;
        } else if (x > 1310 && x < 1385) {
            this.xTouch = 1347.5f;
        } else if (x > 1385 && x < 1460) {
            this.xTouch = 1422.5f;
        }
    }

    /**
     * setYTouch - Sets the y value where the player touched so the rocket can be
     * drawn properly.
     * @param y - the given y value
     */
    public void setYTouch(float y) {
        if (y > 180 && y < 255) {
            this.yTouch = 217.5f;
        } else if (y > 255 && y < 330) {
            this.yTouch = 292.5f;
        } else if (y > 330 && y < 405) {
            this.yTouch = 367.5f;
        } else if (y > 405 && y < 480) {
            this.yTouch = 442.5f;
        } else if (y > 480 && y < 555) {
            this.yTouch = 517.5f;
        } else if (y > 555 && y < 630) {
            this.yTouch = 592.5f;
        } else if (y > 630 && y < 705) {
            this.yTouch = 667.5f;
        } else if (y > 705 && y < 780) {
            this.yTouch = 742.5f;
        } else if (y > 780 && y < 855) {
            this.yTouch = 817.5f;
        } else if (y > 855 && y < 930) {
            this.yTouch = 892.5f;
        }
    }

    /**
     * setLetter - Sets the letter of the y coord for the textview.
     * @param yC - a given touch value for y
     * @return a letter for where the player has touched for the y value.
     */
    public String setLetter(float yC) {
        if (yC >= 180 && yC < 255) {
            return "A";
        } else if (yC >= 255 && yC < 330) {
            return "B";
        } else if (yC >= 330 && yC < 405) {
            return "C";
        } else if (yC >= 405 && yC < 480) {
            return "D";
        } else if (yC >= 480 && yC < 555) {
            return "E";
        } else if (yC >= 555 && yC < 630) {
            return "F";
        } else if (yC >= 630 && yC < 705) {
            return "G";
        } else if (yC >= 705 && yC < 780) {
            return "H";
        } else if (yC >= 780 && yC < 855) {
            return "I";
        } else if (yC >= 855 && yC < 930) {
            return "J";
        }
        return "Error";
    }

    /**
     * setX - Sets the x value for the textview
     * @param xC - a given touch falue for x
     * @return 1-10 depending on where the player touched
     */
    public float setX(float xC) {
        if (xC >= 710 && xC < 785) {
            return 1;
        } else if (xC >= 785 && xC < 860) {
            return 2;
        } else if (xC >= 860 && xC < 935) {
            return 3;
        } else if (xC >= 935 && xC < 1010) {
            return 4;
        } else if (xC >= 1010 && xC < 1085) {
            return 5;
        } else if (xC >= 1085 && xC < 1160) {
            return 6;
        } else if (xC >= 1160 && xC < 1235) {
            return 7;
        } else if (xC >= 1235 && xC < 1310) {
            return 8;
        } else if (xC >= 1310 && xC < 1385) {
            return 9;
        } else if (xC >= 1385 && xC < 1460) {
            return 10;
        }
        return 0;
    }
}



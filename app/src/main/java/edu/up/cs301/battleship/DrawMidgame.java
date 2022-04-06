package edu.up.cs301.battleship;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Picture;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;

import java.util.ArrayList;

import edu.up.cs301.game.GameFramework.infoMessage.GameState;
import edu.up.cs301.game.R;
import edu.up.cs301.tictactoe.infoMessage.TTTState;

/**
 * DrawMidgame - A SurfaceView class that represents what is drawn during the
 * mid game/battle phase.
 *
 * @author Keoni Han
 * @author Austen Furutani
 * @author Tyler Santos
 * @author Steven Lee
 * @version Spring 2022 - 3/31/22
 */
public class DrawMidgame extends SurfaceView{
    private Paint blackPaint = new Paint();
    private Context context;
    public ArrayList<TapValues> tapValues = new ArrayList<TapValues>();

    protected BattleShipGameState state;
    protected int flashColor = Color.BLACK;

    Bitmap fivehp = BitmapFactory.decodeResource(getResources(), R.drawable.fivehpbs);
    private float fivehpLeft = 1814.0f;
    private float fivehpTop = 108.0f;
    Bitmap fourhp1 = BitmapFactory.decodeResource(getResources(), R.drawable.fourhpbs);
    private float fourhp1Left = 1684.0f;
    private float fourhp1Top = 70.0f;
    Bitmap fourhp2 = BitmapFactory.decodeResource(getResources(), R.drawable.fourhpbs);
    private float fourhp2Left = 1677.0f;
    private float fourhp2Top = 393.0f;
    Bitmap threehp1 = BitmapFactory.decodeResource(getResources(), R.drawable.threehpbs);
    private float threehp1Left = 1828.0f;
    private float threehp1Top = 500.0f;
    Bitmap threehp2 = BitmapFactory.decodeResource(getResources(), R.drawable.threehpbs);
    private float threehp2Left = 1830.0f;
    private float threehp2Top = 760.0f;
    Bitmap twohp = BitmapFactory.decodeResource(getResources(), R.drawable.twohpbs);
    private float twohpLeft = 1680.0f;
    private float twohpTop = 807.0f;

    public DrawMidgame(Context context) {//default constructor,
        super(context);
        initPaints();
        setWillNotDraw(false);//sets visible
    }

    public DrawMidgame(Context context, AttributeSet attirs){
        super(context, attirs);
        initPaints();
        setWillNotDraw(false);//sets visible
    }

    public DrawMidgame(Context context, AttributeSet attirs, int defStyle){
        super(context, attirs, defStyle);
        initPaints();
        setWillNotDraw(false);//sets visible
    }

    private void initPaints(){
        this.blackPaint.setColor(0xFF00FF00);
        this.blackPaint.setStyle(Paint.Style.FILL);
    }

    public void setState(BattleShipGameState state) {
        this.state = new BattleShipGameState(state, state.getPlayersTurn());
    }

    public void setFlashColor(int color) {
        this.flashColor = color;
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
//        TouchListener listener = new TouchListener(this, this.context);
//        this.setOnTouchListener(listener);

        //FLOAT - like a double but half the number of bits
        //drawOval(x,y x2, y2, paint);

        //


        Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.battleshipbackground);
        background = Bitmap.createScaledBitmap(background, getWidth(), getHeight(), false);

        //Draws the board for the use will use to select and play their move
        Bitmap grid = BitmapFactory.decodeResource(getResources(), R.drawable.updatedgrid);
        grid =  Bitmap.createScaledBitmap(grid, 1000, 1000, false);
        Bitmap playersGrid =  Bitmap.createScaledBitmap(grid, 400, 400, false);

        Bitmap remainingShips = BitmapFactory.decodeResource(getResources(), R.drawable.ships);
        remainingShips = Bitmap.createScaledBitmap(remainingShips, 150, 1000, false);



        //When user hits a ship a red marker will be placed
        Bitmap redMarker = BitmapFactory.decodeResource(getResources(), R.drawable.hitmarker);
        redMarker =  Bitmap.createScaledBitmap(redMarker, 300, 250, false);
        Bitmap enemyRedMarker = Bitmap.createScaledBitmap(redMarker, 100, 83, false);

        //A missed shot will be indicated with a white marker
        Bitmap whiteMarker = BitmapFactory.decodeResource(getResources(), R.drawable.missmarker);
        whiteMarker =  Bitmap.createScaledBitmap(whiteMarker, 300, 250, false);
        Bitmap enemyWhiteMarker =  Bitmap.createScaledBitmap(whiteMarker, 100, 83, false);

        //When the user selects their move the COOR will be identified with a target
        Bitmap userSelection = BitmapFactory.decodeResource(getResources(), R.drawable.tagetselector);
        userSelection =  Bitmap.createScaledBitmap(userSelection, 200, 150, false);


        Matrix matrix = new Matrix();
        matrix.postRotate(90);





        /**
         * Draws everything to surface view, as of now COOR is just giberish and guess work, will work out
         *a formula later
         */
        if(this.flashColor == Color.BLACK) {
            canvas.drawBitmap(background, 0.0f, 0.0f, blackPaint);
        }
        else {
            Paint tempPaint = new Paint();
            tempPaint.setColor(this.flashColor);
            canvas.drawRect(0.0f, 0.0f, getWidth(), getHeight(), tempPaint);
        }
        canvas.drawBitmap(grid, 550.0f, 25.0f, blackPaint);
        canvas.drawBitmap(playersGrid, 50.0f, 600.0f, blackPaint);
        canvas.drawBitmap(remainingShips, 1800.0f, 25.0f, blackPaint);
//        canvas.drawBitmap(whiteMarker, 150.0f, 290.0f, new Paint());
//        canvas.drawBitmap(redMarker, 490.0f, 290.0f, new Paint());
//        canvas.drawBitmap(userSelection, 540.0f, 450.0f, new Paint());
        for(TapValues tap : tapValues){
            Log.i("midgame", "onDraw: " + tap.getX() + " " +  tap.getY()) ;
            canvas.drawBitmap(whiteMarker, tap.getX(), tap.getY(), blackPaint);

        }
        fivehp = BitmapFactory.decodeResource(getResources(), R.drawable.fivehpbs);
        fivehp = Bitmap.createScaledBitmap(fivehp, 130, 25, false);
        fivehp = Bitmap.createBitmap(fivehp, 0, 0, fivehp.getWidth(), fivehp.getHeight(), matrix, true);

        fourhp1 = BitmapFactory.decodeResource(getResources(), R.drawable.fourhpbs);
        fourhp1 = Bitmap.createScaledBitmap(fourhp1, 98, 25, false);
        fourhp1 = Bitmap.createBitmap(fourhp1, 0, 0, fourhp1.getWidth(), fourhp1.getHeight(), matrix, true);

        fourhp2 = BitmapFactory.decodeResource(getResources(), R.drawable.fourhpbs);
        fourhp2 = Bitmap.createScaledBitmap(fourhp2, 98, 25, false);
        fourhp2 = Bitmap.createBitmap(fourhp2, 0, 0, fourhp2.getWidth(), fourhp2.getHeight(), matrix, true);

        //CREATES 3 hp BS #1
        threehp1 = BitmapFactory.decodeResource(getResources(), R.drawable.threehpbs);
        threehp1 = Bitmap.createScaledBitmap(threehp1, 72, 20, false);
        threehp1 = Bitmap.createBitmap(threehp1, 0, 0, threehp1.getWidth(), threehp1.getHeight(), matrix, true);

        //CREATES 3 hp BS #2
        threehp2 = BitmapFactory.decodeResource(getResources(), R.drawable.threehpbs);
        threehp2 = Bitmap.createScaledBitmap(threehp2, 72, 20, false);
        threehp2 = Bitmap.createBitmap(threehp2, 0, 0, threehp2.getWidth(), threehp2.getHeight(), matrix, true);

        //CREATES 2 hp BS
        twohp = BitmapFactory.decodeResource(getResources(), R.drawable.twohpbs);
        twohp = Bitmap.createScaledBitmap(twohp, 45, 20, false);
        twohp = Bitmap.createBitmap(twohp, 0, 0, twohp.getWidth(), twohp.getHeight(), matrix, true);





        if (state == null) {
            Log.i("State is Null", "onDraw: NULL");
            return;
        }





        //Draw on enemies board
        GameBoard drawBoard = this.state.getBoard(1);
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Log.i("NOT NULL", "");
                if(drawBoard.getCoordHit(row, col)){
                    Coordinates[][] board = drawBoard.getCurrentBoard();
                    float xDrift = 1.5f * (float)row;
                    float yDrift = 0.7f * (float)col;
                    float yVal = state.middleYOfCoord(board[row][col]) - (195.0f - yDrift) ;
                    float xVal = state.middleXOfCoord(board[row][col]) - (226.0f - xDrift);
                    if(board[row][col].getHasShip()){
                        canvas.drawBitmap(redMarker, xVal, yVal,blackPaint);
                    }
                    else {
                        canvas.drawBitmap(whiteMarker, xVal, yVal, blackPaint);
                    }
                    this.invalidate();
                }
            }
        }
        /**Draw on your board
         * Need to change the center methods
         * */


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

        for (int i = 0; i < playerFleet[0].length; i++) {
            if (playerFleet[0][i].getSize() == 5) {
                toPlace = playerFleet[0][i].getFirstCoord();
                fivehpLeft = state.middleXOfEnemyBoard(toPlace) - 21.5f;
                fivehpTop = state.middleYOfEnemyBoard(toPlace) - 16;
            }
            else if (playerFleet[0][i].getSize() == 4) {
                if (i == 1) {
                    toPlace = playerFleet[0][i].getFirstCoord();
                    fourhp1Left = state.middleXOfEnemyBoard(toPlace) - 19.5f;
                    if (toPlace.getX() > 7) {
                        fourhp1Left = state.middleXOfEnemyBoard(toPlace) - 21.5f;
                    }
                    else if (toPlace.getX() < 4) {
                        fivehpLeft = state.middleXOfEnemyBoard(toPlace) - 17.5f;
                    }
                    fourhp1Top = state.middleYOfEnemyBoard(toPlace) - 16;
                }
                else {
                    toPlace = playerFleet[0][i].getFirstCoord();
                    fourhp2Left = state.middleXOfEnemyBoard(toPlace) - 19.5f;
                    if (toPlace.getX() > 7) {
                        fourhp2Left = state.middleXOfEnemyBoard(toPlace) - 21.5f;
                    }
                    else if (toPlace.getX() < 4) {
                        fivehpLeft = state.middleXOfEnemyBoard(toPlace) - 17.5f;
                    }
                    fourhp2Top = state.middleYOfEnemyBoard(toPlace) - 16;
                }
            }
            else if (playerFleet[0][i].getSize() == 3) {
                if (i == 3) {
                    toPlace = playerFleet[0][i].getFirstCoord();
                    threehp1Left = state.middleXOfEnemyBoard(toPlace) - 17.5f;
                    if (toPlace.getX() > 7) {
                        threehp1Left = state.middleXOfEnemyBoard(toPlace) - 21.5f;
                    }
                    else if (toPlace.getX() < 4) {
                        fivehpLeft = state.middleXOfEnemyBoard(toPlace) - 17.5f;
                    }
                    threehp1Top= state.middleYOfEnemyBoard(toPlace) - 16;
                }
                else {
                    toPlace = playerFleet[0][i].getFirstCoord();
                    threehp2Left = state.middleXOfEnemyBoard(toPlace) - 17.5f;
                    if (toPlace.getX() > 7) {
                        threehp2Left = state.middleXOfEnemyBoard(toPlace) - 21.5f;
                    }
                    else if (toPlace.getX() < 4) {
                        fivehpLeft = state.middleXOfEnemyBoard(toPlace) - 17.5f;
                    }
                    threehp2Top = state.middleYOfEnemyBoard(toPlace) - 16;
                }
            }
            else if (playerFleet[0][i].getSize() == 2) {
                toPlace = playerFleet[0][i].getFirstCoord();
                twohpLeft = state.middleXOfEnemyBoard(toPlace) - 20;
                if (toPlace.getX() > 7) {
                    twohpLeft = state.middleXOfEnemyBoard(toPlace) - 21.5f;
                }
                else if (toPlace.getX() < 4) {
                    fivehpLeft = state.middleXOfEnemyBoard(toPlace) - 17.5f;
                }
                twohpTop = state.middleYOfEnemyBoard(toPlace) - 16;
            }

        }
        canvas.drawBitmap(fivehp, fivehpLeft, fivehpTop, blackPaint);
        canvas.drawBitmap(fourhp1, fourhp1Left, fourhp1Top, blackPaint);
        canvas.drawBitmap(fourhp2, fourhp2Left, fourhp2Top, blackPaint);
        canvas.drawBitmap(threehp1, threehp1Left, threehp1Top, blackPaint);
        canvas.drawBitmap(threehp2, threehp2Left, threehp2Top, blackPaint);
        canvas.drawBitmap(twohp, twohpLeft, twohpTop, blackPaint);
        this.invalidate();


//        drawEnemyBoard = this.state.getBoard(0);
        GameBoard drawEnemyBoard = this.state.getBoard(0);
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Log.i("NOT NULL", "");
                if(drawEnemyBoard.getCoordHit(row, col)){
                    Coordinates[][] board = drawEnemyBoard.getCurrentBoard();
                    float xDrift =  1.8f * (float)row;
                    float yDrift =  1.75f * (float)col;
                    float yVal = state.middleYOfEnemyBoard(board[row][col])  - (75 + yDrift);
                    float xVal = state.middleXOfEnemyBoard(board[row][col]) - (82 + xDrift);
                    if(board[row][col].getHasShip()){
                        canvas.drawBitmap(enemyRedMarker, xVal, yVal, blackPaint);
                    }
                    else {
                        canvas.drawBitmap(enemyWhiteMarker, xVal, yVal, blackPaint);
                    }
                    this.invalidate();
                }
            }
        }
    }


    //Setters to get positions of battleships from setup phase
    public void setFivehpLeft(float newValue) {
        fivehpLeft = newValue;
    }
    public void setFivehpTop(float newValue) {
        fivehpTop = newValue;
    }
    public void setFourhp1Left(float newValue) {
        fourhp1Left = newValue;
    }
    public void setFourhp1Top(float newValue) {
        fourhp1Top = newValue;
    }
    public void setFourhp2Left(float newValue) {
        fourhp2Left = newValue;
    }
    public void setFourhp2Top(float newValue) {
        fourhp2Top = newValue;
    }
    public void setThreehp1Left(float newValue) {
        threehp1Left = newValue;
    }
    public void setThreehp1Top(float newValue) {
        threehp1Top = newValue;
    }
    public void setThreehp2Left(float newValue) {
        threehp2Left = newValue;
    }
    public void setThreehp2Top(float newValue) {
        threehp2Top = newValue;
    }
    public void setTwohpLeft(float newValue) {
        twohpLeft = newValue;
    }
    public void setTwohpTop(float newValue) {
        twohpTop = newValue;
    }
}

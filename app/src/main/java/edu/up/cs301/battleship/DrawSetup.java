package edu.up.cs301.battleship;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;


import java.util.ArrayList;

import edu.up.cs301.game.R;

/**
 * DrawSetup - A SurfaceView Class that represents what is being drawn during
 * the setup phase of the game.
 *
 * @author Austen Furutani
 * @author Keoni Han
 * @author Tyler Santos
 * @author Steven Lee
 */
public class DrawSetup extends SurfaceView {

    private Paint blackPaint = new Paint();
    private ArrayList<BattleshipObj> battleshipArrayList = new ArrayList<>();
    private Paint orangePaint = new Paint();
    public int playerID;
    private Context context;

    // Instance variables for the coordinates that check the original positions so you can reset the position of the ships
    final private float fivehpLeftInitial = 1814.0f;
    final private float fivehpTopInitial = 108.0f;
    final private float fourhp1LeftInitial = 1684.0f;
    final private float fourhp1TopInitial = 70.0f;
    final private float fourhp2LeftInitial = 1677.0f;
    final private float fourhp2TopInitial = 393.0f;
    final private float threehp1LeftInitial = 1828.0f;
    final private float threehp1TopInitial = 500.0f;
    final private float threehp2LeftInitial = 1830.0f;
    final private float threehp2TopInitial = 760.0f;
    final private float twohpLeftInitial = 1680.0f;
    final private float twohpTopInitial = 807.0f;

    // Instance Variables to set the current ship's location
    Bitmap fivehp = BitmapFactory.decodeResource(getResources(), R.drawable.fivehpbs);
    private float fivehpLeft = fivehpLeftInitial;
    private float fivehpTop = fivehpTopInitial;
    Bitmap fourhp1 = BitmapFactory.decodeResource(getResources(), R.drawable.fourhpbs);
    private float fourhp1Left = fourhp1LeftInitial;
    private float fourhp1Top = fourhp1TopInitial;
    Bitmap fourhp2 = BitmapFactory.decodeResource(getResources(), R.drawable.fourhpbs);
    private float fourhp2Left = fourhp2LeftInitial;
    private float fourhp2Top = fourhp2TopInitial;
    Bitmap threehp1 = BitmapFactory.decodeResource(getResources(), R.drawable.threehpbs);
    private float threehp1Left = threehp1LeftInitial;
    private float threehp1Top = threehp1TopInitial;
    Bitmap threehp2 = BitmapFactory.decodeResource(getResources(), R.drawable.threehpbs);
    private float threehp2Left = threehp2LeftInitial;
    private float threehp2Top = threehp2TopInitial;
    Bitmap twohp = BitmapFactory.decodeResource(getResources(), R.drawable.twohpbs);
    private float twohpLeft = twohpLeftInitial;
    private float twohpTop = twohpTopInitial;

    private int selectedShipId = 0;
    private int selectedShipIdfinal = 0;
    private float downX = 0.0f;
    private float downY = 0.0f;

    // True means vertical
    // False means horizontal
    private boolean rotFiveHp = true;
    private boolean rotFourHp1 = true;
    private boolean rotFourHp2 = true;
    private boolean rotThreeHp1 = true;
    private boolean rotThreeHp2= true;
    private boolean rotTwoHP = true;

    protected BattleShipGameState state;

    public DrawSetup(Context context) {//default constructor,
        super(context);
        initPaints();
        setWillNotDraw(false);//sets visible
    }

    public DrawSetup(Context context, AttributeSet attirs) {
        super(context, attirs);
        initPaints();
        setWillNotDraw(false);//sets visible
    }

    public DrawSetup(Context context, AttributeSet attirs, int defStyle) {
        super(context, attirs, defStyle);
        initPaints();
        setWillNotDraw(false);//sets visible
    }

    private void initPaints() {
        this.blackPaint.setColor(0xFF000000);
        this.blackPaint.setStyle(Paint.Style.FILL);
        this.orangePaint.setColor(0xFFFF6700);
        this.orangePaint.setStyle(Paint.Style.FILL);
    }

    public void setState(BattleShipGameState state) {
        this.state = new BattleShipGameState(state);
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Matrix matrix = new Matrix();
        matrix.postRotate(90);


        /**Creating the battle ships
         * with varying HP
         */
        fivehp = BitmapFactory.decodeResource(getResources(), R.drawable.fivehpbs);
        fivehp = Bitmap.createScaledBitmap(fivehp, 360, 70, false);
        if(rotFiveHp) {
            fivehp = Bitmap.createBitmap(fivehp, 0, 0, fivehp.getWidth(), fivehp.getHeight(), matrix, true);
        }

        fourhp1 = BitmapFactory.decodeResource(getResources(), R.drawable.fourhpbs);
        fourhp1 = Bitmap.createScaledBitmap(fourhp1, 288, 70, false);
        if(rotFourHp1) {
            fourhp1 = Bitmap.createBitmap(fourhp1, 0, 0, fourhp1.getWidth(), fourhp1.getHeight(), matrix, true);
        }
//        fourhp1 = Bitmap.createBitmap(fourhp1, 0, 0, fourhp1.getWidth(), fourhp1.getHeight(), matrix, true);

        fourhp2 = BitmapFactory.decodeResource(getResources(), R.drawable.fourhpbs);
        fourhp2 = Bitmap.createScaledBitmap(fourhp2, 288, 70, false);
//        fourhp2 = Bitmap.createBitmap(fourhp2, 0, 0, fourhp2.getWidth(), fourhp2.getHeight(), matrix, true);
        if(rotFourHp2) {
            fourhp2 = Bitmap.createBitmap(fourhp2, 0, 0, fourhp2.getWidth(), fourhp2.getHeight(), matrix, true);
        }

        //CREATES 3 hp BS #1
        threehp1 = BitmapFactory.decodeResource(getResources(), R.drawable.threehpbs);
        threehp1 = Bitmap.createScaledBitmap(threehp1, 216, 60, false);
//        threehp1 = Bitmap.createBitmap(threehp1, 0, 0, threehp1.getWidth(), threehp1.getHeight(), matrix, true);
        if(rotThreeHp1) {
            threehp1 = Bitmap.createBitmap(threehp1, 0, 0, threehp1.getWidth(), threehp1.getHeight(), matrix, true);
        }

        //CREATES 3 hp BS #2
        threehp2 = BitmapFactory.decodeResource(getResources(), R.drawable.threehpbs);
        threehp2 = Bitmap.createScaledBitmap(threehp2, 216, 60, false);
//        threehp2 = Bitmap.createBitmap(threehp2, 0, 0, threehp2.getWidth(), threehp2.getHeight(), matrix, true);
        if(rotThreeHp2) {
            threehp2 = Bitmap.createBitmap(threehp2, 0, 0, threehp2.getWidth(), threehp2.getHeight(), matrix, true);
        }

        //CREATES 2 hp BS
        twohp = BitmapFactory.decodeResource(getResources(), R.drawable.twohpbs);
        twohp = Bitmap.createScaledBitmap(twohp, 144, 60, false);
//        twohp = Bitmap.createBitmap(twohp, 0, 0, twohp.getWidth(), twohp.getHeight(), matrix, true);
        if(rotTwoHP) {
            twohp = Bitmap.createBitmap(twohp, 0, 0, twohp.getWidth(), twohp.getHeight(), matrix, true);
        }


        //Set background
        Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.battleshipbackground);
        background = Bitmap.createScaledBitmap(background, getWidth(), getHeight(), false);

        //Draws the board for the use will use to select and play their move
        Bitmap grid = BitmapFactory.decodeResource(getResources(), R.drawable.updatedgrid);
        grid = Bitmap.createScaledBitmap(grid, 1000, 1000, false);


        /**
         *Draws static elements onto screen
         */
        canvas.drawBitmap(background, 0.0f, 0.0f, blackPaint);
        canvas.drawBitmap(grid, 550.0f, 25.0f, blackPaint);

        canvas.drawRect(1650.0f, 50.0f, 1900, 1050, orangePaint);
        Coordinates toPlace;

        // Checks to see if the ship is horizontal or vertical
        // Vertical - 1st if statement
        // Horizontal - else statement
        if (rotFiveHp) {
            canvas.drawBitmap(fivehp, fivehpLeft, fivehpTop, blackPaint);
        }
        else {
            canvas.drawBitmap(fivehp, fivehpLeft, fivehpTop - 15, blackPaint);
        }

        if (rotFourHp1) {
            canvas.drawBitmap(fourhp1, fourhp1Left, fourhp1Top, blackPaint);
        }
        else {
            canvas.drawBitmap(fourhp1, fourhp1Left, fourhp1Top - 15, blackPaint);
        }

        if (rotFourHp2) {
            canvas.drawBitmap(fourhp2, fourhp2Left, fourhp2Top, blackPaint);
        }
        else {
            canvas.drawBitmap(fourhp2, fourhp2Left, fourhp2Top - 15, blackPaint);
        }

        if (rotThreeHp1) {
            canvas.drawBitmap(threehp1, threehp1Left, threehp1Top, blackPaint);
        }
        else {

            canvas.drawBitmap(threehp1, threehp1Left, threehp1Top - 15, blackPaint);
        }

        if (rotThreeHp2) {
            canvas.drawBitmap(threehp2, threehp2Left, threehp2Top, blackPaint);
        }
        else {
            canvas.drawBitmap(threehp2, threehp2Left, threehp2Top - 15, blackPaint);
        }

        if (rotTwoHP) {
            canvas.drawBitmap(twohp, twohpLeft, twohpTop, blackPaint);
        }
        else {
            canvas.drawBitmap(twohp, twohpLeft, twohpTop - 15, blackPaint);
        }

        if (state == null) {
            return;
        }
        /**Draw on your board
         * Need to change the center methods
         * */
        GameBoard drawEnemyBoard = this.state.getBoard(0);
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if (drawEnemyBoard.getCoordHit(row, col)) {
                    Coordinates[][] board = drawEnemyBoard.getCurrentBoard();
                    if (drawEnemyBoard.getHasShip()) {

                    }
                    this.invalidate();
                }
            }
        }
    }

    public int onTouchEventNew(MotionEvent event) {

        int newSize = 0;
        BattleshipObj selectedBattleShip = new BattleshipObj(0, null);

        //checks which action is happening
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {  //checks which ship is being selected
                selectedShipIdfinal = 0;
                downX = event.getX();
                downY = event.getY();
                selectedShipId = containsWhichShip(event.getX(),event.getY());
                selectedShipIdfinal = containsWhichShip(event.getX(),event.getY());
                break;
            }
            case MotionEvent.ACTION_MOVE:
                switch(selectedShipId) {
                    // updates the ships drawn coordinates to the cursor/finger
                    // (35.0f is the adjustment to move the center of the ship to the user's finger
                    case 1: {
                        fivehpLeft = event.getX() - 35.0f;
                        fivehpTop = event.getY();
                        break;
                    }
                    case 2: {
                        fourhp1Left = event.getX() - 35.0f;
                        fourhp1Top = event.getY();
                        break;
                    }
                    case 3: {
                        fourhp2Left = event.getX() - 35.0f;
                        fourhp2Top = event.getY();
                        break;
                    }
                    case 4: {
                        threehp1Left = event.getX() - 35.0f;
                        threehp1Top = event.getY();
                        break;
                    }
                    case 5: {
                        threehp2Left = event.getX() - 35.0f;
                        threehp2Top = event.getY();
                        break;
                    }
                    case 6: {
                        twohpLeft = event.getX() - 35.0f;
                        twohpTop = event.getY();
                        break;
                    }
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP: {
                //places it, gets the ship size and sends it back to the human player method
                float upXVal = event.getX();
                float upYVal = event.getY();
                Coordinates selectedCoord;

                //Rotates ships if tapped on once and not dragged
                if (event.getX() == downX && event.getY() == downY) {
                    switch (selectedShipId) {
                        case 1:{
                            rotFiveHp = !rotFiveHp;
                            invalidate();
                            break;
                        }
                        case 2: {
                            rotFourHp1 = !rotFourHp1;
                            invalidate();
                            break;
                        }
                        case 3: {
                            rotFourHp2 = !rotFourHp2;
                            invalidate();
                            break;
                        }
                        case 4: {
                            rotThreeHp1 = !rotThreeHp1;
                            invalidate();
                            break;
                        }
                        case 5: {
                            rotThreeHp2 = !rotThreeHp2;
                            invalidate();
                            break;
                        }
                        case 6: {
                            rotTwoHP = !rotTwoHP;
                            invalidate();
                            break;
                        }
                    }
                }

                //Checks to see if placement is out of bounds

                // First if-statement checks if it's off the board
                // second if-statement checks if the part of the ships gets placed out of bounds based on the orientation
                // third if-statment updates the location coordinates of the
                // ship and adjusts it based on the inconsistencies of the board grids

                switch(selectedShipId) {
                    case 1: {
                        if (event.getX() < 708.9f || event.getX() > 1462.95f || event.getY() < 185.017f || event.getY() > 927.99f) {
                            resetFivehp();
                            invalidate();
                            return 0;
                        }
                        else if (checkInitialPlaceOutOfBounds(5, upXVal, upYVal, rotFiveHp)) {
                            resetFivehp();
                            rotFiveHp = true;
                            invalidate();
                            return 0;
                        }
                        else {
                            selectedCoord = BattleShipGameState.xyToCoordSetupGame(upXVal, upYVal);
                            if (selectedCoord.getX() == 0 || selectedCoord.getX() == 1) {
                                fivehpLeft = BattleShipGameState.middleXOfCoord(selectedCoord) - 30;
                                fivehpTop = BattleShipGameState.middleYOfCoord(selectedCoord) - 16;
                            }
                            else if (selectedCoord.getX() <= 4) {
                                fivehpLeft = BattleShipGameState.middleXOfCoord(selectedCoord) - 25;
                                fivehpTop = BattleShipGameState.middleYOfCoord(selectedCoord) - 16;
                            }
                            else {
                                fivehpLeft = BattleShipGameState.middleXOfCoord(selectedCoord) - 20;
                                fivehpTop = BattleShipGameState.middleYOfCoord(selectedCoord) - 16;
                            }
                            invalidate();
                        }
                        break;
                    }
                    case 2: {
                        if (event.getX() < 708.9f || event.getX() > 1462.95f || event.getY() < 185.017f || event.getY() > 927.99f) {
                            resetFourhp1();
                            invalidate();
                            return 0;
                        }
                        else if (checkInitialPlaceOutOfBounds(4, upXVal, upYVal, rotFourHp1)) {
                            resetFourhp1();
                            rotFourHp1 = true;
                            invalidate();
                            return 0;
                        }
                        else {
                            selectedCoord = BattleShipGameState.xyToCoordSetupGame(upXVal, upYVal);
                            if (selectedCoord.getX() == 0 || selectedCoord.getX() == 1) {
                                fourhp1Left = BattleShipGameState.middleXOfCoord(selectedCoord) - 30;
                                fourhp1Top = BattleShipGameState.middleYOfCoord(selectedCoord) - 16;
                            }
                            else if (selectedCoord.getX() <= 4) {
                                fourhp1Left = BattleShipGameState.middleXOfCoord(selectedCoord) - 25;
                                fourhp1Top = BattleShipGameState.middleYOfCoord(selectedCoord) - 16;
                            }
                            else {
                                fourhp1Left = BattleShipGameState.middleXOfCoord(selectedCoord) - 20;
                                fourhp1Top = BattleShipGameState.middleYOfCoord(selectedCoord) - 16;
                            }

                            invalidate();
                        }
                        break;
                    }
                    case 3: {
                        if (event.getX() < 708.9f || event.getX() > 1462.95f || event.getY() < 185.017f || event.getY() > 927.99f) {
                            resetFourhp2();
                            invalidate();
                            return 0;
                        }
                        else if (checkInitialPlaceOutOfBounds(4, upXVal, upYVal, rotFourHp2)) {
                            resetFourhp2();
                            rotFourHp2 = true;
                            invalidate();
                            return 0;
                        }
                        else {
                            selectedCoord = BattleShipGameState.xyToCoordSetupGame(upXVal, upYVal);
                            if (selectedCoord.getX() == 0 || selectedCoord.getX() == 1) {
                                fourhp2Left = BattleShipGameState.middleXOfCoord(selectedCoord) - 30;
                                fourhp2Top = BattleShipGameState.middleYOfCoord(selectedCoord) - 16;
                            }
                            else if (selectedCoord.getX() <= 4) {
                                fourhp2Left = BattleShipGameState.middleXOfCoord(selectedCoord) - 25;
                                fourhp2Top = BattleShipGameState.middleYOfCoord(selectedCoord) - 16;
                            }
                            else {
                                fourhp2Left = BattleShipGameState.middleXOfCoord(selectedCoord) - 20;
                                fourhp2Top = BattleShipGameState.middleYOfCoord(selectedCoord) - 16;
                            }
                            invalidate();
                        }
                        break;
                    }
                    case 4: {
                        if (event.getX() < 708.9f || event.getX() > 1462.95f || event.getY() < 185.017f || event.getY() > 927.99f) {
                            resetThreehp1();
                            invalidate();
                            return 0;
                        }
                        else if (checkInitialPlaceOutOfBounds(3, upXVal, upYVal, rotThreeHp1)) {
                            resetThreehp1();
                            rotThreeHp1 = true;
                            invalidate();
                            return 0;
                        }
                        else {
                            selectedCoord = BattleShipGameState.xyToCoordSetupGame(upXVal, upYVal);
                            if (selectedCoord.getX() == 0 || selectedCoord.getX() == 1) {
                                threehp1Left = BattleShipGameState.middleXOfCoord(selectedCoord) - 30;
                                threehp1Top = BattleShipGameState.middleYOfCoord(selectedCoord) - 16;
                            }
                            else if (selectedCoord.getX() <= 4) {
                                threehp1Left = BattleShipGameState.middleXOfCoord(selectedCoord) - 25;
                                threehp1Top = BattleShipGameState.middleYOfCoord(selectedCoord) - 16;
                            }
                            else {
                                threehp1Left = BattleShipGameState.middleXOfCoord(selectedCoord) - 20;
                                threehp1Top = BattleShipGameState.middleYOfCoord(selectedCoord) - 16;
                            }

                            invalidate();
                        }
                        break;
                    }
                    case 5: {
                        if (event.getX() < 708.9f || event.getX() > 1462.95f || event.getY() < 185.017f || event.getY() > 927.99f) {
                            resetThreehp2();
                            invalidate();
                            return 0;
                        }
                        else if (checkInitialPlaceOutOfBounds(3, upXVal, upYVal, rotThreeHp2)) {
                            resetThreehp2();
                            rotThreeHp2 = true;
                            invalidate();
                            return 0;
                        }
                        else {
                            selectedCoord = BattleShipGameState.xyToCoordSetupGame(upXVal, upYVal);
                            if (selectedCoord.getX() == 0 || selectedCoord.getX() == 1) {
                                threehp2Left = BattleShipGameState.middleXOfCoord(selectedCoord) - 30;
                                threehp2Top = BattleShipGameState.middleYOfCoord(selectedCoord) - 16;
                            }
                            else if (selectedCoord.getX() <= 4) {
                                threehp2Left = BattleShipGameState.middleXOfCoord(selectedCoord) - 25;
                                threehp2Top = BattleShipGameState.middleYOfCoord(selectedCoord) - 16;
                            }
                            else {
                                threehp2Left = BattleShipGameState.middleXOfCoord(selectedCoord) - 20;
                                threehp2Top = BattleShipGameState.middleYOfCoord(selectedCoord) - 16;
                            }

                            invalidate();
                        }
                        break;
                    }
                    case 6: {
                        if (event.getX() < 708.9f || event.getX() > 1462.95f || event.getY() < 185.017f || event.getY() > 927.99f) {
                            resetTwohp();
                            invalidate();
                            return 0;
                        }
                        else if (checkInitialPlaceOutOfBounds(2, upXVal, upYVal, rotTwoHP)) {
                            resetTwohp();
                            rotTwoHP = true;
                            invalidate();
                            return 0;
                        }
                        else {
                            selectedCoord = BattleShipGameState.xyToCoordSetupGame(upXVal, upYVal);
                            if (selectedCoord.getX() == 0 || selectedCoord.getX() == 1) {
                                twohpLeft = BattleShipGameState.middleXOfCoord(selectedCoord) - 30;
                                twohpTop = BattleShipGameState.middleYOfCoord(selectedCoord) - 16;
                            }
                            else if (selectedCoord.getX() <= 4) {
                                twohpLeft = BattleShipGameState.middleXOfCoord(selectedCoord) - 25;
                                twohpTop = BattleShipGameState.middleYOfCoord(selectedCoord) - 16;
                            }
                            else {
                                twohpLeft = BattleShipGameState.middleXOfCoord(selectedCoord) - 20;
                                twohpTop = BattleShipGameState.middleYOfCoord(selectedCoord) - 16;
                            }
                            invalidate();
                        }
                        break;
                    }
                }
                this.invalidate();
                selectedShipId = 0;
                break;
            }

        }
        return selectedShipIdfinal;
    }


    /**
     * checkInitialPlaceOutOfBounds - Checks to see if the coordinate where the user dragged
     * results in a boat being partially out of bounds
     * @param size
     * @param x
     * @param y
     * @param rotated
     * @return true if it's not a valid placement and false if it's valid
     */
    public static boolean checkInitialPlaceOutOfBounds(int size, float x, float y, boolean rotated) {
        Coordinates placedCoord = BattleShipGameState.xyToCoordSetupGame(x,y);
        int selectedBoardToEnd;
        if (placedCoord == null) {
            return true;
        }
        if (rotated == true) {
            selectedBoardToEnd = 10 - placedCoord.getY();
        }
        else {
            selectedBoardToEnd = 10 - placedCoord.getX();
        }

        if (selectedBoardToEnd < size) {
            return true;
        }
        return false;
    }

    /**
     * containsWhichShip - Checks to see which ship is tapped based on the coordinates of the tap
     * passed in as the parameters
     *
     * Ship ID's
     * 1 - FiveHP ship
     * 2 - FourHP1 ship
     * 3 - FourHP2 ship
     * 4 - ThreeHP1 ship
     * 5 - ThreeHP2 ship
     * 6 - TwoHP ship
     *
     * @param x
     * @param y
     * @return returns the ship id based on which ship is selected
     */
    public int containsWhichShip(float x, float y) {
        // Checks to see if the ship is rotated
        if (rotFiveHp) {
            if (x >= fivehpLeft && x <= fivehpLeft + 70 && y >= fivehpTop && y <= fivehpTop + 360) { // top right ship 5hp
                return 1;
            }
        }
        else {
            if (x >= fivehpLeft && x <= fivehpLeft + 360 && y >= fivehpTop - 15 && y <= fivehpTop + 70 - 15) { // top right ship 5hp
                return 1;
            }
        }

        //Four hp 1 ship
        if (rotFourHp1) {
            if (x >= fourhp1Left && x <= fourhp1Left + 70 && y >= fourhp1Top && y <= fourhp1Top + 288) { //
                return 2;
            }
        }
        else {
            if (x >= fourhp1Left && x <= fourhp1Left + 288 && y >= fourhp1Top - 15 && y <= fourhp1Top + 70 - 15) { //
                return 2;
            }
        }

        //Four hp 2 ship
        if (rotFourHp2) {
            if (x >= fourhp2Left && x <= fourhp2Left + 70 && y >= fourhp2Top && y <= fourhp2Top + 288) {
                return 3;
            }
        }
        else {
            if (x >= fourhp2Left && x <= fourhp2Left + 288 && y >= fourhp2Top - 15 && y <= fourhp2Top + 70 - 15) {
                return 3;
            }
        }

        if (rotThreeHp1) {
            if (x >= threehp1Left && x <= threehp1Left + 60 && y >= threehp1Top && y <= threehp1Top + 216) {
                return 4;
            }
        }
        else {
            if (x >= threehp1Left && x <= threehp1Left + 216 && y >= threehp1Top - 15 && y <= threehp1Top + 60 - 15) {
                return 4;
            }
        }

        if (rotThreeHp2) {
            if (x >= threehp2Left && x <= threehp2Left + 60 && y >= threehp2Top && y <= threehp2Top + 216) {
                return 5;
            }
        }
        else {
            if (x >= threehp2Left && x <= threehp2Left + 216 && y >= threehp2Top - 15 && y <= threehp2Top + 60 - 15) {
                return 5;
            }
        }

        //Checks if ship 2 is being selected
        if (rotTwoHP) {
            if (x >= twohpLeft && x <= twohpLeft + 60 && y >= twohpTop && y <= twohpTop + 144) {
                return 6;
            }
        }
        else {
            if (x >= twohpLeft && x <= twohpLeft + 144 && y >= twohpTop - 15 && y <= twohpTop + 60 - 15) {
                return 6;
            }
        }
        return 0;
    }

    /**
     * checkOverlapping - Checks to see if any ships are overlapping
     * by checking the gamestate's fleets
     */
    public void checkOverlapping(){
        BattleshipObj[] fleet = state.getPlayersFleet()[playerID];
        if(fleet[0].getSize() == 1){
            resetFivehp();
        }
        if(fleet[1].getSize() == 1){
            resetFourhp1();
        }
        if(fleet[2].getSize() == 1){
            resetFourhp2();
        }
        if(fleet[3].getSize() == 1){
            resetThreehp1();
        }
        if(fleet[4].getSize() == 1){
            resetThreehp2();
        }
        if(fleet[5].getSize() == 1){
            resetTwohp();
        }
        this.invalidate();
    }

    /**
     * checkIfShipsAreReset - Checks to see if any of the ships are in
     * it's initial position to prevent bugs from happening.
     * (i.e. rotating an already placed ship would tp the
     * ship back to the initial spot and allow the player to continue to midgame)
     *
     * @return returns True if one ship coordinate is the same as the initial and False if all ships are on the board
     */
    public boolean checkIfShipsAreReset() {
        float[] shipPos=
                {fivehpTop, fivehpLeft,
                        fourhp1Top, fourhp1Left,
                        fourhp2Top, fourhp2Left,
                        threehp1Top, threehp1Left,
                        threehp2Top, threehp2Left,
                        twohpTop, twohpLeft       };

        float[] shipInitial =
                {fivehpTopInitial, fivehpLeftInitial,
                        fourhp1TopInitial, fourhp1LeftInitial,
                        fourhp2TopInitial, fourhp2LeftInitial,
                        threehp1TopInitial, threehp1LeftInitial,
                        threehp2TopInitial, threehp2LeftInitial,
                        twohpTopInitial, twohpLeftInitial};


        for (int i=0; i < shipInitial.length; i++) {
            if (shipPos[i] == shipInitial[i]) {
                return true;
            }
        }
        return false;
    }

    public void setPlayerID(int playerID) { this.playerID = playerID;}

    /**
     * reset____ - sets the corresponding ship to the initial position
     */
    public void resetFivehp() {
        fivehpLeft = fivehpLeftInitial;
        fivehpTop = fivehpTopInitial;
    }
    public void resetFourhp1() {
        fourhp1Left = fourhp1LeftInitial;
        fourhp1Top = fourhp1TopInitial;
    }
    public void resetFourhp2() {
        fourhp2Left = fourhp2LeftInitial;
        fourhp2Top = fourhp2TopInitial;
    }
    public void resetThreehp1() {
        threehp1Left = threehp1LeftInitial;
        threehp1Top = threehp1TopInitial;
    }
    public void resetThreehp2() {
        threehp2Left = threehp2LeftInitial;
        threehp2Top = threehp2TopInitial;
    }
    public void resetTwohp() {
        twohpLeft = twohpLeftInitial;
        twohpTop = twohpTopInitial;
    }

    public void swapRotFiveHp(){
        this.rotFiveHp = !this.rotFiveHp;
    }

    public boolean getRotFiveHp() {return rotFiveHp;}
    public boolean getRotFourHp1() {return rotFourHp1;}
    public boolean getRotFourHp2() {return rotFourHp2;}
    public boolean getRotThreeHp1() {return rotThreeHp1;}
    public boolean getRotThreeHp2() {return rotThreeHp2;}
    public boolean getRotTwoHP() {return rotTwoHP;}



}



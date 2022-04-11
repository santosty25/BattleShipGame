package edu.up.cs301.battleship;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.text.method.Touch;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

import androidx.constraintlayout.widget.ConstraintSet;

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

    private int selectedShipId = 0;

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
        this.state = new BattleShipGameState(state, state.getPlayersTurn());
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
        fourhp1 = Bitmap.createBitmap(fourhp1, 0, 0, fourhp1.getWidth(), fourhp1.getHeight(), matrix, true);

        fourhp2 = BitmapFactory.decodeResource(getResources(), R.drawable.fourhpbs);
        fourhp2 = Bitmap.createScaledBitmap(fourhp2, 288, 70, false);
        fourhp2 = Bitmap.createBitmap(fourhp2, 0, 0, fourhp2.getWidth(), fourhp2.getHeight(), matrix, true);

        //CREATES 3 hp BS #1
        threehp1 = BitmapFactory.decodeResource(getResources(), R.drawable.threehpbs);
        threehp1 = Bitmap.createScaledBitmap(threehp1, 216, 60, false);
        threehp1 = Bitmap.createBitmap(threehp1, 0, 0, threehp1.getWidth(), threehp1.getHeight(), matrix, true);

        //CREATES 3 hp BS #2
        threehp2 = BitmapFactory.decodeResource(getResources(), R.drawable.threehpbs);
        threehp2 = Bitmap.createScaledBitmap(threehp2, 216, 60, false);
        threehp2 = Bitmap.createBitmap(threehp2, 0, 0, threehp2.getWidth(), threehp2.getHeight(), matrix, true);

        //CREATES 2 hp BS
        twohp = BitmapFactory.decodeResource(getResources(), R.drawable.twohpbs);
        twohp = Bitmap.createScaledBitmap(twohp, 144, 60, false);
        twohp = Bitmap.createBitmap(twohp, 0, 0, twohp.getWidth(), twohp.getHeight(), matrix, true);


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
        canvas.drawBitmap(fivehp, fivehpLeft, fivehpTop, blackPaint);
        canvas.drawBitmap(fourhp1, fourhp1Left, fourhp1Top, blackPaint);
        canvas.drawBitmap(fourhp2, fourhp2Left, fourhp2Top, blackPaint);
        canvas.drawBitmap(threehp1, threehp1Left, threehp1Top, blackPaint);
        canvas.drawBitmap(threehp2, threehp2Left, threehp2Top, blackPaint);
        canvas.drawBitmap(twohp, twohpLeft, twohpTop, blackPaint);
        if (state == null) {
            Log.i("State is Null", "onDraw: NULL");
            return;
        }
        /**Draw on your board
         * Need to change the center methods
         * */
        GameBoard drawEnemyBoard = this.state.getBoard(0);
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Log.i("NOT NULL", "");
                if (drawEnemyBoard.getCoordHit(row, col)) {
                    Coordinates[][] board = drawEnemyBoard.getCurrentBoard();
                    if (drawEnemyBoard.getHasShip()) {

                    }
                    this.invalidate();
                }
            }
        }

//        BattleshipObj[][] playerFleet= state.getPlayersFleet(); // 5 4 4 3 3 2
//        for (int i = 0; i < playerFleet[playerID].length; i++) {
//            float xValue;
//            float yValue;
//            if(playerFleet[playerID][i].getSize() == 5){
//                toPlace = playerFleet[playerID][i].getFirstCoord();
//                xValue = state.middleXOfEnemyBoard(toPlace) + 975;
//                yValue = state.middleYOfCoord(toPlace) - 25;
//                Log.i("ID", "onDraw: " + selectedShipId);
//                if(selectedShipId != 1){
//                    fivehpLeft = xValue;
//                    fivehpTop = yValue;
//                }
//                canvas.drawBitmap(fivehp, fivehpLeft, fivehpTop, blackPaint);
//                this.invalidate();
//            }
//            else if(playerFleet[playerID][i].getSize() == 4){
//                toPlace = playerFleet[playerID][i].getFirstCoord();
//                xValue = state.middleXOfEnemyBoard(toPlace) + 975;
//                yValue = state.middleYOfCoord(toPlace) - 25;
//                if(playerFleet[playerID][i].getTwinShip() == 0){
//                    if(selectedShipId != 2) {
//                        fourhp1Left = xValue;
//                        fourhp1Left = yValue;
//                    }
//                    canvas.drawBitmap(fourhp1, fourhp1Left, fourhp1Top, blackPaint);
//                }
//
//            }
//
//        }


        //canvas.drawBitmap(fivehp, fivehpLeft, fivehpTop, blackPaint);
        canvas.drawBitmap(fourhp1, fourhp1Left, fourhp1Top, blackPaint);
        canvas.drawBitmap(fourhp2, fourhp2Left, fourhp2Top, blackPaint);
        canvas.drawBitmap(threehp1, threehp1Left, threehp1Top, blackPaint);
        canvas.drawBitmap(threehp2, threehp2Left, threehp2Top, blackPaint);
        canvas.drawBitmap(twohp, twohpLeft, twohpTop, blackPaint);
    }

    public int onTouchEventNew(MotionEvent event) {
        BattleshipObj selectedBattleShip = new BattleshipObj(0, null);

        int newSize = 0;
        //checks which action is happening
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {  //checks which ship is being selected
                float x = event.getX();
                float y = event.getY();
                if (x >= fivehpLeft && x <= fivehpLeft + 70 && y >= fivehpTop && y <= fivehpTop + 360) { // top right ship 5hp
                    selectedShipId = 1;
                }
                else if (x >= fourhp1Left && x <= fourhp1Left + 70 && y >= fourhp1Top && y <= fourhp1Top + 288) { //
                    selectedShipId = 2;
                }
                else if (x >= fourhp2Left && x <= fourhp2Left + 70 && y >= fourhp2Top && y <= fourhp2Top + 288) {
                    selectedShipId = 3;
                }
                else if (x >= threehp1Left && x <= threehp1Left + 60 && y >= threehp1Top && y <= threehp1Top + 216) {
                    selectedShipId = 4;
                }
                else if (x >= threehp2Left && x <= threehp2Left + 60 && y >= threehp2Top && y <= threehp2Top + 216) {
                    selectedShipId = 5;
                }
                else if (x >= twohpLeft && x <= twohpLeft + 60 && y >= twohpTop && y <= twohpTop + 144) {
                    selectedShipId = 6;
                }

                break;
            }
            case MotionEvent.ACTION_MOVE:
                switch(selectedShipId) {   //updatest the ships drawn coordinates to the cursor/finger
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
            case MotionEvent.ACTION_UP: { //places it, gets the ship size and sends it back to the human player method
                float upXVal = event.getX();
                float upYVal = event.getY();
                Coordinates selectedCoord;
                switch(selectedShipId) {
                    case 1: {
                        if (event.getX() < 708.9f || event.getX() > 1462.95f || event.getY() < 185.017f || event.getY() > 927.99f) {
                            fivehpLeft = fivehpLeftInitial;
                            fivehpTop = fivehpTopInitial;
                            rotFiveHp = true;
                            invalidate();
                            return 0;
                        }
                        else if (checkInitialPlaceOutOfBounds(5, upXVal, upYVal)) {
                            fivehpLeft = fivehpLeftInitial;
                            fivehpTop = fivehpTopInitial;
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
//                            fivehpLeft = event.getX() - 35.0f;
//                            fivehpTop = event.getY();
                        }
                        break;
                    }
                    case 2: {
                        if (event.getX() < 708.9f || event.getX() > 1462.95f || event.getY() < 185.017f || event.getY() > 927.99f) {
                            fourhp1Left = fourhp1LeftInitial;
                            fourhp1Top = fourhp1TopInitial;
                            invalidate();
                            return 0;
                        }
                        else if (checkInitialPlaceOutOfBounds(4, upXVal, upYVal)) {
                            fourhp1Left = fourhp1LeftInitial;
                            fourhp1Top = fourhp1TopInitial;
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
                            fourhp2Left = fourhp2LeftInitial;
                            fourhp2Top = fourhp2TopInitial;
                            invalidate();
                            return 0;
                        }
                        else if (checkInitialPlaceOutOfBounds(4, upXVal, upYVal)) {
                            fourhp2Left = fourhp2LeftInitial;
                            fourhp2Top = fourhp2TopInitial;
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
//                            fourhp2Left = event.getX() - 35.0f;
//                            fourhp2Top = event.getY();
                        }
                        break;
                    }
                    case 4: {
                        if (event.getX() < 708.9f || event.getX() > 1462.95f || event.getY() < 185.017f || event.getY() > 927.99f) {
                            threehp1Left = threehp1LeftInitial;
                            threehp1Top = threehp1TopInitial;
                            invalidate();
                            return 0;
                        }
                        else if (checkInitialPlaceOutOfBounds(3, upXVal, upYVal)) {
                            threehp1Left = threehp1LeftInitial;
                            threehp1Top = threehp1TopInitial;
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
//                            threehp1Left = event.getX() - 35.0f;
//                            threehp1Top = event.getY();
                        }
                        break;
                    }
                    case 5: {
                        if (event.getX() < 708.9f || event.getX() > 1462.95f || event.getY() < 185.017f || event.getY() > 927.99f) {
                            threehp2Left = threehp2LeftInitial;
                            threehp2Top = threehp2TopInitial;
                            invalidate();
                            return 0;
                        }
                        else if (checkInitialPlaceOutOfBounds(3, upXVal, upYVal)) {
                            threehp2Left = threehp2LeftInitial;
                            threehp2Top = threehp2TopInitial;
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
//                            threehp2Left = event.getX() - 35.0f;
//                            threehp2Top = event.getY();
                        }
                        break;
                    }
                    case 6: {
                        if (event.getX() < 708.9f || event.getX() > 1462.95f || event.getY() < 185.017f || event.getY() > 927.99f) {
                            twohpLeft = twohpLeftInitial;
                            twohpTop = twohpTopInitial;
                            invalidate();
                            return 0;
                        }
                        else if (checkInitialPlaceOutOfBounds(2, upXVal, upYVal)) {
                            twohpLeft = twohpLeftInitial;
                            twohpTop = twohpTopInitial;
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
//                            twohpLeft = event.getX() - 35.0f;
//                            twohpTop = event.getY();
                        }
                        break;
                    }
                }
                this.invalidate();
                break;
            }

        }
        return selectedShipId;
    }

    public static boolean checkInitialPlaceOutOfBounds(int size, float x, float y) {
        Coordinates placedCoord = BattleShipGameState.xyToCoordSetupGame(x,y);
        if (placedCoord == null) {
            return true;
        }
        int selectedBoardToEnd = 10 - placedCoord.getY();

        if (selectedBoardToEnd < size) {
            return true;
        }
        return false;
    }

    //getters to transfer coordinates from this phase to midgame Draw phase
    public float getFivehpLeft() {
        return this.fivehpLeft;
    }
    public float getFivehpTop() {
        return this.fivehpTop;
    }
    public float getFourhp1Left() {
        return this.fourhp1Left;
    }
    public float getFourhp1Top() {
        return this.fourhp1Top;
    }
    public float getFourhp2Left() {
        return this.fourhp2Left;
    }
    public float getFourhp2Top() {
        return this.fourhp2Top;
    }
    public float getThreehp1Left() {
        return this.threehp1Left;
    }
    public float getThreehp1Top() {
        return this.threehp1Top;
    }
    public float getThreehp2Left() {
        return this.threehp2Left;
    }
    public float getThreehp2Top() {
        return this.threehp2Top;
    }
    public float getTwohpLeft() {
        return this.twohpLeft;
    }
    public float getTwohpTop() {
        return this.twohpTop;
    }

    public void setPlayerID(int playerID) { this.playerID = playerID;}

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



}



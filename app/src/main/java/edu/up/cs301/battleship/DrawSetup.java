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

public class DrawSetup extends SurfaceView {

    private Paint blackPaint = new Paint();
    private ArrayList<BattleshipObj> battleshipArrayList = new ArrayList<>();
    private Paint orangePaint = new Paint();
    private Context context;

    Bitmap fivehp = BitmapFactory.decodeResource(getResources(), R.drawable.fivehpbs);
    float fivehpLeft = 1814.0f;
    float fivehpTop = 108.0f;
    Bitmap fourhp1 = BitmapFactory.decodeResource(getResources(), R.drawable.fourhpbs);
    float fourhp1Left = 1684.0f;
    float fourhp1Top = 70.0f;
    Bitmap fourhp2 = BitmapFactory.decodeResource(getResources(), R.drawable.fourhpbs);
    float fourhp2Left = 1677.0f;
    float fourhp2Top = 393.0f;
    Bitmap threehp1 = BitmapFactory.decodeResource(getResources(), R.drawable.threehpbs);
    float threehp1Left = 1828.0f;
    float threehp1Top = 500.0f;
    Bitmap threehp2 = BitmapFactory.decodeResource(getResources(), R.drawable.threehpbs);
    float threehp2Left = 1830.0f;
    float threehp2Top = 760.0f;
    Bitmap twohp = BitmapFactory.decodeResource(getResources(), R.drawable.twohpbs);
    float twohpLeft = 1680.0f;
    float twohpTop = 807.0f;

    private int selectedShipId = 0;


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
        fivehp = Bitmap.createBitmap(fivehp, 0, 0, fivehp.getWidth(), fivehp.getHeight(), matrix, true);

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
        canvas.drawBitmap(background, 0.0f, 0.0f, new Paint());
        canvas.drawBitmap(grid, 550.0f, 25.0f, new Paint());

        canvas.drawRect(1650.0f, 50.0f, 1900, 1050, orangePaint);

        canvas.drawBitmap(fivehp, fivehpLeft, fivehpTop, new Paint());
        canvas.drawBitmap(fourhp1, fourhp1Left, fourhp1Top, new Paint());
        canvas.drawBitmap(fourhp2, fourhp2Left, fourhp2Top, new Paint());
        canvas.drawBitmap(threehp1, threehp1Left, threehp1Top, new Paint());
        canvas.drawBitmap(threehp2, threehp2Left, threehp2Top, new Paint());
        canvas.drawBitmap(twohp, twohpLeft, twohpTop, new Paint());

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


        //SHOULD TAKE USER INPUT TO DRAW ONTO BOARD
        /**
         * Draws dynamic elements to screen
         * make arraylist of red/whitemarkers then a for each loop to draw each one toi board
         */
//        canvas.drawBitmap(whiteMarker, 150.0f, 290.0f, new Paint());
//        canvas.drawBitmap(redMarker, 490.0f, 290.0f, new Paint());
//        canvas.drawBitmap(userSelection, 540.0f, 450.0f, new Paint());
    }

    public int onTouchEventNew(MotionEvent event) {
        BattleshipObj selectedBattleShip = new BattleshipObj(0, null);

        int newSize = 0;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
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
                switch(selectedShipId) {
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
                switch(selectedShipId) {
                    case 1: {
                        fivehpLeft = event.getX() - 35.0f;
                        fivehpTop = event.getY();
                        newSize = 5;
                        break;
                    }
                    case 2: {
                        fourhp1Left = event.getX() - 35.0f;
                        fourhp1Top = event.getY();
                        newSize = 4;
                        break;
                    }
                    case 3: {
                        fourhp2Left = event.getX() - 35.0f;
                        fourhp2Top = event.getY();
                        newSize = 4;
                        break;
                    }
                    case 4: {
                        threehp1Left = event.getX() - 35.0f;
                        threehp1Top = event.getY();
                        newSize = 3;
                        break;
                    }
                    case 5: {
                        threehp2Left = event.getX() - 35.0f;
                        threehp2Top = event.getY();
                        newSize = 3;
                        break;
                    }
                    case 6: {
                        twohpLeft = event.getX() - 35.0f;
                        twohpTop = event.getY();
                        newSize = 2;
                        break;
                    }
                }
                selectedShipId = 0;
                invalidate();
                break;
            }
        }
        return newSize;
    }

}



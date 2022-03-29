package edu.up.cs301.battleship;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;

import java.util.ArrayList;

import edu.up.cs301.game.GameFramework.infoMessage.GameState;
import edu.up.cs301.game.R;
import edu.up.cs301.tictactoe.infoMessage.TTTState;

public class DrawMidgame extends SurfaceView{
    private Paint blackPaint = new Paint();
    private Context context;
    public ArrayList<TapValues> tapValues = new ArrayList<TapValues>();

    protected BattleShipGameState state;

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

        //A missed shot will be indicated with a white marker
        Bitmap whiteMarker = BitmapFactory.decodeResource(getResources(), R.drawable.missmarker);
        whiteMarker =  Bitmap.createScaledBitmap(whiteMarker, 300, 250, false);

        //When the user selects their move the COOR will be identified with a target
        Bitmap userSelection = BitmapFactory.decodeResource(getResources(), R.drawable.tagetselector);
        userSelection =  Bitmap.createScaledBitmap(userSelection, 200, 150, false);


        /**
         * Draws everything to surface view, as of now COOR is just giberish and guess work, will work out
         *a formula later
         */
        canvas.drawBitmap(background, 0.0f, 0.0f, new Paint());
        canvas.drawBitmap(grid, 550.0f, 25.0f, new Paint());
        canvas.drawBitmap(playersGrid, 50.0f, 600.0f, new Paint());
        canvas.drawBitmap(remainingShips, 1800.0f, 25.0f, new Paint());
//        canvas.drawBitmap(whiteMarker, 150.0f, 290.0f, new Paint());
//        canvas.drawBitmap(redMarker, 490.0f, 290.0f, new Paint());
//        canvas.drawBitmap(userSelection, 540.0f, 450.0f, new Paint());
        for(TapValues tap : tapValues){
            Log.i("midgame", "onDraw: " + tap.getX() + " " +  tap.getY()) ;
            canvas.drawBitmap(whiteMarker, tap.getX(), tap.getY(), new Paint());

        }
        if (state == null) {
            Log.i("State is Null", "onDraw: NULL");
            return;
        }

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
                        canvas.drawBitmap(redMarker, xVal, yVal, new Paint());
                    }
                    else {
                        canvas.drawBitmap(whiteMarker, xVal, yVal, new Paint());
                    }
                    this.invalidate();
                }
            }
        }

    }

}

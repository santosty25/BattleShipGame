package edu.up.cs301.battleship;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.text.method.Touch;
import android.util.AttributeSet;
import android.view.SurfaceView;

import java.util.ArrayList;

import edu.up.cs301.game.R;

public class DrawSetup extends SurfaceView {

    private Paint blackPaint = new Paint();
    private ArrayList<BattleshipObj> battleshipArrayList= new ArrayList<>();
    private Paint orangePaint = new Paint();
    private Context context;
    public DrawSetup(Context context) {//default constructor,
        super(context);
        initPaints();
        setWillNotDraw(false);//sets visible
    }

    public DrawSetup(Context context, AttributeSet attirs){
        super(context, attirs);
        initPaints();
        setWillNotDraw(false);//sets visible
    }

    public DrawSetup(Context context, AttributeSet attirs, int defStyle){
        super(context, attirs, defStyle);
        initPaints();
        setWillNotDraw(false);//sets visible
    }

    private void initPaints(){
        this.blackPaint.setColor(0xFF000000);
        this.blackPaint.setStyle(Paint.Style.FILL);
        this.orangePaint.setColor(0xFFFF6700);
        this.orangePaint.setStyle(Paint.Style.FILL);


    }


    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        Matrix matrix = new Matrix();
        matrix.postRotate(90);



        /**Creating the battle ships
         * with varying HP
         */
        Bitmap fivehp = BitmapFactory.decodeResource(getResources(), R.drawable.fivehpbs);
        fivehp =  Bitmap.createScaledBitmap(fivehp, 700, 1000, false);
        fivehp = Bitmap.createBitmap(fivehp, 0, 0, fivehp.getWidth(), fivehp.getHeight(), matrix, true);

        Bitmap fourhp1 = BitmapFactory.decodeResource(getResources(), R.drawable.fourhpbs);
        fourhp1 =  Bitmap.createScaledBitmap(fourhp1, 700, 900, false);
        fourhp1 = Bitmap.createBitmap(fourhp1, 0, 0, fourhp1.getWidth(), fourhp1.getHeight(), matrix, true);

        Bitmap fourhp2 = BitmapFactory.decodeResource(getResources(), R.drawable.fourhpbs);
        fourhp2 =  Bitmap.createScaledBitmap(fourhp2, 700, 900, false);
        fourhp2 = Bitmap.createBitmap(fourhp2, 0, 0, fourhp2.getWidth(), fourhp2.getHeight(), matrix, true);

        //CREATES 3 hp BS #1
        Bitmap threehp1 = BitmapFactory.decodeResource(getResources(), R.drawable.threehpbs);
        threehp1 =  Bitmap.createScaledBitmap(threehp1, 700, 800, false);
        threehp1 = Bitmap.createBitmap(threehp1, 0, 0, threehp1.getWidth(), threehp1.getHeight(), matrix, true);

        //CREATES 3 hp BS #2
        Bitmap threehp2 = BitmapFactory.decodeResource(getResources(), R.drawable.threehpbs);
        threehp2 =  Bitmap.createScaledBitmap(threehp2, 700, 800, false);
        threehp2 = Bitmap.createBitmap(threehp2, 0, 0, threehp2.getWidth(), threehp2.getHeight(), matrix, true);

        //CREATES 2 hp BS
        Bitmap twohp = BitmapFactory.decodeResource(getResources(), R.drawable.twohpbs);
        twohp =  Bitmap.createScaledBitmap(twohp, 650, 600, false);
        twohp = Bitmap.createBitmap(twohp, 0, 0, twohp.getWidth(), twohp.getHeight(), matrix, true);



        //Set background
        Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.battleshipbackground);
        background = Bitmap.createScaledBitmap(background, getWidth(), getHeight(), false);

        //Draws the board for the use will use to select and play their move
        Bitmap grid = BitmapFactory.decodeResource(getResources(), R.drawable.updatedgrid);
        grid =  Bitmap.createScaledBitmap(grid, 1000, 1000, false);



        /**
         *Draws static elements onto screen
         */
        canvas.drawBitmap(background, 0.0f, 0.0f, new Paint());
        canvas.drawBitmap(grid, 550.0f, 25.0f, new Paint());

        canvas.drawRect(1650.0f, 50.0f, 1900, 1050, orangePaint);

        canvas.drawBitmap(fivehp, 1350.0f, -100.0f, new Paint());
        canvas.drawBitmap(fourhp1, 1250.0f, -130.0f, new Paint());
        canvas.drawBitmap(fourhp1, 1250.0f, 200.0f, new Paint());
        canvas.drawBitmap(threehp1, 1450.0f, 200.0f, new Paint());
        canvas.drawBitmap(threehp2, 1450.0f, 500.0f, new Paint());
        canvas.drawBitmap(twohp, 1400.0f, 550.0f, new Paint());





        //SHOULD TAKE USER INPUT TO DRAW ONTO BOARD
        /**
         * Draws dynamic elements to screen
         * make arraylist of red/whitemarkers then a for each loop to draw each one toi board
         */
//        canvas.drawBitmap(whiteMarker, 150.0f, 290.0f, new Paint());
//        canvas.drawBitmap(redMarker, 490.0f, 290.0f, new Paint());
//        canvas.drawBitmap(userSelection, 540.0f, 450.0f, new Paint());
    }






}

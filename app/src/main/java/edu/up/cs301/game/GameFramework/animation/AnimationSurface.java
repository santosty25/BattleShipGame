package edu.up.cs301.game.GameFramework.animation;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

import edu.up.cs301.battleship.BattleShipGameState;
import edu.up.cs301.game.R;

/**
 * A SurfaceView which allows which an animation to be drawn on it by a
 * Animator.
 *
 * @author Steve Vegdahl
 * @author Andrew Nuxoll
 * @version July 2013
 *
 *
 */
public class AnimationSurface extends SurfaceView implements OnTouchListener {
    //Tag for logging
    private static final String TAG = "AnimationSurface";
    // instance variables
    private Animator animator; // our animator
    private AnimationThread animationThread = null; // thread to generate ticks
    private Paint backgroundPaint = new Paint(); // painter for painting background
    private int flashCount; // counts down ticks for background-flash
    private Paint flashPaint; // has color for background flash

    protected BattleShipGameState state;
    public int playerID;
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
    Bitmap xSink = BitmapFactory.decodeResource(getResources(), R.drawable.red_cross);

    private boolean rotFiveHp = true;
    private boolean rotFourHp1 = true;
    private boolean rotFourHp2 = true;
    private boolean rotThreeHp1 = true;
    private boolean rotThreeHp2 = true;
    private boolean rotTwoHP = true;

    /**
     * Constructor for the AnimationSurface class. In order to be useful, an
     * object must be supplied that implements the Animator interface. This
     * can either be done by overriding the 'createAnimator' method (which by
     * default give null, or by invoking the setAnimator method.
     *
     * @param context
     *            - a reference to the activity this animation is run under
     */
    public AnimationSurface(Context context) {
        super(context);
        init();
    }// ctor

    /**
     * An alternate constructor for use when a subclass is directly specified
     * in the layout. It is expected that the subclass will have overridden
     * the 'createAnimator' method.
     *
     * @param context
     *            - a reference to the activity this animation is run under
     * @param attrs
     *            - set of attributes passed from system
     */
    public AnimationSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }// ctor

    /**
     * Helper-method for the constructors
     */
    private void init() {

        // Tell the OS that *yes* I will draw stuff
        setWillNotDraw(false);

        // initialize the animator instance variable animator-creation method
        animator = createAnimator();

        //Begin listening for touch events
        this.setOnTouchListener(this);

        if (animator != null) {
            startAnimation();
        }
    }// init

    /**
     * Starts the animation
     */
    private void startAnimation() {

        // create and start a thread to generate "ticks" for the animator
        // with the frequency that it desires
        this.animationThread = new AnimationThread(getHolder());
        animationThread.start();

        // Initialize the background color paint as instructed by the animator
        backgroundPaint.setColor(animator.backgroundColor());
    }

    /**
     * Creates the animator for the object. If this method returns null, then it will
     * be necessary to invoke the 'setAnimator' method before the animation can start.
     * @return the animator
     */
    public Animator createAnimator() {
        return null;
    }

    /**
     * Sets and starts the animator for the AnimationSurface if it does not already
     * have an animator.
     *
     * @param animator the animator to use.
     */
    public void setAnimator(Animator animator) {
        if (this.animator == null) {
            // set the animator
            this.animator = animator;
        }
        if (this.animator != null) {
            // start the animator
            startAnimation();
        }
    }

    /**
     * Causes the background color to flash (change color) for the specified amount of time.
     * @param color
     * 			the color to flash
     * @param millis
     * 			the number of milliseconds to flash
     */
    public void flash(int color, int millis) {
        animationThread.flash(color, millis);
    }

    /**
     * Thread subclass to control the game loop
     *
     * Code adapted from Android:How to Program by Deitel, et.al., first edition
     * copyright (C)2013.
     *
     */
    private class AnimationThread extends Thread {

        // a reference to a SurfaveView's holder. This is used to "lock" the
        // canvas when we want to write to it
        private SurfaceHolder surfaceHolder;

        // controls animation stop/go based upon instructions from the Animator
        private boolean threadIsRunning = true;

        /** ctor inits instance variables */
        public AnimationThread(SurfaceHolder holder) {
            surfaceHolder = holder;
            setName("AnimationThread");
        }

        /**
         * causes this thread to pause for a given interval.
         *
         * @param interval
         *            duration in milliseconds
         */
        private void sleep(int interval) {
            try {
                Thread.sleep(interval); // use sleep to avoid busy wait
            } catch (InterruptedException ie) {
                // don't care if we're interrupted
            }
        }// sleep

        /**
         * Causes the background to be changed ("flash") for the given period
         * of time.
         *
         * @param color
         * 			the color to flash
         * @param millis
         * 			the number of milliseconds for this the flash should occur
         */
        public void flash(int color, int millis) {
            flashCount = millis; // set the flash count
            flashPaint = new Paint(); // create painter ...
            flashPaint.setColor(color); // ... with the appropriate color
        }

        /**
         * This is the main animation loop. It calls the Animator's draw()
         * method at regular intervals to creation the animation.
         */
        @Override
        public void run() {

            Canvas canvas = null;// ref to canvas animator draws upon
            long lastTickEnded = 0; // when the last tick ended

            while (threadIsRunning) {

                // stop if the animator asks for it
                if (animator.doQuit())
                    break;

                // pause while the animator wishes it
                while (animator.doPause()) {
                    sleep(animator.interval());
                }// while

                // Pause to honor the animator's tick frequency specification
                long currTime = System.currentTimeMillis();
                long remainingWait = animator.interval()
                        - (currTime - lastTickEnded);
                if (remainingWait > 0) {
                    sleep((int) remainingWait);
                }

                // Ok! We can draw now.
                try {
                    // lock the surface for drawing
                    canvas = surfaceHolder.lockCanvas(null);

                    //paint the background
                    if (canvas != null) {
                        // draw the background
                        if (flashCount > 0) {
                            // we are flashing: draw the "flash" color
                            canvas.drawRect(0,0,getWidth(),getHeight(), flashPaint);

                            // decrement the flash count by the number of milliseconds in
                            // our interval
                            flashCount -= animator.interval();

                            // if we've finished, "release" the flash-painting object
                            if (flashCount <= 0) {
                                flashPaint = null;
                            }
                        }
                        else {
                            // not flashing: draw the normal background color
                            canvas.drawRect(0, 0, getWidth(), getHeight(), backgroundPaint);
                        }

                        // tell the animator to draw the next frame
                        synchronized (surfaceHolder) {
                            animator.tick(canvas);
                        }// synchronized
                    }
                }// try
                finally {
                    // release the canvas
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }

                // Note when this tick ended
                lastTickEnded = System.currentTimeMillis();

            }// while
        }// run
    }

    /**
     * if I am touched, pass the touch event to the animator
     */
    public boolean onTouch(View v, MotionEvent event) {
        if (animator != null) {
            this.animator.onTouch(event);
        }
        return true;
    };// class AnimationThread

    public void setPlayerID(int num) {
        this.playerID = num;
    }

    public void setState(BattleShipGameState state) {
        this.state = new BattleShipGameState(state);
    }

    //Setters to get positions of battleships from setup phase
//    public void setFivehpLeft(float newValue) {
//        fivehpLeft = newValue;
//    }
//    public void setFivehpTop(float newValue) {
//        fivehpTop = newValue;
//    }
//    public void setFourhp1Left(float newValue) {
//        fourhp1Left = newValue;
//    }
//    public void setFourhp1Top(float newValue) {
//        fourhp1Top = newValue;
//    }
//    public void setFourhp2Left(float newValue) {
//        fourhp2Left = newValue;
//    }
//    public void setFourhp2Top(float newValue) {
//        fourhp2Top = newValue;
//    }
//    public void setThreehp1Left(float newValue) {
//        threehp1Left = newValue;
//    }
//    public void setThreehp1Top(float newValue) {
//        threehp1Top = newValue;
//    }
//    public void setThreehp2Left(float newValue) {
//        threehp2Left = newValue;
//    }
//    public void setThreehp2Top(float newValue) {
//        threehp2Top = newValue;
//    }
//    public void setTwohpLeft(float newValue) {
//        twohpLeft = newValue;
//    }
//    public void setTwohpTop(float newValue) {
//        twohpTop = newValue;
//    }

    public void setRotFiveHp(boolean newVal) {rotFiveHp = newVal;}
    public void setRotFourHp1(boolean newVal) {rotFourHp1 = newVal;}
    public void setRotFourHp2(boolean newVal) {rotFourHp2 = newVal;}
    public void setRotThreeHp1(boolean newVal) {rotThreeHp1 = newVal;}
    public void setRotThreeHp2(boolean newVal) {rotThreeHp2 = newVal;}
    public void setRotTwoHP(boolean newVal) {rotTwoHP = newVal;}

    public void setFlashColor(int color) {
        this.flashColor = color;
    }
}// class AnimationSurface

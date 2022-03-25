package edu.up.cs301.battleship;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class TouchListener implements View.OnTouchListener {

    private DrawMidgame drawing = null;
    private Context context = null;
    public TouchListener(DrawMidgame initDrawing, Context context) {
        this.drawing = initDrawing;
        this.context = context;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int x = (int)motionEvent.getX();
        int y = (int)motionEvent.getY();
        BattleShipGameState convert = new BattleShipGameState();
        Coordinates test = convert.xyToCoordMidGame(x, y);
        x-=150;
        y-=110;
        drawing.addTap(new TapValues(x, y));
        Log.i("midgame", "onDraw: AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH") ;
        //tell the View that it needs to redraw itself
        drawing.invalidate();
        return true;
    }
}

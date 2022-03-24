package edu.up.cs301.battleship;

import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

public class BuildController implements View.OnTouchListener, View.OnDragListener{
    private DrawMidgame drawing = null;
    public BuildController(DrawMidgame initDrawing) {
        this.drawing = initDrawing;
    }


    public boolean onTouch (View v, MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        Log.i("TEST", "onTouch: " + x + " " + y );
        drawing.invalidate();
        return true;
    }

    public boolean onDrag (View v, DragEvent event) {
        return true;
    }

}

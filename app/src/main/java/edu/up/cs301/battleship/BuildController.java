package edu.up.cs301.battleship;

import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

public class BuildController implements View.OnTouchListener, View.OnDragListener{

    public boolean onTouch (View v, MotionEvent event) {
        return true;
    }

    public boolean onDrag (View v, DragEvent event) {
        return true;
    }

}

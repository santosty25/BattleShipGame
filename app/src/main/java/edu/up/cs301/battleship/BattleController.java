package edu.up.cs301.battleship;

import android.view.MotionEvent;
import android.view.View;

public class BattleController implements View.OnTouchListener{

    public boolean onTouch (View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        //BattleShipGameState gameState = new GameState()
        //Coordinates coord = new Coordinates()

        return true;
    }
}

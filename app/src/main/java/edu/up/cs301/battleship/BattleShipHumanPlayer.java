package edu.up.cs301.battleship;

import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import edu.up.cs301.game.GameFramework.GameMainActivity;
import edu.up.cs301.game.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.game.GameFramework.players.GameHumanPlayer;
import edu.up.cs301.game.R;

public class BattleShipHumanPlayer extends GameHumanPlayer {

    private GameMainActivity myActivity = null;
    private boolean switchPhase = false;

    public BattleShipHumanPlayer(String name) {
        super(name);
    }

    @Override
    public View getTopView() {
        return null;
    }

    @Override
    public void receiveInfo(GameInfo info) {

    }

    @Override
    public void setAsGui(GameMainActivity activity) {
        this.myActivity = activity;
        activity.setContentView(R.layout.setup_phase);
        Button nextButton = activity.findViewById(R.id.confirm_button);

        //setup phase surfaceView
        SurfaceView gameView = activity.findViewById(R.id.boardView);



        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.setContentView(R.layout.midgame);
                //midgame phase surface view
                SurfaceView gameView = activity.findViewById(R.id.boardView);

                gameView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        float x = motionEvent.getX();
                        float y = motionEvent.getY();
                        Log.d("Coords Test", "Coords: " + x + ", " + y);
                        return false;
                    }
                });
            }
        });
        gameView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                Log.d("Coords Test", "Coords: " + x + ", " + y);
                return false;
            }
        });

    }
}

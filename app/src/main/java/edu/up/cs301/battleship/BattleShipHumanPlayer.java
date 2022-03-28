package edu.up.cs301.battleship;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import edu.up.cs301.game.GameFramework.Game;
import edu.up.cs301.game.GameFramework.GameMainActivity;
import edu.up.cs301.game.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.game.GameFramework.infoMessage.IllegalMoveInfo;
import edu.up.cs301.game.GameFramework.infoMessage.NotYourTurnInfo;
import edu.up.cs301.game.GameFramework.players.GameHumanPlayer;
import edu.up.cs301.game.GameFramework.utilities.Logger;
import edu.up.cs301.game.R;
import edu.up.cs301.tictactoe.infoMessage.TTTState;

public class BattleShipHumanPlayer extends GameHumanPlayer {

    private GameMainActivity myActivity = null;
    private boolean switchPhase = false;
    private BattleShipHumanPlayer reference = this;
    private BattleShipGameState currGS;


    public BattleShipHumanPlayer(String name) {
        super(name);
    }

    @Override
    public View getTopView() {
        return null;
    }

    @Override
    public void receiveInfo(GameInfo info) {
        if(!(info instanceof BattleShipGameState)){
            Log.i("FLASHING", "");
            flash(0xFFFF0000, 100);
            return;
        }
        this.reference = this;
        currGS = new BattleShipGameState((BattleShipGameState) info);
    }

    @Override
    public void setAsGui(GameMainActivity activity) {
        this.myActivity = activity;
        activity.setContentView(R.layout.setup_phase);
        Button nextButton = activity.findViewById(R.id.confirm_button);

        //setup phase surfaceView object
        SurfaceView gameView = activity.findViewById(R.id.boardView);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.setContentView(R.layout.midgame);
                //midgame phase surface view
                SurfaceView gameView = activity.findViewById(R.id.boardView);
                currGS.setPhase(1);
                Log.i("Actual Phase:", "The phase is, " + currGS.getPhase());

                gameView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        float x = motionEvent.getX();
                        float y = motionEvent.getY();
                        Log.d("In midGame", "Coords: " + x + ", " + y);
                        Coordinates sendFireto = currGS.xyToCoordMidGame(x, y);
                        if(sendFireto != null){
                            Log.i("Touch", "onTouch: sending fire ");
                            game.sendAction(new Fire(reference, sendFireto));
                        }
                        return false;
                    }
                });
            }
        });

        /** On Touch for setupphase*/
        gameView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
                    float x = motionEvent.getX();
                    float y = motionEvent.getY();

                    BattleshipObj selectedBattleShip = new BattleshipObj(0, null);
                    //Conditionals for selected ship
                    if (x >= 1684 && x <= 1741 && y >= 70 && y <= 328) { // top left 4hp
                        Log.i("Ship touched", "Top left selected");
                        selectedBattleShip = new BattleshipObj(4, null);
                    } else if (x >= 1814 && x <= 1888 && y >= 108 && y <= 399) { // top right 5hp
                        Log.i("Ship touched", "Top right selected");
                        selectedBattleShip = new BattleshipObj(5, null);
                    } else if (x >= 1677 && x <= 1747 && y >= 393 && y <= 665) { // middle left 4 hp
                        Log.i("Ship touched", "middle left selected");
                        selectedBattleShip = new BattleshipObj(4, null);
                    } else if (x >= 1828 && x <= 1874 && y >= 460 && y <= 628) { // middle right 3 hp
                        Log.i("Ship touched", "middle right selected");
                        selectedBattleShip = new BattleshipObj(3, null);
                    } else if (x >= 1680 && x <= 1725 && y >= 807 && y <= 937) { // bottom left 2hp
                        Log.i("Ship touched", "bottom left selected");
                        selectedBattleShip = new BattleshipObj(2, null);
                    } else if (x >= 1830 && x <= 1874 && y >= 747 && y <= 940) { // bottom right 3 hp
                        Log.i("Ship touched", "bottom right selected");
                        selectedBattleShip = new BattleshipObj(3, null);
                    } else { //no ship
                        Log.i("Ship touched", "no ship selected");
                    }
                }
                if(motionEvent.getAction() == motionEvent.ACTION_UP) {
                    float xUp = motionEvent.getX();
                    float yUp = motionEvent.getY();
                    Log.i("placed ship", "at" + xUp + ", " + yUp);
                }

//                Coordinates placedTap = currGS.xyToCoordMidGame(x, y);
//
//                if (placedTap != null) {
//                    Log.i("Touch", "onTouch: placing ship ");
//                    Coordinates placedArray[] = null;
//                    int currentY = placedTap.getY();
//                    for (int i = 0; i < selectedBattleShip.getSize(); i++) {
//                        placedArray[i] = new Coordinates(false, true, placedTap.getX(), currentY + 1);
//                        currentY +=1;
//                    }
//                    if (placedArray != null) {
//                        selectedBattleShip.setLocation(placedArray);
//                        game.sendAction(new PlaceShip(reference, selectedBattleShip));
//                    }
//                }
                //currGS.xyToCoordMidGame(x,y);
                return false;
            }
        });
    }
}


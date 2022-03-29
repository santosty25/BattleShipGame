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
import edu.up.cs301.tictactoe.views.TTTSurfaceView;

public class BattleShipHumanPlayer extends GameHumanPlayer {

    private GameMainActivity myActivity = null;
    private boolean switchPhase = false;
    private BattleShipHumanPlayer reference = this;
    private BattleShipGameState currGS;
    boolean shipIsSelected = false;
    BattleshipObj selectedBattleShip = new BattleshipObj(0, null);

    //mid game surface view
    private DrawMidgame midGameView;


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
        currGS = new BattleShipGameState((BattleShipGameState) info, playerNum);
        if(midGameView != null) {
            midGameView.setState(currGS);
        }
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
                midGameView = activity.findViewById(R.id.boardView);
                currGS.setPhase(1);
                Log.i("Actual Phase:", "The phase is, " + currGS.getPhase());

                gameView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        float x = motionEvent.getX();
                        float y = motionEvent.getY();
                        Log.d("In midGame", "Coords: " + x + ", " + y);
                        Log.i("Players Turn", "" + currGS.getPlayersTurn());
                        if (currGS.getPlayersTurn() == playerNum) {
                            Coordinates sendFireto = currGS.xyToCoordMidGame(x, y);
                            if (sendFireto != null) {
                                Log.i("Touch", "onTouch: sending fire ");
                                game.sendAction(new Fire(reference, sendFireto));
                            }
                            midGameView.invalidate();
                        }
                        return false;
                    }
                });
            }
        });

        if (shipIsSelected == false) {
            gameView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                        float x = motionEvent.getX();
                        float y = motionEvent.getY();

                        if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
                            //Conditionals for selected ship
                            if (x >= 1684 && x <= 1741 && y >= 70 && y <= 328) { // top left 4hp
                                Log.i("Ship touched", "Top left selected");
                                selectedBattleShip.setSize(4);
                            } else if (x >= 1814 && x <= 1888 && y >= 108 && y <= 399) { // top right 5hp
                                Log.i("Ship touched", "Top right selected");
                                selectedBattleShip.setSize(5);
                            } else if (x >= 1677 && x <= 1747 && y >= 393 && y <= 665) { // middle left 4 hp
                                Log.i("Ship touched", "middle left selected");
                                selectedBattleShip.setSize(4);
                            } else if (x >= 1828 && x <= 1874 && y >= 460 && y <= 628) { // middle right 3 hp
                                Log.i("Ship touched", "middle right selected");
                                selectedBattleShip.setSize(3);
                            } else if (x >= 1680 && x <= 1725 && y >= 807 && y <= 937) { // bottom left 2hp
                                Log.i("Ship touched", "bottom left selected");
                                selectedBattleShip.setSize(2);
                            } else if (x >= 1830 && x <= 1874 && y >= 747 && y <= 940) { // bottom right 3 hp
                                Log.i("Ship touched", "bottom right selected");
                                selectedBattleShip.setSize(3);
                            } else { //no ship
                                Log.i("Ship touched", "no ship selected");
                            }
                        }
                    if (motionEvent.getAction() == motionEvent.ACTION_UP) {
                        float xUp = motionEvent.getX();
                        float yUp = motionEvent.getY();
                        Coordinates sendShipTo = currGS.xyToCoordSetupGame(xUp, yUp);
                        if (sendShipTo != null) {
                            Log.i("Selected ship is", "selected ship is size " + selectedBattleShip.getSize());
                        }
//                        Log.d("placed ship", "at " + xUp + ", " + yUp);
                        Coordinates[] eachShipCoord = new Coordinates[selectedBattleShip.getSize()];

                        for (int i = 0; i < selectedBattleShip.getSize(); i++) {
                            eachShipCoord[i] = currGS.xyToCoordSetupGame(xUp,yUp);
                            yUp += 74;
                        }

//                        if (eachShipCoord != null) {
//                            selectedBattleShip.setLocation(eachShipCoord);
//                            char letters[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
//                            for (int i = 0; i < selectedBattleShip.getSize(); i++) {
//                                Log.i("Placed Ship", "Placed at" + (eachShipCoord[i].getX() + 1) + ", " + letters[eachShipCoord[i].getY()]);
//                            }
//                        }
                        return true;
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
                    shipIsSelected = true;
                    return true;
                }
            });
        }
        if (shipIsSelected == true) {
            gameView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
                        float x = motionEvent.getX();
                        float y = motionEvent.getY();
                        Log.i("Second touch", "placing ship on " + x + ", " + y);
                    }
                    return true;
                }
            });


            /** On Touch for setupphase*/

        }
    }
}


//    @Override
//    public boolean onTouch(View view, MotionEvent motionEvent) {
//        if(currGS.getPhase() == 0){
//            float x = motionEvent.getX();
//            float y = motionEvent.getY();
//            Log.d("Coords Test", "Coords: " + x + ", " + y);
//            currGS.xyToCoordMidGame(x,y);
//            return false;
//        }
//        else{
//            float x = motionEvent.getX();
//            float y = motionEvent.getY();
//            Log.d("In midGame", "Coords: " + x + ", " + y);
//            Coordinates sendFireto = currGS.xyToCoordMidGame(x, y);
//            if(currGS.getPlayersTurn() == playerNum) {
//                if (sendFireto != null) {
//                    Log.i("Touch", "onTouch: sending fire ");
//                    game.sendAction(new Fire(reference, sendFireto));
//                }
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public void onClick(View view) {
//        this.myActivity .setContentView(R.layout.midgame);
//        //midgame phase surface view
//        SurfaceView gameView = this.myActivity .findViewById(R.id.boardView);
//        currGS.setPhase(1);
//        gameView.setOnTouchListener(this);
//    }
//}

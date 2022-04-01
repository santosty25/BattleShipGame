package edu.up.cs301.battleship;
import android.content.Intent;
import android.icu.number.LocalizedNumberFormatter;
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

/**
 * BattleShipHumanPlayer - This class represents a human player
 * in a game of battleship. this where th GUI is set up and
 * allows the player to drag a ship on to the board to place a ship
 * and tap on the grid to fire at coordinates.
 *
 * @author Austen Furutani
 * @author Tyler Santos
 * @author Keoni Han
 * @author Steven Lee
 */
public class BattleShipHumanPlayer extends GameHumanPlayer {

    private GameMainActivity myActivity = null;
    private boolean switchPhase = false;
    private BattleShipHumanPlayer reference = this;
    private BattleShipGameState currGS;
    boolean shipIsSelected = false;
    private BattleshipObj selectedBattleShip = new BattleshipObj(0, null);

    //mid game surface view
    private DrawMidgame midGameView;
    private DrawSetup setupView;


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
        Log.i("received info", "receiveInfo: NEW INFO ");
        this.reference = this;
        currGS = new BattleShipGameState((BattleShipGameState) info, playerNum);
        if(midGameView != null) {
            midGameView.setState(currGS);
        }
        //test
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
                //Checking if all ships have been placed
                int i, j;
                for(i = 0; i < 2; i++){
                    for(j = 0; j < 6; j++){
                        if(currGS.getPlayersFleet()[i][j].getSize() == 1 ){
                            return;
                        }
                    }
                }
                activity.setContentView(R.layout.midgame);
                //midgame phase surface view
                SurfaceView gameView = activity.findViewById(R.id.boardView);
                midGameView = activity.findViewById(R.id.boardView);
                currGS.setPhase(BattleShipGameState.BATTLE_PHASE);
                Log.i("Actual Phase:", "The phase is, " + currGS.getPhase());


                //Sets the coordinates of the midgame view to the same ones of the setupview then
                // you need to change and adjust the coords in the drawMidGamePhase
                midGameView.setFivehpLeft(setupView.getFivehpLeft());
                midGameView.setFivehpTop(setupView.getFivehpTop());

                midGameView.setFourhp1Left(setupView.getFourhp1Left());
                midGameView.setFourhp1Top(setupView.getFourhp1Top());

                midGameView.setFourhp2Left(setupView.getFourhp2Left());
                midGameView.setFourhp2Top(setupView.getFourhp2Top());

                midGameView.setThreehp1Left(setupView.getThreehp1Left());
                midGameView.setThreehp1Top(setupView.getThreehp1Top());

                midGameView.setThreehp2Left(setupView.getThreehp2Left());
                midGameView.setThreehp2Top(setupView.getThreehp2Top());

                midGameView.setTwohpLeft(setupView.getTwohpLeft());
                midGameView.setTwohpTop(setupView.getTwohpTop());
                midGameView.invalidate();

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
        setupView = activity.findViewById(R.id.boardView);


        /** On Touch for setupphase*/

        if (shipIsSelected == false) {
            gameView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                        float x = motionEvent.getX();
                        float y = motionEvent.getY();

                        int shipId = setupView.onTouchEventNew(motionEvent);
                    Log.i("SHIP ID", "onTouch: " + shipId);
                    int newSize = 0;
                    switch(shipId) {
                        case 1: {
                            newSize = 5;
                            break;
                        }
                        case 2: {
                            newSize = 4;
                            selectedBattleShip.setTwinShip(0);
                            break;
                        }
                        case 3: {
                            newSize = 4;
                            selectedBattleShip.setTwinShip(1);
                            break;
                        }
                        case 4: {
                            newSize = 3;
                            selectedBattleShip.setTwinShip(0);
                            break;
                        }
                        case 5: {
                            newSize = 3;
                            selectedBattleShip.setTwinShip(1);
                            break;
                        }
                        case 6: {
                            newSize = 2;
                            break;
                        }
                    }

                        selectedBattleShip.setSize(newSize);
                        if (newSize < 0 || newSize >= 6) {
                            return false;
                        }
                    if (motionEvent.getAction() == motionEvent.ACTION_UP) {
                        float xUp = motionEvent.getX();
                        float yUp = motionEvent.getY();
                        Coordinates sendShipTo = currGS.xyToCoordSetupGame(xUp, yUp);
                        if (sendShipTo != null) {
                            Log.i("Selected ship is", "selected ship is size " + newSize);
                        }
//                        Log.d("placed ship", "at " + xUp + ", " + yUp);
                        if(currGS.xyToCoordSetupGame(xUp,yUp) == null){
                            return true;
                        }
                        int selectToBoardEnd = 10 - currGS.xyToCoordSetupGame(xUp,yUp).getY();

                        if (selectToBoardEnd < newSize) {
                            int adjustment = (newSize) * 74;
                            yUp -= adjustment;
                        }
                        Coordinates[] eachShipCoord = new Coordinates[selectedBattleShip.getSize()];
                        for (int i = 0; i < selectedBattleShip.getSize(); i++) {
                            if (currGS.getBoard(playerNum).getHasShip()) {
                                Log.i("Invalid Place", "Ship already placed here");
                                return false;
                            }
                            eachShipCoord[i] = currGS.xyToCoordSetupGame(xUp,yUp);
                            Log.i("Coordinates ", "" + eachShipCoord[i].getX() +  " " + eachShipCoord[i].getY());
                            yUp += 74;
                        }
                        selectedBattleShip.setLocation(eachShipCoord);
                        if (selectedBattleShip != null) {
                            Log.i("Place ship action", "Sending action");

                            game.sendAction(new PlaceShip(reference, selectedBattleShip, playerNum));
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



            setupView.invalidate();
            midGameView.invalidate();

            /** On Touch for setupphase*/

        }
    }
}

//BACKUP CODE
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

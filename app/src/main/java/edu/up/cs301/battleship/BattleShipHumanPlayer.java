package edu.up.cs301.battleship;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.icu.number.LocalizedNumberFormatter;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

    private int lastSelectedShip = 0;

    //mid game surface view
    private DrawMidgame midGameView;
    private DrawSetup setupView;
    /**
     * helper-class to finish a "flash.
     *
     */
    private class Unflasher implements Runnable {

        private int duration;

        public Unflasher(int duration) {
            this.duration = duration;
        }
        // method to run at the appropriate time: sets background color
        // back to the original
        public void run() {
            try {
                Thread.sleep(this.duration);
            }
            catch (InterruptedException ie) {
                //do nothing
            }
            midGameView.setFlashColor(Color.BLACK);
            midGameView.invalidate();
        }
    }

    public BattleShipHumanPlayer(String name) {
        super(name);
    }

    @Override
    public View getTopView() {
        return myActivity.findViewById(R.id.setuptopview);
    }

    /**
     * flash - The built in flash relies on a background color change but can't be used because
     * the game has a background image.
     * @param color
     * 			the color to flash
     * @param duration
     */
    @Override
    protected void flash(int color, int duration) {
        //no flashing until the via is ready
        if (midGameView == null) return;

        // save the original background color; set the new background
        // color
        midGameView.setFlashColor(color);
        midGameView.invalidate();

        // set up a timer event to set the background color back to
        // the original.
        Unflasher handler = new Unflasher(duration);
        Thread thread = new Thread(handler);
        thread.start();
    }

    @Override
    public void receiveInfo(GameInfo info) {
        if (!(info instanceof BattleShipGameState)) {
            return;
        } else {
            Log.i("received info", "receiveInfo: NEW INFO ");
            this.reference = this;
            currGS = new BattleShipGameState((BattleShipGameState) info, playerNum);
            int playersTurn = currGS.getPlayersTurn();
            int gamePhase = currGS.getPhase();
            if (gamePhase == BattleShipGameState.BATTLE_PHASE) {
                if (playersTurn != playerNum) {
                    this.flash(Color.RED, 10);
                }
            }
            if (setupView != null){
                setupView.setState(currGS);
            }
            if (midGameView != null) {
                midGameView.setState(currGS);
                midGameView.invalidate();
            }
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
                //Checking if all ships have been placed
                int i, j;
                //for(i = 0; i < 2; i ++){
                    for(j = 0; j < 6; j++) {
                        if (currGS.getPlayersFleet()[playerNum][j].getSize() == 1) {
                            return;
                        }
                    }
                //}

                activity.setContentView(R.layout.midgame);
                //midgame phase surface view
                SurfaceView gameView = activity.findViewById(R.id.boardView);
                midGameView = activity.findViewById(R.id.boardView);
                midGameView.setPlayerID(playerNum);
                midGameView.invalidate();
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


                TextView xCoord = activity.findViewById(R.id.textView);
                TextView yCoord = activity.findViewById(R.id.textView2);

                gameView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        midGameView.invalidate();
                        float xC = motionEvent.getX();
                        float yC = motionEvent.getY();
                        float x = xC;
                        float y = yC;

                        String letter = "";
                        boolean inBounds = true;

                        // X-Coordinates
                        if (xC < 710 || xC > 1460) {
                            xC = 0;
                            letter = "";
                            inBounds = false;
                        }
                        if (xC > 710 && xC < 785) {
                            xC = 1;
                        } else if (xC > 785 && xC < 860) {
                            xC = 2;
                        } else if (xC > 860 && xC < 935) {
                            xC = 3;
                        } else if (xC > 935 && xC < 1010) {
                            xC = 4;
                        } else if (xC > 1010 && xC < 1085) {
                            xC = 5;
                        } else if (xC > 1085 && xC < 1160) {
                            xC = 6;
                        } else if (xC > 1160 && xC < 1235) {
                            xC = 7;
                        } else if (xC > 1235 && xC < 1310) {
                            xC = 8;
                        } else if (xC > 1310 && xC < 1385) {
                            xC = 9;
                        } else if (xC > 1385 && xC < 1460) {
                            xC = 10;
                        }

                        // Y-Coordinates
                        if (yC < 180 || yC > 930) {
                            letter = "";
                            xC = 0;
                        }
                        if (inBounds == true) {
                            if (yC > 180 && yC < 255) {
                                letter = "A";
                            } else if (yC > 255 && yC < 330) {
                                letter = "B";
                            } else if (yC > 330 && yC < 405) {
                                letter = "C";
                            } else if (yC > 405 && yC < 480) {
                                letter = "D";
                            } else if (yC > 480 && yC < 555) {
                                letter = "E";
                            } else if (yC > 555 && yC < 630) {
                                letter = "F";
                            } else if (yC > 630 && yC < 705) {
                                letter = "G";
                            } else if (yC > 705 && yC < 780) {
                                letter = "H";
                            } else if (yC > 780 && yC < 855) {
                                letter = "I";
                            } else if (yC > 855 && yC < 930) {
                                letter = "J";
                            }
                        }
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
        setupView.setPlayerID(playerNum);


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
                        Coordinates sendShipTo = null;
                        if (currGS == null) {
                            return false;
                        }
                        sendShipTo = currGS.xyToCoordSetupGame(xUp, yUp);
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

                        return true;
                    }
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

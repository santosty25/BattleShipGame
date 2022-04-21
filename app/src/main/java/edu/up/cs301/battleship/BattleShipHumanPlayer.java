package edu.up.cs301.battleship;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.up.cs301.game.GameFramework.Game;
import edu.up.cs301.game.GameFramework.GameMainActivity;
import edu.up.cs301.game.GameFramework.animation.AnimationSurface;
import edu.up.cs301.game.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.game.GameFramework.players.GameHumanPlayer;
import edu.up.cs301.game.R;

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
 * @version 4/14/22
 */
public class BattleShipHumanPlayer extends GameHumanPlayer {

    private GameMainActivity myActivity = null;
    private BattleShipHumanPlayer reference = this;
    protected BattleShipGameState currGS;
    boolean shipIsSelected = false;
    private BattleshipObj selectedBattleShip = new BattleshipObj(0, null);

    private int lastSelectedShip = 0;

    private DrawMidgame midGame;

    //mid game surface view
    private AnimationSurface midGameView;
    private DrawSetup setupView;

    public int getPlayerNum() {
        return this.playerNum;
    }

    public Game getGame() {
        return this.game;
    }

    /**
     * helper-class to finish a flash
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
            midGame.setFlashColor(Color.BLACK);
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
     *          how long the flash will last
     */
    @Override
    protected void flash(int color, int duration) {
        //no flashing until the via is ready
        if (midGameView == null) return;

        // save the original background color; set the new background
        // color
        midGameView.setFlashColor(color);
        midGame.setFlashColor(color);
        midGameView.invalidate();

        // set up a timer event to set the background color back to
        // the original.
        Unflasher handler = new Unflasher(duration);
        Thread thread = new Thread(handler);
        thread.start();
    }

    /**
     * receiveInfo Recveives game info, updating the gamestate
     * and game views
     * @param info - info given to the player
     */
    @Override
    public void receiveInfo(GameInfo info) {
        if (!(info instanceof BattleShipGameState)) {
            return;
        } else {
            this.reference = this;
            this.currGS = new BattleShipGameState((BattleShipGameState) info);
            int playersTurn = currGS.getPlayersTurn();
            int gamePhase = currGS.getPhase();
            if (gamePhase == BattleShipGameState.BATTLE_PHASE) {
                if (playersTurn != playerNum) {
                    this.flash(Color.RED, 10);
                }
            }
            if (setupView != null){
                setupView.setState(currGS);
                setupView.setPlayerID(playerNum);
                setupView.checkOverlapping();
            }
            if (midGameView != null) {

                midGameView.invalidate();
            }
            if(midGame != null) {
                this.midGame.setState(currGS);
            }
        }
    }

    /**
     * Initiallizes the GUI for the game,
     * and touch listeners
     * @param activity
     */
    @Override
    public void setAsGui(GameMainActivity activity) {
        //TODO set animator
        this.myActivity = activity;
        activity.setContentView(R.layout.setup_phase);
        Button nextButton = activity.findViewById(R.id.confirm_button);
        DrawSetup gameView = activity.findViewById(R.id.boardView);
        //setup phase surfaceView object
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Checking if all ships have been placed
                for (int j = 0; j < 6; j++) {
                    if (currGS.getPlayersFleet()[playerNum][j].getSize() == 1) {
                        return;
                    }
                    if (setupView.checkIfShipsAreReset()) {
                        return;
                    }
                }

                activity.setContentView(R.layout.midgame);
                AnimationSurface mySurface = (AnimationSurface) activity.findViewById(R.id.animation_surface);
                midGame = new DrawMidgame(myActivity, reference);
                mySurface.setAnimator(midGame);
                //midgame phase surface view
                SurfaceView gameView = activity.findViewById(R.id.boardView);
                midGameView = activity.findViewById(R.id.animation_surface);

                midGame.setPlayerID(playerNum);
                game.sendAction(new SwitchPhase(reference, playerNum, true));
                midGameView.invalidate();
                Log.i("Actual Phase:", "The phase is, " + currGS.getPhase());
                //Sets the coordinates of the midgame view to the same ones of the setupview then

                // Finds out which ships should be rotated which way from the setupView object
                // and sends it to the midGameView Object
                midGameView.invalidate();
                midGame.setRotFiveHp(setupView.getRotFiveHp());
                midGame.setRotFourHp1(setupView.getRotFourHp1());
                midGame.setRotFourHp2(setupView.getRotFourHp2());
                midGame.setRotThreeHp1(setupView.getRotThreeHp1());
                midGame.setRotThreeHp2(setupView.getRotThreeHp2());
                midGame.setRotTwoHP(setupView.getRotTwoHP());
            }
        });//onclick

        setupView = activity.findViewById(R.id.boardView);
        setupView.setPlayerID(playerNum);

        /** On Touch for setupphase*/
        if (shipIsSelected == false) {
            gameView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if(currGS.getPlayersTurn() != playerNum){
                        return true;
                    }

                    int shipId = setupView.onTouchEventNew(motionEvent);
                    //Uses the ship id to determine which ship has been tapped
                    int newSize = 0;
                    boolean isShipRotated = true;
                    switch(shipId) {
                        case 1: {
                            newSize = 5;
                            isShipRotated = setupView.getRotFiveHp();
                            break;
                        }
                        case 2: {
                            newSize = 4;
                            isShipRotated = setupView.getRotFourHp1();
                            selectedBattleShip.setTwinShip(0);
                            break;
                        }
                        case 3: {
                            newSize = 4;
                            isShipRotated = setupView.getRotFourHp2();
                            selectedBattleShip.setTwinShip(1);
                            break;
                        }
                        case 4: {
                            newSize = 3;
                            isShipRotated = setupView.getRotThreeHp1();
                            selectedBattleShip.setTwinShip(0);
                            break;
                        }
                        case 5: {
                            newSize = 3;
                            isShipRotated = setupView.getRotThreeHp2();
                            selectedBattleShip.setTwinShip(1);
                            break;
                        }
                        case 6: {
                            newSize = 2;
                            isShipRotated = setupView.getRotTwoHP();
                            break;
                        }
                    }

                    Log.i("Test Coordinates", "FIND ME HERE" + motionEvent.getX() + ", " + motionEvent.getY());

                    selectedBattleShip.setSize(newSize);
                    if (newSize < 0 || newSize >= 6) {
                        return false;
                    }
                    if (motionEvent.getAction() == motionEvent.ACTION_UP) {
                        float xUp = motionEvent.getX();
                        float yUp = motionEvent.getY();
                        if (currGS == null) {//ensure the gamestate has been initialized
                            return false;
                        }
                        if(currGS.xyToCoordSetupGame(xUp,yUp) == null){ //checks if coordinate value is valiid
                            return true;
                        }
                        Coordinates[] eachShipCoord = new Coordinates[selectedBattleShip.getSize()];


                        // Checks to see which orientation the ship is in and creates a coordinate array from the tap origin
                        if (isShipRotated) {
                            eachShipCoord = new Coordinates[selectedBattleShip.getSize()];
                            for (int i = 0; i < selectedBattleShip.getSize(); i++) {
                                if (currGS.getBoard(playerNum).getHasShip()) {
                                    Log.i("Invalid Place", "Ship already placed here");
                                    return false;
                                }
                                eachShipCoord[i] = currGS.xyToCoordSetupGame(xUp, yUp);
                                if(i > 0){
                                    if(eachShipCoord[i-1].getY() ==  eachShipCoord[i].getY()){
                                        eachShipCoord[i].setY(eachShipCoord[i-1].getY()+1);
                                    }
                                }
                                Log.i("Coordinates ", "" + eachShipCoord[i].getX() + " " + eachShipCoord[i].getY());
                                yUp += 74;
                            }
                        }
                        else {
                            eachShipCoord = new Coordinates[selectedBattleShip.getSize()];
                            for (int j = 0; j < selectedBattleShip.getSize(); j++) {
                                if (currGS.getBoard(playerNum).getHasShip()) {
                                    Log.i("Invalid Place", "Ship already placed here");
                                    return false;
                                }
                                eachShipCoord[j] = currGS.xyToCoordSetupGame(xUp, yUp);
                                if(j > 0){
                                    if(eachShipCoord[j-1].getX() ==  eachShipCoord[j].getX()){
                                        eachShipCoord[j].setX(eachShipCoord[j-1].getX()+1);
                                    }
                                }
                                Log.i("Coordinates ", "" + eachShipCoord[j].getX() + " " + eachShipCoord[j].getY());
                                xUp += 74;
                            }
                        }
                        selectedBattleShip.setLocation(eachShipCoord);
                        if (selectedBattleShip != null) {//sends a placeship action
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
        }
    }

    public BattleShipGameState getGS(){
        return this.currGS;
    }
}

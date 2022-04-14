package edu.up.cs301.battleship;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import edu.up.cs301.game.GameFramework.GameMainActivity;
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
 */
public class BattleShipHumanPlayer extends GameHumanPlayer {

    private GameMainActivity myActivity = null;
    private BattleShipHumanPlayer reference = this;
    protected BattleShipGameState currGS;
    boolean shipIsSelected = false;
    private BattleshipObj selectedBattleShip = new BattleshipObj(0, null);
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

    /**
     * Recveives game info, updating the gamestate
     * and game views
     * @param info
     */
    @Override
    public void receiveInfo(GameInfo info) {
        if (!(info instanceof BattleShipGameState)) {
            return;
        } else {
            Log.i("received info", "receiveInfo: NEW INFO ");
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
                midGameView.setState(currGS);
                midGameView.invalidate();
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
                changePhase(1);
                Log.i("Actual Phase:", "The phase is, " + currGS.getPhase());
                //Sets the coordinates of the midgame view to the same ones of the setupview then
                // you need to change and adjust the coords in the drawMidGamePhase
                midGameView.setRotFiveHp(setupView.getRotFiveHp());
                midGameView.setRotFourHp1(setupView.getRotFourHp1());
                midGameView.setRotFourHp2(setupView.getRotFourHp2());
                midGameView.setRotThreeHp1(setupView.getRotThreeHp1());
                midGameView.setRotThreeHp2(setupView.getRotThreeHp2());
                midGameView.setRotTwoHP(setupView.getRotTwoHP());
                midGameView.invalidate();


                TextView xCoord = activity.findViewById(R.id.textView);
                TextView yCoord = activity.findViewById(R.id.textView2);


                /** touch listener for Midgame*/
                gameView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        midGameView.invalidate();
                        BattleShipGameState localGS = getGS();
                        float xC = motionEvent.getX();
                        float yC = motionEvent.getY();
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

                        if (!(xC == 0)) {
                            xCoord.setText("X: " + (int) xC);
                        } else {
                            xCoord.setText("X: ");
                        }
                        yCoord.setText("Y: " + letter);

                        float x = motionEvent.getX();
                        float y = motionEvent.getY();
                        Log.d("In midGame", "Coords: " + x + ", " + y);
                        Log.i("Players Turn", "" + currGS.getPlayersTurn());
                        if (currGS.getPlayersTurn() == playerNum) {
                            Coordinates sendFireto = currGS.xyToCoordMidGame(x, y);
                            if (sendFireto != null) {
                                Log.i("Touch", "onTouch: sending fire ");
                                game.sendAction(new Fire(reference, sendFireto, playerNum));
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

                        int shipId = setupView.onTouchEventNew(motionEvent);
                    Log.i("SHIP ID", "onTouch: " + shipId);
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
                        Coordinates sendShipTo = currGS.xyToCoordSetupGame(xUp, yUp);
                        if (sendShipTo != null) {
                            Log.i("Selected ship is", "selected ship is size " + newSize);
                        }
                        if (currGS.xyToCoordSetupGame(xUp, yUp) == null) {
                            return true;
                        }
//                        int selectToBoardEnd = 10 - currGS.xyToCoordSetupGame(xUp,yUp).getY();
//
//                        if (selectToBoardEnd < newSize) {
//                            int adjustment = (newSize) * 74;
//                            yUp -= adjustment;
//                        }

                        Coordinates[] eachShipCoord = new Coordinates[selectedBattleShip.getSize()];

                        if (isShipRotated) {
                           eachShipCoord = new Coordinates[selectedBattleShip.getSize()];
                            for (int i = 0; i < selectedBattleShip.getSize(); i++) {
                                if (currGS.getBoard(playerNum).getHasShip()) {
                                    Log.i("Invalid Place", "Ship already placed here");
                                    return false;
                                }
                                eachShipCoord[i] = currGS.xyToCoordSetupGame(xUp, yUp);
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
                                Log.i("Coordinates ", "" + eachShipCoord[j].getX() + " " + eachShipCoord[j].getY());
                                xUp += 74;
                            }
                        }

//                        if(lastSelectedShip == shipId && shipId != 0){
//                            BattleshipObj temp = new BattleshipObj(currGS.getPlayersFleet()[playerNum][shipId - 1]);
//                            for(i = 0; i < eachShipCoord.length; i ++){
//                                if (!(eachShipCoord[i].getX() == temp.getLocation()[i].getX() && eachShipCoord[i].getY() == temp.getLocation()[i].getY())){
//                                    sameLoc = false;
//                                }
//                            }
//                        }
//                        else{
//                            lastSelectedShip = shipId;
//                        }
//                        if(sameLoc){
//                            setupView.swapRotFiveHp();
//                        }
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



    public void placeShip(BattleshipObj newShip){
        int i, j;
        BattleshipObj[][] currentFleet = new BattleshipObj[2][6];
        for (i = 0;  i < 2; i++) {
            for (j =0; j < 6; j++){
                if (currGS.getPlayersFleet()[i][j] != null) {
                    currentFleet[i][j] = new BattleshipObj(currGS.getPlayersFleet()[i][j]);
                }
            }
        }
        if(newShip.getSize() == 5) { //Ship of size 5 is placed at index 0
            Log.i("placing ship size: 0 ", "" + newShip.getSize());
            currentFleet[0][0] = new BattleshipObj(newShip);
        }
        else if(newShip.getSize() == 4){
            if(newShip.getTwinShip() == 0){
                //Because there are two ships of the same length we need to identify which is which
                currentFleet[0][1] = new BattleshipObj(newShip);
            }
            else{
                currentFleet[0][2] = new BattleshipObj(newShip);
            }
        }
        else if(newShip.getSize() == 3){
            if(newShip.getTwinShip() == 0){
                currentFleet[0][3] = new BattleshipObj(newShip);
            }
            else{
                currentFleet[0][4] = new BattleshipObj(newShip);
            }
        }
        else if(newShip.getSize() == 2) {
            currentFleet[0][5] = new BattleshipObj(newShip);
        }
        currGS.setPlayersFleet(currentFleet, playerNum);
    }


    public void changePhase(int phase){
        this.currGS.setPhase(phase);
    }
    public BattleShipGameState getGS(){
        return this.currGS;
    }
}

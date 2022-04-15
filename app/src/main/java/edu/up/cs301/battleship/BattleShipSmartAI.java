package edu.up.cs301.battleship;

import android.util.Log;

import com.google.android.material.bottomappbar.BottomAppBarTopEdgeTreatment;

import java.util.ArrayList;
import java.util.Random;

import edu.up.cs301.game.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.game.GameFramework.players.GameComputerPlayer;

/**
 * BattleShipSmartAI - WORK IN PROGRESS BUILD
 *
 * This class represents a smarter AI that will fire randomly and place
 * ships randomly. This AI will detect if a hit is registered on the board and fire at
 * coords in cardinal directions until the ship is sunk.
 *
 * @author Austen Furutani
 * @author Tyler Santos
 * @author Keoni Han
 * @author Steven Lee
 * @version Spring 2022 - 3/31/22
 */
public class BattleShipSmartAI extends GameComputerPlayer {
    public static final int RIGHT = 0;
    public static final int LEFT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;

    private BattleshipObj battleship;
    private BattleShipGameState gameState;
    private int shipNum;
    private int placeShips;
    private int dir;
    private int start;
    private ArrayList<Coordinates> successHits = new ArrayList<Coordinates>();
    private Coordinates previousHit;
    private Coordinates succHits;
    private int numTurnsSinceFire;
    private int assumRemainShips;
    private boolean startAlgor;
    private int enemyNum;


    private Coordinates nextCoord;

    public BattleShipSmartAI(String name) {
        super(name);
        this.start = 0;
        this.assumRemainShips = 6;
        this.placeShips = 0;
        this.dir = 0;
        this.startAlgor = false;
        this.numTurnsSinceFire = 0;
    }


    @Override
    protected void receiveInfo(GameInfo info) {

        if (!(info instanceof BattleShipGameState)) {
            return;
        }
        this.gameState = new BattleShipGameState((BattleShipGameState) info);
        if (playerNum == 0) {
            this.enemyNum = 1;
        } else {
            this.enemyNum = 0;
        }

        GameBoard board = new GameBoard(this.gameState .getBoard(enemyNum));

        //CALLING PLACE SHIPS
        shipNum = 0;
        for(int i = 0; i < this.gameState .getPlayersFleet()[playerNum].length; i++){
            if(this.gameState .getPlayersFleet()[playerNum][i].getSize() != 1){
                shipNum++;
            }
        }
        if(shipNum == 6){
            game.sendAction(new SwitchPhase(this, playerNum, true));
        }
        if (this.gameState .getPhase() == BattleShipGameState.SETUP_PHASE){
            placeShips();
        }
        else if(this.gameState.getPhase() == BattleShipGameState.BATTLE_PHASE && gameState.getPlayersTurn() == playerNum){
            this.fire();
        }
    }

    public void fire() {
        if (this.gameState.getPhase() != BattleShipGameState.BATTLE_PHASE) {
            return;
        }
        Log.i("COMPUTER PLAYERS TURN", "");
        Random r = new Random();
        int row = r.nextInt(10);
        int col = r.nextInt(10);
        Coordinates fireLoc = new Coordinates(false, false, row, col);
//        if (this.previousHit != null) {
//            Log.i("LAST WAS A HIT", "@" + previousHit.getX() + previousHit.getY());
//            if(checkSuccHit(previousHit)) {
//                this.succHits = new Coordinates(previousHit);
//                Log.i("last succ hit", "fire: " + succHits.getX() + " " +  succHits.getY());
//            }
//            if(this.succHits != null) {
//                Log.i("last succ hit", "fire: " + succHits.getX() + " " +  succHits.getY());
//                if (checkRight() == true) {
//                    Coordinates rightCoord = new Coordinates(succHits);
//                    int val = rightCoord.getX() + 1;
//                    rightCoord.setX(val);
//                    fireLoc = new Coordinates(rightCoord);
//                } else if (checkLeft()== true) {
//                    Coordinates leftCoord = new Coordinates(succHits);
//                    int val = leftCoord.getX() - 1;
//                    leftCoord.setX(val);
//                    fireLoc = new Coordinates(leftCoord);
//                }
//                else if(checkUp()== true){
//                    Coordinates upCoord = new Coordinates(succHits);
//                    int val = upCoord.getY() + 1;
//                    upCoord.setX(upCoord.getY() + val);
//                    fireLoc = new Coordinates(upCoord);
//                }
//                else if(checkDown()== true){
//                    Coordinates downCoord = new Coordinates(succHits);
//                    int val = downCoord.getY() - 1;
//                    downCoord.setX(val);
//                    fireLoc = new Coordinates(downCoord);
//                }
//            }
//        }
//        if (checkIfMiss(fireLoc)){
//            fire();
//        }
        Log.i("COMPUTER FIRING AT ", "fire:" +  fireLoc.getX() + " " + fireLoc.getY());
        this.previousHit = new Coordinates (fireLoc);
        //sleep(1);
        game.sendAction(new Fire(this, fireLoc, playerNum));
    }

    /**
     * reset - Resets the algorithm so that the AI fires randomly.
     */
    public void reset() {
        this.assumRemainShips--;
        this.successHits.clear();
        this.startAlgor = false;
    }

    /**
     * checkIfCoordHit - Checks if the previous fire was a successful hit.
     *
     * @param coord - a given coordinate
     * @return true if the coordinate was a hit
     * false if the coordinate was a miss
     */
    public boolean checkIfCoordHit(Coordinates coord) {
        GameBoard board = new GameBoard(this.gameState .getBoard(enemyNum));
        if (coord != null) {
            return (board.getCoordHit(coord.getX(), coord.getY()) && board.getHasShip(coord.getX(), coord.getY()));
        }
        return false;
    }
    public boolean checkSuccHit(Coordinates coord){
        GameBoard board = new GameBoard(this.gameState .getBoard(enemyNum));
        if(board.getHasShip(coord.getX(), coord.getY())){
            return true;
        }
        return false;
    }
    public boolean checkIfMiss(Coordinates coord) {
        GameBoard board = new GameBoard(this.gameState.getBoard(enemyNum));
        if (coord != null) {
            Log.i("Already hit", "checkIfMiss: ");
            return (board.getCoordHit(coord.getX(), coord.getY()));
        }
        return false;
    }



    public boolean checkRight(){
        int nextX = succHits.getX();
        Coordinates rightCoord = new Coordinates(succHits);
        if(nextX >= 9){
            return false;
        }
        rightCoord.setX(nextX + 1);
        if(checkIfMiss(rightCoord)){
            Log.i("SHIP ALREADY HIT", "checkRight: SHIP ALREADY HIT");
            return false;
        }
        return true;
    }
    public boolean checkLeft(){
        int nextX = succHits.getX();
        Coordinates leftCoord = new Coordinates(succHits);
        if(nextX <= 1){
            return false;
        }
        leftCoord.setX(nextX - 1);
        if(checkIfMiss(leftCoord)){
            Log.i("SHIP ALREADY HIT", "checkLeft: SHIP ALREADY HIT");
            return false;
        }
        return true;
    }
    public boolean checkUp(){
        int nextY = succHits.getY() + 1;
        Coordinates upCoord = new Coordinates(succHits);
        if(nextY > 9){
            return false;
        }
        upCoord.setX(nextY);
        if(checkIfMiss(upCoord)){
            Log.i("SHIP ALREADY HIT", "checkUp: SHIP ALREADY HIT");
            return false;
        }
        return true;
    }
    public boolean checkDown(){
        int nextY = succHits.getY();
        Coordinates downCoord = new Coordinates(succHits);
        if(nextY <= 1){
            return false;
        }
        downCoord.setX(nextY - 1);
        if(checkIfMiss(downCoord)){
            Log.i("SHIP ALREADY HIT", "CheckDown: SHIP ALREADY HIT");
            return false;
        }
        return true;
    }





    /**
     * setShips - This method creates a battleship of a given size and
     * sends the action to place this battleship.
     *
     * @param size - the size of the battleship
     */
    public void setShips(int size, BattleShipGameState gs, int twin) {
        Coordinates[] location;
        Random randGen = new Random();
        int randRow = randGen.nextInt(10) + 1;
        int randCol = randGen.nextInt(10) + 1;
        int randOrientation = randGen.nextInt(2);

        Coordinates coord1;
        Coordinates coord2;
        Coordinates coord3;
        Coordinates coord4;
        Coordinates coord5;
        //create random x and y coords for ships that doesn't go out of bounds
        while (randRow < 2 || randRow > 7) {
            randRow = randGen.nextInt(10);
        }
        while (randCol < 2 || randCol > 7) {
            randCol = randGen.nextInt(10);
        }
        if (randOrientation == 0) {
            coord1 = new Coordinates(false, true, randRow, randCol + 2);
            coord2 = new Coordinates(false, true, randRow, randCol + 1);
            coord3 = new Coordinates(false, true, randRow, randCol);
            coord4 = new Coordinates(false, true, randRow, randCol - 1);
            coord5 = new Coordinates(false, true, randRow, randCol - 2);
        } else {
            coord1 = new Coordinates(false, true, randRow + 2, randCol);
            coord2 = new Coordinates(false, true, randRow + 1, randCol);
            coord3 = new Coordinates(false, true, randRow, randCol);
            coord4 = new Coordinates(false, true, randRow - 1, randCol);
            coord5 = new Coordinates(false, true, randRow - 2, randCol);
        }
        //this.setOrientation(randOrientation, randRow, randCol, size);

        if (size == 2) {
            location = new Coordinates[]{coord2, coord3};
        } else if (size == 3) {
            location = new Coordinates[]{coord2, coord3, coord4};
        }
        else if (size == 4) {
            location = new Coordinates[]{coord1, coord2, coord3, coord4};
        }
        else {
            location = new Coordinates[]{coord1, coord2, coord3, coord4, coord5};
        }
        boolean overlap = this.checkShip(gs, location);
        while (overlap == true) {

            randRow = randGen.nextInt(10) + 1;
            randCol = randGen.nextInt(10) + 1;
            randOrientation = randGen.nextInt(2);

            //create random x and y coords for ships that doesn't go out of bounds
            while (randRow < 2 || randRow > 7) {
                randRow = randGen.nextInt(10);
            }
            while (randCol < 2 || randCol > 7) {
                randCol = randGen.nextInt(10);
            }
            if (randOrientation == 0) {
                coord1 = new Coordinates(false, true, randRow, randCol + 2);
                coord2 = new Coordinates(false, true, randRow, randCol + 1);
                coord3 = new Coordinates(false, true, randRow, randCol);
                coord4 = new Coordinates(false, true, randRow, randCol - 1);
                coord5 = new Coordinates(false, true, randRow, randCol - 2);
            } else {
                coord1 = new Coordinates(false, true, randRow + 2, randCol);
                coord2 = new Coordinates(false, true, randRow + 1, randCol);
                coord3 = new Coordinates(false, true, randRow, randCol);
                coord4 = new Coordinates(false, true, randRow - 1, randCol);
                coord5 = new Coordinates(false, true, randRow - 2, randCol);
            }
            if (size == 2) {
                location = new Coordinates[]{coord2, coord3};
            } else if (size == 3) {
                location = new Coordinates[]{coord2, coord3, coord4};
            }
            else if (size == 4) {
                location = new Coordinates[]{coord1, coord2, coord3, coord4};
            }
            else {
                location = new Coordinates[]{coord1, coord2, coord3, coord4, coord5};
            }
            overlap = this.checkShip(gs, location);
        }
        this.battleship = new BattleshipObj(size, location);
        this.battleship.setTwinShip(twin);
        game.sendAction(new PlaceShip(this, this.battleship, playerNum));
    }

    /**
     * checkShip - Checks if a given ship has the same location as the battleship
     * that has already been placed on the board.
     * @param gs - the gamestate
     * @param coords - the given coordinates
     * @return true if the ships are overlapping and false if they aren't
     */
    public boolean checkShip(BattleShipGameState gs, Coordinates[] coords) {
        int shipError = 0;

        for (int j = 0; j < 6; j++) {
            for (int k = 0; k < coords.length; k++) {
                if (gs.getPlayersFleet()[playerNum][j] == null) {
                    continue;
                } else {
                    for (int l = 0; l < gs.getPlayersFleet()[playerNum][j].getLocation().length; l++)
                        if (gs.getPlayersFleet()[playerNum][j].getLocation()[l].getX() == coords[k].getX() &&
                                gs.getPlayersFleet()[playerNum][j].getLocation()[l].getY() == coords[k].getY()) {
                            return true;
                        }
                }
            }

        }
        return false;
    }
    /**
     * setOrientation - This method sets the orientation of the ships and creates an
     * array of Coordinates
     * @param orientation - orientation of the ship
     * @param row - x coord
     * @param col - y coord
     * @return the array of Coorinates where the ship is to be placed
     */
    public void setOrientation(int orientation, int row, int col, int size) {

        final int HORIZONTAL = 0;
        //check for orientation and create
//        if(orientation == HORIZONTAL) {
//            this.coord1 = new Coordinates (false, true, row, col + 2);
//            this.coord2 = new Coordinates(false, true, row, col + 1);
//            this.coord3 = new Coordinates(false, true, row, col);
//            this.coord4 = new Coordinates(false, true, row, col - 1);
//            this.coord5 = new Coordinates(false, true, row, col - 2);
//        }
//        else {
//            this.coord1 = new Coordinates (false, true, row + 2, col);
//            this.coord2 = new Coordinates(false, true, row + 1, col);
//            this.coord3 = new Coordinates(false, true, row, col);
//            this.coord4 = new Coordinates(false, true, row - 1, col);
//            this.coord5 = new Coordinates(false, true, row - 2, col);
//        }

        //Coordinates[] coords = new Coordinates[]{coord1, coord2, coord3, coord4, coord5};
        //this.placeShips(coords, size);
    }

    public void placeShips() {
        int i = 0;
        Log.i("PLACING SHIP", "ship Num" + shipNum);
        Coordinates[] loc;
        Coordinates[] temp = new Coordinates[1];
        temp[0] = new Coordinates(false, false, -1, -1);
        BattleshipObj selectedBattleShip = new  BattleshipObj(1, temp);
        if(this.gameState .getPlayersTurn() != playerNum){
            return;
        }
        if(shipNum == 0) {
            setShips(5, this.gameState , 0);
        }
        else if(shipNum == 1) {
            setShips(4, this.gameState , 0);
        }
        else if(shipNum == 2) {
            setShips(4, this.gameState , 1);
        }
        else if(shipNum == 3) {
            setShips(3, this.gameState , 0);
        }
        else if(shipNum == 4) {
            setShips(3, this.gameState , 1);
        }
        else{
            setShips(2, this.gameState , 0);
        }
        game.sendAction(new PlaceShip(this, selectedBattleShip, playerNum));
    }
}
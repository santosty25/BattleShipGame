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
//push
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

    /**
     * The fire though process for the smart AI
     * TODO need to rework checks sometimes pauses for unknown reasons
     * Randomly fires until it finds a direction of a ship
     *
     */
    public void fire() {
        if (this.gameState.getPhase() != BattleShipGameState.BATTLE_PHASE) {
            return;
        }
        Random r = new Random();
        int row = r.nextInt(10);
        int col = r.nextInt(10);
        Coordinates fireLoc = new Coordinates(false, false, row, col); // a random fire coordinate
        if (this.previousHit != null) { //check if a previous hit exits
            if(checkSuccHit(previousHit)) { //Checks if last hit hit a battle ship
                dir = 0;
                if(succHits != null){
                    if(succHits.getY() == previousHit.getY()){ //if the two y values are the same then the direction is right to left
                        dir = 1;
                    }
                    else if(succHits.getX() == previousHit.getX()){//if the two x values are teh same than the direction is up to down
                        dir = 2;
                    }
                }
                this.succHits = new Coordinates(previousHit);
                Log.i("last succ hit", "fire: " + succHits.getX() + " " +  succHits.getY());
            }
            if(this.succHits != null) {
                Log.i("last succ hit", "fire: " + succHits.getX() + " " +  succHits.getY());
                if(dir == 0){ //basecase unknown direction
                    if (checkRight() == true) { //checks if right is a valid move
                        Coordinates rightCoord = new Coordinates(succHits);
                        int val = rightCoord.getX() + 1;
                        rightCoord.setX(val);
                        fireLoc = new Coordinates(rightCoord);
                    } else if (checkLeft() == true) { //check if left is a valid move
                        Coordinates leftCoord = new Coordinates(succHits);
                        int val = leftCoord.getX() - 1;
                        leftCoord.setX(val);
                        fireLoc = new Coordinates(leftCoord);
                    }
                    else if (checkDown() == true) { //checks if down is a valid move
                        Coordinates downCoord = new Coordinates(succHits);
                        int val = downCoord.getY() - 1;
                        downCoord.setX(val);
                        fireLoc = new Coordinates(downCoord);
                    }
                    else if (checkUp() == true) { //checks if up is a valid move
                        Coordinates upCoord = new Coordinates(succHits);
                        int val = upCoord.getY() + 1;
                        upCoord.setY(val);
                        fireLoc = new Coordinates(upCoord);
                    }
                    else{//no moves are possible so fire with random
                        fireLoc = new Coordinates(false, false, row, col);
                    }
                }
                else if(dir == 1) { //ship is facing left to right
                    if (checkRight() == true) { //is right valid
                        Coordinates rightCoord = new Coordinates(succHits);
                        int val = rightCoord.getX() + 1;
                        rightCoord.setX(val);
                        fireLoc = new Coordinates(rightCoord);
                    } else if (checkLeft() == true) { //is left valid
                        Coordinates leftCoord = new Coordinates(succHits);
                        int val = leftCoord.getX() - 1;
                        leftCoord.setX(val);
                        fireLoc = new Coordinates(leftCoord);
                    }
                    else{ //no moves are possible so fire with random
                        fireLoc = new Coordinates(false, false, row, col);
                        dir = 2;
                    }
                }
                else if(dir == 2) { //direction is up and down
                    if (checkDown() == true) { //is down valid?
                        Coordinates downCoord = new Coordinates(succHits);
                        int val = downCoord.getY() - 1;
                        downCoord.setX(val);
                        fireLoc = new Coordinates(downCoord);
                    }
                    else if (checkUp() == true) { // is up valid
                        Coordinates upCoord = new Coordinates(succHits);
                        int val = upCoord.getY() + 1;
                        upCoord.setY(val);
                        fireLoc = new Coordinates(upCoord);
                    }
                    else{//no moves are possible so fire with random
                        fireLoc = new Coordinates(false, false, row, col);
                        dir = 1;
                    }
                }
            }
        }
        if (checkIfMiss(fireLoc)){
         fireLoc = new Coordinates(false, false, row, col);
        }
        Log.i("COMPUTER FIRING AT ", "fire:" +  fireLoc.getX() + " " + fireLoc.getY());
        this.previousHit = new Coordinates (fireLoc);
        //sleep(1);
        game.sendAction(new Fire(this, fireLoc, playerNum));
    }


    /**
     * checks if last fire hit a ship
     * @param coord
     * @return
     */
    public boolean checkSuccHit(Coordinates coord){
        GameBoard board = new GameBoard(this.gameState .getBoard(enemyNum));
        if(board.getHasShip(coord.getX(), coord.getY())){
            return true;
        }
        return false;
    }

    /**
     * checks if a coord has already been fired at
     * @param coord
     * @return
     */
    public boolean checkIfMiss(Coordinates coord) {
        GameBoard board = new GameBoard(this.gameState.getBoard(enemyNum));
        if (coord != null) {
            Log.i("Already hit", "checkIfMiss: ");
            return (board.getCoordHit(coord.getX(), coord.getY()));
        }
        return false;
    }


    /**
     * The following 4 methods are the same, just different directions
     * Check if direction is a valid move, if it is it returns true
     * @return
     */
    public boolean checkRight(){
        int nextX = succHits.getX();
        Coordinates rightCoord = new Coordinates(succHits);
        if(nextX >= 9){
            return false;
        }
        rightCoord.setX(nextX + 1);
        if(checkIfMiss(rightCoord) == true ){
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
        if(checkIfMiss(leftCoord) == true ){
            Log.i("SHIP ALREADY HIT", "checkLeft: SHIP ALREADY HIT");
            return false;
        }
        return true;
    }
    public boolean checkUp(){
        int nextY = succHits.getY();
        Coordinates upCoord = new Coordinates(succHits);
        if(nextY >= 9){
            return false;
        }
        upCoord.setY(nextY + 1);
        if(checkIfMiss(upCoord) == true){
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
        if(checkIfMiss(downCoord) == true){
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
     * Places ship depending on how many ships the comp has already placed
     */
    public void placeShips() {
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
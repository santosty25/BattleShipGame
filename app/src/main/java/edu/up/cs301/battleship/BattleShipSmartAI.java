package edu.up.cs301.battleship;

import android.util.Log;

import java.nio.file.ClosedFileSystemException;
import java.util.Random;

import javax.security.auth.login.LoginException;

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
    private BattleshipObj battleship;
    private BattleShipGameState gameState;
    private int shipNum;
    private int dir;
    private Coordinates previousHit;
    private Coordinates succHits;
    private Coordinates firstSuccHit; //its the first successful hit is a "combo"
    private int enemyNum;

    public BattleShipSmartAI(String name) {
        super(name);
        this.dir = 0;
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
        if (this.gameState .getPhase() == BattleShipGameState.SETUP_PHASE){ //calls place ships if its insetUP
            placeShips();
        }
        else if(this.gameState.getPhase() == BattleShipGameState.BATTLE_PHASE && gameState.getPlayersTurn() == playerNum){
            this.fire();
        }
    }

    /**
     * The fire though process for the smart AI
     * Randomly fires until it finds a direction of a ship
     *
     */
    public void fire() {
        if (this.gameState.getPhase() != BattleShipGameState.BATTLE_PHASE || playerNum != gameState.getPlayersTurn()) {
            return;
        }
        Random r = new Random();
        int row = r.nextInt(10);
        int col = r.nextInt(10);
        Coordinates fireLoc = new Coordinates(false, false, row, col); // a random fire coordinate
        if (this.previousHit != null) { //check if a previous hit exits
            if(checkSuccHit(previousHit)) { //Checks if last hit hit a battle ship
                if(succHits != null) {
                    if (previousHit.getY() == succHits.getY()) { //if the y values are the same, the dir is left OR right
                        dir = 1;
                    } else if (previousHit.getX() == succHits.getX()) { //if the x values are the same, the dir is up OR down
                        dir = 2;
                    }
                }
                else{
                    firstSuccHit = new Coordinates(previousHit); //if there is no succ hit than assign last previous to be firstSucc
                }
                this.succHits = new Coordinates(previousHit);// if the last hit was successful tha assign it to succHit
            }
            if(this.succHits != null) {
                fireLoc = new Coordinates(nextFire());
            }
        }
        Log.i("COMPUTER FIRING AT ", "fire:" +  fireLoc.getX() + " " + fireLoc.getY());
        this.previousHit = new Coordinates (fireLoc);
        //sleep(1); changes
        game.sendAction(new Fire(this, fireLoc, playerNum));
    }


    /**
     * Creates a coodinidate to fire at
     * If no previous coord had been hit, it will return a random coord
     * But if a coord has been hit previously than it will determine the direction and fire
     * @return Coordinate to be fired at
     */
    public Coordinates nextFire(){ //fires
        Random r = new Random();
        int row = r.nextInt(10);
        int col = r.nextInt(10);
        if(row % 2 == 0){ //Random
            if (col % 2 == 0 && col < 9){
                col += 1;
            }
        }
        Coordinates fireLoc = new Coordinates(false, false, row, col);
        if(dir == 0){ //basecase unknown direction
            if (checkRight()) { //checks if right is a valid move
                Coordinates rightCoord = new Coordinates(succHits);
                int val = succHits.getX() + 1;
                rightCoord.setX(val);
                fireLoc = new Coordinates(rightCoord);
            } else if (checkLeft()) { //check if left is a valid move
                Coordinates leftCoord = new Coordinates(succHits);
                int val = succHits.getX() - 1;
                leftCoord.setX(val);
                fireLoc = new Coordinates(leftCoord);
            }
            else if (checkDown()) { //checks if down is a valid move
                Coordinates downCoord = new Coordinates(succHits);
                int val = succHits.getY() + 1;
                downCoord.setY(val);
                fireLoc = new Coordinates(downCoord);
            }
            else if (checkUp()) { //checks if up is a valid move
                Coordinates upCoord = new Coordinates(succHits);
                int val = succHits.getY() - 1;
                upCoord.setY(val);
                fireLoc = new Coordinates(upCoord);
            }
            else{//no moves are possible so
                succHits = null;
            }
        }
        else if(dir == 1){ //checks if right or left are valid moves
            if (checkRight()) { //checks if right is a valid move
                Coordinates rightCoord = new Coordinates(succHits);
                int val = succHits.getX() + 1;
                rightCoord.setX(val);
                fireLoc = new Coordinates(rightCoord);
            } else if (checkLeft()) { //check if left is a valid move
                Coordinates leftCoord = new Coordinates(succHits);
                int val = succHits.getX() - 1;
                leftCoord.setX(val);
                fireLoc = new Coordinates(leftCoord);
            }
            else{ //np possible left or right hits
                dir = 0; //reset dir to 0
                succHits = new Coordinates(firstSuccHit); //set last succ hit to be the first
                fireLoc = new Coordinates(nextFire());
            }
        }
        else if(dir == 2){
            if (checkDown()) { //checks if down is a valid move
                Coordinates downCoord = new Coordinates(succHits);
                int val = succHits.getY() + 1;
                downCoord.setY(val);
                fireLoc = new Coordinates(downCoord);
            }
            else if (checkUp()) { //checks if up is a valid move
                Coordinates upCoord = new Coordinates(succHits);
                int val = succHits.getY() - 1;
                upCoord.setY(val);
                fireLoc = new Coordinates(upCoord);
            }
            else{
                dir = 0; //reset dir to be 0
                succHits = new Coordinates(firstSuccHit); //set succ hit to the first succHit
                fireLoc = new Coordinates(nextFire()); //call fire
            }
        }
        return fireLoc;
    }




    /**
     * checks if last fire hit a ship
     * @param coord Coordinate to be check
     * @return
     */
    public boolean checkSuccHit(Coordinates coord){
        GameBoard board = new GameBoard(this.gameState.getBoard(enemyNum));
        return board.getHasShip(coord.getX(), coord.getY());
    }

    /**
     * checks if a coord has already been fired at
     * @param coord Coordinate to be check
     * @return
     */
    public boolean checkIfMiss(Coordinates coord) {
        GameBoard board = new GameBoard(this.gameState.getBoard(enemyNum));
        if (coord != null) {
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
        int nextX = succHits.getX() + 1;
        Coordinates rightCoord = new Coordinates(succHits);
        if(nextX > 9){//out of bounds check
            return false;
        }
        rightCoord.setX(nextX);
        if(checkIfMiss(rightCoord)){//coord already hit
            return false;
        }
        return true;
    }
    public boolean checkLeft(){
        int nextX = succHits.getX() - 1 ;
        Coordinates leftCoord = new Coordinates(succHits);
        if(nextX < 0){//out of bounds check
            return false;
        }
        leftCoord.setX(nextX);
        if(checkIfMiss(leftCoord)){//coord already hit
            return false;
        }
        return true;
    }
    public boolean checkUp(){
        int nextY = succHits.getY() - 1;
        Coordinates upCoord = new Coordinates(succHits);
        if(nextY < 0){//out of bounds check
            return false;
        }
        upCoord.setY(nextY);
        if(checkIfMiss(upCoord)){//coord already hit
            return false;
        }
        return true;
    }
    public boolean checkDown(){
        int nextY = succHits.getY() + 1;
        Coordinates downCoord = new Coordinates(succHits);
        if(nextY > 9){ //out of bounds check
            return false;
        }
        downCoord.setY(nextY);
        if(checkIfMiss(downCoord)){ //coord already hit
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
        while (overlap) {

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
        for (int j = 0; j < 6; j++) {
            for (Coordinates coord : coords) {
                if (gs.getPlayersFleet()[playerNum][j] == null) {
                    continue;
                } else {
                    for (int l = 0; l < gs.getPlayersFleet()[playerNum][j].getLocation().length; l++)
                        if (gs.getPlayersFleet()[playerNum][j].getLocation()[l].getX() == coord.getX() &&
                                gs.getPlayersFleet()[playerNum][j].getLocation()[l].getY() == coord.getY()) {
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
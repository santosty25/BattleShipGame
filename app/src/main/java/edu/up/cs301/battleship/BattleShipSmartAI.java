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
    private int numTurnsSinceFire;
    private int assumRemainShips;
    private boolean startAlgor;


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
        int enemyNum;
        if (!(info instanceof BattleShipGameState)) {
            return;
        }
        this.gameState = new BattleShipGameState((BattleShipGameState) info);
        if (playerNum == 0) {
            enemyNum = 1;
        } else {
            enemyNum = 0;
        }

        GameBoard board = new GameBoard(gameState.getBoard(enemyNum));

        //CALLING PLACE SHIPS
        shipNum = 0;
        for(int i = 0; i < gameState.getPlayersFleet()[playerNum].length; i++){
            if(gameState.getPlayersFleet()[playerNum][i].getSize() != 1){
                shipNum++;
            }
        }
        if(shipNum == 6){
            game.sendAction(new SwitchPhase(this, playerNum, true));
        }
        if (gameState.getPhase() == BattleShipGameState.SETUP_PHASE){
            placeShip();
        }
        else if(this.gameState.getPhase() == BattleShipGameState.BATTLE_PHASE){
            this.fire();
        }
    }

    public void fire() {
        int enemyNum;
        if (playerNum == 0) {
            enemyNum = 1;
        } else {
            enemyNum = 0;
        }
        if (gameState.getPhase() == BattleShipGameState.BATTLE_PHASE) {
            Log.i("COMPUTER PLAYERS TURN", "");
            if (gameState.getRemainingShips(enemyNum) < this.assumRemainShips) {
                this.reset();
            }
            //check if the previous shot was a successful hit
            GameBoard playerBoard = gameState.getBoard(enemyNum);
            if (previousHit != null) {//null checker
                int x = previousHit.getX();
                int y = previousHit.getY();
                boolean wasHit = playerBoard.getCoordHit(x, y);
                Coordinates[][] coords = playerBoard.getCurrentBoard();
                //Reads locations of opponents board and checks if hit was successful
                if (coords[x][y].getHasShip() == true && wasHit == true) {
                    this.successHits.add(previousHit);
                }
            }//if no sucessful hits stored random fire
            if (this.successHits.size() == 0) {
                this.randomFire();
            } else {
                this.algorithm();
            }
        }
    }



    public void randomFire() {
        int enemyNum;
        if (playerNum == 0) {
            enemyNum = 1;
        } else {
            enemyNum = 0;
        }

        //fire randomly
        Random r = new Random();
        sleep(1);
        int row = r.nextInt(10);
        int col = r.nextInt(10);
        this.previousHit = new Coordinates(false, false, row, col);
        Log.i("COMPUTER randomFire", "Fired at " + row + " " + col + ".");
        if (this.gameState.getBoard(enemyNum).getCurrentBoard()[row][col].getHit()) {
            this.fire();
        }
        game.sendAction(new Fire(this, this.previousHit, playerNum));
    }

    public void algorithm() {
        int enemyNum;
        if (playerNum == 0) {
            enemyNum = 1;
        } else {
            enemyNum = 0;
        }

        //check if coordinates are near the borders
        if (this.previousHit.getX() == 0 || this.previousHit.getX() == 9 ||
                this.previousHit.getY() == 0 || this.previousHit.getY() == 9) {
            this.dir++;
        }
        boolean hit = false;

        GameBoard playerBoard = gameState.getBoard(enemyNum);
        int x = previousHit.getX();
        int y = previousHit.getY();
        boolean wasHit = playerBoard.getCoordHit(x, y);
        Coordinates[][] coords = playerBoard.getCurrentBoard();
        //check for successful hit
        if (coords[x][y].getHasShip() == true && wasHit == true) {
            hit = true;
        }
        if(hit == false) {
            if(successHits.size() != 0) {
                this.successHits.remove(this.successHits.size() - 1);
            }
            this.dir++;
            this.start = 0;
        }
            if (dir == BattleShipSmartAI.RIGHT) {
                if(this.start == 0) {
                    Coordinates lastFire = this.successHits.get(0);
                    this.previousHit = new Coordinates(true, false,
                            lastFire.getX() + 1, lastFire.getY());
                    this.start++;
                } else {
                    Coordinates lastFire = this.successHits.get(this.successHits.size() - 1);
                    this.previousHit = new Coordinates(true, false,
                            lastFire.getX() + 1, lastFire.getY());
                }
                this.successHits.add(this.previousHit);
            }
            if (this.dir == BattleShipSmartAI.LEFT) {
                if(this.start == 0) {
                    Coordinates lastFire = this.successHits.get(0);
                    this.previousHit = new Coordinates(true, false,
                            lastFire.getX() + 1, lastFire.getY());
                    this.start++;
                }
                else {
                    Coordinates lastFire = this.successHits.get(this.successHits.size() - 1);
                    this.previousHit = new Coordinates(true, false,
                            lastFire.getX() - 1, lastFire.getY());
                }
                this.successHits.add(this.previousHit);
            }
            if (this.dir == BattleShipSmartAI.UP) {
                if(this.start == 0) {
                    Coordinates lastFire = this.successHits.get(0);
                    this.previousHit = new Coordinates(true, false,
                            lastFire.getX() + 1, lastFire.getY());
                    this.start++;
                }
                else {
                    Coordinates lastFire = this.successHits.get(this.successHits.size() - 1);
                    this.previousHit = new Coordinates(true, false,
                            lastFire.getX(), lastFire.getY() - 1);
                }
                this.successHits.add(this.previousHit);
            }
            if (this.dir == BattleShipSmartAI.DOWN) {
                if(this.start == 0) {
                    Coordinates lastFire = this.successHits.get(0);
                    this.previousHit = new Coordinates(true, false,
                            lastFire.getX() + 1, lastFire.getY());
                    this.start++;
                }
                else {
                    Coordinates lastFire = this.successHits.get(this.successHits.size() - 1);
                    this.previousHit = new Coordinates(true, false,
                            lastFire.getX(), lastFire.getY() + 1);
                }
                this.successHits.add(this.previousHit);
            }
        if (this.gameState.getBoard(enemyNum).getCurrentBoard()[x][y].getHit()){
            this.fire();
        }
        game.sendAction(new Fire(this, this.previousHit, playerNum));
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
     * setShips - This method creates a battleship of a given size and
     * sends the action to place this battleship.
     *
     * @param size - the size of the battleship
     */
    public void setShips(int size, BattleShipGameState gs) {
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

    public void placeShips(Coordinates[] coords, int size) {
        Coordinates[] location;

        if(size == 2) {
            location = new Coordinates[]{coords[1], coords[2]};
        }
        else if (size == 3) {
            location = new Coordinates[]{coords[1], coords[2], coords[3]};
        }
        else if (size == 4) {
            location = new Coordinates[]{coords[0], coords[1], coords[2], coords[3]};
        }
        else {
            location = new Coordinates[]{coords[0], coords[1], coords[2], coords[3], coords[4]};
        }
        this.battleship = new BattleshipObj(size, location);
        game.sendAction(new PlaceShip(this, this.battleship, playerNum));
    }
}

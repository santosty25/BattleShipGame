package edu.up.cs301.battleship;

import android.util.Log;

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
    private BattleshipObj battleship;
    private int placeShips;
    private int cardinalLineDir;
    private int dir;
    private int test = 0;
    private ArrayList<Coordinates> possibleShip = new ArrayList<Coordinates>();
    private ArrayList<Coordinates> successHits = new ArrayList<Coordinates>();
    private Coordinates previousHit;
    private int numTurnsSinceFire;
    private int assumRemainShips;
    private int startAlgor;

    private Coordinates nextCoord;

    public BattleShipSmartAI(String name) {
        super(name);
        this.assumRemainShips = 6;
        this.placeShips = 1;
        this.dir = 0;
        this.cardinalLineDir = 0;
        this.startAlgor = 0;
        this.numTurnsSinceFire = 0;
    }


    @Override
    protected void receiveInfo(GameInfo info) {
        int enemyNum;
        if (!(info instanceof BattleShipGameState)) {
            return;
        }

        BattleShipGameState gameState = new BattleShipGameState((BattleShipGameState) info, playerNum);
        if(playerNum == 0) {
            enemyNum = 1;
        }
        else {
            enemyNum = 0;
        }
        int enemRemainShips = gameState.getRemainingShips(enemyNum);
        GameBoard board = gameState.getBoard(enemyNum);
        Log.i("COMPUTER PLAYERS TURN", "");
        //Checks if it's the computer player's turn
        //if (gameState.getPlayersTurn() == playerNum) {
            //if(gameState.getPhase() == BattleShipGameState.BATTLE_PHASE) {
            //places ships

            this.setShips(5);
            this.setShips(4);
            this.setShips(4);
            this.setShips(3);
            this.setShips(3);
            this.setShips(2);

            ++this.placeShips;
            //fires at coordinates randomly
            Log.i("COMPUTER PLAYERS TURN", "");
            while(this.startAlgor == 0) {
                if (gameState.getPhase() == BattleShipGameState.BATTLE_PHASE) {
                    //check if the previous shot was a successful hit
                    boolean hit = this.checkIfCoordHit(board, this.previousHit);
                    if (hit == true) {
                        this.possibleShip.add(this.previousHit);
                        this.startAlgor = 1;
                    }

                    Random r = new Random();
                    int row;
                    int col;
                    //sleep(1);
                    row = r.nextInt(10);
                    col = r.nextInt(10);
                    Coordinates fire = new Coordinates(false, false, row, col);
                    this.previousHit = new Coordinates(fire);
                    Log.i("COMPUTER randomFire", "Fired at " + row + " " + col + ".");
                    game.sendAction(new Fire(this, fire));
                }
            //}

            while(this.startAlgor == 1) {
                boolean hit = false;
                if (gameState.getPhase() == BattleShipGameState.BATTLE_PHASE) {
                    //check if the previous shot was a successful hit


                    Coordinates[] enemyShipCoords;
                    BattleshipObj[][] shipsOnBoard = gameState.getPlayersFleet();
                    //Reads locations of opponents board
                        for(int i = 0; i < shipsOnBoard[enemyNum].length; i++){
                            for(int j = 0; j < shipsOnBoard[enemyNum][i].getLocation().length; j++){
                                if(shipsOnBoard[enemyNum][i].getLocation()[j].getX() ==
                                        this.previousHit.getX() &&
                                        shipsOnBoard[enemyNum][i].getLocation()[j].getY()
                                                == this.previousHit.getY()){
                                    hit = true;
                                }
                            }
                        }

                    if (hit == true) {
                        this.successHits.add(this.previousHit);
                    }

                    Coordinates lastFire = new Coordinates(possibleShip.get(0));
                    if(lastFire.getX() != 0 || lastFire.getX() != 9 && hit != false) {
                            Coordinates nextFire = new Coordinates(true, false,
                                    lastFire.getX() + 1, lastFire.getY());
                            if(this.checkIfCoordHit(board, nextFire) == true) {
                                game.sendAction(new Fire(this, nextFire));
                            }
                    }

                }
            }
        }


    }

    /**
     * checkIfCoordHit - Checks if the previous fire was a successful hit.
     * @param board - the board to check
     * @param coord - a given coordinate
     * @return true if the coordinate was a hit
     *         false if the coordinate was a miss
     */
    public boolean checkIfCoordHit(GameBoard board, Coordinates coord) {
        if(coord != null) {
            if (board.getCoordHit(coord.getX(), coord.getY()) == true) {
                return true;
            }
        }
        else {
            return false;
        }
        return false;
    }

    public void setNextHit(Coordinates coord, int direction) {

    }

    /**
     * setShips - This method creates a battleship of a given size and
     * sends the action to place this battleship.
     * @param size - the size of the battleship
     */
    public void setShips(int size) {
        test++;
        Coordinates[] location;
        Random randGen = new Random();
        final int HORIZONTAL = 0;
        int randRow = randGen.nextInt(10);
        int randCol = randGen.nextInt(10);
        int randOrientation = randGen.nextInt(2);

        Coordinates coord1;
        Coordinates coord2;
        Coordinates coord3;
        Coordinates coord4;
        Coordinates coord5;
        //create random x and y coords for ships that doesn't go out of bounds
        while(randRow < 2 || randRow > 7) {
            randRow = randGen.nextInt(10);
        }
        while(randCol < 2 || randCol > 7) {
            randCol = randGen.nextInt(10);
        }
        if(randOrientation == 0) {
            coord1 = new Coordinates (false, true, randRow, randCol + 2);
            coord2 = new Coordinates(false, true, randRow, randCol + 1);
            coord3 = new Coordinates(false, true, randRow, randCol);
            coord4 = new Coordinates(false, true, randRow, randCol - 1);
            coord5 = new Coordinates(false, true, randRow, randCol - 2);
        }
        else {
            coord1 = new Coordinates (false, true,randRow + 2, randCol);
            coord2 = new Coordinates(false, true, randRow + 1, randCol);
            coord3 = new Coordinates(false, true, randRow, randCol);
            coord4 = new Coordinates(false, true, randRow - 1, randCol);
            coord5 = new Coordinates(false, true, randRow - 2, randCol);
        }
        //this.setOrientation(randOrientation, randRow, randCol, size);

        if(size == 2) {
            location = new Coordinates[]{coord2, coord3};
        }
        else if (size == 3) {
            location = new Coordinates[]{coord2, coord3, coord4};
        }
        else if (size == 4) {
            location = new Coordinates[]{coord1, coord2, coord3, coord4};
        }
        else {
            location = new Coordinates[]{coord1, coord2, coord3, coord4, coord5};
        }


        for (int i = 0; i < location.length; i++) {
            Log.d("location test X", "" + location[i].getX() + " " + location[i].getY() + " test: " + test);
        }
        this.battleship = new BattleshipObj(size, location);
        game.sendAction(new PlaceShip(this, this.battleship, playerNum));
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

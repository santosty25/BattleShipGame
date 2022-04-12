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
        this.startAlgor = 0;
        this.numTurnsSinceFire = 0;
    }


    @Override
    protected void receiveInfo(GameInfo info) {
        int enemyNum;
        if (!(info instanceof BattleShipGameState)) {
            return;
        }
        BattleShipGameState gameState = new BattleShipGameState((BattleShipGameState) info);
        if (playerNum == 0) {
            enemyNum = 1;
        } else {
            enemyNum = 0;
        }
        GameBoard board = gameState.getBoard(enemyNum);
        Log.i("COMPUTER PLAYERS TURN", "");
        this.setShips(5);
        this.setShips(4);
        this.setShips(4);
        this.setShips(3);
        this.setShips(3);
        this.setShips(2);

        ++this.placeShips;
        //fires at coordinates randomly
        Log.i("COMPUTER PLAYERS TURN", "");
        if (gameState.getRemainingShips(enemyNum) < this.assumRemainShips) {
            this.possibleShip.remove(0);
            this.assumRemainShips--;
            this.successHits.clear();
            if (this.possibleShip.size() == 0) {
                this.startAlgor = 0;
            }
        }
        if (this.startAlgor == 0) {
            //check if the previous shot was a successful hit
            boolean hit = false;
            BattleshipObj[][] shipsOnBoard = gameState.getPlayersFleet();
            //Reads locations of opponents board
            if (previousHit != null) {
                for (int i = 0; i < shipsOnBoard[enemyNum].length; i++) {
                    for (int j = 0; j < shipsOnBoard[enemyNum][i].getLocation().length; j++) {
                        if (shipsOnBoard[enemyNum][i].getLocation()[j].getX() ==
                                this.previousHit.getX() &&
                                shipsOnBoard[enemyNum][i].getLocation()[j].getY()
                                        == this.previousHit.getY()) {
                            hit = true;
                        }
                    }
                }
            }
            if (hit == true) {
                this.startAlgor++;
                this.possibleShip.add(previousHit);
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

        if (this.startAlgor == 1) {
            boolean hit = false;
            BattleshipObj[][] shipsOnBoard = gameState.getPlayersFleet();
            if (gameState.getPhase() == BattleShipGameState.BATTLE_PHASE) {
                //check if the previous shot was a successful hit
                //Reads locations of opponents board
                for (int i = 0; i < shipsOnBoard[enemyNum].length; i++) {
                    for (int j = 0; j < shipsOnBoard[enemyNum][i].getLocation().length; j++) {
                        if (shipsOnBoard[enemyNum][i].getLocation()[j].getX() ==
                                this.previousHit.getX() &&
                                shipsOnBoard[enemyNum][i].getLocation()[j].getY()
                                        == this.previousHit.getY()) {
                            hit = true;
                        }
                    }
                }

                if (hit == true) {
                    this.successHits.add(this.previousHit);
                } else {
                    this.dir++;
                }

                Coordinates lastFire = new Coordinates(possibleShip.get(0));
                int next = 1;
                if (lastFire.getX() != 0 || lastFire.getX() != 9 && hit == true) {
                    boolean hitCoord;
                    if (dir == 0) {
                        this.previousHit = new Coordinates(true, false,
                                lastFire.getX() + next, lastFire.getY());
                        hitCoord = this.checkIfCoordHit(board, this.previousHit);
                        while (hit == true) {
                            ++next;
                            if(lastFire.getX() + next == 0) {
                                this.dir++;
                                break;
                            }
                            this.previousHit = new Coordinates(true, false,
                                    lastFire.getX() - next, lastFire.getY());
                            hitCoord = this.checkIfCoordHit(board, this.previousHit);
                        }
                    if (hitCoord == false && dir < 0) {
                            game.sendAction(new Fire(this, this.previousHit));
                        }
                    }
                    if (dir == 1) {
                        this.previousHit = new Coordinates(true, false,
                                lastFire.getX() + next, lastFire.getY());
                        hitCoord = this.checkIfCoordHit(board, this.previousHit);
                        while (hit == true) {
                            ++next;
                            if(lastFire.getX() + next > 9) {
                                this.dir++;
                                break;
                            }
                            this.previousHit = new Coordinates(true, false,
                                    lastFire.getX() + next, lastFire.getY());
                            hitCoord = this.checkIfCoordHit(board, this.previousHit);
                        }
                        if (hitCoord == false && dir == 1) {
                            game.sendAction(new Fire(this, this.previousHit));
                        }
                    }

                } else if (lastFire.getX() == 0 || lastFire.getX() == 9 && hit == true) {
                    this.dir++;
                }
                if (lastFire.getY() != 0 || lastFire.getY() != 9 && hit == true) {
                    boolean hitCoord;
                    if (dir == 2) {
                        for (int i = 1; i < this.successHits.size(); i++) {
                            this.possibleShip.add(successHits.get(i));
                        }
                        this.previousHit = new Coordinates(true, false,
                                lastFire.getX(), lastFire.getY() - next);
                        hitCoord = this.checkIfCoordHit(board, this.previousHit);
                        while (hit == true) {
                            ++next;
                            if(lastFire.getY() + next < 0) {
                                this.dir++;
                                break;
                            }
                            this.previousHit = new Coordinates(true, false,
                                    lastFire.getX(), lastFire.getY() - next);
                            hitCoord = this.checkIfCoordHit(board, this.previousHit);
                        }
                        if (hitCoord == false && dir == 2) {
                            game.sendAction(new Fire(this, this.previousHit));
                        }
                    }
                    if (dir == 3) {
                        this.previousHit = new Coordinates(true, false,
                                lastFire.getX(), lastFire.getY() + next);
                        hitCoord = this.checkIfCoordHit(board, this.previousHit);
                        while (hit == true) {
                            ++next;
                            if(lastFire.getY() + next > 9) {
                                this.dir++;
                                break;
                            }
                            this.previousHit = new Coordinates(true, false,
                                    lastFire.getX(), lastFire.getY() + next);
                            hitCoord = this.checkIfCoordHit(board, this.previousHit);
                        }
                        if (hitCoord == false && dir == 3) {
                            game.sendAction(new Fire(this, this.previousHit));
                        }
                    }
                } else if (lastFire.getY() == 0 || lastFire.getY() == 9 && hit == true) {
                    this.dir++;
                }

            }

        }
    }

    /**
     * checkIfCoordHit - Checks if the previous fire was a successful hit.
     *
     * @param board - the board to check
     * @param coord - a given coordinate
     * @return true if the coordinate was a hit
     * false if the coordinate was a miss
     */
    public boolean checkIfCoordHit(GameBoard board, Coordinates coord) {
        if (coord != null) {
            if (board.getCoordHit(coord.getX(), coord.getY()) == true) {
                return true;
            }
        } else {
            return false;
        }
        return false;
    }

    public void setNextHit(Coordinates coord, int direction) {

    }

    /**
     * setShips - This method creates a battleship of a given size and
     * sends the action to place this battleship.
     *
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

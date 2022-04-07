package edu.up.cs301.battleship;

import android.util.Log;

import java.util.Random;

import edu.up.cs301.game.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.game.GameFramework.players.GameComputerPlayer;

/**
 * BattleShipDumbAI - This class represents a dumb AI that places ships in a fixed
 * position and fires randomly.
 *
 * @author Tyler Santos
 * @author Austen Furutani
 * @author Keoni Han
 * @author Steven Lee
 * @version Spring 2022 - 3/31/22
 */
public class BattleShipDumbAI extends GameComputerPlayer {

    private BattleshipObj battleship;
    private int placeShips = 1;
    private int test = 0;

    /**
     * Constructor
     * @param name - the name of the computer player
     */
    public BattleShipDumbAI(String name) {
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {
        if (!(info instanceof BattleShipGameState)) {
            return;
        }

        BattleShipGameState gameState = new BattleShipGameState((BattleShipGameState) info, playerNum);
        Log.i("COMPUTER PLAYERS TURN", "");

        //Checks if it's the computer player's turn
        if (gameState.getPlayersTurn() == playerNum) {
            //if(gameState.getPhase() == BattleShipGameState.BATTLE_PHASE) {
                //places ships
                if(this.placeShips==1) {
                    this.setShips(5);
                    this.setShips(4);
                    this.setShips(4);
                    this.setShips(3);
                    this.setShips(3);
                    this.setShips(2);
                }
                ++this.placeShips;
                //fires at coordinates randomly
                Log.i("COMPUTER PLAYERS TURN", "");
                Random r = new Random();
                int row;
                int col;
                //sleep(1);
                row = r.nextInt(10);
                col = r.nextInt(10);
                Coordinates fire = new Coordinates(false, false, row, col);
                Log.i("COMPUTER randomFire", "Fired at " + row + " " + col + ".");
                game.sendAction(new Fire(this, fire));
            //}
            //else if(gameState.getPhase() == BattleShipGameState.SETUP_PHASE) {

            //}
        }

        //SAVE FOR BETA RELEASE
        //Let's dumb ai place ships randomly
//        if (playerNum == gameState.getPlayersTurn()) {
//            Coordinates[] location = new Coordinates[3];
//            Coordinates c1;
//            Coordinates c2 = new Coordinates();
//            Coordinates c3 = new Coordinates();
//            Coordinates c4 = new Coordinates();
//            Coordinates c5;
//            if (gameState.getPhase() == BattleShipGameState.SETUP_PHASE) {
//                int i = 1;
//                // 2 x 3 length ships
//                while (i < 3) {
//                    Random r = new Random();
//                    int row = r.nextInt(10) + 1;
//                    int col = r.nextInt(10) + 1;
//                    int dir = r.nextInt(2) + 1;
//                    c1 = new Coordinates(false, true, row, col);
//                    if (dir == 1) { // place the ships horizontally
//                        c2 = new Coordinates(false, true, row, col + 1);
//                        c3 = new Coordinates(false, true, row, col - 1);
//                    } else if (dir == 2) { // place the ships vertically
//                        c2 = new Coordinates(false, true, row + 1, col);
//                        c3 = new Coordinates(false, true, row - 1, col);
//                    }
//                    location[0] = c1;
//                    location[1] = c2;
//                    location[2] = c3;
//                    BattleshipObj bsj = new BattleshipObj(3, location);
//                    game.sendAction(new PlaceShip(this, bsj));
//                    i++;
//                }
//                location = new Coordinates[4];
//                i = 1;
//                // 2 x 4 length ships
//                while (i < 3) {
//                    Random r = new Random();
//                    int row = r.nextInt(10) + 1;
//                    int col = r.nextInt(10) + 1;
//                    int dir = r.nextInt(2) + 1;
//                    c1 = new Coordinates(false, true, row, col);
//                    if (dir == 1) { // place the ships horizontally
//                        c2 = new Coordinates(false, true, row, col + 1);
//                        c3 = new Coordinates(false, true, row, col - 1);
//                        c4 = new Coordinates(false, true, row, col - 2);
//                    } else if (dir == 2) { // place the ships vertically
//                        c2 = new Coordinates(false, true, row + 1, col);
//                        c3 = new Coordinates(false, true, row - 1, col);
//                        c4 = new Coordinates(false, true, row - 2, col);
//                    }
//                    location[0] = c1;
//                    location[1] = c2;
//                    location[2] = c3;
//                    location[3] = c4;
//                    BattleshipObj bsj = new BattleshipObj(4, location);
//                    game.sendAction(new PlaceShip(this, bsj));
//                    i++;
//                }
//                // 2 length ship
//                location = new Coordinates[2];
//                Random r = new Random();
//                int row = r.nextInt(10) + 1;
//                int col = r.nextInt(10) + 1;
//                int dir = r.nextInt(2) + 1;
//                c1 = new Coordinates(false, true, row, col);
//                if (dir == 1) { // place the ships vertically
//                    c2 = new Coordinates(false, true, row + 1, col);
//                } else { // place the ships horizontally
//                    c2 = new Coordinates(false, true, row, col + 1);
//                }
//                location[0] = c1;
//                location[1] = c2;
//                BattleshipObj bsj = new BattleshipObj(2, location);
//                game.sendAction(new PlaceShip(this, bsj));
//
//                // 5 length ship
//                location = new Coordinates[5];
//                r = new Random();
//                row = r.nextInt(10) + 1;
//                col = r.nextInt(10) + 1;
//                dir = r.nextInt(2) + 1;
//                c1 = new Coordinates(false, true, row, col);
//                if (dir == 1) { // place the ships horizontally
//                    c2 = new Coordinates(false, true, row, col + 1);
//                    c3 = new Coordinates(false, true, row, col - 1);
//                    c4 = new Coordinates(false, true, row, col - 2);
//                    c5 = new Coordinates(false, true, row, col + 2);
//                } else { // place the ships vertically
//                    c2 = new Coordinates(false, true, row + 1, col);
//                    c3 = new Coordinates(false, true, row - 1, col);
//                    c4 = new Coordinates(false, true, row - 2, col);
//                    c5 = new Coordinates(false, true, row + 2, col);
//                }
//                location[0] = c1;
//                location[1] = c2;
//                location[2] = c3;
//                location[3] = c4;
//                location[4] = c5;
//                BattleshipObj bso = new BattleshipObj(5, location);
//                game.sendAction(new PlaceShip(this, bso));
//            }
//        }
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
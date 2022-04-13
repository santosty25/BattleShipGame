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


    /**
     * Constructor
     *
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

        BattleShipGameState gameState = new BattleShipGameState((BattleShipGameState) info);
        if (gameState.getPhase() == BattleShipGameState.SETUP_PHASE){
            this.setShips(5);
            this.setShips(4);
            this.setShips(4);
            this.setShips(3);
            this.setShips(3);
            this.setShips(2);
        }
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
    }

    /**
     * setShips - This method creates a battleship of a given size and
     * sends the action to place this battleship.
     *
     * @param size - the size of the battleship
     */
    public void setShips(int size) {
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
        } else if (size == 4) {
            location = new Coordinates[]{coord1, coord2, coord3, coord4};
        } else {
            location = new Coordinates[]{coord1, coord2, coord3, coord4, coord5};
        }
        this.battleship = new BattleshipObj(size, location);
        game.sendAction(new PlaceShip(this, this.battleship, playerNum));
    }

    public void placeShips(Coordinates[] coords, int size) {
        Coordinates[] location;

        if (size == 2) {
            location = new Coordinates[]{coords[1], coords[2]};
        } else if (size == 3) {
            location = new Coordinates[]{coords[1], coords[2], coords[3]};
        } else if (size == 4) {
            location = new Coordinates[]{coords[0], coords[1], coords[2], coords[3]};
        } else {
            location = new Coordinates[]{coords[0], coords[1], coords[2], coords[3], coords[4]};
        }
        this.battleship = new BattleshipObj(size, location);
        game.sendAction(new PlaceShip(this, this.battleship, playerNum));
    }
}
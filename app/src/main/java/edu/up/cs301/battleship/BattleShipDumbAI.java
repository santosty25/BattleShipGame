package edu.up.cs301.battleship;

import android.util.Log;

import java.util.ArrayList;
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

    private ArrayList<BattleshipObj> fleet = new ArrayList<BattleshipObj>();
    private BattleShipGameState compGS;
    private BattleshipObj battleship;
    private int placeShips;


    /**
     * Constructor
     *
     * @param name - the name of the computer player
     */
    public BattleShipDumbAI(String name) {
        super(name);
        this.placeShips = 1;
    }

    @Override
    protected void receiveInfo(GameInfo info) {
        if (!(info instanceof BattleShipGameState)) {
            return;
        }


        Random r = new Random();
        int row;
        int col;
        this.compGS = new BattleShipGameState((BattleShipGameState) info);
        if (compGS.getPhase() == BattleShipGameState.SETUP_PHASE){
            if(this.placeShips == 1) {
                this.setShips(5);
                this.setShips(4);
                this.setShips(4);
                this.setShips(3);
                this.setShips(3);
                this.setShips(2);
                game.sendAction(new SwitchPhase(this, playerNum, true));
            }
            ++this.placeShips;
        }
        //fires at coordinates randomly
        Log.i("COMPUTER PLAYERS TURN", "COMPUTER PLAYERS TURN");
        //sleep(1);
        row = r.nextInt(10);
        col = r.nextInt(10);
        Log.i("COMPUTER FIRE;", "Fired at " + row + " " + col + ".");
        Coordinates fire = new Coordinates(false, false, row, col);
        game.sendAction(new Fire(this, fire, playerNum));
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
        this.checkShip(size, location);
        this.fleet.add(battleship);
        game.sendAction(new PlaceShip(this, this.battleship, playerNum));
    }

    public BattleshipObj checkShip(int size, Coordinates[] location) {
        this.battleship = new BattleshipObj(size, location);
        if(this.fleet.size() != 0) {
            Coordinates[] shipPlace = battleship.getLocation();
            boolean overlap = false;
            for(int i = 0; i < this.fleet.size(); i++) {
                for(int j = 0; j < size; j++) {
                    for(int k = 0; k < this.fleet.get(i).getSize(); k++) {
                        Coordinates[] placeShips = this.fleet.get(i).getLocation();
                        if(shipPlace[j].getX() == placeShips[k].getX() &&
                                shipPlace[j].getY() == placeShips[k].getY()) {
                            overlap = true;
                        }
                    }
                }

            }
        }

        return this.battleship;
    }
}
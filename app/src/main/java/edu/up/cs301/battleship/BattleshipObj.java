package edu.up.cs301.battleship;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.Serializable;

/**
 * BattleshipObj
 * Creates a battleship object, storing its location, its size, and if its been sunk
 *
 * @author Austen Furutani
 * @author Keoni Han
 * @author Steven Lee
 * @author Tyler Santos
 * @version Spring 2022 - 2/22/22
 */
public class BattleshipObj implements Serializable {
    private int size; //the size of this battleship
    private boolean sunk; //boolean that states whether this battleship has been sunk
    private Coordinates[] location; //the location of this battleship
    private int twinShip; //for when there are 2 ships of the same size

    private static final long serialVersionUID = 040420025l;

    /**
     * BattleshipObj - Basic constructor that initializes the instance variables with
     * arguments.
     *
     * @param size     - the size of the ship
     * @param location - the location of the ship on the board
     */
    public BattleshipObj(int size, Coordinates[] location) {
        this.size = size;
        this.twinShip = 0;
        this.sunk = false;
        this.location = new Coordinates[this.size];
        //Log.i("in battleship", "BEFORE LOOP");
        int i;
        if (location != null) {
            for (i = 0; i < location.length; i++) {
                this.location[i] = new Coordinates(location[i]);
            }
        }
    }

    /**
     * BattleshipObj - A copy constructor of a BattleshipObj
     *
     * @param orig
     */
    public BattleshipObj(BattleshipObj orig) {
        this.size = orig.size;
        this.sunk = orig.sunk;
        this.twinShip = orig.twinShip;
        int i;
        this.location = new Coordinates[orig.location.length];
        for (i = 0; i < orig.location.length; i++) {
            this.location[i] = new Coordinates(orig.location[i]);
        }
        //Log.i("in BSOBJ", "BattleshipObj: size " + orig.size);
    }

    /**
     * checkIfSunk - Checks if this battleship has been sunk
     *
     * @param ship - a given battleship
     * @return true or false depending on whether a battleship has been hit
     */
    public boolean checkIfSunk(BattleshipObj ship) {
        int i;
        for (i = 0; i < location.length; i++) {
            if (ship.location[i].getHit() == false) {
                return false;
            }
        }
        //ship sunk
        setSunk(true);
        return true;
    }

    /**
     * getFirstCoord - Gets the first coordinate of a battle ship.
     * @return - the first coordinate
     */
    public Coordinates getFirstCoord() {
        return this.location[0];
    }

    /**
     * getSize - Gets the size of the battleship.
     * @return - the size of the battleship
     */
    public int getSize() {
        return size;
    }

    /**
     * getSunk - Gets the state of the battleship if it is sunk or not
     * @return - the boolean stating whether a ship has been sunk or not
     */
    public boolean getSunk() {
        if (this.size == 1){
            return false;
        }
        return sunk;
    }

    /**
     * getTwinShip - Gets an integer value that represents whether there
     * is two ships of the same size.
     * @return 0 - not a twin ship
     *         1 - is a twin ship
     */
    public int getTwinShip() {
        return twinShip;
    }

    /**
     * getLocation - Gets the location of a battleship.
     * @return - an array of Coordinates
     */
    public Coordinates[] getLocation() {
        return location;
    }

    /**
     * setSize - Sets the size of the battleship.
     * @param size - a given size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * setSunk - Sets the battleship to sunk or not sunk
     * @param sunk - whether a ship is sunk or not
     */
    public void setSunk(boolean sunk) {
        this.sunk = sunk;
    }

    /**
     * setTwinShip - Sets an integer value to determine whether a ship
     * is a part of twins or not.
     * @param twinShip - whether a ship is a twin or not
     */
    public void setTwinShip(int twinShip) {
        this.twinShip = twinShip;
    }

    /**
     * setLocation - Sets the location of a ship to a given Coordinates array.
     * @param location - the location where it is to be placed
     */
    public void setLocation(Coordinates[] location) {
        int i;
        this.location = new Coordinates[location.length];
        Log.i("Size", "" + this.location.length);
        for (i = 0; i < location.length; i++) {

            this.location[i] = new Coordinates(location[i]);
        }
    }

    /**
     * checkCoordHit - Checks if a coordinate of the battleship has been hit.
     * @param coord - a given coordinate
     */
    public void checkCoordHit(Coordinates coord) {
        int givenX = coord.getX();
        int givenY = coord.getY();
        for (int i = 0; i < location.length; i++) {
            int x = location[i].getX();
            int y = location[i].getY();
            if(x == givenX && y == givenY) {
                location[i].setHit(true);
            }
        }
    }

    /**
     * checkIfHit - Checks if a battleships has been sunk.
     * @return true if a part of the ship has been sunk
     *         false if a part of the ship hasn't been sunk
     */
    public boolean checkIfHit() {
        int pointsHit = 0;
        for (int i = 0; i < location.length; i++) {
            if (location[i].getHit() == true) {
                pointsHit++;
            } else {
                continue;
            }
        }
        if(this.size == pointsHit) {
            return true;
        }
        return false;
    }
}

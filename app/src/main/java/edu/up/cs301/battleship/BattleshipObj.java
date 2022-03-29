package edu.up.cs301.battleship;

import android.graphics.Bitmap;
import android.util.Log;

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
public class BattleshipObj {
    private int size; //the size of this battleship
    private boolean sunk; //boolean that states whether this battleship has been sunk
    private Coordinates[] location; //the location of this battleship


    /**
     * BattleshipObj - Basic constructor that initializes the instance variables with
     * arguments.
     *
     * @param size     - the size of the ship
     * @param location - the location of the ship on the board
     */
    public BattleshipObj(int size, Coordinates[] location) {
        this.size = size;
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
        int i;
        //Log.i("in BSOBJ", "BattleshipObj: ");
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


    public int getSize() {
        return size;
    }

    public boolean getSunk() {
        return sunk;
    }

    public Coordinates[] getLocation() {
        return location;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setSunk(boolean sunk) {
        this.sunk = sunk;
    }

    public void setLocation(Coordinates[] location) {
        int i;
        for (i = 0; i < this.location.length; i++) {
            this.location[i] = new Coordinates(location[i]);
        }
    }

    public boolean checkIfHit() {
        boolean sunk;
        for (int i = 0; i < location.length; i++) {
            if (location[i].getHit() == true) {
                sunk = true;
            } else {
                sunk = false;
            }
        }
        return this.sunk;
    }
}

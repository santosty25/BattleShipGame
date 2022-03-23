package edu.up.cs301.battleship;

/**
 * coordinates - A representation of the coordinates on a Battleship board.
 *
 * @author Austen Furutani
 * @author Keoni Han
 * @author Steven Lee
 * @author Tyler Santos
 * @version Spring 2022 - 2/22/22
 */
public class Coordinates {
    private boolean hit; //boolean that states whether a ship on this coordinate has been hit
    private boolean hasShip; //boolean that states whether a ship is on this coordinate
    private int x; //the x value of the coordinate
    private int y; //the y value of the coordinate


    /**
     * creates a coordinate object setting its instance variables
     * @param hit
     * @param hasShip
     */
    public Coordinates(boolean hit, boolean hasShip, int x, int y){
        this.hit = hit;
        this.hasShip = hasShip;
        this.x = x;
        this.y = y;
    }

    /**
     * default constructor for coordinate sets hit and hasShip to false
     */
    public Coordinates(){ //default constructor
        this.hit = false;
        this.hasShip = false;
    }

    /**
     * Copy contructor, creates a copy of the passed in coordinate object
     * @param orig
     */
    public Coordinates(Coordinates orig){
        this.hit = orig.hit;
        this.hasShip = orig.hasShip;
        this.x = orig.x;
        this.y = orig.y;
    }

    /**
     * Changes the boolean of hit to the passed in value, then returns
     * if the coordinate hit has a ship on it
     * @param hit
     * @return
     */
    public boolean setHit(boolean hit){
        this.hit = hit;
        return this.hasShip;
    }

    /**
     * setHasShip - Sets a boolean to true on a specific
     * coordinate of the board that has a portion of a ship.
     * @param ship - boolean stating that there is a ship
     */
    public void setHasShip(Boolean ship){this.hasShip = ship;}


    /**
     * setX - Sets the value of the x coord.
     * @param x - value of x coord
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * setY - Sets the value of the y coord.
     * @param y - the value of the y coord
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * getHit - returns a boolean, if the coordinate has already been hit.
     * @return boolean of hit
     */
    public boolean getHit() {
        return this.hit;
    }

    /**
     * getX - Returns the x value in this Coordinate.
     * @return the value of the x instance variable
     */
    public int getX() {
        return x;
    }

    /**
     * getY - Returns the y value in this Coordinate.
     * @return the value of the x instance variable.
     */
    public int getY() {
        return y;
    }

    /**
     * getHasShip - returns the boolean value stating whether a ship
     * is on this Coordinate.
     * Updated
     * @return the hasShip instance variable
     */
    public boolean getHasShip(){
        return this.hasShip;
    }



}

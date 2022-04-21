package edu.up.cs301.battleship;

import static org.junit.Assert.*;

import org.junit.Test;

public class BattleShipGameStateTest {


    @Test
    public void setHasShip() throws Exception {
        BattleShipGameState bgs = new BattleShipGameState();
        bgs.setHasShip(2, 10, false, 0);
        boolean hasShip = bgs.getPlayersBoard()[0].getHasShip(2, 10);
        assertTrue(hasShip);
    }

    @Test
    public void canFire() throws Exception {
        BattleShipGameState gameState = new BattleShipGameState();
        Coordinates coord = new Coordinates(false, true, 4, 5);

        boolean fire = gameState.canFire(coord);
        assertTrue(fire);
    }

    @Test
    public void placeShip() throws Exception{
    }

    @Test
    public void printFire() throws Exception{
        BattleShipGameState gameState = new BattleShipGameState();
        Coordinates coord = new Coordinates(false, false, 4, 5);
        assertNotNull(gameState.printFire(4,5,true));

        // Tests to see what happens if canfire is false and someone clicks it
        Coordinates coordTwo = new Coordinates(false, false, 4, 5);
        assertNotNull(gameState.printFire(100,100,false));
    }

    @Test
    public void printPlaceShip() throws Exception {
        BattleShipGameState gameState = new BattleShipGameState();
        Coordinates shipOne = new Coordinates(false, false, 50,50);
        Coordinates shipTwo = new Coordinates(false, false, 50,50);
        Coordinates[] ships = {shipOne, shipTwo};
        assertNotNull(gameState.printPlaceShip(ships, false));

        // Tests to see what happens if canfire is false and someone clicks it
        assertNotNull(gameState.printFire(100,100,false));
    }

    @Test
    public void setPlayersFleet() throws Exception {
        BattleShipGameState gameState = new BattleShipGameState();
        gameState.setPlayersFleet(gameState.getPlayersFleet(), 1 );
    }

    @Test
    public void xyToCoordMidGame() {
    }

    @Test
    public void xyToCoordSetupGame() {
    }

    @Test
    public void middleXOfCoord() {
    }

    @Test
    public void middleYOfCoord() {
    }

    @Test
    public void middleXOfEnemyBoard() {
    }

    @Test
    public void middleYOfEnemyBoard() {
    }

    @Test
    public void testToString() {
    }

    @Test
    public void getBoard() {
    }

    @Test
    public void getPlayersTurn() {
    }

    @Test
    public void getPlayerID() {
    }

    @Test
    public void getTimer() {
    }

    @Test
    public void getPhase() {
    }

    @Test
    public void getRemainingShips() {
    }

    @Test
    public void setPhase() {
    }

    @Test
    public void setPlayersTurn() {
    }

    @Test
    public void checkPlayerFleet() {
    }

    @Test
    public void updateNumPlayerFleet() {
    }

    @Test
    public void getPlayersFleet() {
    }

    @Test
    public void getPlayersBoard() {
    }

}
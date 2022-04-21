package edu.up.cs301.battleship;

import static org.junit.Assert.*;

import org.junit.Test;

public class BattleShipGameStateTest {

    @Test
    public void setHasShip() {
        BattleShipGameState bgs = new BattleShipGameState();
        bgs.setHasShip(2, 10, false, 0);
        boolean hasShip = bgs.getPlayersBoard()[0].getHasShip(2, 10);
        assertTrue(hasShip);
    }

    @Test
    public void canFire() {

    }

    @Test
    public void placeShip() {
    }

    @Test
    public void printFire() {
    }

    @Test
    public void printPlaceShip() {
    }

    @Test
    public void setPlayersFleet() {
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
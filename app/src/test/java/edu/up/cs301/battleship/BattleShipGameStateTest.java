package edu.up.cs301.battleship;

import static org.junit.Assert.*;

import android.util.Log;

import org.junit.Test;

public class BattleShipGameStateTest {


    @Test
    public void canFire() throws Exception {
        BattleShipGameState gameState = new BattleShipGameState();
        Coordinates coord = new Coordinates(false, true, 0, 0);

        boolean fire = gameState.canFire(coord);
        assertTrue(fire);
    }

    @Test
    public void placeShip() throws Exception {
        BattleShipGameState gameState = new BattleShipGameState();
        Coordinates coord1 = new Coordinates(false, true, 0, 0);
        Coordinates coord2 = new Coordinates(false, true, 0, 1);
        Coordinates coord3 = new Coordinates(false, true, 0, 2);
        Coordinates[] ship = new Coordinates[3];
        ship[0] = coord1;
        ship[1] = coord2;
        ship[2] = coord3;
        BattleshipObj bsj = new BattleshipObj(3, ship);
        assertTrue(gameState.placeShip(gameState.getPlayersFleet(), bsj, 0));
    }

    @Test
    public void printFire() throws Exception {
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
    public void xyToCoordMidGame() throws Exception {
        BattleShipGameState gameState = new BattleShipGameState();
        Coordinates testCoord = new Coordinates(gameState.xyToCoordMidGame
                (750.0f, 200.0f));
        Coordinates coord = new Coordinates(false, false, 0, 0 );
        assertEquals(coord.getX(), testCoord.getX());
        assertEquals(coord.getY(), testCoord.getY());
    }

    @Test
    public void xyToCoordSetupGame() throws Exception {
        BattleShipGameState gameState = new BattleShipGameState();
        Coordinates testCoord = new Coordinates(gameState.xyToCoordSetupGame(750.0f, 200.0f));
        assertEquals(testCoord.getX(), 0);
        assertEquals(testCoord.getY(), 0);
    }

    @Test
    public void middleXOfCoord() throws Exception {
        BattleShipGameState gameState = new BattleShipGameState();
        Coordinates coord = new Coordinates(false, false, 4, 5);
        float x = gameState.middleXOfCoord(coord);
        assertEquals(1044.1155, x, 0.005);
    }

    @Test
    public void middleYOfCoord() throws Exception {
        BattleShipGameState gameState = new BattleShipGameState();
        Coordinates coord = new Coordinates(false, false, 6, 9);
        float y = gameState.middleYOfCoord(coord);
        assertEquals(888.37309, y, 0.005);
    }

    @Test
    public void middleXOfEnemyBoard() throws Exception {
        BattleShipGameState gameState = new BattleShipGameState();
        Coordinates coord = new Coordinates(false, false, 7, 8);
        float x = gameState.middleXOfEnemyBoard(coord);
        assertEquals(353.5, x, 0.005);
    }

    @Test
    public void middleYOfEnemyBoard() throws Exception {
        BattleShipGameState gameState = new BattleShipGameState();
        Coordinates coord = new Coordinates(false, false, 9, 1);
        float y = gameState.middleYOfEnemyBoard(coord);
        assertEquals(712, y, 0.005);
    }

    @Test
    public void testToString() throws Exception {
        BattleShipGameState gameState = new BattleShipGameState();
        gameState.setPlayersTurn(1);
        String message = gameState.toString();
        System.out.println(message);
        gameState.getPlayersFleet()[1][1].setSunk(false);
        gameState.getPlayersFleet()[1][5].setSunk(true);
        message = gameState.toString();
        System.out.println(message);
        gameState.setPlayersTurn(0);
        message = gameState.toString();
        System.out.println(message);
    }

    @Test
    public void checkPlayerFleet() throws Exception {
        BattleShipGameState gameState = new BattleShipGameState();
        BattleshipObj[][] ships = gameState.getPlayersFleet();
        ships[1][0].setSunk(false);
        ships[1][1].setSunk(true);
        ships[1][2].setSunk(false);
        ships[1][3].setSunk(true);
        ships[1][4].setSunk(false);
        ships[1][5].setSunk(true);
        gameState.setPlayersFleet(ships, 1);
        assertEquals(2, gameState.checkPlayerFleet());
    }

}
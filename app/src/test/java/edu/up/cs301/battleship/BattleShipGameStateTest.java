package edu.up.cs301.battleship;

import static org.junit.Assert.*;

import android.util.Log;

import org.junit.Test;

public class BattleShipGameStateTest {


    @Test
    public void setHasShip() throws Exception {
        BattleShipGameState bgs = new BattleShipGameState();
        bgs.setHasShip(2, 9, false, 0);
        boolean hasShip = bgs.getPlayersBoard()[0].getHasShip(2, 9);
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
    public void placeShip() throws Exception {
        BattleShipGameState gameState = new BattleShipGameState();
        Coordinates coord1 = new Coordinates(false, true, 3, 6);
        Coordinates coord2 = new Coordinates(false, true, 3, 7);
        Coordinates coord3 = new Coordinates(false, true, 3, 8);
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
    public void setPlayersFleet() throws Exception {
        BattleShipGameState gameState = new BattleShipGameState();
        BattleshipObj[][] playerFleet = new BattleshipObj[2][6];
        Coordinates coord1 = new Coordinates(false, true, 3, 6);
        Coordinates coord2 = new Coordinates(false, true, 3, 7);
        Coordinates coord3 = new Coordinates(false, true, 3, 8);
        Coordinates[] ship = new Coordinates[3];
        ship[0] = coord1;
        ship[1] = coord2;
        ship[2] = coord3;
        BattleshipObj battleshipObj1 = new BattleshipObj(3, ship);
        playerFleet[1][0] = battleshipObj1;
        Coordinates coord4 = new Coordinates(false, true, 6, 3);
        Coordinates coord5 = new Coordinates(false, true, 5, 3);
        Coordinates coord6 = new Coordinates(false, true, 7, 3);
        Coordinates[] ship2 = new Coordinates[3];
        ship2[0] = coord4;
        ship2[1] = coord5;
        ship2[2] = coord6;
        BattleshipObj battleshipObj2 = new BattleshipObj(3, ship2);
        playerFleet[0][3] = battleshipObj2;
        gameState.setPlayersFleet(playerFleet, 1 );
        assertEquals(battleshipObj1, gameState.getPlayersFleet()[1][0]);
        assertEquals(battleshipObj2, gameState.getPlayersFleet()[0][3]);
    }

    @Test
    public void xyToCoordMidGame() throws Exception {
        BattleShipGameState gameState = new BattleShipGameState();
        gameState.xyToCoordMidGame(750.0f, 200.0f);
        //int x = coord.getX();
        //int y = coord.getY();
        //Log.d("Coordinates", "(" + x + ", " + y + ")");
    }

    @Test
    public void xyToCoordSetupGame() throws Exception {
        BattleShipGameState gameState = new BattleShipGameState();
        gameState.xyToCoordSetupGame(750.0f, 200.0f);
        //int x = coord.getX();
        //int y = coord.getY();
        //Log.d("Coordinates", "(" + x + ", " + y + ")");
    }

    @Test
    public void middleXOfCoord() throws Exception {
        BattleShipGameState gameState = new BattleShipGameState();
        Coordinates coord = new Coordinates(false, false, 4, 5);
        float x = gameState.middleXOfCoord(coord);
    }

    @Test
    public void middleYOfCoord() throws Exception {
        BattleShipGameState gameState = new BattleShipGameState();
        Coordinates coord = new Coordinates(false, false, 6, 9);
        float y = gameState.middleYOfCoord(coord);
    }

    @Test
    public void middleXOfEnemyBoard() throws Exception {
        BattleShipGameState gameState = new BattleShipGameState();
        Coordinates coord = new Coordinates(false, false, 7, 8);
        float x = gameState.middleXOfEnemyBoard(coord);
    }

    @Test
    public void middleYOfEnemyBoard() throws Exception {
        BattleShipGameState gameState = new BattleShipGameState();
        Coordinates coord = new Coordinates(false, false, 9, 1);
        float y = gameState.middleXOfEnemyBoard(coord);
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
    public void getBoard() throws Exception {
        BattleShipGameState gameState = new BattleShipGameState();
        GameBoard player0 = gameState.getBoard(0);
        player0.setHasShip(4, 8, true);
        assertTrue(player0.getHasShip(4, 8));
        GameBoard player1 = gameState.getBoard(1);
        assertFalse(player1.getHasShip(4, 8));
    }

    @Test
    public void getPlayersTurn() throws Exception {
        BattleShipGameState gameState = new BattleShipGameState();
        gameState.setPlayersTurn(1);
        assertEquals(1, gameState.getPlayersTurn());
        gameState.setPlayersTurn(0);
        assertEquals(0, gameState.getPlayersTurn());
    }

    @Test
    public void getPlayerID() throws Exception {
        BattleShipGameState gameState = new BattleShipGameState();
        gameState.getPlayerID();
    }

    @Test
    public void getTimer() throws Exception {
        BattleShipGameState gameState = new BattleShipGameState();
        int time = gameState.getTimer();
        assertEquals(30, time);
    }

    @Test
    public void getPhase() throws Exception {
        BattleShipGameState gameState = new BattleShipGameState();
        // setup phase
        gameState.setPhase(0);
        assertEquals(0, gameState.getPhase());
        // game phase
        gameState.setPhase(1);
        assertEquals(1, gameState.getPhase());
        // end phase
        gameState.setPhase(2);
        assertEquals(2, gameState.getPhase());
    }

    @Test
    public void getRemainingShips() throws Exception {
        BattleShipGameState gameState = new BattleShipGameState();
        assertEquals(6, gameState.getRemainingShips(1));
    }

    @Test
    public void setPhase() throws Exception {
        BattleShipGameState gameState = new BattleShipGameState();
        gameState.setPhase(0);
        assertEquals(0, gameState.getPhase());
        gameState.setPhase(2);
        assertEquals(2, gameState.getPhase());
    }

    @Test
    public void setPlayersTurn() throws Exception {
        BattleShipGameState gameState = new BattleShipGameState();
        gameState.setPlayersTurn(0);
        assertEquals(0, gameState.getPlayersTurn());
        gameState.setPlayersTurn(1);
        assertEquals(1, gameState.getPlayersTurn());
    }

    @Test
    public void checkPlayerFleet() throws Exception {
        BattleShipGameState gameState = new BattleShipGameState();
        gameState.getPlayersFleet()[1][0].setSunk(true);
        gameState.getPlayersFleet()[1][1].setSunk(true);
        gameState.getPlayersFleet()[1][2].setSunk(true);
        gameState.getPlayersFleet()[1][3].setSunk(true);
        gameState.getPlayersFleet()[1][4].setSunk(true);
        gameState.getPlayersFleet()[1][5].setSunk(true);
        assertEquals(0, gameState.checkPlayerFleet());
    }

    @Test
    public void updateNumPlayerFleet() throws Exception {
        BattleShipGameState gameState = new BattleShipGameState();
        gameState.getPlayersFleet()[0][2].setSunk(true);
        gameState.updateNumPlayerFleet();
        assertEquals(5, gameState.getRemainingShips(0));
    }

    @Test
    public void getPlayersFleet() throws Exception {
        BattleShipGameState gameState = new BattleShipGameState();
        BattleshipObj[][] playerFleet = new BattleshipObj[2][6];
        Coordinates coord1 = new Coordinates(false, true, 3, 6);
        Coordinates coord2 = new Coordinates(false, true, 3, 7);
        Coordinates coord3 = new Coordinates(false, true, 3, 8);
        Coordinates[] ship = new Coordinates[3];
        ship[0] = coord1;
        ship[1] = coord2;
        ship[2] = coord3;
        BattleshipObj battleshipObj1 = new BattleshipObj(3, ship);
        playerFleet[1][0] = battleshipObj1;
        gameState.setPlayersFleet(playerFleet, 1);
        assertEquals(battleshipObj1, gameState.getPlayersFleet()[1][0]);
    }

    @Test
    public void getPlayersBoard() throws Exception {
        BattleShipGameState gameState = new BattleShipGameState();
        GameBoard[] board = gameState.getPlayersBoard();
        board[0].setHasShip(5, 7, true);
        assertTrue(board[0].getHasShip(5, 7));
        assertFalse(board[1].getHasShip(5, 7));
    }
}
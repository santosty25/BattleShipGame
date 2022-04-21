package edu.up.cs301;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.up.cs301.battleship.BattleShipGameState;
import edu.up.cs301.battleship.BattleshipObj;
import edu.up.cs301.battleship.Coordinates;
import edu.up.cs301.battleship.GameBoard;

public class BattleShipGameStateTest {

    @Test
    public void BattleShipGameState(BattleShipGameState copy) {
        int idArray[] = {0,1};
        GameBoard playersBoard = new GameBoard();
        BattleshipObj playersFleet[][] = new BattleshipObj[idArray.length][6];
        BattleShipGameState original = new BattleShipGameState();

        BattleShipGameState copyGameState = new BattleShipGameState(original);
    }

    @Test
    public void setHasShip() throws Exception {

    }

    @Test
    public void canFire() {
        BattleShipGameState gameState = new BattleShipGameState();
        Coordinates coord = new Coordinates(false, true, 4, 5);

        boolean fire = gameState.canFire(coord);
        assertTrue(fire);

    }


    @Test
    public void placeShip() {
        BattleShipGameState gameState = new BattleShipGameState();

        Coordinates[][] board = new Coordinates[10][10]; //Creating a 10 x 10 grid
        for (int i = 0; i < 10; i++)  {
            for(int j = 0; j < 10; j++){
                board[i][j] = new Coordinates(false, false, i + 1, j + 1);
            }
        }
        Coordinates[] coords = new Coordinates[5];
        for(int i = 0; i < 5; i++) {
            coords[i] = new Coordinates(board[4 + i][5]);//creates a list of coords for a battle ship object to hold
            coords[i].setX(5 + i);
            coords[i].setY(5);
        }
        BattleshipObj battleship = new BattleshipObj(5, coords);

        BattleshipObj[][] fleet1 = {battleship,battleship,battleship,battleship,battleship,battleship};
        BattleshipObj[] fleet2 = {battleship,battleship,battleship,battleship,battleship,battleship};
        gameState.setPlayersFleet(fleet1, fleet2);

        boolean place = gameState.placeShip(battleship, coords);
        assertTrue(place);
    }

    @Test
    public void printFire() {
        BattleShipGameState gameState = new BattleShipGameState();
        Coordinates coord = new Coordinates(false, false, 4, 5);
        assertNotNull(gameState.printFire(4,5,true));

        // Tests to see what happens if canfire is false and someone clicks it
        Coordinates coordTwo = new Coordinates(false, false, 4, 5);
        assertNotNull(gameState.printFire(100,100,false));
    }

    @Test
    public void printPlaceShip() {
        //checks what happens if someone places out of bounds
        BattleShipGameState gameState = new BattleShipGameState();
        Coordinates shipOne = new Coordinates(false, false, 50,50);
        Coordinates shipTwo = new Coordinates(false, false, 50,50);
        Coordinates ships[] = {shipOne, shipTwo};
        assertNotNull(gameState.printPlaceShip(ships, false));

        // Tests to see what happens if canfire is false and someone clicks it
        assertNotNull(gameState.printFire(100,100,false));
    }

    @Test
    public void setPlayersFleet() throws Exception {

    }

    @Test
    public void xyToCoordMidGame() throws Exception {

    }

    @Test
    public void xyToCoordSetupGame() throws Exception {

    }

    @Test
    public void middleXOfCoord() throws Exception {

    }

    @Test
    public void middleYOfCoord() throws Exception {

    }

    @Test
    public void middleXOfEnemyBoard() throws Exception {

    }

    @Test
    public void middleYOfEnemyBoard() throws Exception {

    }

    @Test
    public void testToString() throws Exception {

    }

    @Test
    public void getBoard() throws Exception {

    }

    @Test
    public void getPlayersTurn() throws Exception {

    }

    @Test
    public void getPlayerID() throws Exception {

    }

    @Test
    public void getTimer() throws Exception {

    }

    @Test
    public void getPhase() throws Exception {

    }

    @Test
    public void getRemainingShips() throws Exception {

    }

    @Test
    public void setPhase() throws Exception {

    }

    @Test
    public void setPlayersTurn() throws Exception {

    }

    @Test
    public void checkPlayerFleet() throws Exception {

    }

    @Test
    public void updateNumPlayerFleet() throws Exception {

    }

    @Test
    public void getPlayersFleet() throws Exception {

    }

}

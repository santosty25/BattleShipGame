package edu.up.cs301.battleship;

import android.app.GameManager;
import android.util.Log;

import edu.up.cs301.game.GameFramework.infoMessage.GameState;

//import androidx.appcompat.widget.AppCompatRadioButton$InspectionCompanion;

/**
 * BattleShipGameState - Contains the state of a BattleShipGame. Sent by the game
 * when a play wants to enquire about the state of the game.
 *
 * @author Austen Furutani
 * @author Keoni Han
 * @author Steven Lee
 * @author Tyler Santos
 * @version Spring 2022 - 2/22/22
 */
public class BattleShipGameState extends GameState {
    private int[] playerID; //an array of each player's ID
    private GameBoard playersBoard; //the Battleship game board
    private int playersTurn; //determines who's turn it is
    private int timer; //a set timer for how long each turn should take
    // 0 = setup | 1 = game phase | 2 = end phase
    private int phase; //determines the phase of the game
    private int remainingShips; //a player's remaining ships
    private BattleshipObj[][] playersFleet; //a player's fleet


    /**
     * BattleShipGameState - Constructor that initializes the variables.
     */
    public BattleShipGameState(){
        this.playerID = new int[]{0,1};
        //Log.i("BSG", "Made playerID");
        this.playersBoard = new GameBoard();
        //Log.i("BSG", "Made gameBoard");
        int num = (int) Math.random() * 1;
        this.playersTurn = num;
        //Log.i("BSG", "Made player turn");
        this.timer = 30;
        //Log.i("BSG", "Made timer");
        this.remainingShips = 6;
        this.playersFleet = new BattleshipObj[2][6];
        //Log.i("BSG", "fleet");
    }

    /**
     * BattleShipGameState - Basic constructor that intializes instance variables
     * with arguments.
     * @param playerID - player's ID
     * @param playersBoard - player's board
     * @param playersTurn - who's turn it is
     * @param timer - the timer of the game
     * @param phase - what phase of the game is it
     * @param remainingShips - a player's remaining ships
     * @param playersFleet - a player's fleet of ships
     */
    public BattleShipGameState(int[] playerID, GameBoard playersBoard, int playersTurn, int timer,
                               int phase, int remainingShips, BattleshipObj[][] playersFleet) {
        this.playerID = playerID;
        this.playersBoard = playersBoard;
        this.playersTurn = playersTurn;
        this.timer = timer;
        this.phase = phase;
        this.remainingShips = remainingShips;
        this.playersFleet = playersFleet;
    }

    /**
     * BattleShipGameState - A deep copy constructor of the
     * BattleShipGameState
     * @param copy - A copy of the original BattleShipGameState
     */
    public BattleShipGameState(BattleShipGameState copy) {
        this.playerID = new int[2];
        for(int k = 0; k < 2; k++){
            this.playerID[k] = copy.playerID[k];
        }
        this.playersBoard = new GameBoard(copy.playersBoard);
        this.playersTurn = copy.playersTurn;
        this.timer = copy.timer;
        this.phase = copy.phase;
        this.remainingShips = copy.remainingShips;
        this.playersFleet = new BattleshipObj[playerID.length][6];

        int i;
        int j;
        Log.i("Test", "before Players fleet for loop");
        for (i = 0;  i < playerID.length; i++) {
            for (j = 0;j < 6; j++) {
                Log.i("Test", i + " " + j);
                if(copy.playersFleet[i][j] == null){
                    Log.i("IS NULL", "NULL");
                }
                else {
                    this.playersFleet[i][j] = new BattleshipObj(copy.playersFleet[i][j]);
                }
            }
        }
        Log.i("Test", "after Players fleet for loop");

    }

    /**
     * canFire - a method that let's a player fire at a specific coordinate. If it
     * returns true, the player can fire and will set the coordinate as if it were hit.
     * If it returns false, the player can't fire.
     * @param coord - the coordinate where the player wants to fire at
     * @return true or false depending on whether the move is valid
     */
    public boolean canFire(Coordinates coord) {
        int row = coord.getX();
        int col = coord.getY();

        boolean isHit = this.playersBoard.getCoordHit(row, col);
        if (isHit == true) {
            return false;
        }
        else {
            this.playersBoard.setCoordHit(row, col, true);
            //this.printFire(row, col, true);
            return true;
        }
    }

    /**
     * placeShip - Checks to see if the coordinates the player has picked
     * are in bounds of board and a ship isn't already placed there
     * @param ship
     * @param toPlace
     * @return true or false depnding on whether the player can place a ship on the board
     */
    public boolean placeShip(BattleshipObj ship, Coordinates[] toPlace) {
        int i;
        for (i = 0; i < toPlace.length; i++) {
            if (toPlace[i].getX() <= 10 && toPlace[i].getY() <= 10 &&
                    toPlace[i].getX() >= 1 && toPlace[i].getY() >= 1) {
                ship.setLocation(toPlace);

                return true;
            }
        }
        return false;
    }

    /**
     * printFire - Prints where a player has fired at on the board.
     * @param row - a given row coord
     * @param col - a given colum coord
     * @param canFire - a boolean that determines if a player can fire at the given coord
     * @return String of where the player has fired at on the board
     */
    public String printFire(int row, int col, boolean canFire) {
        /**
         External Citation
         Date: 25 February 2022
         Problem: Wanted to put each character in a String into a char array
         in an easier way
         Resource:
         https://www.geeksforgeeks.org/convert-a-string-to-character-array-in-java/
         Solution: I used the example code from this post.
         */
        char[] letters = "ABCDEFGHIJ".toCharArray();
        if(canFire == true) {
            char rowLetter = letters[row];
            return playerID[this.playersTurn] + " fired at x: " + rowLetter + " & y: " + col + ".";
        }
        return "Error";
    }

    /**
     * printPlaceShip - Prints where a player has placed a ship.
     * @param coords - given coordinates where the ship has been placed
     * @param canPlaceShip - boolean stating whether a ship can be placed
     * @return String of where the player placed the ship
     */
    public String printPlaceShip(Coordinates[] coords, boolean canPlaceShip) {
        /**
         External Citation
         Date: 25 February 2022
         Problem: Wanted to put each character in a String into a char array
         in an easier way
         Resource:
         https://www.geeksforgeeks.org/convert-a-string-to-character-array-in-java/
         Solution: I used the example code from this post.
         */
        char[] letters = "ABCDEFGHIJ".toCharArray();
        if(canPlaceShip == true) {
            int firstRow = coords[0].getX();
            int firstCol = coords[0].getY();
            char firstRowLetter = letters[firstRow];

            int lastRow = coords[coords.length - 1].getX();
            int lastCol = coords[coords.length - 1].getY();
            char lastRowLetter = letters[lastRow];
            return playerID[this.playersTurn] + " placed a ship from x: " + firstRowLetter + " & y: " +
                    firstCol + " to x: " + lastRowLetter + " & y: " + lastCol + ".";
        }
        return "Error";
    }

    /**
     * setPlayersFleet - Sets each player's fleet with given ships
     * @param playerOneShips - an array of BattleshipObj that player 1 has
     * @param playerZeroShips - an array of BattleshipObj that player 0 has
     */
    public void setPlayersFleet(BattleshipObj[] playerOneShips, BattleshipObj[] playerZeroShips){
        for(int i = 0; i < playerZeroShips.length; i++){
            this.playersFleet[0][i] = new BattleshipObj(playerZeroShips[i]);
        }
        for(int i = 0; i < playerOneShips.length; i++){
            this.playersFleet[1][i] = new BattleshipObj(playerOneShips[i]);
        }
    }

    /**
     * xyToCoordMidGame - Returns a coordinate object based on where the player taps on the enemies mid game board
     * @param touchX - float x coordinate of user tap
     * @param touchY - float y coordinate of user tap
     * @return coordinate object based on board
     */
    public Coordinates xyToCoordMidGame(float touchX, float touchY) {
        //Top left corner of board/grid
        float boardStartX = 713;
        float boardStartY = 185;
        float cellWidth = 74;
        char boardRows[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
        int newX = (int)((touchX - boardStartX) / cellWidth);
        int newY = (int)((touchY - boardStartY) / cellWidth);

        Coordinates tappedCoordinate = new Coordinates(false, false, newX, newY);
        char letterRow = boardRows[newY];
        if (newX >= 10 || newX < 0 || newY >= 10 || newX <= 0)  {
            return null;
        }
        Log.d("Converted Coords", "New Coords:" + newX + ", " + letterRow);
        return tappedCoordinate;
    }

    /** toString - Returns the number of ships remaining for the player if it is their turn or
     * returns that it is not their turn.
     */
    @Override
    public String toString() {
        if (playersTurn == this.playersTurn) {
            return "It is Player " + playerID[this.playersTurn] + "'s turn. They have " + remainingShips + " remaining ships.";
        }
        return "It is not " + playerID[this.playersTurn] + "'s turn.";
    }

    public GameBoard getBoard() {
        return this.playersBoard;
    }

    public int getPlayersTurn(){
        return this.playersTurn;
    }

    public int getPlayerID() { return this.playerID[playersTurn]; }

    public int getTimer() { return this.timer; }

    public int getPhase() { return this.phase; }

    public int getRemainingShips() { return this.remainingShips;}

    public void setPhase(int changePhase) {this.phase = changePhase;}

    public void setPlayersTurn(int initTurn) { this.playersTurn = initTurn;}

    /**
     * checkPlayerFleet - Checks the state of each players fleet.
     * @return - 0 if player 0's fleet still remains
     *           1 if player 1's fleet still remains
     *           2 if both players' fleet still remain
     */
    public int checkPlayerFleet() {
        boolean allSunk0 = false;
        boolean allSunk1 = false;

        //check the state of each player's fleet
        for(int i = 0; i < playersFleet[0].length; i++) {
            allSunk0 = playersFleet[0][i].getSunk();
        }
        for(int j = 0; j < playersFleet[1].length; j++) {
            allSunk1 = playersFleet[1][j].getSunk();
        }

        //player 1 won
        if(allSunk0 == true && allSunk1 == false) {
            return 1;
        }
        //player 0 won
        else if(allSunk0 == false && allSunk1 == true) {
            return 0;
        }
        //game is not over
        return 2;
    }








}
package edu.up.cs301.battleship;
import java.io.Serializable;
import edu.up.cs301.game.GameFramework.infoMessage.GameState;


/**
 * BattleShipGameState - Contains the state of a BattleShipGame. Sent by the game
 * when a play wants to enquire about the state of the game.
 *
 * @author Austen Furutani
 * @author Keoni Han
 * @author Steven Lee
 * @author Tyler Santos
 * @version Spring 2022 - 4/14/22
 */
public class BattleShipGameState extends GameState implements Serializable {
    public static final int SETUP_PHASE = 0;
    public static final int BATTLE_PHASE = 1;
    public static final int END_PHASE = 2;

    private static final long serialVersionUID = 040420026l;

    private int[] playerID; //an array of each player's ID
    private GameBoard[] playersBoard; //the Battleship game board for each player
    private int playersTurn; //determines who's turn it is
//    private int timer; //a set timer for how long each turn should take
    // 0 = setup | 1 = game phase | 2 = end phase
    private int phase; //determines the phase of the game
//    private int remainingShips[]; //a player's remaining ships
    private BattleshipObj[][] playersFleet; //a player's fleet


    /**
     * BattleShipGameState - Constructor that initializes the variables.
     */
    public BattleShipGameState(){
        this.playerID = new int[2];
        this.playerID[0] = 0;
        this.playerID[1] = 1;

        this.playersBoard = new GameBoard[2];
        this.playersBoard[0] = new GameBoard();
        this.playersBoard[1] = new GameBoard();

        //Log.i("BSG", "Made gameBoard");
        int num = (int) Math.random() * 1;
        this.playersTurn = num;
        this.phase = 0;
        this.playersFleet = new BattleshipObj[2][6];

        /** FOR TESTING HARD CODING SHIPS AND LOCATION TO PREVENT NULL EXCEPTION*/
        Coordinates[] locations = new Coordinates[1];
        locations[0] = new Coordinates(true, true, -1, -1);
        int i, j;
        for(i = 0; i < 2; i++){
            for(j = 0; j < 6; j++){
                BattleshipObj testShip = new BattleshipObj(1, locations);
                testShip.setSunk(false);
                playersFleet[i][j] = new BattleshipObj(testShip);
            }
        }
    }

    /**
     * BattleShipGameState - A deep copy constructor of the
     * BattleShipGameState
     * @param copy - A copy of the original BattleShipGameState
     */
    public BattleShipGameState(BattleShipGameState copy) {
        //change so that certain information doesn't get sent to a specific player
        if (copy != null) {
            this.playerID = new int[2];

            for (int k = 0; k < 2; k++) {
                this.playerID[k] = copy.playerID[k];
            }
            this.playersBoard = new GameBoard[2];
            for (int l = 0; l < 2; l++) {
                this.playersBoard[l] = new GameBoard(copy.playersBoard[l]);
            }
            this.playersTurn = copy.playersTurn;
            this.phase = copy.phase;
            this.playersFleet = new BattleshipObj[2][6];

            int i, j;
            for (i = 0; i < 2; i++) {
                for (j = 0; j < 6; j++) {
                    this.playersFleet[i][j] = new BattleshipObj(copy.playersFleet[i][j]);
                    Coordinates[] arrayTest = this.playersFleet[i][j].getLocation();
                    int k;
                    for (k = 0; k < arrayTest.length; k++) {
                    }
                }
            }
        }
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
        if(playersTurn == 0) {
            boolean isHit = this.playersBoard[1].getCoordHit(row, col);
            if (isHit == true) {
                return false;
            } else {
                this.playersBoard[1].setCoordHit(row, col, true);
                //check if a hit on a coordinate matches the coordinate of one of the player's ships
                for(int j = 0; j < playersFleet[1].length; j++) {
                    this.playersFleet[1][j].checkCoordHit(coord);
                }
                for(int i = 0; i < playersFleet[1].length; i++) {
                    //checks if a player's ship has been sunk and sets it as sunk if true
                    boolean sunk = this.playersFleet[1][i].checkIfHit();
                    if(sunk == true) {
                        this.setFleetSunk(1, i);
                    }
                    else {
                        continue;
                    }
                }
                return true;
            }
        }
        else if (playersTurn == 1){
            boolean isHit = this.playersBoard[0].getCoordHit(row, col);
            if (isHit == true) {
                return false;
            } else {
                this.playersBoard[0].setCoordHit(row, col, true);
                //check if a hit on a coordinate matches the coordinate of one of the player's ships
                for(int j = 0; j < playersFleet[0].length; j++) {
                    this.playersFleet[0][j].checkCoordHit(coord);
                }
                for(int i = 0; i < playersFleet[0].length; i++) {
                    boolean sunk = this.playersFleet[0][i].checkIfHit();
                    //checks if a player's ship has been sunk and sets it as sunk if true
                    if(sunk == true) {
                        this.setFleetSunk(0, i);
                    }
                    else {
                        continue;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * placeShip - Checks to see if the coordinates the player has picked
     * doesnt already have another ship on it
     * @param currentFleet
     * @param placedShip
     * @param playerNum
     * @return true or false depnding on whether the player can place a ship on the board
     */
    public boolean placeShip(BattleshipObj[][] currentFleet, BattleshipObj placedShip,
                             int playerNum) {
        int i, j, k;
        for(j = 0; j < 6; j++){ //Grabs all 6 ships from the current players fleet
            if(currentFleet[playerNum][j] != null) {
                BattleshipObj onBoard = new BattleshipObj(currentFleet[playerNum][j]);
                for (i = 0; i < onBoard.getSize(); i++) { //size of the boats already placed
                    for (k = 0; k < placedShip.getSize(); k++) { //size of boat about to be placed
                        if (placedShip.getLocation()[k].getY() == onBoard.getLocation()[i].getY() &&
                                placedShip.getLocation()[k].getX() ==
                                        onBoard.getLocation()[i].getX()) {
                            //Checks if both ships are the same
                            if (!(placedShip.getSize() == onBoard.getSize() &&
                                    placedShip.getTwinShip() == onBoard.getTwinShip())){
                                return false;
                                //if any of the two ships coordinates are the same returns false
                            }
                        }
                    }
                }
            }
            else {
                continue;
            }
        }
        return true;
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
     * @param fleets - a 2d array of battleship objects
     */
    public void setPlayersFleet(BattleshipObj[][] fleets, int playerNum){
        int j, i;
        //clears the board
        GameBoard board = new GameBoard(this.playersBoard[playerNum]);
        for(j = 0; j < board.getCurrentBoard().length; j++){
            for(i = 0; i < board.getCurrentBoard()[j].length; i++){
                board.setHasShip(j, i, false);
            }
        }

        //Reads the ships location and sets the Coordinate to hasShip
        for(j = 0; j < 6; j++){
            this.playersFleet[playerNum][j] = new BattleshipObj(fleets[playerNum][j]);
            for(i = 0; i < this.playersFleet[playerNum][j].getSize(); i++){
                    int x = playersFleet[playerNum][j].getLocation()[i].getX();
                    int y = playersFleet[playerNum][j].getLocation()[i].getY();
                    if (x != -1 && y != -1) {
                        board.setHasShip(x, y, true);
                }
            }
        }
            this.playersBoard[playerNum] = new GameBoard(board);

        }


    /**
     * xyToCoordMidGame - Returns a coordinate object based on where the player
     * taps on the enemies mid game board
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
        if (newX >= 10 || newX < 0 || newY >= 10 || newY < 0)  {
            return null;
        }
        char letterRow = boardRows[newY];
        return tappedCoordinate;
    }

    /**
     * xyToCoordSetupGame - Returns a coordinate object based on where the player
     * taps on the setup board and creates a coordianate
     * with a has ship boolean
     * @param touchX - float x coordinate of user tap
     * @param touchY - float y coordinate of user tap
     * @return coordinate object based on board
     *
     */
    public static Coordinates xyToCoordSetupGame(float touchX, float touchY) {
        //Top left corner of board/grid
        float boardStartX = 715.8f;
        float boardStartY = 185;
        float cellWidth = 75.5f;
        char boardRows[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
        int newX = (int)((touchX - boardStartX) / cellWidth);
        int newY = (int)((touchY - boardStartY) / cellWidth);

        Coordinates tappedCoordinate = new Coordinates(false, true, newX, newY);
        if (newX >= 10 || newX < 0 || newY >= 10 || newY < 0)  {
            return null;
        }
        char letterRow = boardRows[newY];
        return tappedCoordinate;
    }

    /**
     * middleXOfCoord - Returns a float value of X corresponding to the middle of a
     * selected coordinate on the grid these are based on indicies and not actual board
     * coordinates that a player might think
     * @param selected - coordinates of a selected grid on the board
     * @return float value of x that corresponds to the middle of that selected grid
     */
    public static float middleXOfCoord(Coordinates selected) {
        float squareWidth = 74.0356f;
        float pixelX = selected.getX();
        float middleX = ((pixelX * squareWidth) + squareWidth) - 37;
        middleX += 710.9375;
        return middleX;
    }

    /**
     * middleYOfCoord - Returns a float value of Y corresponding to the middle of a selected coordinate on the grid
     * these are based on indicies and not actual board coordinates that a player might think
     * @param selected - coordinates of a selected grid on the board
     * @return float value of y that corresponds to the middle of that selected grid
     */
    public static float middleYOfCoord(Coordinates selected) {
        float squareHeight = 74.0356f;
        float pixelY = selected.getY();
        float middleY = ((pixelY * squareHeight) + squareHeight) - 37;
        middleY += 185.01709;
        return middleY;
    }

    /**
     * middleXOfEnemyCoord - Returns a float value of X corresponding to the middle of a selected coordinate on the enemy's grid
     * these are based on indicies and not actual board coordinates that a player might think/read
     * @param selected - coordinates of a selected grid on the board
     * @return float value of x that corresponds to the middle of that selected grid
     */
    public float middleXOfEnemyBoard(Coordinates selected) {
        float squareWidth = 32;
        float pixelX = selected.getX();
        float middleX = ((pixelX * squareWidth) + squareWidth) - 16;
        middleX += 113.5;
        return middleX;
    }

    /**
     * middleYOfCoord - Returns a float value of Y corresponding to the middle of a selected coordinate on the enemy's grid
     * these are based on indicies and not actual board coordinates that a player might think/read
     * @param selected - coordinates of a selected grid on the board
     * @return float value of y that corresponds to the middle of that selected grid
     */
    public float middleYOfEnemyBoard(Coordinates selected) {
        float squareHeight = 32;
        float pixelY = selected.getY();
        float middleY = ((pixelY * squareHeight) + squareHeight) - 16;
        middleY += 664;
        return middleY;
    }

    /** toString - Returns the number of ships remaining for the player if it is their turn or
     * returns that it is not their turn.
     */
    @Override
    public String toString() {
        if (playersTurn == this.playersTurn) {
            String player0Fleet = "";
            String player1Fleet = "";
            for(int i = 0; i < playersFleet[0].length; i++) {
                player0Fleet = player0Fleet + " size: " + this.playersFleet[0][i].getSize()
                        + "Is Sunk: " + this.playersFleet[0][i].getSunk() + "\n";
            }
            for(int j = 0; j < playersFleet[1].length; j++) {
                player0Fleet = player0Fleet + " size: " + this.playersFleet[1][j].getSize()
                        + "Is Sunk: " + this.playersFleet[1][j].getSunk() + "\n";
            }
            //prints all variables
            return "It is Player " + playerID[this.playersTurn] + "'s turn.\n" +
                    "Player ID: " + playerID[0] + " and " + playerID[1] + "\n" +
                    "Phase: " + this.phase + "\n" +
                    "GameBoard: " + this.playersBoard + " \n"
                    + player0Fleet + "\n"
                    + player1Fleet;
        }
        return "It is not " + playerID[this.playersTurn] + "'s turn.";
    }

    /**
     * getBoard - Gets a specific player's boars.
     * @param playerNum
     * @return the player's board
     */
    public GameBoard getBoard(int playerNum) {
        return this.playersBoard[playerNum];
    }

    /**
     * getPlayersTurn - Gets whose turn it currently is.
     * @return - The player whose turn it is
     */
    public int getPlayersTurn(){
        return this.playersTurn;
    }

    /**
     * getPlayerID - Gets the playerID.
     * @return - The playerID of whose turn it is
     */
    public int getPlayerID() { return this.playerID[playersTurn]; }


    /**
     * getPhase - Gets the current phase of the game.
     * @return - The current phase
     */
    public int getPhase() { return this.phase; }


    /**
     * setPhase - Sets the phase of the game
     * @param changePhase - the given phase
     */
    public void setPhase(int changePhase) {
        this.phase = changePhase;
    }

    /**
     * setPlayersTurn - Sets the players turn.
     * @param initTurn - the player whose turn is next
     */
    public void setPlayersTurn(int initTurn) {
        playersTurn = initTurn;
    }

    /**
     * checkPlayerFleet - Checks the state of each players fleet.
     * @return - 0 if player 0's fleet still remains
     *           1 if player 1's fleet still remains
     *           2 if both players' fleet still remain
     */
    public int checkPlayerFleet() {
        boolean allSunk0;
        boolean allSunk1;
        int numSunkBoats0 = 0;
        int numSunkBoats1 = 0;

        //check the state of each player's fleet
        for (int i = 0; i < playersFleet[0].length; i++) {
            allSunk0 = playersFleet[0][i].getSunk();
            if (allSunk0 == true) {
                numSunkBoats0++;
            }
        }
        for (int j = 0; j < playersFleet[1].length; j++) {
            allSunk1 = playersFleet[1][j].getSunk();
            if (allSunk1 == true) {
                numSunkBoats1++;
            }
        }

        //player 1 won
        if (numSunkBoats0 == 6) {
            return 1;
        }
        //player 0 won
        else if (numSunkBoats1 == 6) {
            return 0;
        }
        //game is not over
        return 2;
    }

    /**
     * getPlayersFleet - gets the player's fleet
     * @return - the player's fleet
     */
    public BattleshipObj[][] getPlayersFleet() {
        return playersFleet;
    }
//push
    public GameBoard[] getPlayersBoard() {
        return playersBoard;
    }

    public void setFleetSunk(int playerNum, int shipNum) {
        this.playersFleet[playerNum][shipNum].setSunk(true);
    }

}
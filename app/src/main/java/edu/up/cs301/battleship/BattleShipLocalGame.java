package edu.up.cs301.battleship;



import edu.up.cs301.game.GameFramework.LocalGame;
import edu.up.cs301.game.GameFramework.actionMessage.GameAction;
import edu.up.cs301.game.GameFramework.players.GamePlayer;

/**
 * BattleShipLocalGame - A local game glass for a game of Battleship. This class handles
 * the game rules and interactions with the players.
 *
 * @author Tyler Santos
 * @author Austen Furutani
 * @author Keoni Han
 * @author Steven Lee
 * @version Spring 2022 - 4/22/22
 */
public class BattleShipLocalGame extends LocalGame {

    private BattleShipGameState localState;
    private boolean player0Ready = false;
    private boolean player1Ready = false;

    /**
     * BattleShipLocalGame - Constructor for the BattleShipLocalGame.
     */
    public BattleShipLocalGame() {
        // perform superclass initialization
        super();
        super.state = new BattleShipGameState();
    }

    /**
     * BattleShipLocalGame - Copy constructor for the local game.
     * @param battleshipState - a copy of the battleship gamestate.
     */
    public BattleShipLocalGame(BattleShipGameState battleshipState){
        super();
        super.state = new BattleShipGameState(battleshipState);
    }

    /**
     * sendUpdatedStateTo - Notify the given player that its state has changed.
     * This should involve sending a GameInfo object to the player. If the game is not a
     * perfect-information game this method should remove any information from the game that
     * the player is not allowed to know.
     * @param p - the player who's getting notified
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        // make a copy of the state, and send it to the player
        BattleShipGameState copy = new BattleShipGameState((BattleShipGameState)state);
        p.sendInfo(copy);
    }

    /**
     * canMove - Determines whether the player is able to make a move during a point in the game.
     * @param playerIdx
     * 		the player's player-number (ID)
     * @return true or false depending on if the move was legal
     */
    @Override
    protected boolean canMove(int playerIdx) {
        return playerIdx == ((BattleShipGameState) state).getPlayersTurn();
    }

    /**
     * checkIfGameOver - Determines whether the game is over or not.
     *
     * @return a String that states who won or null
     */
    @Override
    protected String checkIfGameOver() {
        BattleShipGameState gameState = (BattleShipGameState) super.state;
        int winner = gameState.checkPlayerFleet();
        if (winner == 0) {
            gameState.setPhase(BattleShipGameState.END_PHASE);
            return BattleShipMainActivity.player0Name + " has won. ";
        }
        else if (winner == 1) {
            gameState.setPhase(BattleShipGameState.END_PHASE);
            return BattleShipMainActivity.player1Name + " has won. ";
        }
        return null;
    }

    /**
     * makeMove - Makes a move for the player.
     *
     * @param action
     * 			The move that the player has sent to the game
     * @return true or false depending on whether the move was legal.
     */
    @Override
    protected boolean makeMove(GameAction action) {


        BattleShipGameState state = (BattleShipGameState) super.state; //the gameState
        int phase = state.getPhase(); //the phase
        int player = state.getPlayerID(); //the playerID
        int whoseTurn = state.getPlayersTurn(); //whose turn it is
        int enemy = 0;
        //checks whose turn it is and to set the enemy's board
        if (whoseTurn == 0) {
            enemy = 1;
        }

        if(action instanceof SwitchPhase) {
            int playerNum = ((SwitchPhase)action).getPlayerNum();
            boolean isReady = ((SwitchPhase)action).getIsReady();

            if(playerNum == 0 && isReady == true) {
                this.player0Ready = true;
            }
            else if (playerNum == 0 && isReady == true){
                this.player1Ready = true;
            }

            if(this.player0Ready == true || this.player1Ready == true) {
                state.setPhase(BattleShipGameState.BATTLE_PHASE);
                sendAllUpdatedState();
            }
        }

            if (action instanceof Fire) {
                fire((Fire) action, state);
                return true;
            } else if (action instanceof PlaceShip) {
                //Checks if its the setup phase
                if (phase != 0) {
                    return false;
                }
                BattleShipMainActivity.place.start();//plays a placing sound
                PlaceShip placeAction = new PlaceShip((PlaceShip) action);
                if (placeShip(placeAction, state)){
                    return true;
                }
            }
            return false;
        }

        /**
         * Takes the users ship, checks if its a valid ship and lcoation
         * adds the ship to their fleet
         * @param placeAction
         * @return
         */
        public boolean placeShip(PlaceShip placeAction, BattleShipGameState state){
            int playerNum = placeAction.getPlayerNum();
            BattleshipObj[][] currentFleet = new BattleshipObj[2][6];
            BattleshipObj newShip = new BattleshipObj(placeAction.getBattleship());
            int i, j;
            //Creates a local copy of both players boards
            for (i = 0;  i < 2; i++) {
                for (j =0; j < 6; j++){
                    if (state.getPlayersFleet()[i][j] != null) {
                        currentFleet[i][j] = new BattleshipObj(state.getPlayersFleet()[i][j]);
                    }
                }
            }
            //Checking if the any ships coordinates match, if they do its an illegal placement
            if( state.placeShip(currentFleet, newShip, placeAction.getPlayerNum()) == false){
                Coordinates[] newLocation = new Coordinates[1];
                Coordinates outOfBounds =  new Coordinates();
                outOfBounds.setX(-1);
                outOfBounds.setY(-1);
                newLocation[0] = outOfBounds;
                newShip.setSize(1);
                newShip.setLocation(newLocation);
            }
                if (newShip.getSize() == 5) { //Ship of size 5 is placed at index 0
                    currentFleet[playerNum][0] = new BattleshipObj(newShip);
                } else if (newShip.getSize() == 4) {
                    if (newShip.getTwinShip() == 0) {
                        //Because there are two ships of the same length we need to identify which is which
                        currentFleet[playerNum][1] = new BattleshipObj(newShip);
                    } else {
                        currentFleet[playerNum][2] = new BattleshipObj(newShip);
                    }
                } else if (newShip.getSize() == 3) {
                    if (newShip.getTwinShip() == 0) {
                        currentFleet[playerNum][3] = new BattleshipObj(newShip);
                    } else {
                        currentFleet[playerNum][4] = new BattleshipObj(newShip);
                    }
                } else if (newShip.getSize() == 2) {
                    currentFleet[playerNum][5] = new BattleshipObj(newShip);
                }
            int enemy;
            if (playerNum == 0) { //Determines the player and enemy number
                enemy = 1;
            } else {
                enemy = 0;
            }
            state.setPlayersTurn(enemy);
            state.setPlayersFleet(currentFleet, placeAction.getPlayerNum());
            return true;
        }


        /**
         * * Fires at a coordinate specified by the fireAction
         * Updates gameboard
         * If the hit is successful the player can fire again
         * @param fireAction
         * @param state
         * @return
         */
    public boolean fire(Fire fireAction, BattleShipGameState state) {
        Coordinates coord = new Coordinates(fireAction.getCoord());
        int playerNum = fireAction.getPlayerNum();
        int enemy;
        if (playerNum == 0) { //Determines the player and enemy number
            enemy = 1;
        } else {
            enemy = 0;
        }
        if(playerNum != state.getPlayersTurn()){
            sendAllUpdatedState();
            return false;
        }
        if (state.canFire(coord)) { //If the coord has NOT already been hit
            state.getBoard(enemy).setCoordHit(coord.getX(), coord.getY(), true); //SET THE COORDINATE TO HIT
            int i, j;
            BattleshipObj[][] shipsOnBoard = state.getPlayersFleet();
            for (i = 0; i < shipsOnBoard[enemy].length; i++) {
                for (j = 0; j < shipsOnBoard[enemy][i].getLocation().length; j++) {//Reads locations of opponents board
                    BattleShipSmartAI.AvoidLoop = 0;
                    if (shipsOnBoard[enemy][i].getLocation()[j].getX() == coord.getX() && shipsOnBoard[enemy][i].getLocation()[j].getY() == coord.getY()) {
                        //Checks if the coordinate sent with the fire action has a ship on it
                        //Draw red marker
                        //IT SHOULD STILL BE THE PLAYERS TURN
                        state.getBoard(enemy).setHasShip(coord.getX(), coord.getY(), true); //Sets the coord to hit
                        BattleShipMainActivity.explosion.start();
                        state.setPlayersTurn(playerNum);
                        sendAllUpdatedState();
                        return true;
                    }
                }
            }
            //DRAW WHITE the player missed
            state.setPlayersTurn(enemy);
            BattleShipMainActivity.splash.start();
            sendAllUpdatedState();
            return true;
        }
        sendAllUpdatedState();
        return false;
    }
}


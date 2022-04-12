    package edu.up.cs301.battleship;

import android.util.Log;

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
     * @version 3/31/22
     */
    public class BattleShipLocalGame extends LocalGame {

        private BattleShipGameState localState;
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
                return "Player 0 has won. ";
            }
            else if (winner == 1) {
                gameState.setPhase(BattleShipGameState.END_PHASE);
                return "Player 1 has won. ";
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

            Log.i("IN ACTION", "makeMove: ");
            BattleShipGameState state = (BattleShipGameState) super.state; //the gameState
            int phase = state.getPhase(); //the phase
            int player = state.getPlayerID(); //the playerID
            int whoseTurn = state.getPlayersTurn(); //whose turn it is
            int enemy = 0;
            //checks whose turn it is and to set the enemy's board
            if (whoseTurn == 0) {
                enemy = 1;
            }
            Log.i("Players turn ", "makeMove: " + whoseTurn);

            if(action instanceof Fire) {
                Log.i("fire action", "Instance of fire action ");
                    //get the coordinate given by the player and calls the fire method in gamestate
                    Coordinates coord = ((Fire) action).getCoord();
                    if (state.canFire(coord)) { //If the coord has NOT already been hit
                        state.getBoard(enemy).setCoordHit(coord.getX(), coord.getY(), true); //SET THE COORDINATE TO HIT
                        int i, j;
                        BattleshipObj[][] shipsOnBoard = state.getPlayersFleet();
                        Log.i("Players turn ", "makeMove: " + whoseTurn);
                        Log.i("Firing", " ");
                        if (whoseTurn == 0) {
                            for(i = 0; i < shipsOnBoard[1].length; i++){
                                Log.i("length", "ships " + shipsOnBoard[1].length);
                                for(j = 0; j < shipsOnBoard[1][i].getLocation().length; j++){//Reads locations of opponents board
                                    if(shipsOnBoard[1][i].getLocation()[j].getX() == coord.getX() && shipsOnBoard[1][i].getLocation()[j].getY() == coord.getY()){
                                        //Checks if the coordinate sent with the fire action has a ship on it
                                        //Draw red marker IT SHOULD STILL BE THE PLAYERS TURN
                                        Coordinates[][] enemyBoard = state.getBoard(1).getCurrentBoard();
                                        enemyBoard[coord.getX()][coord.getY()].setHasShip(true);
                                        Log.i("SUCCESSFUL SHOT", "At x: " + coord.getX() + " Y: " +  coord.getY());
                                        BattleShipMainActivity.explosion.start();
                                        state.setPlayersTurn(0);
                                        return true;
                                    }
                                }
                            }
                            //DRAW WHITE
                            Log.i("MISSED SHOT", "At x: " + coord.getX() + " Y: " +  coord.getY());
                            state.setPlayersTurn(1);
                            BattleShipMainActivity.splash.start();
                            return true;


                } else { //PLAYER 1's turn
                    for(i = 0; i < shipsOnBoard[0].length; i++){
                        Log.i("length", "ships " + shipsOnBoard[0].length);
                        for(j = 0; j < shipsOnBoard[0][i].getLocation().length; j++){ //Reads locations of opponents board
                            if(shipsOnBoard[0][i].getLocation()[j].getX() == coord.getX() && shipsOnBoard[0][i].getLocation()[j].getY() == coord.getY()){
                                //Checks if the coordinate sent with the fire action has a ship on it
                                //Draw red marker IT SHOULD STILL BE THE PLAYERS TURN
                                Log.i("SUCCESSFUL SHOT", "makeMove: ");
                                Coordinates[][] enemyBoard = state.getBoard(0).getCurrentBoard();
                                enemyBoard[coord.getX()][coord.getY()].setHasShip(true);
                                Log.i("SUCCESSFUL SHOT", "At x: " + coord.getX() + " Y: " +  coord.getY());
                                state.setPlayersTurn(1);
                                BattleShipMainActivity.explosion.start();
                                return true;
                            }
                        }
                    }
                            //DRAW WHITE
                            Log.i("MISSED SHOT", "At x: " + coord.getX() + " Y: " +  coord.getY());
                            BattleShipMainActivity.splash.start();
                            state.setPlayersTurn(0);
                        }
                        return true;
                    }
                }
            else if(action instanceof PlaceShip){

                Log.i("START OF PLACE SHIP", "");
                //set player's fleet
                PlaceShip placeAction = new PlaceShip((PlaceShip) action);
                BattleshipObj[][] currentFleet = new BattleshipObj[2][6];
                int i, j;

                //Creates a local copy of both players boards=
                for (i = 0;  i < 2; i++) {
                    for (j =0; j < 6; j++){
                        if (state.getPlayersFleet()[i][j] != null) {
                            currentFleet[i][j] = new BattleshipObj(state.getPlayersFleet()[i][j]);
                        }
                    }
                }
                //Checking if the any ships coordinates match, if they do its an illegal placement
                if( state.placeShip(currentFleet, placeAction.getBattleship(), placeAction.getPlayerNum()) == false){
                    return false;
                }
                if(placeAction.getPlayerNum() == 0) {
                    Log.i("MAKING MOVE", "makeMove: " + placeAction.getBattleship().getSize());
                    if (placeAction.getBattleship().getSize() == 5) { //Ship of size 5 is placed at index 0
                        Log.i("placing ship size: 0 ", "" + placeAction.getBattleship().getSize());
                        currentFleet[0][0] = new BattleshipObj(placeAction.getBattleship());
                    } else if (placeAction.getBattleship().getSize() == 4) {
                        if (placeAction.getBattleship().getTwinShip() == 0) {
                            //Because there are two ships of the same length we need to identify which is which
                            currentFleet[0][1] = new BattleshipObj(placeAction.getBattleship());
                        } else {
                            currentFleet[0][2] = new BattleshipObj(placeAction.getBattleship());
                        }
                    } else if (placeAction.getBattleship().getSize() == 3) {
                        if (placeAction.getBattleship().getTwinShip() == 0) {
                            currentFleet[0][3] = new BattleshipObj(placeAction.getBattleship());
                        } else {
                            currentFleet[0][4] = new BattleshipObj(placeAction.getBattleship());
                        }
                    } else if (placeAction.getBattleship().getSize() == 2) {
                        currentFleet[0][5] = new BattleshipObj(placeAction.getBattleship());
                    }
                    state.setPlayersFleet(currentFleet, placeAction.getPlayerNum());
                    BattleShipMainActivity.place.start();
                    return true;
                }
                else if(placeAction.getPlayerNum() == 1){
                    Log.i("MAKING MOVE", "makeMove: " + placeAction.getBattleship().getSize());
                    if (placeAction.getBattleship().getSize() == 5) { //Ship of size 5 is placed at index 0
                        Log.i("placing ship size: 0 ", "" + placeAction.getBattleship().getSize());
                        currentFleet[1][0] = new BattleshipObj(placeAction.getBattleship());
                    } else if (placeAction.getBattleship().getSize() == 4) {
                        if (placeAction.getBattleship().getTwinShip() == 0) {
                            //Because there are two ships of the same length we need to identify which is which
                            currentFleet[1][1] = new BattleshipObj(placeAction.getBattleship());
                        } else {
                            currentFleet[1][2] = new BattleshipObj(placeAction.getBattleship());
                        }
                    } else if (placeAction.getBattleship().getSize() == 3) {
                        if (placeAction.getBattleship().getTwinShip() == 0) {
                            currentFleet[1][3] = new BattleshipObj(placeAction.getBattleship());
                        } else {
                            currentFleet[1][4] = new BattleshipObj(placeAction.getBattleship());
                        }
                    } else if (placeAction.getBattleship().getSize() == 2) {
                        currentFleet[1][5] = new BattleshipObj(placeAction.getBattleship());
                    }
                    state.setPlayersFleet(currentFleet, placeAction.getPlayerNum());
                    BattleShipMainActivity.place.start();
                    return true;
                }
            }


            return false;
        }
    }

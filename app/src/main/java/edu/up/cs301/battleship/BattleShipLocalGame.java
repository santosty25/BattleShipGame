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
     * @version 3/22/22
     */
    public class BattleShipLocalGame extends LocalGame {
        private BattleShipGameState localState;
        //AHHHHHHHHHHHHHHHHHH
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
            super.state = new BattleShipGameState(battleshipState, battleshipState.getPlayersTurn());
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
            BattleShipGameState copy = new BattleShipGameState((BattleShipGameState)state, ((BattleShipGameState) state).getPlayersTurn());
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
            BattleShipGameState state = (BattleShipGameState) super.state;
            int winner = state.checkPlayerFleet();
            if (winner == 0) {
                return "Player 0 has won.";
            }
            else if (winner == 1) {
                return "Player 1 has won.";
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
            GameBoard board = state.getBoard(enemy); //the enemy's board
            //int remainingShips = state.getRemainingShips(); //the number of remaining ships
            Log.i("Players turn ", "makeMove: " + whoseTurn);

            if(action instanceof Fire) {
                Log.i("fire action", "Instance of fire action ");
//                if(phase != 1) {
//                    Log.i("test", " wrong phase " + phase);
//                    //checks if the phase is in battle phase
//                    return false;
//                }
                //else {
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
                                for(j = 0; j < shipsOnBoard[1][i].getLocation().length; j++){
                                    if(shipsOnBoard[1][i].getLocation()[j].getX() == coord.getX() && shipsOnBoard[1][i].getLocation()[j].getY() == coord.getY()){
                                        //Draw red marker IT SHOULD STILL BE THE PLAYERS TURN
                                        Coordinates[][] enemyBoard = state.getBoard(1).getCurrentBoard();
                                        enemyBoard[coord.getX()][coord.getY()].setHasShip(true);
                                        Log.i("SUCCESSFUL SHOT", "At x: " + coord.getX() + " Y: " +  coord.getY());
                                        state.setPlayersTurn(0);
                                        return true;
                                    }
                                }
                            }
                            //DRAW WHITE
                            Log.i("MISSED SHOT", "At x: " + coord.getX() + " Y: " +  coord.getY());
                            state.setPlayersTurn(1);
                            Log.i("Players turn UPDATED ", "setPlayersTurn: " + state.getPlayersTurn());
                            return true;


                        } else { //PLAYER 1's turn
                            for(i = 0; i < shipsOnBoard[0].length; i++){
                                Log.i("length", "ships " + shipsOnBoard[0].length);
                                for(j = 0; j < shipsOnBoard[0][i].getLocation().length; j++){
                                    if(shipsOnBoard[0][i].getLocation()[j].getX() == coord.getX() && shipsOnBoard[0][i].getLocation()[j].getY() == coord.getY()){
                                        //Draw red marker IT SHOULD STILL BE THE PLAYERS TURN
                                        Log.i("SUCCESSFUL SHOT", "makeMove: ");
                                        Coordinates[][] enemyBoard = state.getBoard(0).getCurrentBoard();
                                        enemyBoard[coord.getX()][coord.getY()].setHasShip(true);
                                        Log.i("SUCCESSFUL SHOT", "At x: " + coord.getX() + " Y: " +  coord.getY());
                                        state.setPlayersTurn(1);
                                        return true;
                                    }
                                }
                            }

                            //DRAW WHITE
                            Log.i("MISSED SHOT", "At x: " + coord.getX() + " Y: " +  coord.getY());
                            state.setPlayersTurn(0);
                            Log.i("Players turn UPDATED ", "setPlayersTurn: " + state.getPlayersTurn());
                        }
                        return true;
                    }
                    else{
                        //flash
                    }
                }
            //}
            //add else statement for placeShip Action


            return false;
        }
    }

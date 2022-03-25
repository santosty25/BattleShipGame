package edu.up.cs301.battleship;

import edu.up.cs301.game.GameFramework.LocalGame;
import edu.up.cs301.game.GameFramework.actionMessage.GameAction;
import edu.up.cs301.game.GameFramework.players.GamePlayer;
import edu.up.cs301.tictactoe.infoMessage.TTTState;

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
        p.sendInfo(new BattleShipGameState(((BattleShipGameState) state)));
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
        GameBoard board = state.getBoard(); //the board
        int remainingShips = state.getRemainingShips(); //the number of remaining ships
        int whoseTurn = state.getPlayersTurn(); //whose turn it is

        if(action instanceof Fire) {
            if(phase != 1) {
                //checks if the phase is in battle phase
                return false;
            }
            else {
                //get the coordinate given by the player and calls the fire method in gamestate
                Coordinates coord = ((Fire) action).getCoord();
                state.canFire(coord);
                if(whoseTurn == 0) {
                    state.setPlayersTurn(1);
                }
                else {
                    state.setPlayersTurn(0);
                }
            }
        }
        //add else statement for placeShip Action


        return false;
    }
}


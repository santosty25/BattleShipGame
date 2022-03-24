package edu.up.cs301.battleship;

import edu.up.cs301.game.GameFramework.LocalGame;
import edu.up.cs301.game.GameFramework.actionMessage.GameAction;
import edu.up.cs301.game.GameFramework.players.GamePlayer;
import edu.up.cs301.tictactoe.infoMessage.TTTState;

public class BattleShipLocalGame extends LocalGame {

    public BattleShipLocalGame() {
        // perform superclass initialization
        super();
        super.state = new BattleShipGameState();
    }

    public BattleShipLocalGame(BattleShipGameState battleshipState){
        super();
        super.state = new BattleShipGameState(battleshipState);
    }

    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        // make a copy of the state, and send it to the player
        p.sendInfo(new BattleShipGameState(((BattleShipGameState) state)));
    }

    @Override
    protected boolean canMove(int playerIdx) {
        return playerIdx == ((BattleShipGameState) state).getPlayersTurn();
    }

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

    @Override
    protected boolean makeMove(GameAction action) {
        BattleShipGameState state = (BattleShipGameState) super.state;
        int phase = state.getPhase();
        int player = state.getPlayerID();
        GameBoard board = state.getBoard();
        int remainingShips = state.getRemainingShips();
        int whosTurn = state.getPlayersTurn();

        if(action instanceof Fire) {
            if(phase != 1) {
                return false;
            }
            else {
                Coordinates coord = ((Fire) action).getCoord();
                state.canFire(coord);
                if(whosTurn == 0) {
                    state.setPlayersTurn(1);
                }
                else {
                    state.setPlayersTurn(0);
                }
            }
        }


        return false;
    }
}


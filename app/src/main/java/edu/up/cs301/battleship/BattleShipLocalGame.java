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
        return null;
    }

    @Override
    protected boolean makeMove(GameAction action) {
        BattleShipGameState state = (BattleShipGameState) super.state;
        int phase = state.getPhase();
        GameBoard board = state.getBoard();
        int playerID = state.getPlayerID();
        int remainingShips = state.getRemainingShips();

        if(action instanceof Fire) {
            if(phase != 1) {
                return false;
            }
            else {

            }
        }


        return false;
    }
}


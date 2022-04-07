package edu.up.cs301.battleship;

import android.util.Log;

import java.util.Random;

import edu.up.cs301.game.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.game.GameFramework.players.GameComputerPlayer;

/**
 * BattleShipDumbAI - This class represents a dumb AI that places ships in a fixed
 * position and fires randomly.
 *
 * @author Tyler Santos
 * @author Austen Furutani
 * @author Keoni Han
 * @author Steven Lee
 * @version Spring 2022 - 3/31/22
 */
public class BattleShipDumbAI extends GameComputerPlayer {

    /**
     * Constructor
     * @param name - the name of the computer player
     */
    public BattleShipDumbAI(String name) {
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {
        if (!(info instanceof BattleShipGameState)) {
            return;
        }

        BattleShipGameState gameState = new BattleShipGameState((BattleShipGameState) info, playerNum);
        Log.i("COMPUTER PLAYERS TURN", "");

        //Checks if it's the computer player's turn
        if (gameState.getPlayersTurn() == playerNum) {
            Log.i("COMPUTER PLAYERS TURN", "");
            Random r = new Random();
            int row;
            int col;
           // sleep(1);
            row = r.nextInt(10);
            col = r.nextInt(10);
            Coordinates fire = new Coordinates(false, false, row, col);
            Log.i("COMPUTER randomFire", "Fired at " + row + " " + col + ".");
            game.sendAction(new Fire(this, fire));
        }

    }
}
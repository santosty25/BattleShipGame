package edu.up.cs301.battleship;

import android.util.Log;

import java.util.Random;

import edu.up.cs301.game.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.game.GameFramework.players.GameComputerPlayer;

public class BattleShipDumbAI extends GameComputerPlayer {

    public BattleShipDumbAI(String name) {
        super(name);
    }
    @Override
    protected void receiveInfo(GameInfo info) {
        BattleShipGameState gameState = new BattleShipGameState((BattleShipGameState)info);
//        Fire fire = new Fire(this);
        PlaceShip placeShip = new PlaceShip(this);
        Random r = new Random();
        int row = r.nextInt(10) + 1;
        int col = r.nextInt(10) + 1;

        if (playerNum == gameState.getPlayersTurn()) {
            Coordinates coords = new Coordinates(false, true, row, col);
            gameState.printFire(row, col, true);
            Log.i("randomFire", "Fired at " + row + " " + col + "." );
//            game.sendAction(new Fire(this));
        }
    }
}

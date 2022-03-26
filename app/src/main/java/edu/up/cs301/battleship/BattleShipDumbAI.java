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
        Random r = new Random();
        int row = r.nextInt(10) + 1;
        int col = r.nextInt(10) + 1;
        int dir = r.nextInt(2) + 1;
        // 2 x 3 length battleships
        // 2 x 4 length battleships
        // 1 x 2 length battleships
        // 1 x 5 length battleship
        if (playerNum == gameState.getPlayersTurn()) {
            Coordinates[] location = new Coordinates[3];
            Coordinates c1 = new Coordinates();
            Coordinates c2 = new Coordinates();
            Coordinates c3 = new Coordinates();
            if (gameState.getPhase() == BattleShipGameState.SETUP_PHASE) {
                for (int i = 0; i < 2; i++) {
                    row = r.nextInt(10) + 1;
                    col = r.nextInt(10) + 1;
                    dir = r.nextInt(2) + 1;
                    if (dir == 1) { // place the ships horizontally
                        c1 = new Coordinates(false, true, row, col);
                        c2 = new Coordinates(false, true, row, col + 1);
                        c3 = new Coordinates(false, true, row, col - 1);
                    } else if (dir == 2) { // place the ships vertically
                        c1 = new Coordinates(false, true, row, col);
                        c2 = new Coordinates(false, true, row + 1, col);
                        c3 = new Coordinates(false, true, row - 1, col);
                    }
                    location[0] = c1;
                    location[1] = c2;
                    location[2] = c3;
                    BattleshipObj bsj = new BattleshipObj(3, location);
                    game.sendAction(new PlaceShip(this, bsj));
                }
            }
            // Fire at coordinate
            if (gameState.getPhase() == BattleShipGameState.BATTLE_PHASE) {
                row = r.nextInt(10) + 1;
                col = r.nextInt(10) + 1;
                Coordinates fire = new Coordinates(false, false, row, col);
                Log.i("randomFire", "Fired at " + row + " " + col + ".");
                game.sendAction(new Fire(this, fire));
            }
        }
    }
}

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
        if (!(info instanceof BattleShipGameState)) {
            Log.i("FLASHING", "");
//            flash(0xFFFF0000, 100);
            return;
        }

        BattleShipGameState gameState = new BattleShipGameState((BattleShipGameState) info, playerNum);
        Log.i("COMPUTER PLAYERS TURN", "");

        if (gameState.getPlayersTurn() == playerNum) {
            Log.i("COMPUTER PLAYERS TURN", "");
            Random r = new Random();
            int row;
            int col;
            sleep(1);
            row = r.nextInt(10);
            col = r.nextInt(10);
            Coordinates fire = new Coordinates(false, false, row, col);
            Log.i("COMPUTER randomFire", "Fired at " + row + " " + col + ".");
            game.sendAction(new Fire(this, fire));
        }

        if (playerNum == gameState.getPlayersTurn()) {
            Coordinates[] location = new Coordinates[3];
            Coordinates c1 = new Coordinates();
            Coordinates c2 = new Coordinates();
            Coordinates c3 = new Coordinates();
            Coordinates c4 = new Coordinates();
            Coordinates c5 = new Coordinates();
            if (gameState.getPhase() == BattleShipGameState.SETUP_PHASE) {
                int i = 1;
                // 2 x 3 length ships
                while (i < 3) {
                    Random r = new Random();
                    int dir = r.nextInt(2) + 1;
                    while ((c2.getX() < 1) || (c2.getX() > 10) || (c2.getY() < 1) || (c2.getY() > 10) || (c3.getX() < 1) || (c3.getX() > 10) || (c3.getY() < 1) || (c3.getY() > 10)) {
                        r = new Random();
                        int row = r.nextInt(10) + 1;
                        int col = r.nextInt(10) + 1;
                        c1 = new Coordinates(false, true, row, col);
                        if (dir == 1) { // place the ships horizontally
                            c2 = new Coordinates(false, true, row, col + 1);
                            c3 = new Coordinates(false, true, row, col - 1);
                        } else if (dir == 2) { // place the ships vertically
                            c2 = new Coordinates(false, true, row + 1, col);
                            c3 = new Coordinates(false, true, row - 1, col);
                        }
                    }
                    location[0] = c1;
                    location[1] = c2;
                    location[2] = c3;
                    BattleshipObj bsj = new BattleshipObj(3, location);
                    game.sendAction(new PlaceShip(this, bsj, playerNum));
                    i++;
                }
                location = new Coordinates[4];
                i = 1;
                // 2 x 4 length ships
                while (i < 3) {
                    Random r = new Random();
                    int dir = r.nextInt(2) + 1;
                    while ((c2.getX() < 1) || (c2.getX() > 10) || (c2.getY() < 1) || (c2.getY() > 10) || (c3.getX() < 1) || (c3.getX() > 10) || (c3.getY() < 1) || (c3.getY() > 10) || (c4.getX() < 1) || (c4.getX() > 10) || (c4.getY() < 1) || (c4.getY() > 10)) {
                        r = new Random();
                        int row = r.nextInt(10) + 1;
                        int col = r.nextInt(10) + 1;
                        c1 = new Coordinates(false, true, row, col);
                        if (dir == 1) { // place the ships horizontally
                            c2 = new Coordinates(false, true, row, col + 1);
                            c3 = new Coordinates(false, true, row, col - 1);
                            c4 = new Coordinates(false, true, row, col - 2);
                        } else if (dir == 2) { // place the ships vertically
                            c2 = new Coordinates(false, true, row + 1, col);
                            c3 = new Coordinates(false, true, row - 1, col);
                            c4 = new Coordinates(false, true, row - 2, col);
                        }
                    }
                    location[0] = c1;
                    location[1] = c2;
                    location[2] = c3;
                    location[3] = c4;
                    BattleshipObj bsj = new BattleshipObj(4, location);
                    game.sendAction(new PlaceShip(this, bsj, playerNum));
                    i++;
                }
                // 2 length ship
                location = new Coordinates[2];
                Random r = new Random();
                int dir = r.nextInt(2) + 1;
                while ((c2.getX() < 1) || (c2.getX() > 10) || (c2.getY() < 1) || (c2.getY() > 10)) {
                    r = new Random();
                    int row = r.nextInt(10) + 1;
                    int col = r.nextInt(10) + 1;
                    c1 = new Coordinates(false, true, row, col);
                    if (dir == 1) { // place the ships vertically
                        c2 = new Coordinates(false, true, row + 1, col);
                    } else { // place the ships horizontally
                        c2 = new Coordinates(false, true, row, col + 1);
                    }
                }
                location[0] = c1;
                location[1] = c2;
                BattleshipObj bsj = new BattleshipObj(2, location);
                game.sendAction(new PlaceShip(this, bsj, playerNum));

                // 5 length ship
                location = new Coordinates[5];
                r = new Random();
                dir = r.nextInt(2) + 1;
                while ((c2.getX() < 1) || (c2.getX() > 10) || (c2.getY() < 1) || (c2.getY() > 10) || (c3.getX() < 1) || (c3.getX() > 10) || (c3.getY() < 1) || (c3.getY() > 10) || (c4.getX() < 1) || (c4.getX() > 10) || (c4.getY() < 1) || (c4.getY() > 10) || (c5.getX() < 1) || (c5.getX() > 10) || (c5.getY() < 1) || (c5.getY() > 10) ) {
                    r = new Random();
                    int row = r.nextInt(10) + 1;
                    int col = r.nextInt(10) + 1;
                    c1 = new Coordinates(false, true, row, col);
                    if (dir == 1) { // place the ships horizontally
                        c2 = new Coordinates(false, true, row, col + 1);
                        c3 = new Coordinates(false, true, row, col - 1);
                        c4 = new Coordinates(false, true, row, col - 2);
                        c5 = new Coordinates(false, true, row, col + 2);
                    } else { // place the ships vertically
                        c2 = new Coordinates(false, true, row + 1, col);
                        c3 = new Coordinates(false, true, row - 1, col);
                        c4 = new Coordinates(false, true, row - 2, col);
                        c5 = new Coordinates(false, true, row + 2, col);
                    }
                }
                location[0] = c1;
                location[1] = c2;
                location[2] = c3;
                location[3] = c4;
                location[4] = c5;
                BattleshipObj bso = new BattleshipObj(5, location);
                game.sendAction(new PlaceShip(this, bso, playerNum));
            }
        }
    }
}
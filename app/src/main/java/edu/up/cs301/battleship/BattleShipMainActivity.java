package edu.up.cs301.battleship;

import java.util.ArrayList;

import edu.up.cs301.game.GameFramework.GameMainActivity;
import edu.up.cs301.game.GameFramework.LocalGame;
import edu.up.cs301.game.GameFramework.gameConfiguration.GameConfig;
import edu.up.cs301.game.GameFramework.gameConfiguration.GamePlayerType;
import edu.up.cs301.game.GameFramework.infoMessage.GameState;
import edu.up.cs301.game.GameFramework.players.GamePlayer;
import edu.up.cs301.game.R;
import edu.up.cs301.tictactoe.players.TTTComputerPlayer1;
import edu.up.cs301.tictactoe.players.TTTComputerPlayer2;
import edu.up.cs301.tictactoe.players.TTTHumanPlayer1;
import edu.up.cs301.tictactoe.players.TTTHumanPlayer2;

public class BattleShipMainActivity extends GameMainActivity {
    private static final String TAG = "TTTMainActivity";
    public static final int PORT_NUMBER = 6785;

    @Override
    public GameConfig createDefaultConfig() {
        // Define the allowed player types
        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();

        playerTypes.add(new GamePlayerType("Local Human Player") {
            public GamePlayer createPlayer(String name) {
                return new BattleShipHumanPlayer(name);
            }
        });

        // dumb computer player
        playerTypes.add(new GamePlayerType("Computer Player (dumb)") {
            public GamePlayer createPlayer(String name) {
                return new BattleShipDumbAI(name);
            }
        });

//        // smarter computer player
//        playerTypes.add(new GamePlayerType("Computer Player (smart)") {
//            public GamePlayer createPlayer(String name) {
//                return new TTTComputerPlayer2(name);
//            }
//        });

        // Create a game configuration class for Tic-tac-toe
        GameConfig defaultConfig = new GameConfig(playerTypes, 2,2, "Battleship", PORT_NUMBER);

        // Add the default players
        defaultConfig.addPlayer("Human", 0);
        defaultConfig.addPlayer("Computer", 1);

        // Set the initial information for the remote player
        defaultConfig.setRemoteData("Remote Player", "", 1); // red-on-yellow GUI

        //done!
        return defaultConfig;

    }

    @Override
    public LocalGame createLocalGame(GameState gameState) {
        return new BattleShipLocalGame();
    }
}

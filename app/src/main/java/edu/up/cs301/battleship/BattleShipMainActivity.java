package edu.up.cs301.battleship;

import android.media.MediaPlayer;

import java.util.ArrayList;

import edu.up.cs301.game.GameFramework.GameMainActivity;
import edu.up.cs301.game.GameFramework.LocalGame;
import edu.up.cs301.game.GameFramework.gameConfiguration.GameConfig;
import edu.up.cs301.game.GameFramework.gameConfiguration.GamePlayerType;
import edu.up.cs301.game.GameFramework.infoMessage.GameState;
import edu.up.cs301.game.GameFramework.players.GamePlayer;
import edu.up.cs301.game.R;


/**
 * BattleShipMainActivity - Primary activity for a game of Battleship.
 *
 * Status:
 * The rules have been implemented into our game. Each player is able to set up their ships
 * and tap on the screen to fire at a coordinate once confirming their ship placement.If a player
 * misses the turn wil switch but if the hit was successful, the player gets to go again. When
 * all the ships of one side are sunk the game will end. If a ship gets sunk,
 * the ship will be revealed on the right side bar that will mark an "X" on one of the ships
 * indicating it has been sunk.
 * All parts of the GUI have been implemented except for the fire button, the pause button, and
 * the targeting marker. We decided that the player will tap on the screen to fire instead of
 * confirming it through a button which means that the targeting marker wasn't needed. A pause
 * button wasn't implemented since we felt it wasn't needed for beta.
 * The dumb AI places all of its ships on the top left side of the grid and will fire randomly.
 * The smart AI fires randomly at first and once it detects a hit, it will fire in the
 * cardinal directions. The smart AI's ship placement is random.
 * The game does support network play.
 * The graphics of the game were made to be as appealing as possible.
 * The game can only be played between a Human Player and an AI Player and a
 * Human Player with another Human Player through network play.
 * The GUI allows the player to drag a ship onto the grid which will snap to place
 * and the player can tap the ship to rotate. If a ship goes out of bounds, it will
 * be returned to the inventory.
 *
 * Features: When playing through network play each player needs to take
 * turns to place a ship starting with player 0. Don't fire by tapping on the
 * grid unless both players are on the midgame screen.
 *
 * @author Tyler Santos
 * @author Austen Furutani
 * @author Keoni Han
 * @author Steven Lee
 * @version Spring 2022 - 4/14/22
 */
public class BattleShipMainActivity extends GameMainActivity {
    //Tag for logging
    private static final String TAG = "BattleShipMainActivity";
    public static final int PORT_NUMBER = 6785;
    public static MediaPlayer splash;
    public static MediaPlayer place;
    public static MediaPlayer explosion;
    public static String player0Name;
    public static String player1Name;


    /**
     * a battleship game is for two players. The default is human vs. computer
     */
    public GameConfig createDefaultConfig() {
        splash = MediaPlayer.create(this, R.raw.splash);
        explosion  = MediaPlayer.create(this, R.raw.explosion);
        place = MediaPlayer.create(this, R.raw.pop);
        // Define the allowed player types
        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();

        playerTypes.add(new GamePlayerType("Local Human Player") {
            public GamePlayer createPlayer(String name) {
                player0Name = name;
                return new BattleShipHumanPlayer(name);
            }
        });

        // dumb computer player
        playerTypes.add(new GamePlayerType("Computer Player (dumb)") {
            public GamePlayer createPlayer(String name) {
                player1Name = name;
                return new BattleShipDumbAI(name);
            }
        });

        // smarter computer player
        playerTypes.add(new GamePlayerType("Computer Player (smart)") {
            public GamePlayer createPlayer(String name) {
                player1Name = name;
                return new BattleShipSmartAI(name);
            }
        });

        // Create a game configuration class for Tic-tac-toe
        GameConfig defaultConfig = new GameConfig(playerTypes, 2,2, "Battleship", PORT_NUMBER);

        // Add the default players
        defaultConfig.addPlayer("Human", 0);
        defaultConfig.addPlayer("Computer", 1);

        // Set the initial information for the remote player
        defaultConfig.setRemoteData("Remote Player", "", 1);// red-on-yellow GUI

        //done!
        return defaultConfig;

    }

    /**
     * createLocalGame
     *
     * Creates a new game that runs on the server tablet,
     * @param gameState
     * 				the gameState for this game or null for a new game
     *
     * @return a new, game-specific instance of a sub-class of the LocalGame
     *         class.
     */
    @Override
    public LocalGame createLocalGame(GameState gameState) {
        return new BattleShipLocalGame();
    }

    public String getPlayer0Name() {
        return player0Name;
    }

    public String getPlayer1Name() {
        return player1Name;
    }
}


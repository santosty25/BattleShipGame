package edu.up.cs301.battleship;

import java.io.Serializable;

import edu.up.cs301.game.GameFramework.actionMessage.GameAction;
import edu.up.cs301.game.GameFramework.players.GamePlayer;

/**
 * SwitchPhase - This class represents an action when each player is done setting
 * up their ships.
 *
 * @author Tyler Santos
 * @author Austen Furutani
 * @author Keoni Han
 * @author Steven Lee
 * @version Spring 2022 - 4/13/22
 */
public class SwitchPhase extends GameAction implements Serializable {

    private int playerNum;
    private static final long serialVersionUID = 040420021l;
    private boolean isReady;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public SwitchPhase(GamePlayer player, int playerNum, boolean ready) {
        super(player);
        this.playerNum = playerNum;
        this.isReady = ready;
    }

    public int getPlayerNum() {
        return this.playerNum;
    }

    public boolean getIsReady() {
        return this.isReady;
    }
}

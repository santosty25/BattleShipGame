package edu.up.cs301.battleship;

import edu.up.cs301.game.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.game.GameFramework.players.GameComputerPlayer;

public class BattleShipSmartAI extends GameComputerPlayer {

    public BattleShipSmartAI(String name) {
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {
    }
}

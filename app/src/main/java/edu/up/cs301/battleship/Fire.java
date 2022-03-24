package edu.up.cs301.battleship;

import edu.up.cs301.game.GameFramework.actionMessage.GameAction;
import edu.up.cs301.game.GameFramework.players.GamePlayer;

public class Fire extends GameAction {

    private int x;
    private int y;

    public Fire(GamePlayer player) {
        super(player);
        }

    public void setX(int setX) {
        this.x = setX;
    }

    public void setY(int setY) {
        this.y = setY;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}

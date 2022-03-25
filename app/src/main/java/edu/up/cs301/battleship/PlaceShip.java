package edu.up.cs301.battleship;

import edu.up.cs301.game.GameFramework.actionMessage.GameAction;
import edu.up.cs301.game.GameFramework.players.GamePlayer;

public class PlaceShip extends GameAction {

    private int x;
    private int y;

    //make constructor param take a battleship obj
    public PlaceShip(GamePlayer player) {
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

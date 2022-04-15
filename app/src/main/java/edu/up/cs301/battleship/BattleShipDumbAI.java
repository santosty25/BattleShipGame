package edu.up.cs301.battleship;

import android.util.Log;

import java.util.ArrayList;
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
    private int shipNum;
    private BattleShipGameState compGS;

    /**
     * Constructor
     *
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

        this.compGS = new BattleShipGameState((BattleShipGameState) info);
        shipNum = 0;
        for(int i = 0; i < compGS.getPlayersFleet()[playerNum].length; i++){
            if(compGS.getPlayersFleet()[playerNum][i].getSize() != 1){
                shipNum++;
            }
        }
        if(shipNum == 6){
            game.sendAction(new SwitchPhase(this, playerNum, true));
        }
        if (compGS.getPhase() == BattleShipGameState.SETUP_PHASE){
            placeShip();
        }
        else if (compGS.getPhase() == BattleShipGameState.BATTLE_PHASE){
            fireAction();
        }
        Log.i("COMPUTER PLAYERS TURN", "COMPUTER PLAYERS TURN");
        //sleep(1);
    }
    public void placeShip(){
        int i = 0;
        Log.i("PLACING SHIP", "ship Num" + shipNum);
        Coordinates[] loc;
        Coordinates[] temp = new Coordinates[1];
        temp[0] = new Coordinates(false, false, -1, -1);
        BattleshipObj selectedBattleShip = new  BattleshipObj(1, temp);
        if(compGS.getPlayersTurn() != playerNum){
            return;
        }
        if(shipNum == 0) {
            loc = new Coordinates[5];
            for(i = 0; i < loc.length; i++) {
                Coordinates coord = new Coordinates(false, true, i, shipNum);
                loc[i] = new Coordinates(coord);
            }
            selectedBattleShip = new BattleshipObj(5, loc);
        }
        else if(shipNum == 1) {
            loc = new Coordinates[4];
            for(i = 0; i < loc.length; i++) {
                Coordinates coord = new Coordinates(false, true, i, shipNum);
                loc[i] = new Coordinates(coord);
            }
            selectedBattleShip = new BattleshipObj(loc.length, loc);
            selectedBattleShip.setTwinShip(0);
        }
        else if(shipNum == 2) {
            loc = new Coordinates[4];
            for(i = 0; i < loc.length; i++) {
                Coordinates coord = new Coordinates(false, true, i, shipNum);
                loc[i] = new Coordinates(coord);
            }
            selectedBattleShip = new BattleshipObj(loc.length, loc);
            selectedBattleShip.setTwinShip(1);
        }
        else if(shipNum == 3) {
            loc = new Coordinates[3];
            for(i = 0; i < loc.length; i++) {
                Coordinates coord = new Coordinates(false, true, i, shipNum);
                loc[i] = new Coordinates(coord);
            }
            selectedBattleShip = new BattleshipObj(loc.length, loc);
            selectedBattleShip.setTwinShip(0);
        }
        else if(shipNum == 4) {
            loc = new Coordinates[3];
            for(i = 0; i < loc.length; i++) {
                Coordinates coord = new Coordinates(false, true, i, shipNum);
                loc[i] = new Coordinates(coord);
            }
            selectedBattleShip = new BattleshipObj(loc.length, loc);
            selectedBattleShip.setTwinShip(1);
        }
        else{
            loc = new Coordinates[2];
            for(i = 0; i < loc.length; i++) {
                Coordinates coord = new Coordinates(false, true, i, 5);
                loc[i] = new Coordinates(coord);
            }
            selectedBattleShip= new BattleshipObj(loc.length, loc);
        }
        game.sendAction(new PlaceShip(this, selectedBattleShip, playerNum));
    }

    public void fireAction(){
        Random r = new Random();
        int row;
        int col;
        row = r.nextInt(10);
        col = r.nextInt(10);
        Log.i("COMPUTER FIRE;", "Fired at " + row + " " + col + ".");
        Coordinates fire = new Coordinates(false, false, row, col);
        int enemy = 1;
        if(playerNum ==1){
            enemy = 0;
        }
        if (compGS.getBoard(enemy).getCurrentBoard()[row][col].getHit()){
            fireAction();
        }
        sleep(2);
        game.sendAction(new Fire(this, fire, playerNum));

    }
}
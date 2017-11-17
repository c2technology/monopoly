/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.c2technology.monopoly;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.c2technology.monopoly.player.GamePiece;
import net.c2technology.monopoly.player.Player;
import org.apache.log4j.BasicConfigurator;

/**
 *
 * @author Chris
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BasicConfigurator.configure();
        List<Player> players = new CopyOnWriteArrayList<Player>();
        players.add(new Player("Player One", GamePiece.DOG));
        players.add(new Player("Player Two", GamePiece.DOG));
        players.add(new Player("Player Three", GamePiece.DOG));
        players.add(new Player("Player Four", GamePiece.DOG));
        Monopoly game = new Monopoly(players);
        game.play();
    }
}

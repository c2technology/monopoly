/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.c2technology.monopoly.space;

import net.c2technology.monopoly.board.CardListener;
import net.c2technology.monopoly.player.Player;

/**
 *
 * @author Chris
 */
public class GoToJail implements Space{

    private static Space instance;
    private CardListener listener;
    
    public static Space getInstance(CardListener listener){
        if (instance == null){
            instance = new GoToJail(listener);
        }
        return instance;
    }

    private GoToJail(CardListener listener){
        this.listener = listener;
    }

    public void doAction(Player player, int roll, boolean doubleRent) {
        listener.goToJail(player);
    }
}

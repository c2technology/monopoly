/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.c2technology.monopoly.cards;

import net.c2technology.monopoly.board.CardListener;
import net.c2technology.monopoly.player.Player;

/**
 *
 * @author Chris
 */
public abstract class Card {

    private final String text;

    public Card(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }

    public abstract void action(Player player, CardListener listener);
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.c2technology.monopoly.space;

import net.c2technology.monopoly.board.CardListener;
import net.c2technology.monopoly.cards.Card;
import net.c2technology.monopoly.player.Player;

/**
 *
 * @author Chris
 */
public abstract class CollectibleCard extends Card {

    private final int value;

    public CollectibleCard(String text, int value) {
        super(text);
        this.value = value;
    }

    public void action(Player player, CardListener listener) {
        player.addCard(this);
    }

    public int sell() {
        return this.value;
    }

    public abstract void delayedAction(Player player);
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.c2technology.monopoly.cards;

import net.c2technology.monopoly.space.CollectibleCard;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.c2technology.monopoly.board.CardListener;
import net.c2technology.monopoly.player.Player;
import org.apache.log4j.Logger;

/**
 *
 * @author Chris
 */
public abstract class Deck {

    protected List<Card> deck = new CopyOnWriteArrayList<Card>();
    private final CardListener listener;
    private Iterator<Card> iterator;
    private final static Logger logger = Logger.getLogger(Deck.class);
    private boolean shuffled = false;

    protected Deck(CardListener listener) {
        this.listener = listener;
    }

    public void drawCard(Player player) {
        if (!shuffled) {
            Collections.shuffle(deck);
            this.shuffled = true;
        }
        if (iterator == null || !iterator.hasNext()){
            this.iterator = deck.iterator();
        }
//        if (!iterator.hasNext()) {
//            this.iterator = deck.iterator();
//        }
        logger.debug(player.toString() + " draws a card...");
        Card card = iterator.next();
        logger.debug("CARD: " + player.toString() + " drew " + card.toString());
        card.action(player, listener);
        if (card instanceof CollectibleCard) {
            this.deck.remove(card);
        }
    }
}

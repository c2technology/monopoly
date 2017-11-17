/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.c2technology.monopoly.space;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.c2technology.monopoly.board.CardListener;
import net.c2technology.monopoly.cards.Card;
import net.c2technology.monopoly.cards.Deck;
import net.c2technology.monopoly.player.Player;
import net.c2technology.monopoly.space.CollectibleCard;
import net.c2technology.monopoly.space.Space;
import org.apache.log4j.Logger;

/**
 *
 * @author Chris
 */
public class Chance extends Deck implements Space {

    private static Chance instance;
    private final static Logger logger = Logger.getLogger(Chance.class);

    public static Chance getInstance(CardListener listener) {
        if (instance == null) {
            instance = new Chance(listener);
        }
        return instance;
    }

    private Chance(CardListener listener) {
        super(listener);
        deck = buildDeck();
    }

    public void doAction(Player player, int roll, boolean doubleRent) {
        logger.debug(player.toString() + " landed on Chance!");
        drawCard(player);
    }

    private static List<Card> buildDeck() {
        List<Card> deck = new CopyOnWriteArrayList<Card>();
        Card advanceToNearestRailroad = new Card("ADVANCE TO THE NEAREST RAILROAD. If UNOWNED, you may buy it from the Bank. If OWNED, pay owner twice the rental to which they are otherwise entitled.") {

            public void action(Player player, CardListener listener) {
                logger.debug("CHANCE: " + player.toString() + " " + this.toString());
                listener.advanceToNearestRailroad(player);
            }
        };
        deck.add(
                new Card("ADVACE TO \"GO\". (COLLECT $200 DOLLARS)") {

                    public void action(Player player, CardListener listener) {
                        logger.debug("CHANCE: " + player.toString() + " " + this.toString());
                        listener.move(player, Space.GO, false);
                    }
                });
        deck.add(
                new Card("ADVANCE TO " + Space.ILLINOIS_AVENUE.toString().toUpperCase() + ". IF YOU PASS \"GO\" COLLECT $200.") {

                    public void action(Player player, CardListener listener) {
                        logger.debug("CHANCE: " + player.toString() + " " + this.toString());
                        listener.move(player, Space.ILLINOIS_AVENUE, false);
                    }
                });

        deck.add(
                new Card("BANK PAYS YOU DIVIDENDS OF $50.") {

                    public void action(Player player, CardListener listener) {
                        logger.debug("CHANCE: " + player.toString() + " " + this.toString());
                        player.collect(50);
                    }
                });
        deck.add(
                new Card("SPEEDING FINE $15.") {

                    public void action(Player player, CardListener listener) {
                        logger.debug("CHANCE: " + player.toString() + " " + this.toString());
                        player.collect(15);
                    }
                });
        deck.add(
                new Card("ADVANCE TO " + Space.BOARDWALK.toString().toUpperCase() + ".") {

                    public void action(Player player, CardListener listener) {
                        logger.debug("CHANCE: " + player.toString() + " " + this.toString());
                        listener.move(player, Space.BOARDWALK, false);
                    }
                });
        deck.add(
                new Card("ADVANCE TO " + Space.ST_CHARLES_PLACE.toString().toUpperCase() + ". IF YOU PASS \"GO\" COLLECT $200.") {

                    public void action(Player player, CardListener listener) {
                        logger.debug("CHANCE: " + player.toString() + " " + this.toString());
                        listener.move(player, Space.ST_CHARLES_PLACE, false);
                    }
                });
        deck.add(
                new Card("GO DIRECTLY TO JAIL, DO NOT PASS \"GO\", DO NOT COLLECT $200") {

                    public void action(Player player, CardListener listener) {
                        logger.debug("CHANCE: " + player.toString() + " " + this.toString());
                        listener.goToJail(player);
                    }
                });
        deck.add(
                new Card("ADVANCE TO THE NEAREST UTILITY. If UNOWNED, you may buy it from the Bank. If OWNED, throw dice and pay owner a total ten times amount thrown.") {

                    public void action(Player player, CardListener listener) {
                        logger.debug("CHANCE: " + player.toString() + " " + this.toString());
                        listener.moveToNearestUtility(player);
                    }
                });
        deck.add(
                new Card("MAKE GENERAL REPAIRS ON ALL YOUR PROPERTY: FOR EACH HOUSE PAY $25, FOR EACH HOTEL PAY $100") {

                    public void action(Player player, CardListener listener) {
                        logger.debug("CHANCE: " + player.toString() + " " + this.toString());
                        listener.repairs(player, 25, 100);
                    }
                });
        deck.add(
                new Card("YOUR BUILDING LOAN MATURES. COLLECT $150") {

                    public void action(Player player, CardListener listener) {
                        logger.debug("CHANCE: " + player.toString() + " " + this.toString());
                        player.collect(150);
                    }
                });
        deck.add(
                new Card("GO BACK THREE SPACES") {

                    public void action(Player player, CardListener listener) {
                        logger.debug("CHANCE: " + player.toString() + " " + this.toString());
                        listener.moveBackThreeSpaces(player);
                    }
                });
        deck.add(
                new CollectibleCard("GET OUT OF JAIL FREE", 50) {

                    public void delayedAction(Player player) {
                        logger.debug(player.toString() + " has used his Get Out Of Jail Free Card (Chance)!");
                        player.inJail(false);
                    }
                });
        deck.add(
                new Card("TAKE A TRIP TO READING RAILROAD. IF YOU PASS \"GO\" COLLECT $200.") {

                    public void action(Player player, CardListener listener) {
                        logger.debug("CHANCE: " + player.toString() + " " + this.toString());
                        listener.move(player, Space.READING_RAILROAD, false);
                    }
                });
        deck.add(advanceToNearestRailroad);
        deck.add(advanceToNearestRailroad);
        deck.add(
                new Card("YOU HAVE BEEN ELECTED CHAIRMAN OF THE BOARD. PAY EACH PLAYER $50.") {

                    public void action(Player player, CardListener listener) {
                        logger.debug("CHANCE: " + player.toString() + " " + this.toString());
                        listener.chairman(player);
                    }
                });
        return deck;

    }
}

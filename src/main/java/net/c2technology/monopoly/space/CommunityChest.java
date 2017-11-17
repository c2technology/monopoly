/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.c2technology.monopoly.space;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.c2technology.monopoly.board.CardListener;
import net.c2technology.monopoly.player.Player;
import net.c2technology.monopoly.cards.Card;
import net.c2technology.monopoly.cards.Deck;
import org.apache.log4j.Logger;

/**
 *
 * @author Chris
 */
public class CommunityChest extends Deck implements Space {

    private static CommunityChest instance;
    private final static Logger logger = Logger.getLogger(CommunityChest.class);

    public static CommunityChest getInstance(CardListener listener) {
        if (instance == null) {
            instance = new CommunityChest(listener);
        }
        return instance;
    }

    private CommunityChest(CardListener listener) {
        super(listener);
        deck = buildDeck();
    }

    public void doAction(Player player, int roll, boolean doubleRent) {
        logger.debug(player.toString() + " landed on Community Chest!");
        drawCard(player);
    }

    private static List<Card> buildDeck() {

        List<Card> deck = new CopyOnWriteArrayList<Card>();

        deck.add(
                new Card("ADVANCE TO \"GO\". (COLLECT $200 DOLLARS)") {

                    public void action(Player player, CardListener listener) {
                        logger.debug("COMUNITY CHEST: " + player.toString() + " " + this.toString());
                        listener.move(player, Space.GO, false);
                    }
                });
        deck.add(
                new CollectibleCard("GET OUT OF JAIL FREE", 50) {

                    public void delayedAction(Player player) {
                        logger.debug(player.toString() + " has used his Get Out Of Jail Free Card (Community Chest)!");
                        player.inJail(false);
                    }
                });
        deck.add(
                new Card("GO DIRECTLY TO JAIL, DO NOT PASS \"GO\", DO NOT COLLECT $200") {

                    public void action(Player player, CardListener listener) {
                        logger.debug("COMUNITY CHEST: " + player.toString() + " " + this.toString());
                        listener.goToJail(player);
                    }
                });
        deck.add(
                new Card("YOU ARE ASSESSED FOR STREET REPAIRS: $40 PER HOUSE, $115 PER HOTEL.") {

                    public void action(Player player, CardListener listener) {
                        logger.debug("COMUNITY CHEST: " + player.toString() + " " + this.toString());
                        listener.repairs(player, 40, 115);
                    }
                });

        deck.add(
                new Card("YOU INHERIT $100.") {

                    public void action(Player player, CardListener listener) {
                        logger.debug("COMUNITY CHEST: " + player.toString() + " " + this.toString());
                        player.collect(100);
                    }
                });
        deck.add(
                new Card("INCOME TAX REFUND. COLLECT $20.") {

                    public void action(Player player, CardListener listener) {
                        logger.debug("COMUNITY CHEST: " + player.toString() + " " + this.toString());
                        player.collect(20);
                    }
                });
        deck.add(
                new Card("BANK ERROR IN YOUR FAVOR. COLLECT $200.") {

                    public void action(Player player, CardListener listener) {
                        logger.debug("COMUNITY CHEST: " + player.toString() + " " + this.toString());
                        player.collect(200);
                    }
                });
        deck.add(
                new Card("FROM SALE OF STOCK YOU GET $50.") {

                    public void action(Player player, CardListener listener) {
                        logger.debug("COMUNITY CHEST: " + player.toString() + " " + this.toString());
                        player.collect(50);
                    }
                });

        deck.add(
                new Card("PAY HOSPITAL FEES OF $100.") {

                    public void action(Player player, CardListener listener) {
                        logger.debug("COMUNITY CHEST: " + player.toString() + " " + this.toString());
                        player.pay(100);
                    }
                });
        deck.add(
                new Card("YOU HAVE WON SECOND PRIZE IN A BEAUTY CONTEST. COLLECT $10.") {

                    public void action(Player player, CardListener listener) {
                        logger.debug("COMUNITY CHEST: " + player.toString() + " " + this.toString());
                        player.collect(10);
                    }
                });
        deck.add(
                new Card("RECEIVE $25 CONSULTANCY FEE.") {

                    public void action(Player player, CardListener listener) {
                        logger.debug("COMUNITY CHEST: " + player.toString() + " " + this.toString());
                        player.collect(25);
                    }
                });
        deck.add(
                new Card("IT IS YOUR BIRTHDAY. COLLECT $10 FROM EVERY PLAYER.") {

                    public void action(Player player, CardListener listener) {
                        logger.debug("COMUNITY CHEST: " + player.toString() + " " + this.toString());
                        listener.birthday(player);
                    }
                });

        deck.add(
                new Card("LIFE INSURANCE MATURES. COLLECT $100.") {

                    public void action(Player player, CardListener listener) {
                        logger.debug("COMUNITY CHEST: " + player.toString() + " " + this.toString());
                        player.collect(100);
                    }
                });
        deck.add(
                new Card("PAY SCHOOL FEES OF $50.") {

                    public void action(Player player, CardListener listener) {
                        logger.debug("COMUNITY CHEST: " + player.toString() + " " + this.toString());
                        player.pay(50);
                    }
                });
        deck.add(
                new Card("DOCTOR'S FEES. PAY $50.") {

                    public void action(Player player, CardListener listener) {
                        logger.debug("COMUNITY CHEST: " + player.toString() + " " + this.toString());
                        player.pay(50);
                    }
                });
        deck.add(
                new Card("HOLIDAY FUND MATURES. RECEIVE $100.") {

                    public void action(Player player, CardListener listener) {
                        logger.debug("COMUNITY CHEST: " + player.toString() + " " + this.toString());
                        player.collect(100);
                    }
                });
        return deck;

    }
}

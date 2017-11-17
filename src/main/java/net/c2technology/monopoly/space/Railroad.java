/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.c2technology.monopoly.space;

import net.c2technology.monopoly.player.Player;
import net.c2technology.monopoly.cards.Color;
import org.apache.log4j.Logger;

/**
 *
 * @author Chris
 */
public class Railroad extends Rental {

    private static final Logger logger = Logger.getLogger(Railroad.class);

    protected Railroad(String name) {
        super(name, Color.RAILROAD, new Rent(25, 50, 100, 200), 100, 200);
    }

    public void doAction(Player player, int roll, boolean doubleRent) {
        logger.debug(player.toString() + " landed on " + this.toString());
        if (this.isOwned()) {
            if (!player.hasProperty(this)) {
                int rentPaid = getRent();
                if (doubleRent) {
                    rentPaid = rentPaid * 2;
                }
                player.payRent(landlord, getRent());
            }
        } else {
            if (player.getCash() > this.getPrice()) {
                player.buyProperty(this);
            } else {
                logger.debug(player.toString() + " passed up " + this.toString());
            }
        }
    }

    public int getRent() {
        return rent.getRent(rentIndex);
    }

    public boolean increaseRent() {
        this.rentIndex++;
        return true;
    }

    public boolean decreaseRent() {
        this.rentIndex--;
        return true;
    }

    public boolean isMortgagable() {
        return true;
    }
}

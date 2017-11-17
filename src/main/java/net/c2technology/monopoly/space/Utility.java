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
public class Utility extends Rental {

    private static final Logger logger = Logger.getLogger(Utility.class);
    private int currentRoll;

    protected Utility(String name) {
        super(name, Color.UTILITY, new Rent(4, 10), 75, 150);
        currentRoll = 1;
    }

    public int getRent() {
        return rent.getRent(super.rentIndex) * currentRoll;
    }

    public void doAction(Player player, int roll, boolean doubleRent) {
        this.currentRoll = roll;
        logger.debug(player.toString() + " landed on " + this.toString());
        if (this.isOwned()) {
            if (!player.hasProperty(this)) {
                int rentPaid = getRent();
                if (doubleRent) {
                    rentPaid = rent.getRent(1) * currentRoll;
                }
                player.payRent(landlord, rentPaid);
            }
        } else {
            if (player.getCash() > this.getPrice()) {
                player.buyProperty(this);
            } else {
                logger.debug(player.toString() + " passed up " + this.toString());
            }
        }
        this.currentRoll = 1;
    }

    @Override
    public void setMonopoly(boolean isMonopoly) {
        super.setMonopoly(isMonopoly);
        if (isMonopoly) {
            increaseRent();
        } else {
            decreaseRent();
        }
    }

    @Override
    public boolean increaseRent() {
        super.rentIndex = 1;
        return true;
    }

    @Override
    public boolean decreaseRent() {
        super.rentIndex = 0;
        return true;
    }

    @Override
    public boolean isMortgagable() {
        return (!this.isMortgaged);
    }
}

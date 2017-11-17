/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.c2technology.monopoly.space;

import net.c2technology.monopoly.player.Player;
import org.apache.log4j.Logger;

/**
 *
 * @author Chris
 */
class Fine implements Space {

    private final String name;
    private final int fine;
    private static final Logger logger = Logger.getLogger(Fine.class);

    public Fine(String name, int fine) {
        this.name = name;
        this.fine = fine;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public void doAction(Player player, int roll, boolean doubleRent) {
        logger.debug(player.toString() + " landed on " + this.toString());
        player.pay(fine);
    }
}

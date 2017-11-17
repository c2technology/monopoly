/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.c2technology.monopoly.board;

import net.c2technology.monopoly.player.Player;
import net.c2technology.monopoly.space.Space;

/**
 *
 * @author Chris
 */
public interface CardListener {

    public void goToJail(Player player);

    public void move(Player player, Space space, boolean doubleRent);

    public void advanceToNearestRailroad(Player player);

    public void moveToNearestUtility(Player player);

    public void moveBackThreeSpaces(Player player);

    public void chairman(Player player);

    public void birthday(Player player);

    public void repairs(Player player, int house, int hotel);
}

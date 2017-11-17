/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.c2technology.monopoly.board;

import java.util.List;
import net.c2technology.monopoly.player.Player;
import net.c2technology.monopoly.space.CollectibleCard;
import net.c2technology.monopoly.space.PropertyManager;

/**
 *
 * @author Chris
 */
public interface GameMaster {

    public void ejectPlayer(Player player);

    public void repo(PropertyManager forclosures, List<CollectibleCard> cards);
}

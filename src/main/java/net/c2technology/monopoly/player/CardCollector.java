/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.c2technology.monopoly.player;

import net.c2technology.monopoly.space.CollectibleCard;

/**
 *
 * @author Chris
 */
public interface CardCollector {

    public void addCard(CollectibleCard card);

    public boolean useCard();

    public void sellCards(int rent);

    @Override
    public String toString();
}

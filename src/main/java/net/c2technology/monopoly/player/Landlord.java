/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.c2technology.monopoly.player;

import net.c2technology.monopoly.space.Rental;
import java.util.List;
import net.c2technology.monopoly.space.CollectibleCard;

/**
 *
 * @author Chris
 */
public interface Landlord {

    public boolean hasProperty(Rental property);

    public void buyProperty(Rental property);

    public void collect(int rent);

    public void collect(int cash, List<Rental> properties, List<CollectibleCard> cards);


    @Override
    public String toString();
}

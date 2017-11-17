/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.c2technology.monopoly.space;

import net.c2technology.monopoly.cards.Color;

/**
 *
 * @author Chris
 */
public abstract class Buildable extends Rental {

    public Buildable(String name, Color color, Rent rent, int mortgage, int price) {
        super(name, color, rent, mortgage, price);
    }

    public abstract boolean hasBuildings();

    public abstract boolean canBuild();

    public abstract int sellBuilding();

    public abstract boolean buyBuilding();

    public abstract int getBuildingPrice();
}

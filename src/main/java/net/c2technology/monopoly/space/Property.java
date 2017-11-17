/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.c2technology.monopoly.space;

import net.c2technology.monopoly.player.Player;
import net.c2technology.monopoly.cards.Color;
import org.apache.log4j.Logger;

/**
 * This represents everything similar to each property
 *
 * @author Chris
 */
public class Property extends Buildable {

    //Logger
    private static final Logger LOG = Logger.getLogger(Property.class);
    private final int buildingCost;

    protected Property(String name, Rent rent, Color color, int buildingCost, int mortgage, int price) {
        super(name, color, rent, mortgage, price);
        this.buildingCost = buildingCost;
        this.rentIndex = 0;
    }

    @Override
    public int getRent() {
        if (this.isMortgaged){
            return 0;
        }
        int currentRent = rent.getRent(this.rentIndex);
        if (this.rentIndex == 0 && this.isMonopoly) {
            return currentRent * 2;
        }
        return currentRent;
    }

    @Override
    public int getBuildingPrice() {
        return buildingCost;
    }

    @Override
    public boolean canBuild() {
        return this.rentIndex <= 5 && !this.isMortgaged && this.isMonopoly;
    }

    @Override
    public int sellBuilding() {
        //TODO: Need to evenly sell
        if (decreaseRent()) {
            LOG.debug(landlord.toString() + " sold a building on " + this.toString());
            return this.getBuildingPrice() / 2;
        }
        return 0;
    }

    @Override
    public boolean buyBuilding() {
        //TODO: Need to evenly buy
        LOG.debug(landlord.toString() + " bought a building on " + this.toString());
        return (increaseRent());
    }

    @Override
    protected boolean increaseRent() {
        if (this.canBuild()) {
            this.rentIndex++;
            return true;
        }
        return false;
    }

    @Override
    protected boolean decreaseRent() {
        if (this.hasBuildings()) {
            this.rentIndex--;
            return true;
        }
        return false;
    }

    @Override
    public boolean hasBuildings() {
        return (this.rentIndex > 0);
    }

    @Override
    public void doAction(Player player, int roll, boolean doubleRent) {
        LOG.debug(player.toString() + " landed on " + this.toString());
        if (this.isOwned()) {
            if (!player.hasProperty(this)) {
                player.payRent(landlord, this.getRent());
            }
        } else if (player.getCash() > this.getPrice()) {
            player.buyProperty(this);
        } else {
            LOG.debug(player.toString() + " passed up " + this.toString());
        }
    }

    @Override
    public boolean isMortgagable() {
        return !this.hasBuildings() && this.isOwned() && !isMortgaged();
    }
}

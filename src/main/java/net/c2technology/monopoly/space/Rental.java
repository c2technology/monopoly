/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.c2technology.monopoly.space;

import net.c2technology.monopoly.player.Landlord;
import net.c2technology.monopoly.cards.Color;
import org.apache.log4j.Logger;

/**
 *
 * @author Chris
 */
public abstract class Rental implements Space, Comparable<Rental>, Foreclosable {

    protected Landlord landlord;
    protected int rentIndex;
    protected boolean isMortgaged;
    protected final int mortgage;
    protected final int price;
    protected final String name;
    protected boolean isMonopoly;
    protected final Rent rent;
    protected final Color color;
    private static final Logger LOG = Logger.getLogger(Rental.class);

    public Rental(String name, Color color, Rent rent, int mortgage, int price) {
        this.rent = rent;
        this.name = name;
        this.color = color;
        this.mortgage = mortgage;
        this.price = price;
        this.isMortgaged = false;
        this.rentIndex = 0;
        this.landlord = null;
    }

    public boolean isOwned() {
        return (null != this.landlord);
    }

    /**
     * @return the payment required to un-mortgage a {@link Rental}
     */
    public int getMortgagePayment() {
        return (int) Math.round(this.mortgage * 1.2);
    }

    //The utilities cause this
    public abstract int getRent();

    public void setLandlord(Landlord landlord) {
        this.landlord = landlord;
    }

    public Color getColor() {
        return this.color;
    }

    public int getMortgage() {
        return this.mortgage;
    }

    /**
     * Sets wether or not this {@link Rental) is mortgaged. Mortgaged {@link Rental}s
     * cannot be considered for collecting rent, mortgaging (again), building
     * houses, or selling houses.
     *
     * @param isMortgaged the new state of mortgage
     */
    public void setMortgaged(boolean isMortgaged) {
        LOG.debug(this.toString() + " has been " + (isMortgaged ? "" : "un") + "mortgaged.");
        this.isMortgaged = isMortgaged;
    }

    /**
     * The state of this {@link Rental}'s mortgage
     *
     * @return <code>true</code> of the {@link Rental} is mortgaged
     */
    public boolean isMortgaged() {
        return this.isMortgaged;
    }

    protected abstract boolean increaseRent();

    protected abstract boolean decreaseRent();

    public abstract boolean isMortgagable();

    public int getPrice() {
        return this.price;
    }

    /**
     * The state of this {@link Rental}'s monopoly
     *
     * @return <code>true</code> of the {@link Rental} is part of a monopoly
     */
    public boolean isMonopoly() {
        return this.isMonopoly;
    }

    public void setMonopoly(boolean isMonopoly) {
        this.isMonopoly = isMonopoly;
    }

    /**
     * The name of the {@link Rental}
     *
     * @return the Name
     */
    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object property) {
        if (property instanceof Rental) {
            return this.toString().equals(property.toString());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + this.getRent();
        return hash;
    }

    @Override
    public int compareTo(Rental property) {

        //single (by rent), monopolies, monopolies (with buildings)
        //sort by mortgage status...
        if (!this.isMortgaged && property.isMortgaged()) {
            return 1;
        }
        if (this.isMortgaged && !property.isMortgaged()) {
            return -1;
        }

        //Sort by monopoly status...
        if (this.isMonopoly && !property.isMonopoly()) {
            return 1;
        }
        if (!this.isMonopoly && property.isMonopoly()) {
            return -1;
        }

        //If same, sort by rent...
        if (this.getRent() < property.getRent()) {
            return -1;
        }
        if (this.getRent() > property.getRent()) {
            return 1;
        }
        //if monopoly, rent, and mortgage is all the same, then they are value
        //the same
        return 0;

    }

    public void forclose() {
        this.setMortgaged(false);
        this.setMonopoly(false);
        this.setLandlord(null);
        this.rentIndex = 0;
    }

    public int getRentIndex() {
        return this.rentIndex;
    }
}

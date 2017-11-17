/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.c2technology.monopoly.player;

/**
 *
 * @author Chris
 */
public interface Renter {

    public void declareBankruptcy(Landlord player);

    public void payRent(Landlord player, int rent);

    @Override
    public String toString();
}

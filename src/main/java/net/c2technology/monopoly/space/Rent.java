/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.c2technology.monopoly.space;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Chris
 */
public class Rent {

    private List<Integer> rent = new ArrayList<Integer>();
    public Rent(int base, int house1, int house2, int house3, int house4, int hotel) {
        rent.add(base);
        rent.add(house1);
        rent.add(house2);
        rent.add(house3);
        rent.add(house4);
        rent.add(hotel);
    }

    public Rent(int multiplier1, int multiplier2) {
        rent.add(multiplier1);
        rent.add(multiplier2);
    }

    public Rent(int railroad1, int railroad2, int railroad3, int railroad4) {
        rent.add(railroad1);
        rent.add(railroad2);
        rent.add(railroad3);
        rent.add(railroad4);
    }

    public int getRent(int level) { //0-5
        return rent.get(level);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.c2technology.monopoly.board;

import java.util.Random;

/**
 *
 * @author Chris
 */
public class Dice {

    private Random die1;
    private Random die2;
    private int roll;
    private boolean doubles;

    public Dice() {
        this.die1 = new Random();
        this.die2 = new Random();
        this.doubles = false;
    }

    public int roll() {
        int roll1 = die1.nextInt(6) + 1;
        int roll2 = die2.nextInt(6) + 1;
        doubles = (roll1 == roll2);
        roll = roll1 + roll2;
        return roll;
    }

    public boolean doubles() {
        return this.doubles;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.c2technology.monopoly.space;

import net.c2technology.monopoly.player.Player;
import net.c2technology.monopoly.cards.Color;

/**
 *
 * @author Chris
 */
public interface Space {
    //BROWN

    public static final Space MEDITERRANEAN_AVENUE = new Property("Mediterranean Avenue", new Rent(2, 10, 30, 90, 160, 250), Color.BROWN, 50, 30, 60);
    public static final Space BALTIC_AVENUE = new Property("Baltic Avenue", new Rent(4, 20, 60, 180, 320, 450), Color.BROWN, 50, 30, 60);
    //LIGHT_BLUE
    public static final Space ORIENTAL_AVENUE = new Property("Oriental Avenue", new Rent(6, 30, 90, 270, 400, 550), Color.LIGHT_BLUE, 50, 50, 100);
    public static final Space VERMONT_AVENUE = new Property("Vermont Avenue", new Rent(6, 30, 90, 270, 400, 550), Color.LIGHT_BLUE, 50, 50, 100);
    public static final Space CONNECTICUT_AVENUE = new Property("Connecticut Avenue", new Rent(8, 40, 100, 300, 450, 600), Color.LIGHT_BLUE, 50, 60, 120);
    //PURPLE
    public static final Space ST_CHARLES_PLACE = new Property("St. Charles Place", new Rent(10, 50, 150, 450, 625, 750), Color.PURPLE, 100, 70, 140);
    public static final Space STATES_AVENUE = new Property("States Avenue", new Rent(10, 50, 150, 450, 625, 750), Color.PURPLE, 100, 70, 140);
    public static final Space VIRGINA_AVENUE = new Property("Virginia Avenue", new Rent(12, 60, 180, 500, 700, 900), Color.PURPLE, 100, 80, 160);
    //ORANGE
    public static final Space ST_JAMES_PLACE = new Property("St. James Place", new Rent(14, 70, 200, 550, 750, 950), Color.ORANGE, 100, 90, 180);
    public static final Space TENNESSEE_AVENUE = new Property("Tennessee Avenue", new Rent(14, 70, 200, 550, 750, 950), Color.ORANGE, 100, 90, 180);
    public static final Space NEW_YORK_AVENUE = new Property("New York Avenue", new Rent(16, 80, 220, 600, 800, 1000), Color.ORANGE, 100, 100, 200);
    //RED
    public static final Space KENTUCKY_AVENUE = new Property("Kentucky Avenue", new Rent(18, 90, 250, 700, 875, 1050), Color.RED, 150, 110, 220);
    public static final Space INDIANA_AVENUE = new Property("Indiana Avenue", new Rent(18, 90, 250, 700, 875, 1050), Color.RED, 150, 110, 220);
    public static final Space ILLINOIS_AVENUE = new Property("Illinois Avenue", new Rent(20, 100, 300, 750, 925, 1100), Color.RED, 150, 120, 240);
    //YELLOW
    public static final Space ATLANTIC_AVENUE = new Property("Atlantic Avenue", new Rent(22, 110, 330, 800, 975, 1150), Color.YELLOW, 150, 130, 260);
    public static final Space VENTNOR_AVENUE = new Property("Ventnor Avenue", new Rent(22, 110, 330, 800, 975, 1150), Color.YELLOW, 150, 130, 260);
    public static final Space MARVIN_GARDENS = new Property("Marvin Gardens", new Rent(24, 120, 360, 850, 1025, 1200), Color.YELLOW, 150, 140, 280);
    //GREEN
    public static final Space PACIFIC_AVENUE = new Property("Pacific Avenue", new Rent(26, 130, 390, 900, 1100, 1275), Color.GREEN, 200, 150, 300);
    public static final Space NORTH_CAROLINA_AVENUE = new Property("North Carolina Avenue", new Rent(26, 130, 390, 900, 1100, 1275), Color.GREEN, 200, 150, 300);
    public static final Space PENNSYLVANIA_AVENUE = new Property("Pennsylvania Avenue", new Rent(28, 150, 450, 1000, 1200, 1400), Color.GREEN, 200, 160, 320);
    //BLUE
    public static final Space PARK_PLACE = new Property("Park Place", new Rent(35, 175, 500, 1100, 1300, 1500), Color.BLUE, 200, 175, 350);
    public static final Space BOARDWALK = new Property("Board Walk", new Rent(50, 200, 600, 1400, 1700, 2000), Color.BLUE, 200, 200, 400);
    //RAILROADS
    public static final Space B_O_RAILROAD = new Railroad("B. & O. Railroad");
    public static final Space READING_RAILROAD = new Railroad("Reading Railroad");
    public static final Space SHORT_LINE = new Railroad("Short Line");
    public static final Space PENNSYLVANIA_RAILROAD = new Railroad("Pennsylvania Railroad");
    //UTILITIES
    public static final Space WATER_WORKS = new Utility("Water Works");
    public static final Space ELECTRIC_COMPANY = new Utility("Electric Company");
    //Tax Spaces
    public static final Space INCOME_TAX = new Fine("Income Tax", 200);
    public static final Space LUXURY_TAX = new Fine("Luxury Tax", 75);
    public static final Space GO = new Space() {

        @Override
        public void doAction(Player player, int roll, boolean doubleRent) {
            player.collect(200);
        }
    };
    public static final Space FREE_PARKING = new Space() {

        @Override
        public void doAction(Player player, int roll, boolean doubleRent) {
            //Do Nothing!
        }
    };
    public static final Space JAIL = new Space() {

        @Override
        public void doAction(Player player, int roll, boolean doubleRent) {
            //Just Visiting
        }
    };

    public void doAction(Player player, int roll, boolean doubleRent);

    @Override
    public String toString();
}

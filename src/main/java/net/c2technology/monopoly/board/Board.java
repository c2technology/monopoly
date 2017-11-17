/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.c2technology.monopoly.board;

import net.c2technology.monopoly.space.Space;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.c2technology.monopoly.player.Player;
import net.c2technology.monopoly.space.Chance;
import net.c2technology.monopoly.space.Buildable;
import net.c2technology.monopoly.space.CommunityChest;
import net.c2technology.monopoly.space.GoToJail;
import net.c2technology.monopoly.space.Rental;
import org.apache.log4j.Logger;

/**
 *
 * @author Chris
 */
public class Board implements CardListener {

    public static final int JAIL = 10;
    public static final int GO_TO_JAIL = 30;
    public static final int GO = 40;
    private Map<Player, Integer> playerLocation;
    private Map<Integer, Space> spaces;
    private static final Logger logger = Logger.getLogger(Board.class);

    public Board(List<Player> players) {
        playerLocation = new HashMap<Player, Integer>();

        this.spaces = new HashMap<Integer, Space>();
        buildSpaces();

        for (Player player : players) {
            logger.debug("Welcome " + player.toString());
            playerLocation.put(player, 0);
        }
    }

    public void progress(Player player, int roll, boolean doubleRent) {

        int currentLocation = this.playerLocation.get(player);
        int newLocation = currentLocation + roll;
        if (newLocation > 40) {
            newLocation -= 40;
            logger.debug(player.toString() + " collects 200 dollars!");
            player.collect(200);
        }

        logger.debug("Moving " + player.toString() + " " + roll + " from " + currentLocation + " to " + newLocation);
        this.playerLocation.put(player, newLocation);
        this.spaces.get(newLocation).doAction(player, roll, doubleRent);
    }

    public void goToJail(Player player) {
        logger.debug(player.toString() + " is in Jail (" + JAIL + ")!");
        playerLocation.put(player, JAIL);
        player.inJail(true);
    }

    private void buildSpaces() {
        spaces.put(1, Space.BALTIC_AVENUE);
        spaces.put(2, CommunityChest.getInstance(this));
        spaces.put(3, Space.MEDITERRANEAN_AVENUE);
        spaces.put(4, Space.INCOME_TAX);
        spaces.put(5, Space.READING_RAILROAD);
        spaces.put(6, Space.ORIENTAL_AVENUE);
        spaces.put(7, Chance.getInstance(this));
        spaces.put(8, Space.VERMONT_AVENUE);
        spaces.put(9, Space.CONNECTICUT_AVENUE);
        spaces.put(10, Space.JAIL);
        spaces.put(11, Space.ST_CHARLES_PLACE);
        spaces.put(12, Space.ELECTRIC_COMPANY);
        spaces.put(13, Space.STATES_AVENUE);
        spaces.put(14, Space.VIRGINA_AVENUE);
        spaces.put(15, Space.PENNSYLVANIA_RAILROAD);
        spaces.put(16, Space.ST_JAMES_PLACE);
        spaces.put(17, CommunityChest.getInstance(this));
        spaces.put(18, Space.TENNESSEE_AVENUE);
        spaces.put(19, Space.NEW_YORK_AVENUE);
        spaces.put(20, Space.FREE_PARKING);
        spaces.put(21, Space.KENTUCKY_AVENUE);
        spaces.put(22, Chance.getInstance(this));
        spaces.put(23, Space.INDIANA_AVENUE);
        spaces.put(24, Space.ILLINOIS_AVENUE);
        spaces.put(25, Space.B_O_RAILROAD);
        spaces.put(26, Space.ATLANTIC_AVENUE);
        spaces.put(27, Space.VENTNOR_AVENUE);
        spaces.put(28, Space.WATER_WORKS);
        spaces.put(29, Space.MARVIN_GARDENS);
        spaces.put(30, GoToJail.getInstance(this));
        spaces.put(31, Space.PACIFIC_AVENUE);
        spaces.put(32, Space.NORTH_CAROLINA_AVENUE);
        spaces.put(33, CommunityChest.getInstance(this));
        spaces.put(34, Space.PENNSYLVANIA_AVENUE);
        spaces.put(35, Space.SHORT_LINE);
        spaces.put(36, Chance.getInstance(this));
        spaces.put(37, Space.PARK_PLACE);
        spaces.put(38, Space.LUXURY_TAX);
        spaces.put(39, Space.BOARDWALK);
        spaces.put(40, Space.GO);
    }

    //TODO: REMOVE
    public List<Rental> getUnownedRentals() {
        List<Rental> retVal = new ArrayList<Rental>();
        for (Integer key : this.spaces.keySet()) {
            Space space = this.spaces.get(key);
            if (space instanceof Rental) {
                Rental property = (Rental) space;
                if (!property.isOwned()) {
                    retVal.add(property);
                }
            }
        }
        return retVal;

    }

    public void move(Player player, Space space, boolean doubleRent) {
        int spacesToMove = findLocation(space) - this.playerLocation.get(player);
        if (spacesToMove < 0) {
            spacesToMove = 40 + spacesToMove;
        }
        progress(player, spacesToMove, doubleRent);
    }

    private int determineSpaces(int currentLocation, int newLocation) {
        if (newLocation < currentLocation) {
            newLocation += (40 - currentLocation);
        }
        return newLocation;
    }

    public void advanceToNearestRailroad(Player player) {
        int currentLocation = playerLocation.get(player);
        int reading = findLocation(Space.READING_RAILROAD);
        int bo = findLocation(Space.B_O_RAILROAD);
        int penn = findLocation(Space.PENNSYLVANIA_RAILROAD);
        int shortline = findLocation(Space.SHORT_LINE);

        if (shortline < currentLocation) {
            logger.debug("Moving " + player.toString() + " to " + Space.READING_RAILROAD.toString());
            progress(player, determineSpaces(currentLocation, reading), true);
            return;
        }

        if (bo < currentLocation) {
            logger.debug("Moving " + player.toString() + " to " + Space.SHORT_LINE.toString());
            progress(player, determineSpaces(currentLocation, shortline), true);
            return;
        }
        if (penn < currentLocation) {
            logger.debug("Moving " + player.toString() + " to " + Space.B_O_RAILROAD.toString());
            progress(player, determineSpaces(currentLocation, bo), true);
            return;
        }
        if (reading < currentLocation) {
            logger.debug("Moving " + player.toString() + " to " + Space.PENNSYLVANIA_RAILROAD.toString());
            progress(player, determineSpaces(currentLocation, penn), true);
            return;
        }
        logger.debug("Defaulting " + player.toString() + " to " + Space.READING_RAILROAD.toString());
        progress(player, determineSpaces(currentLocation, reading), true);
    }

    private int findLocation(Space space) throws RuntimeException {
        for (Integer i : spaces.keySet()) {
            if (spaces.get(i).equals(space)) {
                return i;
            }
        }
        throw new RuntimeException(space.toString() + " doesn't exist!");
    }

    public void moveToNearestUtility(Player player) {
        int currentLocation = playerLocation.get(player);
        int electric = findLocation(Space.ELECTRIC_COMPANY);
        int water = findLocation(Space.WATER_WORKS);

        if (water < currentLocation) {
            move(player, Space.ELECTRIC_COMPANY, true);
            return;
        }
        if (electric < currentLocation) {
            move(player, Space.WATER_WORKS, true);
            return;
        }
        move(player, Space.ELECTRIC_COMPANY, true);
    }

    public void generalRepairs(Player player) {
        logger.debug("General repairs...");
    }

    public void moveBackThreeSpaces(Player player) {
        logger.debug("Moving player back from " + playerLocation.get(player) + " to " + (playerLocation.get(player) - 3));
        progress(player, -3, false);
    }

    public void chairman(Player player) {
        logger.debug("It's " + player.toString() + "'s birthday!");
        for (Player otherPlayer : this.playerLocation.keySet()) {
            player.payRent(otherPlayer, 50);
        }
    }

    public void birthday(Player player) {
        logger.debug("It's " + player.toString() + "'s birthday!");
        for (Player otherPlayer : this.playerLocation.keySet()) {
            otherPlayer.payRent(player, 10);
        }

    }

    public void repairs(Player player, int house, int hotel) {

        for (Buildable b : player.getProperties().getBuildableProperties()) {
            if (b.hasBuildings()) {
                int buildings = b.getRentIndex();
                if (buildings == 5) {
                    logger.debug(player.toString() + " pays " + hotel + " for having a hotel on " + b.toString());
                    player.pay(hotel);
                } else {
                    for (int i = 1; i <= buildings; i++) {
                        logger.debug(player.toString() + " pays " + house + " for having a house on " + b.toString());
                        player.pay(house);
                    }
                }
            }
            logger.debug(player.toString() + " finished assessing damages for " + b.toString());
        }
        logger.debug("Street repairs...");
    }
}

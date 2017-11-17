/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.c2technology.monopoly.player;

import net.c2technology.monopoly.space.PropertyManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.c2technology.monopoly.space.Buildable;
import net.c2technology.monopoly.board.GameMaster;
import net.c2technology.monopoly.space.Rental;
import net.c2technology.monopoly.space.CollectibleCard;
import net.c2technology.monopoly.cards.Color;
import org.apache.log4j.Logger;

/**
 *
 * @author Chris
 */
public class Player implements Landlord, Renter, CardCollector {

    private int cash;
    private final String name;
    private final PropertyManager properties;
    private final List<CollectibleCard> cards;
    private final GamePiece piece;
    private boolean inJail;
    private static final Logger logger = Logger.getLogger(Player.class);
    private int jailTimeLeft;
    private GameMaster gm;

    public Player(String name, GamePiece piece) {
        this.name = name;
        this.cash = 1500;
        this.properties = new PropertyManager();
        this.cards = new CopyOnWriteArrayList<CollectibleCard>();
        this.piece = piece;
        this.inJail = false;
        this.jailTimeLeft = 0;
    }

    public boolean hasProperty(Rental property) {
        return this.properties.contains(property);
    }

    public void registerGameMaster(GameMaster gm) {
        this.gm = gm;
    }

    public void pay(int fine) {

        this.cash -= fine;

        if (this.cash >= 0) {
            logger.debug(this.name + " has paid a fine of " + fine);
            return;
        }
        logger.debug(toString() + "connor pay the fine of " + fine + " and is forclosing!");
        gm.repo(this.properties, this.cards);
        gm.ejectPlayer(this);

    }

    public boolean isInJail() {
        return inJail;
    }

    public void inJail(boolean inJail) {
        this.jailTimeLeft = inJail ? 3 : 0;
        logger.debug(name + (inJail ? " in put in" : " got out of") + " jail!");
        this.inJail = inJail;
    }

    public int getJailTimeLeft() {
        return this.jailTimeLeft;
    }

    public void spendJailTime() {
        logger.debug(name + " has spend a turn in jail and has " + (jailTimeLeft - 1) + " turns left!");
        this.jailTimeLeft--;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int getCash() {
        return this.cash;
    }

    public int getPropertyCount() {
        return this.properties.size();
    }

    public int getCardCount() {
        return this.cards.size();
    }

    public void collect(int cash) {
        logger.debug(name + " has collected " + cash + " cash!");
        this.cash += cash;
    }

    public void collect(int cash, List<Rental> properties, List<CollectibleCard> cards) {
        logger.debug(name + " has collected " + cash + " cash, " + properties.size() + " properties, and " + cards.size() + " cards!");
        this.cash += cash;
        this.cards.addAll(cards);
        for (Rental property : properties) {
            addProperty(property);
        }

    }

    public void addProperty(Rental property) {
        logger.debug(name + " collected property: " + property.toString());
        checkAgainstOwnedProperties(property);
        this.properties.add(property);
        property.setLandlord(this);
    }

    private void checkAgainstOwnedProperties(Rental acquired) {

        List<Rental> ownedRentalsWithSameColor = this.properties.getRentals(acquired.getColor());

        if (ownedRentalsWithSameColor.size() > 0) {
            //we have existing rentals sharing color!
            switch (acquired.getColor()) {
                case RAILROAD:
                case UTILITY:
                    boolean monopoly = false;
                    for (Rental rental : ownedRentalsWithSameColor) {
                        if (ownedRentalsWithSameColor.size() > 0) {
                            monopoly = true;
                        }
                        rental.setMonopoly(monopoly);
                    }
                    
                    acquired.setMonopoly(monopoly);
                    break;
                case LIGHT_BLUE:
                case PURPLE:
                case RED:
                case YELLOW:
                case GREEN:
                case ORANGE:
                    if (ownedRentalsWithSameColor.size() + 1 == 3) {
                        for (Rental rental : ownedRentalsWithSameColor) {
                            rental.setMonopoly(true);
                        }
                        acquired.setMonopoly(true);
                    }
                    break;
                case BROWN:
                case BLUE:
                    if (ownedRentalsWithSameColor.size() + 1 == 2) {
                        for (Rental rental : ownedRentalsWithSameColor) {
                            rental.setMonopoly(true);
                        }
                        acquired.setMonopoly(true);
                        break;
                    }
            }
        }

    }

//    public boolean sellProperty(Rental property, Landlord setLandlord) {
//        if (this.properties.contains(property)) {
//            this.properties.remove(property);
//            property.setLandlord(setLandlord);
//            checkMonopolies(property.getColor());
//            return true;
//        }
//        return false;
//    }
    public boolean equals(Player player) {
        return this.name.equals(player.toString());
    }

    public void declareBankruptcy(Landlord landlord) {
        logger.debug(this.toString() + " just declared bankruptcy to " + landlord.toString());
        landlord.collect(this.cash, this.properties.getRentals(), this.cards);
        gm.ejectPlayer(this);
    }

    /**
     * Pays the {@link Landlord} the requested rent. If the cash available does
     * not meet the rent requirement, all non-property related assets will be
     * sold. If the rent requirement is still not met, buildings will be sold
     * starting with the lowest valued property until the rent is met. If all
     * buildings are sold and the rent is not met, properties will begin to be
     * mortgaged starting with the lowest valued property. If, after all
     * properties are mortgaged, the rent is still not met, the {@link Player}
     * will declare bankruptcy and give all assets to the {@link Landlord}.
     *
     * @param landlord
     * @param rent
     */
    public void payRent(Landlord landlord, int rent) {
        logger.debug(this.toString() + " is paying " + rent + " in rent to " + landlord.toString());
        sellCards(rent);
        sellBuildings(rent);
        mortgageProperties(rent);

        //If the above didn't get us in the black then declare bankruptcy
        if (this.cash < rent) {
            logger.debug(this.toString() + " has only " + this.cash + " and cannot pay " + rent);
            declareBankruptcy(landlord);
            return;
        }
        this.cash -= rent;
        landlord.collect(rent);
    }

    public void addCard(CollectibleCard card) {
        logger.debug(name + " now collected " + card.toString());
        this.cards.add(card);
    }

    //TODO: Currently all Collectible Cards are Get Out Of Jail Free cards
    public boolean useCard() {
        if (isInJail()) {
            if (!this.cards.isEmpty()) {
                this.cards.get(0).delayedAction(this);
                this.cards.remove(0);
                return true;
            }
        }
        return false;

    }

    public void sellCard(Player player, CollectibleCard card) {
        if (this.cards.contains(card)) {
            logger.debug(name + " sold card to " + player.toString());
            player.payRent(this, 50);
            this.cards.remove(card);
            player.addCard(card);
        }
    }

    public void sellCards(int rent) {
        for (CollectibleCard card : cards) {
            if (this.cash > rent) {
                break;
            }
            logger.debug(name + " sold card to bank.");
            this.cards.remove(card);
            this.cash += card.sell();
        }
    }

    private void sellBuildings(int rent) {
        for (Buildable property : properties.getBuildableProperties()) {
            if (this.cash > rent) {
                break;
            }
            if ((property).hasBuildings()) {
                this.cash += property.sellBuilding();
            }
        }
    }

    private void mortgageProperties(int rent) {
        for (Rental property : properties.getRentals()) {
            if (this.cash > rent) {
                break;
            }
            this.cash += property.getMortgage();
            property.setMortgaged(true);
        }
    }

    public void buyProperty(Rental property) {
        this.cash -= property.getPrice();
        this.addProperty(property);
    }

    public PropertyManager getProperties() {
        return this.properties;
    }

    public List<Rental> getProperties(Color color) {
        List<Rental> retVal = new ArrayList<Rental>();
        for (Rental property : properties.getProperties()) {
            if ((property.getColor() == color)) {
                retVal.add(property);
            }
        }
        return retVal;
    }

    /**
     * Un-mortgages, if able, all properties owned from highest to lowest valued
     * beginning first with monopolies then then the remaining properties;
     */
    public void unmortgageProperties() {
        List<Rental> props = this.properties.getRentals();
        Collections.sort(props);

        for (Rental property : props) {
            if (property.isMortgaged()) {
                int payment = property.getMortgagePayment();
                if (this.cash > payment) {
                    this.cash -= payment;
                    property.setMortgaged(false);
                }
            }
        }
    }

    public void buildHouses() {
        for (Color color : Color.values()) {
            List<Buildable> buildableProperties = properties.getBuildableProperties(color);
            Collections.reverse(buildableProperties);
            if (buildableProperties.size() > 0) {
                while (cash > 0) {
                    boolean stopBuilding = false;
                    for (Buildable b : buildableProperties) {
                        if (b.canBuild()) { //check to see if we hit the upper limit
                            if (cash > b.getBuildingPrice()) {
                                b.buyBuilding();
                                continue;
                            }
                        }
                        stopBuilding = true;
                    }
                    if (stopBuilding) {
                        break;
                    }
                }
            }
        }
    }
}

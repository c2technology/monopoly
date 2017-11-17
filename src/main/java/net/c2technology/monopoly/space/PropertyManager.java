package net.c2technology.monopoly.space;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.c2technology.monopoly.cards.Color;

/**
 * Manages all {@link Property}s and access to their particular sub-types.
 * @author Chris
 */
public class PropertyManager {

    private List<Rental> rentals;

    public PropertyManager(){
        rentals = new ArrayList<Rental>();
    }
    public List<Foreclosable> getForclosures() {
        List<Foreclosable> retVal = new ArrayList<Foreclosable>();
        for (Rental property : rentals) {
            if (property instanceof Foreclosable) {
                retVal.add((Foreclosable) property);
            }
        }
        return retVal;
    }

    public List<Property> getProperties() {
        List<Property> retVal = new ArrayList<Property>();
        for (Rental property : rentals) {
            if (property instanceof Property) {
                retVal.add((Property) property);
            }
        }
        Collections.sort(retVal);
        return retVal;
    }

    public List<Railroad> getRailroads() {
        List<Railroad> retVal = new ArrayList<Railroad>();
        for (Rental property : rentals) {
            if (property instanceof Railroad) {
                retVal.add((Railroad) property);
            }
        }
        Collections.sort(retVal);
        return retVal;
    }

    public List<Rental> getRentals(Color color) {
        List<Rental> retVal = new ArrayList<Rental>();
        for (Rental property : rentals) {
            if (property.getColor().equals(color)) {
                retVal.add(property);
            }
        }
        Collections.sort(retVal);
        return retVal;
    }

    public List<Utility> getUtilities() {
        List<Utility> retVal = new ArrayList<Utility>();
        for (Rental property : rentals) {
            if (property instanceof Utility) {
                retVal.add((Utility) property);
            }
        }
        Collections.sort(retVal);
        return retVal;
    }

    /**
     * Fetch all {@link Rental}s sorted by low to high value
     * @return a sorted (low to high value) {@link List} of {@link Rental}s
     */
    public List<Rental> getRentals() {
        List<Rental> retVal = new ArrayList<Rental>();
        for (Rental property : rentals) {
            retVal.add(property);
        }
        Collections.sort(retVal);
        return retVal;
    }

    public List<Buildable> getBuildableProperties() {
        List<Buildable> retVal = new ArrayList<Buildable>();
        for (Rental property : rentals) {
            if (property instanceof Buildable) {
                retVal.add((Buildable) property);
            }
        }
        Collections.sort(retVal);
        return retVal;
    }

    /**
     * Fetches all {@link Buildable} {@link Rental}s that are eligible for buildings
     * @param color
     * @return {@link Buildable} {@link Rental}s that are eligible for buildings
     */
    public List<Buildable> getBuildableProperties(Color color) {
        List<Buildable> retVal = new ArrayList<Buildable>();
        for (Rental property : rentals) {
            if (property instanceof Buildable && property.getColor().equals(color)) {
                Buildable b = (Buildable) property;
                if (b.canBuild()){
                    retVal.add(b);
                }
            }
        }
        Collections.sort(retVal);
        return retVal;
    }

    public boolean contains(Rental rental){
        return rentals.contains(rental);
    }

    public int size(){
        return rentals.size();
    }

    public boolean add(Rental r){
        return rentals.add(r);
    }
}

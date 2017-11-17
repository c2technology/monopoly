package net.c2technology.monopoly;

import net.c2technology.monopoly.board.Board;
import net.c2technology.monopoly.board.GameMaster;
import net.c2technology.monopoly.board.Dice;
import java.util.List;
import net.c2technology.monopoly.player.Player;
import net.c2technology.monopoly.space.CollectibleCard;
import net.c2technology.monopoly.space.Foreclosable;
import net.c2technology.monopoly.space.PropertyManager;
import net.c2technology.monopoly.space.Rental;
import org.apache.log4j.Logger;

/**
 *
 * @author Chris
 */
public class Monopoly implements GameMaster {

    private List<Player> players;
    private Dice dice;
    private int turn;
    private Board board;
    private static final Logger logger = Logger.getLogger(Monopoly.class);
    private boolean ejected = true;

    /**
     * Constructor
     * @param players The {@link Player} of the game
     */
    public Monopoly(List<Player> players) {
        this.players = players;
        this.dice = new Dice();
        this.board = new Board(players);
        registerListeners(players);
    }

    private void registerListeners(List<Player> players) {
        for (Player player : players) {
            player.registerGameMaster(this);
        }
    }

    /**
     * Runs the game.
     */
    public void play() {
        logger.debug("Let's play Monopoly!");
        while (players.size() > 1) {
            logger.debug("There are still " + players.size() + " players left!");
            turn++;
            logger.debug(" ====== Beginning Turn: " + turn + " ====== ");
            for (Player player : players) {
                logger.debug("Go " + player.toString() + "!");
                ejected = false;
                turn(player);
            }
//            if (turn == 500) {
//                break;
//            }
            logger.debug(" ===== Ending Turn: " + turn + " ===== ");
        }
        logEndGame();

    }

    /**
     * A single turn in the game
     * @param player The {@link Player} who's turn it is
     */
    private void turn(Player player) {
        int consecutiveDoubles = 0;
        boolean doubles = false;
        do {
            player.unmortgageProperties();
            player.buildHouses();
            logger.debug("Rolling for: " + player.toString() + "[Cash: " + player.getCash() + "]");
            int roll = dice.roll();
            doubles = dice.doubles();
            logger.debug(" Roll: " + roll);
            if (doubles) {
                logger.debug("Doubles!");
                if (player.isInJail()) {
                    player.inJail(false);
                    doubles = false;
                } else {
                    consecutiveDoubles++;
                    logger.debug(player.toString() + " has rolled " + consecutiveDoubles + " doubles!");
                    if (consecutiveDoubles == 3) {
                        board.goToJail(player);
                        break;
                    }
                }
            }

            if (player.isInJail()) {
                if (!player.useCard()) {
                    if (player.getCash() > 50) {
                        logger.debug(player.toString() + " paid $50 to get out of Jail!");
                        player.pay(50);
                        player.inJail(false);
                    } else {
                        player.spendJailTime();
                        logger.debug(player.toString() + " has spend a turn in jail! [" + player.getJailTimeLeft() + " turns left]");
                        if (player.getJailTimeLeft() == 0) {
                            player.pay(50);
                            player.inJail(false);
                            doubles = false;
                        }
                    }
                }
            }


            if (!player.isInJail()) {
                board.progress(player, roll, false);
            }
            //A player could have been ejected after rolling doubles.
            if (ejected) {
                logger.debug(player.toString() + ", YOU DO NOT EXIST!");
            } else {
                if (doubles) {
                    logger.debug(player.toString() + " goes again!");
                }
            }
        } while (doubles && !ejected);
    }

    /**
     * Remove given {@link Player} from the game. All necessary actions need to
     * be taken before this is called or all entities the {@link Player} has
     * will be unavailable to the other {@link Player}s
     * @param player The {@link Player} to remove from the game
     */
    public void ejectPlayer(Player player) {
        logger.debug(player.toString() + " has been ejected from the game!");
        this.players.remove(player);
        ejected = true;
    }

    /**
     * A Debugging function to print the end results to screen.
     */
    public void logEndGame() {
        logger.debug("***************The game has ended after " + turn + " turns!");
        logger.debug(players.size() + " players remain!");
        for (Player player : players) {
            logger.debug("Stats for " + player.toString());
            logger.debug(" Ending cash: " + player.getCash());
            logger.debug(" Cards owned: " + player.getCardCount());
            logger.debug(" Properties owned: " + player.getPropertyCount());
            PropertyManager properties = player.getProperties();
            for (Rental property : properties.getRentals()) {
                logger.debug(" +-- " + property.toString());
                logger.debug(" | Rent        " + property.getRent());
                logger.debug(" | Rent Index  " + property.getRentIndex());
                logger.debug(" | Mortgaged?  " + property.isMortgaged());
                logger.debug(" | Monopoly?   " + property.isMonopoly());
            }
        }
        logger.debug("Unowned Properties:");
        for (Rental property : board.getUnownedRentals()) {
            logger.debug(" --> " + property.toString() + " (" + property.getPrice() + ")");
        }
    }

    /**
     * Makes the given {Link PropertyManager} and {@link CollectibleCard}s available
     * to other {@link Player}s. This is generally called before a {@link Player}
     * is ejected from the game.
     * @param forclosures The {@link PropertyManager} to be repossessed
     * @param cards The {@link CollectibleCard}s to be repossessed
     */
    public void repo(PropertyManager forclosures, List<CollectibleCard> cards) {
        for (Foreclosable f : forclosures.getForclosures()){
            logger.debug(f.toString() + " has been repossessed!");
            f.forclose();
        }
        for (CollectibleCard c : cards){
            logger.debug(c.toString() + " has been destroyed!");
            c = null;
        }
    }
}

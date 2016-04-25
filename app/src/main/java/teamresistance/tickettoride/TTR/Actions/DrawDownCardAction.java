package teamresistance.tickettoride.TTR.Actions;

import java.io.Serializable;

import teamresistance.tickettoride.Game.GamePlayer;
import teamresistance.tickettoride.Game.actionMsg.GameAction;

/**
 *  Draws a card from a face down deck
 *
 * @author Nick Scacciotti
 * @author Nick Larson
 * @author Jess Mann
 * @author Parker Schibel
 * @version April 2016
 */
public class DrawDownCardAction extends GameAction implements Serializable {
    private static final long serialVersionUID = 388240786564192016L;

    /**
     * Draws a card from a face down deck
     * @param player
     */
    public DrawDownCardAction(GamePlayer player) {
        super(player);
    }
}

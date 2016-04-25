package teamresistance.tickettoride.TTR.Actions;

import java.io.Serializable;

import teamresistance.tickettoride.Game.GamePlayer;
import teamresistance.tickettoride.Game.actionMsg.GameAction;

/**
 * Changes the GameAction made to the new playerC
 *
 * @author Nick Scacciotti
 * @author Nick Larson
 * @author Jess Mann
 * @author Parker Schibel
 * @version April 2016
 */
public class ChangeModeAction extends GameAction implements Serializable {
    private static final long serialVersionUID = 3884322323564192016L;

    /**
     * Changes the GameAction made to the new player
     * @param player
     */
    public ChangeModeAction(GamePlayer player) {
        super(player);
    }
}
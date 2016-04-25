package teamresistance.tickettoride.TTR;
import java.io.*;
/**
 * A class meant to be inherited from by the other cards
 *
 * @author Nick Scacciotti
 * @author Nick Larson
 * @author Jess Mann
 * @author Parker Schibel
 * @version April 2016
 */

public class Card implements Serializable{
    private static final long serialVersionUID = 288446564192016L;
    private boolean highlight = false;
    /**
     * Sets the highlight variable to the new variable
     *
     * @param highlighted- new boolean value for if highlighted or not
     */
    public void setHighlight(boolean highlighted){
        this.highlight = highlighted;
    }

    /**
     * Tells whether the Destination ticket is highlighted or not
     *
     * @return
     *      Returns true or false
     */
    public boolean getHighlight(){
        return highlight;
    }

    public String toString(){
        return "Card";
    }
}

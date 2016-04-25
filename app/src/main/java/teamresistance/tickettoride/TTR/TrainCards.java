package teamresistance.tickettoride.TTR;

import java.io.Serializable;

/**
 * Train Card class. Contains the train card colors and sets the card to a specific type.
 *
 * @author Nick Scacciotti
 * @author Nick Larson
 * @author Jess Mann
 * @author Parker Schibel
 * @version April 2016
 */
public class TrainCards extends Card implements Serializable {
    private static final long serialVersionUID = 777245564192016L;
    //String of names of the different train card colors
    private final String[] trainCarNames = {"Yellow", "Blue", "Orange", "White",
            "Pink", "Black", "Red", "Green",
            "Rainbow", "Blank"};

    //instance variables for what color type and if highlighted
    private String type;

    /**
     * Constructor for TrainCards class
     *
     * @param typeNum the int value for the color type in the
     *                trainCarNames array     *
     */
    public TrainCards(int typeNum){
        type = trainCarNames[typeNum];
        setHighlight(false);
    }

    /**
     * Gives the color type of the Train Card
     *
     * @return
     *      Returns the type of card
     */
    @Override
    public String toString(){
        return this.type;
    }

    /**
     * Gives the color type of the Train Card
     *
     * @return
     *      Returns the card type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the Train card type to a new color type
     *
     * @param type - new color type
     */
    public void setType(String type) {
        this.type = type;
    }
}

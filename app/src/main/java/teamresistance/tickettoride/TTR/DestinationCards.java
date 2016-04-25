package teamresistance.tickettoride.TTR;

import java.io.Serializable;

/**
 *  DestinationCards is a class representing destination cards and extends Card class
 *
 * @author Nick Scacciotti
 * @author Nick Larson
 * @author Jess Mann
 * @author Parker Schibel
 * @version March 2016
 */
public class DestinationCards extends Card implements Serializable {
    private static final long serialVersionUID = 558245564192016L;
    //instance variables for beginning and end destinations, score, and if highlighted/claimed
    /** The first city listed on a destination card */
    private String city1;
    /** The second city listed on a destination card */
    private String city2;
    /** The number of points that can be gained from completing the card */
    private int score;

    //String array containing city names
    private final String[] destNames = {"Denver", "El Paso", "Kansas City",
            "Houston", "New York", "Atlanta", "Chicago", "New Orleans", "Calgary",
            "Salt Lake City", "Helena", "Los Angeles", "Duluth", "Sault Ste Marie",
            "Nashville", "Montreal", "Oklahoma City", "Seattle", "Santa Fe",
            "Toronto", "Miami", "Portland", "Phoenix", "Dallas",
            "Pittsburgh", "Winnipeg", "Little Rock", "Boston", "Vancouver",
            "San Francisco"};

    //The first locations labeled on the destination cards based on the destNames array
    private int[] cities1 = {11, 12, 13, 4, 21, 28, 12, 19, 21, 23, 8, 8, 11, 25, 29, 2,
            11, 0, 6, 28, 27, 6, 15, 17, 0, 10, 25, 15, 13, 17};

    //The second locations labeled on the destination cards based on the destNames array
    private int[] cities2 = {4, 3, 14, 5, 14, 15, 1, 20, 22, 4, 9, 22, 20, 26, 5, 3,
            6, 24, 18, 18, 20, 7, 5, 4, 1, 11, 3, 7, 16, 11};

    private int[] scores = {21, 8, 8, 6, 17, 20, 10, 10, 11, 11, 7, 13, 20, 11, 17, 5, 16,
            11, 9, 13, 12, 7, 9, 22, 4, 8, 12, 13, 9, 9};

    /**
     * Constructor for Destination Cards
     *
     * @param dest1 an index to the first city
     * @param dest2 an index to the second city
     * @param point the value received for completing the track
     */
    public DestinationCards(int dest1, int dest2, int point){
        if(dest1 > destNames.length-1){
            city1 = "";
        }
        else if (dest2 > destNames.length-1){
            city2 = "";
        } else {
            city1 = destNames[cities1[dest1]];
            city2 = destNames[cities2[dest2]];
        }
        score = scores[point];
        setHighlight(false);
    }

    /**
     * Sets the score of the ticket to a new value
     *
     * @param newScore- the new destination tickets score value
     */
    public void setScore(int newScore){
        this.score = newScore;
    }

    /**
     * Tells the destination ticket's score
     *
     * @return
     *      returns the score
     */
    public int getScore(){
        return score;
    }

    public String getCity1(){return this.city1;}
    public String getCity2(){return this.city2;}
}

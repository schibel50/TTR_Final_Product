package teamresistance.tickettoride.TTR;

import android.graphics.Path;
import android.graphics.Rect;

import java.io.Serializable;

/**
 *  Track is a class representing the tracks
 *
 * @author Nick Scacciotti
 * @author Nick Larson
 * @author Jess Mann
 * @author Parker Schibel
 * @version March 2016
 */
public class Track implements Serializable {
    private static final long serialVersionUID = 388245564192016L;
    //instance variables for tracks
    private int trainTrackNum = 0;
    private int playerID = -1;
    private String trackColor = null;
    private boolean selected = false;
    private boolean highlight = false;
    private boolean covered = false;
    private String startCity = null;
    private String endCity = null;
    private boolean selectHighlight = false;

    /**
     * Constructor for track object
     * @param trainTrackNum the length/number of train cars needed to claim route
     * @param trackColor  color assigned to track route
     * @param secondCity collection of paths representing the locations for placed train tokens
     * @param firstCity area around path that is selectable by user
     */
    public Track(int trainTrackNum, String trackColor, String firstCity, String secondCity){
        this.trainTrackNum = trainTrackNum;
        this.trackColor = trackColor;
        this.startCity = firstCity;
        this.endCity = secondCity;
        this.highlight = false;
        this.selected = false;
    }

    /**
     * Copy constructor for track object
     * @param orig original track object
     */
    public Track(Track orig){
        this.trainTrackNum = orig.getTrainTrackNum();
        this.trackColor = orig.getTrackColor();
        this.startCity = orig.getStartCity();
        this.endCity = orig.getEndCity();
        this.highlight = orig.getHighlight();
        this.selected = orig.getSelected();
    }

    /**
     * Sets whether the track is highlighted or not
     *
     * @param val - new value for if track is highlighted
     */
    public void setHighlight(Boolean val){
        highlight = val;
    }

    /**
     * Retruns if highlighted
     *
     * @return
     *      Returns true/false if track is highlighted
     */
    public Boolean getHighlight(){
        return highlight;
    }

    /**
     * Returns the tracknumber
     * @return
     */
    public int getTrainTrackNum(){
        return trainTrackNum;
    }


    /**
     * Checks if track is selected
     * @return
     */
    public Boolean getSelected(){
        return selected;
    }

    /**
     * Sets the tracks new selected value
     * @param val - new boolean for if selected
     */
    public void setSelected(Boolean val){
        selected = val;
    }

    /**
     * Retruns if the track is covered
     * @return
     */
    public Boolean getCovered(){
        return covered;
    }

    public int getPlayerID(){return playerID;}

    public void setPlayerID(int val){this.playerID = val;}

    /**
     * Sets whether the track is/isn't selected
     * @param val - new boolean for if covered
     */
    public void setCovered(Boolean val){
        covered = val;
    }

    public void setTrainTrackNum(int trainTrackNum) {
        this.trainTrackNum = trainTrackNum;
    }

    public String getTrackColor() {
        return trackColor;
    }

    public void setTrackColor(String trackColor) {
        this.trackColor = trackColor;
    }

    public String getStartCity() {
        return startCity;
    }

    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    public String getEndCity() {
        return endCity;
    }

    public void setEndCity(String endCity) {
        this.endCity = endCity;
    }

    /**
     * Returns if selected
     * @return
     */
    public boolean isSelectHighlight() {
        return selectHighlight;
    }

    public void setSelectHighlight(boolean selectHighlight) {
        this.selectHighlight = selectHighlight;
    }
}

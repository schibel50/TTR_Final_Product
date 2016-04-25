package teamresistance.tickettoride.TTR.DijkstraAlg;

import java.io.Serializable;

/**
 * @author Nicholas Larson
 * @author Parker Schibel
 * @author Jess Mann
 * @author Nick Scaciotti
 * @version April 2016
 *
 * Creates the nodes for the dijkstra algorithm
 *
/**
External Citation
Date: 4/18/2016
Problem: Had trouble re-visualizing the structure of Dijkstra's algorithm
Resource:
https://www.cs.usfca.edu/~galles/visualization/Dijkstra.html
and C-code from Data Structures class(Spring 2015)
Solution: This tool helped me to visualize the step by step process better
and build my graph based on that. The previously written program helped me check
my steps along the way
 *
 * @author Nick Scacciotti
 * @author Nick Larson
 * @author Jess Mann
 * @author Parker Schibel
 * @version April 2016
 */
public class Vertex implements Serializable {
    private static final long serialVersionUID = 380426764192016L;

    final private String name;      //name of the city this Vertex represents
    final private int id;           //id number of the Vertex
    private int distance = -1;      //how far the Vertex is from the source Vertex
    private boolean known = false;  //if during the algorithm this Vertex has been relaxed
    private int predecessor = -1;   //assists in finding the shortest path.

    /**
     * Constructor for Vertex object
     * @param name
     * @param id
     */
    public Vertex(String name, int id) {
        this.name = name;
        this.id = id;
    }
    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public int getDistance(){
        return distance;
    }

    public void setDistance(int distance){
        this.distance = distance;
    }

    public boolean getKnown(){
        return known;
    }

    public void setKnown(boolean known){
        this.known = known;
    }

    public int getPredecessor(){
        return predecessor;
    }

    public void setPredecessor(int predecessor){
        this.predecessor = predecessor;
    }

    @Override
    public String toString() {
        return name;
    }
}

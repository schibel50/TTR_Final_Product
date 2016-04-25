package teamresistance.tickettoride.TTR.DijkstraAlg;

import teamresistance.tickettoride.TTR.Track;

import java.io.Serializable;

/**
 * @author Nicholas Larson
 * @author Parker Schibel
 * @author Jess Mann
 * @author Nick Scaciotti
 * @version April 2016
 *
 * Creates the edges for the dijkstra algorithm
 *
 * External Citation
Date: 4/18/2016
Problem: Had trouble re-visualizing the structure of Dijkstra's algorithm
Resource:
https://www.cs.usfca.edu/~galles/visualization/Dijkstra.html
and C-code from Data Structures class(Spring 2015)
Solution: This tool helped me to visualize the step by step process better
and build my graph based on that. The previously written program helped me check
my steps along the way.
 */
public class Edge implements Serializable {
    private final Track track;
    private static final long serialVersionUID = 386249734192016L;
    private final String city1; //first city attached to edge. helps to identify correct edge in algorithm
    private final String city2; //second city attached to edge. helps to identify correct edge in algorithm
    private final Vertex v1;    //first vertex of the edge, corresponding to the first city
    private final Vertex v2;    //second vertex of the edge, corresponding to the second city
    private final int weight;   //how many tracks are in this edge.

    /**
     * Constructor for Edge object
     * @param track
     * @param v1
     * @param v2
     * @param weight
     */
    public Edge(Track track, Vertex v1, Vertex v2, int weight) {
        this.track = track;
        this.city1 = track.getStartCity();
        this.city2 = track.getEndCity();
        this.v1 = v1;
        this.v2 = v2;
        this.weight = weight;
    }

    public Track getTrack(){ return track; }
    public Vertex getV1() {
        return v1;
    }

    public Vertex getV2() {
        return v2;
    }
    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return v1.getName() + " to " + v2.getName();
    }
}

package teamresistance.tickettoride.TTR.DijkstraAlg;


import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Creates the nodes for the dijkstra algorithm
 *
 * * External Citation
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

public class Dijkstra implements Serializable {
    private static final long serialVersionUID = 3867985564192016L;
    private DijkstraGraph myGraph;     //the graph that is being evaluated
    final private int MAX = 100000000; //the maximum distance a Vertex can have

    /**
     * Constructor for Dijkstra object
     * @param myGraph
     */
    public Dijkstra(DijkstraGraph myGraph){
        this.myGraph = myGraph;
    }

    /**
     * Runs the dijkstra algorithm on the graph pertaining to this class.
     *
     * @param source this is the starting Vertex in myGraph from where the
     *               algorithm will evaluate the source's distances from other nodes.
     */
    public void dijkstra(int source) {

        //initialize everything so that it has the max distance and no predecessors
        for (Vertex vertex : myGraph.getVertexes()) {
            vertex.setDistance(MAX);
            vertex.setKnown(false);
            vertex.setPredecessor(-1);
        }

        //set the source as the first vertex to be evaluated and set its distance to 0.
        int currentVertex = source;
        myGraph.getVertexes().get(currentVertex).setDistance(0);

        //relax all of the edges until every reachable node has been evaluated.
        while(currentVertex != -1){
            relax(currentVertex);
            currentVertex = findSmallest();
        }
    }

    /**
     * Takes in a vertex position and finds all of its neighbors via their edges
     *
     * @param vertex this is the vertex who's neighbors are being found
     * @return neighbors    the ArrayList of all the edges that contain
     *                      neighbors of the given vertex
     */
    private ArrayList<Edge> getNeighbors(Vertex vertex){

        //an arrayList of the edges that connect the vertex to its neighbors
        ArrayList<Edge> neighbors = new ArrayList<Edge>();
        for(Edge edge: myGraph.getEdges()){
            //if the current edge has vertex as one of its vertexes, add it to the list.
            if(edge.getV1().getName().equals(vertex.getName())
                    || edge.getV2().getName().equals(vertex.getName())){
                neighbors.add(edge);
            }
        }
        //return the ArrayList to help with getting distances
        return neighbors;
    }

    /**
     * Takes in a vertex position and evaluates all of its neighbors, giving them
     * all the shortest distances they are from the source.
     *
     * @param currentVertex this is the vertex that is having its neighbor's distances evaluated
     */
    private void relax(int currentVertex){

        //an arrayList of edges that contain all of the current Vertexes neighbors via their edges.
        ArrayList<Edge> neighbors = getNeighbors(myGraph.getVertexes().get(currentVertex));

        //go through all of the edges.
        for(Edge edge: neighbors){
            int checking = -1;

            //These strings are used to help evaluate which Vertex in the edge is the current Vertex
            String check1 = edge.getV1().getName();
            String check2 = edge.getV2().getName();

            //finds the Vertex it needs to check
            if(check1.equals(myGraph.getVertexes().get(currentVertex).getName())){
                checking = edge.getV2().getId();
            }
            else if(check2.equals(myGraph.getVertexes().get(currentVertex).getName())){
                checking = edge.getV1().getId();
            }

            //if the Vertex being checked has already saved a distance from the source that
            //is larger than the current Vertex's distance plus the weight of the edge,
            //give the vertex the new shorter distance, otherwise, leave it alone
            if(myGraph.getVertexes().get(checking).getDistance() >
                    (edge.getWeight() + myGraph.getVertexes().get(currentVertex).getDistance())){
                myGraph.getVertexes().get(checking).setDistance(
                        edge.getWeight() + myGraph.getVertexes().get(currentVertex).getDistance());
                myGraph.getVertexes().get(checking).setPredecessor(currentVertex);
            }
        }
        //set the current Vertex's known boolean to true to show that this Vertex has
        //had all of its neighbors checked.
        myGraph.getVertexes().get(currentVertex).setKnown(true);
    }

    /**
     * Finds the Vertex with the currently smallest distance value from the source.
     *
     * @return smallestId The id number of the Vertex with the smallest distance
     */
    private int findSmallest(){
        int smallest = MAX;
        int smallestId = -1;
        for(Vertex vertex: myGraph.getVertexes()){
            if(!vertex.getKnown()){
                //if this vertex has a smaller distance than the current smallest one
                //change the value of smallest.
                if(vertex.getDistance() < smallest){
                    smallest = vertex.getDistance();
                    smallestId = vertex.getId();
                }
            }
        }
        return smallestId;
    }

    /**
     * Takes in two positions and finds the shortest path between them, saves
     * that path as an ArrayList of Vertexes. Used to find the Vertexes to meet necessary for
     * completion of a route
     *
     * @param source        the starting Vertex position
     * @param destination   the final Vertex
     *
     * @return path         the ArrayList that contains the shortest path from
     *                      the source to the destination.
     */
    public ArrayList<Vertex> getPath(int source, int destination){
        dijkstra(source);
        ArrayList<Vertex> path = new ArrayList<Vertex>();
        Vertex currentVertex = myGraph.getVertexes().get(destination);

        //add the destination vertex as the first part of the path
        path.add(currentVertex);

        //the predecessor of the source is -1, therefore, path all the following
        //vertexes predecessors until you reach -1.
        while(currentVertex.getPredecessor() != -1){
            int newVertex = currentVertex.getPredecessor();
            currentVertex = myGraph.getVertexes().get(newVertex);
            path.add(currentVertex);
        }
        return path;
    }

    /**
     * Takes in two positions and returns all of the edges of the shortest path between them
     *
     * @param source        the starting Vertex position
     * @param destination   the final Vertex
     *
     * @return myEdges      The ArrayList of all the edges between a source and its destination
     */
    public ArrayList<Edge> getEdges(int source, int destination){
        //evaluate all of the Vertexes and their distances from the source
        dijkstra(source);

        //Get all of the Vertexes
        ArrayList<Vertex> usedVertices = getPath(source, destination);
        ArrayList<Edge> myEdges = new ArrayList<Edge>();

        //Cycle through the Vertexes we know exist on the path.
        for(Vertex vertex: usedVertices){
            //if an edge contains the Vertex, check to see if its the source
            for(Edge edge: myGraph.getEdges()){
                if(edge.getV1().getName().equals(vertex.getName())
                   || edge.getV2().getName().equals(vertex.getName())){
                    //if the vertex is the source, do nothing
                    if(vertex.getPredecessor() == -1){}
                    //otherwise, add to the list
                    else if(edge.getV1().getName().equals(myGraph.getVertexes().get(vertex.getPredecessor()).getName())
                            || edge.getV2().getName().equals(myGraph.getVertexes().get(vertex.getPredecessor()).getName())){
                        myEdges.add(edge);
                    }
                }
            }
        }
        return myEdges;
    }

    /**
     * Evaluates all of the Vertexes and determines if it is the start of a track.
     * Used to calculate the longest continuous track by providing vertexes that only have
     * one edge claimed by a player
     *
     */
    public ArrayList<Vertex> getSingleNeighbor(){
        ArrayList<Vertex> lonelyNeighbor = new ArrayList<Vertex>();
        for(Vertex vertex: myGraph.getVertexes()){
            //if there is only one neighbor connected to the Vertex that has been claimed
            //add it to the list.
            if(getNeighbors(vertex).size() == 1){
                lonelyNeighbor.add(vertex);
            }
        }
        return lonelyNeighbor;
    }

    public DijkstraGraph getMyGraph(){
        return myGraph;
    }

    public void clear(){
        myGraph.clear();
    }

    public boolean isEmpty(){
        if(myGraph.getVertexes().isEmpty() || myGraph.getEdges().isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }
}

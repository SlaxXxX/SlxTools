package de.slxSoft.graphGen;

/**
 * Generates a random curve revolving around a predefined function and returns it as an integer array.
 * for the setup, look up the javadoc of the setter methods of the Graph.
 *
 * Later, this Generator will Manage multiple Graphs
 * and probably have more cool functions :P
 */
public class Generator {

    private Graph graph;

    public Generator() {
        graph = new Graph();
    }

    /**
     * Generates values with previously set-up variables in the Graph
     * @return Returns the Array of generated Values
     * For later Versions, that can generate multiple Graphs at once, use this generate() method
     * As of now, you may also use the generate() method directly from the Graph
     */
    public int[] generate(){
        return graph.generate();
    }

    public Graph getGraph(){
        return graph;
    }

}

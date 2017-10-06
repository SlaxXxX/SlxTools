package de.slxSoft.graphGen;

/**
 * Created by SlaxX on 05.10.2017.
 */
public class Generator {

    private Graph graph;

    public Generator() {
        graph = new Graph();
    }

    public int[] generate(){
        return graph.generate();
    }

    public Graph getGraph(){
        return graph;
    }

}

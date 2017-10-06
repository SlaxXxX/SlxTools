package de.slxSoft.graphGen;

/**
 * Created by SlaxX on 05.10.2017.
 */
public class Generator {

    Graph graph;

    public Generator() {
        graph = new Graph();
    }

    public int[] generate(){
        return graph.generate();
    }

}

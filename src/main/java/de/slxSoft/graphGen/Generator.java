package de.slxSoft.graphGen;

import java.util.*;

/**
 * Generates a random curve revolving around a predefined function and returns it as an integer array<br>
 * for the setup, look up the javadoc of the setter methods of the Graph<br><br>
 */
public class Generator {

    private LinkedHashMap<Long, Graph> graphs = new LinkedHashMap<>();
    private final long internalSeed = 42;
    private long seed;
    private Random seeder = new Random(internalSeed);

    /**
     * Creates a random seed for the Graphs
     */
    public Generator() {
        seed = new Random().nextLong();
    }

    /**
     * Uses the seed to create an exact copy<br><br>
     *
     * @param seed Seed to use for random generation
     */
    public Generator(long seed) {
        this.seed = seed;
    }

    /**
     * Returns the Generators seed and all the graphs settings<br>
     *
     * @return The seed
     */
    public String getSettings() {
        StringBuilder settings = new StringBuilder();
        settings.append("G" + graphs.size());
        graphs.values().forEach(graph ->
                settings.append("F" + graph.getFunction())
                        .append("C" + graph.getChangeCooldown())
                        .append("P" + graph.getChangeProbability())
                        .append("D" + graph.getSubdecimals())
                        .append("O" + graph.getMaxOffset())
                        .append("R" + graph.getRelativeToLast())
                        .append("S" + graph.getSize()));
        settings.append("SEED"+seed);
        return settings.toString();
    }

    /**
     * adds another Graph to the Generator
     */
    public Graph addGraph() {
        Graph graph = new Graph();
        graphs.put(seeder.nextLong(), graph);
        return graph;
    }

    /**
     * adds specified amount of Graphs to the Generator
     */
    public Graph[] addGraphs(int i) {
        Graph[] graphs = new Graph[i];
        for (i += 0; i > 0; i--) {
            Graph graph = new Graph();
            this.graphs.put(seeder.nextLong(), graph);
            graphs[i - 1] = graph;
        }
        return graphs;
    }


    /**
     * Generates values with previously set-up variables in the Graph<br>
     *
     * @return Returns the Array of generated Values<br><br>
     * <p>
     * use this generate() method to generate all Graphs in a two-dimensional array<br>
     * As of now, you may also use the generate() method directly from the Graph<br>
     */
    public double[][] generate() {
        List<double[]> list = new ArrayList<>();
        for (Map.Entry<Long, Graph> entry : graphs.entrySet()) {
            list.add(entry.getValue().generate(entry.getKey() % seed));
        }
        return list.toArray(new double[0][0]);
    }

    /**
     * Returns the Graph with requested index<br>
     * by default there is a Graph with index 0<br><br>
     *
     * @param index The index of the Graph<br>
     * @return The Graph
     */
    public Graph getGraph(int index) {
        return (Graph) getElementByIndex(graphs, index);
    }

    /**
     * @return Amount of Graphs stored in this Generator
     */
    public int getGraphCount() {
        return graphs.size();
    }

    public Object getElementByIndex(LinkedHashMap map, int index) {
        return map.get((map.keySet().toArray())[index]);
    }

}

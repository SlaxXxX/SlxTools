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
        graphs.put(seeder.nextLong(), new Graph());
    }

    /**
     * Uses the seed to create an exact copy<br><br>
     *
     * @param seed Seed to use for random generation
     */
    public Generator(long seed) {
        this.seed = seed;

        graphs.put(seeder.nextLong(), new Graph());
    }
    
    /**
     * Returns the Generators seed<br>
     * @return The seed
     */
    public long getSeed() {
    	return seed;
    }

    /**
     * adds another Graph to the Generator
     */
    public void addGraph() {
        graphs.put(seeder.nextLong(), new Graph());
    }

    /**
     * adds specified amount of Graphs to the Generator
     */
    public void addGraph(int i) {
        for (i += 0; i > 0; i--) {
            graphs.put(seeder.nextLong(), new Graph());
        }
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

package de.slxSoft.graphGen;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * @param settings The settings of the initial Generator
     */
    public Generator(String settings) {
        Matcher matcher = Pattern.compile("G(\\d+)(.+)SEED(-?\\d+)").matcher(settings);
        matcher.matches();
        seed = Long.parseLong(matcher.group(3));
        this.addGraphs(Integer.parseInt(matcher.group(1)));
        Matcher graphMatcher = Pattern.compile("F([x\\W0-9]+)C(\\d+)P(\\d+)D(\\d+)O(\\d+\\.\\d+)R(true|false)S(\\d+)").matcher(matcher.group(2));

        Iterator<Graph> it = graphs.values().iterator();
        while(graphMatcher.find()) {
            Graph graph = it.next();
            graph.setFunction(graphMatcher.group(1));
            graph.setCooldownSpeed(Integer.parseInt(graphMatcher.group(2)));
            graph.setChangeProbability(Integer.parseInt(graphMatcher.group(3)));
            graph.setSubdecimals(Integer.parseInt(graphMatcher.group(4)));
            graph.setMaxOffset(Double.parseDouble(graphMatcher.group(5)));
            graph.setRelativeToLast(Boolean.parseBoolean(graphMatcher.group(6)));
            graph.setSize(Integer.parseInt(graphMatcher.group(7)));
        }
    }

    /**
     * Returns the Generators seed and all the graphs settings<br>
     *
     * @return The seed
     */
    public String getSettings() {
        StringBuilder settings = new StringBuilder();
        settings.append("G" + graphs.size());
        Iterator<Graph> it = graphs.values().iterator();
        while(it.hasNext()) {
            Graph graph = it.next();
            settings.append("F" + graph.getFunction())
                    .append("C" + graph.getCooldownSpeed())
                    .append("P" + graph.getChangeProbability())
                    .append("D" + graph.getSubdecimals())
                    .append("O" + graph.getMaxOffset())
                    .append("R" + graph.getRelativeToLast())
                    .append("S" + graph.getSize());
        }

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
        for (int j = 0; j < i; j++) {
            Graph graph = new Graph();
            this.graphs.put(seeder.nextLong(), graph);
            graphs[j] = graph;
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

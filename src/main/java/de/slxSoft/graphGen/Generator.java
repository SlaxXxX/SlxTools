package de.slxSoft.graphGen;

import javax.crypto.spec.DESedeKeySpec;
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
        Matcher matcher = Pattern.compile("G(\\d+)(.+)E(-?\\d+)").matcher(settings);
        matcher.matches();
        seed = Long.parseLong(matcher.group(3));
        this.addGraphs(Integer.parseInt(matcher.group(1)));
        Matcher graphMatcher = Pattern.compile("IF?((?:[x\\W0-9]+)?)C?((?:\\d+)?)P?((?:\\d+)?)D?((?:\\d+)?)O?((?:\\d+\\.\\d+)?)R?((?:t|f)?)S?((?:\\d+)?)").matcher(matcher.group(2));

        Iterator<Graph> it = graphs.values().iterator();
        while (graphMatcher.find()) {
            Graph graph = it.next();
            if (!graphMatcher.group(1).isEmpty())
                graph.setFunction(graphMatcher.group(1));
            if (!graphMatcher.group(2).isEmpty())
                graph.setCooldownSpeed(Integer.parseInt(graphMatcher.group(2)));
            if (!graphMatcher.group(3).isEmpty())
                graph.setChangeProbability(Integer.parseInt(graphMatcher.group(3)));
            if (!graphMatcher.group(4).isEmpty())
                graph.setSubdecimals(Integer.parseInt(graphMatcher.group(4)));
            if (!graphMatcher.group(5).isEmpty())
                graph.setMaxOffset(Double.parseDouble(graphMatcher.group(5)));
            if (!graphMatcher.group(6).isEmpty())
                graph.setRelativeToLast(graphMatcher.group(6).equals("t"));
            if (!graphMatcher.group(7).isEmpty())
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
        while (it.hasNext()) {
            Graph graph = it.next();
            settings.append("I")
                    .append(graph.getFunction().equals(Defaults.function) ? "" : "F" + graph.getFunction())
                    .append(graph.getCooldownSpeed() == Defaults.cooldownSpeed ? "" : "C" + graph.getCooldownSpeed())
                    .append(graph.getChangeProbability() == Defaults.changeProbability ? "" : "P" + graph.getChangeProbability())
                    .append(graph.getSubdecimals() == Defaults.subdecimals ? "" : "D" + graph.getSubdecimals())
                    .append(graph.getMaxOffset() == Defaults.maxOffset ? "" : "O" + graph.getMaxOffset())
                    .append(graph.getRelativeToLast() == Defaults.relativeToLast ? "" : "R" + (graph.getRelativeToLast() ? "t" : "f"))
                    .append(graph.getSize() == Defaults.size ? "" : "S" + graph.getSize());
        }

        settings.append("E" + seed);
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

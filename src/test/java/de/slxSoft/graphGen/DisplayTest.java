package de.slxSoft.graphGen;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by SlaxX on 06.10.2017.
 */
public class DisplayTest {
    static java.util.List<double[][]> values;
    static final int GRAPHS = 10;
    static final int STACKS = 1;

    public static void main(String[] args) {
        values = new ArrayList<>();

        Generator generator = new Generator();
        Graph[] graphs = generator.addGraphs(GRAPHS);
        for (int i = 0; i < GRAPHS; i++) {
            graphs[i].setRelativeToLast(false);
            graphs[i].setChangeProbability(0);
            graphs[i].setFunction("1");
            graphs[i].setMaxOffset(0.2);
            graphs[i].setCooldownSpeed(1000);
            graphs[i].setSubdecimals(3);
        }


        for (int i = 0; i < STACKS; i++) {
            values.add(generator.generate());
            for (int j = 0; j < values.get(values.size() - 1).length; j++) {
                double[] doubles = values.get(values.size() - 1)[j];
                for (int k=0;k<doubles.length;k++) {
                    System.out.print(doubles[k] + ", ");
                }
                System.out.println();
            }
            System.out.println();
        }

        JPanel panel = new JPanel();
        panel.setSize(1000, 800);
        panel.setBackground(Color.white);
        Draw drawing = new Draw();
        panel.add(drawing);
        JFrame frame = new JFrame("Graph Test");
        frame.setSize(1000, 800);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public static class Draw extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            for (double[][] value : values) {
                for (int j = 0; j < value.length; j++) {
                    g.setColor(new Color(j * (255 / value.length), j * (255 / value.length), 255 - j * (255 / value.length)));
                    for (int i = 0; i < value[j].length - 1; i++) {
                        g.drawLine(i * 10, 250 + (j * 600 / value.length) - (int) (value[j][i] / 2), (i + 1) * 10, 250 + (j * 600 / value.length) - (int) value[j][i + 1] / 2);
                        g.drawOval(i * 10 - 1, 250 + (j * 600 / value.length) - (int) value[j][i] / 2 - 1, 2, 2);
                        g.drawOval((i + 1) * 10 - 1, 250 + (j * 600 / value.length) - (int) value[j][i + 1] / 2 - 1, 2, 2);
                    }
                }
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(1000, 800);
        }
    }
}

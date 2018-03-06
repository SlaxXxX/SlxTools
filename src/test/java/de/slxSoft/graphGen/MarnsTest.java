package de.slxSoft.graphGen;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by SlaxX on 06.10.2017.
 */
public class MarnsTest {
    static java.util.List<double[][]> values;

    public static void main(String[] args) {
        values = new ArrayList<>();

        Generator generator = new Generator();
        generator.addGraph();
        Graph variance = generator.getGraph(0);
        variance.setFunction("1");
        variance.setMaxUpperOffset(0.2);
        variance.setMaxLowerOffset(0.2);
        variance.setSubdecimals(2);
        variance.setCooldownSpeed(99999999);
        
        values.add(generator.generate());

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
                        g.drawLine(i * 10, 250 + (j * 600 / value.length) - (int)(value[j][i] / 2), (i + 1) * 10, 250 + (j * 600 / value.length) - (int)value[j][i + 1] / 2);
                        g.drawOval(i * 10 - 1, 250 + (j * 600 / value.length) - (int)value[j][i] / 2 - 1, 2, 2);
                        g.drawOval((i + 1) * 10 - 1, 250 + (j * 600 / value.length) - (int)value[j][i + 1] / 2 - 1, 2, 2);
                        System.out.println(value[j][i + 1]);
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

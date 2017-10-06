package de.slxSoft.graphGen;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by SlaxX on 06.10.2017.
 */
public class DisplayTest {
    static java.util.List<int[]> values;
    static final int GRAPHS = 5;

    public static void main(String[] args) {
        values = new ArrayList<>();

        Generator generator = new Generator();
        for (int i=0;i<GRAPHS;i++)
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

            for(int[] value : values) {
                int index = values.indexOf(value);
                g.setColor(new Color(index * (255 / values.size()),index * (255 / values.size()),255 - index * (255 / values.size())));
                for (int i = 0; i < value.length - 1; i++)
                    g.drawLine(i * 10, 250 + (index * 550 / values.size() ) - value[i] / 2, (i + 1) * 10, 250 + (index * 550 / values.size() ) - value[(i + 1)] / 2);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(1000, 800); // As suggested by camickr
        }
    }
}

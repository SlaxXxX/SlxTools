package de.slxSoft.graphGen;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

/**
 * Created by SlaxX on 05.10.2017.
 */
public class Graph {

    private double maxOffset = 0.4;
    private int changeSpeed = 1;
    private int size = 100;
    private int changeCooldown = 1;
    private int cooldownSpeed = 10;

    private String function = "50+3.5*x";
    private int[] values;

    public void setMaxOffset(int offset) {
        maxOffset = offset;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public void setChangeSpeed(int speed) {
        changeSpeed = speed * 2 + 1;
    }

    public void setCooldownSpeed(int speed) {
        cooldownSpeed = speed;
    }

    public int[] generate() {
        values = new int[size + 1];
        Expression expression = new ExpressionBuilder(function).variable("x").build();
        for (int i = 0; i <= size; i++) {
            expression.setVariable("x", i);
            values[i] = (int) Math.round(expression.evaluate());
            //System.out.print(i + "\t\t" + values[i] + "\t\t");

            values[i] = distort(values[i - Math.min(i, 1)], values[i]);

            //System.out.println(values[i] + 2);
        }
        return values;
    }

    private int distort(int lastval, int thisval) {
        int maxDist = (int) (thisval * maxOffset);

        double distortion = Math.pow(
                Math.min(
                        Math.max(
                                Math.random() * 2 - 1,
                                -1 - (double) (lastval - thisval) / maxDist),
                        1 - (double) (lastval - thisval) / maxDist
                ), changeSpeed);

        int newval = lastval + (int) Math.round(maxDist * distortion / changeCooldown);

        changeCooldown += Math.abs(distortion) * 100 / changeCooldown;
        changeCooldown = Math.max(1, changeCooldown - cooldownSpeed);

        return newval;
    }
}

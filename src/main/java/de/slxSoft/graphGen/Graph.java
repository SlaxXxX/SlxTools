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

    /**
     * DEFAULT is 0.4 (40%)
     * @param offset Maximum offset from the Function in percent
     */
    public void setMaxOffset(int offset) {
        maxOffset = offset;
    }

    /**
     * DEFAULT is 100
     * @param size Size of the Array
     * Always starts at 0 and includes the maximum value (therefore contains size+1 values)
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * DEFAULT is "50 + 3.5 * x" --> with default size a function that ranges from 50 - 400
     * @param function A String that contains the Function that the Graph will determine values after
     * "x" is a predefined variable, other variables are not allowed
     * for more info on what constants and operators are usable, visit http://www.objecthunter.net/exp4j
     */
    public void setFunction(String function) {
        this.function = function;
    }

    /**
     * DEFAULT is 0
     * @param speed ChangeSpeed defines the chance for the curve to change it's value
     * the higher the value, the less the curve will have "noise"
     * (but 0 seems to be the best..)
     */
    public void setChangeSpeed(int speed) {
        changeSpeed = speed * 2 + 1;
    }

    /**
     * DEFAULT is 10
     * To further reduce noise, the curve will be on a "cooldown" after a big value change
     * @param speed CooldownSpeed sets the speed of the cooldown regeneration
     * the lower the value, the slower the cooldown will regenerate
     */
    public void setCooldownSpeed(int speed) {
        cooldownSpeed = speed;
    }

    /**
     *
     * @return Returns the Array of generated Values
     * If Generator.generate() wasn't executed yet, this will be empty
     * Alternatively, Generator.generate() will return the same Array
     */
    public int[] getGraphValues(){ return values; }

    /**
     *
     * @return Returns the Array of generated Values
     * The Graph can also directly be generated
     * For later Versions, that can generate multiple Graphs at once, use the generate() method from the Generator
     */
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

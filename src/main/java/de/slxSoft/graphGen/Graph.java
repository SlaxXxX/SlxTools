package de.slxSoft.graphGen;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.Random;

/**
 * Created by SlaxX on 05.10.2017.
 */
public class Graph {

    private boolean relativeToLast;
    private double maxUpperOffset;
    private double maxLowerOffset;
    private double changeProbability;
    private int size;
    private int changeCooldown;
    private int cooldownSpeed;
    private int subdecimals;
    private String function;
    private double[] values;

    public Graph() {
        relativeToLast = Defaults.relativeToLast;
        maxUpperOffset = Defaults.maxUpperOffset;
        maxLowerOffset = Defaults.maxLowerOffset;
        changeProbability = Defaults.changeProbability;
        size = Defaults.size;
        cooldownSpeed = Defaults.cooldownSpeed;
        subdecimals = Defaults.subdecimals;
        function = Defaults.function;

    }

    boolean getRelativeToLast() {
        return relativeToLast;
    }

    double getMaxUpperOffset() {
        return maxUpperOffset;
    }

    double getMaxLowerOffset() {
        return maxLowerOffset;
    }

    double getChangeProbability() {
        return changeProbability;
    }

    int getSize() {
        return size;
    }

    int getCooldownSpeed() {
        return cooldownSpeed;
    }

    int getSubdecimals() {
        return subdecimals;
    }

    String getFunction() {
        return function;
    }

    /**
     * DEFAULT is true<br><br>
     * <p>
     * If it is set to true, each value will be generated
     * based on the last value.
     * If it is set to false, it will always be the functions value.
     *
     * @param relative The boolean
     */
    public void setRelativeToLast(boolean relative) {
        relativeToLast = relative;
    }

    /**
     * DEFAULT is 0.4 (40%)<br>
     * <br>
     *
     * @param offset Maximum upper offset from the Function in percent
     */
    public void setMaxUpperOffset(double offset) {
        maxUpperOffset = offset;
    }

    /**
     * DEFAULT is 0.4 (40%)<br>
     * <br>
     *
     * @param offset Maximum lower offset from the Function in percent
     */
    public void setMaxLowerOffset(double offset) {
        maxLowerOffset = offset;
    }

    /**
     * DEFAULT is 100<br>
     * <br>
     *
     * @param size Size of the Array<br>
     *             <br>
     *             <p>
     *             Always starts at 0 and includes the maximum value (therefore
     *             contains size+1 values)
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * DEFAULT is "50 + 3.5 * x" --> with default size a function that ranges
     * from 50 - 400<br>
     * <br>
     *
     * @param function A String that contains the Function that the Graph will
     *                 determine values after<br>
     *                 <br>
     *                 <p>
     *                 <p>
     *                 "x" is a predefined variable, other variables are not
     *                 allowed<br>
     *                 for more info on what constants and operators are usable,
     *                 visit {@http://www.objecthunter.net/exp4j}
     */
    public void setFunction(String function) {
        this.function = function;
    }

    /**
     * DEFAULT is 0<br>
     * <br>
     *
     * @param probability ChangeProbability defines the chance for the distortion
     *                    to get a high value<br>
     *                    <br>
     *                    <p>
     *                    the higher the value, the lower the chance will get, that you get a high distortion<br>
     *                    (but 0 seems to be the best..)
     */
    public void setChangeProbability(double probability) {
        changeProbability = probability;
    }

    /**
     * DEFAULT is 20<br>
     * <br>
     * <p>
     * To further reduce noise, the curve will be on a "cooldown" after a big
     * value change<br>
     * <br>
     *
     * @param speed CooldownSpeed sets the speed of the cooldown regeneration<br>
     *              the lower the value, the slower the cooldown will regenerate
     */
    public void setCooldownSpeed(int speed) {
        cooldownSpeed = speed;
    }

    /**
     * Sets the amount of subdecimals the values get rounded to.
     *
     * @param decimals
     */
    public void setSubdecimals(int decimals) {
        subdecimals = decimals;
    }

    /**
     * @return Returns the Array of generated Values<br>
     * <br>
     * <p>
     * If Generator.generate() wasn't executed yet, this will be
     * empty<br>
     * Alternatively, Generator.generate() will return the same Array
     */
    public double[] getGraphValues() {
        return values;
    }

    /**
     * @return Returns the Array of generated Values<br>
     * <br>
     * <p>
     * The Graph can also directly be generated<br>
     * Use this Method to specifically generate the values for this
     * Graph only
     */
    public double[] generate(long seed) {

        Random random = new Random(seed);
        values = new double[size];

        Expression expression = new ExpressionBuilder(function).variable("x").build();

        changeCooldown = 1;
        int format = 1;
        for (int i = 0; i < subdecimals; i++)
            format *= 10;
        for (int i = 0; i < size; i++) {
            expression.setVariable("x", i);
            values[i] = expression.evaluate();

            values[i] = (double) Math.round(distort((relativeToLast ? values[i - Math.min(i, 1)] : values[i]), values[i], random.nextDouble()) * format) / format;


        }
        return values;
    }

    private double distort(double lastval, double thisval, double random) {

        double distortedRandom = Math.pow(random * 2 - 1, changeProbability);

        double maxDist = (thisval * (distortedRandom > 0 ? maxUpperOffset : maxLowerOffset));

        double distortion =
                Math.min(
                        Math.max(
                                distortedRandom,
                                -1 - (lastval - thisval) / maxDist),
                        1 - (lastval - thisval) / maxDist);

        double newval = lastval + maxDist * distortion / changeCooldown;

        changeCooldown += Math.abs(distortion) * 100 / changeCooldown;
        changeCooldown = Math.max(1, changeCooldown - cooldownSpeed);

        return newval;
    }
}

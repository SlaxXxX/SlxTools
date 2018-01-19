package de.slxSoft.graphGen;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * Created by SlaxX on 05.10.2017.
 */
public class Graph {

	private double maxOffset = 0.4;
	private int changeSpeed = 1;
	private int size = 100;
	private int changeCooldown = 1;
	private int cooldownSpeed = 20;
	private int subdecimals = 0;

	private String function = "50+3.5*x";
	private double[] values;

	/**
	 * DEFAULT is 0.4 (40%)<br>
	 * <br>
	 *
	 * @param offset
	 *            Maximum offset from the Function in percent
	 */
	public void setMaxOffset(double offset) {
		maxOffset = offset;
	}

	/**
	 * DEFAULT is 100<br>
	 * <br>
	 *
	 * @param size
	 *            Size of the Array<br>
	 *            <br>
	 *
	 *            Always starts at 0 and includes the maximum value (therefore
	 *            contains size+1 values)
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * DEFAULT is "50 + 3.5 * x" --> with default size a function that ranges
	 * from 50 - 400<br>
	 * <br>
	 *
	 * @param function
	 *            A String that contains the Function that the Graph will
	 *            determine values after<br>
	 *            <br>
	 *
	 *
	 *            "x" is a predefined variable, other variables are not
	 *            allowed<br>
	 *            for more info on what constants and operators are usable,
	 *            visit {@http://www.objecthunter.net/exp4j}
	 */
	public void setFunction(String function) {
		this.function = function;
	}

	/**
	 * DEFAULT is 0<br>
	 * <br>
	 *
	 * @param speed
	 *            ChangeSpeed defines the chance for the curve to change it's
	 *            value<br>
	 *            <br>
	 *
	 *            the higher the value, the less the curve will have "noise"<br>
	 *            (but 0 seems to be the best..)
	 */
	public void setChangeSpeed(int speed) {
		changeSpeed = speed * 2 + 1;
	}

	/**
	 * DEFAULT is 20<br>
	 * <br>
	 *
	 * To further reduce noise, the curve will be on a "cooldown" after a big
	 * value change<br>
	 * <br>
	 *
	 * @param speed
	 *            CooldownSpeed sets the speed of the cooldown regeneration<br>
	 *            the lower the value, the slower the cooldown will regenerate
	 */
	public void setCooldownSpeed(int speed) {
		cooldownSpeed = speed;
	}
	
	/**
	 * Sets the amount of subdecimals the values get rounded to.
	 * @param decimals
	 */
	public void setSubdecimals(int decimals) {
		subdecimals = decimals;
	}

	/**
	 *
	 * @return Returns the Array of generated Values<br>
	 *         <br>
	 *
	 *         If Generator.generate() wasn't executed yet, this will be
	 *         empty<br>
	 *         Alternatively, Generator.generate() will return the same Array
	 */
	public double[] getGraphValues() {
		return values;
	}

	/**
	 *
	 * @return Returns the Array of generated Values<br>
	 *         <br>
	 *
	 *         The Graph can also directly be generated<br>
	 *         Use this Method to specifically generate the values for this
	 *         Graph only
	 */
	public double[] generate(long seed) {

		Random random = new Random(seed);
		values = new double[size + 1];

		Expression expression = new ExpressionBuilder(function).variable("x").build();

		changeCooldown = 1;
		int format = 1;
		for (int i = 0; i < subdecimals; i++)
			format *= 10;
		for (int i = 0; i <= size; i++) {
			expression.setVariable("x", i);
			values[i] = expression.evaluate();

			values[i] = (double)Math.round(distort(values[i - Math.min(i, 1)], values[i], random.nextDouble()) * format) / format;
			

		}
		return values;
	}

	private double distort(double lastval, double thisval, double random) {

		double maxDist = (thisval * maxOffset);

		double distortion = Math.pow(
				Math.min(
						Math.max(
								random * 2 - 1,
								-1 - (lastval - thisval) / maxDist),
						1 - (lastval - thisval) / maxDist),
				changeSpeed);

		double newval = lastval + maxDist * distortion / changeCooldown;

		changeCooldown += Math.abs(distortion) * 100 / changeCooldown;
		changeCooldown = Math.max(1, changeCooldown - cooldownSpeed);

		return newval;
	}
}

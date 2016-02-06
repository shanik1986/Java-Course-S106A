package Assignment3.Breakout;

import acm.util.RandomGenerator;
import acm.graphics.*;
import java.awt.Color;

public class Ball extends GOval {
	//Private instance variables:
	private double vx;
	private double vy;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private GObject collider;
	
	public Ball (double width, double height, Color color) {
		super (width, height);
		this.setColor(color);
		this.setFilled(true);
		vy = 3;
		collider = null;
		restart();
	}

	public void restart () {
		vx = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5)) vx = -vx;
	}
	
	public double getVx() {
		return vx;
	}

	public double getVy() {
		return vy;
	}

	public void setVx(double vx) {
		this.vx = vx;
	}

	public void setVy(double vy) {
		this.vy = vy;
	}

	public GObject getCollider() {
		return collider;
	}

	public void setCollider(GObject collider) {
		this.collider = collider;
	}
	
}

package Assignment3.Breakout;

import java.awt.Color;

public class Paddle extends ColoredBrick {
	
	private int counter;
	
	public Paddle (double width, double height, Color color) {
		super (width, height);
		this.setColor(color);
		this.setFilled(true);
		counter = 0;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}
	
}

package Assignment3.Breakout;
/*
 * This class is an extension of GRect, and it allows to easily construct a filled
 * GRect in a specific Color
 */


import acm.graphics.*;
import java.awt.Color;

public class ColoredBrick extends GRect {
	
	private int counter;
	
	public ColoredBrick (double x, double y, double width, double height, int row) {
		super (x, y, width, height);
		this.setColor(decideColor(row));
		this.setFilled(true);
	}
	
	public ColoredBrick (double width, double height, int row) {
		this (0, 0, width, height, row);
	}
	
	public ColoredBrick (double width, double height) {
		super (width, height);
	}
	

	
	private Color decideColor (int row) {
		Color color;
		row %= 10;
		if ((row == 1) || (row == 2)) {
			color = Color.RED;
		} else if ((row == 3) || (row == 4)) {
			color = Color.ORANGE;
		} else if ((row == 5) || (row == 6)) {
			color = Color.YELLOW;
		} else if ((row == 7) || (row == 8)) {
			color = Color.GREEN;
		} else if ((row == 9) || (row == 0)) {
			color = Color.CYAN;
		} else {
			color = Color.BLACK; //This line occurs only if there is an ERROR
		}
		return color;
	}
}

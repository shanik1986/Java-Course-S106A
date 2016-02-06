/*
 * File: RunBreakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */
package Assignment3.Breakout;

import acm.graphics.*;
import acm.program.*;
import java.awt.*;
import java.awt.event.*;

public class RunBreakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (APPLICATION_WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Start location of ball */
	private static final double BALL_START_X = (APPLICATION_WIDTH - 
			(2 * BALL_RADIUS))  / 2;
	
	private static final double BALL_START_Y = (APPLICATION_HEIGHT - 
			(2 * BALL_RADIUS)) / 2;
	
	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 100;
	
/** Labels font */
	private static final String FONT_STYLE = "SansSerif-BOLD-60";

/** Animation Delay */
	private static final int DELAY = 20;
	
/** Private instance variables: */
	private Paddle paddle;
	private int lives = NTURNS;
	private Ball ball;

/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
		GLabel clickToPlay = new GLabel ("Click to Play!");
		setup(clickToPlay);
		playGame(clickToPlay);
		remove (ball);
	}
	
	public static void main(String[] args) {
		new RunBreakout().start(args);
	}

	private void playGame(GLabel clickToPlay) {
		boolean bricksRemaining = getElementCount() > 2;
		GLabel tempLabel = new GLabel ("");
		while ((lives > 0) && bricksRemaining) {
			ball.move(ball.getVx(), ball.getVy());
			checkWallCollisions(clickToPlay);
			checkObjectresponse(ball.getCollider());
			println ("vx: " + ball.getVx() + ", vy: " + ball.getVy() + ", ballX: " + ball.getX() + ", ballY: " +ball.getY());
			
			pause (DELAY);
		}
		if (! bricksRemaining) {
			tempLabel.setLabel ("Good Job!");
			addLabel(tempLabel);
		} else {
			tempLabel.setLabel ("Game Over :(");
			addLabel(tempLabel);
		}
	}
	
	private void checkObjectresponse(GObject collider) {
		ball.setCollider(getCollidingObject());
		if (ball.getCollider() != null) {
			if (ball.getCollider() != paddle) {
				remove (ball.getCollider());
				ball.setVy(- ball.getVy());
			} else {
				ball.setVy (-Math.abs(ball.getVy()));
					/*paddle.setCounter(paddle.getCounter() + 1);;
					if (paddle.getCounter() == 7) {
						ball.setVx (ball.getVx() * 2);
					}
				} 	
				if (ball.getY() + ball.getHeight() == paddle.getY() ) { 
					ball.setVy (- ball.getVy());*/
				
			}
		}
	}
	
	private GObject getCollidingObject() {
		GObject collider = null;
		GPoint upperLeftCorner = new GPoint (ball.getX(), ball.getY());
		GPoint upperRightCorner = new GPoint (ball.getX() + ball.getHeight(), 
				ball.getY());
		GPoint lowerLeftCorner = new GPoint (ball.getX(), 
				ball.getY() + ball.getHeight());
		GPoint lowerRightCorner = new GPoint (ball.getX() + ball.getHeight(),
				ball.getY() + ball.getHeight());
		/*GPoint leftSide = new GPoint (-0.01, BALL_RADIUS);
		GPoint rightSide = new GPoint (getWidth() + 0.01, BALL_RADIUS);
		GPoint bottomSide = new GPoint (BALL_RADIUS, getHeight() + 0.01);*/
		if (getElementAt(upperLeftCorner) != null) {
			collider = getElementAt(upperLeftCorner);
		} else if (getElementAt(upperRightCorner) != null) {
			collider = getElementAt(upperRightCorner);
			
		} else if (getElementAt (lowerLeftCorner) != null) {
			collider = getElementAt (lowerLeftCorner);
			
		} else if (getElementAt (lowerRightCorner) != null) {
			collider = getElementAt (lowerLeftCorner);
		/*} else if (getElementAt (leftSide) != null) {
			collider = getElementAt (leftSide);
		} else if (getElementAt (rightSide) != null) {
			collider = getElementAt (rightSide);
		} else if (getElementAt (bottomSide) != null) {
			collider = getElementAt (bottomSide);*/
		}	
		return collider;
	}
	
	private void checkWallCollisions(GLabel clickToPlay) {
		//Check top-bottom collision
		if (ball.getY() <= 0) {
			ball.setVy(- ball.getVy());
		
		//Check side collision 
		} else if ((ball.getX() <= 0) || 
				(ball.getX() + (2 * BALL_RADIUS) >= getWidth())) {
			ball.setVx(- ball.getVx());
		
		//Check bottom collision:
		} else if (ball.getY() + (2 * BALL_RADIUS) >= getHeight()) {
			strikeOut(clickToPlay);
		}
	}
	
	private void strikeOut(GLabel clickToPlay) {
		ball.setLocation(BALL_START_X, BALL_START_Y);
		ball.restart();
		lives--;
		if (lives != 0) {
			addLabel (clickToPlay);
			waitForClick();
			remove (clickToPlay);
		}
	}
	
		
/** Sets up the bricks, the paddle and the starting text */ 	
	private void setup(GLabel clickToPlay) {
		addBricks();
		addPaddle(Color.BLACK);
		addLabel(clickToPlay);
		ball = new Ball (2 * BALL_RADIUS, 2 * BALL_RADIUS, Color.BLUE);
		add (ball, BALL_START_X, BALL_START_Y);
		waitForClick();
		remove (clickToPlay);
		addMouseListeners();
	}
	
	/**
	 * This method is initiated when the mouse is moved, and it makes the paddle
	 * move along with the mouse pointer.
	 */
	public void mouseMoved (MouseEvent e) {
		double paddleRightExtreme = getWidth() - (PADDLE_WIDTH / 2);
		double paddleLeftExtreme = PADDLE_WIDTH / 2;
		if ((e.getX() < paddleRightExtreme) && (e.getX() > paddleLeftExtreme)) {
			paddle.setLocation(e.getX() - (PADDLE_WIDTH / 2), paddle.getY());
		} else if (e.getX() >= paddleRightExtreme){ 
			paddle.setLocation(getWidth() - PADDLE_WIDTH, paddle.getY());
		} else {
			paddle.setLocation (0, paddle.getY());
		}
	}
	
/*	private boolean checkPaddleCollision() {
		boolean isPaddleColliding = false;
		if (ball.getY() + ball.getHeight() > paddle.getY()) {
			if ((getElementAt(paddle.getX(), paddle.getY()) != paddle) || 
					getElementAt(paddle.getX(), paddle.getY() + PADDLE_HEIGHT) != paddle) {
				isPaddleColliding = true;
			} else if ((getElementAt(paddle.getX() + PADDLE_WIDTH, paddle.getY()) != 
					paddle) || (getElementAt(paddle.getX() + PADDLE_WIDTH, 
							paddle.getY() + PADDLE_HEIGHT) != paddle)) {
				isPaddleColliding = true;
			}
		}
		return isPaddleColliding;
	}*/
	
	private void addLabel (GLabel label) {
		label.setFont(FONT_STYLE);
		double x = (getWidth() - label.getWidth()) / 2;
		double y = (getHeight() - label.getHeight()) / 2;
		add (label, x, y);
	}
	
	private void addPaddle (Color color) {
		double x = (getWidth() - PADDLE_WIDTH) / 2;
		double y = getHeight() - PADDLE_Y_OFFSET;
		paddle = new Paddle (PADDLE_WIDTH, PADDLE_HEIGHT, color);
		add (paddle, x, y);
	}
	
	/**
	 * Adds all the bricks at the top of the application. Each brick is a ColoredBrick
	 * object.
	 */
	private void addBricks() {
		double totalLength = (NBRICKS_PER_ROW * BRICK_WIDTH) + 
				((NBRICKS_PER_ROW - 1) * BRICK_SEP);
		double startX = (getWidth() - totalLength) / 2;
		double x = startX;
		double y = BRICK_Y_OFFSET;
		for (int i = 1; i <= NBRICK_ROWS; i++) {
			for (int j = 0; j < NBRICKS_PER_ROW; j++) {
				ColoredBrick brick = new ColoredBrick (BRICK_WIDTH, BRICK_HEIGHT, i);
				add (brick, x, y);
				x += BRICK_WIDTH + BRICK_SEP;
			}
			x = startX;
			y += BRICK_HEIGHT + BRICK_SEP;
		}
	}

}

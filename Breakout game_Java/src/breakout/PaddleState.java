package breakout;

import java.awt.Color;

/**
 * Represents the state of a normal paddle in the breakout game.
 *
 * @immutable
 * @invar | getCenter() != null
 */
public class PaddleState {
	
	public static final int HEIGHT = 500;
	public static final int WIDTH = 3000; //3000
	private final int counts = -1;
	
	/**
	 * @invar | center != null
	 */
	private final Point center;

	/**
	 * Construct a normal paddle located around a given center in the field.
	 * 
	 * @pre | center != null
	 * @post | getCenter().equals(center)
	 */
	public PaddleState(Point center) {
		this.center = center;
	}
	
	/**
	 * Return the center point of this paddle.
	 * 
	 */
	public Point getCenter() {
		return center;
	}

	/**
	 * Return the rectangle occupied by this paddle in the field.
	 * 
	 * @post | result != null
	 * @post | result.getTopLeft().equals(getCenter().plus(new Vector(-WIDTH/2,-HEIGHT/2)))
	 * @post | result.getBottomRight().equals(getCenter().plus(new Vector(WIDTH/2,HEIGHT/2)))
	 */
	public Rect getLocation() {
		Vector halfDiag = new Vector(-WIDTH/2,-HEIGHT/2);
		return new Rect(center.plus(halfDiag), center.plus(halfDiag.scaled(-1)));
	}
	
	/**
	 * Return the color of this type of block
	 * 
	 */
	public Color Paint() {
		return Color.orange;
	}

	/**
	 * Return -1 which indicates it is a normal paddle
	 * While return the numbers that how many ball(s) are going to be created
	 * after a ball collided with a replicator paddle
	 * 
	 * 
	 */
	
	public int getCounts() {
		return counts;
	}

	/**
	 * Set the location of this paddle without changing the other properties of this
	 * paddle
	 * 
	 * @pre | center != null
	 * @mutates | this 
	 * 
	 * @post | Paint().equals(old(Paint()))
	 * @post | getCounts() == old(getCounts())
	 * 
	 */

	public PaddleState move(Point center) {
		return new PaddleState(center);
	}
	
	

}

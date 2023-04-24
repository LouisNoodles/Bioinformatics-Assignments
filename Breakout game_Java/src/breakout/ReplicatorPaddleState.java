package breakout;

import java.awt.Color;

/**
 * Represents the state of a normal paddle in the breakout game.
 *
 * @immutable
 * @invar | getCenter() != null
 * @invar | getCounts() <= 3 || getCounts() >= 1
 */

public class ReplicatorPaddleState extends PaddleState{
	private final int counts;
	
	/**
	 * Construct a replicator paddle located around a given center in the field
	 * with the `count` that indicates that how many balls are going to be created
	 * after colliding with a ball
	 * 
	 * @pre | center != null
	 * @pre | counts <= 3 && counts >= 1
	 * 
	 * @post | getCenter().equals(center)
	 * @post | getCounts() == counts
	 */

	public ReplicatorPaddleState(Point center, int counts) {
		super(center);
		this.counts = counts;
	
	}
	
	/**
	 * Return the color of this type of block
	 * 
	 */
	
	public Color Paint() {
		return Color.magenta;
	}
	
	/**
	 * Return a number(1, 2 or 3) that indicates that 
	 * how many ball(s) are going to be created after colliding with a ball
	 * 
	 * @ post | result == 1 || result == 2 || result == 3
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
	 * @post | result.getCenter().getY() == center.getY()
	 * @post | Paint().equals(old(Paint()))
	 * @post | getCounts() == old(getCounts())
	 * 
	 */
	
	public PaddleState move(Point center) {
		return new ReplicatorPaddleState(center, this.counts);
	}
}

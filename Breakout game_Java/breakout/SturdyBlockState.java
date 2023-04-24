package breakout;

import java.awt.Color;
import java.util.HashMap;

/**
 * Represents the state of a sturdy block in the breakout game.
 *
 * @immutable
 * @invar | getLocation() != null
 */

public class SturdyBlockState extends BlockState{

	private final int hitCounts;
	private Color color = Color.blue;

	/**
	 * Construct a block occupying a given rectangle in the field with defined hit times
	 * that required to destroy this sturdy block.
	 * @pre | location != null
	 * @pre | i > 0
	 * @post | getHitCounts() == i
	 */

	public SturdyBlockState(Rect location, int i, Color color) {
		super(location);
		this.hitCounts = i;
	}

	/**
	 * Return the rectangle occupied by this block in the field.
	 * 
	 */

	@Override
	public Rect getLocation() {
		return location;
	}

	/**
	 * Return the color of this type of block
	 * 
	 */

	@Override
	public Color paint() {
		return this.color;
	}

	
	@Override
	public void paint(Rect rect) {

		HashMap<BlockState, Integer> map = BreakoutState.getCount();
		map.forEach((block, c) -> {
			if(block.getLocation().equals(rect) && c == 1) {
				color = Color.cyan;
			}
			else if(block.getLocation().equals(rect) && c == 2) {
				color = Color.green;
			}
		});

	}


	/**
	 * Return a new ball after this ball collided with this rect without 
	 * changing the other properties of this ball.
	 * 
	 *  @pre | ball != null
	 *  
	 *  @post | ball.getLocation().equals(result.getLocation())
	 *  @post | getLocation().equals(old(getLocation()))
	 *  @post | ball.getDiameter() == (result.getDiameter())
	 *  @post | ball.getVelocity().equals(result.getVelocity())
	 *  @post | ball.getLifeTime() == result.getLifeTime()
	 */


	@Override
	public Ball collideWithBall(Ball ball) {
		return ball;
	}


	/**
	 * Return the time(s) that required to destroy this normal block
	 * 

	 */

	@Override
	public int getHitCounts() {
		return this.hitCounts;
	}



}

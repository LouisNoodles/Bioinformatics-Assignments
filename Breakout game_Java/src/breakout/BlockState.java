package breakout;

import java.awt.Color;

/**
 * Represents the state of a block in the breakout game.
 *
 * @immutable
 * @invar | getLocation() != null
 */
public abstract class BlockState {
	
	/**
	 * @invar | location != null
	 */
	protected final Rect location;


	/**
	 * Construct a block occupying a given rectangle in the field.
	 * @pre | location != null
	 * @post | getLocation().equals(location)
	 */
	public BlockState(Rect location) {
		this.location = location;
	}

	/**
	 * Return the rectangle occupied by this block in the field.
	 * 
	 * result == getLocation()
	 */
	public abstract Rect getLocation();
	
	/**
	 * Return the color of this type of block
	 * 
	 */
	
	public abstract Color paint();
	
	/**
	 * Change the color of this sturdy block due to the interaction with `rect`,
	 * while for other types of block, this method is non sense.
	 * @return 
	 * 
	 * @pre | rect != null
	 *
	 * 
	 */
	
	public abstract void paint(Rect rect);
	
	/**
	 * Return a new ball after this ball collided with this rect without 
	 * changing the other properties of this ball.
	 * When an normal ball collided with a `rect` that links to a PowerupBallBlock
	 * then it will become a supercharged ball
 	 * 
	 *  @pre | ball != null
	 *  
	 *  @post | ball.getLocation().equals(result.getLocation())
	 *  @post | getLocation().equals(old(getLocation()))
	 *  @post | ball.getDiameter() == (result.getDiameter())
	 *  @post | ball.getVelocity().equals(result.getVelocity())
	 *  @post | ball.getLifeTime() == result.getLifeTime()
	 */
	
	
	public abstract Ball collideWithBall(Ball ball);
	
	/**
	 * Return the time(s) that required (or one time less) to destroy this block
	 * 
	 */
	
	public abstract int getHitCounts();
	
	
}

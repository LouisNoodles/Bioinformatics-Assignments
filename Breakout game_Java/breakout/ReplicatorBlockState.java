package breakout;

import java.awt.Color;

/**
 * Represents the state of a replicator block in the breakout game.
 *
 * @immutable
 * @invar | getLocation() != null
 */

public class ReplicatorBlockState extends BlockState{
	

	public ReplicatorBlockState(Rect location) {
		super(location);

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
		return Color.darkGray;
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

		return 0;
	}

	@Override
	public void paint(Rect rect) {
		
	}

}

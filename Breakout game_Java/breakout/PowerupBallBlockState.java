package breakout;

import java.awt.Color;

/**
 * Represents the state of a power up ball block in the breakout game.
 *
 * @immutable
 * @invar | getLocation() != null
 */

public class PowerupBallBlockState extends BlockState{

	public PowerupBallBlockState(Rect location) {
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
	 * @post | result.equals(Color.red)
	 */
	@Override
	public Color paint() {
		return Color.red;
		
	}
	
	/**
	 * Return a super charged ball after this ball collided with this rect without 
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
	
	public Ball collideWithBall(Ball ball) {
		ball = new SuperchargedBall(ball.getLocation(),ball.getDiameter(),ball.getVelocity(),ball.getLifeTime());
		return ball;
	}
	
	/**
	 * Return the time(s) that required to destroy this normal block
	 * 
	 */
	
	@Override
	public int getHitCounts() {
		return 1;
	}

	@Override
	public void paint(Rect rect) {
		// TODO Auto-generated method stub
		
	}




}

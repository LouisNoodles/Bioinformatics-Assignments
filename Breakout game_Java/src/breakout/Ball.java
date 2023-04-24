package breakout;

import java.awt.Color;
/**
 * Represents the state of a ball in the breakout game.
 * 
 * @mutable
 * @invar | getLocation() != null
 * @invar | getDiameter() > 0
 * @invar | getVelocity() != null
 * @invar | getLifeTime() <= 10000 && -49 <= getLifeTime()
 */

public abstract class Ball {
	
	protected Point location;
	protected int diameter;
	protected Vector velocity;
	protected int lifeTime;
	
	/**
	 * Construct a new ball at a given `location`, with a given `diameter` and `velocity`.
	 * 
	 * @pre | location != null
	 * @pre | velocity != null
	 * @pre | diameter > 0
	 * @post | getLocation() == location
	 * @post | getDiameter() == diameter
	 * @post | getVelocity().equals(velocity) 
	 */

	public Ball(Point location, int diameter, Vector velocity) {
		this.location = location;
		this.diameter = diameter;
		this.velocity = velocity;
	}
	
	/**
	 * Return a boolean that indicates whether this ball hit a block
	 * 
	 * @pre | rect != null
	 * @pre | destroyed == true || destroyed == false
	 * 
	 */
	public abstract boolean hitBlock(Rect rect, boolean destroyed);
	
	/**
	 * Check whether this ball collides with a given `rect` and if so, return the 
	 * new velocity this ball will have after bouncing on the given rect.
	 * 
	 * @pre | rect != null
	 * @post | (rect.collideWith(this) == null && result == null) ||
	 *       | (getVelocity().product(rect.collideWith(this)) <= 0 && result == null) || 
	 *       | (result.equals(getVelocity().mirrorOver(rect.collideWith(this))))
	 */
	public abstract Vector bounceOn(Rect rect);
	
	/**
	 * Return the color of this type of block
	 * 
	 */
	
	public abstract Color paint();
	
	/**
	 * There is no meaning for the normal ball, only work for the super charged ball:
	 * return true is the life time of this super ball is running out, 
	 * otherwise, return false.
	 * 
	 * 
	 * @post | (getLifeTime() > 0 && result == false) ||
	 * 		 | (getLifeTime() <= 0 && result == true) ||
	 * 	 	 | (result == false)
	 * 
	 */
	
	public abstract boolean countDown(int elapsedTime);
	
	/**
	 * Return this ball's location.
	 * 
	 * 
	 */
	public abstract Point getLocation();
	
	/**
	 * Set this ball's location.
	 * 
	 * @pre | location != null
	 * @post | getLocation().equals(location)
	 */
	
	public abstract void setLocation(Point location);
	
	/**
	 * Return this ball's diameter.
	 * 
	 *
	 */
	public abstract int getDiameter();
	
	/**
	 * Set this ball's diameter.
	 * 
	 * @pre | diameter > 0
	 * @post | getDiameter() == diameter
	 */
	
	public abstract void setDiameter(int diameter);
	
	/**
	 * Return this ball's velocity.
	 * 
	 * 
	 */
	public abstract Vector getVelocity();
	
	/**
	 * Set this ball's velocity.
	 * 
	 * @pre | velocity != null
	 * @post | getVelocity() == velocity
	 */
	
	public abstract void setVelocity(Vector velocity);
	
	
	/**
	 * Return this ball's current `lifeTime` or the initial `lifeTime`.
	 * 
	 * 
	 */
	public abstract int getLifeTime();
	
	
	/**
	 * Set this ball's lifeTime.
	 * 
	 * @pre | lifeTime <= 10000 && -49 <= lifeTime
	 * @post | getLifeTime() == lifeTime
	 */
	public abstract void setLifeTime(int lifeTime);
	

}
	

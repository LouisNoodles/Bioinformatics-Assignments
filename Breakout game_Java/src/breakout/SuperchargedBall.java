package breakout;

import java.awt.Color;
import java.util.HashMap;

/**
 * Represents the state of a super charged ball in the breakout game.
 * 
 * @mutable
 * @invar | getLocation() != null
 * @invar | getDiameter() > 0
 * @invar | getVelocity() != null
 * @invar | getLifeTime() <= 10000
 */

public class SuperchargedBall extends Ball{

	private int lifeTime;
	boolean ballHitBlock = false;

	/**
	 * Construct a new ball at a given `location`, with a given `velocity`.
	 * 
	 * @pre | location != null
	 * @pre | diameter > 0
	 * @pre | velocity != null
	 * @post | getLocation() == location
	 * @post | getDiameter() == diameter
	 * @post | getVelocity().equals(velocity) 
	 */


	public SuperchargedBall(Point location, int diameter, Vector velocity, int lifetime) {
		super(location, diameter, velocity);
		this.lifeTime = lifetime;

	}

	/**
	 * Return this ball's location.
	 */
	@Override
	public Point getLocation() {
		return location;
	}

	/**
	 * Return this ball's velocity.
	 */
	@Override
	public Vector getVelocity() {
		return velocity;
	}

	/**
	 * Return this ball's diameter.
	 * 
	 */
	
	@Override
	public int getDiameter() {
		return diameter;
	}
	
	/**
	 * Set this ball's diameter.
	 * 
	 * @pre | diameter > 0
	 * @post | getDiameter() == diameter
	 */
	
	@Override
	public void setDiameter(int diameter) {
		this.diameter = diameter;
	}
	
	/**
	 * Set this ball's location.
	 * 
	 * @pre | location != null
	 * @post | getLocation().equals(location)
	 */
	
	
	@Override
	public void setLocation(Point location) {
		this.location = location;
	}
	
	/**
	 * Set this ball's velocity.
	 * 
	 * @pre | velocity != null
	 * @post | getVelocity() == velocity
	 */
	
	
	@Override
	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}


	/**
	 * Check whether this ball collides with a given `rect` and if so, return the 
	 * new velocity this ball will have after bouncing on the given rect.
	 * 
	 * @pre | rect != null
	 * @post | (rect.collideWith(this) == null && result == null) ||
	 *       | (getVelocity().product(rect.collideWith(this)) <= 0 && result == null) || 
	 *       | (result.equals(getVelocity().mirrorOver(rect.collideWith(this))))
	 */
	@Override
	public Vector bounceOn(Rect rect) {
		Vector coldir = rect.collideWith(this);
		if(coldir != null && velocity.product(coldir) > 0) {
			return velocity.mirrorOver(coldir);
		}
		return null;
	}

	/**
	 * Check whether this super charged ball collides with a given `rect` that represents for a block
	 * and if so, set the new velocity this ball will have after bouncing on the given sturdy block, 
	 * otherwise, when other types of block is collided, the velocity of the ball will remain the same.
	 * As long as this super charged ball collides with a block, the count number of the corresponding 
	 * block in the BreakouState will increase one.
	 * 
	 * @pre | rect != null
	 * @pre | destroyed == true || destroyed == false
	 * 
	 * @post | (rect.collideWith(this) == null && result == false) ||
	 * 		 | (getVelocity().product(rect.collideWith(this)) <= 0 && result == false) ||
	 * 		 | (this.getVelocity().equals(old(getVelocity().mirrorOver(rect.collideWith(this))))
	 * 	 	 | && result == destroyed) ||
	 * 		 | (this.getVelocity().equals(old(getVelocity())) && result == destroyed)
	 */

	@Override
	public boolean hitBlock(Rect rect, boolean destroyed) {
		
		Vector coldir = rect.collideWith(this);
		if(coldir != null && velocity.product(coldir) > 0){
			HashMap<BlockState, Integer> count = BreakoutState.getCount();
			count.forEach((block, c) -> {
				if(block.getLocation().equals(rect) && block.getHitCounts() == 3) {
					velocity = velocity.mirrorOver(rect.collideWith(this));
					int hitTimes = count.get(block);
					count.replace(block, ++hitTimes);
					ballHitBlock = destroyed;
				}
				else if(block.getLocation().equals(rect)){
					int hitTimes = count.get(block);
					count.replace(block, ++hitTimes);
					//velocity = velocity;
					ballHitBlock = destroyed;
				}
			});
		}
		else if(coldir == null) {
			//velocity = velocity;
		}
		return ballHitBlock;


	}
	
	/**
	 * Return the color of this type of block
	 * 
	 */

	@Override
	public Color paint() {
		// TODO Auto-generated method stub
		return Color.orange;
	}

	/**
	 * Return true is the life time of this super ball is running out, otherwise,
	 * return false
	 * @pre | 0 < elapsedTime
	 * 
	 * @post | (getLifeTime() > 0 && result == false) ||
	 * 		 | (getLifeTime() <= 0 && result == true)
	 * 
	 */

	public boolean countDown(int elapsedTime) {
		lifeTime = lifeTime - elapsedTime;
		if (lifeTime <= 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Return this ball's current `lifeTime`.
	 * 
	 */
	
	@Override
	public int getLifeTime() {
		// TODO Auto-generated method stub
		return this.lifeTime;
	}

	
	
	/**
	 * Set this ball's lifeTime.
	 * 
	 * @pre | lifeTime <= 10000 && -49 <= lifeTime
	 * @post | getLifeTime() == lifeTime
	 */
	
	@Override
	public void setLifeTime(int lifeTime) {
		// TODO Auto-generated method stub
		this.setLifeTime(lifeTime);
	}

}

package breakout;

import java.awt.Color;
import java.util.HashMap;

/**
 * Represents the state of a normall ball in the breakout game.
 * 
 * @mutable
 * @invar | getLocation() != null
 * @invar | getDiameter() > 0
 * @invar | getVelocity() != null
 * @invar | getLifeTime() == BreakoutFacade.lifetime
 */

public class NormalBall extends Ball{


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


	public NormalBall(Point location, int diameter, Vector velocity) {
		super(location, diameter, velocity);
	}

	/**
	 * Return this ball's location.
	 * 
	 */
	@Override
	public Point getLocation() {
		return location;
	}

	/**
	 * Return this ball's velocity.
	 * 
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
	 * Return the color of this type of block
	 * 
	 */

	@Override
	public Color paint() {
		return Color.white;
	}

	/**
	 * Check whether this ball collides with a given `rect` that represents for a block
	 * and if so, set the new velocity this ball will have after bouncing on the given block.
	 * The count number of the corresponding block in the BreakouState will increase one
	 * 
	 * @pre | rect != null
	 * @pre | destroyed == true || destroyed == false
	 * 
	 * @post | (rect.collideWith(this) == null && result == false) ||
	 * 		 | (getVelocity().product(rect.collideWith(this)) <= 0 && result == false) ||
	 * 		 | (this.getVelocity().equals(old(getVelocity().mirrorOver(rect.collideWith(this))))
	 * 	 	 | && result == destroyed)
	 */

	@Override
	public boolean hitBlock(Rect rect, boolean destroyed) {
		// TODO Auto-generated method stub
		boolean ballHitBlock = false;
		Vector coldir = rect.collideWith(this);
		if(coldir != null && velocity.product(coldir) > 0){
			this.setVelocity(velocity.mirrorOver(rect.collideWith(this)));
			HashMap<BlockState, Integer> count = BreakoutState.getCount();
			count.forEach((block, c) -> {
				if(block.getLocation().equals(rect)) {
					int hitTimes = count.get(block);
					count.replace(block, ++hitTimes);
				}
			});
			ballHitBlock = destroyed;
		}
		else if(coldir == null) {
			this.setVelocity(velocity);
		}
		return ballHitBlock;
	}

	/**
	 *  There is no meaning of countDown method for the normal ball
	 */
	@Override
	public boolean countDown(int elapsedTime) {
		// TODO Auto-generated method stub
		return false;
	}


	/**
	 * Return the lifeTime to allow the creation of a super charged ball 
	 * if it is necessarily.
	 * 
	 */
	
	@Override
	public int getLifeTime() {
		// TODO Auto-generated method stub
		return BreakoutFacade.lifetime;
	}


	/**
	 * Set this normal ball's lifeTime, although it is meaningless because the `lifeTime`
	 * of this ball will not be exposed.
	 * 
	 */

	@Override
	public void setLifeTime(int lifeTime) {

		this.lifeTime = lifeTime;
	}

}
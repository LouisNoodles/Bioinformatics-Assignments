package breakout;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


/**
 * Represents the current state of a breakout game.
 *  
 * @invar | getBalls() != null
 * @invar | getBlocks() != null
 * @invar | getPaddle() != null
 * @invar | getBottomRight() != null
 * @invar | Point.ORIGIN.isUpAndLeftFrom(getBottomRight())
 * @invar | Arrays.stream(getBalls()).allMatch(b -> getField().contains(b.getLocation()))
 * @invar | Arrays.stream(getBlocks()).allMatch(b -> getField().contains(b.getLocation()))
 * @invar | getField().contains(getPaddle().getLocation())
 */
public class BreakoutState {

	public static final Vector PADDLE_VEL = new Vector(10,0);//10,0
	public static final int MAX_ELAPSED_TIME = 50;
	private static HashMap<BlockState, Integer> count;
	private static ArrayList<Ball> createdBalls;
	private static final Vector v1 = new Vector(2,-2); //(2,-2)
	private static final Vector v2 = new Vector(-2,2); //(-2,2)
	private static final Vector v3 = new Vector(2,2); //(2,2)
	private static final Vector [] vectors = {v1, v2, v3};

	/**
	 * @invar | bottomRight != null
	 * @invar | Point.ORIGIN.isUpAndLeftFrom(bottomRight)
	 */
	private final Point bottomRight;
	/**
	 * @invar | balls != null
	 * @invar | Arrays.stream(balls).allMatch(b -> getFieldInternal().contains(b.getLocation()))
	 * @representationObject
	 */
	private Ball[] balls;	
	/**
	 * @invar | blocks != null
	 * @invar | Arrays.stream(blocks).allMatch(b -> getFieldInternal().contains(b.getLocation()))
	 * @representationObject
	 */
	private BlockState[] blocks;
	/**
	 * @invar | paddle != null
	 * @invar | getFieldInternal().contains(paddle.getLocation())
	 */
	private PaddleState paddle;

	private final Rect topWall;
	private final Rect rightWall;
	private final Rect leftWall;
	private final Rect[] walls;

	/**
	 * Construct a new BreakoutState with the given balls, blocks, paddle.
	 * 
	 * @throws IllegalArgumentException | balls == null
	 * @throws IllegalArgumentException | blocks == null
	 * @throws IllegalArgumentException | bottomRight == null
	 * @throws IllegalArgumentException | paddle == null
	 * @throws IllegalArgumentException | !Point.ORIGIN.isUpAndLeftFrom(bottomRight)
	 * @throws IllegalArgumentException | !(new Rect(Point.ORIGIN,bottomRight)).contains(paddle.getLocation())
	 * @throws IllegalArgumentException | !Arrays.stream(blocks).allMatch(b -> (new Rect(Point.ORIGIN,bottomRight)).contains(b.getLocation()))
	 * @throws IllegalArgumentException | !Arrays.stream(balls).allMatch(b -> (new Rect(Point.ORIGIN,bottomRight)).contains(b.getLocation()))
	 * @post | Arrays.equals(getBalls(),balls)
	 * @post | Arrays.equals(getBlocks(),blocks)
	 * @post | getBottomRight().equals(bottomRight)
	 * @post | getPaddle().equals(paddle)
	 */



	public BreakoutState(Ball[] balls, BlockState[] blocks, Point bottomRight, PaddleState paddle) {

		if( balls == null) throw new IllegalArgumentException();
		if( blocks == null) throw new IllegalArgumentException();
		if( bottomRight == null) throw new IllegalArgumentException();
		if( paddle == null) throw new IllegalArgumentException();

		if(!Point.ORIGIN.isUpAndLeftFrom(bottomRight)) throw new IllegalArgumentException();
		this.bottomRight = bottomRight;
		if(!getFieldInternal().contains(paddle.getLocation())) throw new IllegalArgumentException();
		if(!Arrays.stream(blocks).allMatch(b -> getFieldInternal().contains(b.getLocation()))) throw new IllegalArgumentException();
		if(!Arrays.stream(balls).allMatch(b -> getFieldInternal().contains(b.getLocation()))) throw new IllegalArgumentException();

		this.balls = balls.clone();
		this.blocks = blocks.clone();
		this.paddle = paddle;

		this.topWall = new Rect( new Point(0,-1000), new Point(bottomRight.getX(),0));
		this.rightWall = new Rect( new Point(bottomRight.getX(),0), new Point(bottomRight.getX()+1000,bottomRight.getY()));
		this.leftWall = new Rect( new Point(-1000,0), new Point(0,bottomRight.getY()));
		this.walls = new Rect[] {topWall,rightWall, leftWall };

		count = new HashMap<BlockState, Integer>();
		for (BlockState block: blocks) {
			count.put(block, 0);
		}
	}

	/**
	 * Return the balls of this BreakoutState.
	 * 
	 * @creates result
	 */
	public Ball[] getBalls() {
		return balls.clone();
	}

	/**
	 * Return the blocks of this BreakoutState. 
	 *
	 * @creates result
	 */
	public BlockState[] getBlocks() {
		return blocks.clone();
	}

	/**
	 * Return the paddle of this BreakoutState. 
	 */
	public PaddleState getPaddle() {
		return paddle;
	}

	/**
	 * Return the point representing the bottom right corner of this BreakoutState.
	 * The top-left corner is always at Coordinate(0,0). 
	 */
	public Point getBottomRight() {
		return bottomRight;
	}

	// internal version of getField which can be invoked in partially inconsistent states
	private Rect getFieldInternal() {
		return new Rect(Point.ORIGIN, bottomRight);
	}

	/**
	 * Return a rectangle representing the game field.
	 * @post | result != null
	 * @post | result.getTopLeft().equals(Point.ORIGIN)
	 * @post | result.getBottomRight().equals(getBottomRight())
	 */
	public Rect getField() {
		return getFieldInternal();
	}


	public static HashMap<BlockState, Integer> getCount() {
		return count;
	}

	private Ball bounceWalls(Ball ball) {
		for( Rect wall : walls) {
			Vector nspeed = ball.bounceOn(wall);
			if(nspeed != null ) {
				ball.setVelocity(nspeed);
			}
		}
		return ball;
	}

	private Ball removeDead(Ball ball) {
		if(ball.getLocation().plus(new Vector(ball.diameter,ball.diameter)).getY() > bottomRight.getY()) { return null; }
		else { return ball; }
	}

	private Ball clampBall(Ball b) {
		Point loc = getFieldInternal().constrain(b.getLocation());
		b.setLocation(loc);
		return b;
	}

	public Ball collideBallBlocks(Ball ball, int elapsedTime) {
		if (ball.countDown(elapsedTime)) {
			ball = new NormalBall(ball.getLocation(),ball.getDiameter(),ball.getVelocity());
		}
		for(BlockState block : blocks) {
			boolean hit = ball.hitBlock(block.getLocation(), true);

			if(hit) {
				block.paint(block.getLocation());
				if (count.get(block) > block.getHitCounts()) {
					paddle = new ReplicatorPaddleState(paddle.getCenter(),3);
					removeBlock(block);
				}
				if(count.get(block) == block.getHitCounts()) {
					removeBlock(block);
				}
				ball = block.collideWithBall(ball);
			}
		}
		return ball;
	}

	public ArrayList<Ball> collideBallPaddle(Ball ball, Vector paddleVel) {

		ArrayList<Ball> newBalls = new ArrayList<Ball>();
		Vector nspeed = ball.bounceOn(paddle.getLocation());
		if(nspeed != null) {
			if (paddle.getCounts() > 0) {
				for(int i = 0; i < paddle.getCounts(); i++) {
					newBalls.add(new NormalBall(ball.getLocation(),ball.getDiameter(),nspeed.plus(vectors[i])));
				}
			}
			int c = paddle.getCounts()-1;
			if(c == 0) {
				paddle = new PaddleState(paddle.getCenter());
			}
			else if (c > 0) {
				paddle = new ReplicatorPaddleState(paddle.getCenter(), c);
			}

			Point ncenter = ball.getLocation().plus(nspeed);
			nspeed = nspeed.plus(paddleVel.scaledDiv(5));
			ball.setLocation(ncenter);
			ball.setVelocity(nspeed);
		}

		return newBalls;
	}

	private void removeBlock(BlockState block) {
		ArrayList<BlockState> nblocks = new ArrayList<BlockState>();
		for( BlockState b : blocks ) {
			if(b != block) {
				nblocks.add(b);
			}
		}
		blocks = nblocks.toArray(new BlockState[] {});
	}

	/**
	 * Move all moving objects one step forward.
	 * 
	 * @mutates this
	 */

	public void tick(int paddleDir, int elapsedTime) {
		stepBalls(elapsedTime); 
		bounceBallsOnWalls();
		removeDeadBalls();
		bounceBallsOnBlocks(elapsedTime);
		bounceBallsOnPaddle(paddleDir);
		clampBalls();
		balls = Arrays.stream(balls).filter(x -> x != null).toArray(Ball[]::new);
	}

	private void clampBalls() {
		for(int i = 0; i < balls.length; ++i) {
			if(balls[i] != null) {
				balls[i] = clampBall(balls[i]);
			}		
		}
	}


	private void bounceBallsOnPaddle(int paddleDir) {
		Vector paddleVel = PADDLE_VEL.scaled(paddleDir);
		createdBalls = new ArrayList<Ball>();
		for(int i = 0; i < balls.length; ++i) {
			if(balls[i] != null) {
				createdBalls.add(balls[i]);
				createdBalls.addAll(collideBallPaddle(balls[i], paddleVel));
			}
		}
		balls = createdBalls.toArray(new Ball[createdBalls.size()]);

	}

	private void bounceBallsOnBlocks(int elapsedTime) {
		for(int i = 0; i < balls.length; ++i) {
			if(balls[i] != null) {
				balls[i] = collideBallBlocks(balls[i], elapsedTime);
			}
		}
	}

	private void removeDeadBalls() {
		for(int i = 0; i < balls.length; ++i) {
			balls[i] = removeDead(balls[i]);
		}
	}

	private void bounceBallsOnWalls() {
		for(int i = 0; i < balls.length; ++i) {
			balls[i] = bounceWalls(balls[i]);
		}
	}
	private void stepBalls(int elapsedTime) {
		for(int i = 0; i < balls.length; ++i) {
			Point newcenter = balls[i].getLocation().plus(balls[i].getVelocity().scaled(elapsedTime));
			balls[i].setLocation(newcenter);
		}
	}

	/**
	 * Move the paddle right.
	 * 
	 * @mutates this
	 */
	public void movePaddleRight(int elapsedTime) {
		Point ncenter = paddle.getCenter().plus(PADDLE_VEL.scaled(elapsedTime));
		paddle = paddle.move(getField().minusMargin(PaddleState.WIDTH/2,0).constrain(ncenter));
	}

	/**
	 * Move the paddle left.
	 * 
	 * @mutates this
	 */
	public void movePaddleLeft(int elapsedTime) {
		Point ncenter = paddle.getCenter().plus(PADDLE_VEL.scaled(-1).scaled(elapsedTime));
		paddle = paddle.move(getField().minusMargin(PaddleState.WIDTH/2,0).constrain(ncenter));
	}

	/**
	 * Return whether this BreakoutState represents a game where the player has won.
	 * 
	 * @post | result == (getBlocks().length == 0 && !isDead())
	 * @inspects this
	 */
	public boolean isWon() {
		return getBlocks().length == 0 && !isDead();
	}

	/**
	 * Return whether this BreakoutState represents a game where the player is dead.
	 * 
	 * @post | result == (getBalls().length == 0)
	 * @inspects this
	 */
	public boolean isDead() {
		return getBalls().length == 0;
	}
}

package breakout;

import java.awt.Color;

//No documentation required for this class
public class BreakoutFacade {

	public static final int lifetime = 10000;
	
	public PaddleState createNormalPaddleState(Point center) {
	
		return new PaddleState(center);
	}

	public Ball createNormalBall(Point center, int diameter, Vector initBallVelocity) {

		return new NormalBall(center, diameter, initBallVelocity);
	}
	
	public Ball createSuperchargedBall(Point center, int diameter, Vector initBallVelocity, int lifetime) {

		return new SuperchargedBall(center, diameter, initBallVelocity, lifetime);
	}

	public BreakoutState createBreakoutState(Ball[] balls, BlockState[] blocks, Point bottomRight,
			PaddleState paddle) {
	
		return new BreakoutState(balls, blocks, bottomRight, paddle);
	}

	public BlockState createNormalBlockState(Point topLeft, Point bottomRight) {

		Rect loc = new Rect(topLeft,bottomRight);
		return new NormalBlockState(loc);
	}

	public BlockState createSturdyBlockState(Point topLeft, Point bottomRight, int i) {

		Rect loc = new Rect(topLeft,bottomRight);
		return new SturdyBlockState(loc, i, Color.blue);
	}

	public BlockState createReplicatorBlockState(Point topLeft, Point bottomRight) {

		Rect loc = new Rect(topLeft,bottomRight);
		return new ReplicatorBlockState(loc);
	}

	public BlockState createPowerupBallBlockState(Point topLeft, Point bottomRight) {

		Rect loc = new Rect(topLeft,bottomRight);
		return new PowerupBallBlockState(loc);
	}

	public Color getColor(PaddleState paddle) {

		return paddle.Paint();
	}

	public Color getColor(Ball ball) {

		return ball.paint();
	}

	public Color getColor(BlockState block) {

		return block.paint();
	}

	public Rect getLocation(PaddleState paddle) {
		return paddle.getLocation();
	}

	public Point getCenter(Ball ball) {
		return ball.getLocation();
	}

	public int getDiameter(Ball ball) {
		return ball.getDiameter();
	}

	public Ball[] getBalls(BreakoutState breakoutState) {
		return breakoutState.getBalls();
	}

	public Rect getLocation(BlockState block) {
		return block.getLocation();
	}
}

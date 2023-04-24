package breakout;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;

import breakout.BreakoutState;
import org.junit.jupiter.api.Test;
import breakout.*;

class BreakoutStateTest {
	
	private Ball[] oneBall;
	private BlockState block;
	private BlockState[] oneBlock;
	private Point bottomRight;
	private PaddleState paddle;
	private BreakoutState stateBeforeBounceBlock;
	private Ball powerupball;
	private Ball ball;
	private BreakoutFacade facade = new BreakoutFacade();
	int paddleDir;
	int elapsedTime;
	private BlockState normalblock;
	private BlockState sturdyblock;
	private BlockState powerupblock;
	private BlockState replicatorblock;
	
	@BeforeEach
	void setUp() throws Exception {
		normalblock = facade.createNormalBlockState(new Point(0, 1051), new Point(2001, 1200));
		sturdyblock = facade.createSturdyBlockState(new Point(0, 1052), new Point(2002, 1200),3);
		powerupblock = facade.createPowerupBallBlockState(new Point(0, 1053), new Point(2003, 1200));
		replicatorblock = facade.createReplicatorBlockState(new Point(0, 1054), new Point(2004, 1200));
		ball = facade.createNormalBall(new Point(1000, 1000), 120, new Vector(5, 5));
		powerupball = facade.createSuperchargedBall(new Point(1000, 1000), 120, new Vector(5, 5),BreakoutFacade.lifetime);
		oneBall = new Ball[] { ball };
		block = facade.createNormalBlockState(new Point(0, 1051), new Point(2000, 1200));
		oneBlock = new BlockState[] { block };
		bottomRight = new Point(10000, 2000);
		paddle = facade.createNormalPaddleState(new Point(2000,1750));
		stateBeforeBounceBlock = facade.createBreakoutState(oneBall, oneBlock, bottomRight, paddle);
	}
	@Test
	void testStepBall() {
		paddleDir = 0;
		elapsedTime = 10;
		stateBeforeBounceBlock.tick(paddleDir, elapsedTime);
		assertEquals(new Point(1050,1050),ball.getLocation());
		assertEquals(new Vector(5,-5),ball.getVelocity()); // normal ball bounce on the normal block
	}
	
	@Test
	void testMovePaddleRight() {
		elapsedTime = 10;
		stateBeforeBounceBlock.movePaddleRight(elapsedTime);
		assertEquals((new Rect(Point.ORIGIN,bottomRight)).minusMargin(PaddleState.WIDTH/2,0).constrain(paddle.getCenter()),paddle.getCenter());
	}
	
	@Test
	void testMovePaddleLeft() {
		elapsedTime = 10;
		stateBeforeBounceBlock.movePaddleLeft(elapsedTime);
		assertEquals((new Rect(Point.ORIGIN,bottomRight)).minusMargin(PaddleState.WIDTH/2,0).constrain(paddle.getCenter()),paddle.getCenter());
	}
	
	@Test
	void testbounceBallOnPaddle() {
		paddleDir = 0;
		elapsedTime = 10;
		stateBeforeBounceBlock.tick(paddleDir, elapsedTime);
	}
	

	@Test
	void testcollideBallPaddle(){
		oneBlock = new BlockState[] { normalblock };
		oneBall = new Ball[] { ball };
		stateBeforeBounceBlock = facade.createBreakoutState(oneBall, oneBlock, bottomRight, paddle);
		elapsedTime = 1;
		//ball not collide with paddle
		stateBeforeBounceBlock.collideBallPaddle(ball, new Vector(1,0));
		assertEquals(-1, paddle.getCounts());
		assertEquals(0,stateBeforeBounceBlock.collideBallPaddle(ball, new Vector(1,0)).size());
		//paddle is replicator paddle, ball is not collide with paddle
		paddle = new ReplicatorPaddleState(paddle.getCenter(),3);
		stateBeforeBounceBlock = facade.createBreakoutState(oneBall, oneBlock, bottomRight, paddle);
		assertEquals(3, paddle.getCounts());
		assertEquals(true, paddle.getCounts()>0);
		stateBeforeBounceBlock.tick(0, 1);
		assertEquals(3, paddle.getCounts());
		assertEquals(0,stateBeforeBounceBlock.collideBallPaddle(ball, new Vector(1,0)).size());
		//ball collide with paddle
		ball = new NormalBall(new Point(2000,1300),600,ball.getVelocity());
		oneBall = new Ball[] { ball };
		stateBeforeBounceBlock = facade.createBreakoutState(oneBall, oneBlock, bottomRight, paddle);
		assertEquals(3, paddle.getCounts());
		assertEquals(true, paddle.getCounts()>0);
		stateBeforeBounceBlock.tick(0, 1);
		assertEquals(3, paddle.getCounts());
		assertEquals(0,stateBeforeBounceBlock.collideBallPaddle(ball, new Vector(1,0)).size());
		
	}
	
	@Test
	void testcollideNormalBallwithBlock_Normal(){
		//test_collide(not collide has been check by Ball.test)
		oneBlock = new BlockState[] { normalblock };
		oneBall = new Ball[] { ball };
		stateBeforeBounceBlock = facade.createBreakoutState(oneBall, oneBlock, bottomRight, paddle);
		elapsedTime = 1;
		stateBeforeBounceBlock.collideBallBlocks(ball, elapsedTime);
		assertEquals(false,ball.countDown(elapsedTime));
		assertEquals(1,stateBeforeBounceBlock.getCount().get(normalblock));
		assertEquals(new Vector(5,-5),ball.getVelocity());
		assertEquals(false,ball.hitBlock(normalblock.getLocation(),stateBeforeBounceBlock.getCount().get(normalblock)<= normalblock.getHitCounts()));
		assertEquals(true,stateBeforeBounceBlock.getCount().get(normalblock)==normalblock.getHitCounts());
		assertEquals(false,stateBeforeBounceBlock.getCount().get(normalblock)>normalblock.getHitCounts());
		assertEquals(ball,normalblock.collideWithBall(ball));
		assertEquals(ball,stateBeforeBounceBlock.collideBallBlocks(ball, elapsedTime));
		assertEquals(1,oneBlock.length); // not tick(), so not being removed
	}
	
	
	@Test
	void testcollideNormalBallwithBlock_Sturdy(){
		//test_collide
		oneBlock = new BlockState[] { sturdyblock };
		oneBall = new Ball[] { ball };
		stateBeforeBounceBlock = facade.createBreakoutState(oneBall, oneBlock, bottomRight, paddle);
		elapsedTime = 1;
		stateBeforeBounceBlock.collideBallBlocks(ball, elapsedTime);
		assertEquals(false,ball.countDown(elapsedTime));
		assertEquals(1,stateBeforeBounceBlock.getCount().get(sturdyblock));
		assertEquals(new Vector(5,-5),ball.getVelocity());
		assertEquals(false,ball.hitBlock(sturdyblock.getLocation(),stateBeforeBounceBlock.getCount().get(sturdyblock)<= sturdyblock.getHitCounts()));
		assertEquals(false,stateBeforeBounceBlock.getCount().get(sturdyblock)==sturdyblock.getHitCounts());
		assertEquals(false,stateBeforeBounceBlock.getCount().get(sturdyblock)>sturdyblock.getHitCounts());
		assertEquals(ball,sturdyblock.collideWithBall(ball));
		assertEquals(ball,stateBeforeBounceBlock.collideBallBlocks(ball, elapsedTime));
		assertEquals(1,oneBlock.length);
	}
	
	@Test
	void testcollideNormalBallwithBlock_powerup(){
		//collide
		oneBlock = new BlockState[] { powerupblock };
		oneBall = new Ball[] { ball };
		stateBeforeBounceBlock = facade.createBreakoutState(oneBall, oneBlock, bottomRight, paddle);
		elapsedTime = 1;
		stateBeforeBounceBlock.collideBallBlocks(ball, elapsedTime);
		assertEquals(false,ball.countDown(elapsedTime));
		assertEquals(1,stateBeforeBounceBlock.getCount().get(powerupblock));
		assertEquals(new Vector(5,-5),ball.getVelocity());
		assertEquals(false,ball.hitBlock(powerupblock.getLocation(),stateBeforeBounceBlock.getCount().get(powerupblock)<= powerupblock.getHitCounts()));
		assertEquals(true,stateBeforeBounceBlock.getCount().get(powerupblock)==powerupblock.getHitCounts());
		assertEquals(false,stateBeforeBounceBlock.getCount().get(powerupblock)>powerupblock.getHitCounts());
		assertEquals(ball.getLocation(),powerupblock.collideWithBall(ball).getLocation());
		assertEquals(ball,stateBeforeBounceBlock.collideBallBlocks(ball, elapsedTime));
		assertEquals(1,oneBlock.length);
	}
	
	@Test
	void testcollideNormalBallwithBlock_replicator(){
		//collide
		oneBlock = new BlockState[] { replicatorblock };
		oneBall = new Ball[] { ball };
		stateBeforeBounceBlock = facade.createBreakoutState(oneBall, oneBlock, bottomRight, paddle);
		elapsedTime = 1;
		stateBeforeBounceBlock.collideBallBlocks(ball, elapsedTime);
		assertEquals(false,ball.countDown(elapsedTime));
		assertEquals(1,stateBeforeBounceBlock.getCount().get(replicatorblock));
		assertEquals(new Vector(5,-5),ball.getVelocity());
		assertEquals(false,ball.hitBlock(replicatorblock.getLocation(),stateBeforeBounceBlock.getCount().get(replicatorblock)<= replicatorblock.getHitCounts()));
		assertEquals(false,stateBeforeBounceBlock.getCount().get(replicatorblock)==replicatorblock.getHitCounts());
		assertEquals(true,stateBeforeBounceBlock.getCount().get(replicatorblock)>replicatorblock.getHitCounts());
		assertEquals(-1,paddle.getCounts());
		assertEquals(ball.getLocation(),replicatorblock.collideWithBall(ball).getLocation());
		assertEquals(ball,stateBeforeBounceBlock.collideBallBlocks(ball, elapsedTime));
		assertEquals(1,oneBlock.length);//but we don't invoke tick to actually remove the block
		
	}
	
	@Test
	void testcollideSuperBallwithBlock_Normal() {
		oneBlock = new BlockState[] { normalblock };
		oneBall = new Ball[] { powerupball };
		stateBeforeBounceBlock = facade.createBreakoutState(oneBall, oneBlock, bottomRight, paddle);
		elapsedTime = 1;
		stateBeforeBounceBlock.collideBallBlocks(powerupball, elapsedTime);
		assertEquals(false,powerupball.countDown(elapsedTime));
		assertEquals(1,stateBeforeBounceBlock.getCount().get(normalblock));
		assertEquals(new Vector(5,5),powerupball.getVelocity());
		assertEquals(true,powerupball.hitBlock(normalblock.getLocation(),stateBeforeBounceBlock.getCount().get(normalblock)<= normalblock.getHitCounts()));
		assertEquals(false,stateBeforeBounceBlock.getCount().get(normalblock)==normalblock.getHitCounts());
		assertEquals(true,stateBeforeBounceBlock.getCount().get(normalblock)>normalblock.getHitCounts());
		assertEquals(true,normalblock.collideWithBall(powerupball).getClass() == powerupball.getClass());
		assertEquals(true,stateBeforeBounceBlock.collideBallBlocks(powerupball, elapsedTime).getClass() == powerupball.getClass());
		assertEquals(1,oneBlock.length);
		assertEquals(true,stateBeforeBounceBlock.collideBallBlocks(powerupball, 10002).getClass() == ball.getClass());
		assertEquals(false,stateBeforeBounceBlock.collideBallBlocks(powerupball, 10002).countDown(10002));
	}
	@Test
	void testcollideSuperBallwithBlock_Sturdy() {
		oneBlock = new BlockState[] { sturdyblock };
		oneBall = new Ball[] { powerupball };
		stateBeforeBounceBlock = facade.createBreakoutState(oneBall, oneBlock, bottomRight, paddle);
		elapsedTime = 1;
		stateBeforeBounceBlock.collideBallBlocks(powerupball, elapsedTime);
		assertEquals(false,powerupball.countDown(elapsedTime));
		assertEquals(1,stateBeforeBounceBlock.getCount().get(sturdyblock));
		assertEquals(new Vector(5,-5),powerupball.getVelocity());
		assertEquals(true,powerupball.hitBlock(sturdyblock.getLocation(),stateBeforeBounceBlock.getCount().get(sturdyblock)<= sturdyblock.getHitCounts()));
		assertEquals(false,stateBeforeBounceBlock.getCount().get(sturdyblock)==sturdyblock.getHitCounts());
		assertEquals(false,stateBeforeBounceBlock.getCount().get(sturdyblock)>sturdyblock.getHitCounts());
		assertEquals(true,sturdyblock.collideWithBall(powerupball).getClass() == powerupball.getClass());
		assertEquals(true,stateBeforeBounceBlock.collideBallBlocks(powerupball, elapsedTime).getClass() == powerupball.getClass());
		assertEquals(1,oneBlock.length);
	}
	
}

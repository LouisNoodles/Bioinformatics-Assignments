package breakout;

import static org.junit.jupiter.api.Assertions.*;
import breakout.*;

import java.awt.Color;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BlockTest {
	
	private BreakoutFacade facade = new BreakoutFacade();
	private Ball ball;
	private Ball powerupball;
	private BlockState normalblock;
	private BlockState sturdyblock;
	private BlockState powerupblock;
	private BlockState replicatorblock;
	private BreakoutState breakoutstate;
	private Ball[] balls;
	private BlockState[] blocks;
	private PaddleState paddle;
	private Point bottomRight;
	private HashMap<BlockState,Integer> map;
	
	
	@BeforeEach
	void setUp() throws Exception {
		normalblock = facade.createNormalBlockState(new Point(0, 1051), new Point(2001, 1200));
		sturdyblock = facade.createSturdyBlockState(new Point(0, 1052), new Point(2002, 1200),3);
		powerupblock = facade.createPowerupBallBlockState(new Point(0, 1053), new Point(2003, 1200));
		replicatorblock = facade.createReplicatorBlockState(new Point(0, 1054), new Point(2004, 1200));
		ball = facade.createNormalBall(new Point(1000, 1000), 100, new Vector(0, 5));
		powerupball = facade.createSuperchargedBall(new Point(1000, 1000), 100, new Vector(0, 5),BreakoutFacade.lifetime);
		bottomRight = new Point(10000, 2000);
		blocks = new BlockState[] {normalblock,sturdyblock,powerupblock,replicatorblock};
		balls = new Ball[] {ball,powerupball};
		paddle = facade.createNormalPaddleState(new Point(2000, 1750));
		breakoutstate = facade.createBreakoutState(balls,blocks,bottomRight,paddle);
	}
	
	@Test
	void testBlock(){
		assertEquals(new Rect(new Point(0, 1051), new Point(2001, 1200)),normalblock.getLocation());
		assertEquals(new Rect(new Point(0, 1052), new Point(2002, 1200)),sturdyblock.getLocation());
		assertEquals(new Rect(new Point(0, 1053), new Point(2003, 1200)),powerupblock.getLocation());
		assertEquals(new Rect(new Point(0, 1054), new Point(2004, 1200)),replicatorblock.getLocation());
		assertEquals(Color.green,normalblock.paint());
		assertEquals(Color.blue,sturdyblock.paint()); 
		assertEquals(Color.red,powerupblock.paint());
		assertEquals(Color.darkGray,replicatorblock.paint());
	}
	
	@Test
	void testColliddeWithBall(){
		assertEquals(ball,normalblock.collideWithBall(ball));
		assertEquals(ball,sturdyblock.collideWithBall(ball));
		assertEquals(powerupball.getLocation(),powerupblock.collideWithBall(ball).getLocation());
		assertEquals(ball,replicatorblock.collideWithBall(ball));
	}
	

	@Test 
	void testGetHitCounts() {
		assertEquals(1,normalblock.getHitCounts());
		assertEquals(3,sturdyblock.getHitCounts());
		assertEquals(1,powerupblock.getHitCounts());
		assertEquals(0,replicatorblock.getHitCounts());
	}
	
	@Test 
	void testvoidPaintRectFirst(){
		map = breakoutstate.getCount();
		map.put(normalblock, 1);
		map.put(sturdyblock, 1);
		map.put(replicatorblock, 1);
		map.put(powerupblock, 1);
		normalblock.paint(normalblock.getLocation());
		sturdyblock.paint(sturdyblock.getLocation());
		powerupblock.paint(powerupblock.getLocation());
		replicatorblock.paint(replicatorblock.getLocation());
		assertEquals(Color.green,normalblock.paint());
		assertEquals(Color.cyan,sturdyblock.paint()); 
		assertEquals(Color.red,powerupblock.paint());
		assertEquals(Color.darkGray,replicatorblock.paint());
	}
	
	@Test 
	void testvoidPaintRectSecond(){
		map = breakoutstate.getCount();
		map.put(normalblock, 2);
		map.put(sturdyblock, 2);
		map.put(replicatorblock, 2);
		map.put(powerupblock, 2);
		normalblock.paint(normalblock.getLocation());
		sturdyblock.paint(sturdyblock.getLocation());
		powerupblock.paint(powerupblock.getLocation());
		replicatorblock.paint(replicatorblock.getLocation());
		assertEquals(Color.green,normalblock.paint());
		assertEquals(Color.green,sturdyblock.paint()); 
		assertEquals(Color.red,powerupblock.paint());
		assertEquals(Color.darkGray,replicatorblock.paint());
	}
}


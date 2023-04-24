package breakout;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import breakout.*;


//test all type of the ball 
class BallTest {
	private BreakoutFacade facade = new BreakoutFacade();
	private Ball ball;
	private Ball powerupball;
	private Ball powerupball2;
	private BlockState normalblock;
	private BlockState sturdyblock;
	private BlockState powerupblock;
	private BlockState replicatorblock;
	
	@BeforeEach
	void setUp() throws Exception {
		
		normalblock = facade.createNormalBlockState(new Point(0, 1051), new Point(2001, 1200));
		sturdyblock = facade.createSturdyBlockState(new Point(0, 1552), new Point(2002, 2200),3);
		ball = facade.createNormalBall(new Point(1000, 1000), 100, new Vector(0, 5));
		powerupball = facade.createSuperchargedBall(new Point(1000, 1001), 200, new Vector(0, 6),BreakoutFacade.lifetime);
		powerupball2 = facade.createSuperchargedBall(new Point(1000, 1001), 200, new Vector(0, 6),10);
		
	}
	
	@Test
	void testBall(){
		assertEquals(new Point(1000, 1000),ball.getLocation());
		assertEquals(new Point(1000, 1001),powerupball.getLocation());
		assertEquals(new Vector(0, 5),ball.getVelocity());
		assertEquals(new Vector(0, 6),powerupball.getVelocity());
		assertEquals(100,ball.getDiameter());
		assertEquals(200,powerupball.getDiameter());
		assertEquals(10000,powerupball.getLifeTime());
	}
	
	@Test 
	void testSetMethod() {
		ball.setDiameter(10);
		ball.setLifeTime(10000);
		ball.setVelocity(new Vector(5,5));
		ball.setLocation(new Point(10,20));
		assertEquals(new Point(10, 20),ball.getLocation());
		assertEquals(new Vector(0,6),powerupball.getVelocity());
		assertEquals(10,ball.getDiameter());
		assertEquals(10000,ball.getLifeTime());
	}
	
	@Test 
	void testbounceOn(){
		assertEquals(null,ball.bounceOn(normalblock.getLocation()));
		assertNotEquals(ball.getVelocity().mirrorOver(Vector.DOWN),ball.bounceOn(normalblock.getLocation()));
		assertEquals(null,ball.bounceOn(sturdyblock.getLocation()));
	}
	
	@Test 
	void testPaint(){
		assertEquals(Color.white,ball.paint());
		assertEquals(Color.orange,powerupball.paint());
	}
	
	@Test
	void testBallHitBlock() {
		boolean k;
		k = ball.hitBlock(normalblock.getLocation(),true);
		assertEquals(false,k);
		k = ball.hitBlock(sturdyblock.getLocation(),true);
		assertEquals(false,k);
		k = powerupball.hitBlock(sturdyblock.getLocation(), true);
		assertEquals(false,k);
	}
	@Test
	void testcountDown() {
		int elapsedTime = 10;
		int elapsedTime2 = 20;
		assertEquals(false ,ball.countDown(elapsedTime));
		assertEquals(false ,ball.countDown(elapsedTime2));
		assertEquals(false ,powerupball.countDown(elapsedTime));
		assertEquals(true ,powerupball2.countDown(elapsedTime2));
	}
	
	
	
}

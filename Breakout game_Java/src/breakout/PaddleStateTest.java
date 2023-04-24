package breakout;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import breakout.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//this is for all paddle
class PaddleStateTest {

	private BreakoutFacade facade = new BreakoutFacade();
	private PaddleState paddle;
	private PaddleState replicatepaddle;
	
	@BeforeEach
	void setUp() throws Exception {
		paddle = facade.createNormalPaddleState(new Point(2000, 1750));
		replicatepaddle = new ReplicatorPaddleState(new Point(2001,1750),3);
	}
	
	@Test
	void testPaddle() {
		assertEquals(new Point(2000, 1750),paddle.getCenter());
		assertEquals(new Point(2001, 1750),replicatepaddle.getCenter());
		assertEquals(3,replicatepaddle.getCounts());
	}
	
	@Test
	void testPaddleLocation() {
		assertEquals(new Point(500,1500),paddle.getLocation().getTopLeft());
		assertEquals(new Point(3500,2000),paddle.getLocation().getBottomRight());
		assertEquals(new Point(501, 1500),replicatepaddle.getLocation().getTopLeft());
		assertEquals(new Point(3501, 2000),replicatepaddle.getLocation().getBottomRight());
	}
	
	@Test
	void testPaint() {
		assertEquals(Color.orange,paddle.Paint());
		assertEquals(Color.magenta,replicatepaddle.Paint());
	}
	@Test
	void testgetCounts(){
		assertEquals(-1,paddle.getCounts());
		assertEquals(3,replicatepaddle.getCounts());
	}
	
	@Test
	void testMoveCenter() {
		paddle = paddle.move(new Point(2050,1750));
		assertEquals(new Point(2050,1750),paddle.getCenter());
		assertEquals(PaddleState.class,paddle.getClass());
		replicatepaddle = replicatepaddle.move(new Point(2050,1750));
		assertEquals(new Point(2050,1750),replicatepaddle.getCenter());
		assertEquals(3,replicatepaddle.getCounts());
		assertEquals(ReplicatorPaddleState.class,replicatepaddle.getClass());
	}
}

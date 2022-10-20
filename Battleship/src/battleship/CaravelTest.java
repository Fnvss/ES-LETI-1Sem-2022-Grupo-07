/**
 * 
 */
package battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author fba
 *
 */
class CaravelTest
{
	static Caravel cN, cS, cE, cW;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception
	{
		cN = new Caravel(Compass.NORTH, new Position(5, 5));
		cS = new Caravel(Compass.SOUTH, new Position(5, 5));
		cE = new Caravel(Compass.EAST, new Position(5, 5));
		cW = new Caravel(Compass.WEST, new Position(5, 5));	
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception
	{

	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception
	{

	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception
	{

	}

	/**
	 * Test method for {@link battleship.Caravel#getSize()}.
	 */
	@Test
	final void testGetSize()
	{
		 assertEquals(2, cN.getSize());
		 assertEquals(2, cS.getSize());
		 assertEquals(2, cE.getSize());
		 assertEquals(2, cW.getSize());
	}

	/**
	 * Test method for {@link battleship.Caravel#Caravel(battleship.Compass, battleship.IPosition)}.
	 */
	@Test
	final void testCaravel()
	{
			Assertions.assertNotNull(cN);
			assertEquals(Compass.NORTH, cN.getBearing());
			assertEquals(5, cN.getTopMostPos());
			
			assertNotNull(cS);
			assertEquals(Compass.SOUTH, cS.getBearing());
			assertEquals(6, cS.getBottomMostPos());		

			assertNotNull(cE);
			assertEquals(Compass.EAST, cE.getBearing());
			assertEquals(6, cE.getRightMostPos());	
			
			assertNotNull(cW);
			assertEquals(Compass.WEST, cW.getBearing());
			assertEquals(5, cW.getLeftMostPos());
			
			assertThrows(IllegalArgumentException.class, () -> new Caravel(null, new Position(0, 0)));
			assertThrows(IllegalArgumentException.class, () -> new Caravel(null, null));
	}

}
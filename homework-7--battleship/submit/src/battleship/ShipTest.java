package battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShipTest {

	Ocean ocean;
	Ship ship;
	
	@BeforeEach
	void setUp() throws Exception {
		ocean = new Ocean();
	}

	@Test
	void testGetLength() {

		//Test 1: creates a new Battleship and checks its length
		ship = new Battleship();
		assertEquals(4, ship.getLength(), "Battleship length should be 4.");
		
		//Test 2: creates a new Cruiser and checks its length
		ship = new Cruiser();
		assertEquals(3, ship.getLength(), "Cruiser length should be 3.");
		
		//Test 3: creates a new Destroyer and checks its length
		ship = new Destroyer();
		assertEquals(2, ship.getLength(), "Destroyer length should be 2.");
		
		//Test 4: creates a new Submarine and checks its length
		ship = new Submarine();
		assertEquals(1, ship.getLength(), "Submarine length should be 1.");
		
	}

	@Test
	void testGetBowRow() {

		//Test 1: places a Battleship horizontally in the top row
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		battleship.placeShipAt(row, column, horizontal, ocean);
		assertEquals(row, battleship.getBowRow(), "Battleship's bow row should be 0.");
		
		//Test 2: places a Cruiser vertically in a middle row
		Ship cruiser = new Cruiser();
		row = 3;
		column = 0;
		horizontal = false;
		cruiser.placeShipAt(row, column, horizontal, ocean);
		assertEquals(row, cruiser.getBowRow(), "Cruiser's bow row should be 3.");
		
		//Test 3: places a Destroyer horizontally in a middle row
		Ship destroyer = new Destroyer();
		row = 6;
		column = 9;
		horizontal = true;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		assertEquals(row, destroyer.getBowRow(), "Destroyer's bow row should be 6.");
		
		//Test 4: places a Submarine vertically in the last row
		Ship submarine = new Submarine();
		row = 9;
		column = 7;
		horizontal = false;
		submarine.placeShipAt(row, column, horizontal, ocean);
		assertEquals(row, submarine.getBowRow(), "Submarine's bow row should be 9.");
		
	}

	@Test
	void testGetBowColumn() {

		//Test 1: places a Battleship horizontally in a middle column
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		battleship.placeShipAt(row, column, horizontal, ocean);
		battleship.setBowColumn(column);
		assertEquals(column, battleship.getBowColumn(), "Battleship's bow column should be 4.");	
		
		//Test 2: places a Cruiser vertically in the first column
		Ship cruiser = new Cruiser();
		row = 3;
		column = 0;
		horizontal = false;
		cruiser.placeShipAt(row, column, horizontal, ocean);
		assertEquals(column, cruiser.getBowColumn(), "Cruiser's bow column should be 0.");
		
		//Test 3: places a Destroyer horizontally in the last column
		Ship destroyer = new Destroyer();
		row = 6;
		column = 9;
		horizontal = true;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		assertEquals(column, destroyer.getBowColumn(), "Destroyer's bow column should be 9.");
				
		//Test 4: places a Submarine vertically in a middle column
		Ship submarine = new Submarine();
		row = 9;
		column = 7;
		horizontal = false;
		submarine.placeShipAt(row, column, horizontal, ocean);
		assertEquals(column, submarine.getBowColumn(), "Submarine's bow column should be 7.");
		
	}

	@Test
	void testGetHit() {

		//Test 1: sets up a new Battleship with initial hit array all set to false
		ship = new Battleship();
		boolean[] hits = new boolean[4];
		assertArrayEquals(hits, ship.getHit());
		assertFalse(ship.getHit()[0]);
		assertFalse(ship.getHit()[1]);
		assertFalse(ship.getHit()[2]);
		assertFalse(ship.getHit()[3]);
		
		//stimulates a hit on the Battleship and verify hit array
		ship.getHit()[1] = true;
		hits[1] = true;
		assertTrue(ship.getHit()[1], "Hit at position 1 should now be true.");
		
		//Test 2: sets up a new Cruiser and tests multiple hits
		Ship cruiser = new Cruiser();
		boolean[] hits2 = new boolean[3];
		assertArrayEquals(hits2, cruiser.getHit());
		assertFalse(cruiser.getHit()[0]);
		assertFalse(cruiser.getHit()[1]);
		assertFalse(cruiser.getHit()[2]);
		
		cruiser.getHit()[0] = true;
		hits2[0] = true;
		assertTrue(cruiser.getHit()[0], "Hit at position 0 should now be true.");
		
		cruiser.getHit()[1] = true;
		hits2[1] = true;
		assertTrue(cruiser.getHit()[1], "Hit at position 1 should now be true.");
		assertFalse(cruiser.isSunk(), "The Cruiser should not be sunk after 2 hits.");
		
		//Test 3: sets up a new Submarine, hits it, and sinks the ship
		Ship sub = new Submarine();
		boolean[] hits3 = new boolean[1];
		assertArrayEquals(hits3, sub.getHit());
		assertFalse(sub.getHit()[0]);
		
		sub.getHit()[0] = true;
		hits3[0] = true;
		assertTrue(sub.getHit()[0], "Hit at position 0 should now be true.");
		assertTrue(sub.isSunk(), "The Submarine should be sunk after being hit.");
		
	}
	@Test
	void testGetShipType() {

		//Test 1: tests a Battleship
		ship = new Battleship();
		assertEquals("battleship", ship.getShipType());
		
		//Test 2: tests a Cruiser
		Ship cruiser = new Cruiser();
		assertEquals("cruiser", cruiser.getShipType());
		
		//Test 3: tests a Destroyer
		Ship destroyer = new Destroyer();
		assertEquals("destroyer", destroyer.getShipType());
		
		//Test 4: tests a Submarine
		Ship sub = new Submarine();
		assertEquals("submarine", sub.getShipType());
		
	}
	
	@Test
	void testIsHorizontal() {

		//Test 1: places a Battleship horizontally
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		battleship.placeShipAt(row, column, horizontal, ocean);
		assertTrue(battleship.isHorizontal(), "isHorizontal() should return true for a Battleship placed horizontally.");
		
		//Test 2: places a Cruiser vertically
		Ship cruiser = new Cruiser();
		row = 3;
		column = 0;
		horizontal = false;
		cruiser.placeShipAt(row, column, horizontal, ocean);
		assertFalse(cruiser.isHorizontal(), "isHorizontal() should return false for a Cruiser placed vertically.");
				
		//Test 3: places a Destroyer horizontally
		Ship destroyer = new Destroyer();
		row = 6;
		column = 9;
		horizontal = true;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		assertTrue(destroyer.isHorizontal(), "isHorizontal() should return true for a Destroyer placed horizontally.");
				
		//Test 4: places a Submarine vertically
		Ship submarine = new Submarine();
		row = 9;
		column = 7;
		horizontal = false;
		submarine.placeShipAt(row, column, horizontal, ocean);
		assertFalse(submarine.isHorizontal(), "isHorizontal() should return false for a Submarine placed vertically.");
		
	}
	
	@Test
	void testSetBowRow() {

		//Test 1: sets the bow row for a Battleship and verify
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		battleship.setBowRow(row);
		assertEquals(row, battleship.getBowRow());
		
		//Test 2: sets the bow row for a Cruiser and verify
		Ship cruiser = new Cruiser();
		row = 3;
		column = 0;
		horizontal = false;
		cruiser.setBowRow(row);
		assertEquals(row, cruiser.getBowRow());
						
		//Test 3: sets the bow row for a Destroyer and verify
		Ship destroyer = new Destroyer();
		row = 6;
		column = 9;
		horizontal = true;
		destroyer.setBowRow(row);
		assertEquals(row, destroyer.getBowRow());
						
		//Test 4: sets the bow row for a Submarine and verify
		Ship submarine = new Submarine();
		row = 9;
		column = 7;
		horizontal = false;
		submarine.setBowRow(row);
		assertEquals(row, submarine.getBowRow());
		
	}

	@Test
	void testSetBowColumn() {

		//Test 1: sets the bow column for a Battleship and verify
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		battleship.setBowColumn(column);
		assertEquals(column, battleship.getBowColumn());
		
		//Test 2: sets the bow column for a Cruiser and verify
		Ship cruiser = new Cruiser();
		row = 3;
		column = 0;
		horizontal = false;
		cruiser.setBowColumn(column);
		assertEquals(column, cruiser.getBowColumn());
								
		//Test 3: sets the bow column for a Destroyer and verify
		Ship destroyer = new Destroyer();
		row = 6;
		column = 9;
		horizontal = true;
		destroyer.setBowColumn(column);;
		assertEquals(column, destroyer.getBowColumn());
								
		//Test 4: sets the bow column for a Submarine and verify
		Ship submarine = new Submarine();
		row = 9;
		column = 7;
		horizontal = false;
		submarine.setBowColumn(column);;
		assertEquals(column, submarine.getBowColumn());
		
	}

	@Test
	void testSetHorizontal() {

		//Test 1: sets a horizontal Battleship and verify
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		battleship.setHorizontal(horizontal);
		assertTrue(battleship.isHorizontal());
		
		//Test 2: sets a vertical Cruiser and verify
		Ship cruiser = new Cruiser();
		row = 3;
		column = 0;
		horizontal = false;
		cruiser.setHorizontal(horizontal);
		assertFalse(cruiser.isHorizontal());
										
		//Test 3: sets a horizontal Cruiser and verify
		Ship destroyer = new Destroyer();
		row = 6;
		column = 9;
		horizontal = true;
		destroyer.setHorizontal(horizontal);
		assertTrue(destroyer.isHorizontal());
										
		//Test 4: sets a vertical Submarine and verify
		Ship submarine = new Submarine();
		row = 9;
		column = 7;
		horizontal = false;
		submarine.setHorizontal(horizontal);;
		assertFalse(submarine.isHorizontal());
		
	}

	@Test
	void testOkToPlaceShipAt() {
		
		//test when other ships are not in the ocean

		//Test 1: places a valid ship
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		boolean ok = battleship.okToPlaceShipAt(row, column, horizontal, ocean);
		assertTrue(ok, "OK to place ship here.");
		
		//Test 2: places a valid ship near the edge of the ocean
		Ship cruiser = new Cruiser();
		row = 7;
		column = 0;
		horizontal = false;
		ok = cruiser.okToPlaceShipAt(row, column, horizontal, ocean);
		assertTrue(ok, "OK to place ship near the edge.");
		
		//Test 3: places an invalid ship exceeding ocean's boundaries
		Ship destroyer = new Destroyer();
		row = 0;
		column = 9;
		horizontal = false;
		ok = destroyer.okToPlaceShipAt(row, column, horizontal, ocean);
		assertFalse(ok, "NOT ok to place ship out of bounds.");
		
	}
	
	@Test
	void testOkToPlaceShipAtAgainstOtherShipsOneBattleship() {
		
		//test when other ships are in the ocean
		
		//Test 1: tests an invalid horizontal placement
		//place first ship
		Battleship battleship1 = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		boolean ok1 = battleship1.okToPlaceShipAt(row, column, horizontal, ocean);
		assertTrue(ok1, "OK to place ship here.");
		battleship1.placeShipAt(row, column, horizontal, ocean);

		//test second ship
		Battleship battleship2 = new Battleship();
		row = 1;
		column = 4;
		horizontal = true;
		boolean ok2 = battleship2.okToPlaceShipAt(row, column, horizontal, ocean);
		assertFalse(ok2, "Not OK to place ship vertically adjacent below.");
		
		//Test 2: tests a valid vertical placement
		//place first ship
		Cruiser cruiser1 = new Cruiser();
		row = 9;
		column = 9;
		horizontal = false;
		ok1 = cruiser1.okToPlaceShipAt(row, column, horizontal, ocean);
		assertTrue(ok1, "OK to place ship here.");
		cruiser1.placeShipAt(row, column, horizontal, ocean);
		
		//test second ship
		Cruiser cruiser2 = new Cruiser();
		row = 9;
		column = 7;
		horizontal = false;
		ok2 = cruiser2.okToPlaceShipAt(row, column, horizontal, ocean);
		assertTrue(ok2, "OK to place ship horizontally one row over.");
		
		//Test 3: tests an invalid overlapping placement
		//place first ship
		Destroyer destroyer1 = new Destroyer();
		row = 5;
		column = 4;
		horizontal = true;
		ok1 = destroyer1.okToPlaceShipAt(row, column, horizontal, ocean);
		assertTrue(ok1, "OK to place ship here.");
		destroyer1.placeShipAt(row, column, horizontal, ocean);
		
		//test second ship
		Destroyer destroyer2 = new Destroyer();
		row = 6;
		column = 4;
		horizontal = false;
		ok2 = destroyer2.okToPlaceShipAt(row, column, horizontal, ocean);
		assertFalse(ok2, "NOT ok to place ship on another overlapping ship.");
		
	}

	@Test
	void testPlaceShipAt() {
		
		//Test 1: places a Battleship and verify
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		battleship.placeShipAt(row, column, horizontal, ocean);
		assertEquals(row, battleship.getBowRow());
		assertEquals(column, battleship.getBowColumn());
		assertTrue(battleship.isHorizontal());
		
		assertEquals("empty", ocean.getShipArray()[0][0].getShipType());
		assertEquals(battleship, ocean.getShipArray()[0][1]);
		
		//Test 2: places a Cruiser and verify
		Ship cruiser = new Cruiser();
		row = 3;
		column = 0;
		horizontal = false;
		cruiser.placeShipAt(row, column, horizontal, ocean);
		assertEquals(row, cruiser.getBowRow());
		assertEquals(column, cruiser.getBowColumn());
		assertFalse(cruiser.isHorizontal());
		
		assertEquals(cruiser, ocean.getShipArray()[1][0]);
		assertEquals(cruiser, ocean.getShipArray()[2][0]);
		assertEquals("empty", ocean.getShipArray()[4][0].getShipType());
			
		//Test 3: places a Submarine and verify
		Ship submarine = new Submarine();
		row = 9;
		column = 9;
		horizontal = true;
		submarine.placeShipAt(row, column, horizontal, ocean);
		assertEquals(row, submarine.getBowRow());
		assertEquals(column, submarine.getBowColumn());
		assertTrue(submarine.isHorizontal());
		
		assertEquals(submarine, ocean.getShipArray()[9][9]);
		assertEquals("empty", ocean.getShipArray()[9][8].getShipType());
		assertEquals("empty", ocean.getShipArray()[8][9].getShipType());
		
	}

	@Test
	void testShootAt() {
		
		//Test 1: shooting at location not occupied by Battleship
		Ship battleship = new Battleship();
		int row = 0;
		int column = 9;
		boolean horizontal = true;
		battleship.placeShipAt(row, column, horizontal, ocean);
		
		assertFalse(battleship.shootAt(1, 9));
		boolean[] hitArray0 = {false, false, false, false};
		assertArrayEquals(hitArray0, battleship.getHit(), "Missed. Hit array should remain unchanged.");
		
		//Test 2: shooting at location occupied by Battleship
		assertTrue(battleship.shootAt(0, 9));
		boolean[] hitArray1 = {true, false, false, false};
		assertArrayEquals(hitArray1, battleship.getHit(), "Hit. First item in hit array should be marked.");
		
		//Test 3: sinks the Battleship, then shoots at the already sunken ship
		assertTrue(battleship.shootAt(0, 8));
		assertTrue(battleship.shootAt(0, 7));
		assertTrue(battleship.shootAt(0, 6));
		boolean[] hitArray2 = {true, true, true, true};
		assertArrayEquals(hitArray2, battleship.getHit(), "All parts of the ship should now be hit.");
		
		assertTrue(battleship.isSunk(), "Battleship should be marked as sunk.");
		assertFalse(battleship.shootAt(0, 9), "Shooting at a sunken Battleship should return false.");
		
	}
	
	@Test
	void testIsSunk() {
		
		//Test 1: places a Submarine then shoots at an unrelated location
		Ship submarine = new Submarine();
		int row = 3;
		int column = 3;
		boolean horizontal = true;
		submarine.placeShipAt(row, column, horizontal, ocean);
		
		assertFalse(submarine.isSunk());
		assertFalse(submarine.shootAt(5, 2));
		assertFalse(submarine.isSunk(), "Shooting at an unrelated location should not affect the submarine.");
		
		//Test 2: shoots at the Submarine and sinks it
		assertTrue(submarine.shootAt(3, 3));
		assertTrue(submarine.isSunk(), "Submarine should be marked as sunk after shooting at it.");
		
		//Test 3: places a Cruiser and shoots at part of it
		Ship cruiser = new Cruiser();
		row = 8;
		column = 1;
		horizontal = false;
		cruiser.placeShipAt(row, column, horizontal, ocean);
		
		assertFalse(cruiser.isSunk());
		assertTrue(cruiser.shootAt(8, 1));
		assertTrue(cruiser.shootAt(7, 1));
		assertFalse(cruiser.isSunk(), "Cruiser should not be sunk after being partially hit.");
		
		//Test 4: sinks the Cruiser and verifies sunken status
		assertTrue(cruiser.shootAt(6, 1));
		assertTrue(cruiser.isSunk(), "Cruiser should be sunk after all parts have been hit.");
				
	}

	@Test
	void testToString() {
		
		//Test 1: places a Battleship and partially hits it
		Ship battleship = new Battleship();
		assertEquals("x", battleship.toString());
		
		int row = 9;
		int column = 1;
		boolean horizontal = false;
		battleship.placeShipAt(row, column, horizontal, ocean);
		battleship.shootAt(9, 1);
		assertEquals("x", battleship.toString(), "A partially hit Battleship should still return 'x'");
		
		//Test 2: places a Submarine and sinks it
		Ship sub = new Submarine();
		assertEquals("x", sub.toString());
		
		row = 0;
		column = 9;
		horizontal = true;
		sub.placeShipAt(row, column, horizontal, ocean);
		sub.shootAt(0, 9);
		assertEquals("s", sub.toString(), "A sunken Submarine should return 's'");
		
		//Test 3: places a Destroyer and verifies output until its sunk
		Ship destroyer = new Destroyer();
		assertEquals("x", destroyer.toString());
		
		row = 4;
		column = 7;
		horizontal = true;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		destroyer.shootAt(4, 7);
		assertEquals("x", destroyer.toString(), "A partially hit Destroyer should still return 'x'");
		
		destroyer.shootAt(4, 6);
		assertEquals("s", destroyer.toString(), "A sunken Destroyer should return 's'");
		
	}

}
package battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OceanTest {

	Ocean ocean;
	
	static int NUM_BATTLESHIPS = 1;
	static int NUM_CRUISERS = 2;
	static int NUM_DESTROYERS = 3;
	static int NUM_SUBMARINES = 4;
	static int OCEAN_SIZE = 10;
	
	@BeforeEach
	void setUp() throws Exception {
		ocean = new Ocean();
	}
	
	@Test
	void testEmptyOcean() {
		
		//tests that all locations in the ocean are "empty"
		
		Ship[][] ships = ocean.getShipArray();
		
		for (int i = 0; i < ships.length; i++) {
			for (int j = 0; j < ships[i].length; j++) {
				Ship ship = ships[i][j];
				
				assertEquals("empty", ship.getShipType());
			}
		}
		
		assertEquals(0, ships[0][0].getBowRow());
		assertEquals(0, ships[0][0].getBowColumn());
		
		assertEquals(5, ships[5][5].getBowRow());
		assertEquals(5, ships[5][5].getBowColumn());
		
		assertEquals(9, ships[9][0].getBowRow());
		assertEquals(0, ships[9][0].getBowColumn());
	}
	
	@Test
	void testPlaceAllShipsRandomly() {
		
		//tests that the correct number of each ship type is placed in the ocean
		
		ocean.placeAllShipsRandomly();

		Ship[][] ships = ocean.getShipArray();
		ArrayList<Ship> shipsFound = new ArrayList<Ship>();
		
		int numBattlehips = 0;
		int numCruisers = 0;
		int numDestroyers = 0;
		int numSubmarines = 0;
		int numEmptySeas = 0;
		
		for (int i = 0; i < ships.length; i++) {
			for (int j = 0; j < ships[i].length; j++) {
				Ship ship = ships[i][j];
				if (!shipsFound.contains(ship)) {
					shipsFound.add(ship);
				}
			}
		}
		
		for (Ship ship : shipsFound) {
			if ("battleship".equals(ship.getShipType())) {		
				numBattlehips++;
			} else if ("cruiser".equals(ship.getShipType())) {
				numCruisers++;
			} else if ("destroyer".equals(ship.getShipType())) {
				numDestroyers++;
			} else if ("submarine".equals(ship.getShipType())) {
				numSubmarines++;
			} else if ("empty".equals(ship.getShipType())) {
				numEmptySeas++;
			}
		}
		
		assertEquals(NUM_BATTLESHIPS, numBattlehips);
		assertEquals(NUM_CRUISERS, numCruisers);
		assertEquals(NUM_DESTROYERS, numDestroyers);
		assertEquals(NUM_SUBMARINES, numSubmarines);
		
		//calculate total number of available spaces and occupied spaces
		int totalSpaces = OCEAN_SIZE * OCEAN_SIZE; 
		int occupiedSpaces = (NUM_BATTLESHIPS * 4)
				+ (NUM_CRUISERS * 3)
				+ (NUM_DESTROYERS * 2)
				+ (NUM_SUBMARINES * 1);
		
		//test number of empty seas, each with length of 1
		assertEquals(totalSpaces - occupiedSpaces, numEmptySeas);
	}

	@Test
	void testIsOccupied() {

		//Test 1: place a Destroyer and verify occupied positions
		Destroyer destroyer = new Destroyer();
		int row = 1;
		int column = 5;
		boolean horizontal = false;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.isOccupied(1, 5), "Position (1, 5) should be occupied by the Destroyer.");
		assertTrue(ocean.isOccupied(0, 5), "Position (0, 5) should be occupied by the Destroyer.");
		assertFalse(ocean.isOccupied(2, 5), "Position (2, 5) should NOT be occupied by the Destroyer.");
		
		//Test 2: place a Submarine at the edge of the ocean and verify its position
		Ship submarine = new Submarine();
		row = 0;
		column = 0;
		horizontal = false;
		submarine.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.isOccupied(0, 0), "Position (0, 0) should be occupied by the Submarine.");
		assertFalse(ocean.isOccupied(0, 1), "Position (0, 1) should NOT be occupied by the Submarine.");
		
		//Test 3: place a horizontal Battleship and verify occupied positions
		Ship battleship = new Battleship();
		row = 9;
		column = 8;
		horizontal = true;
		battleship.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.isOccupied(9, 8), "Position (9, 8) should be occupied by the Battleship.");
		assertTrue(ocean.isOccupied(9, 5), "Position (9, 5) should be occupied by the Battleship.");
		assertFalse(ocean.isOccupied(9, 9), "Position (9, 9) should NOT be occupied by the Battleship.");
		assertFalse(ocean.isOccupied(8, 9), "Position (8, 9) should NOT be occupied by the Battleship.");
		
		assertFalse(ocean.isOccupied(5, 5), "Position (5, 5) is not near any ship and should NOT be occupied.");
		
	}

	@Test
	void testShootAt() {
	
		//Test 1: shoot at an empty location in the ocean
		assertFalse(ocean.shootAt(0, 1), "Shooting at an empty ocean should return false.");
		
		//Test 2: place a Destroyer and shoot at it until it sinks
		Destroyer destroyer = new Destroyer();
		int row = 1;
		int column = 5;
		boolean horizontal = false;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.shootAt(1, 5), "(1, 5) is a hit on the Destroyer and should return true.");
		assertFalse(destroyer.isSunk(), "A partially hit Destroyer should not be sunk.");
		
		assertTrue(ocean.shootAt(0, 5), "(0, 5) is a hit on the Destroyer and should return true.");
		assertTrue(destroyer.isSunk(), "A completely hit Destroyer should be sunk.");

		assertFalse(ocean.shootAt(1, 5), "Shooting again at a sunken Destroyer should return false.");
		
		//Test 3: place a horizontal Cruiser and shoot at it until it sinks
		Cruiser cruiser = new Cruiser();
		row = 9;
		column = 9;
		horizontal = true;
		cruiser.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.shootAt(9, 9), "(9, 9) is a hit on the Cruiser and should return true.");
		assertTrue(ocean.shootAt(9, 8), "(9, 8) is a hit on the Cruiser and should return true.");
		assertFalse(cruiser.isSunk(), "A partially hit Cruiser should not be sunk.");
		
		assertTrue(ocean.shootAt(9, 7), "(9, 7) is a hit on the Cruiser and should return true.");
		assertTrue(cruiser.isSunk(), "A completely hit Cruiser should be sunk.");

		assertFalse(ocean.shootAt(8, 9), "(8, 9) is adjacent but not occupied by the Cruiser. Should return false.");
		
	}

	@Test
	void testGetShotsFired() {
		
		//Test 1: shoot at empty locations
		//should be all false - no ships added yet
		assertFalse(ocean.shootAt(0, 1));
		assertFalse(ocean.shootAt(1, 0));
		assertFalse(ocean.shootAt(3, 3));
		assertFalse(ocean.shootAt(9, 9));
		assertEquals(4, ocean.getShotsFired(), "The total number of shots fired so far is 4.");
		
		//Test 2: place a Destroyer and a Submarine then shoot at their positions
		Destroyer destroyer = new Destroyer();
		int row = 1;
		int column = 5;
		boolean horizontal = false;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		
		Ship submarine = new Submarine();
		row = 0;
		column = 0;
		horizontal = false;
		submarine.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.shootAt(1, 5), "Shooting at (1, 5) should be a hit on the Destroyer.");
		assertFalse(destroyer.isSunk(), "The Destroyer should not be sunk after one hit.");
		assertTrue(ocean.shootAt(0, 5), "Shooting at (0, 5) should be a hit on the Destroyer.");
		assertTrue(destroyer.isSunk(), "The Destroyer should be sunk after both its positions got hit.");
		assertEquals(6, ocean.getShotsFired(), "The total number of shots fired so far is 6.");
		
		//Test 3: sink the Submarine then shoot at the sunken ships
    //number of shots fired should still be going up
		assertTrue(ocean.shootAt(0, 0), "Shooting at (0, 0) should be a hit on the Submarine.");
		assertTrue(submarine.isSunk(), "The Submarine should be sunk after it's hit.");
		
		assertFalse(ocean.shootAt(1, 5), "Shooting at an already sunk Destroyer should return false.");
		assertFalse(ocean.shootAt(0, 5), "Shooting at an already sunk Destroyer should return false.");
		assertFalse(ocean.shootAt(0, 0), "Shooting at an already sunk Submarine should return false.");
		assertEquals(10, ocean.getShotsFired(), "The total number of shots fired so far is 10.");
		
	}

	@Test
	void testGetHitCount() {
		
		//Test 1: places a Destroyer and hits it once
		Destroyer destroyer = new Destroyer();
		int row = 1;
		int column = 5;
		boolean horizontal = false;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.shootAt(1, 5), "Shooting at (1, 5) should be a hit on the Destroyer.");
		assertFalse(destroyer.isSunk(), "The Destroyer should not be sunk after one hit.");
		assertEquals(1, ocean.getHitCount(), "The total number of hits so far is 1.");
		
		assertFalse(ocean.shootAt(1, 4), "Shooting at (1, 4) doesn't hit anything.");
		assertEquals(1, ocean.getHitCount(), "The total number of hits should not change with a missed shot.");
		
		//Test 2: places a Submarine and hits it once, sinking it
		Ship sub = new Submarine();
		row = 3;
		column = 3;
		horizontal = true;
		sub.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.shootAt(3, 3), "Shooting at (3, 3) should be a hit on the Submarine.");
		assertTrue(sub.isSunk(), "The Submarine should be sunk after it's hit.");
		assertEquals(2, ocean.getHitCount(), "The total number of hits so far is 2.");
		
		//Test 3: sinks the Destroyer then shoots at the sunken ships
		assertTrue(ocean.shootAt(0, 5), "Shooting at (0, 5) should be a hit on the Destroyer.");
		assertTrue(destroyer.isSunk(), "The Destroyer should be sunk after both parts are hit.");
		
		assertFalse(ocean.shootAt(0, 5), "Shooting at (0, 5) again should return false.");
		assertEquals(4, ocean.getHitCount(), "The total number of hits so far is 4, despite hitting a sunken ship.");
		
		assertFalse(ocean.shootAt(1, 5), "Shooting at (1, 5) again should return false.");
		assertFalse(ocean.shootAt(3, 3), "Shooting at (3, 3) again should return false.");
		assertEquals(6, ocean.getHitCount(), "The total number of hits so far is 6, despite hitting two sunken ships.");
		
	}
	
	@Test
	void testGetShipsSunk() {
		
		//Test 1: places a Destroyer and hits it once
		Destroyer destroyer = new Destroyer();
		int row = 1;
		int column = 5;
		boolean horizontal = false;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.shootAt(1, 5), "Shooting at (1, 5) should be a hit on the Destroyer.");
		assertFalse(destroyer.isSunk(), "The Destroyer should not be sunk after one hit.");
		assertEquals(1, ocean.getHitCount(), "The total number of hits so far is 1.");
		assertEquals(0, ocean.getShipsSunk(), "There are no sunken ships.");
		
		//Test 2: places a Submarine and hits it once, sinking it
		Ship sub = new Submarine();
		row = 3;
		column = 3;
		horizontal = true;
		sub.placeShipAt(row, column, horizontal, ocean);
				
		assertTrue(ocean.shootAt(3, 3), "Shooting at (3, 3) should be a hit on the Submarine.");
		assertTrue(sub.isSunk(), "The Submarine should be sunk after it's hit.");
		assertEquals(2, ocean.getHitCount(), "The total number of hits so far is 2.");
		assertEquals(1, ocean.getShipsSunk(), "The total number of ships sunk so far is 1.");
		
		//Test 3: sinks the Destroyer then shoots at the sunken ships
		assertTrue(ocean.shootAt(0, 5), "Shooting at (0, 5) should be a hit on the Destroyer.");
		assertTrue(destroyer.isSunk(), "The Destroyer should be sunk after both parts are hit.");
		assertEquals(2, ocean.getShipsSunk(), "The total number of ships sunk so far is 2.");
				
		assertFalse(ocean.shootAt(0, 5), "Shooting at (0, 5) again should return false.");
		assertEquals(4, ocean.getHitCount(), "The total number of hits so far is 4, despite hitting a sunken ship.");
				
		assertFalse(ocean.shootAt(1, 5), "Shooting at (1, 5) again should return false.");
		assertFalse(ocean.shootAt(3, 3), "Shooting at (3, 3) again should return false.");
		assertEquals(6, ocean.getHitCount(), "The total number of hits so far is 6, despite hitting two sunken ships.");
		assertEquals(2, ocean.getShipsSunk(), "The total number of ships sunk should still be 2.");
		
	}

	@Test
	void testGetShipArray() {
		
		//Test 1: verify initial empty ocean array dimensions
		Ship[][] shipArray = ocean.getShipArray();
		assertEquals(OCEAN_SIZE, shipArray.length);
		assertEquals(OCEAN_SIZE, shipArray[0].length);
		
		assertEquals("empty", shipArray[0][0].getShipType(), "All initial positions should be EmptySea.");
		
		//Test 2: place a Destroyer and verify its position
		Destroyer destroyer = new Destroyer();
		int row = 1;
		int column = 5;
		boolean horizontal = false;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		
		assertEquals("destroyer", shipArray[1][5].getShipType(), "Position (1, 5) should now contain a Destroyer.");
		assertEquals("destroyer", shipArray[0][5].getShipType(), "Position (0, 5) should now contain a Destroyer.");
		assertEquals("empty", shipArray[2][5].getShipType(), "Position (2, 5) should still be EmptySea.");
		
		//Test 3: place a Submarine, sink it, then verify its position
		Ship sub = new Submarine();
		row = 3;
		column = 3;
		horizontal = true;
		sub.placeShipAt(row, column, horizontal, ocean);
		
		assertEquals("submarine", shipArray[3][3].getShipType(), "Position (3, 3) should now contain a Submarine.");
		assertEquals("empty", shipArray[3][2].getShipType(), "Position (3, 2) should still be EmptySea.");
		assertEquals("empty", shipArray[2][3].getShipType(), "Position (2, 3) should still be EmptySea.");
		
		assertTrue(ocean.shootAt(3, 3), "Shooting at (3, 3) should be a hit on the Submarine.");
		assertTrue(sub.isSunk(), "The Submarine should be sunk after it's hit.");
		assertEquals("submarine", shipArray[3][3].getShipType(), "Position (3, 3) should still contain a Submarine even if it sunk.");
		
	}

}
package battleship;

import java.util.Random;

/**
 * Represents an "ocean" of Ships on a 10x10 array
 */
public class Ocean {

	//instance variables
	
	/**
	 * Initializes a 10x10 ocean array of Ship objects
	 */
	private Ship[][] ships = new Ship[10][10];
	
	/**
	 * Total number of shots fired by user
	 */
	private int shotsFired;
	
	/**
	 * Total number of times a shot hit a ship
	 * Additional hits on the same part are still counted
	 */	
	private int hitCount;
	
	/**
	 * Total number of ships sunk
	 */
	private int shipsSunk;
	
	/**
	 * Array to track shot status for each cell
	 */
	private boolean[][] shotsFiredGrid = new boolean[10][10];
	

  //constructor
	
	/**
	 * Creates an "empty" ocean and fills the ships array with EmptySea objects
	 * Initializes game variables such as shots fired, hit count, and ships sunk
	 */
	public Ocean() {
		
		initializeOcean();
		
		//initializes game variables
		shotsFired = 0;
		hitCount = 0;
		shipsSunk = 0;
	}

  /**
	 * Helper method to fill the ocean grid with EmptySea objects
	 */
	private void initializeOcean() {
		
		//iterates through each cell in the ocean grid
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
								
				//creates new EmptySea object
				EmptySea emptySea = new EmptySea();
				
				//sets bow row and column to the current row and column
				emptySea.setBowRow(i);
				emptySea.setBowColumn(j);
				
				//places the EmptySea in the grid
				ships[i][j] = emptySea;
				
				//initializes each cell as not shot at
				shotsFiredGrid[i][j] = false;
				
			}
		}
	}

  /**
	 * Helper method to get the shotsFired grid
	 * @return grid of shots fired
	 */	
	public boolean[][] getShotsFiredGrid() {
		return shotsFiredGrid;
	}


  //methods

  /**
	 * Places all 10 ships randomly on the ocean starting with the largest
	 */
	void placeAllShipsRandomly() {
		
		//places all the ships randomly
		Random random = new Random();
		
		//creates and places each type of ship starting with the largest
		Ship[] fleet = {
				new Battleship(),
				new Cruiser(), new Cruiser(),
				new Destroyer(), new Destroyer(), new Destroyer(),
				new Submarine(), new Submarine(), new Submarine(), new Submarine()
		};
		
		//iterates through each ship in the fleet and places each on the ocean board
		for (Ship ship : fleet) {
			
			//initializes boolean variable indicating whether the ship is already placed
			boolean placed = false;
			
			//iterates through each ship that hasn't been placed yet
			while (!placed) {
				int row = random.nextInt(10); //chooses a random row number 0-9
				int column = random.nextInt(10); //chooses a random column number 0-9
				boolean horizontal = random.nextBoolean(); //chooses a random horizontal or vertical placement
						
				//checks if it's legal to place the ship at the chosen position
				if (ship.okToPlaceShipAt(row, column, horizontal, this)) {
					ship.placeShipAt(row, column, horizontal, this);
					placed = true; //indicates that ship is successfully placed and moves on to the next one
				}
			}
		}	
	}

  /**
	 * Returns whether the given cell contains a ship
	 * @param row of cell
	 * @param column of cell
	 * @return true if location contains a ship, false if not
	 */
	boolean isOccupied(int row, int column) {
		
		//gets the ship at the given location in the ocean grid
		Ship shipLocation = ships[row][column];
		
		//checks if the location has anything other than EmptySea
		if (shipLocation instanceof EmptySea) {
			return false; //location is not occupied by a ship
		} else {
			return true; //location is occupied by a ship
		}
				
	}

  /**
	 * Returns true if given cell contains a ship (excluding EmptySea)
	 * @param row of cell
	 * @param column of cell
	 * @return true if cell contains a ship, false if EmptySea or ship has been sunk
	 */
	boolean shootAt(int row, int column) {
		
		//increments shotsFired each time player takes a shot
		shotsFired++;
		
		//mark the cell as shot at
		shotsFiredGrid[row][column] = true;
		
		//gets the ship at the given cell in the ocean grid
		Ship targetShip = ships[row][column];
		
    //increments hitCount if shot hits any part of a ship, including sunken ones
		if (!(targetShip instanceof EmptySea)) {
			hitCount++;
		}

		//checks the location being shot has a ship
		if (targetShip.shootAt(row, column)) {
			
			//increments shipsSunk count if ship is sunk
			if (targetShip.isSunk()) {
				shipsSunk++;

        //prints a message indicating which type of ship was sunk
				System.out.println("You just sank a " + targetShip.getShipType() + ".");
			}
									
			//returns true if shot hits the ship
			return true;
		}
		
		//returns false if cell contains EmptySea or a sunken ship
		return false;
			
	}

  /**
	 * Gets the total number of shots fired in the game
	 * @return number of shots fired
	 */
	int getShotsFired() {
		return shotsFired;
	}
	
	/**
	 * Gets the total number of hits recorded in the game
	 * All duplicate hits are counted
	 * @return number of hits recorded
	 */
	int getHitCount() {
		return hitCount;
	}
	
	/**
	 * Gets the total number of ships sunk in the game
	 * @return number of ships sunk
	 */
	int getShipsSunk() {
		return shipsSunk;
	}
	
	/**
	 * Returns true if all 10 ships have been sunk, indicating the end of the game
	 * @return boolean indicating whether all ships have been sunk
	 */
	boolean isGameOver() {
		return shipsSunk == 10;
	}
	
	/**
	 * Allows other classes such as Ship to view or modify the contents of the ocean
	 * @return the 10x10 array of ships
	 */
	Ship[][] getShipArray(){
		return ships;
	}

  /**
	 * Prints the Ocean in a 10x10 grid with characters indicating what happened in each cell
	 * "x" indicates a cell user fired upon and hit a ship
	 * "-" indicates a cell user fired upon and hit nothing
	 * "s" indicates a cell containing a sunken ship
	 * "." indicates a cell user has never fired upon
	 */
	void print() {
		
		//prints each column with column numbers at the top of each column (0 to 9)
		System.out.print(" ");
		for (int col = 0; col < 10; col++) {
			System.out.print(col + " ");
		}
		
		System.out.println(); //blank line
		
		//prints each row with row numbers at the start of each row (0 to 9)
		for (int row = 0; row < 10; row++) {
			System.out.print(row + " ");
			
			//iterates through each column in the current row
			for (int col = 0; col < 10; col++) {
				Ship ship = ships[row][col];
				
				//checks if this cell has been shot at
				if (shotsFiredGrid[row][col]) {
					if (ship.isSunk()) {
						System.out.print("s "); //prints "s" for sunken ship
					} else if (!ship.getShipType().equals("empty")) {
						System.out.print("x "); //prints "x" for a hit on a real ship
					} else {
						System.out.print("- "); //prints "-" for a missed shot on EmptySea
					}					
				} else {
					//prints "." for cells that haven't been hit or shot at
					System.out.print(". ");
				}
			}
			
			//prints blank line and moves to the next row
			System.out.println();
		}
				
	}

  /**
	 * Reveals locations of all ships for debugging purposes
	 * "b" indicates a battleship
	 * "c" indicates a cruiser
	 * "d" indicates a destroyer
	 * "s" indicates a submarine
	 * " " (single space) indicates an EmptySea
	 */
	void printWithShips() {
		
		//prints each column with column numbers at the top of each column (0 to 9)
		System.out.print(" ");
		for (int col = 0; col < 10; col++) {
			System.out.print(col + " ");
		}
		
		System.out.println(); //blank line
		
		//prints each row with row numbers at the start of each row (0 to 9)
		for (int row = 0; row < 10; row++) {
			System.out.print(row + " ");
					
			//iterates through each column in the current row
			for (int col = 0; col < 10; col++) {
				Ship ship = ships[row][col];
				
				//determines letter based on type of ship
				if (ship.getShipType().equals("battleship")) {
					System.out.print("b "); //prints "b" for Battleship
				} else if (ship.getShipType().equals("cruiser")) {
					System.out.print("c "); //prints "c" for Cruiser
				} else if (ship.getShipType().equals("destroyer")) {
					System.out.print("d "); //prints "d" for Destroyer
				} else if (ship.getShipType().equals("submarine")) {
					System.out.print("s "); //prints "s" for Submarine
				} else if (ship.getShipType().equals("empty")) {
					System.out.print("  "); //prints a single space for EmptySea
				}
			}
			
			//prints blank line and moves to the next row
			System.out.println();
		}
		
	}
		
}


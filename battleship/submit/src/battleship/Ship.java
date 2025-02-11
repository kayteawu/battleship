package battleship;

/**
 * Represents characteristics common to all Ships in an abstract class
 */
public abstract class Ship {
	
	//instance variables
	
	/**
	 * Row that contains the ship's bow (frontmost part)
	 */
	private int bowRow;
	
	/**
	 * Column that contains the ship's bow (frontmost part)
	 */
	private int bowColumn;
	
	/**
	 * Length of ship
	 */
	private int length;
	
	/**
	 * True if ship is horizontal, false if not
	 */
	private boolean horizontal;
	
	/**
	 * Array that tells us whether each part of the ship was hit or not
	 */
	private boolean[] hit;


  //constructor
	
	/**
	 * Creates a ship with given length
	 * Initializes hit array based on ship's length
	 * @param length of ship
	 */	
	public Ship(int length) {
		this.length = length;
		this.hit = new boolean[length];
	}


  //methods
	//getters
	
	/**
	 * Gets the ship's length
	 * @return length of ship
	 */
	public int getLength() {
	return length;
	}
		
	/**
	 * Gets the bow's row number
	 * @return row of bow
	 */
	public int getBowRow() {
		return bowRow;
	}
		
	/**
	 * Gets the bow's column number
	 * @return column of bow
	 */
	public int getBowColumn() {
		return bowColumn;
	}
			
	/**
	 * Gets the array of booleans that indicate whether each part of the ship has been hit
	 * @return array of booleans
	 */
	public boolean[] getHit() {
		return hit;
	}
	
	/**
	 * Gets boolean value for whether the ship is horizontal or not
	 * @return true for horizontal, false for vertical
	 */
	public boolean isHorizontal() {
		return horizontal;
	}

  //setters
	
	/**
	 * Sets the value of the bow's row
	 * @param bowRow of ship
	 */
	public void setBowRow(int bowRow) {
		this.bowRow = bowRow;
	}
	
	/**
	 * Sets the value of the bow's column
	 * @param bowColumn of ship
	 */
	public void setBowColumn(int bowColumn) {
		this.bowColumn = bowColumn;
	}
		
	/**
	 * Sets the value of horizontal
	 * @param horizontal orientation of ship
	 */
	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}


  //abstract methods
	
	/**
	 * Gets the type of ship as a string
	 * @return type of ship
	 */
	public abstract String getShipType();


  //other methods
	
	/**
	 * Tells us whether a placement for the ship is valid or not
	 * @param row of ship's bow
	 * @param column of ship's bow
	 * @param horizontal orientation
	 * @param ocean cell(s) to place ship in
	 * @return true if okay to place ship, false if not
	 */
	boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean) {
		
		//sets the ocean to a 10x10 grid
		int oceanSize = 10;
		
		//checks if the ship would "stick out" of the ocean boundaries
		if (horizontal) {
			if (column - getLength() + 1 < 0) return false; //ship would go out of bounds horizontally
		} else {
			if (row - getLength() + 1 < 0) return false; //ship would go out of bounds vertically
		}
		
		//iterates through each cell where the ship would be placed, including its surrounding cells
		for (int i = 0; i < length; i++) {
			
			//initializes the current row and column where the ship would be placed
			int currentRow;
			int currentColumn;
			
			//if ship is horizontal, row stays the same and column increments to the left by 1
			if (horizontal) {
				currentRow = row;
				currentColumn = column - i;
			
			//if ship is vertical, column stays the same and row increments upwards by 1
			} else {
				currentColumn = column;
				currentRow = row - i;			
			}
			
			//checks the surrounding area of each ship part for existing ships
			for (int r = currentRow - 1; r <= currentRow + 1; r++) {
				for (int c = currentColumn - 1; c <= currentColumn + 1; c++) {
					
					//ensures we're checking within ocean bounds
					if (r >= 0 && r < oceanSize && c >= 0 && c < oceanSize) {
						
						//checks if there's an existing ship in this position
						if (!ocean.getShipArray()[r][c].getShipType().equals("empty")){
							return false; //cannot place ship where there's an existing ship
						}
					}
				}
			}
			
		}
		
		//if all checks pass, return true as it's ok to place a ship there
		return true;	
		
	}

  /**
	 * Places the ship in the ocean
	 * @param row of ship's bow
	 * @param column of ship's bow
	 * @param horizontal orientation
	 * @param ocean to place ship in
	 */
	void placeShipAt(int row, int column, boolean horizontal, Ocean ocean) {
		
		//sets the ship's bow position
		setBowRow(row);
		setBowColumn(column);
		setHorizontal(horizontal);
		
		//iterates through each segment of the ship based on the ship's length
		for (int i = 0; i < getLength(); i++) {
			if (horizontal) {
				
				//places each segment of the horizontal ship to the left of the bow
				ocean.getShipArray()[getBowRow()][getBowColumn() - i] = this;
			} else {
				
				//places each segment of the vertical ship to the cells above the bow
				ocean.getShipArray()[getBowRow() - i][getBowColumn()] = this;
			}
		}
	}

  /**
	 * Marks the cell of the ocean grid as "hit" or not
	 * @param row to hit
	 * @param column to hit
	 * @return true if hit, false if not
	 */
	boolean shootAt(int row, int column) {
		
		//checks if there's a sunken ship
		if (isSunk()) {
			return false; //can't be a hit if ship is already sunk
		}
				
		//calculates the index in the hit array corresponding to the target location
		int index;
		if (isHorizontal()) {
			
			//checks that the row matches the bow row for a horizontal ship
			if(row != bowRow) {
				return false;
			}
			
			//if horizontal, index is based on the column distance from the bow
			index = getBowColumn() - column;
			
		} else {
			
			//checks that the column matches the bow column for a vertical ship
			if(column != bowColumn) {
				return false;
			}
			
			//if vertical, index is based on the row distance from the bow
			index = getBowRow() - row;
			
		}
		
		//checks if the index is within the valid range of the ship's length
		if (index >= 0 && index < getLength()) {
							
			//marks the part as hit
			getHit()[index] = true;
			return true;
		}
		
		//if no part of the ship occupies the given row and column, does not mark a hit and returns false
		return false;
	
	}

  /**
	 * Tells us whether the ship is sunk by checking if all parts are hit
	 * @return true if ship is sunk, false if not
	 */
	boolean isSunk() {
		
		//iterates through the hit array
		for (boolean partHit : getHit()) {
			
			//if any part is NOT hit, the ship is not sunk
			if (!partHit) {
				return false;
			}
		}
		
		//if all parts of the ship are hit, the ship is sunk
		return true;
		
	}

  /**
	 * Prints out locations in the ocean that have been shot at
	 * Indicates whether or not the ship has been sunk
	 * Returns a single character String
	 */
	@Override
	public String toString() {
		
		//checks if the ship is sunk
		if (isSunk()) {
			return "s"; //returns "s" if ship is completely sunk
		} else {
			return "x"; //returns "x" if ship is hit but not fully sunk
		}
	}
	
}
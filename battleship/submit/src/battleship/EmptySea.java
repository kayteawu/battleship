package battleship;

/**
 * Represents a part of the ocean that doesn't contain a Ship
 */
public class EmptySea extends Ship {

	//constructor
	
	/**
	 * Calls the superclass Ship constructor with length 1
	 */
	public EmptySea() {
		super(1);		
	}

	
	//methods
	
	/**
	 * Always returns false to indicate that nothing was hit
	 * Inherited from Ship
	 */
	@Override
	boolean shootAt(int row, int column) {
		return false;
	}
	
	/**
	 * Always returns false to indicate nothing was sunk
	 * Inherited from Ship
	 */
	@Override
	boolean isSunk() {
		return false;
	}
	
	/**
	 * Always returns the single char "-" to indicate a shot was fired but nothing was hit
	 */
	@Override
	public String toString() {
		return "-";
	}
	
	/**
	 * Returns the string "empty" since there is no ship in EmptySea
	 */
	@Override
	public String getShipType() {
		return "empty";
	}
		
}
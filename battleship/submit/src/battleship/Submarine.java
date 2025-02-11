package battleship;

/**
 * Represents a Ship with a length of 1
 */
public class Submarine extends Ship {

	//static final variables
	
	/**
	 * Type of ship
	 */
	private static final String TYPE = "submarine";
	
	/**
	 * Length of ship
	 */
	private static final int LENGTH = 1;
	
	
	//constructor
	
	/**
	 * Creates a Submarine with a fixed length of 1
	 */
	public Submarine() {
		
		//calls the superclass constructor with the submarine's length
		super(LENGTH);
		
	}

	/**
	 * Returns the type of ship
	 * Overrides method from Ship
	 */
	@Override
	public String getShipType() {
		return TYPE;
	}
		
}
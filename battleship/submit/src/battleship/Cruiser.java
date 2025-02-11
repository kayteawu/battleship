package battleship;

/**
 * Represents a Ship with a length of 3
 */
public class Cruiser extends Ship {

	//static final variables
	
	/**
	 * Type of ship
	 */
	private static final String TYPE = "cruiser";
	
	/**
	 * Length of ship
	 */
	private static final int LENGTH = 3;
	
	
	//constructor
	
	/**
	 * Creates a Cruiser with a fixed length of 3
	 */
	public Cruiser() {
		
		//calls the superclass constructor with the cruiser's length
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
package battleship;

/**
 * Represents a Ship with a length of 2
 */
public class Destroyer extends Ship {

	//static final variables
	
	/**
	 * Type of ship
	 */
	private static final String TYPE = "destroyer";
	
	/**
	 * Length of ship
	 */
	private static final int LENGTH = 2;
	
	
	//constructor
	
	/**
	 * Creates a Destroyer with a fixed length of 2
	 */
	public Destroyer() {
		
		//calls the superclass constructor with the destroyer's length
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
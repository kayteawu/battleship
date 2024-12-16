package battleship;

/**
 * Represents a Ship with a length of 4
 */
public class Battleship extends Ship {

	//static final variables
	
	/**
	 * Type of ship
	 */
	private static final String TYPE = "battleship";
	
	/**
	 * Length of ship
	 */
	private static final int LENGTH = 4;
	
	
	//constructor
	
	/**
	 * Creates a Battleship with a fixed length of 4
	 */
	public Battleship() {
		
		//calls the superclass constructor with the battleship's length
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
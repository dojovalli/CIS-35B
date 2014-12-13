
public class CartesianCoordinate {

	// CONSTANTS
	private static final boolean DEBUG = false;
	
	// MEMBERS
	private double mX;
	private double mY;
	
	/** Creates a CartesianCoordinate
	 * 
	 */
	public CartesianCoordinate() {
		mX = 0;
		mY = 0;
	}
	
	public CartesianCoordinate(double x, double y) {
		mX = x;
		mY = y;
	}
	
	public CartesianCoordinate(WindowCoordinate coord, double positiveRangeY) {
		windowToCartesian(coord, positiveRangeY);
	}
	
	
	// CONVENIENCE METHODS
	public void windowToCartesian(WindowCoordinate coord, double positiveRangeY) {
		// The X-Axis remains the same
		mX = coord.getX();
		
		double tempY = 0;
		// Invert the Y value and subtract the positiveRange of Y
		tempY = coord.getY() * -1;
		tempY -= positiveRangeY;
		
		mY = tempY;
	}
	
	public WindowCoordinate toWindowCoordinate(	double rangeMinX,
												double rangeMaxY,
												double scaleX,
												double scaleY,
												double verticalPadding, 
												double horizontalPadding ) {
		if (DEBUG) {
			System.out.println("Enter toWindowCoordinate()");
			System.out.println("INITIAL VARIABLES");
			System.out.println("rangeMinX: " + rangeMinX + "\n"
								+ "rangeMaxY: " + rangeMaxY + "\n"
								+ "scaleX: " + scaleX + "\n"
								+ "scaleY: " + scaleY + "\n"
								+ "verticalPadding: " + verticalPadding + "\n"
								+ "horizontalPadding: " + horizontalPadding + "\n");
				
		}
		// Create a WindowCoordinate
		WindowCoordinate coord = new WindowCoordinate();
		double tempY = 0;
		
		// The X-Axis remains the same
		double tempX = mX;
		tempX -= rangeMinX;
		// Handle Scaling
		tempX = tempX * scaleX;
		// Add Padding
		tempX += horizontalPadding;
		coord.setX(tempX);
		 
		// All Y-Axis values are inverted
		tempY = mY * -1;
		
		
		// Add the rangeMaxY to shift the values appropriately
		tempY += rangeMaxY;
		// Handle Scaling
		tempY = tempY * scaleY;
		// Add Padding
		tempY += verticalPadding;
		
		// Set the coord Y value
		coord.setY(tempY);
		
		return coord;
	}
	
	// OVERRIDES
	@Override
	public String toString() {
		String str = "CartesianCoordinate - X; " + mX + "\tY: " + mY;
		return str;
	}
	
	// GETTERS & SETTERS
	public double getX() {
		return mX;
	}

	public void setX(double x) {
		mX = x;
	}

	public double getY() {
		return mY;
	}

	public void setY(double y) {
		mY = y;
	}

}

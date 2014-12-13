
public class WindowCoordinate {


	// CONSTANTS
	private static final boolean DEBUG = true;
	
	// FLAGS
	public static final int INVALID = -1;
	
	// MEMBERS
	private double mX;
	private double mY;
	private double mPositiveRange;
	
	/** Creates a CartesianCoordinate
	 * 
	 */
	public WindowCoordinate() {
		mX = 0;
		mY = 0;
		mPositiveRange = 1;
	}
	
	public WindowCoordinate(double x, double y) {
		this(x,y,y);
	}
	
	public WindowCoordinate(double x, double y, double positiveRangeY) {
		mX = x;
		mY = y;
		
		// positiveRange cannot be negative
		if (positiveRangeY < 0) {
			positiveRangeY = y * -1;
		}
		else {
			mPositiveRange = positiveRangeY;
		}
	}
	
	public WindowCoordinate(CartesianCoordinate coord, double positiveRangeY){
		cartesianToWindow(coord, positiveRangeY);
	}
	
	// CONVENIENCE METHODS
	public void cartesianToWindow(CartesianCoordinate coord, double positiveRangeY) {
		// The X-Axis remains the same
		mX = coord.getX();
		
		double tempY = 0;
		// Invert the Y value and subtract the positiveRange of Y
		tempY = coord.getY() * -1;
		tempY -= positiveRangeY;
		
		mY = tempY;
	}
	
	public CartesianCoordinate toCartesianCoordinate(double positiveRangeY) {
		// Create a WindowCoordinate
		CartesianCoordinate coord = new CartesianCoordinate();
		double tempY = 0;
		
		// The X-Axis remains the same
		coord.setX(mX);
		 
		// All Y-Axis values are inverted
		tempY = mY * -1;
		
		// Add the positiveRangeY to shift the values appropriately
		tempY += positiveRangeY;
		
		// Set the coord Y value
		coord.setY(tempY);
		
		return coord;
	}
	
	// OVERRIDES
	@Override
	public String toString() {
		String str = "WindowCoordinate - X: " + mX + "\tY: " + mY;
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
	
	public double getPositiveRange() {
		return mPositiveRange;
	}

	public void setPositiveRange(double positiveRange) {
		mPositiveRange = positiveRange;
	}

	public double getGraphableX(double bufferX) {
		return addBuffer(mX, bufferX);
	}
	public double getGraphableY(double bufferY) {
		return addBuffer(mY, bufferY);
	}
	private double addBuffer(double coordAxisValue, double buffer) {
		if (buffer < 0) { return coordAxisValue + (buffer * -1); }
		else { return coordAxisValue + buffer; }
	}
	

}

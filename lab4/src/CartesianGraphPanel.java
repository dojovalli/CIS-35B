import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;


public class CartesianGraphPanel extends JPanel {

	// CONSTANTS
	private static final boolean DEBUG = false;
	private static final boolean DEBUG_SAMPLE_CART_COORDS = false;
	private static final boolean DEBUG_CART_TO_WINDOW_COORD = true;
	private static final boolean DEBUG_MIN_COORD = true;
	private static final boolean DEBUG_MAX_COORD = true;
	
	// Define size of plotting panel
	private static int W = 600;
	private static int H = 400;
		  
	
	// MEMBERS
	double mScaleX;
	double mScaleY;
	double mVerticalPadding;
	double mHorizontalPadding;
	
	// Used for Scaling
	double mMaxX;
	double mMinX;
	double mMaxY;
	double mMinY;
	
	// Used for Plotting
	boolean mPlot;
	
	// Data Sets
	ArrayList<CartesianCoordinate> mCartesianCoords;
	ArrayList<WindowCoordinate> mWindowCoords;
	
	
	// CONSTRUCTORS
	public CartesianGraphPanel() {
		this(sampleCartesianCoords(-11, 100), 10, 10, true);
	}
	
	public CartesianGraphPanel(ArrayList<CartesianCoordinate> valueSet, double verticalPadding, double horizontalPadding, boolean usePlots) {
		
		// Set the Size of the panel
		setPreferredSize(new java.awt.Dimension(W + (int) (horizontalPadding * 2), H + (int) (verticalPadding * 2)));
		
		// Set Data Structure
		mCartesianCoords = valueSet;
		
		// Set Values
		mVerticalPadding = verticalPadding;
		mHorizontalPadding = horizontalPadding;
		mPlot = usePlots;
		
		// Setup the Graph Variables
		calculateGraphVars(mCartesianCoords);
	}
	
	
	// CONVENIENCE METHODS
	public void calculateGraphVars(ArrayList<CartesianCoordinate> valueSet) {
		// Calculate the maximums and minimums of the both the X and Y Coordinates
		getMaxiumCoord(valueSet);
		getMinimumCoord(valueSet);
		
		// horizontal scaling
		double totalRangeX = mMaxX - mMinX;
	    mScaleX = W / totalRangeX; //+ (horizontalPadding * 2));   
	 
	    // vertical scaling
	    double totalRangeY = mMaxY - mMinY;
	    mScaleY = H / totalRangeY; // + (verticalPadding * 2));
		
		// Convert the set to WindowCoordinates
		mWindowCoords = convertCartesianToWindow(valueSet);
	}
	
	public ArrayList<WindowCoordinate> convertCartesianToWindow(ArrayList<CartesianCoordinate> coords) {
		ArrayList<WindowCoordinate> conversionObject = new ArrayList<WindowCoordinate>();
		
		if (DEBUG && DEBUG_CART_TO_WINDOW_COORD) {
			System.out.println("Converting Cartesian Coordinates to Window Coordinates...");
		}
		// Convert the contents of the ArrayList<CartesianCoordinate> one-by-one
		Iterator coordIterator = coords.iterator();
		while (coordIterator.hasNext()) {
			// Get the next coord
			CartesianCoordinate coord = (CartesianCoordinate) coordIterator.next();
			// Convert it to a WindowCoord
			WindowCoordinate windowCoord = coord.toWindowCoordinate(mMinX, 
																	mMaxY,
																	mScaleX,
																	mScaleY,
																	mVerticalPadding, 
																	mHorizontalPadding);
			// Log
			if (DEBUG && DEBUG_CART_TO_WINDOW_COORD) {
				System.out.println(windowCoord.toString());
			}
			// Add it to the ArrayList<WindowCoordinate>
			conversionObject.add(windowCoord);
		}
		
		return conversionObject;
	}
	
	public void getMaxiumCoord(ArrayList<CartesianCoordinate> coords) {

		// Convert the contents of the ArrayList<CartesianCoordinate> one-by-one
		Iterator coordIterator = coords.iterator();
		// Initialize the mMaxX & mMaxY to the first Coordinate
		CartesianCoordinate initialCoord = (CartesianCoordinate) coordIterator.next();
		mMaxX = initialCoord.getX();
		mMaxY = initialCoord.getY();
		
		while (coordIterator.hasNext()) {
			// Get the next coord
			CartesianCoordinate coord = (CartesianCoordinate) coordIterator.next();
			
			// If the coord value of X is greater than max, set it to max
			if(coord.getX() > mMaxX) mMaxX = coord.getX();
			// If the coord value of Y is greater than max, set it to max
			if(coord.getY() > mMaxY) mMaxY = coord.getY();
		}
		
		if (DEBUG && DEBUG_MAX_COORD) {
			System.out.println("MAX VALUES");
			System.out.println("mMaxX: " + mMaxX + "\n"
								+"mMaxY: " + mMaxY + "\n");
		}
	}
	
	
	public void getMinimumCoord(ArrayList<CartesianCoordinate> coords) {

		// Convert the contents of the ArrayList<CartesianCoordinate> one-by-one
		Iterator coordIterator = coords.iterator();
		// Initialize the mMaxX & mMaxY to the first Coordinate
		CartesianCoordinate initialCoord = (CartesianCoordinate) coordIterator.next();
		mMinX = initialCoord.getX();
		mMinY = initialCoord.getY();
		
		while (coordIterator.hasNext()) {
			// Get the next coord
			CartesianCoordinate coord = (CartesianCoordinate) coordIterator.next();
			
			// If the coord value of X is greater than max, set it to max
			if(coord.getX() < mMinX) mMinX = coord.getX();
			// If the coord value of Y is greater than max, set it to max
			if(coord.getY() < mMinY) mMinY = coord.getY();
		}
		if (DEBUG && DEBUG_MIN_COORD) {
			System.out.println("MIN VALUES");
			System.out.println("mMinX: " + mMinX + "\n"
								+"mMinY: " + mMinY + "\n");
		}
	}
	 

	// GETTER & SETTERS
	public void setCartesianValueSet(ArrayList<CartesianCoordinate> valueSet) {
		// Set the Reference
		mCartesianCoords = valueSet;
		
		// Update the Panel View
		calculateGraphVars(mCartesianCoords);
	}
	 
	 // VIEW METHODS
	  @Override public void paint(Graphics gr)
	  {
		  
		// Draw X & Y Axis lines
		CartesianCoordinate xAxisLeftPointC = new CartesianCoordinate(mMinX, 0);
		CartesianCoordinate xAxisRightPointC = new CartesianCoordinate(mMaxX, 0);
		CartesianCoordinate yAxisTopPointC = new CartesianCoordinate(0, mMinY);
		CartesianCoordinate yAxisBottomPointC = new CartesianCoordinate(0, mMaxY);
		
		WindowCoordinate xAxisLeftPointW = xAxisLeftPointC.toWindowCoordinate(	mMinX, 
																				mMaxY, 
																				mScaleX, 
																				mScaleY, 
																				mVerticalPadding, 
																				mHorizontalPadding);
		WindowCoordinate xAxisRightPointW = xAxisRightPointC.toWindowCoordinate(	mMinX, 
																					mMaxY, 
																					mScaleX, 
																					mScaleY, 
																					mVerticalPadding, 
																					mHorizontalPadding);
		WindowCoordinate yAxisTopPointW = yAxisTopPointC.toWindowCoordinate(	mMinX, 
																				mMaxY, 
																				mScaleX, 
																				mScaleY, 
																				mVerticalPadding, 
																				mHorizontalPadding);
		WindowCoordinate yAxisBottomPointW = yAxisBottomPointC.toWindowCoordinate(	mMinX, 
																					mMaxY, 
																					mScaleX, 
																					mScaleY, 
																					mVerticalPadding, 
																					mHorizontalPadding);
		
		// Actually draw the Axis Lines
		gr.drawLine(	(int) xAxisLeftPointW.getX(),
						(int) xAxisLeftPointW.getY(),
						(int) xAxisRightPointW.getX(),
						(int) xAxisRightPointW.getY()	);
		gr.drawLine(	(int) yAxisTopPointW.getX(),
						(int) yAxisTopPointW.getY(),
						(int) yAxisBottomPointW.getX(),
						(int) yAxisBottomPointW.getY()	);
		
	    
	    // Iterate through the ArrayList<CartesianCoordinate> and plot the points
	    Iterator coordIterator = mWindowCoords.iterator();
	    // Get the first coordinate
	    WindowCoordinate previousCoord = (WindowCoordinate) coordIterator.next();
	    WindowCoordinate currentCoord = null;
	    		
	    // While the Iterator hasNext, plot the points
	    while(coordIterator.hasNext()) {
	    	// Get the currentCoord
	    	currentCoord = (WindowCoordinate) coordIterator.next();
	    	
	    	// Place 'Plot' object
	    	if(mPlot) plot(gr, (int) currentCoord.getX(), (int) currentCoord.getY());
	    	
	    	// Change the color to Blue
	    	gr.setColor(new Color(00,00,255));
	    	
	    	// Draw the line
	    	gr.drawLine((int) previousCoord.getX(), 
	    				(int) previousCoord.getY(), 
	    				(int) currentCoord.getX(), 
	    				(int) currentCoord.getY());
	    	// Set the Previous Coord with the CurrentCoord
	    	previousCoord = currentCoord;
	    }
	  }
	 
	  public void plot(Graphics gr, int x, int y)
	  {
	    // draw a small rectangle to represent the data point
	    gr.setColor(java.awt.Color.RED);
	    int width = 10;
	    int height = 10;
	    int xUpperLeft = x-width/2;
	    int yUpperLeft = y-height/2;
	    gr.fillRect(xUpperLeft, yUpperLeft, width, height);
	  }
	  
	 
	  // Testing
	  public static ArrayList<CartesianCoordinate> sampleCartesianCoords(int min, int max) {
		  ArrayList<CartesianCoordinate> sampleCoords = new ArrayList<CartesianCoordinate>();
		  if(DEBUG && DEBUG_SAMPLE_CART_COORDS) System.out.println("Creating Sample Coordinates...");
		  for (int i = min; i < max; i++) {
			  CartesianCoordinate coord = new CartesianCoordinate(i, i);
			  sampleCoords.add(coord);
			  if(DEBUG && DEBUG_SAMPLE_CART_COORDS) System.out.println(coord.toString());
		  }
		  
		  return sampleCoords;
	  }
	  
	  /*
	  public static void main(String[] args) throws Exception
	  {
	    JFrame frame = new JFrame("Cartesianc Graph");
	    frame.setSize(W, H);
	    frame.add(new CartesianGraphPanel());
	    frame.pack();
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setVisible(true);
	  }
		*/
	
}



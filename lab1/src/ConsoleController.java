import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;

@SuppressWarnings("unused")
public class ConsoleController {

	// DEBUG
	private static final boolean DEBUG = true;
	private static final boolean DEBUG_STORE = false;
	private static final boolean DEBUG_PROMPT = false;
	
	// CONSTANTS
	private static final String MESSAGE_ORIGIN = "Enter Origin City & State (Ex. San Jose, CA): ";
	private static final String MESSAGE_DEST = "Enter Destination City & State (Ex. San Jose, CA): ";
	private static final String MESSAGE_INVALID_LOC = "The supplied location is invalid! Please try again.\n";
	
	// MEMBERS
	private String mRecentInput;
	private LocationStore mLocationStore;
	private Location mOrigin;
	private Location mDestination;
	
	// CONSTRUCTORS
	public ConsoleController() {
		mRecentInput = "No input recieved";
		mOrigin = null;
		mDestination = null;
	}
	
	
	// LIFE-CYCLE METHODS
	public void onCreate() {
		
		setupSystemLocations();
		promptUserForLocations();
		calculateDistance();
		
	}
	
	

	
	// CONVENIENCE METHODS
	private void setupSystemLocations() {
		// Get the Locations available in the System
		mLocationStore = LocationStore.get();
		mLocationStore.setLocations("Coordinates.xml");
		
		// Sort list of Locations
		mLocationStore.sort();
		
		if (DEBUG && DEBUG_STORE) System.out.println("SORTED LOCATIONS:");
		if (DEBUG && DEBUG_STORE) System.out.println(mLocationStore.getLocations().toString());
	}
	
	private void promptUserForLocations() {
		
		// While the Origin Location is not Valid in the System, prompt user for Origin
		while (	true ) {
			
			if (mOrigin != null && mDestination != null) {
				
				if (DEBUG) System.out.println("Validating Location...");
				if ( mLocationStore.contains(mOrigin) && mLocationStore.contains(mDestination) ) {
					System.out.println("Locations Valid");
					break;
				} else {
					System.out.println("The System does not contain this location!" + "\n"
										+ "Please try a new location or type \"Exit\" to quit the program.");
				}
			}
			
			// While the Origin is not a Valid Location
			mOrigin = promptLocation(MESSAGE_ORIGIN);
			mDestination = promptLocation(MESSAGE_DEST);
			
			if (DEBUG && DEBUG_PROMPT) {
				System.out.println(mOrigin.toString());
				System.out.println(mDestination.toString());
			}
			
		}
		
	}
	
	private void calculateDistance() {
		// Create a DistanceCalculator
		DistanceCalculator calculator = new DistanceCalculator(mOrigin, mDestination);
		
		// Calculate & Display the Distance
		System.out.printf("Distance: %.2f km", calculator.getDistance());
	}
	
	public Location promptLocation(String promptMessage) {
		// Greater Looper Vars
		Location loc = null;
		boolean isValidLocation = false;
		boolean locationExists = false;
		boolean passedLoop = false;
		
		while (true) {
			// If the location is valid, exit the loop
			if ( isValidLocation && locationExists ) break;
			
			// If we have been through the loop more than once, display try again message
			if (passedLoop) {
				System.out.println(MESSAGE_INVALID_LOC);
			}
			// PROMPT FOR ORIGIN
			promptUser(promptMessage);
			String locationResponse = getInput();
			
			// Handle User Input
			loc = new Location(locationResponse);
			if (DEBUG && DEBUG_PROMPT) System.out.println("location: " + loc.toString());
			
			// If the location is valid, check to see if it exists as an XML Node
			// Check to see if the Location Format is valid
			isValidLocation = loc.isValid();
			if (isValidLocation) {
				// Check to see if the Location exists within the SystemLocations
				locationExists = mLocationStore.getLocations().contains(loc);
				if (locationExists) {
					loc = mLocationStore.get(loc);
				} else {
					
				}
			}
			
			
			passedLoop = true;
		}
		
		return loc;
	}
	
	public void promptUser(String userPrompt) {
		System.out.println(userPrompt);
		 
		try{
			// Create BufferedReader to read from the Console
		    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		    
		    // Read the line from the console
		    mRecentInput = bufferRead.readLine();
		    if (mRecentInput.equalsIgnoreCase("Exit")) exit();

		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public String getInput() {
		return mRecentInput;
	}
	
	public void exit() {
		//System.out.println("Quiting");
		System.exit(0);
	}

}

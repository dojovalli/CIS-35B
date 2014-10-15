import java.util.HashMap;

@SuppressWarnings("unused")
public class Location {

	// DEBUG
	private static final boolean DEBUG = true;
	private static final boolean DEBUG_EQUALS = false;
	private static final boolean DEBUG_INPUT_CONSTRUCTOR = false;
	private static final int EXPECTED_LOC_ARRAY_SIZE = 2;
	
	// FLAGS
	private static final double UNSET = -1;
	public static final String INVALID_FORMAT = "Invalid Format";
	
	// KEYS
	public static final String KEY_LAT = "Latitude";
	public static final String KEY_LONG = "Longitude";
	public static final String KEY_CITY = "City";
	public static final String KEY_STATE = "State";
	
	// MEMBERS
	private static String mFields[] = { KEY_LAT, KEY_LONG, KEY_CITY, KEY_STATE };
	private double mLatitude;
	private double mLongitude;
	private String mCity;
	private String mState;
	
	// CONSTRUCTOR
	public Location() {
		mLatitude = UNSET;
		mLongitude = UNSET;
		mCity = null;
		mState = null;
	}
	
	public Location(double latitude, double longitude, String city, String state) {
		mLatitude = latitude;
		mLongitude = longitude;
		mCity = city;
		mState = state;
	}
	
	public Location(String cityStateInput) {
		// Break the String into parts using Regex
		String[] location = cityStateInput.split("[,]");
		if (DEBUG && DEBUG_INPUT_CONSTRUCTOR) {
			for (int i = 0; i < location.length; i++) {
				System.out.println("location[" + i + "]: " + location[i].trim());
			}
		}
		
		// Setup Location Object
		mLatitude = UNSET;
		mLongitude = UNSET;
		
		if ( location.length >= EXPECTED_LOC_ARRAY_SIZE) {
			mCity = location[0].trim();
			mState = location[1].trim();
		} else {
			mCity = INVALID_FORMAT;
			mState = INVALID_FORMAT;
			System.out.println("The input does not meet the required format!" + "\n"
								+ "Re-run the program and try again.");
		}
	}

	// CONVENIENCE METHODS
	public boolean isValid() {
		boolean isValid = true;
		
		// Conditions that 'fail' validity
		if (mCity.isEmpty() || mCity == null) isValid = false;
		if (mState.isEmpty() || mState == null) isValid = false;
		
		return isValid;
	}
	
	// OVERRIDES
	public String toString() {
		String str = super.toString() + "\n";
		
		// Build String
		str += "\t" + "latitude: " + mLatitude + "\n";
		str += "\t" + "longitude: " + mLongitude + "\n";
		str += "\t" + "city: " + mCity + "\n";
		str += "\t" + "state: " + mState + "\n";
		
		return str;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean isEqual = false;
		
		if ( obj instanceof Location) {
			// Cast the object to Location type
			Location o = (Location) obj;
			
			// Check the Properties that determine equality
			boolean sameCity = this.getCity().equalsIgnoreCase(o.getCity());
			boolean sameState = this.getState().equalsIgnoreCase(o.getState());
			
			if(DEBUG && DEBUG_EQUALS) {
				System.out.println("sameCity: " + sameCity);
				System.out.println("sameState: " + sameState);
			}
			
			// If all properties are equal, the objects are equal
			if (sameCity && sameState) isEqual = true;
		}
			
		return isEqual;
	}
	
	// GETTERS & SETTERS
	public void set(HashMap<String, String> locationMap) {
		mLatitude = Double.valueOf(locationMap.get(KEY_LAT));
		mLongitude = Double.valueOf(locationMap.get(KEY_LONG));
		mCity = locationMap.get(KEY_CITY);
		mState = locationMap.get(KEY_STATE);
	}
	public double getLatitude() {
		return mLatitude;
	}

	public void setLatitude(double latitude) {
		mLatitude = latitude;
	}

	public double getLongitude() {
		return mLongitude;
	}

	public void setLongitude(double longitude) {
		mLongitude = longitude;
	}

	public String getCity() {
		return mCity;
	}

	public void setCity(String city) {
		mCity = city;
	}

	public String getState() {
		return mState;
	}

	public void setState(String state) {
		mState = state;
	}
	
	public static String[] getFields() {
		return mFields;
	}

}

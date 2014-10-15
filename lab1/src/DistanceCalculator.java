
public class DistanceCalculator {

	// DEBUG
	private static final boolean DEBUG = true;
	
	// FLAGS
	public static final int UNCALCULATED = -1;
	public static final int NULL_ORIGIN = -2;
	public static final int NULL_DESTINATION = -3;
	public static final int NULL_LOCATIONS = -4;
	
	// CONSTANTS
	public static final double RADIUS_EARTH_KM = 6371;
	
	// MEMBERS
	private Location mOrigin;
	private Location mDestination;
	private double mDistance;
	
	// INTERFACE
	interface TripPlanner {
		public void promptUser(String prompt);
	}
	
	
	// CONSTRUCTORS
	public DistanceCalculator() {
		// Empty Constructor
	}
	
	public DistanceCalculator(Location origin, Location destination) {
		mOrigin = origin;
		mDestination = destination;
		mDistance = UNCALCULATED;
	}
	
	
	// GETTERS & SETTERS
	public Location getOrigin() {
		return mOrigin;
	}
	public void setOrigin(Location origin) {
		mOrigin = origin;
	}
	public Location getDestination() {
		return mDestination;
	}
	public void setDestination(Location destination) {
		mDestination = destination;
	}
	
	public double getDistance() {
		
		if ( mOrigin == null || mDestination == null) {
			// Set Flags if Null values
			if (mOrigin == null) mDistance = NULL_ORIGIN;
			if (mDestination == null) mDistance = NULL_DESTINATION;
			if (mOrigin == null && mDestination == null) mDistance = NULL_LOCATIONS;
			return mDistance;
		}
		
		// Get values
		double lat1 = mOrigin.getLatitude();
		double lng1 = mOrigin.getLongitude();
		double lat2 = mDestination.getLatitude();
		double lng2 = mDestination.getLongitude();
		
		// Calculate Distance
		mDistance = calculateDistance(lat1, lat2, lng1, lng2);
		
		return mDistance;
	}
	
	public double calculateDistance(double lat1, double lat2, double lng1, double lng2) {

		// Haversine Great Circle Distance
		double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(lng2-lng1);
	    double sindLat = Math.sin(dLat / 2);
	    double sindLng = Math.sin(dLng / 2);
	    double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
	            * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	   	double dist = RADIUS_EARTH_KM * c;
	    
	   	return dist;
	}
	


}

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/** LocationStore -- SINGLETON CLASS
 * 
 * Responsible for handling storage of Location objects and implementation for
 * creating, sorting, & retrieving Location objects.
 * 
 * 
 * @author Matthew Valli
 *
 */
@SuppressWarnings("unused")
public class LocationStore {

	// DEBUG
	private static final boolean DEBUG = true;
	private static final boolean DEBUG_NODES = false;
	private static final boolean DEBUG_CONTAINS = false;
	
	// FLAGS
	public static final int NOT_FOUND= -1;
	
	// MEMBERS
	private static LocationStore mLocationStore;
	private ArrayList<Location> mLocations;
	
	// CONSTRUCTOR
	private LocationStore() {
		mLocations = new ArrayList<Location>();
	}
	
	// ACCESSOR METHODS
	public static LocationStore get() {
		if (mLocationStore == null) {
			mLocationStore = new LocationStore();
		}
		return mLocationStore;
	}
	
	public ArrayList<Location> getItems() {
		return mLocations;
	}
	
	public Location get(Location loc) {
		// Check each element of the Array for equality
		for (Location l : getItems()) {
			// If the supplied location is equal to the location in the array, return the location in the array
			if (l.equals(loc)) return l;
		}
		
		// If no location matches, return null
		return null;
	}
	
	public int binarySearch( Location x )
    {
		System.out.println("Search using binarySearch");
        int low = 0;
        int high = mLocations.size() - 1;
        int mid;

        while( low <= high )
        {
        	// Calculate Mid Value
            mid = ( low + high ) / 2;
            // Compare Locations
            int compareVal = mLocations.get( mid ).compareTo( x );
            
            // Set High/Lows or Return Index value
            if( compareVal < 0 )
                low = mid + 1;
            else if(  compareVal > 0 )
                high = mid - 1;
            else
                return mid;
        }

        return NOT_FOUND;     // NOT_FOUND = -1
    }
	
	public void sort() {
		mLocations.sort(new Comparator<Location>() {
	        public int compare(Location a, Location b)
			{
	            return  a.getCity().compareTo(b.getCity());
	        }
		});
	}
	
	// OVERRIDES
	public boolean contains(Location loc) {
		boolean containsLoc = false;
		
		// Check each element of the Array for equality
		for (Location l : getItems()) {
			// If the supplied location is equal to the location in the array, return the location in the array
			if (DEBUG && DEBUG_CONTAINS) System.out.println(l.getCity() + ", " + l.getState() + " : " + loc.getCity() + ", " + loc.getState() );
			if (l.equals(loc)) return true;
		}
		
		
		return containsLoc;
	}
	
	/** Setup the available Locations based on XML File
	 * 
	 * @param xmlFileName
	 */
	public void setLocations(XMLReader reader) {
		if (DEBUG && DEBUG_NODES) System.out.println("Enter setLocations(String xmlFileName)");
		
		try {
			// Parse the NodeList and create Location Objects from the Node Data
			mLocations = generateItemArrayList(Location.getSFields(), reader.getNodeHashMaps());
			if (DEBUG && DEBUG_NODES) System.out.println(mLocations.toString());
			
		} catch (Exception e ) {
			System.out.print("Exception: ");
			e.printStackTrace();
		} 
	}
	public void setLocations(String xmlFilename, String element, String[] fields) {
		
		if (DEBUG && DEBUG_NODES) System.out.println("Enter setLocations(String xmlFileName)");
		
		try {
			// Read the XML File
			//Document loctionXML = XMLReader.ReadXML("Coordinates.xml");
			XMLReader locationXML = new XMLReader(xmlFilename, element, Location.getSFields());
			
			// Create a NodeList based on the Location tag
			//NodeList locationNodes = XMLReader.GetNodes(loctionXML,  "Location");
			
			// Parse the NodeList and create Location Objects from the Node Data
			//mLocations = getItems(locationNodes, Location.getFields());
			mLocations = generateItemArrayList(fields, locationXML.getNodeHashMaps());
			if (DEBUG && DEBUG_NODES) System.out.println(mLocations.toString());
			
		} catch (Exception e ) {
			System.out.print("Exception: ");
			e.printStackTrace();
		} 
	}
	
	
	// Generates an ArrayList<T> of Type T objects from an ArrayList of HashMap<String,String> representing the class Type T
		public ArrayList<Location> generateItemArrayList(String[] fields, ArrayList<HashMap<String,String>> itemsAsHashMaps) {
			
			// Create a new ArrayList of Type T
			ArrayList<Location> objectList = new ArrayList<Location>();
			
			// Convert the HashMap into Objects of Type T
			for (int i = 0; i < itemsAsHashMaps.size(); i++) {
				// Create a new Object of Type T
				Location obj = new Location();
				
				// Use the Abstract Method T.set(HashMap map) to setup the object
				obj.set(itemsAsHashMaps.get(i));
				
				// Add the Object to the ArrayList of Type T objects
				objectList.add(obj);
			}
			
			return objectList;
		}
	
/*
	public static ArrayList<Location> getItems(NodeList nodelist, String[] selements)
	{
		ArrayList<Location> locationList = new ArrayList<Location>();
        for(int s=0; s<nodelist.getLength() ; s++)
        {
        	
        	// Create a new Location object
        	Location loc = new Location();
        	
        	// Tech Stuff
            Node firstNode = XMLReader.GetNode(nodelist,s);
            if(firstNode.getNodeType() == Node.ELEMENT_NODE)
            {
            	// Create a HashMap to store the values pulled from the elements
                HashMap<String,String> locationMap = new HashMap<String,String>();
                Element element = (Element)firstNode;
                for (int x=0; x<selements.length; x++)
                {
                    NodeList nlist = element.getElementsByTagName(selements[x]);
                    Element item = (Element)nlist.item(0);
                    NodeList tlist = item.getChildNodes();
                    
                    // Value of the current Element & Add it to the HashMap
                    String elementValue = ((Node)tlist.item(0)).getNodeValue().trim();
                    locationMap.put(selements[x], elementValue);
                    
                }
                // Set the values of the Location Object using the HashMap & add the Location to the ArrayList
                loc.set(locationMap);
                locationList.add(loc);
            }
            
        }
        return locationList;
	}
	*/

}

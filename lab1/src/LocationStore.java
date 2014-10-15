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
public class LocationStore {

	// DEBUG
	private static final boolean DEBUG = true;
	private static final boolean DEBUG_NODES = false;
	private static final boolean DEBUG_CONTAINS = false;
	
	// MEMBERS
	private static LocationStore mLocationStore;
	private static ArrayList<Location> mLocations;
	
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
	
	public ArrayList<Location> getLocations() {
		return mLocations;
	}
	
	public Location get(Location loc) {
		// Check each element of the Array for equality
		for (Location l : getLocations()) {
			// If the supplied location is equal to the location in the array, return the location in the array
			if (l.equals(loc)) return l;
		}
		
		// If no location matches, return null
		return null;
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
		for (Location l : getLocations()) {
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
	public void setLocations(String xmlFileName) {
		
		if (DEBUG && DEBUG_NODES) System.out.println("Enter setLocations(String xmlFileName)");
		
		try {
			// Read the XML File
			Document loctionXML = XMLReader.ReadXML("Coordinates.xml");
			
			// Create a NodeList based on the Location tag
			NodeList locationNodes = XMLReader.GetNodes(loctionXML,  "Location");
			
			// Parse the NodeList and create Location Objects from the Node Data
			mLocations = getLocations(locationNodes, Location.getFields());
			if (DEBUG && DEBUG_NODES) System.out.println(mLocations.toString());
			
		} catch (Exception e ) {
			System.out.print("Exception: ");
			e.printStackTrace();
		} 
	}
	

	public static ArrayList<Location> getLocations(NodeList nodelist, String[] selements)
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

}

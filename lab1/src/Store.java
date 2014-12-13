import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/** Store -- SINGLETON CLASS
 * 
 * Responsible for handling storage of T objects and implementation for
 * creating, sorting, & retrieving T objects.
 * 
 * 
 * @author Matthew Valli
 *
 */
@SuppressWarnings("unused")
public class Store<T extends StoreInterface<T>> {

	// DEBUG
	private static final boolean DEBUG = true;
	private static final boolean DEBUG_NODES = false;
	private static final boolean DEBUG_CONTAINS = false;
	
	// FLAGS
	public static final int NOT_FOUND= -1;
	
	// MEMBERS
	private static Store mStore;
	private ArrayList<T> mItems;
	private Class mClass;
	private XMLReader mXMLReader;
	
	
	// CONSTRUCTOR
	private Store() {
		this(new Object().getClass(), null);
	}
	
	private Store(Class c, XMLReader xmlFileReader) {
		mItems = new ArrayList<T>();
		mXMLReader = xmlFileReader;
		mClass = c;
	}
	
	// ACCESSOR METHODS
	public static Store newInstance(Class c) {
		return newInstance(c, null);
	}
	public static Store newInstance(Class c, XMLReader reader) {
		if (mStore == null) {
			mStore = new Store(c, reader);
		}
		return mStore;
	}
	
	
	public static Store get() {
		return mStore;
	}
	
	public ArrayList<T> getItems() {
		return mItems;
	}
	
	public T get(T item) {
		// Check each element of the Array for equality
		for (T i : getItems()) {
			// If the supplied T is equal to the T in the array, return the T in the array
			if (i.equals(item)) return item;
		}
		
		// If no T matches, return null
		return null;
	}
	
	public int binarySearch( T x )
    {
		System.out.println("Search using binarySearch");
        int low = 0;
        int high = mItems.size() - 1;
        int mid;

        while( low <= high )
        {
        	// Calculate Mid Value
            mid = ( low + high ) / 2;
            // Compare Locations
            int compareVal = NOT_FOUND;
            try {
            	compareVal = mItems.get( mid ).compareTo( x );
            } catch (Exception e) {
            	// Log the Exception to the Console
            	System.out.println("Error! Most-likely Type T does not implement the interface StoreInterface<T>: "
            						+ "public int compareTo(T anotherObject)");
            	System.out.print("Exception: ");
    			e.printStackTrace();
            }
            
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
		mItems.sort(new Comparator<T>() {
	        public int compare(T a, T b)
			{
	            return  a.compareTo(b);
	        }
		});
	}
	
	// OVERRIDES
	public boolean contains(T item) {
		boolean containsItem = false;
		
		// Check each element of the Array for equality
		for (T l : getItems()) {
			// If the supplied T is equal to the T in the array, return the T in the array
			if (DEBUG && DEBUG_CONTAINS) System.out.println(l.toString() + " : " + item.toString() );
			if (l.equals(item)) return true;
		}
		
		
		return containsItem;
	}
	
	/** Setup the available Locations based on XML File
	 * 
	 * @param xmlFileName
	 */
public void setItems(XMLReader reader) {
		
		if (DEBUG && DEBUG_NODES) System.out.println("Enter setLocations(XMLReader reader)");
		
		try {
			// Convert HashMap To an ArrayList of Type T
			mItems = hashMapToArrayList(reader);
			if (DEBUG && DEBUG_NODES) System.out.println(mItems.toString());
			
		} catch (Exception e ) {
			System.out.print("Exception: ");
			e.printStackTrace();
		} 
	}
	
	public void setItems(String xmlFilename, String xmlElement, String[] objectFields) {
		
		if (DEBUG && DEBUG_NODES) System.out.println("Enter setLocations(" + "\n" 
														+ "String xmlFileName, "
														+ "String xmlElement, "
														+ "String[] objectFields)");
		
		try {
			// Read the XML File
			XMLReader xmlLocationFile = new XMLReader(xmlFilename, xmlElement, objectFields);
			
			// Convert HashMap To an ArrayList of Type T
			mItems = hashMapToArrayList(xmlLocationFile);
			if (DEBUG && DEBUG_NODES) System.out.println(mItems.toString());
			
		} catch (Exception e ) {
			System.out.print("Exception: ");
			e.printStackTrace();
		} 
	}
	
	// Generates an ArrayList<T> of Type T objects from an ArrayList of HashMap<String,String> representing the class Type T
	public ArrayList<T> generateItemArrayList(String[] fields, ArrayList<HashMap<String,String>> itemsAsHashMaps) {
		
		// Create a new ArrayList of Type T
		ArrayList<T> objectList = new ArrayList<T>();
		
		// Convert the HashMap into Objects of Type T
		for (int i = 0; i < itemsAsHashMaps.size(); i++) {
			// Create a new Object of Type T
			T obj = null;
			try {
				// Attempt to create a new object using newInstance()
				obj = (T) mClass.newInstance();
			} catch (Exception e) {
				System.out.println("Failed to create a newInstance of T in Store.class");
				System.out.print("Eception: ");
				e.printStackTrace();
			}
			
			// If the obj isn't null, set it's values and add it to the objectList
			if (obj != null) {
				// Use the Abstract Method T.set(HashMap map) to setup the object
				obj.set(itemsAsHashMaps.get(i));
				
				// Add the Object to the ArrayList of Type T objects
				objectList.add(obj);
			} else {
			}
		}
		
		return objectList;
	}

	
	// CONVENIENCE METHODS
	private ArrayList<T> hashMapToArrayList(XMLReader reader) {
		ArrayList<T> list = new ArrayList<T>();
		
		// Parse the NodeList and create T Objects from the Node Data
		// Get an ArrayList of HashMaps representing individual Nodes
		ArrayList<HashMap<String, String>> itemsAsHashMaps = reader.getNodeHashMaps();
		// Convert the HashMaps into Objects of Type T & set the ArrayList 'mItems' to a list of T Objects
		list = generateItemArrayList(reader.getFields(), itemsAsHashMaps);
		if (DEBUG && DEBUG_NODES) System.out.println(mItems.toString());
		
		return list;
	}

}

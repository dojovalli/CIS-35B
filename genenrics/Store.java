import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import org.w3c.dom.Document;
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
public class Store<T extends StoreInterfaceObject> {

	// DEBUG
	private static final boolean DEBUG = true;
	private static final boolean DEBUG_NODES = false;
	private static final boolean DEBUG_CONTAINS = false;
	
	// FLAGS
	public static final int NOT_FOUND= -1;
	
	// MEMBERS
	private static Store mStore;
	private ArrayList<T> mItems;
	
	// INTERFACE
	interface StoreInterface<T> {
		public int compareTo(T anotherObject);
		public String[] getFields();
	}
	
	// CONSTRUCTOR
	private Store<T>() {
		mItems = new ArrayList<T>();
	}
	
	// ACCESSOR METHODS
	public static Store newInstance(Class classname) {
		if (mStore == null) {
			mStore = new Store<classname>();
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
		for (T item : getItems()) {
			// If the supplied T is equal to the T in the array, return the T in the array
			if (item.equals(item)) return item;
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
	public void setItems(String xmlFileName) {
		
		if (DEBUG && DEBUG_NODES) System.out.println("Enter setLocations(String xmlFileName)");
		
		try {
			// Read the XML File
			// TODO CHANGE!!!
			Document itemXML = XMLReader.ReadXML("Coordinates.xml");
			
			// Create a NodeList based on the T tag
			NodeList itemNodes = XMLReader.GetNodes(itemXML,  "T");
			
			// Parse the NodeList and create T Objects from the Node Data
			mItems = getItems(itemNodes, T.getFields());
			if (DEBUG && DEBUG_NODES) System.out.println(mItems.toString());
			
		} catch (Exception e ) {
			System.out.print("Exception: ");
			e.printStackTrace();
		} 
	}
	

	public static ArrayList<T> getItems(NodeList nodelist, String[] selements)
	{
		ArrayList<T> itemList = new ArrayList<T>();
        for(int s=0; s<nodelist.getLength() ; s++)
        {
        	
        	// Create a new T object
        	T item = new T();
        	
        	// Tech Stuff
            Node firstNode = XMLReader.GetNode(nodelist,s);
            if(firstNode.getNodeType() == Node.ELEMENT_NODE)
            {
            	// Create a HashMap to store the values pulled from the elements
                HashMap<String,String> itemMap = new HashMap<String,String>();
                Element element = (Element)firstNode;
                for (int x=0; x<selements.length; x++)
                {
                    NodeList nlist = element.getElementsByTagName(selements[x]);
                    Element item = (Element)nlist.item(0);
                    NodeList tlist = item.getChildNodes();
                    
                    // Value of the current Element & Add it to the HashMap
                    String elementValue = ((Node)tlist.item(0)).getNodeValue().trim();
                    itemMap.put(selements[x], elementValue);
                    
                }
                // Set the values of the T Object using the HashMap & add the T to the ArrayList
                item.set(itemMap);
                itemList.add(item);
            }
            
        }
        return itemList;
	}

}

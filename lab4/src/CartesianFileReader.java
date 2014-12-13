import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class CartesianFileReader {

	private static final boolean DEBUG = true;
	private static final boolean DEBUG_READ = true;
	
	public static ArrayList<CartesianCoordinate> read(String path) throws IOException  {
		// Local Vars
		int count = 0;
		ArrayList<CartesianCoordinate> valueSet = new ArrayList<CartesianCoordinate>();
		
		// Use a BufferedReader to read the File
		BufferedReader in = new BufferedReader(new FileReader(path));
		
		if (DEBUG && DEBUG_READ) {
			System.out.println("Converting File to Cartesian Coordinates...");
		}
		
		// Read the file line by line, creating an ArrayList<CartesianCoordinate>
		while (in.ready()) {
			// Read the line
			String s = in.readLine();
			
			// Create a CartesianCoordinate
			CartesianCoordinate coord = new CartesianCoordinate(count, Double.valueOf(s));
			
			if (DEBUG && DEBUG_READ) {
				System.out.println(coord.toString());
			}
			
			// Add the object to the ArrayList<CartesianCoordinate>
			valueSet.add(coord);
			
			// Increment the count
			count++;
		}
		// Close the BufferedReader
		in.close();
		
		return valueSet;
	}
}

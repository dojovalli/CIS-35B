import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SuppressWarnings("unused")
public class Regex {

	public static final boolean DEBUG = false;
	public static final boolean DEBUG_HAS_EXT = true;

	public static boolean hasExtension(String path, String extension) {
		
		// Match Numberic and Hexadecimal Values
	    Pattern extTest = Pattern.compile("\\.(" + extension.trim() + ")$"); 
	    Matcher values = extTest.matcher(path);
	 
	    if (DEBUG && DEBUG_HAS_EXT) {
	    	System.out.println("Regex - Extension Matches");
	    	System.out.println("Search String: " + path);
		    System.out.println("Pattern: " + extTest.pattern());
		    System.out.print("Match: ");
	    }
	    
	    // Returns true if there is anotherMatch remaining, returns false if no matches remain
	    boolean anotherMatch = values.find();
	    
	    if (anotherMatch == true) {
	    	while(anotherMatch) {
	    		
	    		// If we get here, there is a match
	    		// Log
	    	    if (DEBUG && DEBUG_HAS_EXT) System.out.println(values.group());
	    		
	    		// Check to see if there is anotherMatch
	    		anotherMatch = values.find();
	    	}
	    	return true;
	    } else {
		    if (DEBUG && DEBUG_HAS_EXT) System.out.println("There was no match");
	    	return false;
	    }
	}
}

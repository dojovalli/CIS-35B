import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;

@SuppressWarnings("unused")
public abstract class ConsoleController {

	// DEBUG
	private static final boolean DEBUG = false;
	private static final boolean DEBUG_STORE = false;
	private static final boolean DEBUG_PROMPT = false;
	private static final boolean DEBUG_VALUES = false;
	
	// CONSTANTS
	protected static final String DEFAULT_MESSAGE = "No input recieved";
	
	
	// MEMBERS
	private String mRecentInput;
	
	
	// CONSTRUCTORS
	public ConsoleController() {
		mRecentInput = DEFAULT_MESSAGE;
	}

	
	// LIFE-CYCLE METHODS
	abstract public void onCreate();

	public String promptUser(String userPrompt) {
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
		return mRecentInput;
	}
	
	public String getInput() {
		return mRecentInput;
	}
	
	public void exit() {
		//System.out.println("Quiting");
		System.exit(0);
	}

}

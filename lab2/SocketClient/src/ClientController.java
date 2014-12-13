import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;


public class ClientController extends ConsoleController {
	
	// DEBUG
	private static final boolean DEBUG = true;
	private static final boolean DEBUG_STORE = false;
	private static final boolean DEBUG_PROMPT = false;
	private static final boolean DEBUG_VALUES = true;
	
	// CONSTANTS
	public static final String PROMPT_MESSAGE = "Enter the filepath to a '.csv' file to convert to a '.xml' file: ";
	public static final int FILE_CONVERSION_PORT = 8081;
	public static final int FILE_TRANSFER_PORT = 54321;
	
	// MEMBERS
	private String userFilepath;
	private SocketClient mClient;
	private String mHostname;
	private int mPort;
	
	// CONSTRUCTORS
	public ClientController() {
		super();
		mClient = null;
	}
	
	// LIFE-CYCLE METHODS
	@Override
	public void onCreate() {
		
		// Setup the Connection to the Server
		mClient = setupServerConnection();
        
		// Get the path to the CSV file for conversion
		requestPathToCSVFile();
	
        // Attempt to Transfer the file
        try {
        	// SEND THE FILEPATH
        	//sendPathToServer(userFilepath);
        	System.out.println("USER PATH: " + userFilepath);
        	// SEND THE ACTUAL FILE FOR CONVERSION (start FileTransfer Server and wait for request)
        	//sendFileToServer(userFilepath);
        	//mClient.sendTransfer(userFilepath);
        	
        	// SEND THE FILEPATH
        	sendPathToServer(userFilepath);

        } catch (Exception e) {
        	System.out.println("Failed to transfer the file!");
        	System.out.println("Exception: ");
        	e.printStackTrace();
        }
        
        // Receive From Server
        byte[] buffer = new byte[1024];
		int bytes_read = 0;
		try 
		{
			/*
			// This call to read() will wait forever, until the
			// program on the other side either sends some data,
			// or closes the socket.
			bytes_read = mClient.getInputStream().read(buffer, 0, buffer.length);
			
			// If the socket is closed, sockInput.read() will return -1.
			if(bytes_read < 0) 
			{
				System.err.println("Tried to read from socket, read() returned < 0,  Closing socket.");
				return;
			}
			*/
			
			// Create a FileOutputStream to create a file on the Client Machine
			FileOutputStream fos = new FileOutputStream("/Users/admin/Desktop/test.xml");
			
			// Create a BufferedOutputStream that will handle writing the file to the machine
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			
			// Declare vars
			int bytesRead = 0;
			int count = 0;
			
			// Begin reading the data from the Server
			while ((bytesRead = mClient.getInputStream().read(buffer, 0, buffer.length)) != -1)
			{
				// Save the data on the Client Machine
				bos.write(buffer, 0, bytesRead);
				bos.flush();
				System.out.println(++count);
			}
			// Close the OutputStream for writing to the Client Computer
			bos.close();
			System.out.println("++++++++++++++++++++++++Server Received "+ bytes_read +" bytes, data=" + (new String(buffer, 0, bytes_read)));
		}
		catch (Exception e)
		{
			System.err.println("Exception reading from/writing to socket, e="+e);
			e.printStackTrace(System.err);
			return;
		}
        
        
        // Attempt to send some message 50 times
        //mClient.sendSomeMessages(3);
		
		
	}
	
	// CONVENIENCE METHODS
	private SocketClient setupServerConnection() {
		mHostname = "localhost";
       	mPort = FILE_CONVERSION_PORT;
        byte[] data = "Hello Server".getBytes();
        
        return new SocketClient(mHostname, mPort, data);
	}
	
	private void requestPathToCSVFile() {
		// Debug vars
		boolean isEmpty = true;
		boolean isDefaultMessage = true;
		boolean isCSV = false;
		
		while (true){
			
			if (!isEmpty && !isDefaultMessage && isCSV) break;
			
        	// Prompt user
        	userFilepath = promptUser(PROMPT_MESSAGE);
        	System.out.println(userFilepath);
        	// Debugging
        	isEmpty = userFilepath.isEmpty();
			isDefaultMessage = userFilepath.equals(DEFAULT_MESSAGE);
			isCSV = isCSVPath(userFilepath);
			
			
			// Output Debugging
			if (DEBUG && DEBUG_VALUES) {
				if (userFilepath != null) {
					System.out.println("DEBUG userFilepath: " + userFilepath);
				} else {
					System.out.println("DEBUG userFilepath: isNull");
				}
				System.out.println("isEmpty: " + (isEmpty? "true":"false"));
				System.out.println("DEBUG userFilepath:" + (isDefaultMessage? "true":"false"));
    			System.out.println("isCSVPath: " + (isCSVPath(userFilepath)? "true":"false"));
			}
			
        }
	}
	private void sendPathToServer(String path) {
		// Send the filepath to the Server
    	mClient.sendMessage(path);
	}
	
	/*
	private void sendFileToServer(String path) {
		
		mClient.openSocket();
		
		// Send the CSV file to the Server
		// Create a new Socket for transfering the file
		//FileTransferServer sendCsv = new FileTransferServer();
		// Create a file object from the filepath
		System.out.println("path: " + path);
		File myFile = new File("test.csv");
		try {
			// Attempt to send the file at the path to the Server through the provided port
			System.out.println("Initiating File Upload...");
			//sendCsv.sendTransfer(FILE_TRANSFER_PORT, path);
			mClient.sendTransfer(path);
			
			
			// START MULITLINE COMMENT
			System.out.println("myFile.absolutePath: " + myFile.getAbsolutePath() + "\n"
								+ "myFile.length: " + myFile.length() + "\n");
			
			// Create a byte array for transfer (the size of the file)
			byte[] tranFile = new byte[(int) myFile.length()];
			System.out.println("tranFile.size: " + tranFile.length + "\n"
								+ "tranFile: " + tranFile.toString());
			
			// Create a BufferedInputStream filled with the data from the file object
			FileInputStream fileStream = new FileInputStream(myFile);
			System.out.println(fileStream.toString());
			BufferedInputStream bis = new BufferedInputStream(fileStream);
			// Read the file byte array to make sure no exceptions are thrown before transfer
			bis.read(tranFile, 0, tranFile.length);
			
			// Get the Output connection to the Client
			OutputStream os = mClient.getSocket().getOutputStream();
			
			// Actually SEND the information to the Client
			os.write(tranFile, 0, tranFile.length);
			// If any bytes remain that have not been set, send them now
			os.flush();
			// END MULTILINE COMMENT
		
			
		} catch (Exception e) {
			System.out.println("Exception while attempting to transfer '.csv' file from Client to Server");
			e.printStackTrace();
		}
		
		mClient.closeSocket();
	}
	*/
	
	
	
	private boolean isCSVPath(String path) {
		return Regex.hasExtension(path, "csv");
	}
}

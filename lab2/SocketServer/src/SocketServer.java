import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

// An example of a very simple socket server.  
public class SocketServer 
{
	// CONSTANTS
	public static final int TRANSFER_SIZE_1KB = 1024;
	private static final boolean DEBUG = true;
	private static final boolean DEBUG_NODES = true;
	
	// FLAGS
	private static final int FLAG_MESSAGE = 0;
	private static final int FLAG_CONVERT_CSV_TO_XML = 1;
	
	// CONSTANTS
	public static final int FILE_CONVERSION_PORT = 8081;
	public static final int FILE_TRANSFER_PORT = 54321;
	private static final String TEMPORARY_FOLDER_PATH = "/tmp";
	private static final String TEMP_CSV_FILENAME = "csvToConvertToXml.csv";
	private static final String TEMP_XML_FILENAME = "xmlOfCsv.xml";
	
	// MEMBERS
	private int serverPort;
	private ServerSocket mServerSocket = null;
	private Socket mSocket;
	private InputStream mInputStream;
	private OutputStream mOutputStream;
	private int mSocketFlag;
	private String mPathToCsv;
	

	public SocketServer(int serverPort) 
	{
		this.serverPort = serverPort;

		try 
		{
			mServerSocket = new ServerSocket(serverPort);
		}
		catch (IOException e)
		{
			e.printStackTrace(System.err);
		}
	}


	public void waitForConnections() 
	{
		
		mSocket = null;
		mInputStream = null;
		mOutputStream = null;
		while (true) 
		{
			// Open a Socket on the Server and Wait for a Connection
			openSocket();

			// TODO CODE THE FLAGGING
			mSocketFlag = 1;
			
			switch (mSocketFlag) {
				case FLAG_MESSAGE:
					handleConnection();
				break;
				case FLAG_CONVERT_CSV_TO_XML:
					handleFileConversionConnection();
				break;
			}

			// Now close the socket.
			closeSocket();
		}
	}
	
	// All this method does is wait for some bytes from the
	// connection, read them, then write them back again, until the
	// socket is closed from the other side.
	public void handleFileConversionConnection() 
	{
		while(true) 
		{
			byte[] buffer = new byte[TRANSFER_SIZE_1KB];
			int bytes_read = 0;
			try 
			{
				// This call to read() will wait forever, until the
				// program on the other side either sends some data,
				// or closes the socket.
				bytes_read = mInputStream.read(buffer, 0, buffer.length);
				
				// If the socket is closed, sockInput.read() will return -1.
				if(bytes_read < 0) 
				{
					System.err.println("Tried to read from socket, read() returned < 0,  Closing socket.");
					return;
				}
				
				// Set the mPathToCsv
				mPathToCsv = new String(buffer, 0, bytes_read);
				
				// Log the RECIEVED DATA
				System.out.println("Server Received "+ bytes_read +" bytes, data=" + mPathToCsv);
				System.out.println("mPathToCsv: " + mPathToCsv);
				
				
				// Get the a the path supplied by the Client and save it in the /temp folder on the server
				//String pathToTempCsv = getFileFromClient(mPathToCsv,TEMPORARY_FOLDER_PATH, TEMP_CSV_FILENAME);
				
				// Convert the Csv to XML
				String pathToXmlForClient = convertCsvToXml(mPathToCsv, TEMPORARY_FOLDER_PATH, TEMP_XML_FILENAME);
				
				// Transfer the new XML Document to the Client
				transfer(pathToXmlForClient);
				
				
				// 
				mOutputStream.write(buffer, 0, bytes_read);
				// This call to flush() is optional - we're saying go
				// ahead and send the data now instead of buffering it.
				mOutputStream.flush();
			}
			catch (Exception e)
			{
				System.err.println("Exception reading from/writing to socket, e="+e);
				e.printStackTrace(System.err);
				return;
			}
		}
	}
	
	// All this method does is wait for some bytes from the
		// connection, read them, then write them back again, until the
		// socket is closed from the other side.
		public void handleConnection() 
		{
			while(true) 
			{
				byte[] buffer = new byte[1024];
				int bytes_read = 0;
				try 
				{
					// This call to read() will wait forever, until the
					// program on the other side either sends some data,
					// or closes the socket.
					bytes_read = mInputStream.read(buffer, 0, buffer.length);
					
					// If the socket is closed, sockInput.read() will return -1.
					if(bytes_read < 0) 
					{
						System.err.println("Tried to read from socket, read() returned < 0,  Closing socket.");
						return;
					}
					System.out.println("Server Received "+ bytes_read +" bytes, data=" + (new String(buffer, 0, bytes_read)));
					mOutputStream.write(buffer, 0, bytes_read);
					// This call to flush() is optional - we're saying go
					// ahead and send the data now instead of buffering it.
					mOutputStream.flush();
				}
				catch (Exception e)
				{
					System.err.println("Exception reading from/writing to socket, e="+e);
					e.printStackTrace(System.err);
					return;
				}
			}
		}

	
	
	public void openSocket() {
		try 
		{
			// This method call, accept(), blocks and waits
			// (forever if necessary) until some other program
			// opens a socket connection to our server.  When some
			// other program opens a connection to our server,
			// accept() creates a new socket to represent that
			// connection and returns.
			mSocket = mServerSocket.accept();
			System.err.println("Have accepted new socket.");

			// From this point on, no new socket connections can
			// be made to our server until accept() is called again.
			mInputStream = mSocket.getInputStream();
			mOutputStream = mSocket.getOutputStream();
		}
		catch (IOException e)
		{
			e.printStackTrace(System.err);
		}
	}
	
	public void closeSocket() {
		try 
		{
			System.err.println("Closing socket.");
			mSocket.close();
		}
		catch (Exception e)
		{
			System.err.println("Exception while closing socket.");
			e.printStackTrace(System.err);
		}

		System.err.println("Finished with socket, waiting for next connection.");
	}

	
	// CONVENIENCE METHODS
	public void transfer(String sfile) throws IOException {
		if (DEBUG && DEBUG_NODES) System.out.println("Enter transfer(String sfile)");
		
		// Create a new File object
		File myFile = new File(sfile);
	
		//String tester = "this is a test";
		
			byte[] mybytearray = new byte[(int) myFile.length()];
			
			//mybytearray = tester.getBytes();
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile));
			bis.read(mybytearray, 0, mybytearray.length);
			
			// Get the OutputStream for the Socket
			//mOutputStream.write(mybytearray, 0, mybytearray.length);
			//mOutputStream.flush();
		
		mOutputStream.write(mybytearray, 0, mybytearray.length);
		mOutputStream.flush();
			// Close the socket
			//bis.close();
	}
	
	public String getFileFromClient(String pathToFile, String destinationDirectory, String newFilename) {
		if (DEBUG && DEBUG_NODES) System.out.println("Enter getFileFromClient(String pathToFile, String destinationDirectory, String newFilename)");
		String pathToNewFile = destinationDirectory + "/" + newFilename;
		// TODO GET THE FILE FROM THE CLIENT
		// Connect to the FILE_TRANSFER_PORT
		try {
			System.out.println("Getting file for conversion from the Client...");
			// Create a new FileTransferClient and request the file from the Client (in this short-sided case, a Server)
			//FileTransferClient requestCsv = new FileTransferClient();
			//requestCsv.recieveTransfer("localhost", FILE_TRANSFER_PORT, pathToNewFile);
			// Create a new byte array
			byte[] bytearray = new byte[1024];
			
			// Get a connection to the Output from the Server
			//InputStream is = socket.getInputStream();
			
			// Create a FileOutputStream to create a file on the Client Machine
			FileOutputStream fos = new FileOutputStream(newFilename);
			
			// Create a BufferedOutputStream that will handle writing the file to the machine
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			
			// Declare vars
			int bytesRead = 0;
			int count = 0;
			
			System.out.println("Downloading transfer...");
			boolean enteredLoop = false;
			// Begin reading the data from the Server
			while (bytesRead != -1)
			{
				// Read the transfer
				bytesRead = mInputStream.read(bytearray, 0, bytearray.length);
				System.out.println("bytearray: " + bytearray.toString());
				
				// Save the data on the Client Machine
				bos.write(bytearray, 0, bytesRead);
				bos.flush();
				System.out.println(++count);
				enteredLoop = true;
			}
			System.out.println("enteredLoop: " + (enteredLoop? "true" : "false"));
			// Close the OutputStream for writing to the Client Computer
			bos.close();
			
			// Wait for the file to transfer before moving on
			//requestCsv.wait(50);
		} catch (Exception e) {
			System.out.println("There was an error in requesting the '.csv' file from the Client");
			e.printStackTrace();
		}
		
		if (DEBUG && DEBUG_NODES) System.out.println("Exit getFileFromClient(String pathToFile, String destinationDirectory, String newFilename)");
		return pathToNewFile;
	}
	
	public int getServerPort() {
		return serverPort;
	}


	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}


	public ServerSocket getServerSocket() {
		return mServerSocket;
	}


	public void setServerSocket(ServerSocket serverSocket) {
		mServerSocket = serverSocket;
	}


	public Socket getSocket() {
		return mSocket;
	}


	public void setSocket(Socket socket) {
		mSocket = socket;
	}


	public InputStream getInputStream() {
		return mInputStream;
	}


	public void setInputStream(InputStream inputStream) {
		mInputStream = inputStream;
	}


	public OutputStream getOutputStream() {
		return mOutputStream;
	}


	public void setOutputStream(OutputStream outputStream) {
		mOutputStream = outputStream;
	}


	public String getPathToCsv() {
		return mPathToCsv;
	}


	public void setPathToCsv(String pathToCsv) {
		mPathToCsv = pathToCsv;
	}


	public String convertCsvToXml(String pathToCsv, String pathToDestinationDirectory, String newFilename) {
		if (DEBUG && DEBUG_NODES) System.out.println("Enter convertCsvToXml(String pathToCsv, String pathToDestinationDirectory, String newFilename)");
		String pathToNewFile = pathToDestinationDirectory + "/" + newFilename;
		// TODO CREATE THE NEW FILE AND CONVERT THE CSV TO XML
		XMLWriter xmlFile = new XMLWriter(pathToCsv, pathToNewFile);
		xmlFile.csvToXml();
		
		if (DEBUG && DEBUG_NODES) System.out.println("Exit convertCsvToXml(String pathToCsv, String pathToDestinationDirectory, String newFilename)");
		return pathToNewFile;
	}
	
	public void getFilePathFromClient() {
		
	}
	
	
	public static void main(String argv[]) 
	{
		SocketServer server = new SocketServer(FILE_CONVERSION_PORT);
		server.waitForConnections();
	}
}

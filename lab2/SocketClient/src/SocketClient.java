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

public class SocketClient 
{
	// CONSTANTS
	public static final int TRANSFER_SIZE_1KB = 1024;
	public static final int FILE_CONVERSION_PORT = 8081;
	public static final int FILE_TRANSFER_PORT = 54321;
	
	// MEMBERS
    private String mServerHostname = null;
    private int mServerPort = 0;
    private byte[] data = null;
    private Socket mSocket = null;
    private InputStream mSocketInput = null;
    private OutputStream mSocketOutput = null;

    public SocketClient(String ServerHostname, int ServerPort)
    {
        this(ServerHostname, ServerPort, null);
    }
    
    public SocketClient(String mServerHostname, int mServerPort, byte[] data)
    {
        this.mServerHostname =  mServerHostname;
        this.mServerPort = mServerPort;
        this.data = data;
        openSocket();
    }
    
    public void sendTransfer( String filepath ) throws Exception {
    	/*
    	// New Socket
    	ServerSocket socket = null;
    	try {
    		socket = new ServerSocket(FILE_TRANSFER_PORT);
    	} catch (Exception e) {
    		System.out.println("Failed to create a new ServerSocket while attempting to send a file.");
    		e.printStackTrace();
    	}
    	*/
    	openSocket();
    	// Create a file object from the filepath
    			File myFile = new File(filepath);
    			while (true) 
    			{
    				// Accept new connection
    				//mSocket = socket.accept();
    				
    					
    				// Create a byte array for transfer (the size of the file)
    				byte[] mybytearray = new byte[(int) myFile.length()];
    				
    				// Create a BufferedInputStream filled with the data from the file object
    				BufferedInputStream bis = null;
    				try {
    					bis = new BufferedInputStream(new FileInputStream(myFile));
    				} catch (Exception e) {
    					System.out.println("The supplied file does not exist!");
    					e.printStackTrace();
    				}
    				// Read the file byte array to make sure no exceptions are thrown before transfer
    				bis.read(mybytearray, 0, mybytearray.length);
    				
    				// Get the Output connection to the Client
    				//OutputStream os = mSocket.getOutputStream();
    				
    				// Actually SEND the information to the Client
    				mSocketOutput.write(mybytearray, 0, mybytearray.length);
    				// If any bytes remain that have not been set, send them now
    				mSocketOutput.flush();
    				
    				// Close the connection from the Server to the Client
    				bis.close();
    				//socket.close();
    				break;
    			}
    	//closeSocket();
    }
    
    public void getTransfer( String outfile ) throws Exception {
    	
    	// Open the Socket
    	openSocket();
    	
    	// Create a new byte array size of 1KB
		byte[] bytearray = new byte[TRANSFER_SIZE_1KB];

		// Create a bufferedOutputStream from the outfile path
		FileOutputStream fos = new FileOutputStream(outfile);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		
		// TESTING
		fos.toString();
		
		// Initialize values to 0
		int bytesRead = 0;
		int count = 0;
		
		System.out.println("GOT HERE");
		
		// Transfer the 'outfile' 1KB (1024 bytes) increments, each pass is 1KB chunk of files
		while ((bytesRead = mSocketInput.read(bytearray, 0, bytearray.length)) != -1)
		{
			System.out.println("GOT HERE");
			// Send the data to the Server
			bos.write(bytearray, 0, bytesRead);
			// If less than 1KB, finish the transfer using flush()
			bos.flush();
			System.out.println(++count);
		}
		

		
		// Log to console
		System.out.println("Finished transferring " + outfile);
		
		// Close the bufferedOutputStream
		bos.close();

		// Close the socket
		//closeSocket();
	}
    
 
    
    public void sendSomeMessages(int iterations) 
    {
    	// Open the Socket
    	openSocket();

        byte[] buf = new byte[data.length];
        int bytes_read = 0;
        for(int x=1; x<=iterations; x++) 
        {
            try 
            {
                mSocketOutput.write(data, 0, data.length); 
                bytes_read = mSocketInput.read(buf, 0, buf.length);
            }
            catch (IOException e)
            {
                e.printStackTrace(System.err);
            }
            if( bytes_read != data.length ) 
            {
                System.out.println("run: Sent "+ data.length +" bytes, server should have sent them back, read "+bytes_read+" bytes, not the same number of bytes.");
            }
            else 
            {
                System.out.println("Client Sent "+bytes_read+" bytes to server and received them back again, msg = "+(new String(data)));
            }

            // Sleep for a bit so the action doesn't happen to fast - 
            // this is purely for reasons of demonstration, and not required technically.
            try { Thread.sleep(50);}
            catch (Exception e) {}; 
        }
        System.err.println("Done reading/writing to/from socket, closing socket.");

        // Close the Socket
        //;
    }
    
    public void sendMessage(String str) {
    	// Open the Socket
    	//openSocket();

    	// Convert the String to a Byte Array
    	byte[] data = str.getBytes();
    	
    	// Create a buffer object the size of the data byte array
        byte[] buf = new byte[data.length];
        int bytes_read = 0;
        
        // Attempt to send the data over the network
            try 
            {
                mSocketOutput.write(data, 0, data.length); 
                bytes_read = mSocketInput.read(buf, 0, buf.length);
            }
            catch (IOException e)
            {
                e.printStackTrace(System.err);
            }
            if( bytes_read != data.length ) 
            {
                System.out.println("run: Sent "+ data.length +" bytes, server should have sent them back, read "+bytes_read+" bytes, not the same number of bytes.");
            }
            else 
            {
                System.out.println("Client Sent "+bytes_read+" bytes to server and received them back again, msg = "+(new String(data)));
            }

            // Sleep for a bit so the action doesn't happen to fast - 
            // this is purely for reasons of demonstration, and not required technically.
            try { Thread.sleep(50);}
            catch (Exception e) {}; 

        System.err.println("Done reading/writing to/from socket, closing socket.");

        // Close the Socket
        //closeSocket();
    }
    
    public void openSocket() {
    	// Open the Socket
        System.err.println("Opening connection to "+mServerHostname+" port "+mServerPort);
        try 
        {
            mSocket = new Socket(mServerHostname, mServerPort);
            mSocketInput = mSocket.getInputStream();
            mSocketOutput = mSocket.getOutputStream();
        }
        catch (IOException e)
        {
            e.printStackTrace(System.err);
            return;
        }

        System.err.println("About to start reading/writing to/from socket.");
    }
    
    public void closeSocket() {
    	// Close the Socket
        try 
        {
            mSocket.close();
        }
        catch (IOException e)
        {
            System.err.println("Exception closing socket.");
            e.printStackTrace(System.err);
        }
        System.err.println("Exiting.");
    }

    
    // GETTERS & SETTERS
	public String getServerHostname() {
		return mServerHostname;
	}



	public void setServerHostname(String serverHostname) {
		this.mServerHostname = mServerHostname;
	}



	public int getServerPort() {
		return mServerPort;
	}



	public void setServerPort(int serverPort) {
		this.mServerPort = mServerPort;
	}



	public Socket getSocket() {
		return mSocket;
	}



	public void setSocket(Socket socket) {
		this.mSocket = mSocket;
	}



	public InputStream getInputStream() {
		return mSocketInput;
	}



	public void setInputStream(InputStream socketInput) {
		this.mSocketInput = mSocketInput;
	}



	public OutputStream getOutputStream() {
		if (mSocketOutput == null) {
			// Connect to the OutputStream
			
		}
		return mSocketOutput;
	}



	public void setOutputStream(OutputStream socketOutput) {
		this.mSocketOutput = mSocketOutput;
	}
   
	
}

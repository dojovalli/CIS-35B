
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.Socket;

// client
public class FileTransferClient 
{
	Socket socket = null;
	
	public void recieveTransfer(String host, int port, String outfile ) throws Exception 
	{
		// Create a Socket Connection to the Server
		socket = new Socket(host, port);
		// Create a new byte array
		byte[] bytearray = new byte[1024];
		
		// Get a connection to the Output from the Server
		InputStream is = socket.getInputStream();
		
		// Create a FileOutputStream to create a file on the Client Machine
		FileOutputStream fos = new FileOutputStream(outfile);
		
		// Create a BufferedOutputStream that will handle writing the file to the machine
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		
		// Declare vars
		int bytesRead = 0;
		int count = 0;
		
		// Begin reading the data from the Server
		while ((bytesRead = is.read(bytearray, 0, bytearray.length)) != -1)
		{
			// Save the data on the Client Machine
			bos.write(bytearray, 0, bytesRead);
			bos.flush();
			System.out.println(++count);
		}
		// Close the OutputStream for writing to the Client Computer
		bos.close();
		// Close the connection to the Server
		socket.close();
	}
	
	/* EXAMPLE USAGE *
	public static void main(String[] argv) throws Exception 
	{
		FileTransferClient xfer = new FileTransferClient();
		xfer.recieveTransfer("localhost", 1234, "/Users/admin/Desktop/output.png");
	}
	*/
}
	
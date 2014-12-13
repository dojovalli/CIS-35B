
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

// server
public class FileTransferServer 
{
	ServerSocket servsock = null;
	Socket socket = null;
	
	public void sendTransfer(int port,String sfile) throws IOException {
		servsock = new ServerSocket(port);
		
		// Create a file object from the filepath
		File myFile = new File(sfile);
		while (true) 
		{
			// Accept new connection
			socket = servsock.accept();
			
			// Create a byte array for transfer (the size of the file)
			byte[] mybytearray = new byte[(int) myFile.length()];
			
			// Create a BufferedInputStream filled with the data from the file object
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile));
			// Read the file byte array to make sure no exceptions are thrown before transfer
			bis.read(mybytearray, 0, mybytearray.length);
			
			// Get the Output connection to the Client
			OutputStream os = socket.getOutputStream();
			
			// Actually SEND the information to the Client
			os.write(mybytearray, 0, mybytearray.length);
			// If any bytes remain that have not been set, send them now
			os.flush();
			
			// Close the connection from the Server to the Client
			socket.close();
		}
	}
	
	/*
	public static void main(String[] args) throws IOException 
	{
		// Create a FileTransfer object (Default Constructor does nothing but reserve memory)
		FileTransferServer xfer = new FileTransferServer();
		// Transfer the file on the supplied port
		xfer.sendTransfer(1234,"/Users/admin/Desktop/musicShot.png");
	}
	*/
}

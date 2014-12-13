import java.io.*;


import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*; 

public class XMLWriter
{ 	
	// Members
	private Document mDocumentForOutput;
	private String mInFilePath;
	private String mOutFilePath;
	
	// CONSTRUCTORS
	public XMLWriter(String filePathForConversion, String filePathForOutput) {
		mInFilePath = filePathForConversion;
		mOutFilePath = filePathForOutput;
		try {
			mDocumentForOutput = createDocument();
			} catch (Exception e) {
				System.out.println("Exception: ");
				e.printStackTrace();
			}
	}
	
	public Document createDocument() throws IOException, ParserConfigurationException
	{
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		return doc;
	}
	
	public void processDocument(String delimeter) throws Exception
	{
		// Get a File object from the InPath
		File inFile  = new File(mInFilePath);
		
		// Create a BufferedReader from the file
        BufferedReader reader = new BufferedReader(new FileReader(inFile));
        
        // Read a line from the file
        String sline = reader.readLine();
        
        //
        String[] sheaders = sline.split(delimeter);
        sline = reader.readLine();
        String[] snodes = sline.split(delimeter);
		Element aroot;
		Element achild;
		aroot = mDocumentForOutput.createElement(sheaders[0].trim());
		mDocumentForOutput.appendChild(aroot);
        while ((sline=reader.readLine()) != null) 
        {    		
        	achild = mDocumentForOutput.createElement(sheaders[1].trim());
        	String[] sdata = sline.split(delimeter);
        	for (int x=0; x<snodes.length; ++x)
        	{
				Element c = mDocumentForOutput.createElement(snodes[x].trim());  
				c.appendChild(mDocumentForOutput.createTextNode(sdata[x].trim()));
				achild.appendChild(c);
        	}
        	aroot.appendChild(achild);
        }		
	}
	
	public void createXML() throws Exception
	{
		//TransformerFactory instance is used to create Transformer objects. 
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		
		// create string from xml tree
		StringWriter sw = new StringWriter();
		StreamResult result = new StreamResult(sw);
		DOMSource source = new DOMSource(mDocumentForOutput);
		transformer.transform(source, result);
		String xmlString = sw.toString();
		File file = new File(mOutFilePath);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
		bw.write(xmlString);
		bw.flush();
		bw.close();		
	}

	public void createXmlDocument(String delimeter) throws Exception 
	{
		processDocument(delimeter);
		createXML();
	}
	
	public void csvToXml() {
		try {
			createXmlDocument(",");
		} catch (Exception e) {
			System.out.print("Exception: ");
			e.printStackTrace();
		}
	}
	
	public static void testWriter(String csvFile)
	{
		try
		{
			try {
			XMLWriter xmlWriter = new XMLWriter(csvFile, "Output.xml");
			xmlWriter.csvToXml();
			} catch (Exception e) {
				System.out.println("'" + csvFile + "' does not exist in the given directory! Please check that you have properly entered your filepath");
			}
			System.out.println("<b>Xml File Created Successfully</b>");
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}
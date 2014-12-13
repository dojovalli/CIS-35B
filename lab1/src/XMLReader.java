import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLReader 
{
	// MEMBERS
	private String mFilename;
	private Document mXMLDocument;
	private NodeList mNodeList;
	private String mObjectNode;
	private String[] mFields;
	
	// CONSTRUCTORS
	public XMLReader(String xmlFileName, String objectNode, String[] objectFields) {
		mFilename = xmlFileName;
		// Try and set the Document from the Filename
		try {
			// Create a Document object from the provided filepath
			mXMLDocument = readXML();
			
			// Set the Key Node String and its sub-Node Strings
			mObjectNode = objectNode;
			mFields = objectFields;
			
			// Get the Nodes by Tag Name
			mNodeList = getNodes();
			
		} catch (Exception e) {
			System.out.println("Excepetion: ");
			e.printStackTrace();
		}
	}
	
	// CONVENIENCE METHODS
	public Document readXML() throws ParserConfigurationException, SAXException, IOException
	{
		File file = new File(mFilename);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		mXMLDocument = db.parse(file);
		return mXMLDocument; 
	}
	
	public NodeList getNodes()
	{
		mXMLDocument.getDocumentElement().normalize();
		NodeList nodelist = mXMLDocument.getElementsByTagName(mObjectNode);
		return nodelist;
	}
	
	public Node getNode(int index)
	{	
		Node node = null;
		int length = mNodeList.getLength();
		if (index > -1 && index < length)
			node = mNodeList.item(index);
		return node;
	}
	
	public ArrayList<String> getNodeStrings()
	{
		ArrayList<String> slist = new ArrayList<String>();
        for(int s=0; s<mNodeList.getLength() ; s++)
        {
            Node firstNode = getNode(s);
            if(firstNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element element = (Element)firstNode;
                for (int x=0; x<mFields.length; x++)
                {
                    NodeList nlist = element.getElementsByTagName(mFields[x]);
                    Element item = (Element)nlist.item(0);
                    NodeList tlist = item.getChildNodes();
                    slist.add(((Node)tlist.item(0)).getNodeValue().trim());
                }
            }
        }
        return slist;
	}
	
	public ArrayList<HashMap<String,String>> getNodeHashMaps()
	{
		// Create a new ArrayList of HashMap<String,String> to store HashMaps representing individual Nodes
		ArrayList<HashMap<String,String>> objectList = new ArrayList<HashMap<String,String>>();
		
		// Loop through the NodeList representing an Entire XML file
		// where each Node is defined by the Constructor 'Element' value
        for(int s=0; s<mNodeList.getLength() ; s++)
        {
        	// Create a New HashMap<String,String> for each Node
        	HashMap<String,String> nodeMap = new HashMap<String,String>();
        	
        	// Get the Node representing the current index
            Node firstNode = getNode(s);
            if(firstNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element element = (Element)firstNode;
                for (int x=0; x<mFields.length; x++)
                {
                	// Get a 'sub-element' or 'internal node' by tag name 
                    NodeList nlist = element.getElementsByTagName(mFields[x]);
                    Element item = (Element)nlist.item(0);
                    NodeList tlist = item.getChildNodes();
                    
                    // Get the nodeValue at the element string stored in selements[x]
                    String nodeValue = ((Node)tlist.item(0)).getNodeValue().trim();
                    
                    // add the Key/Value pair to the map
                    nodeMap.put(mFields[x], nodeValue);
                }
            }
            
            // Add the nodeMap for the current pass (represents an instance of the given node)
            objectList.add(nodeMap);
        }
        return objectList;
	}
	

	public static boolean TestXML(String sfile,String selement,String[] sfields) 
	throws ParserConfigurationException, SAXException, IOException
	{
		boolean breturn = false;
		try
		{
			XMLReader xmlFile = new XMLReader(sfile, selement, sfields);
			ArrayList<String> slist = xmlFile.getNodeStrings();
			for (String s : slist)
				System.out.println(s);
		}
		finally 
		{
			//System.out.println("Error in xml file: " + sfile);
		}
		breturn = true;
		return breturn;
	}

	public String getObjectNode() {
		return mObjectNode;
	}

	public String[] getFields() {
		return mFields;
	}

	
	// GETTERS & SETTERS
	
}


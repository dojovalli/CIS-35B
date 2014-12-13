import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;


public class CartesianGraphViewController {
	
	// DEBUG
	private static final boolean DEBUG = true;
	private static final boolean DEBUG_SAMPLE_CART_COORDS = false;
	
	// CONSTANTS
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 700;
	
	// MEMBERS
		// Model
		ArrayList<CartesianCoordinate> mCartesianCoords;
		// UI
			// High Level Containers
			private JFrame mainFrame;
			private CartesianGraphPanel graph;
			private JPanel table;
			private JPanel input;
			// Components
			private JTextField inputFilePath;
			private JButton executeButton;
			JScrollPane tablePane;
			private JTable coordinateTable;
	
	// CONSTRUCTORS
	public CartesianGraphViewController() {
		// Empty Constructor
	}
	
	// LIFE-CYCLE METHODS
	public void onCreate() {
		// Setup Default Coords
		mCartesianCoords = sampleCartesianCoords(-10, 15);
		// Create the JFrame
		mainFrame = new JFrame("Cartesian Grapher");
		mainFrame.setSize(WIDTH, HEIGHT);
		
		
		// Setup the UI
		setupUI();
		
		mainFrame.pack();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}
	
	public void setupUI() {
		
		
		// Create the Panels
		setupInputPanel();
		setupTablePanel();
		setupGraphPanel();
		
		// Add the Panels to the JFrame
		mainFrame.add(input, BorderLayout.PAGE_START);
		mainFrame.add(table, BorderLayout.WEST);
		mainFrame.add(graph, BorderLayout.CENTER);
		
	}
	
	public void setupInputPanel() {
		// Create a JPanel
		input = new JPanel();
		
		// Create the Panels components
		inputFilePath = new JTextField("Input a filepath to Value Set...");
		executeButton = new JButton("Load Graph");
		setupExecuteClickListener();
		// TODO - SET THE BUTTON'S CLICK LISTENER
		//executeButton.set
		
		// Add the components to the JPanel
		input.add(inputFilePath);
		input.add(executeButton);
	}
	
	public void setupTablePanel() {
		// Create a JPanel
		table = new JPanel();
		
		// Update the Table
		updateTable();
		
		// Add the components to the JPanel
		table.add(tablePane);
		
	}
	
	public void setupGraphPanel() {
		graph = new CartesianGraphPanel(mCartesianCoords, 10, 10, true);
		// Set the Background Color to White
		graph.setBackground(new Color(255,255,255));
	}
	
	public void setupExecuteClickListener() {
		executeButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				System.out.println("Loading New Graph from file...");
				// Get the Text
				String path = inputFilePath.getText();
				System.out.println("Filename: " + path);
				
				// Build a ValueSet of CartesianCoordinates from the File at the filepath
				try {
					// Get an ArrayList<CartesianCoordinate>
					mCartesianCoords = CartesianFileReader.read(path);
					
					// Update the View
					updateGraph();
					updateTable();
					
				} catch (Exception exception) {
					String message = "There was a problem loading the file!\nCheck the filename/filepath.";
					System.out.println(message);
					exception.printStackTrace();
				}
			}
		});
	}
	
	public void updateGraph() {
		System.out.println("Udating the graph...");
		// Remove the previous graph
		mainFrame.remove(graph);
		// Set the ValueSet of the Graph JPanel
		graph = new CartesianGraphPanel(mCartesianCoords,10,10,false);
		// Set the Background Color to White
		graph.setBackground(new Color(255,255,255));
		// Add the new graph
		mainFrame.add(graph, BorderLayout.CENTER);
		// Update the view
		mainFrame.repaint();
		mainFrame.revalidate();
	}
	
	public void updateTable() {
		System.out.println("Udating the table...");
		
		// Remove the Table from the JScrollView
		
		// Setup the fields
		coordinateTable = new JTable(new CartesianTableModel(mCartesianCoords));
		
		// Set Table Settings
		coordinateTable.setFillsViewportHeight(true);
		
		// Remove the Previous Pane
		if (table.getComponentCount() == 1) {
			table.remove(tablePane);
		}
		
		// Create a Scrollable Pane
		tablePane = new JScrollPane(coordinateTable);
		tablePane.setPreferredSize(new Dimension((WIDTH / 5), HEIGHT - 300));
				
		// Add the new pane
		table.add(tablePane);
		
		// Update the view
		mainFrame.repaint();
		mainFrame.revalidate();
	}
	
	
	public static ArrayList<CartesianCoordinate> sampleCartesianCoords(int min, int max) {
		  ArrayList<CartesianCoordinate> sampleCoords = new ArrayList<CartesianCoordinate>();
		  if(DEBUG && DEBUG_SAMPLE_CART_COORDS) System.out.println("Creating Sample Coordinates...");
		  for (int i = min; i < max; i++) {
			  CartesianCoordinate coord = new CartesianCoordinate(i, i);
			  sampleCoords.add(coord);
			  if(DEBUG && DEBUG_SAMPLE_CART_COORDS) System.out.println(coord.toString());
		  }
		  
		  return sampleCoords;
	  }
	
	public static void main(String[] args) throws Exception {
	    // Create a new CartesianGraphViewController
		CartesianGraphViewController controller = new CartesianGraphViewController();
		controller.onCreate();
	}
}

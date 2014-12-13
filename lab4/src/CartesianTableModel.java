import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;


public class CartesianTableModel extends AbstractTableModel {
	
	// DEBUG
	private static final boolean DEBUG = true;
	private static final boolean DEBUG_NODES = true;
	private static final boolean DEBUG_VALUES = false;
	
	// MEMBERS
	private ArrayList<CartesianCoordinate> mCartesianCoords;
    private String[] columnNames = {"X Value", "Y Value"};
    private Object[][] data; /* = {
			{"0","0"},
			{"1","-1"},
			{"2","-2"},
			{"3","-3"}
	};*/
    
    // CONSTRUCTORS
    public CartesianTableModel(ArrayList<CartesianCoordinate> coords) {
    	mCartesianCoords = coords;
    	
    	generateDataSet(mCartesianCoords);
    }

    private void generateDataSet(ArrayList<CartesianCoordinate> cartesianCoords) {
		if (DEBUG && DEBUG_NODES) System.out.println("Enter generateDataSet(cartesianCoords)");
		if (DEBUG && DEBUG_VALUES) System.out.println("cartesianCoords: " + cartesianCoords.toString());
    	
    	if (cartesianCoords != null) {
	    	data = new Object[cartesianCoords.size()][getColumnCount()];
	    	
	    	// Set a dataRow for each Coord
	    	for (int i = 0; i < data.length; i++) {
	    		String[] dataRow = { 	String.valueOf(cartesianCoords.get(i).getX()), 
	    								String.valueOf(cartesianCoords.get(i).getY()) };
	    		data[i] = dataRow;
	    	}
    	} else {
    		data = new Object[1][2];
    		data[0][0] = "Empty Array";
    		data[0][1] = "Empty Array";
    	}
	}

	public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        if (col < 2) {
            return false;
        } else {
            return true;
        }
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
	    
}



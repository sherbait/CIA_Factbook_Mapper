package dbf_editor;

import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * A representation of .dbf files in table format. This class extends
 * <CODE>JTable</CODE> to display uneditable fields (columns) and
 * records (rows).
 * 
 * @author Dinia Gepte
 *
 */
public class DBFEditor extends JTable
{
	private JScrollPane jsp;
	
	/**
	 * Sole constructor that creates an default <CODE>JTable</CODE> in
	 * a <CODE>JScrollPane</CODE>.
	 */
	public DBFEditor()
	{
		super();
		layoutGUI();
	}
	
	/**
	 * Returns the <CODE>JScrollPane</CODE> containing the <CODE>JTable</CODE>
	 * of this class.
	 * 
	 * @return
	 * 	 the <CODE>JScrollPane</CODE> containing this class.
	 */
	public JScrollPane getJScrollPane()			{		return jsp;		}
	
	
	public boolean isCellEditable(int row, int column)
	{
		return false;
	}
	
	private void layoutGUI()
	{
		// THIS WILL RENDER OUR SPREADSHEET
		Font tableFont = new Font("Arial", Font.PLAIN, 14);
		setFont(tableFont);
		setRowHeight(25);
		Font headerFont = new Font("Verdana", Font.BOLD, 16);
		getTableHeader().setFont(headerFont);
		getTableHeader().setReorderingAllowed(false);
		setColumnSelectionAllowed(false);
		setRowSelectionAllowed(false);

		// THIS WILL HOLD THE DATA IN OUR SPREADSHEET
		DefaultTableModel tableModel = new DefaultTableModel();
		setModel(tableModel);
			
		// WE WANT THE TABLE TO SCROLL IF NECESSARY
		jsp = new JScrollPane(this);
	}
}

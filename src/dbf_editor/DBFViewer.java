package dbf_editor;

import java.awt.Component;
import java.util.Iterator;

import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import dbf_editor.DBFEditor;
import dbf_editor.data.DBFField;
import dbf_editor.data.DBFRecord;
import dbf_editor.data.DBFTable;

/**
 * A class containing .dbf records and fields
 * (see {@link DBFTableManager}) and the <CODE>JTable</CODE>
 * (see {@link DBFEditor}) representing said data.
 * This class provides a method that loads a {@link DBFTable} into
 * the <CODE>JTable</CODE>.
 * 
 * @author Dinia Gepte
 *
 */
public class DBFViewer
{
	private DBFEditor editor;
	private DBFTableManager tableManager;
	
	/**
	 * Class constructor that creates a new <CODE>DBFEditor</CODE> but leaves
	 * the <CODE>DBFTableManager</CODE> null. 
	 */
	public DBFViewer()
	{
		editor = new DBFEditor();
		tableManager = null;
	}
	
	// ACCESSOR METHODS
	/**
	 * Returns the class managing the .dbf data records and fields.
	 *  
	 * @return
	 *   the <CODE>DBFTableManager</CODE> of this class.
	 */
	public DBFTableManager getTableManager()		{	return tableManager;	}
	
	/**
	 * Returns the <CODE>JTable</CODE> representing the .dbf data records
	 * and fields.
	 * 
	 * @return
	 *   the table representing the .dbf data records and fields.
	 */
	public DBFEditor getDBFEditor()					{	return editor;			}
	
	// MUTATOR METHODS
	/**
	 * Sets the .dbf data manager used by this class. The constructor of this
	 * class does not initialize the <CODE>DBFTableManager</CODE>.
	 * 
	 * @param manager
	 *   - the data manager to be used by this class
	 */
	public void setDBFTableManager(DBFTableManager manager)	{	tableManager = manager;	}
	
	/**
	 * This method loads the contents of the data argument, which is a fully
	 * loaded .dbf file, into the <CODE>JTable</CODE> so that the user can
	 * view it.
	 * 
	 * @param data
	 *   - a fully loaded .dbf file to be put into the <CODE>DBFEditor</CODE>
	 */
	public void loadDBFIntoTable(DBFTable data)
	{
		DefaultTableModel tableModel = new DefaultTableModel();
		JTable table = editor;
		
		// RESET THE TABLE'S DATA
		table.setModel(tableModel);
		
		// GET THE COLUMN HEADERS ONE BY ONE
		Iterator<DBFField> fieldsIt = data.fieldsIterator();
		while (fieldsIt.hasNext())
		{
			DBFField field = fieldsIt.next();
			String name = field.getName();
			tableModel.addColumn(name);
		}
		
		// GET THE ROWS OF DATA ONE BY ONE
		Iterator<DBFRecord> recordsIt = data.recordsIterator();
		while(recordsIt.hasNext())
		{
			DBFRecord record = recordsIt.next();
			tableModel.addRow(record.getAllData());
		}

		// RESIZE THE COLUMNS TO FIT THE DATA
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		autoSizeAllColumns();
	}
	
	/**
	 * This helper method automatically resizes all the table's
	 * columns according to how wide the data inside is such
	 * that the user may read all the data.
	 */
	private void autoSizeAllColumns() 
	{
		JTable table = editor;
	    for (int i = 0; i < table.getColumnCount(); i++) 
	    {
	        autoSizeColumn(i);
	    }
	}
	
	/**
	 * This method resizes the columnIndex column according to
	 * the with needed by its contents.
	 */
	private void autoSizeColumn(int columnIndex) 
	{
		// GET THE COLUMN DATA
		JTable table = editor;
	    DefaultTableColumnModel columnModel = (DefaultTableColumnModel)table.getColumnModel();
	    TableColumn column = columnModel.getColumn(columnIndex);
	    int columnWidth = 0;

	    // AND GET THE RENDERER FOR THIS COLUMN
	    TableCellRenderer renderer = column.getHeaderRenderer();
	    if (renderer == null) 
	    {
	        renderer = table.getTableHeader().getDefaultRenderer();
	    }
	    Component component = renderer.getTableCellRendererComponent(table, column.getHeaderValue(), false, false, 0, 0);
	    columnWidth = component.getPreferredSize().width;

	    // FIND THE MAXIMUM WIDTH PIECE OF DATA, WE'LL MAKE THAT OUR WIDTH
	    for (int i = 0; i < table.getRowCount(); i++) 
	    {
	        renderer = table.getCellRenderer(i, columnIndex);
	        component = renderer.getTableCellRendererComponent(table, table.getValueAt(i, columnIndex), false, false, i, columnIndex);
	        columnWidth = Math.max(columnWidth, component.getPreferredSize().width);
	    }

	    // PADDING
	    columnWidth *= 2;

	    // AND SET THE PARAMETERS FOR THIS COLUMN
	    column.setWidth(columnWidth);
	    column.setPreferredWidth(columnWidth);
	}
}

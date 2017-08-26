package dbf_editor.data;

/**
 * A class stores all of the data for a single row in a .dbf file for
 * a {@link DBFTable}.
 * 
 * @author Richard McKenna
 */
public class DBFRecord
{
	// WHAT IS THIS BYTE OF DATA FROM THE FILE BEFORE EACH RECORD?
	private byte mystery;
	
	// HERE'S ALL THE DATA FOR THIS ROW
	private Object[] fieldData;

	/**
	 * This constructor initializes our array for all the data depending on how
	 * many columns we will have.
	 * 
	 * @param numFields
	 *   - the number of columns for each <CODE>DBFRecord</CODE>
	 */
	public DBFRecord(int numFields)
	{
		fieldData = new Object[numFields];
		for (int i = 0; i < fieldData.length; i++)
			fieldData[i] = null;
	}
	
	// ACCESSOR METHODS
	public int 		getNumFields() 		{ return fieldData.length;	}	
	public Object[] getAllData()		{ return fieldData; 		}
	public byte 	getMystery()		{ return mystery;			}
	public Object 	getData(int index) 	{ return fieldData[index];	}

	// MUTATOR METHODS
	public void setData(Object data, int index) { fieldData[index] = data;	}
	public void setMystery(byte initMystery)	{ mystery = initMystery;	}

	/**
	 * Used for updating this record whenever a field
	 * is added to the table. When that happens, we have to increase our
	 * array of data by one to accommodate it.
	 */
	public void addField()
	{
		Object[] updatedArray = new Object[fieldData.length + 1];
		for (int i = 0; i < fieldData.length; i++)
			updatedArray[i] = fieldData[i];
		fieldData = updatedArray;
	}
}

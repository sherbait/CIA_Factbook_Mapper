package dbf_editor.data;

import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Vector;

import dbf_editor.data.DBFField;
import dbf_editor.data.DBFRecord;

/**
 * Objects of this class would be used to store all of the data
 * found in a single .dbf file. Note that the columns data would be stored
 * in fields and records, just as in a database table.
 * 
 * @author Richard McKenna
 */
public class DBFTable 
{
	// COLUMN HEADERS
	private Vector<DBFField> fields;
	
	// ROW DATA
	private Vector<DBFRecord> records;

	// DBF FILE DESCRIPTION
	private byte fileType;
	private GregorianCalendar lastModifiedDate;
	private int numberOfRecords;
	private short positionOfFirstDataRecorded;
	private short dataRecordLength;
	private short zeroes;
	private byte dbaseTransactionFlag;
	private byte dbaseEncryptionFlag;
	private int[] mup;
	private byte flags;
	private byte codePageMark;
	private short reserved;
	private byte terminator;

	/**
	 * This default constructor simply sets up the fields and records vectors.
	 */
	public DBFTable()
	{
		fields = new Vector<DBFField>();
		records = new Vector<DBFRecord>();
	}

	// ACCESSOR METHODS
	public byte 				getFileType() 						{ return fileType; 						}
	public GregorianCalendar	getLastModifiedDate() 				{ return lastModifiedDate; 				}
	public int 					getNumberOfRecords() 				{ return numberOfRecords; 				}
	public short 				getPositionOfFirstDataRecorded()	{ return positionOfFirstDataRecorded;	}
	public short 				getDataRecordLength()				{ return dataRecordLength;				}
	public short 				getZeroes()							{ return zeroes;						}
	public byte 				getDbaseTransactionFlag()			{ return dbaseTransactionFlag;			}
	public byte 				getDbaseEncryptionFlag()			{ return dbaseEncryptionFlag;			}
	public int[] 				getMup()							{ return mup;							}
	public byte 				getFlags()							{ return flags;							}
	public byte					getCodePageMark()					{ return codePageMark;					}
	public short				getReserved()						{ return reserved;						}
	public byte					getTerminator()						{ return terminator;					}
	public DBFField 			getField(int index)					{ return fields.get(index); 			}
	public DBFRecord			getRecord(int index)				{ return records.get(index);			}
	public int 					getNumFields()						{ return fields.size();					}
	public int 					getNumRecords()						{ return records.size();				}

	/**
	 * This accessor method gets the index of a specific column with a header
	 * name equivalent to that of the <CODE>testFieldName</CODE> index. If no
	 * column is found with that name, -1 is returned.
	 * 
	 * @param testFieldName
	 *   - name of the header to find the index number
	 * @return
	 *   the index of the given <CODE>testFieldName</CODE>. -1 if no such
	 *   field name exists.
	 */
	public int getFieldIndex(String testFieldName)
	{
		int index = 0;
		Iterator<DBFField> fieldsIt = fieldsIterator();
		while(fieldsIt.hasNext())
		{
			DBFField field = fieldsIt.next();
			if (field.getName().equals(testFieldName))
				return index;
			else
				index++;
		}
		return -1;
	}	
	
	// ITERATOR METHODS - THESE WILL RETURN ITERATORS FOR GOING THROUGH ALL THE DATA IN THE TABLE
	public Iterator<DBFField> 	fieldsIterator() 	{ return fields.iterator(); 	}
	public Iterator<DBFRecord> 	recordsIterator()	{ return records.iterator(); 	}
	
	// MUTATOR METHODS	
	public void addField(DBFField dbf)			{ fields.addElement(dbf);		}
	public void addRecord(DBFRecord dbr)		{ records.addElement(dbr);		}
	
	public void setFileType(byte initFileType)
	{
		fileType = initFileType;
	}
	
	public void setLastModifiedDate(int year, int month, int day)
	{
		lastModifiedDate = new GregorianCalendar(year, month, day);
	}
	
	public void setNumberOfRecords(int initNumberOfRecords)
	{
		numberOfRecords = initNumberOfRecords;
	}
	
	public void setPositionOfFirstDataRecorded(short initPositionOfFirstDataRecorded)
	{
		positionOfFirstDataRecorded = initPositionOfFirstDataRecorded;
	}

	public void setDataRecordLength(short initDataRecordLength)
	{
		dataRecordLength = initDataRecordLength;
	}
	
	public void setZeroes(short initZeroes)
	{
		zeroes = initZeroes;
	}
	
	public void setDbaseTransactionFlag(byte initDbaseTransactionFlag)
	{
		dbaseTransactionFlag = initDbaseTransactionFlag;
	}

	public void setDbaseEncryptionFlag(byte initDbaseEncryptionFlag)
	{
		dbaseEncryptionFlag = initDbaseEncryptionFlag;
	}
	
	public void setMup(int[] initMup)
	{
		mup = initMup;
	}
	
	public void setFlags(byte initFlags)
	{
		flags = initFlags;
	}
	
	public void setCodePageMark(byte initCodePageMark)
	{
		codePageMark = initCodePageMark;
	}
	
	public void setReserved(short initReserved)
	{
		reserved = initReserved;
	}
	
	public void setTerminator(byte initTerminator)
	{
		terminator = initTerminator;
	}
	
	/**
	 * Adds a new field to the table. This would be used
	 * during table editing. Note that all records in the table
	 * are updated by this method to accommodate the new field.
	 * 
	 * @param fieldName
	 *   - the column header to be added
	 * @param fieldType
	 *   - <CODE>C</CODE> or <CODE>N</CODE> value that determines the
	 *   data type of the field to add
	 * @param length
	 *   - the maximum number of characters allowed to store into each row
	 */
	public void addField(String fieldName, char fieldType, int length)
	{
		// MAKE OUR NEW FIELD
		DBFField fieldToAdd = new DBFField();
		fieldToAdd.setName(fieldName);
		fieldToAdd.setType(fieldType);
		fieldToAdd.setLength(length);
		
		// PUT IT IN THE TABLE
		addField(fieldToAdd);
		
		// AND UPDATE ALL THE RECORDS
		Iterator<DBFRecord> it = recordsIterator();
		while(it.hasNext())
		{
			DBFRecord rec = it.next();
			rec.addField();
		}
		
		// UPDATE TABLE STATS
		update();
	}
	
	/**
	 * Updates all the table statistics important for saving
	 * a .dbf file. This method should be called whenever the table
	 * structure is changed. 
	 */
	public void update()
	{
		lastModifiedDate = new GregorianCalendar();
		updateDataRecordLength();
		updatePositionOfFirstDataRecorded();
	}
	
	/**
	 * Determines the length of all the fields in the 
	 * table and sums these values, then using this result to set
	 * the <CODE>dataRecordLength</CODE> variable,
	 * which may later be written to a .dbf file.
	 */
	public void updateDataRecordLength()
	{
		Iterator<DBFField> it = fields.iterator();
		dataRecordLength = 0;
		while(it.hasNext())
		{
			DBFField field = it.next();
			dataRecordLength += field.getLength();
		}
		dataRecordLength++;
	}
	
	/**
	 * Determines the position of the first record
	 * inside a .dbf table if the current table being edited were
	 * to be saved. This value is assigned to the 
	 * <CODE>positionOfFirstDatRecorded</CODE>
	 * variable, which is written to a .dbf file during saving.
	 */
	public void updatePositionOfFirstDataRecorded()
	{
		positionOfFirstDataRecorded = (short)(32 + (32 * fields.size()) + 1);
	}
	
	/**
	 * Sorts the records in this table according to the
	 * provided <CODE>fieldName</CODE> and in increasing order
	 * if the increasing argument is <CODE>true</CODE>, in
	 * decreasing order otherwise.
	 * 
	 * @param fieldName
	 *   - field to sort
	 * @param increasing
	 *   - how the field is sorted
	 */
	public void sortRecords(String fieldName, boolean increasing)
	{
		// FIND THE SORTING CRITERIA INDEX
		int fieldIndex = -1;
		int index = 0;
		while ((fieldIndex < 0) && (index < fields.size()))
		{
			DBFField testField = fields.get(index);
			if (testField.getName().equals(fieldName))
				fieldIndex = index;
			index++;
		}

		// ONLY SORT IF IT'S FOUND
		if (fieldIndex >= 0)
		{
			DBFRowSorter rowSorter = new DBFRowSorter(fieldIndex, increasing);
			Collections.sort(records, rowSorter);
			
			// UPDATE THE TABLE STATS
			update();
		}
	}
	
	/**
	 * A helper class that performs all comparisons between
	 * objects during table sorting.
	 */
	private class DBFRowSorter implements Comparator
	{
		// THESE DICTATE THE SORTING CRITERIA
		private int sortingIndex;
		private boolean increasing;

		/**
		 * This constructor sets up this object for sorting.
		 */
		public DBFRowSorter(int initSortingIndex, boolean initIncreasing)
		{
			sortingIndex = initSortingIndex;
			increasing = initIncreasing;
		}

		/**
		 * Compares the two <CODE>Object</CODE> arguments and returns
		 * 0 if they are equivalent. Returns -1 if <CODE>obj1</CODE> is
		 * "smaller" than <CODE>obj2</CODE>, and 1 otherwise. 
		 */
		public int compare(Object obj1, Object obj2) 
		{
			DBFRecord record1 = (DBFRecord)obj1;
			DBFRecord record2 = (DBFRecord)obj2;
			int result;

			// ARE WE COMPARING TEXT?
			if (record1.getData(sortingIndex) instanceof String)
			{
				String text1 = (String)record1.getData(sortingIndex);
				String text2 = (String)record2.getData(sortingIndex);
				result = text2.compareTo(text1);
			}
			// TREAT EVERYTHING ELSE AS A DOUBLE
			else
			{
				Double double1 = 0.0;
				Double double2 = 0.0;
				if (record1.getData(sortingIndex) instanceof Double)
				{
					double1 = (Double)record1.getData(sortingIndex);
					if (record2.getData(sortingIndex) instanceof Long)
					{
						Long tempLong = (Long)record2.getData(sortingIndex);
						double2 = (double)tempLong;
					}
					else
					{
						double2 = (Double)record2.getData(sortingIndex);						
					}
				}
				// EVEN IF IT'S ACTUALLY A LONG, IT MAKES COMPARISONS EASIER
				else
				{
					Long tempLong = (Long)record1.getData(sortingIndex);
					if (tempLong == null)
						double1 = new Double(0);
					else
						double1 = (double)tempLong;
					if (record2.getData(sortingIndex) instanceof Long)
					{
						tempLong = (Long)record2.getData(sortingIndex);
						if (tempLong == null)
							double2 = new Double(0);
						else
							double2 = (double)tempLong;
					}
					else
					{
						Double tempDouble = (Double)record2.getData(sortingIndex);
						if (tempDouble == null)
							double2 = new Double(0);
						else
							double2 = (Double)record2.getData(sortingIndex);
					}
				}
				if (double1 == null) double1 = 0.0;
				if (double2 == null) double2 = 0.0;
				if (double1 < double2) result = 1;
				else if (double1 > double2) result = -1;
				else result = 0;
			}
			// IF INCREASING, JUST REVERSE THE RESULTS
			if (increasing)
				result *= -1;
			return result;
		}	
	}
	
	/**
	 * Checks to see if there is already a field name in
	 * the table that is equivalent to the <CODE>testName</CODE>
	 * argument. If there is, <CODE>true</CODE> is returned,
	 * else <CODE>false</CODE>. Note that no two columns should
	 * have the same name in a table, so this method should be used
	 * to help prevent that from happening.
	 * 
	 * @param testName
	 *   - the header name to find in a <CODE>DBFTable</CODE>
	 * @return
	 *   <CODE>true</CODE> if the given parameter is found as one
	 *   of the headers, otherwise <CODE>false</CODE>.
	 */
	public boolean containsNamedColumn(String testName)
	{
		// SEARCH THROUGH ALL THE FIELDS
		Iterator<DBFField> it = fieldsIterator();
		while (it.hasNext())
		{
			DBFField field = it.next();
			if (field.getName().equals(testName))
			{
				return true;
			}
		}
		return false;
	}
}
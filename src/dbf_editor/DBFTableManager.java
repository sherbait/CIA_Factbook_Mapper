package dbf_editor;

import java.awt.Image;

import dbf_editor.data.DBFTable;

/**
 * Stores a {@link DBFTable} and provide methods to manipulate fields
 * and records in it. It also stores the <CODE>DBFTable</CODE> accompanying
 * the table describing the fields. Changes made by this class to the
 * <CODE>DBFTable</CODE> are always loaded into the <CODE>JTable</CODE>,
 * that is the {@link DBFViewer}, representing the table.
 * 
 * @author Dinia Gepte
 *
 */
public class DBFTableManager
{
	private DBFTable table;
	private DBFTable fieldDescriptions;
	private String fieldToSort;
	
	/**
	 * Class constructor initializing the <CODE>DBFTable</CODE> containing
	 * the fields and records and respective <CODE>DBFTable</CODE> field
	 * descriptions.
	 * 
	 * @param initTable
	 * 	 - the table containing the .dbf data
	 * @param initFieldDescriptions
	 * 	 - the table containing the field descriptions of the
	 * 	   <CODE>initTable</CODE>
	 */
	public DBFTableManager(DBFTable initTable, DBFTable initFieldDescriptions)
	{
		table = initTable;
		fieldDescriptions = initFieldDescriptions;
	}
	
	// ACCESSOR METHODS
	/**
	 * Returns the <CODE>DBFTable</CODE> containing the fields and records of
	 * the main .dbf data.
	 * @return
	 * 	 the table mainly represented by this class.
	 */
	public DBFTable getTable()
	{
		return table;
	}
	
	/**
	 * Returns a detailed description of the given field name.
	 * 
	 * @param fieldName
	 * 	 - the field requiring the field description
	 * @return
	 *   the <CODE>String</CODE> describing the parameter.
	 */
	public String getLongDescription(String fieldName)
	{
		//TODO
		return null;
	}
	
	/**
	 * Returns a short summary of the given field name.
	 * 
	 * @param fieldName
	 * 	 - the field requiring the field description.
	 * @return
	 * 	 the <CODE>String</CODE> describing the parameter.
	 */
	public String getShortDescription(String fieldName)
	{
		return fieldName;
		
	}
	
	/**
	 * Returns the unit used by the given field name. Units are only available
	 * for numerical fields.
	 * 
	 * @param fieldName
	 *  - the field requiring the unit description
	 * @return
	 *   the <CODE>String</CODE> describing the parameter.
	 */
	public String getUnitDescription(String fieldName)
	{
		return fieldName;
	}
	
	/**
	 * Returns the <CODE>Image</CODE> associated with the given country
	 * code. The image returned is the flag of the country of the
	 * designated country code.
	 * 
	 * @param countryCode
	 * 	 - the designated short-hand code of a country
	 * @return
	 *   the flag of the country corresponding the country code.
	 */
	public Image getFlagOfCountry(String countryCode)
	{
		// TODO
		return null;
	}
	
	// MUTATOR METHODS
	/**
	 * Sets how the records will be arranged by the given field.
	 * 
	 * @param name
	 *   - the name of the field to sort the records
	 */
	public void setFieldToSort(String name)
	{
		fieldToSort = name;
	}
	
	/**
	 * Adds a field to the <CODE>DBFTable</CODE> whose record values
	 * can be calculated by the given formula. The field will be named
	 * by the parameter.
	 * 
	 * @param fieldName
	 *   - the name of the new field to create
	 * @param formula
	 *   - a valid formula to calculate the values of each record of the
	 *     new field
	 */
	public void addNumericField(String fieldName, String formula)
	{
		// TODO
	}
	
	/**
	 * Sorts the records of the <CODE>DBFTable</CODE> based on the
	 * current <CODE>fieldToSort</CODE>.
	 */
	public void sortField()
	{
		// TODO
	}
}

package factbook_mapper.tools;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;

/**
 * A class that manages the creation of a new field in terms of
 * header name and field data formula validity.
 * 
 * @author Dinia Gepte
 *
 */
public class FormulaManager
{	
	private static MaskFormatter nameMask;
	private static String formula;
	
	/**
	 * Returns the <CODE>MaskFormatter</CODE> for the header name.
	 * The mask only allows up to 10 characters, to adhere to the 
	 * .dbf maximum field name length.
	 *
	 * @return
	 *   the mask for the header title to be used by
	 *   <CODE>JFormattedTextField</CODE>.
	 */
	public static MaskFormatter getNameMaskFormatter()
	{
		try {
			nameMask = new MaskFormatter("**********");
		} catch (ParseException e) {	e.printStackTrace();	}
		nameMask.setPlaceholderCharacter(' ');
		return nameMask;
	}
	
	/**
	 * Checks if the parameter <CODE>initFormula</CODE> is a valid
	 * mathematical formula.
	 * 
	 * @param initFormula
	 *   - the <CODE>String</CODE> to analyze for formula validity
	 * @return
	 *   <CODE>true</CODE> if the parameter is a valid formula,
	 *   <CODE>false</CODE> otherwise.
	 */
	public static boolean isGoodFormula(String initFormula)
	{
		formula = initFormula;
		//TODO ADD COMPUTATIONS
		return false;
	}
	
	
}

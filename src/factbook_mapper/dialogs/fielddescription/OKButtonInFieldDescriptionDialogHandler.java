package factbook_mapper.dialogs.fielddescription;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import factbook_mapper.dialogs.countryname.CountryNamesDialog;

/**
 * A class that handles the <CODE>JButton OKButton</CODE> response from
 * {@link FieldDescriptionDialog}.
 * 
 * @author Dinia Gepte
 *
 */
public class OKButtonInFieldDescriptionDialogHandler implements ActionListener 
{
	private JDialog dialog;
	
	/**
	 * Constructor of this class that sets the dialog owning the
	 * <CODE>JButton</CODE> to the parameter <CODE>initDialog</CODE>.
	 * 
	 * @param initDialog
	 *   - the dialog owning the <CODE>JButton</CODE> using this
	 *   <CODE>ActionListener</CODE>
	 */
	public OKButtonInFieldDescriptionDialogHandler(JDialog initDialog)
	{
		dialog = initDialog;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		dialog.setVisible(false);
	}
}

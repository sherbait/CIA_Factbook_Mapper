package factbook_mapper.dialogs.countryflag;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

/**
 * A class that handles the <CODE>JButton OKButton</CODE> response from
 * {@link CountryFlagsDialog}.
 * 
 * @author Dinia Gepte
 *
 */
public class OKButtonInCountryFlagsDialogHandler implements ActionListener
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
	public OKButtonInCountryFlagsDialogHandler(JDialog initDialog)
	{
		dialog = initDialog;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		dialog.setVisible(false);
	}
}

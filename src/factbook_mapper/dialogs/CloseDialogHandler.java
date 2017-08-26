package factbook_mapper.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

/**
 * A class that simply closes a <CODE>JDialog</CODE> using
 * the <CODE>setVisible</CODE> method in the <CODE>JDialog</CODE>
 * class.
 * 
 * @author Dinia Gepte
 *
 */
public class CloseDialogHandler implements ActionListener
{
	private JDialog dialog;
	
	/**
	 * Constructor of this class that determines which dialog
	 * to close from the paramater <CODE>initDialog</CODE>.
	 * 
	 * @param initDialog
	 *   - the dialog to close
	 */
	public CloseDialogHandler(JDialog initDialog)
	{
		dialog = initDialog;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		dialog.setVisible(false);
	}	
}
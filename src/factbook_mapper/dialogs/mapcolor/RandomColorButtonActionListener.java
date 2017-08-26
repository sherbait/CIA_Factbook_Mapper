package factbook_mapper.dialogs.mapcolor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * A class that provides an appropriate response when the user
 * clicks the Random Color <CODE>JButton</CODE> in {@link MapColorDialog}.
 * 
 * @author Dinia Gepte
 *
 */
public class RandomColorButtonActionListener implements ActionListener
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
	public RandomColorButtonActionListener(JDialog initDialog)
	{
		dialog = initDialog;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		JOptionPane.showMessageDialog(dialog, "OK!");
	}
}

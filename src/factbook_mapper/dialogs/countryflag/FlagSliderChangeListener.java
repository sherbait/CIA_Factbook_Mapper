package factbook_mapper.dialogs.countryflag;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * A class that listens for changes on the <CODE>JSlider</CODE> in the
 * {@link CountryFlagsDialog}.
 * 
 * @author Dinia Gepte
 *
 */
public class FlagSliderChangeListener implements ChangeListener
{	
	private JDialog dialog;
	
	/**
	 * Constructor of this class that sets the dialog owning a
	 * <CODE>JSlider</CODE> to the parameter <CODE>initDialog</CODE>.
	 * 
	 * @param initDialog
	 *   - the dialog owning the <CODE>JSlider</CODE> using
	 *   this <CODE>ChangeListener</CODE>
	 */
	public FlagSliderChangeListener(JDialog initDialog)
	{
		dialog = initDialog;
	}
	
	public void stateChanged(ChangeEvent e)
	{
		JSlider source = (JSlider)e.getSource();
		if (!source.getValueIsAdjusting())
		{
			int value = source.getValue();
			JOptionPane.showMessageDialog(dialog, "Value is: " + value);
			// TODO METHOD STUB
		}
	}
}

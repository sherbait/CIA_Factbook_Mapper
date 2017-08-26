package factbook_mapper.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import factbook_mapper.FactbookMapper;

/**
 * This handler responds when the user selects the <CODE>JRadioButton</CODE>
 * labeled "Increasing" on the main window.
 *  
 * @author Dinia Gepte
 *
 */
public class SortIncreasingHandler implements ActionListener 
{
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		JRadioButton source = (JRadioButton)e.getSource();
		if (source.isSelected())
			JOptionPane.showMessageDialog(FactbookMapper.getFactbookMapper(), "Sort increasing handler OK!");
	}
}

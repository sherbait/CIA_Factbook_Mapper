package factbook_mapper.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import factbook_mapper.FactbookMapper;

/**
 * This handler responds when the user selects the <CODE>JCheckBox</CODE>
 * labeled "Title" on the main window.
 *  
 * @author Dinia Gepte
 *
 */
public class TitleCheckBoxHandler implements ActionListener
{
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(FactbookMapper.getFactbookMapper(), "Title check box OK!");
	}
}

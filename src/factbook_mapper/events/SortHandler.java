package factbook_mapper.events;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JOptionPane;

import factbook_mapper.FactbookMapper;

/**
 * This handler responds when the user selects an item from
 * the <CODE>JComboBox</CODE> containing a list of field name
 * headers on the main window.
 *  
 * @author Dinia Gepte
 *
 */
public class SortHandler implements ItemListener 
{
	public void itemStateChanged(ItemEvent e)
	{
		// TODO Auto-generated method stub
		if (e.getStateChange() == ItemEvent.SELECTED)
		{
			String item = (String) e.getItem();
			JOptionPane.showMessageDialog(FactbookMapper.getFactbookMapper(), "Item is: " + item);
		}
	}
}

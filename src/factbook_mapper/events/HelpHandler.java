package factbook_mapper.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import factbook_mapper.FactbookMapper;
import factbook_mapper.dialogs.HelpDialog;

/**
 * This handler responds when the user clicks on the Help
 * <CODE>JButton</CODE> on the main window.
 *  
 * @author Dinia Gepte
 *
 */
public class HelpHandler implements ActionListener
{
	public void actionPerformed(ActionEvent e)
	{
		HelpDialog dialog = new HelpDialog(FactbookMapper.getFactbookMapper());
	}
}

package factbook_mapper.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import factbook_mapper.FactbookMapper;

/**
 * This handler responds when the user clicks on the New Map
 * <CODE>JButton</CODE> on the main window.
 *  
 * @author Dinia Gepte
 *
 */
public class NewMapHandler implements ActionListener
{
	public void actionPerformed(ActionEvent e)
	{
		// TODO ADD FUNCTIONALITY TO CANNOT OPEN NEW POLITICAL MAP
		// IF UNSAVED DATA
		((FactbookMapper) FactbookMapper.getFactbookMapper()).getFileManager().processNewRequest();
	}
}

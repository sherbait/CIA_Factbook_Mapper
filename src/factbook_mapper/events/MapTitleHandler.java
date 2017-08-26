package factbook_mapper.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import factbook_mapper.FactbookMapper;
import factbook_mapper.dialogs.maptitle.MapTitleDialog;

/**
 * This handler responds when the user clicks on the Map Title
 * <CODE>JButton</CODE> on the main window.
 *  
 * @author Dinia Gepte
 *
 */
public class MapTitleHandler implements ActionListener 
{
	public void actionPerformed(ActionEvent e)
	{
		MapTitleDialog dialog = new MapTitleDialog(FactbookMapper.getFactbookMapper());
	}
}

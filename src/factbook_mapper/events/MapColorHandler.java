package factbook_mapper.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import factbook_mapper.FactbookMapper;
import factbook_mapper.dialogs.mapcolor.MapColorDialog;

/**
 * This handler responds when the user clicks on the Map Color
 * <CODE>JButton</CODE> on the main window.
 *  
 * @author Dinia Gepte
 *
 */
public class MapColorHandler implements ActionListener
{
	public void actionPerformed(ActionEvent e)
	{
		MapColorDialog dialog = new MapColorDialog(FactbookMapper.getFactbookMapper());	
	}
}

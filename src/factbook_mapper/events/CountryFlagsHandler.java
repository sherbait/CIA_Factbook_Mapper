package factbook_mapper.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import factbook_mapper.FactbookMapper;
import factbook_mapper.dialogs.countryflag.CountryFlagsDialog;

/**
 * This handler responds when the user clicks on the Country Flags
 * <CODE>JButton</CODE> on the main window.
 *  
 * @author Dinia Gepte
 *
 */
public class CountryFlagsHandler implements ActionListener
{
	public void actionPerformed(ActionEvent e)
	{
		CountryFlagsDialog dialog = new CountryFlagsDialog(FactbookMapper.getFactbookMapper());	
	}
}

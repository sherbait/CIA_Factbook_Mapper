package factbook_mapper.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import factbook_mapper.FactbookMapper;
import factbook_mapper.dialogs.countryname.CountryNamesDialog;

/**
 * This handler responds when the user clicks on the Country Names
 * <CODE>JButton</CODE> on the main window.
 *  
 * @author Dinia Gepte
 *
 */
public class CountryNamesHandler implements ActionListener
{
	public void actionPerformed(ActionEvent w)
	{
		CountryNamesDialog dialog = new CountryNamesDialog(FactbookMapper.getFactbookMapper());	
	}
}

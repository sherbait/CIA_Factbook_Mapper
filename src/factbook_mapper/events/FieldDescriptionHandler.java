package factbook_mapper.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import factbook_mapper.FactbookMapper;
import factbook_mapper.dialogs.fielddescription.FieldDescriptionDialog;

/**
 * This handler responds when the user clicks on the Field
 * Description <CODE>JButton</CODE> on the main window.
 *  
 * @author Dinia Gepte
 *
 */
public class FieldDescriptionHandler implements ActionListener
{
	public void actionPerformed(ActionEvent e)
	{
		FieldDescriptionDialog dialog = new FieldDescriptionDialog(FactbookMapper.getFactbookMapper());
	}
}

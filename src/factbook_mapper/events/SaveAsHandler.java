package factbook_mapper.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import factbook_mapper.FactbookMapper;

/**
 * This handler responds when the user clicks on the Save As
 * <CODE>JButton</CODE> on the main window.
 *  
 * @author Dinia Gepte
 *
 */
public class SaveAsHandler implements ActionListener
{
	public void actionPerformed(ActionEvent e)
	{
		FactbookMapper.getFactbookMapper().getFileManager().processSaveAsRequest();
	}
}

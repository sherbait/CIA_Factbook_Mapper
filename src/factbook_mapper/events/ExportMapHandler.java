package factbook_mapper.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import factbook_mapper.FactbookMapper;

/**
 * This handler responds when the user clicks on the Export Map
 * <CODE>JButton</CODE> on the main window.
 *  
 * @author Dinia Gepte
 *
 */
public class ExportMapHandler implements ActionListener
{
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		//JOptionPane.showMessageDialog(FactbookMapper.getFactbookMapper(), "Export map handler OK!");
		FactbookMapper.getFactbookMapper().getFileManager().processExportRequest();
	}
}

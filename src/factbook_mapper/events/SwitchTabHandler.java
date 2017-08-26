package factbook_mapper.events;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import factbook_mapper.FactbookMapper;

/**
 * This handler responds when the user switches between "Map" and "Table" tabs.
 * 
 * @author Dinia Gepte
 *
 */
public class SwitchTabHandler implements ChangeListener
{
	public void stateChanged(ChangeEvent e) 
	{
		JTabbedPane source = (JTabbedPane) e.getSource();
		if (source.getSelectedIndex() == 0)
		{
			if (FactbookMapper.getFactbookMapper().getMapViewer().getMapData() != null)
			{
				FactbookMapper.getFactbookMapper().enableButtonsForMapTabSelection();
				FactbookMapper.getFactbookMapper().getMapViewer().getRenderer().requestFocusInWindow();
			}
				
			// ERASE WILL BE DISABLED MAP IS A POLITICAL MAP
			if (FactbookMapper.getFactbookMapper().getFileManager().isPoliticalMapIsMap())
				FactbookMapper.getFactbookMapper().disableEraseButton();
		}
		else if (source.getSelectedIndex() == 1)
		{
			if (FactbookMapper.getFactbookMapper().getTableViewer() != null)
				FactbookMapper.getFactbookMapper().disableButtonsForTableTabSelection();
		}
	}
}

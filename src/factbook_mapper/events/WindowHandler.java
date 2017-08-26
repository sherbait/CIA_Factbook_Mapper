package factbook_mapper.events;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import factbook_mapper.FactbookMapper;

/**
 * This handler responds when the user interacts with the window itself.
 * It only provides a custom response for <CODE>windowClosing</CODE>.
 * 
 * @author Dinia Gepte
 *
 */
public class WindowHandler implements WindowListener
{

	public void windowActivated(WindowEvent e) 	{	}
	public void windowClosed(WindowEvent e) 	{	}

	public void windowClosing(WindowEvent e)
	{
		FactbookMapper.getFactbookMapper().getFileManager().processExitProgramRequest();	
	}

	public void windowDeactivated(WindowEvent e) 	{	}
	public void windowDeiconified(WindowEvent e) 	{	}
	public void windowIconified(WindowEvent e)		{	}
	public void windowOpened(WindowEvent e) 		{	}
}

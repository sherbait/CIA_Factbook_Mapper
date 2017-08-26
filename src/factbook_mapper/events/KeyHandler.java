package factbook_mapper.events;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

import factbook_mapper.FactbookMapper;

import shp_renderer.SHPRenderer;
import shp_renderer.SHPViewer;

/**
 * This handler responds when the user presses a key when the
 * viewport is displayed.
 * 
 * @author Dinia Gepte
 *
 */
public class KeyHandler implements KeyListener
{
	private SHPRenderer renderer;

	/**
	 * Constructor that sets the <CODE>SHPViewer</CODE> of this class
	 * to the parameter <CODE>initViewer</CODE>.
	 * 
	 * @param initViewer
	 *   - the map viewer to be used by this class
	 */
	public KeyHandler(SHPRenderer initRenderer)
	{
		renderer = initRenderer;
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		// STORES THE KEY PRESSED FOR THIS EVENT
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_A)
			//processZoomInRequest();
			//JOptionPane.showMessageDialog(FactbookMapper.getFactbookMapper(), "A key OK!");
			renderer.zoomIn();
		else if (key == KeyEvent.VK_S)
			//processZoomOutRequest();
			//JOptionPane.showMessageDialog(FactbookMapper.getFactbookMapper(), "S key OK!");
			renderer.zoomOut();
		else if ((key == KeyEvent.VK_UP) ||
				 (key == KeyEvent.VK_LEFT) ||
				 (key == KeyEvent.VK_DOWN) ||
				 (key == KeyEvent.VK_RIGHT))
			//processNavigationRequest(key);
			//JOptionPane.showMessageDialog(FactbookMapper.getFactbookMapper(), "Directional key OK!");
		{
			if (key == KeyEvent.VK_UP)
				renderer.incViewportCenterY();
			else if (key == KeyEvent.VK_LEFT)
				renderer.decViewportCenterX();
			else if (key == KeyEvent.VK_DOWN)
				renderer.decViewportCenterY();
			else
				renderer.incViewportCenterX();
		}
		else
			return;
		
		// IF WE DID SOMETHING, WE DIDN'T RETURN, WHICH MEANS
		// WE CHANGED THE VIEW, SO REDRAW THE MAP
		renderer.repaint();
	}

	public void keyReleased(KeyEvent e) 	{	}
	public void keyTyped(KeyEvent e)		{	}
}

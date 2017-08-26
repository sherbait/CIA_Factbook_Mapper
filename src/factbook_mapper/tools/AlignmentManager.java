package factbook_mapper.tools;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import factbook_mapper.FactbookMapper;

/**
 * A class that manages a component's alignment on a panel. The component
 * is represented by a <CODE>Rectangle</CODE> whose alignment is controlled
 * by four <CODE>JButton</CODE>s: Up, Down, Left, Right.
 * <p>
 * Each button uses a corresponding <CODE>ActionListener</CODE>
 * defined in this class:
 * <CODE>
 * <li>UpButtonActionListener
 * <li>LeftButtonActionListener
 * <li>DownButtonActionListener
 * <li>RightButtonActionListener
 * </CODE>
 * <p><br>
 * Furthermore, using this class can display the current location of the
 * component on the panel.
 * 
 * @author Dinia Gepte
 *
 */
public class AlignmentManager extends JPanel 
{
	private int panelWidth;
	private int panelHeight;
	private Rectangle boxToAlign;
	private int xCoordOfBox;
	private int yCoordOfBox;
	private UpButtonActionListener ubal;
	private LeftButtonActionListener lbal;
	private DownButtonActionListener dbal;
	private RightButtonActionListener rbal;
	
	/**
	 * Constructor of this class that initializes all
	 * <CODE>ActionListener</CODE>s that will respond to button
	 * clicks.
	 */
	public AlignmentManager()
	{
		ubal = new UpButtonActionListener();
		lbal = new LeftButtonActionListener();
		dbal = new DownButtonActionListener();
		rbal = new RightButtonActionListener();
		// TODO ADD DEFAULT VALUES
	}
	
	/**
	 * Returns the x-axis location of the component relative to the panel.
	 * 
	 * @return
	 *   the x-coordinate of the component.
	 */
	public int getXCoordOfAlignedBox()		{	return xCoordOfBox;		}
	
	/**
	 * Returns the y-axis location of the component relative to the panel.
	 * 
	 * @return
	 *   the y-coordinate of the component.
	 */
	public int getYCoordOfAlignedBox()		{	return yCoordOfBox;		}
	
	/**
	 * Returns the <CODE>ActionListener</CODE> that responds to
	 * "Up" <CODE>JButton</CODE> clicks. It will move the component
	 * 10 units upward for each click until it reaches the top edge of the
	 * panel.
	 * 
	 * @return
	 *   the listener responding to up button clicks.
	 */
	public ActionListener getUpButtonActionListener()		{	return ubal;	}
	
	/**
	 * Returns the <CODE>ActionListener</CODE> that responds to
	 * "Left" <CODE>JButton</CODE> clicks. It will move the component
	 * 10 units to the left for each click until it reaches the left edge
	 * of the panel.
	 * 
	 * @return
	 *   the listener responding to left button clicks.
	 */
	public ActionListener getLeftButtonActionListener()		{	return lbal;	}
	
	/**
	 * Returns the <CODE>ActionListener</CODE> that responds to
	 * "Down" <CODE>JButton</CODE> clicks. It will move the component
	 * 10 units downward for each click until it reaches the bottom edge
	 * of the panel.
	 * 
	 * @return
	 *   the listener responding to down button clicks.
	 */
	public ActionListener getDownButtonActionListener()		{	return dbal;	}
	
	/**
	 * Returns the <CODE>ActionListener</CODE> that responds to
	 * "Right" <CODE>JButton</CODE> clicks. It will move the component
	 * 10 units to the right for each click until it reaches the right 
	 * edge of the panel.
	 * 
	 * @return
	 *   the listener responding to right button clicks.
	 */
	public ActionListener getRightButtonActionListener()	{	return rbal;	}
	
	/**
	 * Sets the size of the component to be aligned on the panel.
	 * 
	 * @param width
	 *   - horizontal length of the component
	 * @param height
	 *   - vertical length of the compoenent
	 */
	public void setBoxSize(int width, int height)
	{
		
	}
	
	// ADD paintComponent() OVERRIDE HERE
	
	class UpButtonActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			// TODO Auto-generated method stub
			JOptionPane.showMessageDialog(FactbookMapper.getFactbookMapper(), "UP!");
		}
	}
	
	class LeftButtonActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			// TODO Auto-generated method stub
			JOptionPane.showMessageDialog(FactbookMapper.getFactbookMapper(), "LEFT!");
		}
	}
	
	class DownButtonActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			// TODO Auto-generated method stub
			JOptionPane.showMessageDialog(FactbookMapper.getFactbookMapper(), "DOWN!");
			
		}
	}
	
	class RightButtonActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			// TODO Auto-generated method stub
			JOptionPane.showMessageDialog(FactbookMapper.getFactbookMapper(), "RIGHT!");
		}
	}
}

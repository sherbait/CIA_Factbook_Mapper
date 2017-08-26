package factbook_mapper.tools;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;
import javax.swing.JPanel;

/**
 * A class that manages user-selection of preferred map color gradients,
 * instead of the program-generated gradient. The colors are selected
 * using <CODE>JColorChooser</CODE> and can only be accessed by
 * <CODE>JButton</CODE>s using the listeners defined in the
 * <CODE>MapColorManager</CODE> class.
 * 
 * @author Dinia Gepte
 *
 */
public class MapColorManager extends JPanel 
{
	private Color startColor;
	private Color endColor;
	private StartColorActionListener scal;
	private EndColorActionListener ecal;
	
	/**
	 * Constructor that initializes default start and end color
	 * gradients, as well as initializing relevant listeners.
	 * 
	 * @param owner
	 *   - the component owning the buttons to open the color
	 *   choose dialog
	 */
	public MapColorManager(Component owner)
	{
		startColor = Color.WHITE;
		endColor = Color.BLACK;
		scal = new StartColorActionListener(owner);
		ecal = new EndColorActionListener(owner);
	}
	
	/**
	 * Returns the start <CODE>Color</CODE> gradient of coloring numeric
	 * fields of the map.
	 * 
	 * @return
	 *   the starting color to color the map with.
	 */
	public Color getStartColor()							{	return startColor;	}
	
	/**
	 * Returns the end <CODE>Color</CODE> gradient of coloring numeric
	 * fields of the map.
	 * 
	 * @return
	 *   the ending color to color the map with.
	 */
	public Color getEndColor()								{	return endColor;	}
	
	/**
	 * Returns the <CODE>ActionListener</CODE> that responds to a
	 * a click on a color chooser <CODE>JButton</CODE>. The listener
	 * will respond by displaying a <CODE>JColorChooser</CODE> dialog. 
	 * The color chosen from the dialog will be used as the starting color
	 * gradient.
	 * 
	 * @return
	 *   the listener for a <CODE>JButton</CODE> that responds to user clicks.
	 */
	public ActionListener getStartColorActionListener()		{ 	return scal;		}
	
	/**
	 * Returns the <CODE>ActionListener</CODE> that responds to a
	 * a click on a color chooser <CODE>JButton</CODE>. The listener
	 * will respond by displaying a <CODE>JColorChooser</CODE> dialog. 
	 * The color chosen from the dialog will be used as the ending color
	 * gradient.
	 * 
	 * @return
	 *   the listener for a <CODE>JButton</CODE> that responds to user clicks.
	 */
	public ActionListener getEndColorActionListener()		{	return ecal;		}
	
	// TODO override paintComponent()
	
	class StartColorActionListener implements ActionListener
	{
		private Component owner;
				
		public StartColorActionListener(Component initOwner)
		{
			owner = initOwner;
		}
				
		public void actionPerformed(ActionEvent e)
		{
			startColor = JColorChooser.showDialog(owner, "Color Chooser", startColor);
		}
	}
	
	class EndColorActionListener implements ActionListener
	{
		private Component owner;
				
		public EndColorActionListener(Component initOwner)
		{
			owner = initOwner;
		}
				
		public void actionPerformed(ActionEvent e)
		{
			startColor = JColorChooser.showDialog(owner, "Color Chooser", startColor);
		}
	}
}

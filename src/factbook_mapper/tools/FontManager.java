package factbook_mapper.tools;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import factbook_mapper.FactbookMapper;

/**
 * A class that represents fonts and font colors to render text visually.
 * It has its own list of fonts, font sizes, and font styles that can be
 * placed in components preferably
 * <li><CODE>JList</CODE> - font list, size list
 * <li><CODE>JComboBox</CODE> - font styles
 * <p><br> due to the <CODE>ActionListener</CODE>s corresponding to each
 * component already within the <CODE>FontManager</CODE> class. 
 * <p><br> The font color is chosen using a <CODE>JColorChooser</CODE> which
 * can be accessed by a <CODE>JButton</CODE> using the
 * <CODE>ActionListener</CODE> in this class.
 *  
 * @author Dinia Gepte
 *
 */
public class FontManager
{
	private Font font;
	private String fontName;
	private int style;
	private int size;
	private Color color;
	private FontListSelectionListener flsl;
	private SizeListSelectionListener slsl;
	private ColorChooserButtonActionListener ccbal;
	private StyleComboBoxActionListener scbal;
	
	/**
	 * Constructor that sets a default <CODE>Font</CODE> and initializes
	 * all related listeners. The <CODE>Font</CODE> is created 
	 * with <CODE>null</CODE> name, <CODE><i>Font.PLAIN</i></CODE> style,
	 * and 12-point size. Note that the <CODE>FontManager</CODE> class
	 * requires a component owner from the parameter <CODE>owner</CODE>.
	 * 
	 * @param owner
	 *   - the component owning the button to access the color chooser
	 */
	public FontManager(Component owner)
	{
		fontName = null;
		style = Font.PLAIN;
		size = 12;
		color = Color.BLACK;
		font = new Font(fontName, style, size);
		flsl = new FontListSelectionListener();
		slsl = new SizeListSelectionListener();
		ccbal = new ColorChooserButtonActionListener(owner);
		scbal = new StyleComboBoxActionListener();
	}
	
	/**
	 * Returns an array of all available <i>font family names</i>
	 * in the local <CODE>GraphicsEnvironment</CODE>.
	 *   
	 * @return
	 *   a <CODE>String</CODE> array of a list of available
	 *   font family names in the system.
	 * @see Font
	 */
	public static String[] getAllFontNames()
	{
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		return ge.getAvailableFontFamilyNames();
	}
	
	/**
	 * Returns an array of pre-selected font sizes for the
	 * <CODE>FontManager</CODE> class.
	 * 
	 * @return
	 *   an <CODE>int</CODE> array representing the font sizes
	 *   available to use.
	 */
	public static Integer[] getAllFontSizes()
	{
		Integer[] sizes = {8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
		return sizes;
	}
	
	/**
	 * Returns an array of all available <i>font styles</i>.
	 * 
	 * @return
	 *   a <CODE>String</CODE> array containing the list of font styles
	 *   available to use.
	 * @see Font
	 */
	public static String[] getAllFontStyles()
	{
		String[] styles = {"Plain", "Bold", "Italic", "Bold Italic"};
		return styles;
	}
	
	/**
	 * Returns the current <CODE>Font</CODE> used to render text.
	 *
	 * @return
	 *   the font currently set.
	 */
	public Font getFont()			{	return font;	}
	
	/**
	 * Returns the current <CODE>Color</CODE> used to render text.
	 * 
	 * @return
	 *   the color currently selected.
	 */
	public Color getFontColor()		{	return color;	}
	
	/**
	 * Returns the <CODE>ListSelectionListener</CODE> that responds to a
	 * change in selection in the <CODE>JList</CODE> containing a list
	 * of font family names.
	 * 
	 * @return
	 *   the listener for a <CODE>JList</CODE> that responds to font
	 *   family name selection.
	 */
	public ListSelectionListener getFontListSelectionListener()		{	return flsl;	}
	
	/**
	 * Returns the <CODE>ListSelectionListener</CODE> that responds to a
	 * change in selection in the <CODE>JList</CODE> containing a list
	 * of font sizes.
	 * 
	 * @return
	 *   the listener for a <CODE>JList</CODE> that responds to font
	 *   size selection.
	 */
	public ListSelectionListener getSizeListSelectionListener()		{	return slsl;	}
	
	/**
	 * Returns the <CODE>ActionListener</CODE> that responds to a
	 * a click on a color chooser <CODE>JButton</CODE>. The listener
	 * will respond by displaying a <CODE>JColorChooser</CODE> dialog. 
	 * 
	 * @return
	 *   the listener for a <CODE>JButton</CODE> that responds to user clicks.
	 */
	public ActionListener getColorChooserButtonActionListener()		{	return ccbal;	}
	
	/**
	 * Returns the <CODE>ItemListener</CODE> that responds to a change in
	 * selection in the <CODE>JComboBox</CODE> containing a list of
	 * font styles.
	 * 
	 * @return
	 *   the listener for a <CODE>JComboBox</CODE> that responds to
	 *   font style selection.
	 */
	public ItemListener getStyleComboBoxItemListener()				{	return scbal;	}
	
	/**
	 * Adds a component in a container using <CODE>GridBagLayout</CODE>.
	 * This is especially useful for creating the layout for the
	 * <CODE>FontManager</CODE>'s components.
	 * 
	 * @param jc
	 *   - the component to add
	 * @param c
	 *   - the container to hold <CODE>jc</CODE>
	 * @param x
	 *   - equivalent to <CODE>gridx</CODE> in <CODE>GridBagLayout</CODE>
	 * @param y
	 *   - equivalent to <CODE>gridy</CODE> in <CODE>GridBagLayout</CODE> 
	 * @param w
	 * - equivalent to <CODE>gridwidth</CODE> in <CODE>GridBagLayout</CODE>
	 * @param h
	 * - equivalent to <CODE>gridheight</CODE> in <CODE>GridBagLayout</CODE>
	 * @see GridBagLayout
	 */
	public static void addJComponentToContainerUsingGBL(	JComponent jc,
															Container c,
															int x, int y,
															int w, int h	)
	{
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = w;
		gbc.gridheight = h;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);
		c.add(jc, gbc);
	}
	
	class FontListSelectionListener implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e)
		{
			JList source = (JList)e.getSource();
			if (!source.getValueIsAdjusting())
			{
				fontName = (String)source.getSelectedValue();
				JOptionPane.showMessageDialog(FactbookMapper.getFactbookMapper(), fontName);	// TODO REMOVE LATER
			}
		}
	}
	
	class SizeListSelectionListener implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e)
		{
			JList source = (JList)e.getSource();
			if (!source.getValueIsAdjusting())
			{
				size = (Integer)source.getSelectedValue();
				JOptionPane.showMessageDialog(FactbookMapper.getFactbookMapper(), size);	// TODO REMOVE LATER
			}
		}
	}
	
	class ColorChooserButtonActionListener implements ActionListener
	{
		private Component owner;
		
		public ColorChooserButtonActionListener(Component initOwner)
		{
			owner = initOwner;
		}
		
		public void actionPerformed(ActionEvent e)
		{
			color = JColorChooser.showDialog(owner, "Color Chooser", color);
		}
	}
	
	class StyleComboBoxActionListener implements ItemListener
	{
		public void itemStateChanged(ItemEvent e)
		{
			if (e.getStateChange() == ItemEvent.SELECTED)
			{
				String item = (String)e.getItem();
				if (item.equals("Plain"))
					style = Font.PLAIN;
				else if (item.equals("Bold"))
					style = Font.BOLD;
				else if (item.equals("Italic"))
					style = Font.ITALIC;
				else if (item.equals("Bold Italic"))
					style = Font.BOLD | Font.ITALIC;
				JOptionPane.showMessageDialog(FactbookMapper.getFactbookMapper(), style);
			}
		}	
	}
}

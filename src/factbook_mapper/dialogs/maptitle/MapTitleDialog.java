package factbook_mapper.dialogs.maptitle;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import factbook_mapper.dialogs.CloseDialogHandler;
import factbook_mapper.tools.AlignmentManager;
import factbook_mapper.tools.FontManager;

/**
 * A class that displays controls to the user for manipulating the
 * visual representation of map title rendered on the viewport.
 * Controls include setting the <CODE>Font</CODE> and <CODE>Color</CODE>
 * of the map title determined by the user, as well as its location on the map.
 * 
 * @author Dinia Gepte
 *
 */
public class MapTitleDialog extends JDialog
{
	private FontManager fontManager;
	private JPanel northPanel;
	private JTextArea titleTextArea;
	private JPanel alignmentBox;
	private JButton upButton;
	private JButton leftButton;
	private JButton downButton;
	private JButton rightButton;
	private AlignmentManager previewPanel;
	private JPanel fontBox;
	private JList fontList;
	private JList sizeList;
	private JComboBox styleComboBox;
	private JButton colorChooserButton;
	private JPanel southPanel;
	private JButton OKButton;
	private JButton cancelButton;
	
	/**
	 * Constructs an instance of this class with an owner frame given
	 * by the parameter <CODE>owner</CODE>, a title of "Map Title",
	 * and modality of <CODE>true</CODE>.
	 * 
	 * @param owner
	 *   - the <CODE>Frame</CODE> owner of this <CODE>JDialog</CODE>
	 */
	public MapTitleDialog(JFrame owner)
	{
		super(owner, "Map Title", true);
		fontManager = new FontManager(this);
		layoutGUI();
		initHandlers();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void layoutGUI()
	{
		northPanel = new JPanel(new GridBagLayout());
		
		// TITLE TEXT BOX
		titleTextArea = new JTextArea();
		titleTextArea.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		JScrollPane textScrollPane = new JScrollPane(titleTextArea);
		textScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		textScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		textScrollPane.setPreferredSize(new Dimension(140, 100));
		textScrollPane.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEmptyBorder(), "Title", 
				TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
		
		// MAP ALIGNMENT BOX
		alignmentBox = new JPanel(new GridBagLayout());
		alignmentBox.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Map Alignment"));
		
		// ALIGNMENT CONTROLS
		upButton = new JButton("Up");
		leftButton = new JButton("Left");
		downButton = new JButton("Down");
		rightButton = new JButton("Right");
		
		// ADD CONTROLS TO ALIGNMENT BOX
		FontManager.addJComponentToContainerUsingGBL(upButton, alignmentBox, 1, 0, 1, 1);
		FontManager.addJComponentToContainerUsingGBL(leftButton, alignmentBox, 0, 0, 1, 2);
		FontManager.addJComponentToContainerUsingGBL(downButton, alignmentBox, 1, 1, 1, 1);
		FontManager.addJComponentToContainerUsingGBL(rightButton, alignmentBox, 3, 0, 1, 2);
		
		// PREVIEW PANEL
		previewPanel = new AlignmentManager();
		previewPanel.setPreferredSize(new Dimension(100, 128));	//TODO SCALE RELATIVE TO SHPRenderer
		previewPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createBevelBorder(BevelBorder.LOWERED),
				"Preview", TitledBorder.CENTER, TitledBorder.ABOVE_TOP));
		
		// FONT BOX
		fontBox = new JPanel(new GridBagLayout());
		fontBox.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Font"));
		
		// FONT SELECTION
		fontList = new JList(FontManager.getAllFontNames());
		fontList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fontList.setLayoutOrientation(JList.VERTICAL);
		JScrollPane fontScroll = new JScrollPane(fontList);
		fontScroll.setPreferredSize(new Dimension(128, 128));
		
		// SIZE SELECTION
		sizeList = new JList(FontManager.getAllFontSizes());
		sizeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		sizeList.setLayoutOrientation(JList.VERTICAL);
		JScrollPane sizeScroll = new JScrollPane(sizeList);
		sizeScroll.setPreferredSize(new Dimension(64, 128));
		
		// STYLE SELECTION
		styleComboBox = new JComboBox(FontManager.getAllFontStyles());
		
		// COLOR SELECTION
		colorChooserButton = new JButton("Color Chooser...");
		
		// LAYOUT THE COMPONENTS IN THE FONT BOX
		FontManager.addJComponentToContainerUsingGBL(new JLabel("Font:"), fontBox, 0, 0, 2, 1);
		FontManager.addJComponentToContainerUsingGBL(new JLabel("Size:"), fontBox, 2, 0, 1, 1);
		FontManager.addJComponentToContainerUsingGBL(fontScroll, fontBox, 0, 1, 2, 1);
		FontManager.addJComponentToContainerUsingGBL(sizeScroll, fontBox, 2, 1, 1, 1);
		FontManager.addJComponentToContainerUsingGBL(new JLabel("Style:"), fontBox, 0, 2, 1, 1);
		FontManager.addJComponentToContainerUsingGBL(styleComboBox, fontBox, 1, 2, 2, 1);
		FontManager.addJComponentToContainerUsingGBL(colorChooserButton, fontBox, 0, 3, 3, 1);
		
		// ADD COMPONENTS TO northPanel
		FontManager.addJComponentToContainerUsingGBL(textScrollPane, northPanel, 0, 0, 1, 1);
		FontManager.addJComponentToContainerUsingGBL(alignmentBox, northPanel, 1, 0, 1, 1);
		FontManager.addJComponentToContainerUsingGBL(fontBox, northPanel, 0, 1, 1, 2);
		FontManager.addJComponentToContainerUsingGBL(previewPanel, northPanel, 1, 1, 1, 2);
		
		// PANEL CONTAINING OKButton AND cancelButton
		southPanel = new JPanel();
		((FlowLayout)southPanel.getLayout()).setAlignment(FlowLayout.RIGHT);
		OKButton = new JButton("Apply");
		cancelButton = new JButton("Cancel");
		southPanel.add(OKButton);
		southPanel.add(cancelButton);
		
		// ADD ALL COMPONENTS TO THIS DIALOG
		add(northPanel, BorderLayout.NORTH);
		add(southPanel, BorderLayout.SOUTH);
		
		pack();
	}
	
	private void initHandlers()
	{
		fontList.addListSelectionListener(fontManager.getFontListSelectionListener());
		sizeList.addListSelectionListener(fontManager.getSizeListSelectionListener());
		styleComboBox.addItemListener(fontManager.getStyleComboBoxItemListener());
		colorChooserButton.addActionListener(fontManager.getColorChooserButtonActionListener());
		
		upButton.addActionListener(previewPanel.getUpButtonActionListener());
		leftButton.addActionListener(previewPanel.getLeftButtonActionListener());
		downButton.addActionListener(previewPanel.getDownButtonActionListener());
		rightButton.addActionListener(previewPanel.getRightButtonActionListener());
		
		OKButtonInMapTitleDialogHandler OKButtonActionListener = 
			new OKButtonInMapTitleDialogHandler(this);
		OKButton.addActionListener(OKButtonActionListener);
		
		CloseDialogHandler cancelButtonActionListener = new CloseDialogHandler(this);
		cancelButton.addActionListener(cancelButtonActionListener);
	}
}

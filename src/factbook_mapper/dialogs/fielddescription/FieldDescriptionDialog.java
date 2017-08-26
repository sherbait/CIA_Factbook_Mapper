package factbook_mapper.dialogs.fielddescription;

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
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import factbook_mapper.dialogs.CloseDialogHandler;
import factbook_mapper.tools.AlignmentManager;
import factbook_mapper.tools.FontManager;

/**
 * A class that displays controls to the user for manipulating the
 * visual representation of field description rendered on the viewport.
 * Controls include setting the <CODE>Font</CODE> and <CODE>Color</CODE>
 * of the field description currently used by the application, as well
 * as its location on the map.
 * 
 * @author Dinia Gepte
 *
 */
public class FieldDescriptionDialog extends JDialog
{
	private FontManager fontManager;
	private JPanel northPanel;
	private JPanel fontBox;
	private JList fontList;
	private JList sizeList;
	private JComboBox styleComboBox;
	private JButton colorChooserButton;
	private JPanel alignmentBox;
	private JButton upButton;
	private JButton leftButton;
	private JButton downButton;
	private JButton rightButton;
	private AlignmentManager previewPanel;
	private JPanel southPanel;
	private JButton OKButton;
	private JButton cancelButton;
	
	/**
	 * Constructs an instance of this class with an owner frame given
	 * by the parameter <CODE>owner</CODE>, a title of "Field Description",
	 * and modality of <CODE>true</CODE>.
	 * 
	 * @param owner
	 *   - the <CODE>Frame</CODE> owner of this <CODE>JDialog</CODE>
	 */
	public FieldDescriptionDialog(JFrame owner)
	{
		super(owner, "Field Description", true);
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
		
		// ADD COMPONENTS TO northPanel
		FontManager.addJComponentToContainerUsingGBL(fontBox, northPanel, 0, 0, 1, 2);
		FontManager.addJComponentToContainerUsingGBL(alignmentBox, northPanel, 1, 0, 1, 1);
		FontManager.addJComponentToContainerUsingGBL(previewPanel, northPanel, 1, 1, 1, 1);
		
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
		
		OKButtonInFieldDescriptionDialogHandler OKButtonActionListener = 
			new OKButtonInFieldDescriptionDialogHandler(this);
		OKButton.addActionListener(OKButtonActionListener);
		
		CloseDialogHandler cancelButtonActionListener = new CloseDialogHandler(this);
		cancelButton.addActionListener(cancelButtonActionListener);
	}
}

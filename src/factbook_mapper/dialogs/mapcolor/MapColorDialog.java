package factbook_mapper.dialogs.mapcolor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;

import factbook_mapper.FactbookMapper;
import factbook_mapper.dialogs.CloseDialogHandler;
import factbook_mapper.tools.FontManager;
import factbook_mapper.tools.FormulaManager;
import factbook_mapper.tools.MapColorManager;

/**
 * A class that displays controls to the user for manipulating the
 * colors of the map rendered on the viewport.
 * Controls include selecting the colors, and creating a new field
 * to color the map with.
 * 
 * @author Dinia Gepte
 *
 */
public class MapColorDialog extends JDialog 
{
	private JPanel northPanel;
	private JList fieldList;
	private JButton newFormulaButton;
	private JButton editColorButton;
	private JPanel colorBox;
	private JButton startColorButton;
	private JButton endColorButton;
	private MapColorManager sampleGradientBox;
	private JButton randomColorButton;
	private JPanel formulaBox;
	private JTextField nameField;
	private JTextField formulaField;
	private JPanel southPanel;
	private JButton OKButton;
	private JButton cancelButton;
	
	/**
	 * Constructs an instance of this class with an owner frame given
	 * by the parameter <CODE>owner</CODE>, a title of "Map Color",
	 * and modality of <CODE>true</CODE>.
	 * 
	 * @param owner
	 *   - the <CODE>Frame</CODE> owner of this <CODE>JDialog</CODE>
	 */
	public MapColorDialog(JFrame owner)
	{
		super(owner, "Map Color", true);
		layoutGUI();
		initHandlers();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void layoutGUI()
	{
		northPanel = new JPanel(new GridBagLayout());
		
		// FIELD SELECTION
		String[] fields = {"Field 1", "Field 2", "Field 3", "Field 4", "Field 5", "Field 6"};//TODO REPLACE THIS
		fieldList = new JList(fields);
		fieldList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fieldList.setLayoutOrientation(JList.VERTICAL);
		JScrollPane fieldScroll = new JScrollPane(fieldList);
		fieldScroll.setPreferredSize(new Dimension(80, 128));
		fieldScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		// BUTTONS
		newFormulaButton = new JButton("New Formula");
		editColorButton = new JButton("Edit Color");
		
		// COLOR BOX
		colorBox = new JPanel(new GridBagLayout());
		colorBox.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Color"));
		startColorButton = new JButton("Start Color...");
		endColorButton = new JButton("End Color...");
		sampleGradientBox = new MapColorManager(this);
		sampleGradientBox.setPreferredSize(new Dimension(64, 64));
		sampleGradientBox.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		Image icon = FactbookMapper.getFactbookMapper().loadImage(
				FactbookMapper.BUTTONS_ICON_PATH + "random.png");
		randomColorButton = new JButton(new ImageIcon(icon));
		randomColorButton.setPreferredSize(new Dimension(36, 36));
		randomColorButton.setToolTipText("Randomize Color");
		
		// ADD COMPONENTS IN COLOR BOX
		FontManager.addJComponentToContainerUsingGBL(startColorButton, colorBox, 0, 0, 1, 1);
		FontManager.addJComponentToContainerUsingGBL(endColorButton, colorBox, 0, 1, 1, 1);
		colorBox.add(sampleGradientBox, sampleGradientBoxGBC());
		colorBox.add(randomColorButton, randomColorButtonGBC());
		
		// FORMULA BOX
		formulaBox = new JPanel(new GridBagLayout());
		formulaBox.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Formula"));
		nameField = new JFormattedTextField(FormulaManager.getNameMaskFormatter());
		nameField.setPreferredSize(new Dimension(140, 20));
		nameField.setToolTipText("Max characters = 10");
		nameField.setBorder(BorderFactory.createLoweredBevelBorder());
		formulaField = new JFormattedTextField();
		formulaField.setPreferredSize(new Dimension(140, 20));
		formulaField.setBorder(BorderFactory.createLoweredBevelBorder());
		
		// ADD THE COMPONENTS TO formulaBox
		FontManager.addJComponentToContainerUsingGBL(new JLabel("Name:"), formulaBox, 0, 0, 1, 1);
		FontManager.addJComponentToContainerUsingGBL(nameField, formulaBox, 1, 0, 1, 1);
		FontManager.addJComponentToContainerUsingGBL(new JLabel("Formula:"), formulaBox, 0, 1, 1, 1);
		FontManager.addJComponentToContainerUsingGBL(formulaField, formulaBox, 1, 1, 1, 1);
		
		// ADD ALL COMPONENTS IN northPanel
		FontManager.addJComponentToContainerUsingGBL(new JLabel("Field:", JLabel.CENTER), northPanel, 0, 0, 1, 1);
		FontManager.addJComponentToContainerUsingGBL(fieldScroll, northPanel, 0, 1, 1, 1);
		FontManager.addJComponentToContainerUsingGBL(newFormulaButton, northPanel, 0, 2, 1, 1);
		FontManager.addJComponentToContainerUsingGBL(editColorButton, northPanel, 0, 3, 1, 1);
		FontManager.addJComponentToContainerUsingGBL(colorBox, northPanel, 1, 0, 1, 4);
		FontManager.addJComponentToContainerUsingGBL(formulaBox, northPanel, 0, 4, 2, 1);
		
		// PANEL CONTAINING OKButton and cancelButton
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
	
	private GridBagConstraints sampleGradientBoxGBC()
	{
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridwidth = 1;
		c.gridy = 2;
		return c;
	}
	
	private GridBagConstraints randomColorButtonGBC()
	{
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.LAST_LINE_END;
		c.insets = new Insets(15,5,5,5);
		c.gridx = 0;
		c.gridwidth = 1;
		c.gridy = 3;
		return c;
	}
	
	private void initHandlers()
	{
		NewFormulaButtonActionListener nfbal = new NewFormulaButtonActionListener(this);
		newFormulaButton.addActionListener(nfbal);
		
		EditColorButtonActionListener ecbal = new EditColorButtonActionListener(this);
		editColorButton.addActionListener(ecbal);
		
		RandomColorButtonActionListener rcbal = new RandomColorButtonActionListener(this);
		randomColorButton.addActionListener(rcbal);
		
		startColorButton.addActionListener(sampleGradientBox.getStartColorActionListener());
		endColorButton.addActionListener(sampleGradientBox.getEndColorActionListener());
		
		OKButtonInMapColorDialogHandler OKButtonActionListener =
			new OKButtonInMapColorDialogHandler(this);
		OKButton.addActionListener(OKButtonActionListener);
		
		CloseDialogHandler cancelButtonActionListener = new CloseDialogHandler(this);
		cancelButton.addActionListener(cancelButtonActionListener);
	}
}

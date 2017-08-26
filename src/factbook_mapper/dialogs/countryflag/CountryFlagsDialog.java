package factbook_mapper.dialogs.countryflag;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;

import factbook_mapper.FactbookMapper;
import factbook_mapper.dialogs.CloseDialogHandler;
import factbook_mapper.tools.FontManager;

/**
 * A class that displays controls to the user for manipulating the
 * size of country flags rendered on the viewport.
 * 
 * @author Dinia Gepte
 *
 */
public class CountryFlagsDialog extends JDialog
{
	private JPanel northPanel;
	private JLabel previousFlag;
	private JLabel currentFlag;
	private JSlider reSizer;
	private JPanel southPanel;
	private JButton OKButton;
	private JButton cancelButton;
	
	/**
	 * Constructs an instance of this class with an owner frame given
	 * by the parameter <CODE>owner</CODE>, a title of "Country Flags",
	 * and modality of <CODE>true</CODE>.
	 * 
	 * @param owner
	 *   - the <CODE>Frame</CODE> owner of this <CODE>JDialog</CODE>
	 */
	public CountryFlagsDialog(JFrame owner)
	{
		super(owner, "Country Flags", true);
		layoutGUI();
		initHandlers();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void layoutGUI()
	{	
		// PREVIEW PANEL
		northPanel = new JPanel(new GridBagLayout());
		
		// GET THE IMAGE REPRESENTING THE FLAGS
		Image icon = FactbookMapper.getFactbookMapper().loadImage(
				FactbookMapper.MAP_FLAGS_PATH + "plain_flag.png");
		// TODO GET FLAG SCALE FROM SHPRendererSettings THEN USE ImageResizer CLASS
		previousFlag = new JLabel(new ImageIcon(icon));
		previousFlag.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Previous"));
		previousFlag.setPreferredSize(new Dimension(140, 128));
		currentFlag = new JLabel(new ImageIcon(icon));
		currentFlag.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Current"));
		currentFlag.setPreferredSize(new Dimension(140, 128));
		
		// RESIZER
		reSizer = new JSlider(JSlider.HORIZONTAL, 1, 10, 10);	// INIT VALUE(10): TO BE CHANGED
		reSizer.setFont(new Font("SansSerif", Font.BOLD, 10));
		reSizer.setMajorTickSpacing(1);
		reSizer.setPaintTicks(true);
		reSizer.setPaintLabels(true);
		reSizer.setSnapToTicks(true);
		reSizer.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEmptyBorder(), "Size",
				TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
		
		// ADD THE COMPONENTS TO northPanel
		FontManager.addJComponentToContainerUsingGBL(previousFlag, northPanel, 0, 0, 1, 1);
		FontManager.addJComponentToContainerUsingGBL(currentFlag, northPanel, 1, 0, 1, 1);
		FontManager.addJComponentToContainerUsingGBL(reSizer, northPanel, 0, 1, 2, 1);
		
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
	
	private void initHandlers()
	{
		FlagSliderChangeListener reSizerChangeListener = new FlagSliderChangeListener(this);
		reSizer.addChangeListener(reSizerChangeListener);
		
		OKButtonInCountryFlagsDialogHandler OKButtonActionListener =
			new OKButtonInCountryFlagsDialogHandler(this);
		OKButton.addActionListener(OKButtonActionListener);
		
		CloseDialogHandler cancelButtonActionListener = new CloseDialogHandler(this);
		cancelButton.addActionListener(cancelButtonActionListener);
	}
}

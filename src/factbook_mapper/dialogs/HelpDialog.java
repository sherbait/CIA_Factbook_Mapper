package factbook_mapper.dialogs;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A class that displays a complete list of input controls from the user
 * to navigate the displayed .shp map on the viewport.
 * 
 * @author Dinia Gepte
 *
 */
public class HelpDialog extends JDialog
{
	private JPanel northPanel;
	private JLabel controls;
	private JPanel southPanel;
	private JLabel functions;
	private JButton OKButton;
	
	/**
	 * Constructs an instance of this class with an owner frame given
	 * by the parameter <CODE>owner</CODE>, a title of "Help", and
	 * modality of <CODE>true</CODE>.
	 * 
	 * @param owner
	 *   - the <CODE>Frame</CODE> owner of this <CODE>JDialog</CODE>
	 */
	public HelpDialog(JFrame owner)
	{
		super(owner, "Help", true);
		layoutGUI();
		initHandlers();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void layoutGUI()
	{
		northPanel = new JPanel();
		
		// LIST OF CONTROLS
		controls = new JLabel("<html><center>A<br>S<br>Directional Keys<br>"
				+ "Left Mouse Drag<br>Right Mouse Drag</center></html>");
		controls.setFont(new Font("SansSerif", Font.PLAIN, 12));
		controls.setBorder(BorderFactory.createTitledBorder
				(BorderFactory.createEtchedBorder(), "Control"));
		
		// LIST OF CONTROL FUNCTIONS
		functions = new JLabel("<html>Zoom Out<br>" + "Zoom In<br>" +
				"Move Viewport<br>" + "Marquee Zoom In<br>" + "Drag Map<html>");
		functions.setFont(new Font("SansSerif", Font.PLAIN, 12));
		functions.setBorder(BorderFactory.createTitledBorder
				(BorderFactory.createEtchedBorder(), "Function"));
		
		// ADD THE LABELS TO northPanel
		northPanel.add(controls);
		northPanel.add(functions);
		
		// PANEL CONTAINING OKButton
		southPanel = new JPanel();
		OKButton = new JButton("OK");
		southPanel.add(OKButton);
		
		// ADD ALL THE COMPONENTS TO THIS DIALOG
		add(northPanel, BorderLayout.NORTH);
		add(southPanel, BorderLayout.SOUTH);
		
		pack();
	}
	
	private void initHandlers()
	{
		CloseDialogHandler OKButtonListener = new CloseDialogHandler(this);
		OKButton.addActionListener(OKButtonListener);
	}
}

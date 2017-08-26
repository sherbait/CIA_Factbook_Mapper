package factbook_mapper;

import java.awt.*;
import javax.swing.*;

import dbf_editor.DBFViewer;
import factbook_mapper.events.*;
import shp_renderer.SHPViewer;

/**
 * The main class of an application that can load .shp and .dbf files
 * and render the maps, coloring specific map regions according to the
 * data found in the database file. This class layouts the main window
 * of the CIA Factbook Mapper application and is responsible for linking
 * each component to its respective event handlers.
 * 
 * @author Dinia Gepte
 * <br>CSE219, Spring 2011
 * <br>Stony Brook University
 *
 */
public class FactbookMapper extends JFrame
{	
	/** The directory path containing the .shp and .sbf files. */
	public static final String MAPS_DIRECTORY = "./setup/maps/";
	/** The directory path containing the images for each button. */
	public static final String BUTTONS_ICON_PATH = "./setup/buttons/";
	/** The directory path containing the flag images for each country. */
	public static final String MAP_FLAGS_PATH = "./setup/map_flags/";
	/** The directory path containing saved map images and map settings. */
	public static final String SAVED_FILES_DIRECTORY = "./setup/saved_files/";
	
	private static FactbookMapper factbookMapper;
	
	private FactbookMapperFileManager fileManager; 
	private String mainDirectory;
	
	private SHPViewer mapViewer;
	private DBFViewer tableViewer;
	
	// GUI COMPONENTS
	private JPanel northPanel;
	private JPanel firstPanelInNorthPanel;
	private JButton newMapButton;
	private JButton openFileButton;
	private JButton saveButton;
	private JButton saveAsButton;
	private JButton exportMapButton;
	private JPanel secondPanelInNorthPanel;
	private JButton mapTitleButton;
	private JButton mapColorsButton;
	private JButton countryFlagsButton;
	private JButton countryNamesButton;
	private JButton fieldDescriptionButton;
	private JButton eraseButton;
	private JPanel thirdPanelInNorthPanel;
	private JCheckBox titleCheckBox;
	private JCheckBox fieldCheckBox;
	private JPanel fourthPanelInNorthPanel;
	private JCheckBox flagsCheckBox;
	private JCheckBox legendCheckBox;
	private JPanel fifthPanelInNorthPanel;
	private JCheckBox countryNamesCheckBox;
	private JCheckBox countryInformationCheckBox;
	private JPanel sixthPanelInNorthPanel;
	private JComboBox fieldComboBox;
	private JRadioButton ascendingRadioButton;
	private JRadioButton descendingRadioButton;
	private JPanel seventhPanelInNorthPanel;
	private JButton helpButton;
	private JPanel southPanel;
	private JTabbedPane viewTabbedPane;
	
	/**
	 * Sole constructor that initializes the viewers for both the
	 * .dbf and .shp files. It also layouts the main window and
	 * links each component found in the window to a corresponding
	 * event handler.
	 * 
	 * @see SHPViewer
	 * @see DBFViewer
	 */
	public FactbookMapper()
	{
		mainDirectory = System.getProperty("user.dir");
		fileManager = new FactbookMapperFileManager();
		mapViewer = new SHPViewer();
		tableViewer = new DBFViewer();
		initWindow();
		layoutGUI();
		initHandlers();
		disableButtonsForUnloadedMap();
	}
	
	/**
	 * Returns an object of this class that allows access to other public
	 * methods within this class.
	 * 
	 * @return
	 *   an initialized object of this class.
	 */
	public static FactbookMapper getFactbookMapper()	{	return factbookMapper;	}
	
	/**
	 * Returns the {@link FactbookMapperFileManager} used by the application
	 * responsible for loading and saving files.
	 * 
	 * @return
	 *   the file manager used by the application.
	 */
	public FactbookMapperFileManager getFileManager()	{	return fileManager;		}
	
	/**
	 * Returns the present working directory of this application.
	 * 
	 * @return
	 *   the absolute path of the present working directory
	 */
	public String getMainDirectory()					{	return mainDirectory;	}
	
	/**
	 * Returns the {@link SHPViewer} used by the application to render maps.
	 * 
	 * @return
	 *   the map viewer for rendering .shp data.
	 */
	public SHPViewer getMapViewer()						{	return mapViewer;		}
	
	/**
	 * Returns the {@link DBFViewer} used by the application to display a
	 * database in table format.
	 * 
	 * @return
	 *   the table viewer for displaying .dbf data.
	 */
	public DBFViewer getTableViewer()					{	return tableViewer;		}
	
	/**
	 * Returns the <CODE>JTabbedPane</CODE> containing the table.
	 *  
	 * @return
	 *   the component containing the table.
	 */
	public JTabbedPane getTabbedPane()					{	return viewTabbedPane;	}//TODO
	
	/**
	 * Disables unrelated buttons on the toolbar when there is no loaded
	 * .shp and .dbf file. The buttons disabled are those related to
	 * map rendering and table viewing.
	 */
	public void disableButtonsForUnloadedMap()
	{
		saveButton.setEnabled(false);
		saveAsButton.setEnabled(false);
		exportMapButton.setEnabled(false);
		mapTitleButton.setEnabled(false);
		mapColorsButton.setEnabled(false);
		countryFlagsButton.setEnabled(false);
		countryNamesButton.setEnabled(false);
		fieldDescriptionButton.setEnabled(false);
		eraseButton.setEnabled(false);
		titleCheckBox.setEnabled(false);
		fieldCheckBox.setEnabled(false);
		flagsCheckBox.setEnabled(false);
		legendCheckBox.setEnabled(false);
		countryNamesCheckBox.setEnabled(false);
		countryInformationCheckBox.setEnabled(false);
		fieldComboBox.setEnabled(false);
		ascendingRadioButton.setEnabled(false);
		descendingRadioButton.setEnabled(false);
		helpButton.setEnabled(false);
	}
	
	/**
	 * Enables buttons on the tool bar related to manipulating loaded
	 * .shp and .dbf files.
	 */
	public void enableButtonsForLoadedMap()
	{
		saveButton.setEnabled(true);
		saveAsButton.setEnabled(true);
		exportMapButton.setEnabled(true);
		mapTitleButton.setEnabled(true);
		mapColorsButton.setEnabled(true);
		countryFlagsButton.setEnabled(true);
		countryNamesButton.setEnabled(true);
		fieldDescriptionButton.setEnabled(true);
		eraseButton.setEnabled(true);
		titleCheckBox.setEnabled(true);
		fieldCheckBox.setEnabled(true);
		flagsCheckBox.setEnabled(true);
		legendCheckBox.setEnabled(true);
		countryNamesCheckBox.setEnabled(true);
		countryInformationCheckBox.setEnabled(true);
		fieldComboBox.setEnabled(true);
		ascendingRadioButton.setEnabled(true);
		descendingRadioButton.setEnabled(true);
		helpButton.setEnabled(true);
	}
	
	/**
	 * Disables buttons on the tool bar used for map rendering settings,
	 * and enables buttons for table viewing settings.
	 */
	public void disableButtonsForTableTabSelection()
	{
		mapTitleButton.setEnabled(false);
		mapColorsButton.setEnabled(false);
		countryFlagsButton.setEnabled(false);
		countryNamesButton.setEnabled(false);
		fieldDescriptionButton.setEnabled(false);
		eraseButton.setEnabled(false);
		titleCheckBox.setEnabled(false);
		fieldCheckBox.setEnabled(false);
		flagsCheckBox.setEnabled(false);
		legendCheckBox.setEnabled(false);
		countryNamesCheckBox.setEnabled(false);
		countryInformationCheckBox.setEnabled(false);
		fieldComboBox.setEnabled(true);
		ascendingRadioButton.setEnabled(true);
		descendingRadioButton.setEnabled(true);
		helpButton.setEnabled(false);
	}
	
	/**
	 * Enables buttons on the tool bar for map rendering settings, and disables
	 * buttons for table viewing settings. 
	 */
	public void enableButtonsForMapTabSelection()
	{
		mapTitleButton.setEnabled(true);
		mapColorsButton.setEnabled(true);
		countryFlagsButton.setEnabled(true);
		countryNamesButton.setEnabled(true);
		fieldDescriptionButton.setEnabled(true);
		if (fileManager.isPoliticalMapIsMap())
			eraseButton.setEnabled(false);
		else
			eraseButton.setEnabled(true);
		titleCheckBox.setEnabled(true);
		fieldCheckBox.setEnabled(true);
		flagsCheckBox.setEnabled(true);
		legendCheckBox.setEnabled(true);
		countryNamesCheckBox.setEnabled(true);
		countryInformationCheckBox.setEnabled(true);
		fieldComboBox.setEnabled(false);
		ascendingRadioButton.setEnabled(false);
		descendingRadioButton.setEnabled(false);
		helpButton.setEnabled(true);
	}
	
	/**
	 * TODO
	 */
	public void requestFocusForMapTabbedPane()
	{
		if (viewTabbedPane.getSelectedIndex() == 0)
		{
			mapViewer.getRenderer().requestFocusInWindow();
			enableButtonsForMapTabSelection();
		}
		else
			disableButtonsForTableTabSelection();
	}
	
	/**
	 * Disables the save button on the tool bar.
	 */
	public void disableSaveButton()
	{
		saveButton.setEnabled(false);
	}
	
	/**
	 * Enables the save button on the tool bar.
	 */
	public void enableSaveButton()
	{
		saveButton.setEnabled(true);
	}
	
	/**
	 * Disables the erase button on the tool bar.
	 */
	public void disableEraseButton()
	{
		eraseButton.setEnabled(false);
	}
	
	/**
	 * Enables the erase button on the tool bar.
	 */
	public void enableEraseButton()
	{
		eraseButton.setEnabled(true);
	}
	
	/**
	 * Returns the current visible state of the save button on the tool bar.
	 * 
	 * @return
	 *   <CODE>true</CODE> if the save button is enabled, <CODE>false</CODE>
	 *   otherwise.
	 */
	public boolean isSaveButtonEnabled()
	{
		return saveButton.isEnabled();
	}
	
	/**
	 * Returns the current visible state of the erase button on the tool bar.
	 *  
	 * @return
	 *   <CODE>true</CODE> if the erase button is enabled, <CODE>false</CODE>
	 *   otherwise.
	 */
	public boolean isEraseButtonEnabled()
	{
		return eraseButton.isEnabled();
	}
	
	/**
	 * Loads an Image which can be used for buttons, and makes sure 
	 * it's fully loaded before continuing.
	 * 
	 * @param path
	 *   - the file location, along with corresponding directory (if any),
	 *   of the image to load
	 * @return
	 *   the image loaded from the <CODE>path</CODE>.
	 */
	public Image loadImage(String path)
	{
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image img = tk.getImage(path);
		MediaTracker mt = new MediaTracker(this);
		mt.addImage(img, 0);
		try { mt.waitForID(0); }
		catch (Exception e) { e.printStackTrace(); }
		return img;
	}
	
	/**
	 * Main method for the CIA Factbook Mapper application that creates
	 * an instance of this class.
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		factbookMapper = new FactbookMapper();
		factbookMapper.setVisible(true);
	}

	private void initWindow()
	{
		setTitle("CIA Factbook Mapper");
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}
	
	private void layoutGUI()
	{
		// EDITING OPTIONS PANEL
		northPanel = new JPanel();
		((FlowLayout)northPanel.getLayout()).setAlignment(FlowLayout.LEFT);
		
		// MENU OPTIONS PANEL
		firstPanelInNorthPanel = new JPanel();
		
		// NEW MAP BUTTON
		Image icon = loadImage(BUTTONS_ICON_PATH + "new.png");
		newMapButton = new JButton(new ImageIcon(icon));
		newMapButton.setPreferredSize(new Dimension(36, 36));
		newMapButton.setToolTipText("New Political Map");
		
		// OPEN FILE BUTTON
		icon = loadImage(BUTTONS_ICON_PATH + "open.png");
		openFileButton = new JButton(new ImageIcon(icon));
		openFileButton.setPreferredSize(new Dimension(36, 36));
		openFileButton.setToolTipText("Open");
		
		// SAVE BUTTON
		icon = loadImage(BUTTONS_ICON_PATH + "save.png");
		saveButton = new JButton(new ImageIcon(icon));
		saveButton.setPreferredSize(new Dimension(36, 36));
		saveButton.setToolTipText("Save");
		
		// SAVE AS BUTTON
		icon = loadImage(BUTTONS_ICON_PATH + "save_as.png");
		saveAsButton = new JButton(new ImageIcon(icon));
		saveAsButton.setPreferredSize(new Dimension(36, 36));
		saveAsButton.setToolTipText("Save As");
		
		// EXPORT MAP BUTTON
		icon = loadImage(BUTTONS_ICON_PATH + "export.png");
		exportMapButton = new JButton(new ImageIcon(icon));
		exportMapButton.setPreferredSize(new Dimension(36, 36));
		exportMapButton.setToolTipText("Export Map");
		
		// ADD THE BUTTONS TO THE MENU OPTIONS PANEL
		firstPanelInNorthPanel.add(newMapButton);
		firstPanelInNorthPanel.add(openFileButton);
		firstPanelInNorthPanel.add(saveButton);
		firstPanelInNorthPanel.add(saveAsButton);
		firstPanelInNorthPanel.add(exportMapButton);
		
		// MAP RENDERING OPTIONS PANEL
		secondPanelInNorthPanel = new JPanel();
		
		// MAP TITLE BUTTON
		icon = loadImage(BUTTONS_ICON_PATH + "title.png");
		mapTitleButton = new JButton(new ImageIcon(icon));
		mapTitleButton.setPreferredSize(new Dimension(36, 36));
		mapTitleButton.setToolTipText("Map Title");
		
		// MAP COLORS BUTTON
		icon = loadImage(BUTTONS_ICON_PATH + "color.png");
		mapColorsButton = new JButton(new ImageIcon(icon));
		mapColorsButton.setPreferredSize(new Dimension(36, 36));
		mapColorsButton.setToolTipText("Map Color");
		
		// COUNTRY FLAGS BUTTON
		icon = loadImage(BUTTONS_ICON_PATH + "flag.png");
		countryFlagsButton = new JButton(new ImageIcon(icon));
		countryFlagsButton.setPreferredSize(new Dimension(36, 36));
		countryFlagsButton.setToolTipText("Country Flags");
		
		// COUNTRY NAMES BUTTON
		icon = loadImage(BUTTONS_ICON_PATH + "name.png");
		countryNamesButton = new JButton(new ImageIcon(icon));
		countryNamesButton.setPreferredSize(new Dimension(36, 36));
		countryNamesButton.setToolTipText("Country Names");
		
		// FIELD DESCRIPTION BUTTON
		icon = loadImage(BUTTONS_ICON_PATH + "field.png");
		fieldDescriptionButton = new JButton(new ImageIcon(icon));
		fieldDescriptionButton.setPreferredSize(new Dimension(36, 36));
		fieldDescriptionButton.setToolTipText("Field Description");
		
		// ERASE BUTTON
		icon = loadImage(BUTTONS_ICON_PATH + "erase.png");
		eraseButton = new JButton(new ImageIcon(icon));
		eraseButton.setPreferredSize(new Dimension(36, 36));
		eraseButton.setToolTipText("Clear Map Colors");
		
		// ADD THE BUTTONS TO THE MAP RENDERING OPTIONS PANEL
		secondPanelInNorthPanel.add(mapTitleButton);
		secondPanelInNorthPanel.add(mapColorsButton);
		secondPanelInNorthPanel.add(countryFlagsButton);
		secondPanelInNorthPanel.add(countryNamesButton);
		secondPanelInNorthPanel.add(countryNamesButton);
		secondPanelInNorthPanel.add(fieldDescriptionButton);
		secondPanelInNorthPanel.add(eraseButton);
		
		// DISPLAY OPTIONS PANELS
		thirdPanelInNorthPanel = new JPanel(new GridLayout(2,1,0,-2));
		fourthPanelInNorthPanel = new JPanel(new GridLayout(2,1,0,-2));
		fifthPanelInNorthPanel = new JPanel(new GridLayout(2,1,0,-2));
		
		// CHECK BOXES
		titleCheckBox = new JCheckBox("Title");
		fieldCheckBox = new JCheckBox("Field");
		thirdPanelInNorthPanel.add(titleCheckBox);
		thirdPanelInNorthPanel.add(fieldCheckBox);
		flagsCheckBox = new JCheckBox("Flags");
		legendCheckBox = new JCheckBox("Legend");
		fourthPanelInNorthPanel.add(flagsCheckBox);
		fourthPanelInNorthPanel.add(legendCheckBox);
		countryNamesCheckBox = new JCheckBox("Country Names");
		countryInformationCheckBox = new JCheckBox("Country Information");
		fifthPanelInNorthPanel.add(countryNamesCheckBox);
		fifthPanelInNorthPanel.add(countryInformationCheckBox);
				
		// SORTING OPTIONS PANEL
		sixthPanelInNorthPanel = new JPanel();
		
		// SORT COMPONENTS
		String[] list = {"Field 1", "Field 2", "Field 3", "Field 4", ". . ."};
		fieldComboBox = new JComboBox(list);	//TODO add fields[]
		fieldComboBox.setPreferredSize(new Dimension(108, 25));
		ascendingRadioButton = new JRadioButton("Ascending", true);
		descendingRadioButton = new JRadioButton("Descending");
		// GROUP THE BUTTONS
		ButtonGroup sortGroup = new ButtonGroup();
		sortGroup.add(ascendingRadioButton);
		sortGroup.add(descendingRadioButton);
		
		// ADD THE COMPONENTS TO THE SORTING OPTIONS PANEL
		sixthPanelInNorthPanel.add(fieldComboBox);
		sixthPanelInNorthPanel.add(ascendingRadioButton);
		sixthPanelInNorthPanel.add(descendingRadioButton);
		
		// HELP PANEL
		seventhPanelInNorthPanel = new JPanel();
		
		// HELP BUTTON
		icon = loadImage(BUTTONS_ICON_PATH + "help.png");
		helpButton = new JButton(new ImageIcon(icon));
		helpButton.setPreferredSize(new Dimension(36, 36));
		helpButton.setToolTipText("Help");
		
		// ADD THE BUTTON TO THE HELP PANEL
		seventhPanelInNorthPanel.add(helpButton);
		
		// INITIALIZE SEPARATORS
		JSeparator js1 = new JSeparator(JSeparator.VERTICAL);
		js1.setPreferredSize(new Dimension(2, 32));
		JSeparator js2 = new JSeparator(JSeparator.VERTICAL);
		js2.setPreferredSize(new Dimension(2, 32));
		JSeparator js3 = new JSeparator(JSeparator.VERTICAL);
		js3.setPreferredSize(new Dimension(2, 32));
		
		// ADD ALL COMPONENTS TO THE OPTIONS PANEL
		northPanel.add(firstPanelInNorthPanel);
		northPanel.add(js1);
		northPanel.add(secondPanelInNorthPanel);
		northPanel.add(js2);
		northPanel.add(thirdPanelInNorthPanel);
		northPanel.add(fourthPanelInNorthPanel);
		northPanel.add(fifthPanelInNorthPanel);
		northPanel.add(js3);
		northPanel.add(sixthPanelInNorthPanel);
		northPanel.add(Box.createHorizontalStrut(234));
		northPanel.add(seventhPanelInNorthPanel);
		
		// DISPLAY PANEL
		southPanel = new JPanel(new BorderLayout());
		
		// TABBED PANE
		viewTabbedPane = new JTabbedPane();
		viewTabbedPane.setFocusable(false);
		viewTabbedPane.addTab("Map", mapViewer.getRenderer());
		viewTabbedPane.addTab("Table", tableViewer.getDBFEditor().getJScrollPane());
		
		// ADD THE TABBED PANE TO THE DISPLAY PANEL
		southPanel.add(viewTabbedPane, BorderLayout.CENTER);
		
		// ADD ALL COMPONENTS TO THIS WINDOW
		add(northPanel, BorderLayout.NORTH);
		add(southPanel, BorderLayout.CENTER);
	}
	
	private void initHandlers()
	{	
		SwitchTabHandler sth = new SwitchTabHandler();
		viewTabbedPane.addChangeListener(sth);
		
		NewMapHandler nmh = new NewMapHandler();
		newMapButton.addActionListener(nmh);
		
		OpenHandler oh = new OpenHandler();
		openFileButton.addActionListener(oh);
		
		SaveHandler sh = new SaveHandler();
		saveButton.addActionListener(sh);
		
		SaveAsHandler sah = new SaveAsHandler();
		saveAsButton.addActionListener(sah);
		
		ExportMapHandler emh = new ExportMapHandler();
		exportMapButton.addActionListener(emh);
		
		MapTitleHandler mth = new MapTitleHandler();
		mapTitleButton.addActionListener(mth);
		
		MapColorHandler mch = new MapColorHandler();
		mapColorsButton.addActionListener(mch);
		
		CountryFlagsHandler cfh = new CountryFlagsHandler();
		countryFlagsButton.addActionListener(cfh);
		
		CountryNamesHandler cnh = new CountryNamesHandler();
		countryNamesButton.addActionListener(cnh);
		
		FieldDescriptionHandler fdh = new FieldDescriptionHandler();
		fieldDescriptionButton.addActionListener(fdh);
		
		EraseHandler eh = new EraseHandler();
		eraseButton.addActionListener(eh);
		
		TitleCheckBoxHandler tcbh = new TitleCheckBoxHandler();
		titleCheckBox.addActionListener(tcbh);
		
		FieldCheckBoxHandler ficbh = new FieldCheckBoxHandler();
		fieldCheckBox.addActionListener(ficbh);
		
		FlagsCheckBoxHandler flcbh = new FlagsCheckBoxHandler();
		flagsCheckBox.addActionListener(flcbh);
		
		LegendCheckBoxHandler lcbh = new LegendCheckBoxHandler();
		legendCheckBox.addActionListener(lcbh);
		
		CountryNamesCheckBoxHandler cncbh = new CountryNamesCheckBoxHandler();
		countryNamesCheckBox.addActionListener(cncbh);
		
		CountryInformationCheckBoxHandler cicbh = new CountryInformationCheckBoxHandler();
		countryInformationCheckBox.addActionListener(cicbh);
		
		SortHandler soh = new SortHandler();
		fieldComboBox.addItemListener(soh);
		
		SortIncreasingHandler sih = new SortIncreasingHandler();
		ascendingRadioButton.addActionListener(sih);
		
		SortDecreasingHandler sdh = new SortDecreasingHandler();
		descendingRadioButton.addActionListener(sdh);
		
		HelpHandler hh = new HelpHandler();
		helpButton.addActionListener(hh);
		
		WindowHandler wh = new WindowHandler();
		this.addWindowListener(wh);
		//TODO
	}
}

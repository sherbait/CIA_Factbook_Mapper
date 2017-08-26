package factbook_mapper;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import shp_renderer.SHPRendererSettings;
import shp_renderer.data.SHPMap;
import dbf_editor.data.DBFFileIO;
import dbf_editor.DBFTableManager;
import dbf_editor.data.DBFTable;

/**
 * A class responsible for all file-handling done by the CIA Factbook Mapper
 * application. This includes loading .shp and .dbf files, loading and saving
 * .obj files containing rendering settings, as well as exporting map images
 * to .png files.
 * <p>
 * This class has an <CODE>OBJFileFilter</CODE> class that filters only .obj
 * files to be shown to the user in a <CODE>JFileChooser</CODE>.
 *  
 * @author Dinia Gepte
 *
 */
public class FactbookMapperFileManager
{
	private File selectedFile;
	private boolean savedSinceLastEdit = true;
	private boolean politicalMapIsMap = true;
	private DBFFileIO dbfFileIO = new DBFFileIO();
	private OBJFileFilter objFileFilter = new OBJFileFilter();
	private PNGFileFilter pngFileFilter = new PNGFileFilter();
	
	// MUTATOR METHODS
	/**
	 * Sets the <CODE>savedSinceLastEdit</CODE> instance variable to
	 * the parameter, and enables/disables the save button in the
	 * {@link FactbookMapper} depending on the parameter. It enables
	 * the button if <CODE>flag</CODE> if <CODE>false</CODE>; disables
	 * if <CODE>flag</CODE> is <CODE>true</CODE>.
	 *   
	 * @param flag
	 *   - used to set the value of <CODE>savedSinceLastEdit</CODE>
	 */
	public void markFileAsSaved(boolean flag)
	{
		if (flag)
		{
			savedSinceLastEdit = true;
			FactbookMapper.getFactbookMapper().disableSaveButton();
		}
		else
		{
			savedSinceLastEdit = false;
			FactbookMapper.getFactbookMapper().enableSaveButton();
		}
	}
	
	// ACCESSOR METHODS
	/**
	 * Returns the selected .obj file from the <CODE>JFileChooser</CODE>.
	 * 
	 * @return
	 *   the file containing {@link SHPRendererSettings}
	 */
	public File getSelectedFile()			{	return selectedFile;	}
	
	/**
	 * TODO
	 * @return
	 */
	public boolean isPoliticalMapIsMap()	{	return politicalMapIsMap;	}
	
	/**
	 * Provides a custom response when the user closes the application.
	 */
	public void processExitProgramRequest()
	{
		if (!savedSinceLastEdit)
		{
			int selection = showSavePrompt();
			if (selection == JOptionPane.OK_OPTION)
				processSaveAsRequest();
			else if (selection == JOptionPane.NO_OPTION)
				System.exit(0);
			else
				return;
		}
		else
			System.exit(0);		
	}
	
	/**
	 * Provides a response when the user wants to open a new political map.
	 * Response includes loading .shp and .dbf files, and setting appropriate
	 * tool bar buttons response.
	 */
	public void processNewRequest()
	{
		if (!savedSinceLastEdit)
		{
			int selection = showSavePrompt();
			if (selection == JOptionPane.YES_OPTION)
				processSaveAsRequest();
			else if (selection == JOptionPane.NO_OPTION);
			else
				return;
		}
		loadTable();
		loadPoliticalMap();
		FactbookMapper.getFactbookMapper().enableButtonsForLoadedMap();
		FactbookMapper.getFactbookMapper().requestFocusForMapTabbedPane();
		markFileAsSaved(true);	
	}
	
	/**
	 * Provides a response when the user wants to open a previously saved
	 * .obj file containing map rendering settings. 
	 */
	public void processOpenRequest()
	{
		if (!savedSinceLastEdit)
		{
			int selection = showSavePrompt();
			if (selection == JOptionPane.OK_OPTION)
				processSaveAsRequest();
			else if (selection == JOptionPane.NO_OPTION);
			else
				return;
		}
		
		// DIRECTORY WHERE SAVED FILES ARE STORED
		JFileChooser jfc = new JFileChooser("setup/saved_files");
		
		// ONLY .obj FILES WILL BE SHOWN
		jfc.setFileFilter(objFileFilter);
		
		// DISPLAY THE DIALOG
		int result = jfc.showOpenDialog(FactbookMapper.getFactbookMapper());
		if (result == JFileChooser.APPROVE_OPTION)
		{
			selectedFile = jfc.getSelectedFile();
			
			// USER HAS NOT SELECTED A FILE
			if (selectedFile == null)
				JOptionPane.showMessageDialog(FactbookMapper.getFactbookMapper(), 
						"ERROR - No selected file.", "CIA Factbook Mapper", 
						JOptionPane.ERROR_MESSAGE);
			else
			{
				try
				{
					// INPUT STREAM
					FileInputStream fis = new FileInputStream(selectedFile);
					ObjectInputStream ois = new ObjectInputStream(fis);
					FactbookMapper.getFactbookMapper().getMapViewer().getRenderer().
						setSHPRendererSettings((SHPRendererSettings) ois.readObject());
					
					// LOAD THE SETTINGS
					loadSHPRendererSettings();
					
					// TREAT A NEWLY OPENED FILE AS SAVED
					markFileAsSaved(true);
					
					// TREAT AN OPENED MAP AS NOT A POLITICAL MAP
					politicalMapIsMap = false;
					
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(FactbookMapper.getFactbookMapper(), 
							"Error loading file " +	selectedFile.toString() + 
							". Either the file is corrupted or a wrong file format.",	
							"CIA Factbook Mapper", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	
	/**
	 * Provides a response when the user wants to save the current
	 * rendering settings.
	 */
	public void processSaveRequest()
	{
		if (selectedFile == null)
			processSaveAsRequest();
		else
		{
			try 
			{
				// OUTPUT STREAM
				FileOutputStream fos = new FileOutputStream(selectedFile);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(FactbookMapper.getFactbookMapper().
						getMapViewer().getRenderer().getSHPRendererSettings());
				
				// FILE IS SAVED
				markFileAsSaved(true);
			} 
			catch (Exception e) 
			{
				JOptionPane.showMessageDialog(FactbookMapper.getFactbookMapper(), 
						"Error saving file to " +	selectedFile.toString() + 
						". Either it is not being saved properly or it is a wrong file format.",	
						"CIA Factbook Mapper", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	/**
	 * Provides a response when the user wants to save the current
	 * rendering settings to a different file name.
	 */
	public void processSaveAsRequest()
	{
		// DIRECTORY WHERE WE SAVE THE FILES
		JFileChooser jfc = new JFileChooser("setup/saved_files");
		
		// ONLY DISPLAY .obj FILES
		jfc.setFileFilter(objFileFilter);
		
		// FILE WE WANT TO SAVE TO
		jfc.setSelectedFile(selectedFile);
		
		// DISPLAY SAVE AS DIALOG
		int result = jfc.showSaveDialog(FactbookMapper.getFactbookMapper());
		if (result == JFileChooser.APPROVE_OPTION)
		{
			String fileNameTest = jfc.getSelectedFile().getName();
			
			// MAKES SURE THE USER-INPUT FILE NAME WILL BE A .obj FILE
			if (!fileNameTest.contains(".obj"))
				selectedFile = new File("setup/saved_files/" + fileNameTest + ".obj");
			else
				selectedFile = jfc.getSelectedFile();
			processSaveRequest();
		}
	}
	
	/**
	 * Provides a response when the user wants to export the current
	 * map to a .png image for external use.
	 */
	public void processExportRequest()
	{
		// DIRECTORY WHERE WE SAVE THE FILES
		JFileChooser jfc = new JFileChooser("setup/saved_files");
		
		// IMAGE FILE NAME
		File imageFile;
		
		// ONLY DISPLAY .png FILES
		jfc.setFileFilter(pngFileFilter);
		
		// DISPLAY SAVE AS DIALOG
		int result = jfc.showSaveDialog(FactbookMapper.getFactbookMapper());
		if (result == JFileChooser.APPROVE_OPTION)
		{
			String fileNameTest = jfc.getSelectedFile().getName();
			
			// MAKES SURE THE USER-INPUT FILE NAME WILL BE A .png FILE
			if (!fileNameTest.contains(".png"))
				imageFile = new File("setup/saved_files/" + fileNameTest + ".png");
			else
				imageFile = jfc.getSelectedFile();
			
			// GET IMAGE DIMENSIONS
			int width = FactbookMapper.getFactbookMapper().getMapViewer().getRenderer().getWidth();
			int height = FactbookMapper.getFactbookMapper().getMapViewer().getRenderer().getHeight();
			
			try
			{
				// BUFFERED IMAGE OBJECT WITH SAME DIMENSION AS RENDERING PANEL
				BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				
				// IMAGE OBJECT'S GRAPHICS CONTEXT
				Graphics2D g2 = bi.createGraphics();
				
				// DRAW THE MAP ON THE IMAGE
				FactbookMapper.getFactbookMapper().getMapViewer().getRenderer().renderMap(g2);
				// TODO ADD METHOD THAT DISPLAYS MAP WITH LEGEND AND STUFF
				
				// WRITE THE IMAGE
				ImageIO.write(bi, "png", imageFile);
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(FactbookMapper.getFactbookMapper(), 
						"Error saving file to " +	imageFile.toString() + 
						". Either it is not being saved properly or it is a wrong file format.",
						"CIA Factbook Mapper", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void loadTable()
	{
		// GET THE FILE THE USER SELECTED
		File factbookFile = new File(FactbookMapper.getFactbookMapper().getMainDirectory()
				+ FactbookMapper.MAPS_DIRECTORY, "CIAFactbook.dbf");
		File fieldDescriptionFile = new File(FactbookMapper.getFactbookMapper().getMainDirectory()
				+ FactbookMapper.MAPS_DIRECTORY, "CIAFactbookFieldDescriptions.dbf");
		
		try
		{
			// READ IN THE DATA
			DBFTable tableData = dbfFileIO.loadDBF(factbookFile);
			DBFTable fieldDescriptionsData = dbfFileIO.loadDBF(fieldDescriptionFile);
			
			// CREATE A COPY THAT WILL BE EDITABLE 
			DBFTableManager tableManager = new DBFTableManager(tableData, fieldDescriptionsData);
			
			// SAVE IT
			FactbookMapper.getFactbookMapper().getTableViewer().setDBFTableManager(tableManager);
			
			// AND PUT IT IN OUR JTable
			FactbookMapper.getFactbookMapper().getTableViewer().loadDBFIntoTable(tableData);
		}
		catch(Exception e)
		{
			// DISASTER
			JOptionPane.showMessageDialog(	FactbookMapper.getFactbookMapper(),
					"Error Loading " + factbookFile + ": Either the file is in the incorrect format or it is being loaded improperly",
					"Error Loading " + factbookFile,
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void loadPoliticalMap()
	{
		File shapeFile = new File(FactbookMapper.getFactbookMapper().getMainDirectory()
				+ FactbookMapper.MAPS_DIRECTORY, "world.shp");
		
		try
		{
			SHPMap map = FactbookMapper.getFactbookMapper().getMapViewer().getLoader().loadShapefile(shapeFile);
			
			// AND UPDATE THE GUI
			FactbookMapper.getFactbookMapper().getMapViewer().setMap(map);
			FactbookMapper.getFactbookMapper().getMapViewer().getRenderer().initMapColors();
			FactbookMapper.getFactbookMapper().getMapViewer().getRenderer().zoomToMapBounds();
			FactbookMapper.getFactbookMapper().getMapViewer().getRenderer().repaint();
			
		} catch (IOException e)
		{
			// THIS WOULD HAPPEN DURING A LOADING ERROR
			JOptionPane.showMessageDialog(FactbookMapper.getFactbookMapper(), "Error loading " + 
					shapeFile, "Error loading " + shapeFile, JOptionPane.ERROR_MESSAGE);
		}
		politicalMapIsMap = true;
	}
	
	private void loadSHPRendererSettings()
	{
		
	}
	
	private void saveSHPRendererSettings()
	{
		
	}
	
	private int showSavePrompt()
	{
		String[] options = {"Yes", "No", "Cancel"};
		return JOptionPane.showOptionDialog(FactbookMapper.getFactbookMapper(), 
				"Do you wish to save before continuing?",
				"CIA Factbook Mapper", JOptionPane.YES_NO_CANCEL_OPTION, 
				JOptionPane.WARNING_MESSAGE, null, options, options[2]);
	}
	
	/**
	 * A class that filters only .obj files to be displayed to the user.
	 * 
	 * @author Dinia Gepte
	 *
	 */
	class OBJFileFilter extends FileFilter
	{

		/**
		 * This method only allows the user to view .obj files inside the JFileChooser.
		 */
		public boolean accept(File file) 
		{
			return file.getName().endsWith(".obj");
		}

		/**
		 * This description will be displayed inside the dialog.
		 */
		public String getDescription() 
		{
			return ".obj";
		}
	}
	
	/**
	 * A class that filters only .png files to be displayed to the user.
	 * 
	 * @author Dinia Gepte
	 * @author Dinia
	 *
	 */
	class PNGFileFilter extends FileFilter
	{
		public boolean accept(File file)
		{
			return file.getName().endsWith(".png");
		}

		public String getDescription()
		{
			return ".png";
		}
	}
}

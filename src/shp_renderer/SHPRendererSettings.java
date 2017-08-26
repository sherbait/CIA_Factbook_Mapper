package shp_renderer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.io.Serializable;

/**
 * The class that stores information about the rendering settings
 * specified by the user.
 * 
 * @author Dinia Gepte
 *
 */
public class SHPRendererSettings implements Serializable 
{
	private String mapTitle;
	private Font mapTitleFont;
	private Color mapTitleColor;
	private Point mapTitleCenterCoords;
	private String coloringField;
	private Color coloringGradientStart;
	private Color coloringGradientEnd;
	private double countryFlagsScale;
	private Font countryNamesFont;
	private Color countryNamesColor;
	private Font fieldDescriptionFont;
	private Color fieldDescriptionColor;
	private Point fieldDescriptionCenterCoords;
	private boolean titleSelection;
	private boolean fieldSelection;
	private boolean flagsSelection;
	private boolean legendSelection;
	private boolean countryNamesSelection;
	private boolean countryInformationSelection;
	
	// ACCESSOR METHODS
	public String getMapTitle()							{	return mapTitle;						}
	public Font getMapTitleFont()						{	return mapTitleFont;					}
	public Color getMapTitleColor()						{	return mapTitleColor;					}
	public Point getMapTitleCenterCoords()				{	return mapTitleCenterCoords;			}
	public String getColoringField()					{	return coloringField;					}
	public Color getColoringGradientStart()				{	return coloringGradientStart;			}
	public Color getColoringGradientEnd()				{	return coloringGradientEnd;				}
	public double getCountryFlagsScale()				{	return countryFlagsScale;				}
	public Font getCountryNamesFont()					{	return countryNamesFont;				}
	public Color getCountryNamesColor()					{	return countryNamesColor;				}
	public Font getFieldDescriptionFont()				{	return fieldDescriptionFont;			}
	public Color getFieldDescriptionColor()				{	return fieldDescriptionColor;			}
	public Point getFieldDescriptionCenterCoords()		{	return fieldDescriptionCenterCoords;	}
	public boolean isTitleSelected()					{	return titleSelection;					}
	public boolean isFieldSelected()					{	return fieldSelection;					}
	public boolean isFlagsSelected()					{	return flagsSelection;					}
	public boolean isLegendSelected()					{	return legendSelection;					}
	public boolean isCountryNamesSelected()				{	return countryNamesSelection;			}
	public boolean isCountryInformationSelected()		{	return countryInformationSelection;		}
	
	
	// MUTATOR METHDOS
	public void setMapTitle(String name)				{	mapTitle = name;						}
	public void setMapTitleFont(Font font)				{	mapTitleFont = font;					}
	public void setMapTitleColor(Color color)			{	mapTitleColor = color;					}
	public void setMapTitleCenterCoords(Point coords)	{	mapTitleCenterCoords = coords;			}
	public void setColoringField(String fieldName)		{	coloringField = fieldName;				}
	public void setColoringGradient(Color start, Color end)
	{
		coloringGradientStart = start;
		coloringGradientEnd = end;
	}
	public void setCountryFlagsScale(double scale)		{	countryFlagsScale = scale;				}
	public void setCountryNamesFont(Font font)			{	countryNamesFont = font;				}
	public void setCountryNamesColor(Color color)		{	countryNamesColor = color;				}
	public void setFieldDescriptionFont(Font font)		{	fieldDescriptionFont = font;			}
	public void setFieldDescriptionColor(Color color)	{	fieldDescriptionColor = color;			}
	public void setFieldDescriptionCenterCoords(Point coords)		{	fieldDescriptionCenterCoords = coords;	}
	public void setTitleSelection(boolean selection)				{	titleSelection = selection;				}
	public void setFieldSelection(boolean selection)				{	fieldSelection = selection;				}
	public void setFlagsSelection(boolean selection)				{	flagsSelection = selection;				}
	public void setLegendSelection(boolean selection)				{	legendSelection = selection;			}
	public void setCountryNamesSelection(boolean selection)			{	countryNamesSelection = selection;	}
	public void setCountryInformationSelection(boolean selection)	{	countryInformationSelection = selection;	}
}

package shp_renderer;

import factbook_mapper.FactbookMapper;
import shp_renderer.data.SHPDataLoader;
import shp_renderer.data.SHPMap;

/**
 * A class that contains the necessary classes to display .shp data.
 * It has the instances {@link SHPMap}, {@link SHPDataLoader}, and
 * {@link SHPRenderer}.
 * 
 * @author Dinia Gepte
 *
 */
public class SHPViewer
{
	private SHPMap mapData;
	private SHPDataLoader loader;
	private SHPRenderer renderer;
	
	/**
	 * Constructor that initializes all instances except for
	 * <CODE>SHPData</CODE> which is determined based on
	 * user selection.
	 */
	public SHPViewer()
	{
		mapData = null;
		loader = new SHPDataLoader();
		renderer = new SHPRenderer(this);
	}
	
	// ACCESSOR METHODS
	public SHPDataLoader getLoader()	{	return loader;		}
	public SHPMap getMapData()			{	return mapData;		}
	public SHPRenderer getRenderer()	{	return renderer;	}
	
	// MUTATOR METHODS
	public void setMap(SHPMap initMap)	{	mapData = initMap;	}
}

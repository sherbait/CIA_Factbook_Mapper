package shp_renderer.geometry;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.Vector;
/**
 * This class is used to represent a shapefile polygon type, which
 * means it stores a series of polygons, called parts. 
 * 
 * @author Richard McKenna and Dinia Gepte
 */
public class SHPPolygon extends SHPPolyType
{	
	// POLYGON OBJECTS
	private Vector<Polygon> polygons;
	
	/**
	 * This constructor fully initializes all data needed for use.
	 */
	public SHPPolygon(	double[] initBoundingBox,
						int initNumBytes,
						int initNumParts,
						int initNumPoints,
						int[] initParts,
						double[] initXPointsData,
						double[] initYPointsData)
	{
		super(initBoundingBox, initNumBytes, initNumParts, initNumPoints, initParts, initXPointsData, initYPointsData);
		polygons = new Vector<Polygon>();
	}

	/**
	 * This method provides the implementation for rendering polygons. Note that
	 * it will go through and render each part.
	 */
	public void render(	Graphics2D g2, 
						double viewportCenterX, double viewportCenterY,
						int panelWidth, int panelHeight,
						double width, double height) 
	{	
		polygons.clear();
		
		// FOR ALL PARTS (POLYGONS)
		for (int a = 0; a < numParts; a++)
		{
			// DETERMINE HOW MANY POINTS ARE IN THIS PART
			int size = calculateSize(a);

			// AND FILL OUR POINTS ARRAYS WITH DATA
			this.fillData(a, size, xRenderData, yRenderData, viewportCenterX, viewportCenterY, panelWidth, panelHeight, width, height);

			// THEN USE THEM TO RENDER
			g2.setColor(fillColor);
			g2.fillPolygon(xRenderData, yRenderData, size);
			g2.setColor(lineColor);
			g2.drawPolygon(xRenderData, yRenderData, size);
			
			// CONSTRUCT THE POLYGON
			polygons.add(new Polygon(xRenderData, yRenderData, size));
		}
	}
	
	/**
	 * This determines whether a set of points given by the parameters
	 * is within any of the polygons in this class.
	 */
	public boolean containPoints(int x, int y)
	{
		for (int i = 0; i < polygons.size(); i++)
			if (polygons.get(i).contains(x, y))
				return true;
		return false;
	}
}
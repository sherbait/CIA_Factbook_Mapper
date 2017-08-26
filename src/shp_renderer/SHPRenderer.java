package shp_renderer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.util.Iterator;

import javax.swing.JPanel;

import factbook_mapper.events.KeyHandler;
import factbook_mapper.events.MouseHandler;

import shp_renderer.data.SHPMap;
import shp_renderer.geometry.SHPShape;

/**
 * The class that does all the map rendering inside of a panel (itself).
 * It knows how to render the shapes. It also manages the viewport,
 * which means the window on the map that is currently viewed.
 * 
 * @author Richard McKenna and Dinia Gepte
 *
 */
public class SHPRenderer extends JPanel
{
	private SHPViewer viewer;
	private SHPRendererSettings settings;
	private double viewportCenterX;
	private double viewportCenterY;
	private double scale;
	private Rectangle lasso;
	private int mouseXCoord;
	private int mouseYCoord;
	private double longWidth;
	private double latHeight;
	private double leftEdge;
	private double rightEdge;
	private double topEdge;
	private double bottomEdge;
	
	/**
	 * Constructor that gives default values to the viewport and
	 * background (blue), but it doesn't know what to draw until
	 * the user opens a new or a previously saved map.
	 */
	public SHPRenderer(SHPViewer initViewer)
	{
		viewer = initViewer;
		settings = new SHPRendererSettings();	//TODO
		viewportCenterX = 0;
		viewportCenterY = 0;
		//mouseXCoord = 0;//TODO
		//mouseYCoord = 0;
		scale = 1;
		lasso = new Rectangle();
		setBackground(Color.blue);
		initHandlers();
	}
	
	// ACCESSOR METHODS
	/**
	 * Returns the x-coordinate of the viewport center relative
	 * to the whole panel.
	 * 
	 * @return
	 *   the x-coordinate of the viewport center.
	 */
	public double getViewportCenterX() 		{ return viewportCenterX; }
	
	/**
	 * Returns the y-coordinate of the viewport center relative
	 * to the whole panel.
	 * 
	 * @return
	 *   the y-coordinate of the viewport center.
	 */
	public double getViewportCenterY() 		{ return viewportCenterY; }
	public double getLongWidth()			{ return longWidth;	}
	public double getLatHeight()			{ return latHeight;	}
	public SHPViewer getViewer()			{ return viewer;	}
	
	/**
	 * Returns the <CODE>SHPRendererSettings</CODE> used by the application
	 * to store rendering settings selected by the user.
	 * 
	 * @return
	 *   the renderer settings object containing information on how
	 *   elements are rendered on the map.
	 */
	public SHPRendererSettings getSHPRendererSettings()		{ return settings;	}
	//public Rectangle getLasso()				{ return lasso;		}
	
	// MUTATOR METHODS
	/**
	 * Sets the <CODE>SHPRendererSettings</CODE> to be used by the application
	 * to store rendering settings selected by the user.
	 * 
	 * @param initSettings
	 *   - the settings to be used to store information on how elements
	 *   are rendered on the map
	 */
	public void setSHPRendererSettings(SHPRendererSettings initSettings)
	{
		settings = initSettings;
	}
	
	/**
	 * Resizes the <CODE>Rectangle</CODE> (lasso) rendered on the
	 * viewport given the parameters.
	 * 
	 * @param initX
	 *   - the x-coordinate where the lasso will begin rendering
	 * @param initY
	 *   - the y-coordinate where the lasso will begin rendering
	 * @param finalX
	 *   - the x-coordinate where the lasso will stop rendering
	 * @param finalY
	 *   - the y-coordinate where the lasso will stop rendering
	 */
	public void updateLasso(int initX, int initY, int finalX, int finalY)
	{
		// RECTANGLE DIMENSIONS
		int width = finalX - initX;
		int height = finalY - initY;
		
		// USED SO THAT THE RECTANGLE CAN BE DRAWN IN ANY DIRECTION,
		// WITHOUT HAVING NEGATIVE WIDTH AND HEIGHT
		if (width < 0)
		{
			initX += width;
			width = -width;
		}
		if (height < 0)
		{
			initY += height;
			height = -height;
		}
		lasso.setBounds(initX, initY, width, height);
	}
	
	/**
	 * Resets all values, including dimension, of the lasso to 0.
	 */
	public void clearLasso()
	{
		lasso.setRect(0, 0, 0, 0);
	}
	
	/**
	 * Sets the center of the viewport to the location indicated by
	 * the parameters. The parameters are in latitude and longitude terms.
	 * The viewport is corrected if necessary.
	 * 
	 * @param x
	 *   - a <CODE>double</CODE> value representing the longitude coordinate 
	 * @param y
	 *   - a <CODE>doble</CODE> value representing the latitude coordinate
	 */
	public void setViewportCenter(double x, double y)
	{
		viewportCenterX = x;
		viewportCenterY = y;
		correctViewport();
	}
	
	/**
	 * Sets the pixel coordinates of the mouse in the viewport
	 * by the given parameters.
	 * 
	 * @param x
	 *   - the x-coordinate of the mouse
	 * @param y
	 *   - the y-coordinate of the mouse
	 */
	public void setMouseCoord(int x, int y)
	{
		mouseXCoord = x;
		mouseYCoord = y;
	}
	
	/**
	 * This method is called by the Swing and AWT libraries every time
	 * the panel is displayed and then when we repaint it. All needed rendering
	 * on the panel must be done here each time.
	 */
	public void paintComponent(Graphics g)
	{
		// CLEAR THE PANEL TO THE BACKGROUND COLOR (BLUE FOR THE OCEAN)
		super.paintComponent(g);
		
		// INITIALIZE SOME VARIABLES THAT ARE NEEDED
		// FOR RENDERING AND MIGHT CHANGE IF THE
		// VIEWPORT CHANGES
		initBatchRenderingData();

		// ONLY RENDER THE MAP IF THE USER HAS SELECTED IT
		if (viewer.getMapData() != null)
			renderMap(g);
	}
	
	/**
	 * This method should be called whenever a new map is loaded. It
	 * determines the fill color of each polygon. As it is, it uses
	 * shades of green, with those near the north pole a light green
	 * and with polygons getting gradually darker as we go south.
	 */
	public void initMapColors()
	{
		SHPMap mapData = viewer.getMapData();
		Iterator<SHPShape> shapesIt = mapData.shapesIterator();

		// PICK COLORS FOR ALL SHAPES
		while (shapesIt.hasNext())
		{
			SHPShape shape = shapesIt.next();
			double[] boundingBox = shape.getBoundingBox();
			double topY = boundingBox[3];
			topY += 90;
			
			double yPercentage = (topY/180.0);			
			int r = 0;
			int g = (int)(255 * yPercentage);
			int b = 0;
			if (r < 0) r = 0; if (r > 255) r = 255;
			if (g < 0) g = 0; if (g > 255) g = 255;
			if (b < 0) b = 0; if (b > 255) b = 255;
			Color fillColor = new Color(r,g,b);
			shape.setFillColor(fillColor);
		}
	}
	
	/**
	 * This method updates necessary rendering variables that may
	 * change if the user chooses to zoom in or move the viewport.
	 */
	private void initHandlers()
	{
		// TODO
		KeyHandler kh = new KeyHandler(this);
		this.addKeyListener(kh);
		this.setFocusable(true);
		
		MouseHandler mh = new MouseHandler(this);
		this.addMouseListener(mh);
		this.addMouseMotionListener(mh);
	}
	
	private void initBatchRenderingData()
	{
		longWidth = 360/scale;
		latHeight = 180/scale;
		leftEdge = viewportCenterX - (longWidth/2);
		rightEdge = viewportCenterX + (longWidth/2);
		topEdge = viewportCenterY + (latHeight/2);
		bottomEdge = viewportCenterY - (latHeight/2);
	}
	
	public void renderMap(Graphics g)
	{
		// WE'LL USE Graphics2D METHODS
		Graphics2D g2 = (Graphics2D)g;
		SHPMap mapData = viewer.getMapData();
		Iterator<SHPShape> shapesIt = mapData.shapesIterator();
		SHPShape shapeHovered = null;
		
		// RENDER ALL THE SHAPES
		while (shapesIt.hasNext())
		{
			SHPShape shape = shapesIt.next();
			
			// ONLY RENDER THE SHAPE IF IT IS INSIDE THE VIEWPORT
			if (isInViewport(shape))
			{
				shape.render(g2, viewportCenterX, viewportCenterY,
						getWidth(), getHeight(), longWidth, latHeight);
				if (shape.containPoints(mouseXCoord, mouseYCoord))
					shapeHovered = shape;
			}	
			
			// ALWAYS SET THE COLOR BACK TO BLACK (THE DEFAULT)
			g2.setColor(Color.black);
		}
		
		if (shapeHovered != null)
			this.highlight(shapeHovered, g2);
		//if (lasso != null)
		renderMapLines(g2);
		layoutLasso(g2);
		
	}
	
	private void renderMapLines(Graphics2D g2)
	{
		// CURRENT STROKE OF THE RENDERER
		Stroke defaultStroke = g2.getStroke();
		
		// COLOR OF THE MAP LINES
		g2.setColor(Color.darkGray);
		
		// DEGREE OF THE TROPICS NORTH AND SOUTH OF THE EQUATOR
		double tropicDegree = 26.44;
		
		// EQUATORIAL LINE
		g2.drawLine(xCoordinateToPixel(-180), yCoordinateToPixel(0), 
				xCoordinateToPixel(180), yCoordinateToPixel(0));
		
		// TROPIC OF CANCER
		g2.drawLine(xCoordinateToPixel(-180), yCoordinateToPixel(tropicDegree), 
				xCoordinateToPixel(180), yCoordinateToPixel(tropicDegree));
		
		// TROPIC OF CAPRICORN
		g2.drawLine(xCoordinateToPixel(-180), yCoordinateToPixel(-tropicDegree), 
				xCoordinateToPixel(180), yCoordinateToPixel(-tropicDegree));
		
		// DEGREE LINES OF THE MAP
		int[] latLongLines = {15, 30, 45, 60, 75, 90, 105, 120, 135, 150, 165, 180};
		
		// STROKE THAT WE WILL BE USING TO DRAW THE LONGITUDE/LATITUDE LINES
		BasicStroke stroke = new BasicStroke(1.0f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_MITER, 1.0f, new float[] {1.0f, 4.0f}, 0);
		g2.setStroke(stroke);
		
		// LONGITUDE LINE OF DEGREE 0
		g2.drawLine(xCoordinateToPixel(0), yCoordinateToPixel(90),
			    xCoordinateToPixel(0), yCoordinateToPixel(-90));
		
		// LONGITUDE LINES
		for (int i = 0; i < 11; i++)
		{
			g2.drawLine(xCoordinateToPixel(latLongLines[i]), yCoordinateToPixel(90),
				    xCoordinateToPixel(latLongLines[i]), yCoordinateToPixel(-90));
			g2.drawLine(xCoordinateToPixel(-latLongLines[i]), yCoordinateToPixel(90),
				    xCoordinateToPixel(-latLongLines[i]), yCoordinateToPixel(-90));
		}
		
		// LATITUDE LINES
		for (int i = 0; i < 5; i++)
		{
			g2.drawLine(xCoordinateToPixel(-180), yCoordinateToPixel(latLongLines[i]),
				    xCoordinateToPixel(180), yCoordinateToPixel(latLongLines[i]));
			g2.drawLine(xCoordinateToPixel(-180), yCoordinateToPixel(-latLongLines[i]),
				    xCoordinateToPixel(180), yCoordinateToPixel(-latLongLines[i]));
		}
		
		// SET THE DRAWING LINE BACK TO DEFAULT AFTER DRAWING THE LONG/LAT LINES
		g2.setStroke(defaultStroke);
		g2.setColor(Color.black);
	}
	
	private void highlight(SHPShape s, Graphics2D g2)
	{
		s.setLineColor(Color.cyan);
		s.render(g2, viewportCenterX, viewportCenterY, 
			this.getWidth(), this.getHeight(), longWidth, latHeight);
		s.setLineColor(Color.black);
	}
	
	private void layoutLasso(Graphics2D g2)
	{
		g2.setColor(Color.black);
		g2.drawRect((int)lasso.getX(), (int)lasso.getY(),
				(int)lasso.getWidth(), (int)lasso.getHeight());
	}
	
	public void incViewportCenterX()	{ viewportCenterX += 1; correctViewport(); }
	public void decViewportCenterX()	{ viewportCenterX -= 1; correctViewport(); }
	public void incViewportCenterY()	{ viewportCenterY += 1; correctViewport(); }
	public void decViewportCenterY()	{ viewportCenterY -= 1; correctViewport(); }
	
	/**
	 * This method makes sure the viewport has not gone off the map. If the
	 * latitude or longitude is illegal, it gets corrected.
	 */
	private void correctViewport()
	{
		double longWidth = 360/scale;
		double minLong = viewportCenterX - (longWidth/2);
		double maxLong = viewportCenterX + (longWidth/2);

		double latHeight = 180/scale;
		double minLat = viewportCenterY - (latHeight/2);
		double maxLat = viewportCenterY + (latHeight/2);
	
		// CORRECT VIEWPORT IF IT WENT OFF THE WESTERN EDGE
		double diff = minLong - (-180);
		if (diff < 0)
		{
			viewportCenterX -= diff;
		}
		// OR OFF THE EASTERN EDGE
		else
		{
			diff = 180 - maxLong;
			if (diff < 0)
			{
				viewportCenterX += diff;
			}
		}

		// OR OFF THE SOUTHERN EDGE
		diff = minLat - (-90);
		if (diff < 0)
		{
			viewportCenterY -= diff;			
		}
		// OR THE NORTHERN EDGE
		else
		{
			diff = 90 - maxLat;
			if (diff < 0)
			{
				viewportCenterY += diff;
			}
		}
	}
	
	private boolean isInViewport(SHPShape shape)
	{
		double[] bb = shape.getBoundingBox();
		boolean rightTest 	= bb[0] > rightEdge;
		boolean topTest 	= bb[1] > topEdge;
		boolean leftTest 	= bb[2] < leftEdge;
		boolean bottomTest	= bb[3] < bottomEdge;
		
		if (rightTest || topTest || leftTest || bottomTest)
			return false;
		else
			return true;
	}
	
	/**
	 * Zoom the viewport slightly in.
	 */
	public void zoomIn()
	{
		scale *= 1.1;
	}

	/**
	 * Zoom the viewport slightly out and correct if necessary.
	 */
	public void zoomOut()
	{
		scale /= 1.1;
		if (scale < 1.0)
			scale = 1.0;
		correctViewport();
	}

	/**
	 * This can be used to zoom to perfectly fit the bounding box of
	 * a shape.
	 */
	public void zoomToMapBounds()
	{
		SHPMap mapData = viewer.getMapData();
		double[] mapBounds = mapData.getShapefileData().getMBR();
		zoom(	xCoordinateToPixel(mapBounds[0]),
				yCoordinateToPixel(mapBounds[3]),
				xCoordinateToPixel(mapBounds[2]),
				yCoordinateToPixel(mapBounds[1]));
	}
	
	/**
	 * This method will zoom the viewport in on the region
	 * represented by the x,y arguments. Note that these are
	 * pixel coordinates.
	 * 
	 * @param x1
	 *   - the initial x-coordinate
	 * @param y1
	 *   - the initial y-coordinate
	 * @param x2
	 *   - the final x-coordinate
	 * @param y2
	 *   - the final y-coordinate
	 */
	public void zoom(int x1, int y1, int x2, int y2)
	{//TODO to edit
		// ONLY ZOOM IF THIS IS A VALID ZOOM RECTANGLE
		//if ((x2 > x1) && (y2 > y1))
		{			
			double longWidth = 360/scale;
			double latHeight = 180/scale;
			double longPerPixel = longWidth/getWidth();
			double latPerPixel = latHeight/getHeight();
			
			double longDist1 = 0;
			double longDist2 = 0;
			double latDist1 = 0;
			double latDist2 = 0;
			
			double long1 = 0;
			double long2 = 0;
			double lat1 = 0;
			double lat2 = 0;
			
			if ((x2 > x1) && y2 > y1)
			{
				longDist1 = (x1 - (getWidth()/2)) * longPerPixel;
				longDist2 = (x2 - (getWidth()/2)) * longPerPixel;
				latDist1 = ((getHeight()/2) - y1) * latPerPixel;
				latDist2 = ((getHeight()/2) - y2) * latPerPixel;
				
				long1 = longDist1 + viewportCenterX;
				long2 = longDist2 + viewportCenterX;
				lat1 = latDist1 + viewportCenterY;
				lat2 = latDist2 + viewportCenterY;
				
				viewportCenterX = ((long1 + long2)/2);
				viewportCenterY = ((lat1 + lat2)/2);
			}
			// BOTTOM LEFT TO TOP RIGHT
			else if ((x2 > x1) && y1 > y2)
			{
				longDist1 = (x1 - (getWidth()/2)) * longPerPixel;
				longDist2 = (x2 - (getWidth()/2)) * longPerPixel;
				latDist1 = (y1 - (getHeight()/2)) * latPerPixel;
				latDist2 = (y2 - (getHeight()/2)) * latPerPixel;
				
				long1 = longDist1 + viewportCenterX;
				long2 = longDist2 + viewportCenterX;
				lat1 = latDist1 + viewportCenterY;
				lat2 = latDist2 + viewportCenterY;
				
				viewportCenterX = ((long1 + long2)/2);
				viewportCenterY = -((lat1 + lat2)/2);
			}
			//TOP RIGHT TO BOTTOM LEFT
			else if ((x1 > x2) && y2 > y1)
			{
				longDist1 = ((getWidth()/2) - x1) * longPerPixel;
				longDist2 = ((getWidth()/2) - x2) * longPerPixel;
				latDist1 = ((getHeight()/2) - y1) * latPerPixel;
				latDist2 = ((getHeight()/2) - y2) * latPerPixel;
				
				long1 = longDist1 + viewportCenterX;
				long2 = longDist2 + viewportCenterX;
				lat1 = latDist1 + viewportCenterY;
				lat2 = latDist2 + viewportCenterY;
				
				viewportCenterX = -((long1 + long2)/2);
				viewportCenterY = ((lat1 + lat2)/2);
			}
			//BOTTOM RIGHT TO TOP LEFT
			else if ((x1 > x2) && y1 > y2)
			{
				longDist1 = ((getWidth()/2) - x1) * longPerPixel;
				longDist2 = ((getWidth()/2) - x2) * longPerPixel;
				latDist1 = (y1 - (getHeight()/2)) * latPerPixel;
				latDist2 = (y2 - (getHeight()/2)) * latPerPixel;
				
				long1 = longDist1 + viewportCenterX;
				long2 = longDist2 + viewportCenterX;
				lat1 = latDist1 + viewportCenterY;
				lat2 = latDist2 + viewportCenterY;
				
				viewportCenterX = -((long1 + long2)/2);
				viewportCenterY = -((lat1 + lat2)/2);
			}

			// THE PROVIDED RECT WILL LIKELY NOT HAVE THE SAME ASPECT
			// RATIO OF THE VIEWPORT, SO LET'S FIGURE OUT 
			// THE PROPER SCALING FACTORS FOR X AND Y
			double scaleX = 360/(long2-long1);
			double scaleY = 180/(lat1-lat2);
			if (scaleX > scaleY)
				scale = scaleY;
			else
				scale = scaleX;

			// CORRECT THE VIEWPORT IF NECESSARY, WE DON'T WANT TO SCALE OFF THE MAP
			correctViewport();
		}
		
	}
	

	/*** CONVERSION FUNCTIONS ***/
	
	/**
	 * This calculates and returns the longitude value that
	 * corresponds to the xPixel argument.
	 * 
	 * @param xPixel
	 *   - the pixel x-coordinate to be converted to longitude value
	 * @return
	 *   the longitude value equivalent to the parameter.
	 */
	public double pixelToXCoordinate(int xPixel)
	{
		double minLong = viewportCenterX - (longWidth/2);
	
		// SCALE THE PIXEL
		double percentLong = ((double)xPixel)/getWidth();
		double xLong = minLong + (percentLong * longWidth);
		return xLong;
	}

	/**
	 * This calculates and returns the latitude value that
	 * corresponds to the yPixel argument.
	 * 
	 * @param yPixel
	 *   - the pixel y-coordinate to be converted to latitude value
	 * @return
	 *   the latitude value equivalent to the parameter.
	 */
	public double pixelToYCoordinate(int yPixel)
	{
		double minLat = viewportCenterY - (latHeight/2);
	
		// SCALE THE PIXEL
		double percentLat = ((getHeight()-(double)yPixel)/getHeight());
		double yLat = minLat + (percentLat * latHeight);
		return yLat;
	}

	/**
	 * This calculates and returns the x pixel value that
	 * corresponds to the xCoord longitude argument.
	 * 
	 * @param xCoord
	 *   - the longitude value to be converted to pixel x-coordinate
	 * @return
	 *   the pixel x-coordinate equivalent to the parameter.
	 */
	public int xCoordinateToPixel(double xCoord)
	{
		double minLong = viewportCenterX - (longWidth/2);
	
		// SCALE THE COORDINATE
		double percentX = (xCoord - minLong)/longWidth;
		return (int)Math.round(getWidth() * percentX);
	}

	/**
	 * This calculates and returns the y pixel value that
	 * corresponds to the yCoord latitude argument.
	 * 
	 * @param yCoord
	 *   - the latitude value to be converted to pixel y-coordinate
	 * @return
	 *   the pixel y-coordinate equivalent to the parameter.
	 */
	public int yCoordinateToPixel(double yCoord)
	{
		double minLat = viewportCenterY - (latHeight/2);
	
		// SCALE THE COORDINATE
		double percentY = (yCoord - minLat)/latHeight;
		yCoord = getHeight() * percentY;
		return getHeight() - ((int)yCoord);
	}
}

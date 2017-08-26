package factbook_mapper.events;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.event.MouseInputAdapter;

import shp_renderer.SHPRenderer;
import shp_renderer.SHPViewer;

/**
 * This handler responds while the mouse is on the viewport.
 * 
 * @author Dinia Gepte
 *
 */
public class MouseHandler extends MouseInputAdapter
{
	private SHPViewer viewer;
	private SHPRenderer mapRenderer;
	private boolean isDragging;
	private boolean isClicking;
	private double initX;
	private double initY;
	
	/**
	 * Constructor of this class that sets the {@link SHPViewer} to the
	 * parameter <CODE>initViewer</CODE>, and sets the {@link SHPRenderer}
	 * to the renderer associated with the parameter.
	 * 
	 * @param initViewer
	 *   - the viewer to be used by this class
	 */
	public MouseHandler(SHPRenderer initRenderer)
	{
		mapRenderer = initRenderer;
		viewer = initRenderer.getViewer();
	}
	
	public void mousePressed(MouseEvent e)
	{
		if (e.isMetaDown() && (viewer.getMapData() != null) && !isClicking)
		{
			initX = mapRenderer.pixelToXCoordinate(e.getX());
			initY = mapRenderer.pixelToYCoordinate(e.getY());
			isDragging = true;
		}
		if (((e.getModifiers() & MouseEvent.BUTTON1_MASK) == MouseEvent.BUTTON1_MASK)
				&& (viewer.getMapData() != null) && !isDragging)
		{
			initX = mapRenderer.pixelToXCoordinate(e.getX());
			initY = mapRenderer.pixelToYCoordinate(e.getY());
			isClicking = true;
		}
	}
	
	public void mouseDragged(MouseEvent e)
	{
		if (isDragging && !isClicking)
			dragMap(e);
		/*{
			double x = mapRenderer.pixelToXCoordinate(e.getX());
			double y = mapRenderer.pixelToYCoordinate(e.getY());
			mapRenderer.setViewportCenter(x, y);
			//mapRenderer.repaint();
		}*/
		if (isClicking && !isDragging)
			calculateLassoCoords(e);
		
		mapRenderer.repaint();
	}
	
	public void mouseReleased(MouseEvent e)
	{
		if (isDragging && !isClicking)
			isDragging = false;
		if (isClicking && !isDragging)
		{
			calculateLassoZoomCoords(e);
			// "TURN OFF" THE CLICKING
			isClicking = false;
			mapRenderer.repaint();
		}
	}
	
	public void mouseMoved(MouseEvent e)
	{
		if (viewer.getMapData() != null)
		{
			mapRenderer.setMouseCoord(e.getX(), e.getY());
			mapRenderer.repaint();
		}
	}
	
	private void dragMap(MouseEvent e)
	{
		/*// INITIAL VIEWPORT VALUES
		double xCenter = mapRenderer.getViewportCenterX();
		double yCenter = mapRenderer.getViewportCenterY();
		double width = mapRenderer.getLongWidth();
		double height = mapRenderer.getLatHeight();
		
		// MAXIMUM NUMBER OF MOVES BEFORE REACHING THE BORDER
		double maxVerticalMove = (180 - height) / 2;
		double maxHorizontalMove = (360 - width) / 2;
		
		// ON-SCREEN DRAG VALUES
		double xCoord = mapRenderer.pixelToXCoordinate(e.getX());
		double yCoord = mapRenderer.pixelToYCoordinate(e.getY());
		double xMoves = xCoord - initX;
		double yMoves = yCoord - initY;
					
		// DRAG HORIZONTAL
		if ((xCenter > -maxHorizontalMove+xMoves) && (xCenter < maxHorizontalMove+xMoves))
			mapRenderer.setViewportCenter(xCenter-xMoves, yCenter);
		
		// DRAG VERTICAL
		if ((yCenter < maxVerticalMove+yMoves) && (yCenter > -maxVerticalMove+yMoves))
			mapRenderer.setViewportCenter(xCenter, yCenter-yMoves);*/
		
		int currentX = e.getX();
		int currentY = e.getY();
		double currentLong = mapRenderer.pixelToXCoordinate(currentX);
		double currentLat = mapRenderer.pixelToYCoordinate(currentY);
		
	//	double previousLong = mapRenderer.pixelToXCoordinate(pressedX);
		//double previousLat = viewer.getRenderer().pixelToYCoordinate(pressedY);
		
		double diffX = currentLong - initX;
		double diffY = currentLat - initY;
		//viewer.getRenderer().setCenter(viewer.getRenderer().getViewportCenterX() - diffX, viewer.getRenderer().getViewportCenterY() + diffY);
		mapRenderer.setViewportCenter(mapRenderer.getViewportCenterX() - diffX, 
				mapRenderer.getViewportCenterY() - diffY);
		mapRenderer.setMouseCoord(e.getX(), e.getY());
		initX = mapRenderer.pixelToXCoordinate(currentX);
		initY = mapRenderer.pixelToYCoordinate(currentY);
	}
	
	private void calculateLassoCoords(MouseEvent e)
	{
		// RECTANGLE COORDINATES
		int x = mapRenderer.xCoordinateToPixel(initX);
		int y = mapRenderer.yCoordinateToPixel(initY);
		
		// USED TO DETERMINE RECTANGLE DIMENSIONS
		int finalX = e.getX();
		int finalY = e.getY();
		
		
		
		// CREATE A NEW LASSO...
		//if (lasso == null)
		//	lasso = new Rectangle(x, y, 0, 0);
		// ...OR UPDATE THE LASSO WITH ITS NEW SIZE
		//else
			mapRenderer.updateLasso(x, y, finalX, finalY);
		
		//mapRenderer.setLasso(lasso);
		//mapRenderer.repaint();
	}
	
	private void calculateLassoZoomCoords(MouseEvent e)
	{
		double finalX = mapRenderer.pixelToXCoordinate(e.getX());
		double finalY = mapRenderer.pixelToYCoordinate(e.getY());
		
		// THE USER HAS TO CREATE A RECTANGLE TO ACTIVATE THE LASSOZOOM FUNCTION
	///	if ((initX != finalX) && (initY != finalY))
		if ((initX != finalX) && (initY != finalY))
			{
				//double width = finalX - initX;
				//double height = finalY - initY;
				//double xCenter = (initX + finalX) / 2;
				//double yCenter = (initY + finalY) / 2;	
			
				// THIS IS FOR TOP-RIGHT TO BOTTOM-LEFT LASSO.
				// IT'S A SPECIAL CASE SINCE width AND height
				// WILL BOTH BE NEGATIVE.
				//if ((initX > finalX) && (initY > finalY))
				//{
				//	width = initX - finalX;
				//	height = initY - finalY;
				//}
				//mapRenderer.setViewportCenter(xCenter, yCenter);
				mapRenderer.zoom(mapRenderer.xCoordinateToPixel(initX), 
						mapRenderer.yCoordinateToPixel(initY), e.getX(), e.getY());
			}

		// ERASES THE LASSO AFTER MOUSE IS RELEASED
		mapRenderer.clearLasso();
		
	//	mapRenderer.repaint();
	}
}

package factbook_mapper.tools;

import java.awt.Image;

/**
 * A class that resizes an <CODE>Image</CODE> using a scale factor.
 * The image can have a maximum dimension of 100 by 100 pixels.
 * 
 * @author Dinia Gepte
 *
 */
public class ImageResizer
{
	private final int MAX_WIDTH = 100;
	private final int MAX_HEIGHT = 100;
	
	private Image image;
	private float scale;
	
	/**
	 * Constructor that sets the image to resize to
	 * the parameter <CODE>imageToResize</CODE> with a
	 * default scale of 1.0. This means that calling <CODE>resize</CODE>
	 * without setting the scale will return an image of the same
	 * size.
	 * 
	 * @param imageToResize
	 *   - the image to be resized using the current scale
	 */
	public ImageResizer(Image imageToResize)
	{
		image = imageToResize;
		scale = 1.0f;
	}
	
	/**
	 * Sets the <CODE>Image</CODE> that will be resized and returned
	 * when <CODE>resize</CODE> is called.
	 * 
	 * @param initImage
	 *   - the image to be resized
	 */
	public void setImage(Image initImage)
	{
		image = initImage;
	}
	
	/**
	 * Sets the scale to be used by <CODE>resize</CODE> to change
	 * the dimensions of the image.
	 * 
	 * @param initScale
	 *   - a float value to be used as a scaling factor for the image
	 */
	public void setScale(Float initScale)
	{
		scale = initScale;
	}
	
	/**
	 * Changes the dimensions of the current image in the
	 * <CODE>ImageResizer</CODE> using the current scale factor
	 * and returns the scaled image.
	 * 
	 * @return
	 *   the image resized using the scale factor.
	 */
	public Image resize()
	{
		// TODO
		return null;
	}
}

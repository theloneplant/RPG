package _Appearances;

import java.awt.Dimension;
import java.awt.geom.Rectangle2D;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class ImageAppearance implements Appearance
{
    private Color     colorBlend;
    private float     angle;
    private Image     image;
    private Dimension dim;
    
    /**
     * An appearance that uses an image to draw the parent actor.
     * 
     * This appearance includes a color filter that you can set to change the
     * color of it.
     * 
     * @param image
     * @param colorBlend
     * @param angle
     */
    public ImageAppearance (Image image, Color colorBlend, float angle)
    {
        this.image = image;
        this.colorBlend = colorBlend;
        this.angle = angle;
        this.dim = new Dimension(image.getWidth(), image.getHeight());
        if (colorBlend != null)
        {
            this.image.setColor(0, colorBlend.r, colorBlend.g, colorBlend.b, colorBlend.a);
            this.image.setColor(1, colorBlend.r, colorBlend.g, colorBlend.b, colorBlend.a);
            this.image.setColor(2, colorBlend.r, colorBlend.g, colorBlend.b, colorBlend.a);
            this.image.setColor(3, colorBlend.r, colorBlend.g, colorBlend.b, colorBlend.a);
        }
    }
    
    public void draw (Rectangle2D newRect, Graphics g)
    {
        image.setRotation(angle);
        g.drawImage(image, (int) newRect.getX(), (int) newRect.getY());
    }
    
    public void setImage (Image image)
    {
        this.image = image;
    }
    
    public void setColor (Color color)
    {
        colorBlend = color;
    }
    
    public Image getImage ()
    {
        return image;
    }
    
    public Color getColor ()
    {
        return colorBlend;
    }
    
    @Override
    public Dimension getDimension ()
    {
        return dim;
    }
}

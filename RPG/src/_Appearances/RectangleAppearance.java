package _Appearances;

import java.awt.Dimension;
import java.awt.geom.Rectangle2D;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class RectangleAppearance implements Appearance
{
    private Color     color;
    private Dimension dim;
    
    /**
     * A simple appearance class that draws a rectangle between two points of a
     * dimension for its parent.
     * 
     * @param color
     * @param dim
     */
    public RectangleAppearance (Color color, Dimension dim)
    {
        this.color = color;
        this.dim = dim;
    }
    
    public void draw (Rectangle2D newRect, Graphics g)
    {
        g.setColor(color);
        g.fillRect((int) newRect.getX(), (int) newRect.getY(), (int) newRect.getWidth(), (int) newRect.getHeight());
    }
    
    public void setColor (Color newColor)
    {
        color = newColor;
    }
    
    public Color getColor ()
    {
        return color;
    }
    
    @Override
    public Dimension getDimension ()
    {
        return dim;
    }
}

package _Appearances;

import java.awt.Dimension;
import java.awt.geom.Rectangle2D;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class LineAppearance implements Appearance
{
    private Color     color;
    private Dimension dim;
    
    /**
     * A simple appearance class that draws a line between two points of a
     * dimension for its parent.
     * 
     * @param color
     * @param dim
     */
    public LineAppearance (Color color, Dimension dim)
    {
        this.color = color;
        this.dim = dim;
    }
    
    public void draw (Rectangle2D newRect, Graphics g)
    {
        g.setColor(color);
        g.drawLine((int) newRect.getX(), (int) newRect.getY(), (int) (newRect.getX() + newRect.getWidth()), (int) (newRect.getY() + newRect.getHeight()));
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

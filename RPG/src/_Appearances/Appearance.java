package _Appearances;

import java.awt.Dimension;
import java.awt.geom.Rectangle2D;

import org.newdawn.slick.Graphics;

public interface Appearance
{
    public void draw (Rectangle2D newRect, Graphics g);
    
    public Dimension getDimension ();
}

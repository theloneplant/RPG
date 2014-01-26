package _Actors;

import java.awt.Dimension;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import _Appearances.ImageAppearance;
import _Vectors.Position;

public class Grass extends Actor
{
    Image grass;
    
    /**
     * It's grass
     * 
     * @param p
     */
    public Grass (Position p)
    {
        super("Grass");
        
        try
        {
            grass = new Image("images/Structures/Grass/grass.gif");
            setAppearance(new ImageAppearance(grass, null, 0));
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }
        setDynamic(false);
        setDrawDimension(new Dimension(grass.getWidth(), grass.getHeight()));
        setDrawPosition(p);
        setHitBoxDimension(new Dimension(15, 15));
        setHitBoxOffset(new Dimension(0, 0));
    }
    
}

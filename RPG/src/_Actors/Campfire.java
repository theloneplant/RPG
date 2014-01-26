package _Actors;

import java.awt.Dimension;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import _Appearances.ImageAppearance;
import _Vectors.Position;

public class Campfire extends Actor
{
    Image campfire;
    
    /**
     * It's a campfire
     * 
     * @param p
     */
    public Campfire (Position p)
    {
        super("Campfire");
        try
        {
            campfire = new Image("images/Structures/Campfire/campfire.png");
            setAppearance(new ImageAppearance(campfire, new Color(255, 255, 255, 255), 0));
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }
        setCollides(true);
        setDynamic(false);
        setDrawDimension(new Dimension(campfire.getWidth(), campfire.getHeight()));
        setDrawPosition(p);
        setHitBoxDimension(new Dimension(59, 41));
        setHitBoxOffset(new Dimension(0, 10));
    }
}

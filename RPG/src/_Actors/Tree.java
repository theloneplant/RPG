package _Actors;

import java.awt.Dimension;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import _Appearances.ImageAppearance;
import _Vectors.Position;

public class Tree extends Actor
{
    Image tree;
    
    /**
     * It's a tree.
     * 
     * @param p
     */
    public Tree (Position p)
    {
        super("Tree");
        
        try
        {
            tree = new Image("images/Structures/Tree/tree3.png");
            setAppearance(new ImageAppearance(tree, new Color(255, 255, 255, 255), 0));
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }
        setCollides(true);
        setDynamic(false);
        setDrawDimension(new Dimension(tree.getWidth(), tree.getHeight()));
        setDrawPosition(p);
        setHitBoxDimension(new Dimension(93, 53));
        setHitBoxOffset(new Dimension(83, 230));
    }
}

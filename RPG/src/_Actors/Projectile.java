package _Actors;

import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.newdawn.slick.Color;

import _Appearances.RectangleAppearance;
import _Main.Main;
import _Misc.KeyEvent;
import _Misc.MouseEvent;
import _Vectors.Position;

public class Projectile extends Actor
{
    /**
     * An actor that destroys itself when it hits something and attempts to deal
     * a certain amount of damage to it.
     * 
     * @param character
     */
    public Projectile (Actor character)
    {
        super("Projectile");
        setSpeed(400);
        setDamage(character.getDamage());
        setDrawDimension(new Dimension(5, 5));
        setHitBoxDimension(new Dimension(5, 5));
        setHitBoxOffset(new Dimension(0, 0));
        setCollides(true);
        setAppearance(new RectangleAppearance(Color.red, new Dimension()));
    }
    
    public void update (double elapsedTimeSinceLastUpdate, KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        if (inBounds())
        {
            super.update(elapsedTimeSinceLastUpdate, keyEvent, mouseEvent);
        }
        else
            setAlive(false);
        
        ArrayList<Actor> victim = FindIntersectingActor(new Position(getPosition().x, getPosition().y, getPosition().z));
        
        if (victim != null)
        {
            for (int i = 0; i < victim.size(); i++)
            {
                if (victim.get(i).getClass() != AimLine.class && !(victim.get(i) instanceof Projectile) && (victim.get(i).collides()))
                {
                    this.setAlive(false);
                    victim.get(i).setHealth(victim.get(i).getHealth() - getDamage());
                    
                    if (victim.get(i).getHealth() <= 0 && victim.get(i).dynamic())
                        victim.get(i).setAlive(false);
                }
            }
        }
    }
    
    /**
     * Finds and returns all actors that intersect with it.
     * 
     * This code will be replaced by a collision engine in the future.
     * 
     * @param position
     * @return
     */
    protected ArrayList<Actor> FindIntersectingActor (Position position)
    {
        Rectangle2D hitBox = new Rectangle2D.Double((int) position.x, (int) position.y, getDrawBox().width, getDrawBox().height);
        ArrayList<Actor> actors = new ArrayList<Actor>();
        
        for (int i = 0; i < Main.allActors.size(); i++)
        {
            if (hitBox.intersects(Main.allActors.get(i).getHitBox()))
                actors.add(Main.allActors.get(i));
            
        }
        return actors;
    }
    
    /**
     * Checks if the projectile is in the dimensions of the level and removes it
     * if it is not.
     * 
     * @return
     */
    public boolean inBounds ()
    {
        if (getDrawBox().x + getDrawBox().width >= 0 && getDrawBox().x <= Main.dim.width && getDrawBox().y + getDrawBox().height >= 0 && getDrawBox().y <= Main.dim.height)
            return true;
        else
            return false;
    }
    
    public void setColor (Color c)
    {
        ((RectangleAppearance) this.getAppearance()).setColor(c);
    }
}

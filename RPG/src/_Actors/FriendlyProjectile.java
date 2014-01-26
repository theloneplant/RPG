package _Actors;

import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.newdawn.slick.Color;

import _Actors.Emitters.Emitter;
import _Actors.Emitters.FireEmitter;
import _Main.Main;
import _Misc.KeyEvent;
import _Misc.MouseEvent;
import _Vectors.Position;

public class FriendlyProjectile extends Projectile
{
    Emitter emit;
    
    /**
     * A projectile that will ignore the player, so that the player doesn't hurt
     * themselves when shooting
     * 
     * @param character
     */
    public FriendlyProjectile (Actor character)
    {
        super(character);
        setDamage(character.getDamage());
        setDrawDimension(new Dimension(10, 10));
        setHitBoxDimension(new Dimension(50, 50));
        setHitBoxOffset(new Dimension(0, 0));
        setCollides(true);
        setColor(new Color(255, 255, 255, 255));
        setSpeed(1000);
    }
    
    public void update (double elapsedTimeSinceLastUpdate, KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        if (inBounds())
        {
            super.update(elapsedTimeSinceLastUpdate, keyEvent, mouseEvent);
        }
        else
        {
            setAlive(false);
            Main.allActors.remove(emit);
        }
        
        ArrayList<Actor> victim = FindIntersectingActor(new Position(getPosition().x, getPosition().y, getPosition().z));
        
        if (victim != null)
        {
            for (int i = 0; i < victim.size(); i++)
            {
                if (victim.get(i).getClass() != PC.class && !(victim.get(i) instanceof Projectile) && (victim.get(i).collides()))
                {
                    this.setAlive(false);
                    Main.allActors.remove(emit);
                }
            }
        }
    }
    
    /**
     * Collision method to ignore the player
     * 
     * These collision methods scattered around will eventually be replaces by a
     * collision engine
     */
    protected ArrayList<Actor> FindIntersectingActor (Position position)
    {
        Rectangle2D hitBox = new Rectangle2D.Double((int) position.x, (int) position.y, getDrawBox().width, getDrawBox().height);
        ArrayList<Actor> actors = new ArrayList<Actor>();
        
        for (int i = 0; i < Main.allActors.size(); i++)
        {
            if (Main.allActors.get(i).collides() && Main.allActors.get(i).getClass() != PC.class)
            {
                if (hitBox.intersects(Main.allActors.get(i).getHitBox()))
                    actors.add(Main.allActors.get(i));
            }
        }
        return actors;
    }
    
    // TODO
    /**
     * Creates an attached emitter to the projectile. This will eventually be
     * removed
     */
    public void createEmitter ()
    {
        emit = new FireEmitter(this, 0, 0, -20);
        Main.allActors.add(emit);
    }
}

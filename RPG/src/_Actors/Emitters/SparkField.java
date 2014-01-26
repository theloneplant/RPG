package _Actors.Emitters;

import java.awt.Dimension;

import org.newdawn.slick.SlickException;

import _Actors.Actor;
import _Actors.Particles.Particle_Spark;
import _Main.Main;
import _Misc.KeyEvent;
import _Misc.MouseEvent;
import _Vectors.Position;

public class SparkField extends AreaEmitter
{
    /**
     * Emitter that creates spark particles
     * 
     * @param parent
     * @param size
     * @param xOffset
     * @param yOffset
     * @param zOffset
     */
    public SparkField (Actor parent, Dimension size, int xOffset, int yOffset, int zOffset)
    {
        super(parent, size, 200, xOffset, yOffset, zOffset, SpawnType.rectangle);
        try
        {
            particle = new Particle_Spark(this);
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }
        attached = true;
    }
    
    /**
     * Emitter that creates spark particles
     * 
     * @param p
     * @param size
     */
    public SparkField (Position p, Dimension size)
    {
        super(p, size, 200, SpawnType.rectangle);
        try
        {
            particle = new Particle_Spark(this);
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }
        attached = false;
    }
    
    public void update (double deltaTime, KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        super.update(deltaTime, keyEvent, mouseEvent);
        
        if (emitting)
        {
            time += deltaTime;
            
            while (time >= delay)
            {
                time -= delay;
                Particle_Spark spark = null;
                try
                {
                    spark = new Particle_Spark(this);
                }
                catch (SlickException e)
                {
                    e.printStackTrace();
                }
                spark.setDrawPosition(rectanglePoint());
                
                Main.allActors.add(spark);
            }
        }
    }
}

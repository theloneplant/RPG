package _Actors.Emitters;

import org.newdawn.slick.SlickException;

import _Actors.Actor;
import _Actors.Particles.Particle_Fire;
import _Actors.Particles.Particle_Spark;
import _Main.Main;
import _Misc.KeyEvent;
import _Misc.MouseEvent;
import _Vectors.Position;

public class FireEmitter extends Emitter
{
    /**
     * Spawns fire particles
     * 
     * @param parent
     * @param xOffset
     * @param yOffset
     * @param zOffset
     */
    public FireEmitter (Actor parent, int xOffset, int yOffset, int zOffset)
    {
        super(parent, 60, xOffset, yOffset, zOffset);
        attached = true;
    }
    
    /**
     * Spawns fire particles
     * 
     * @param p
     */
    public FireEmitter (Position p)
    {
        super(p, 60);
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
                try
                {
                    Main.allActors.add(new Particle_Fire(this));
                    Main.allActors.add(new Particle_Spark(this));
                }
                catch (SlickException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}

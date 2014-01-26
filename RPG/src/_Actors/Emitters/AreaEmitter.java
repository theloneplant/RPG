package _Actors.Emitters;

import java.awt.Dimension;

import _Actors.Actor;
import _Actors.Particles.Particle;
import _Main.Main;
import _Misc.KeyEvent;
import _Misc.MouseEvent;
import _Vectors.Position;

public class AreaEmitter extends Emitter
{
    protected SpawnType spawn;
    protected Dimension dim;
    protected Particle  particle;
    
    /**
     * Takes in parameters to emit particles in a given area (ellipse or
     * rectangle)
     * 
     * @param parent
     * @param size
     * @param frequency
     * @param xOffset
     * @param yOffset
     * @param zOffset
     * @param spawn
     */
    public AreaEmitter (Actor parent, Dimension size, int frequency, int xOffset, int yOffset, int zOffset, SpawnType spawn)
    {
        super(parent, frequency, xOffset, yOffset, zOffset);
        this.spawn = spawn;
        this.dim = size;
    }
    
    /**
     * Takes in parameters to emit particles in a given area (ellipse or
     * rectangle)
     * 
     * @param p
     * @param size
     * @param frequency
     * @param spawn
     */
    public AreaEmitter (Position p, Dimension size, int frequency, SpawnType spawn)
    {
        super(p, frequency);
        this.spawn = spawn;
        this.dim = size;
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
                
                if (spawn.equals(SpawnType.rectangle))
                {
                    particle.setDrawPosition(rectanglePoint());
                }
                else if (spawn.equals(SpawnType.ellipse))
                {
                    particle.setDrawPosition(ellipsePoint());
                }
                
                Main.allActors.add(particle);
            }
        }
    }
    
    /**
     * Calculates a random point to spawn a particle within a rectangle area
     * 
     * @return
     */
    public Position rectanglePoint ()
    {
        
        double x = Math.random() * dim.width / 2 - dim.width / 4;
        double y = Math.random() * dim.height / 2 - dim.height / 4;
        double z = 0;
        if (parent != null)
        {
            x += parent.getPosition().x + parent.getDrawBox().width / 2;
            y += parent.getPosition().y + parent.getDrawBox().height / 2;
            z = parent.getPosition().z;
        }
        else
        {
            x += getPosition().x;
            y += getPosition().y;
            z += getPosition().z;
        }
        return new Position(x - particle.getDrawBox().width / 2, y - particle.getDrawBox().height / 2, z);
    }
    
    /**
     * Calculates a random point to spawn a particle within a circular area
     * 
     * @return
     */
    public Position ellipsePoint ()
    {
        double angle = Math.random() * 360;
        double r = (Math.random() * ((dim.width * dim.height) / Math.sqrt(Math.pow((dim.width * Math.cos(angle)), 2) + Math.pow((dim.height * Math.sin(angle)), 2))) / 2) / Math.asin(.9);
        double x = (r * Math.sin(angle));
        double y = (r * Math.cos(angle));
        double z = 0;
        
        if (parent != null)
        {
            x += parent.getPosition().x + parent.getDrawBox().width / 2;
            y += parent.getPosition().y + parent.getDrawBox().height / 2;
            z = parent.getPosition().z;
        }
        else
        {
            x += getPosition().x;
            y += getPosition().y;
            z += getPosition().z;
        }
        return new Position(x - particle.getDrawBox().width / 2, y - particle.getDrawBox().height / 2, z);
    }
    
    /**
     * Which type of spawn method to use with area emitters
     * 
     * @author Michael LaPlante
     */
    protected enum SpawnType
    {
        rectangle, ellipse;
    }
}

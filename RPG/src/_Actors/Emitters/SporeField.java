package _Actors.Emitters;

import java.awt.Dimension;

import org.newdawn.slick.SlickException;

import _Actors.Actor;
import _Actors.Particles.SporeParticle;
import _Main.Main;
import _Misc.KeyEvent;
import _Misc.MouseEvent;
import _Vectors.Position;

public class SporeField extends AreaEmitter
{
    /**
     * Emitter that creates spore particles
     * 
     * @param parent
     * @param size
     * @param xOffset
     * @param yOffset
     * @param zOffset
     */
    public SporeField (Actor parent, Dimension size, int xOffset, int yOffset, int zOffset)
    {
        super(parent, size, 15 * Main.screen.width / 1920, xOffset, yOffset, zOffset, SpawnType.rectangle);
        attached = true;
    }
    
    /**
     * Emitter that creates spore particles
     * 
     * @param p
     * @param size
     */
    public SporeField (Position p, Dimension size)
    {
        super(p, size, 15 * Main.screen.width / 1920, SpawnType.rectangle);
        attached = false;
    }
    
    public void update (double deltaTime, KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        try
        {
            particle = new SporeParticle(this);
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }
        super.update(deltaTime, keyEvent, mouseEvent);
    }
}

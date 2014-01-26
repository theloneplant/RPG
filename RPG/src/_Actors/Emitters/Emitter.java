package _Actors.Emitters;

import java.awt.Dimension;

import org.newdawn.slick.Graphics;

import _Actors.Actor;
import _Main.Main;
import _Misc.KeyEvent;
import _Misc.MouseEvent;
import _Vectors.Acceleration;
import _Vectors.Position;

public class Emitter extends Actor
{
    protected int          xOffset, yOffset, zOffset;
    protected boolean      attached, emitting;
    protected double       frequency;
    protected double       delay;
    protected double       time;
    protected double       speed;
    protected double       life;
    protected Acceleration accel;
    protected Dimension    particleSize;
    protected Actor        parent;
    
    /**
     * An actor which doesn't have any appearance and whose only purpose is to
     * spawn particles in a given point by using given properties
     * 
     * @param parent
     * @param frequency
     * @param xOffset
     * @param yOffset
     * @param zOffset
     */
    public Emitter (Actor parent, int frequency, int xOffset, int yOffset, int zOffset)
    {
        super("Emitter");
        attached = true;
        emitting = true;
        this.parent = parent;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;
        setDrawDimension(new Dimension(0, 0));
        setHitBoxDimension(new Dimension(0, 0));
        setHitBoxOffset(new Dimension(0, 0));
        setVelocity(parent.getVelocity());
        this.frequency = frequency;
        updateSetting();
        time = 0;
    }
    
    /**
     * An actor which doesn't have any appearance and whose only purpose is to
     * spawn particles in a given point by using given properties
     * 
     * @param p
     * @param frequency
     */
    public Emitter (Position p, int frequency)
    {
        super("Emitter");
        attached = false;
        emitting = true;
        setDrawDimension(new Dimension(0, 0));
        setHitBoxDimension(new Dimension(0, 0));
        setHitBoxOffset(new Dimension(0, 0));
        setDrawPosition(p);
        this.frequency = frequency;
        updateSetting();
        time = 0;
    }
    
    public void update (double deltaTime, KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        updateSetting();
        if (parent != null)
        {
            if (!parent.getAlive())
            {
                Main.allActors.remove(this);
            }
        }
        if (attached)
        {
            setDrawPosition(new Position((parent.getPosition().x + parent.getDrawBox().width / 2 + xOffset), (parent.getPosition().y + parent.getDrawBox().height / 2 + yOffset),
                    parent.getPosition().z + zOffset));
        }
    }
    
    /**
     * Empty draw method
     */
    public void draw (Graphics g)
    {
        
    }
    
    /**
     * Universal method that changes how often particles are spawned by using
     * the Options Menu setting
     */
    public void updateSetting ()
    {
        if (Main.particleSetting.equals(Main.Setting.None))
        {
            delay = 1;
            emitting = false;
        }
        else if (Main.particleSetting.equals(Main.Setting.Low))
        {
            emitting = true;
            delay = 1 / this.frequency * 4;
        }
        else if (Main.particleSetting.equals(Main.Setting.Medium))
        {
            emitting = true;
            delay = 1 / this.frequency * 2;
        }
        else if (Main.particleSetting.equals(Main.Setting.High))
        {
            emitting = true;
            delay = 1 / this.frequency;
        }
    }
}

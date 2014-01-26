package _Actors.Emitters;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

import _Vectors.Acceleration;
import _Vectors.Velocity;

public class ParticleManager
{
    private double       lifePercent;
    private Velocity     velocity;
    private Acceleration acceleration;
    private Image        image;
    private Color        color;
    private float        size;
    private float        rotation;
    private boolean      additive, aligned, attached;
    
    /**
     * A class filled with properties whose only purpose is to save properties
     * of a particle and use them throughout the particle's lifespan. This
     * allows the particle to transform as its life progresses.
     * 
     * @param lifePercent
     * @param image
     * @param color
     * @param size
     * @param vel
     * @param accel
     * @param rotation
     * @param additive
     * @param aligned
     * @param attached
     */
    public ParticleManager (double lifePercent, Image image, Color color, float size, Velocity vel, Acceleration accel, float rotation, boolean additive, boolean aligned, boolean attached)
    {
        this.lifePercent = lifePercent;
        this.setImage(image);
        this.setColor(color);
        this.size = size;
        this.velocity = vel;
        this.acceleration = accel;
        this.rotation = rotation;
        this.additive = additive;
        this.aligned = aligned;
        this.attached = attached;
    }
    
    public double getLifePercent ()
    {
        return lifePercent;
    }
    
    public void setLifePercent (double lifePercent)
    {
        this.lifePercent = lifePercent;
    }
    
    public Image getImage ()
    {
        return image;
    }
    
    public void setImage (Image image)
    {
        this.image = image;
    }
    
    public Color getColor ()
    {
        return color;
    }
    
    public void setColor (Color color)
    {
        this.color = color;
    }
    
    public Velocity getVelocity ()
    {
        return velocity;
    }
    
    public void setVelocity (Velocity velocity)
    {
        this.velocity = velocity;
    }
    
    public Acceleration getAcceleration ()
    {
        return acceleration;
    }
    
    public void setAcceleration (Acceleration acceleration)
    {
        this.acceleration = acceleration;
    }
    
    public float getSize ()
    {
        return size;
    }
    
    public void setSize (float size)
    {
        this.size = size;
    }
    
    public float getRotation ()
    {
        return rotation;
    }
    
    public void setRotation (float rotation)
    {
        this.rotation = rotation;
    }
    
    public boolean isAdditive ()
    {
        return additive;
    }
    
    public void setAdditive (boolean additive)
    {
        this.additive = additive;
    }
    
    public boolean isAligned ()
    {
        return aligned;
    }
    
    public void setAligned (boolean aligned)
    {
        this.aligned = aligned;
    }
    
    public boolean isAttached ()
    {
        return attached;
    }
    
    public void setAttached (boolean attached)
    {
        this.attached = attached;
    }
}

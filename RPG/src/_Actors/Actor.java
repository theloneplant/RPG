package _Actors;

import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import _Actions.Action;
import _Appearances.Appearance;
import _Main.Main;
import _Misc.KeyEvent;
import _Misc.MouseEvent;
import _Vectors.Acceleration;
import _Vectors.Position;
import _Vectors.Velocity;

public class Actor
{
    private int               health, damage; // Health is how much damage it
                                               // can take, damage is how much
                                               // damage it deals
    private Dimension         hitBoxDimension; // The hitbox properties
    private Dimension         hitBoxOffset;
    private Dimension         drawDimension;  // The drawbox properties
    private Position          drawPosition;
    private double            scale;          // Scale of the actor
    private double            speed;          // Speed of the actor
    private boolean           alive;          // Determines whether or not this
                                               // actor should be removed
    private boolean           collides, dynamic, visible; // Collides determines
                                                          // if the actor should
                                                          // calculate for
                                                          // collisions with
                                                          // anything, dynamic
                                                          // determines whether
                                                          // the actor can move
                                                          // and get destroyed,
                                                          // and visible
                                                          // determines whether
                                                          // the actor is seen
                                                          // or not
    private String            name;                      // The actor's name
    private Velocity          velocity, maxVel;          // Velocities
    private Acceleration      accel;                     // Acceleration
    private ArrayList<Action> actions;                   // Universal list of
                                                          // actions
    private Appearance        displayed;                 // Appearance to
                                                          // determine how to
                                                          // draw the actor
                                                          
    /**
     * The base class for almost all objects that interact in the game. Every
     * actor has a list of actions that they can execute.
     * 
     * @param name
     */
    public Actor (String name)
    {
        this.name = name;
        health = 0;
        damage = 0;
        alive = true;
        speed = 0;
        scale = 1;
        collides = false;
        dynamic = true;
        visible = true;
        
        drawPosition = new Position(0, 0, 0);
        drawDimension = new Dimension(0, 0);
        hitBoxDimension = new Dimension(0, 0);
        hitBoxOffset = new Dimension(0, 0);
        
        velocity = new Velocity(0, 0, 0);
        maxVel = new Velocity(0, 0, 0);
        accel = new Acceleration(0, 0, 0);
        
        actions = new ArrayList<Action>();
        displayed = null;
    }
    
    /**
     * The global update method that is called in the game loop
     * 
     * @param delta
     * @param keyEvent
     * @param mouseEvent
     */
    public void update (double delta, KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        if (alive == true)
        {
            for (int idx = 0; idx < actions.size(); idx++)
            {
                if (actions != null)
                    actions.get(idx).update(this, delta, keyEvent, mouseEvent);
            }
            calculatePosition(delta);
        }
        else
            Main.allActors.remove(this);
    }
    
    /**
     * The global drawing method that is called in the game loop
     * 
     * @param g
     */
    public void draw (Graphics g)
    {
        g.scale((float) scale, (float) scale);
        
        if (displayed != null)
        {
            if (visible)
            {
                displayed.draw(new Rectangle2D.Double(drawPosition.x / scale, drawPosition.y / scale - drawPosition.z / scale, drawDimension.width, drawDimension.height), g);
            }
        }
        else
        {
            System.out.println("Appearance for " + getName() + " is null.");
        }
        
        g.scale((float) (1 / scale), (float) (1 / scale));
    }
    
    /**
     * Calculates the appropriate position of the actor by adding velocities and
     * accelerations.
     * 
     * @param deltaTime
     */
    public void calculatePosition (double deltaTime)
    {
        if (velocity != null)
            drawPosition = new Position(drawPosition.x + velocity.x * deltaTime, drawPosition.y + velocity.y * deltaTime, drawPosition.z + velocity.z * deltaTime);
        if (accel != null)
        {
            if (velocity.x < maxVel.x)
                velocity.x += accel.x * deltaTime;
            if (velocity.y < maxVel.y)
                velocity.y += accel.y * deltaTime;
            if (velocity.z < maxVel.z)
                velocity.z += accel.z * deltaTime;
        }
    }
    
    /**
     * Checks if the actor is still in the level. If not, set its position
     * appropriately.
     */
    public void inScreen ()
    {
        if (drawPosition.x < 0)
            drawPosition.x = 0;
        if (drawPosition.x + drawDimension.width > Main.dim.width)
            drawPosition.x = Main.dim.width - drawDimension.width;
        if (drawPosition.y < 0)
            drawPosition.y = 0;
        if (drawPosition.y + drawDimension.height > Main.dim.height)
            drawPosition.y = Main.dim.height - drawDimension.height;
    }
    
    /**
     * Adds an action to the actor's global action list
     * 
     * @param action
     */
    public void addAction (Action action)
    {
        actions.add(action);
    }
    
    /**
     * Checks if the actor's hit box intersects with another actor
     * 
     * @param other
     * @return
     */
    public boolean intersects (Actor other)
    {
        if (hitBoxDimension != null && hitBoxOffset != null)
        {
            if (other.getHitBox().intersects(this.getHitBox()))
                return true;
            else
                return false;
        }
        else
            return false;
    }
    
    /**
     * Checks if the actor's draw box intersects with another actor
     * 
     * @param other
     * @return
     */
    public boolean intersectsDraw (Actor other)
    {
        if (other.getDrawBox().intersects(this.getDrawBox()))
            return true;
        else
            return false;
    }
    
    // Getters and setters
    
    public void setAlive (boolean newState)
    {
        alive = newState;
    }
    
    public void setCollides (boolean newCollides)
    {
        collides = newCollides;
    }
    
    public void setDynamic (boolean isDynamic)
    {
        dynamic = isDynamic;
    }
    
    public void setScale (double newScale)
    {
        scale = newScale;
    }
    
    public void setSpeed (double newSpeed)
    {
        speed = newSpeed;
    }
    
    public void setDamage (int newDamage)
    {
        damage = newDamage;
    }
    
    public void setHealth (int newHealth)
    {
        if (newHealth <= 0)
            health = 0;
        else
            health = newHealth;
    }
    
    public void setName (String newName)
    {
        name = newName;
    }
    
    public void setVelocity (Velocity velocity)
    {
        if (velocity != null)
            this.velocity = velocity;
    }
    
    public void setMaxVel (Velocity velocity)
    {
        if (velocity != null)
            maxVel = velocity;
    }
    
    public void setAcceleration (Acceleration newAccel)
    {
        if (accel != null)
            accel = newAccel;
    }
    
    public void setDrawDimension (Dimension d)
    {
        drawDimension = d;
    }
    
    public void setDrawPosition (Position p)
    {
        drawPosition = p;
    }
    
    public void setHitBoxDimension (Dimension newDimension)
    {
        hitBoxDimension = newDimension;
    }
    
    public void setHitBoxOffset (Dimension newDimension)
    {
        hitBoxOffset = newDimension;
    }
    
    public void setAppearance (Appearance app)
    {
        displayed = app;
    }
    
    public void setVisible (boolean visible)
    {
        this.visible = visible;
    }
    
    // ======================================
    
    public Velocity getVelocity ()
    {
        return velocity;
    }
    
    public Velocity getMaxVel ()
    {
        return maxVel;
    }
    
    public Acceleration getAcceleration ()
    {
        return accel;
    }
    
    public boolean collides ()
    {
        return collides;
    }
    
    public boolean dynamic ()
    {
        return dynamic;
    }
    
    public int getHealth ()
    {
        return health;
    }
    
    public int getDamage ()
    {
        return damage;
    }
    
    public double getSpeed ()
    {
        return speed;
    }
    
    public boolean getAlive ()
    {
        return alive;
    }
    
    public String getName ()
    {
        return name;
    }
    
    public double getScale ()
    {
        return scale;
    }
    
    public Dimension getHitBoxOffset ()
    {
        return hitBoxOffset;
    }
    
    public Rectangle2D.Double getHitBox ()
    {
        return new Rectangle2D.Double((drawPosition.x + hitBoxOffset.width), (drawPosition.y + hitBoxOffset.height), hitBoxDimension.width, hitBoxDimension.height);
    }
    
    public Rectangle2D.Double getDrawBox ()
    {
        return new Rectangle2D.Double(drawPosition.x, drawPosition.y, drawDimension.width, drawDimension.height);
    }
    
    public Position getPosition ()
    {
        return drawPosition;
    }
    
    public Appearance getAppearance ()
    {
        return displayed;
    }
    
    public boolean getVisible ()
    {
        return visible;
    }
}

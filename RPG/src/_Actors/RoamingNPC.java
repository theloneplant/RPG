package _Actors;

import org.newdawn.slick.Color;

import _Main.Main;
import _Misc.KeyEvent;
import _Misc.MouseEvent;
import _Vectors.Position;
import _Vectors.Velocity;

public class RoamingNPC extends NPC
{
    private int      maxHealth;
    private int      maxWalk;
    private boolean  panic;
    private double   walkTime;
    private Position destination;
    private double   waitTime;
    private double   currentWait;
    private double   xVector;
    private double   yVector;
    
    public RoamingNPC ()
    {
        super();
        maxWalk = 1;
        walkTime = Math.random();
        destination = new Position(getPosition().x, getPosition().y, 0);
        waitTime = Math.random() / 20;
        currentWait = 0;
        xVector = 0;
        yVector = 0;
        panic = false;
        maxHealth = getHealth();
        newDestination();
    }
    
    public void update (double delta, KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        super.update(delta, keyEvent, mouseEvent);
        
        Roam(delta);
        
        if (getHealth() < maxHealth)
            panic = true;
        if (panic)
        {
            setSpeed(200);
        }
    }
    
    /**
     * Chooses a new destination to walk to and sets the velocity in that
     * direction
     * 
     * This code will be modified eventually to implement the AStar pathfinding
     */
    public void newDestination ()
    {
        destination = new Position(getDrawBox().x, getDrawBox().y, 0);
        
        double xRandom = 0;
        double yRandom = 0;
        
        if (panic)
        {
            while (xRandom > -0.5 && xRandom < 0.5)
                xRandom = (Math.random() - 0.5) * 2;
            while (yRandom > -0.5 && yRandom < 0.5)
                yRandom = (Math.random() - 0.5) * 2;
        }
        else
        {
            xRandom = (Math.random() - 0.5) * 2;
            yRandom = (Math.random() - 0.5) * 2;
        }
        double xMove = maxWalk * xRandom;
        double yMove = maxWalk * yRandom;
        destination.setPosition(xMove, yMove, 0);
        xVector = xRandom * getSpeed();
        yVector = yRandom * getSpeed();
        double mySpeed = Math.sqrt((xVector * xVector) + (yVector * yVector));
        frameDuration = 8 / mySpeed;
        setVelocity(new Velocity(xVector, yVector, 0));
    }
    
    /**
     * Chooses a random point and walks for a random time to that point. Then
     * the NPC waits for a time before roaming again.
     * 
     * This code will be modified eventually to implement the AStar pathfinding
     * 
     * @param gameTime
     */
    public void Roam (double gameTime)
    {
        if (walkTime > maxWalk)
        {
            setVelocity(new Velocity(0, 0, 0));
            currentWait += gameTime;
            walkTime = 0;
        }
        
        if (currentWait >= waitTime)
        {
            waitTime = Math.random();
            waitTime = waitTime / 20;
            
            currentWait = 0;
            newDestination();
        }
        
        walkTime += gameTime;
    }
    
    /**
     * Makes the NPC explode into confetti
     * 
     * This code is temporary
     */
    public void Explode ()
    {
        int potency = 20;
        Actor npc = this;
        Position npcPos = new Position(getPosition().x, getPosition().y, getPosition().z);
        Main.allActors.remove(this);
        
        for (int i = 0; i < potency; i++)
        {
            Projectile shrapnel = new Projectile(npc);
            double color = Math.random();
            if (color <= 0.1)
                shrapnel.setColor(Color.red);
            if (color <= 0.2 && color > 0.1)
                shrapnel.setColor(Color.orange);
            if (color <= 0.3 && color > 0.2)
                shrapnel.setColor(Color.yellow);
            if (color <= 0.4 && color > 0.3)
                shrapnel.setColor(Color.green);
            if (color <= 0.5 && color > 0.4)
                shrapnel.setColor(Color.cyan);
            if (color <= 0.6 && color > 0.5)
                shrapnel.setColor(Color.blue);
            if (color <= 0.7 && color > 0.6)
                shrapnel.setColor(Color.magenta);
            if (color <= 0.8 && color > 0.7)
                shrapnel.setColor(Color.white);
            if (color <= 0.9 && color > 0.8)
                shrapnel.setColor(Color.white);
            if (color <= 1 && color > 0.9)
                shrapnel.setColor(Color.lightGray);
            
            shrapnel.setDrawPosition(npcPos);
            Main.allActors.add(shrapnel);
            shrapnel.setVelocity(new Velocity(Math.random() * shrapnel.getSpeed() * 2 - shrapnel.getSpeed(), Math.random() * shrapnel.getSpeed() * 2 - shrapnel.getSpeed(), 0));
        }
    }
    
    /**
     * Modified setAlive(boolean newAlive) method to now make the NPC explode
     * 
     * This code is temporary
     */
    public void setAlive (boolean newAlive)
    {
        super.setAlive(newAlive);
        
        if (!getAlive())
            Explode();
    }
}

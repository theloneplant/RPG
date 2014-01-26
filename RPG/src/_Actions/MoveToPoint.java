package _Actions;

import _Actors.Actor;
import _Misc.KeyEvent;
import _Misc.MouseEvent;
import _Vectors.Position;
import _Vectors.Velocity;

public class MoveToPoint extends Action
{
    private Actor    actor;
    private Position point;
    private Velocity velocity;
    private double   distance, estimatedTime, elapsedTime;
    
    /**
     * Takes in a position that the given actor will move to, and sets the
     * actor's velocity in that direction for an estimated time.
     * 
     * @param actor
     * @param point
     */
    public MoveToPoint (Actor actor, Position point)
    {
        this.actor = actor;
        this.point = new Position(point.x, point.y, point.y);
        updateActorVelocity();
        elapsedTime = 0;
        this.start();
    }
    
    public void update (Actor parent, double delta, KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        if (getBusy())
        {
            if (elapsedTime >= estimatedTime)
            {
                actor.setVelocity(new Velocity(0, 0, 0));
                stop();
            }
            else
            {
                elapsedTime += delta;
                start();
            }
            
            if (!getBusy())
            {
                actor.setVelocity(new Velocity(0, 0, 0));
            }
        }
    }
    
    /**
     * Resets the target position and redirects the parent NPC to that point
     * 
     * @param newPoint
     */
    public void setPoint (Position newPoint)
    {
        point = newPoint;
        updateActorVelocity();
        start();
    }
    
    /**
     * Estimates that amount of time it may take to get to the destination
     * 
     * @param distance
     */
    public void calculateEstimatedTime (double distance)
    {
        estimatedTime = distance / actor.getSpeed();
    }
    
    /**
     * Finds the appropriate vector between the parent NPC and the destination,
     * and scales it to the NPC's speed parameter
     */
    public void updateActorVelocity ()
    {
        double x = point.x - (actor.getHitBox().x);
        double y = point.y - (actor.getHitBox().y);
        distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        
        calculateEstimatedTime(distance);
        
        double xVel = x * (actor.getSpeed() / distance);
        double yVel = y * (actor.getSpeed() / distance);
        
        velocity = new Velocity(xVel, yVel, 0);
        actor.setVelocity(velocity);
    }
}

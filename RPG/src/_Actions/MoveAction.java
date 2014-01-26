package _Actions;

import _Actors.Actor;
import _Inputs.Input;
import _Misc.KeyEvent;
import _Misc.MouseEvent;
import _Vectors.Velocity;

public class MoveAction extends Action
{
    private Input    start;
    private Input    stop;
    private Velocity velocity;
    
    /**
     * Moving action for the player. It adds velocity in the direction that the
     * player will be traveling
     * 
     * @param start
     * @param stop
     * @param velocity
     */
    public MoveAction (Input start, Input stop, Velocity velocity)
    {
        this.start = start;
        this.stop = stop;
        this.velocity = velocity;
    }
    
    public void update (Actor parent, double gameTime, KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        if (start.Happened(keyEvent, mouseEvent) && !getBusy())
        {
            this.start();
            Velocity v = parent.getVelocity();
            v.add(velocity);
            parent.setVelocity(v);
        }
        
        if (stop.Happened(keyEvent, mouseEvent) && getBusy())
        {
            this.stop();
            Velocity v = parent.getVelocity();
            v.subtract(velocity);
            parent.setVelocity(v);
        }
    }
}

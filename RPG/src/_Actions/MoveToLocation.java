package _Actions;

import _Actors.Actor;
import _Actors.NPC;
import _Misc.KeyEvent;
import _Misc.MouseEvent;
import _Vectors.Position;
import _Vectors.Velocity;

public class MoveToLocation extends Action
{
    private MoveToPoint move;
    private NPC         member;
    private Position    destination;
    private boolean     moving;
    private double      number;
    
    /**
     * Action which uses nested actions to move the target NPC to a location
     * small steps at a time to avoid collisions. This action uses a modified
     * AStar algorithm to find a path.
     * 
     * This action holds MoveToPoint actions
     * 
     * @param member
     * @param destination
     */
    public MoveToLocation (NPC member, Position destination)
    {
        this.destination = destination;
        this.number = 1;
        moving = true;
        this.member = member;
        move = new MoveToPoint(member, member.findNextPath(destination));
        this.start();
    }
    
    public void update (Actor parent, double gameTime, KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        if (getBusy())
        {
            if (move != null)
            {
                move.update(parent, gameTime, keyEvent, mouseEvent);
                
                if (member.getHitBox().intersects(destination.x - (member.getHitBox().width / 2) * number, destination.y - (member.getHitBox().height / 2) * number, member.getHitBox().width * number,
                        member.getHitBox().height * number))
                {
                    moving = false;
                    member.setVelocity(new Velocity(0, 0, 0));
                    stop();
                }
                else if (!move.getBusy() && moving)
                {
                    move = new MoveToPoint(member, member.findNextPath(destination));
                }
            }
        }
    }
    
    public void setNumber (double num)
    {
        number = num;
    }
    
    public NPC getMember ()
    {
        return member;
    }
}

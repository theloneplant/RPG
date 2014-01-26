package _Actors.Squad;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import _Actors.PC;
import _Main.Main;
import _Misc.KeyEvent;
import _Misc.MouseEvent;
import _Vectors.Position;

public class Squad
{
    private ArrayList<SquadMember> members;
    private Position               mouse;
    private PC                     leader;
    private boolean                clicked;
    
    /**
     * The player's squad, which houses a list of SquadMembers that are modified
     * in it. The squad mainly checks to see which members are selected.
     * 
     * @param leader
     */
    public Squad (PC leader)
    {
        members = new ArrayList<SquadMember>();
        mouse = new Position(0, 0, 0);
        this.leader = leader;
        clicked = false;
    }
    
    /**
     * Updates the list of squad members
     * 
     * @param delta
     * @param keyEvent
     * @param mouseEvent
     */
    public void update (double delta, KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        if (mouseEvent != null)
        {
            if (mouseEvent.eventType != MouseEvent.MouseEventType.wheelMoved)
            {
                mouse.x = mouseEvent.x + Main.camera.getPosition().x;
                mouse.y = mouseEvent.y + Main.camera.getPosition().y;
            }
            
            if (mouseEvent.button == Input.MOUSE_LEFT_BUTTON)
            {
                if (mouseEvent.eventType == MouseEvent.MouseEventType.pressed)
                {
                    clicked = true;
                }
                else if (mouseEvent.eventType != MouseEvent.MouseEventType.moved && mouseEvent.eventType != MouseEvent.MouseEventType.dragged)
                {
                    clicked = false;
                }
            }
        }
        
        Rectangle2D selection = new Rectangle2D.Double(leader.getSelection().getOldPos().x, leader.getSelection().getOldPos().y, leader.getSelection().getDrawBox().width, leader.getSelection()
                .getDrawBox().height);
        for (int i = 0; i < members.size(); i++)
        {
            if (members.get(i).getDrawBox().intersectsLine(mouse.x, mouse.y, mouse.x + 1, mouse.y + 1))
            {
                if (clicked)
                {
                    members.get(i).setSelected(true);
                }
                
                if (!members.get(i).getSelected())
                {
                    members.get(i).setHovered(true);
                }
            }
            else if (selection.intersects(members.get(i).getDrawBox()))
            {
                members.get(i).setHovered(false);
                if (clicked)
                {
                    members.get(i).setSelected(true);
                }
            }
            else
            {
                members.get(i).setHovered(false);
                if (clicked)
                {
                    members.get(i).setSelected(false);
                }
            }
        }
    }
    
    public void draw (Graphics g)
    {
    }
    
    /**
     * Adds a SquadMember to the Squad
     * 
     * @param newMember
     */
    public void addMember (SquadMember newMember)
    {
        members.add(newMember);
    }
    
    /**
     * Removes a SquadMember from the Squad
     * 
     * @param name
     */
    public void removeMember (String name)
    {
        for (int i = 0; i < members.size(); i++)
        {
            if (members.get(i).getName().equals(name))
            {
                members.remove(i);
            }
        }
    }
    
    /**
     * Returns all SquadMembers that are selected
     * 
     * @return
     */
    public ArrayList<SquadMember> getSelectedMembers ()
    {
        ArrayList<SquadMember> selected = new ArrayList<SquadMember>();
        for (int i = 0; i < members.size(); i++)
        {
            if (members.get(i).getSelected())
            {
                selected.add(members.get(i));
            }
        }
        return selected;
    }
    
    public SquadMember getMember (int i)
    {
        return members.get(i);
    }
}

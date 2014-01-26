package _Actions;

import java.util.ArrayList;

import _Actors.Actor;
import _Actors.PC;
import _Actors.Squad.SquadMember;
import _Inputs.Input;
import _Main.Main;
import _Misc.KeyEvent;
import _Misc.MouseEvent;
import _Vectors.Position;

public class MoveSquadToLocation extends Action
{
    private Input                     start;
    private ArrayList<MoveToLocation> move;
    private ArrayList<SquadMember>    members;
    private PC                        parent;
    private Position                  destination;
    private double                    num;
    private Actor                     arrow;
    
    /**
     * Uses nested actions to move the players squad to a position determined by
     * the mouse.
     * 
     * This action holds MoveToLocation actions
     * 
     * @param parent
     * @param arrow
     * @param start
     */
    public MoveSquadToLocation (PC parent, Actor arrow, Input start)
    {
        this.start = start;
        this.parent = parent;
        this.members = parent.getSquad().getSelectedMembers();
        this.move = new ArrayList<MoveToLocation>();
        this.arrow = arrow;
        num = 1;
        this.start();
    }
    
    public void update (Actor parent, double gameTime, KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        if (getBusy())
        {
            if (start.Happened(keyEvent, mouseEvent))
            {
                num = 1;
                members = this.parent.getSquad().getSelectedMembers();
                if (mouseEvent != null)
                {
                    destination = new Position(mouseEvent.x + Main.camera.getPosition().x, mouseEvent.y + Main.camera.getPosition().y, 0);
                    arrow.setVisible(true);
                    arrow.setDrawPosition(new Position(mouseEvent.x + Main.camera.getPosition().x - arrow.getDrawBox().width / 2, mouseEvent.y + Main.camera.getPosition().y
                            - arrow.getDrawBox().height, 0));
                }
                for (int i = 0; i < move.size(); i++)
                {
                    for (int j = 0; j < members.size(); j++)
                    {
                        try
                        {
                            if (members.get(j).equals(move.get(i).getMember()))
                            {
                                move.remove(i);
                            }
                        }
                        catch (IndexOutOfBoundsException e)
                        {
                            // prevents the exception from crashing - bug
                            // involved spamming the move command quickly which
                            // resulted in an index exception from removal
                        }
                    }
                }
                for (int i = 0; i < members.size(); i++)
                {
                    members.get(i).clearNodes();
                    move.add(new MoveToLocation(members.get(i), destination));
                }
            }
            else
            {
                if (move != null)
                {
                    for (int i = 0; i < move.size(); i++)
                    {
                        move.get(i).update(parent, gameTime, keyEvent, mouseEvent);
                        move.get(i).setNumber(num);
                        if (!move.get(i).getBusy())
                        {
                            move.remove(i);
                            num += 2;
                        }
                    }
                    
                    if (move.size() == 0)
                    {
                        arrow.setVisible(false);
                    }
                }
            }
        }
    }
}

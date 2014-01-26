package _Actions;

import _Actors.Actor;
import _GameStates.Transitions.Transition;
import _Inputs.Input;
import _Main.Main;
import _Misc.KeyEvent;
import _Misc.MouseEvent;

public class StateSwitchAction extends Action
{
    private Input      start;
    private Transition trans;
    
    /**
     * Meant for use in buttons and other interfaces - Transitions from one
     * state to another
     * 
     * Lateral movement in the stack
     * 
     * @param trans
     * @param start
     */
    public StateSwitchAction (Transition trans, Input start)
    {
        this.trans = trans;
        this.start = start;
        this.start();
    }
    
    public void update (Actor parent, double gameTime, KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        if (getBusy())
        {
            if (start.Happened(keyEvent, mouseEvent))
            {
                Main.gsm.transition(trans);
            }
        }
    }
}

package _Actions;

import _Actors.Actor;
import _GameStates.Transitions.Transition;
import _Inputs.Input;
import _Main.Main;
import _Misc.KeyEvent;
import _Misc.MouseEvent;

public class StatePopSwitchAction extends Action
{
    private Input      start;
    private Transition trans;
    private int        numOfPops;
    
    /**
     * Meant for use in buttons and other interfaces - Pops a state from the
     * stack, then transitions the new state to another
     * 
     * Vertical and lateral movement in the stack
     * 
     * @param trans
     * @param start
     * @param numOfPops
     */
    public StatePopSwitchAction (Transition trans, Input start, int numOfPops)
    {
        this.trans = trans;
        this.start = start;
        this.numOfPops = numOfPops;
        this.start();
    }
    
    public void update (Actor parent, double gameTime, KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        if (getBusy())
        {
            if (start.Happened(keyEvent, mouseEvent))
            {
                for (int i = 0; i < numOfPops; i++)
                {
                    Main.gsm.popState();
                }
                Main.gsm.transition(trans);
            }
        }
    }
}

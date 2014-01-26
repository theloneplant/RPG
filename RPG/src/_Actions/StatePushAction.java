package _Actions;

import _Actors.Actor;
import _GameStates.GameState;
import _Inputs.Input;
import _Main.Main;
import _Misc.KeyEvent;
import _Misc.MouseEvent;

public class StatePushAction extends Action
{
    private Input     start;
    private GameState gs;
    
    /**
     * Meant for use in buttons and other interfaces - Pushes a state on to the
     * stack
     * 
     * Vertical movement in the stack
     * 
     * @param gs
     * @param start
     */
    public StatePushAction (GameState gs, Input start)
    {
        this.start = start;
        this.gs = gs;
        this.start();
    }
    
    public void update (Actor parent, double gameTime, KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        if (getBusy())
        {
            if (start.Happened(keyEvent, mouseEvent))
            {
                Main.gsm.pushState(gs);
            }
        }
    }
}

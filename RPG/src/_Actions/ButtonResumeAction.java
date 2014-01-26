package _Actions;

import _Actors.Actor;
import _Inputs.Input;
import _Main.Main;
import _Misc.KeyEvent;
import _Misc.MouseEvent;

public class ButtonResumeAction extends Action
{
    private Input start;
    
    /**
     * Meant for use in buttons and other interfaces - Action for the paused
     * state and resuming the game
     * 
     * @param start
     */
    public ButtonResumeAction (Input start)
    {
        this.start = start;
        this.start();
    }
    
    public void update (Actor parent, double gameTime, KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        if (getBusy())
        {
            if (start.Happened(keyEvent, mouseEvent))
            {
                Main.gsm.resume();
            }
        }
        
    }
}

package _Actions;

import _Actors.Actor;
import _Inputs.Input;
import _Main.Main;
import _Misc.KeyEvent;
import _Misc.MouseEvent;

public class PauseAction extends Action
{
    private Input start;
    
    /**
     * Uses an Input to pause the current GameState
     * 
     * @param start
     * @param keyCode
     */
    public PauseAction (Input start, int keyCode)
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
                if (!Main.gsm.paused())
                {
                    Main.gsm.pause();
                }
                else
                {
                    Main.gsm.resume();
                }
            }
        }
    }
}

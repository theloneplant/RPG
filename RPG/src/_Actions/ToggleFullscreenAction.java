package _Actions;

import org.newdawn.slick.SlickException;

import _Actors.Actor;
import _Inputs.Input;
import _Main.Main;
import _Misc.KeyEvent;
import _Misc.MouseEvent;

public class ToggleFullscreenAction extends Action
{
    private Input start;
    
    /**
     * Meant for use in buttons and other interfaces - Switches the display
     * between windowed and fullscreen
     * 
     * @param start
     */
    public ToggleFullscreenAction (Input start)
    {
        this.start = start;
        this.start();
    }
    
    public void update (Actor parent, double delta, KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        if (getBusy())
        {
            if (start.Happened(keyEvent, mouseEvent))
            {
                try
                {
                    if (Main.gsm.gc.isFullscreen())
                    {
                        Main.gsm.gc.setFullscreen(false);
                        Main.fullscreen = false;
                    }
                    else
                    {
                        Main.gsm.gc.setFullscreen(true);
                        Main.fullscreen = true;
                    }
                }
                catch (SlickException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}

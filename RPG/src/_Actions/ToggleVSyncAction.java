package _Actions;

import _Actors.Actor;
import _Inputs.Input;
import _Main.Main;
import _Misc.KeyEvent;
import _Misc.MouseEvent;

public class ToggleVSyncAction extends Action
{
    private Input start;
    
    /**
     * Meant for use in buttons and other interfaces - Switches vertical syncing
     * between on and off.
     * 
     * V-sync matches the refresh rate of the game to the monitor's native
     * refresh rates. Generally this locks framerates at intervals of 30 (15,
     * 30, 60, or 120 generally). This feature prevents screen tearing when in
     * fullscreen mode.
     * 
     * @param start
     */
    public ToggleVSyncAction (Input start)
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
                if (Main.gsm.gc.isVSyncRequested())
                {
                    Main.gsm.gc.setVSync(false);
                }
                else
                {
                    Main.gsm.gc.setVSync(true);
                }
            }
        }
    }
}

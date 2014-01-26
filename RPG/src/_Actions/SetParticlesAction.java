package _Actions;

import _Actors.Actor;
import _Inputs.Input;
import _Main.Main;
import _Misc.KeyEvent;
import _Misc.MouseEvent;

public class SetParticlesAction extends Action
{
    private Input        start;
    private Main.Setting setting;
    
    /**
     * Changes the amount of particles that are emitted globally
     * 
     * @param setting
     * @param start
     */
    public SetParticlesAction (Main.Setting setting, Input start)
    {
        this.start = start;
        this.setting = setting;
        this.start();
    }
    
    public void update (Actor parent, double delta, KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        if (getBusy())
        {
            if (start.Happened(keyEvent, mouseEvent))
            {
                Main.particleSetting = setting;
            }
        }
    }
}

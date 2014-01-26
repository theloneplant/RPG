package _Actions;

import org.newdawn.slick.Color;

import _Actors.Actor;
import _Actors.AimLine;
import _Actors.PC;
import _Inputs.Input;
import _Misc.KeyEvent;
import _Misc.MouseEvent;

public class AimAction extends Action
{
    private Input   start, end;
    private AimLine aim;
    
    /**
     * Controls the visibility of the players aim line with Inputs
     * 
     * @param aim
     * @param start
     * @param end
     * @param keyCode
     * @param c
     */
    public AimAction (AimLine aim, Input start, Input end, int keyCode, Color c)
    {
        this.start = start;
        this.end = end;
        this.aim = aim;
        aim.setColor(c);
    }
    
    public void update (Actor parent, double gameTime, KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        if (!enabled)
        {
            this.stop();
            aim.setVisible(false);
        }
        
        if (this.start.Happened(keyEvent, mouseEvent) && !getBusy() && (PC) parent != null)
        {
            this.start();
            aim.setVisible(true);
        }
        
        if (this.end.Happened(keyEvent, mouseEvent) && getBusy())
        {
            this.stop();
            aim.setVisible(false);
        }
    }
}

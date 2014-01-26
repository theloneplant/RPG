package _Actions;

import java.awt.Dimension;

import org.newdawn.slick.SlickException;

import _Actors.Actor;
import _Inputs.Input;
import _Main.Main;
import _Misc.KeyEvent;
import _Misc.MouseEvent;

public class SetResolutionAction extends Action
{
    private Input     start;
    private Dimension dim;
    
    /**
     * Meant for use in buttons and other interfaces - Changes the game's
     * resolution and attempts to scale everything back to size
     * 
     * There are known bugs correlated with resizing, but nothing that will
     * break the game
     * 
     * @param dim
     * @param start
     */
    public SetResolutionAction (Dimension dim, Input start)
    {
        this.start = start;
        this.dim = dim;
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
                    Main.gsm.gc.setDisplayMode(dim.width, dim.height, Main.fullscreen);
                    Main.screen = dim;
                    Main.camera.resize(dim);
                    Main.camera.reset();
                }
                catch (SlickException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}

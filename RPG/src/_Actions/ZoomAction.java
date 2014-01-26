package _Actions;

import org.lwjgl.input.Mouse;

import _Actors.Actor;
import _Inputs.Input;
import _Main.Main;
import _Misc.KeyEvent;
import _Misc.MouseEvent;

public class ZoomAction extends Action
{
    private Input zoom;
    private float zoomVel, maxOut, maxIn, maxVel;
    
    /**
     * Uses an input to accelerate the game's scale in and out from the player.
     * 
     * @param zoom
     */
    public ZoomAction (Input zoom)
    {
        this.start();
        this.zoom = zoom;
        maxIn = (float) 2;
        maxOut = (float) .8;
        maxVel = (float) 6;
        zoomVel = 0;
    }
    
    public void update (Actor parent, double delta, KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        if (getBusy())
        {
            int wheel = Mouse.getDWheel();
            if (wheel > 0)
            {
                if (Main.scale <= maxIn)
                {
                    zoomVel += 1.5;
                }
            }
            else if (wheel < 0)
            {
                if (Main.scale >= maxOut)
                {
                    zoomVel -= 1.5;
                }
            }
            
            float decrement = (float) 1.1; // must be above 1, the larger it is,
                                           // the faster the decrement
            int fps = Main.gsm.gc.getFPS(); // scaling from 60 fps
            float scaledDecrement = ((decrement - 1) * 60 / fps) + 1;
            
            zoomVel = (float) (zoomVel / scaledDecrement);
            float scaledVel = zoomVel * Main.scale;
            
            if (Math.abs(zoomVel) > maxVel)
            {
                if (zoomVel > 0)
                {
                    zoomVel = maxVel;
                    scaledVel = maxVel;
                }
                else
                {
                    zoomVel = -maxVel;
                    scaledVel = -maxVel;
                }
            }
            
            if (Math.abs(scaledVel) < .01)
            {
                zoomVel = 0;
                scaledVel = 0;
            }
            
            Main.scale += scaledVel * delta;
            
            if (Main.scale < maxOut)
            {
                Main.scale = maxOut;
                zoomVel = 0;
                scaledVel = 0;
            }
            else if (Main.scale > maxIn)
            {
                Main.scale = maxIn;
                zoomVel = 0;
                scaledVel = 0;
            }
        }
    }
}

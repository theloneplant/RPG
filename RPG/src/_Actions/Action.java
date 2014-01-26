package _Actions;

import _Actors.Actor;
import _Misc.KeyEvent;
import _Misc.MouseEvent;

public class Action
{
    private boolean   runAtStart;
    protected boolean enabled;
    private boolean   busy;
    
    /**
     * Actions are classes that modify actors in the game through the use of
     * Inputs. It is possible to have nested actions
     */
    public Action ()
    {
        runAtStart = false;
        enabled = true;
        busy = false;
    }
    
    /**
     * Global update
     * 
     * @param parent
     * @param gameTime
     * @param keyEvent
     * @param mouseEvent
     */
    public void update (Actor parent, double gameTime, KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        
    }
    
    /**
     * Sets busy to true
     */
    public void start ()
    {
        if (busy == false)
            busy = true;
    }
    
    /**
     * Sets busy to false
     */
    public void stop ()
    {
        if (busy == true)
            busy = false;
    }
    
    public boolean getRunAtStart ()
    {
        return runAtStart;
    }
    
    public boolean getEnabled ()
    {
        return enabled;
    }
    
    public boolean getBusy ()
    {
        return busy;
    }
    
    public void setRunAtStart (boolean in)
    {
        runAtStart = in;
    }
    
    public void setEnabled (boolean in)
    {
        enabled = in;
    }
    
    public void setBusy (boolean in)
    {
        busy = in;
    }
}

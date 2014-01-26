package _Inputs;

import _Misc.KeyEvent;
import _Misc.MouseEvent;

public class ButtonInput implements Input
{
    /**
     * This Input is created whenever a button is activated.
     */
    public ButtonInput ()
    {
    }
    
    public boolean Happened (KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        return true;
    }
}

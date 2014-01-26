package _Inputs;

import _Misc.KeyEvent;
import _Misc.MouseEvent;

public interface Input
{
    /**
     * Determines whether the input has triggered or not.
     * 
     * @param keyEvent
     * @param mouseEvent
     * @return
     */
    public boolean Happened (KeyEvent keyEvent, MouseEvent mouseEvent);
}

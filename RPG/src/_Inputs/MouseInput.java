package _Inputs;

import _Misc.KeyEvent;
import _Misc.MouseEvent;

public class MouseInput implements Input
{
    private MouseEvent.MouseEventType eventType;
    private int                       mouseButton;
    
    /**
     * MouseInput happens whenever its mouse code and event type match the
     * current mouse event
     * 
     * @param in
     * @param eventType
     */
    public MouseInput (int in, MouseEvent.MouseEventType eventType)
    {
        mouseButton = in;
        this.eventType = eventType;
    }
    
    public boolean Happened (KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        return (mouseEvent == null) ? false : (mouseEvent.eventType == eventType && mouseEvent.button == mouseButton);
    }
}

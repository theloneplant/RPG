package _Inputs;

import _Misc.KeyEvent;
import _Misc.MouseEvent;

public class KeyInput implements Input
{
    private int                   keyCode;
    private KeyEvent.KeyEventType eventType;
    
    /**
     * KeyInput happens whenever its key code and event type match the current
     * key event
     * 
     * @param keyCode
     * @param eventType
     */
    public KeyInput (int keyCode, KeyEvent.KeyEventType eventType)
    {
        this.keyCode = keyCode;
        this.eventType = eventType;
    }
    
    public boolean Happened (KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        return (keyEvent == null) ? false : (keyEvent.eventType == eventType && keyEvent.keyCode == keyCode);
    }
}
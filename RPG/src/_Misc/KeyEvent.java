package _Misc;

public class KeyEvent
{
    public final char   keyChar;
    public final int    keyCode;
    public KeyEventType eventType;
    
    /**
     * An event that is made whenever a keyboard is typed on. It gets passed
     * through the main update loop.
     * 
     * @param eventType
     * @param key
     * @param c
     */
    public KeyEvent (KeyEventType eventType, int key, char c)
    {
        this.eventType = eventType;
        keyCode = key;
        keyChar = c;
    }
    
    /**
     * An enumerator that determines if a key event was down or up.
     * 
     * @author Michael
     * 
     */
    public enum KeyEventType
    {
        down, up
    }
}

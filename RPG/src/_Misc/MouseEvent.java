package _Misc;

public class MouseEvent
{
    public final int      button, x, y, clickCount, oldX, oldY, wheelChange;
    public MouseEventType eventType;
    
    /**
     * An event that is made whenever the mouse is used. It gets passed through
     * the main update loop.
     * 
     * @param eventType
     * @param button
     * @param x
     * @param y
     * @param oldX
     * @param oldY
     * @param clickCount
     * @param wheelChange
     */
    public MouseEvent (MouseEventType eventType, int button, int x, int y, int oldX, int oldY, int clickCount, int wheelChange)
    {
        this.eventType = eventType;
        this.button = button;
        this.x = x;
        this.y = y;
        this.oldX = oldX;
        this.oldY = oldY;
        this.clickCount = clickCount;
        this.wheelChange = wheelChange;
    }
    
    /**
     * An enumerator that determines what type a mouse event was.
     * 
     * @author Michael
     * 
     */
    public enum MouseEventType
    {
        clicked, dragged, moved, pressed, released, wheelMoved
    }
}

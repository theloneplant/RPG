package _Actors.Interface;

import java.awt.Dimension;

import org.newdawn.slick.Graphics;

import _Actions.Action;
import _Appearances.Appearance;
import _Main.Main;
import _Misc.KeyEvent;
import _Misc.MouseEvent;
import _Vectors.Position;

public class Button extends Interface
{
    protected Appearance normal, hover, clicked;
    protected Action     action;
    protected Text       text;
    protected int        textX, textY;
    protected Window     parent;
    private Position     offset;
    private boolean      focused, title;
    
    /**
     * An actor that behaves like a button.
     * 
     * All buttons have a child Text actor
     * 
     * @param text
     * @param parent
     * @param size
     * @param offset
     * @param action
     * @param normal
     * @param hover
     * @param clicked
     * @param textX
     * @param textY
     * @param isTitleFont
     * @param centeredText
     */
    public Button (String text, Window parent, Dimension size, Position offset, Action action, Appearance normal, Appearance hover, Appearance clicked, int textX, int textY, boolean isTitleFont,
            boolean centeredText)
    {
        super(text);
        setDrawPosition(offset);
        setDrawDimension(size);
        setHitBoxDimension(size);
        setDynamic(false);
        setAppearance(normal);
        this.offset = offset;
        this.parent = parent;
        this.action = action;
        this.text = new Text(text, this, new Position(textX, textY, 0), isTitleFont, centeredText);
        this.textX = textX;
        this.textY = textY;
        this.normal = normal;
        this.hover = hover;
        this.clicked = clicked;
        title = isTitleFont;
        focused = false;
    }
    
    public void update (double delta, KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        super.update(delta, keyEvent, mouseEvent);
        text.updatePos();
        updatePos();
        
        if (isClicked(mouseEvent) && action != null)
        {
            action.start();
            action.update(this, delta, keyEvent, mouseEvent);
            action.stop();
        }
    }
    
    public void draw (Graphics g)
    {
        super.draw(g);
        text.draw(g);
    }
    
    /**
     * Checks if the button was clicked by the mouse
     * 
     * @param mouseEvent
     * @return
     */
    public boolean isClicked (MouseEvent mouseEvent)
    {
        if (mouseEvent != null)
        {
            if (mouseEvent.x >= getPosition().x / Main.scale - Main.camera.getPosition().x
                    && mouseEvent.x <= getPosition().x / Main.scale - Main.camera.getPosition().x + getDrawBox().width / Main.scale)
            {
                if (mouseEvent.y >= getPosition().y / Main.scale - Main.camera.getPosition().y
                        && mouseEvent.y <= getPosition().y / Main.scale - Main.camera.getPosition().y + getDrawBox().height / Main.scale)
                {
                    if (mouseEvent.eventType.equals(MouseEvent.MouseEventType.released))
                    {
                        setAppearance(hover);
                        focused = true;
                        return true;
                    }
                    else if (mouseEvent.eventType.equals(MouseEvent.MouseEventType.moved))
                    {
                        setAppearance(hover);
                        return false;
                    }
                    else
                    {
                        setAppearance(clicked);
                        focused = true;
                        return false;
                    }
                }
                else
                {
                    setAppearance(normal);
                    if (mouseEvent.eventType.equals(MouseEvent.MouseEventType.released))
                    {
                        focused = false;
                    }
                    return false;
                }
            }
            else
            {
                setAppearance(normal);
                if (mouseEvent.eventType.equals(MouseEvent.MouseEventType.released))
                {
                    focused = false;
                }
                return false;
            }
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Updates the position relative to a parent actor
     */
    public void updatePos ()
    {
        setDrawPosition(new Position(parent.getScaledPosition().x + offset.x, parent.getScaledPosition().y + offset.y, parent.getScaledPosition().z + offset.z));
    }
    
    public void setOffset (Position p)
    {
        offset = p;
    }
    
    @Override
    public Position getOffset ()
    {
        return offset;
    }
    
    public boolean isFocused ()
    {
        return focused;
    }
    
    public Text getText ()
    {
        return text;
    }
    
    public void setText (String text)
    {
        this.text.setText(text);
    }
    
    public void setWindow (Window win)
    {
        this.parent = win;
    }
}

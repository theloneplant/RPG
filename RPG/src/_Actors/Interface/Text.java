package _Actors.Interface;

import java.awt.Dimension;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import _Appearances.RectangleAppearance;
import _Main.Main;
import _Misc.KeyEvent;
import _Misc.MouseEvent;
import _Vectors.Position;

public class Text extends Interface
{
    private String    text;
    private Interface parent;
    private Position  offset;
    private boolean   title, centered;
    
    /**
     * An actor that holds and displays text relative to its parent and offset.
     * The title boolean changes whether it uses a title font or normal font.
     * 
     * @param text
     * @param parent
     * @param offset
     * @param title
     * @param centered
     */
    public Text (String text, Interface parent, Position offset, boolean title, boolean centered)
    {
        super(text);
        this.text = text;
        this.parent = parent;
        this.offset = offset;
        this.title = title;
        this.centered = centered;
        this.setCollides(false);
        setAppearance(new RectangleAppearance(new Color(0, 0, 0, 0), new Dimension(0, 0)));
    }
    
    public void update (double delta, KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        super.update(delta, keyEvent, mouseEvent);
        updatePos();
    }
    
    public void draw (Graphics g)
    {
        if (title)
        {
            Main.getTitleFont().drawString((int) getPosition().x, (int) getPosition().y, text);
        }
        else
        {
            Main.getBodyFont().drawString((int) getPosition().x, (int) getPosition().y, text);
        }
    }
    
    /**
     * Updates the position relative to a parent actor
     */
    @Override
    public void updatePos ()
    {
        if (centered)
        {
            setDrawPosition(new Position((int) (parent.getScaledPosition().x + parent.getDrawBox().width / 2 - getWidth() / 2), (int) (parent.getScaledPosition().y + offset.y),
                    (int) (parent.getScaledPosition().z + offset.z)));
        }
        else
        {
            setDrawPosition(new Position((int) (parent.getScaledPosition().x + offset.x), (int) (parent.getScaledPosition().y + offset.y), (int) (parent.getScaledPosition().z + offset.z)));
        }
    }
    
    @Override
    public void setOffset (Position p)
    {
        offset = p;
    }
    
    @Override
    public Position getOffset ()
    {
        return offset;
    }
    
    public int getWidth ()
    {
        if (title)
        {
            return Main.getTitleFont().getWidth(text);
        }
        else
        {
            return Main.getBodyFont().getWidth(text);
        }
    }
    
    public int getHeight ()
    {
        if (title)
        {
            return Main.getTitleFont().getHeight(text);
        }
        else
        {
            return Main.getBodyFont().getHeight(text);
        }
    }
    
    public String toString ()
    {
        return text;
    }
    
    public void setText (String text)
    {
        this.text = text;
    }
}

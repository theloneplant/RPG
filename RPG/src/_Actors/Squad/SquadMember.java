package _Actors.Squad;

import java.awt.Dimension;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import _Actors.NPC;
import _Actors.Interface.Text;
import _Actors.Interface.Window;
import _Misc.KeyEvent;
import _Misc.MouseEvent;
import _Vectors.Position;

public class SquadMember extends NPC
{
    private boolean selected, hovered;
    private Text    text;
    private Window  win;
    
    /**
     * NPC that the player can command. SquadMembers handle whether or not they
     * are selected and also display their names.
     * 
     * @param name
     */
    public SquadMember (String name)
    {
        super();
        this.setName(name);
        this.win = new Window(new Dimension(0, 0), new Position(0, 0, 0), new Color(0, 0, 0, 150));
        this.text = new Text(name, win, new Position(0, 0, 0), false, true);
        win.setDrawDimension(new Dimension(text.getWidth() + 6, text.getHeight()));
        win.add(text);
        selected = false;
        hovered = false;
        setSpeed(200);
    }
    
    public void update (double delta, KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        super.update(delta, keyEvent, mouseEvent);
        win.update(delta, keyEvent, mouseEvent);
        win.setScaledPosition(new Position(getPosition().x - win.getDrawBox().width / 2 + getDrawBox().width / 2, getPosition().y - win.getDrawBox().height, 0));
        text.update(delta, keyEvent, mouseEvent);
    }
    
    public void draw (Graphics g)
    {
        super.draw(g);
        win.draw(g);
        
        if (selected)
        {
            g.setColor(new Color(30, 255, 30, 255));
            g.drawRect((float) getPosition().x - 1, (float) getPosition().y - 1, (float) getDrawBox().width + 2, (float) getDrawBox().height + 2);
        }
        else if (hovered)
        {
            g.setColor(new Color(30, 255, 30, 100));
            g.drawRect((float) getPosition().x - 1, (float) getPosition().y - 1, (float) getDrawBox().width + 2, (float) getDrawBox().height + 2);
        }
    }
    
    public boolean getSelected ()
    {
        return selected;
    }
    
    public void setSelected (boolean isSelected)
    {
        selected = isSelected;
    }
    
    public boolean getHovered ()
    {
        return hovered;
    }
    
    public void setHovered (boolean isHovered)
    {
        hovered = isHovered;
    }
}

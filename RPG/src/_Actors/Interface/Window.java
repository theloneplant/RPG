package _Actors.Interface;

import java.awt.Dimension;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import _Main.Main;
import _Misc.KeyEvent;
import _Misc.MouseEvent;
import _Vectors.Position;

public class Window extends Interface
{
    private ArrayList<Interface> list;
    private Color                color;
    private Position             scaledPosition;
    
    /**
     * The standard Interface actor which draws a rectangle around its position
     * and houses a list of child Interfaces
     * 
     * @param dim
     * @param p
     * @param c
     * @param list
     */
    public Window (Dimension dim, Position p, Color c, ArrayList<Interface> list)
    {
        super("Window");
        setDrawDimension(dim);
        setDrawPosition(p);
        this.setCollides(false);
        scaledPosition = new Position(p.x * Main.scale, p.y * Main.scale, p.z * Main.scale);
        this.list = list;
    }
    
    /**
     * The standard Interface actor which draws a rectangle around its position
     * and houses a list of child Interfaces
     * 
     * @param dim
     * @param p
     * @param c
     */
    public Window (Dimension dim, Position p, Color c)
    {
        super("Window");
        setDrawDimension(dim);
        setDrawPosition(p);
        scaledPosition = new Position(p.x * Main.scale, p.y * Main.scale, p.z * Main.scale);
        this.color = c;
        list = new ArrayList<Interface>();
    }
    
    public void update (double delta, KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        updatePos();
        for (int i = 0; i < list.size(); i++)
        {
            list.get(i).update(delta, keyEvent, mouseEvent);
        }
    }
    
    public void draw (Graphics g)
    {
        g.setColor(color);
        g.fillRect((float) getScaledPosition().x, (float) getScaledPosition().y, (float) getDrawBox().width, (float) getDrawBox().height);
        
        for (int i = 0; i < list.size(); i++)
        {
            list.get(i).draw(g);
        }
    }
    
    public void add (Interface a)
    {
        list.add(a);
    }
    
    /**
     * Updates the position relative to a parent actor
     */
    public void updatePos ()
    {
        scaledPosition = new Position(getPosition().x * Main.scale, getPosition().y * Main.scale, getPosition().z * Main.scale);
        for (int i = 0; i < list.size(); i++)
        {
            list.get(i).updatePos();
        }
    }
    
    /**
     * Attempts to scale itself in relation to the camera
     * 
     * @param dim
     */
    public void resize (Dimension dim)
    {
        for (int i = 0; i < list.size(); i++)
        {
            list.get(i).setOffset(
                    new Position(list.get(i).getOffset().x + dim.width / 2 - getDrawBox().width / 2, list.get(i).getOffset().y + dim.height / 2 - getDrawBox().height / 2, list.get(i).getOffset().z));
            list.get(i).setDrawPosition(
                    new Position(list.get(i).getPosition().x + dim.width / 2 - getDrawBox().width / 2, list.get(i).getPosition().y + dim.height / 2 - getDrawBox().height / 2, list.get(i)
                            .getPosition().z));
        }
        setDrawDimension(dim);
    }
    
    public void setScaledPosition (Position p)
    {
        scaledPosition = p;
    }
    
    public Position getScaledPosition ()
    {
        return scaledPosition;
    }
    
    public void setOffset (Position p)
    {
        // No support of nested windows yet, I will add this later
    }
    
    @Override
    public Position getOffset ()
    {
        return new Position(0, 0, 0);
    }
}

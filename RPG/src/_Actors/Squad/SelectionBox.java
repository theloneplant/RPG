package _Actors.Squad;

import java.awt.Dimension;
import java.awt.geom.Rectangle2D;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import _Actors.Actor;
import _Appearances.RectangleAppearance;
import _Main.Main;
import _Misc.KeyEvent;
import _Misc.MouseEvent;
import _Vectors.Position;

public class SelectionBox extends Actor
{
    private Position    oldPos, newPos;
    private boolean     visible;
    private int         lastX, lastY;
    private Rectangle2D tempRect;
    
    /**
     * A rectangle that draws when the player wants to select a number of squad
     * members. Any squad members in the selection become selected and can be
     * ordered to perform tasks.
     * 
     * This is still unfinished and contains a bug where the rectangle can only
     * select from top left to bottom right
     */
    public SelectionBox ()
    {
        super("SelectionBox");
        oldPos = new Position(0, 0, 0);
        newPos = new Position(0, 0, 0);
        setDrawPosition(new Position(0, 0, 0));
        setDrawDimension(new Dimension(1, 1));
        setHitBoxDimension(new Dimension(0, 0));
        setHitBoxOffset(new Dimension(0, 0));
        tempRect = new Rectangle2D.Double();
        setAppearance(new RectangleAppearance(new Color(30, 255, 30, 20), new Dimension(0, 0)));
        visible = false;
    }
    
    public void update (double delta, KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        if (mouseEvent != null)
        {
            if (mouseEvent.eventType != MouseEvent.MouseEventType.wheelMoved)
            {
                lastX = (int) mouseEvent.x;
                lastY = (int) mouseEvent.y;
            }
            else
            {   
                
            }
        }
        newPos = new Position(lastX + Main.camera.getPosition().x, lastY + Main.camera.getPosition().y, 0);
        tempRect.setFrame(Main.userPosition.x, Main.camera.getPosition().y + Main.camera.getDrawBox().height - 10, (newPos.x - oldPos.x), (newPos.y - oldPos.y));
        setDrawPosition(new Position(Main.userPosition.x, Main.camera.getPosition().y + Main.camera.getDrawBox().height - 10, 0));
        setDrawDimension(new Dimension((int) (newPos.x - oldPos.x), (int) (newPos.y - oldPos.y)));
    }
    
    public void draw (Graphics g)
    {
        if (visible)
        {
            g.setColor(new Color(30, 255, 30, 255));
            g.drawRect((float) (oldPos.x), (float) (oldPos.y), (float) (newPos.x - oldPos.x), (float) (newPos.y - oldPos.y));
            g.setColor(new Color(30, 255, 30, 50));
            g.fillRect((float) (oldPos.x), (float) (oldPos.y), (float) (newPos.x - oldPos.x), (float) (newPos.y - oldPos.y));
        }
    }
    
    public void setOldPos (Position p)
    {
        oldPos = p;
    }
    
    public Position getOldPos ()
    {
        return oldPos;
    }
    
    public void setVisible (boolean visible)
    {
        this.visible = visible;
    }
}

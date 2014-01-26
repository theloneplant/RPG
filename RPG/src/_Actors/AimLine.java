package _Actors;

import java.awt.Dimension;
import java.awt.Rectangle;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import _Appearances.LineAppearance;
import _Main.Main;
import _Misc.KeyEvent;
import _Misc.MouseEvent;
import _Vectors.Position;

public class AimLine extends Actor
{
    private LineAppearance appearance;
    private Actor          character;
    private Rectangle      line;
    private int            lastX = 0;
    private int            lastY = 0;
    private boolean        visible;
    
    /**
     * Actor that draws a line from the player to the mouse and beyond
     * 
     * @param myActor
     */
    public AimLine (Actor myActor)
    {
        super("AimLine");
        setDrawDimension(new Dimension(1, 1));
        setHitBoxDimension(new Dimension(0, 0));
        setHitBoxOffset(new Dimension(0, 0));
        appearance = new LineAppearance(Color.white, new Dimension(0, 0));
        setAppearance(appearance);
        setDynamic(false);
        character = myActor;
        visible = false;
    }
    
    public void update (double elapsedTimeSinceLastUpdate, KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        setDrawPosition(new Position(character.getPosition().x, character.getPosition().y - 5, getPosition().z));
        int x = (int) ((int) character.getPosition().x + character.getDrawBox().width / 2);
        int y = (int) ((int) character.getPosition().y + character.getDrawBox().height / 2);
        
        if (mouseEvent != null)
        {
            lastX = (int) mouseEvent.x;
            lastY = (int) mouseEvent.y;
        }
        
        int mouseX = (int) (lastX + Main.camera.getPosition().x);
        int mouseY = (int) (lastY + Main.camera.getPosition().y);
        
        int lineX = x - mouseX;
        int lineY = mouseY - y;
        
        double vectorX = lineX * (lineX - Main.dim.width);
        double vectorY = lineY * (Main.dim.width - lineX);
        
        line = new Rectangle(x, y, (int) vectorX, (int) vectorY);
    }
    
    public void draw (Graphics g)
    {
        if (visible == true)
        {
            appearance.draw(line, g);
        }
    }
    
    public void setColor (Color c)
    {
        appearance.setColor(c);
    }
    
    public void setVisible (boolean vis)
    {
        visible = vis;
    }
}

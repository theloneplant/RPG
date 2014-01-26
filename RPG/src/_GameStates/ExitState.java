package _GameStates;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import _Main.Main;
import _Misc.KeyEvent;
import _Misc.MouseEvent;

public class ExitState extends GameState
{
    Color c;
    
    /**
     * A simple GameState that exits the game.
     * 
     * @param c
     */
    public ExitState (Color c)
    {
        this.c = c;
    }
    
    @Override
    public void init ()
    {
    }
    
    @Override
    public void cleanUp ()
    {
    }
    
    @Override
    public void pause ()
    {
    }
    
    @Override
    public void resume ()
    {
    }
    
    @Override
    public void update (KeyEvent keyEvent, MouseEvent mouseEvent, double delta)
    {
        try
        {
            Main.gsm.exit();
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }
    }
    
    @Override
    public void draw (GameContainer gc, Graphics g)
    {
        g.setColor(c);
        g.fillRect(0, 0, Main.screen.width, Main.screen.height);
    }
}

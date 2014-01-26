package _GameStates;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import _Actors.Actor;
import _Misc.KeyEvent;
import _Misc.MouseEvent;

public abstract class GameState
{
    public ArrayList<Actor> actors = new ArrayList<Actor>();
    protected boolean       paused = false;
    
    /**
     * A GameState handles updates, drawing, and transitioning between different
     * screens in the game. This allows the player to switch to a title screen,
     * options menu, and the game itself. This method of handling things is very
     * powerful and can be used to do a lot of different things.
     */
    
    /**
     * Initializes the GameState
     */
    public abstract void init ();
    
    /**
     * Finishes anything that will need to be done before the GameState is
     * removed.
     * 
     * This method is always called before removal.
     */
    public abstract void cleanUp ();
    
    /**
     * This method is called whenever the GameStateManager pauses the state.
     */
    public abstract void pause ();
    
    /**
     * This method is called whenever the GameStateManager resumes the state.
     */
    public abstract void resume ();
    
    /**
     * The global update method called by the game loop.
     * 
     * @param keyEvent
     * @param mouseEvent
     * @param delta
     */
    public abstract void update (KeyEvent keyEvent, MouseEvent mouseEvent, double delta);
    
    /**
     * The global draw method called by the game loop
     * 
     * @param gc
     * @param g
     */
    public abstract void draw (GameContainer gc, Graphics g);
}

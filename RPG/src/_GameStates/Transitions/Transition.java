package _GameStates.Transitions;

import org.newdawn.slick.Graphics;

import _GameStates.GameState;
import _Main.Main;

public class Transition
{
    protected GameState state1, state2;
    protected boolean   switched, pushState, popState;
    
    /**
     * A transition switches game states. It can either get passed in two states
     * to switch between them or one and pop to go to a lower level in the
     * stack.
     * 
     * @param gs1
     * @param gs2
     * @param pushState
     */
    public Transition (GameState gs1, GameState gs2, boolean pushState)
    {
        state1 = gs1;
        state2 = gs2;
        state1.pause();
        this.pushState = pushState;
        switched = false;
    }
    
    /**
     * A transition switches game states. It can either get passed in two states
     * to switch between them or one and pop to go to a lower level in the
     * stack.
     * 
     * @param gs1
     * @param popState
     */
    public Transition (GameState gs1, boolean popState)
    {
        state1 = gs1;
        state2 = gs1;
        this.popState = popState;
        state1.pause();
    }
    
    /**
     * Updates the Transition
     * 
     * @param delta
     */
    public void update (double delta)
    {
        if (pushState)
        {
            Main.gsm.pushState(state2);
        }
        else if (popState)
        {
            Main.gsm.popState();
        }
        else
        {
            Main.gsm.changeState(state2);
        }
        switched = true;
    }
    
    /**
     * Draws the transition
     * 
     * @param g
     */
    public void draw (Graphics g)
    {
        
    }
    
    /**
     * Returns whether or not the state is finished switching.
     * 
     * @return
     */
    public boolean isComplete ()
    {
        return switched;
    }
}

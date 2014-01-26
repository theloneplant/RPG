package _GameStateManager;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import _GameStates.GameState;
import _GameStates.PauseState;
import _GameStates.Transitions.Transition;
import _Main.Main;
import _Misc.KeyEvent;
import _Misc.MouseEvent;

public class GameStateManager
{
    private boolean                    running, paused, switching;
    private ArrayList<GameState>       states   = new ArrayList<GameState>();
    private Transition                 myTransition;
    public AppGameContainer            gc;
    public static ArrayList<Dimension> displays = new ArrayList<Dimension>();
    
    /**
     * Initializes the game. The GameStateManager stores an implementation of a
     * stack of GameStates and controls how they behave and update.
     * 
     * @param title
     * @param width
     * @param height
     * @param fullscreen
     * @throws SlickException
     */
    public void init (String title, int width, int height, boolean fullscreen) throws SlickException
    {
        AppGameContainer app = new AppGameContainer(new Main());
        
        int count = 0;
        GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        for (int i = 0; i < devices.length; i++)
        {
            GraphicsDevice dev = devices[i];
            DisplayMode[] modes = dev.getDisplayModes();
            for (int j = 0; j < modes.length; j++)
            {
                DisplayMode m = modes[j];
                if (m.getWidth() <= 1920 && m.getWidth() >= 800)
                {
                    if (m.getHeight() <= 1080 && m.getHeight() >= 600)
                    {
                        Dimension dim = new Dimension(m.getWidth(), m.getHeight());
                        if (count - 1 >= 0 && displays.get(count - 1) != null)
                        {
                            if (displays.get(count - 1).width != dim.width || displays.get(count - 1).height != dim.height)
                            {
                                displays.add(new Dimension(m.getWidth(), m.getHeight()));
                                count++;
                            }
                        }
                        else
                        {
                            displays.add(dim);
                            count++;
                        }
                    }
                }
                
            }
        }
        
        for (int i = 0; i < displays.size(); i++)
        {
            for (int j = 0; j < displays.size(); j++)
            {
                if (displays.get(i).equals(displays.get(j)))
                {
                    displays.remove(j);
                }
            }
        }
        
        // Using a basic bubble sort to organize the resolutions by width
        // This wont affect performance much since it only gets called once
        boolean flag = true;
        Dimension temp;
        while (flag)
        {
            flag = false;
            for (int i = 0; i < displays.size() - 1; i++)
            {
                if (displays.get(i).width < displays.get(i + 1).width)
                {
                    temp = displays.get(i);
                    displays.set(i, displays.get(i + 1));
                    displays.set(i + 1, temp);
                    flag = true;
                }
            }
        }
        
        app.setDisplayMode(width, height, false);
        app.setFullscreen(fullscreen);
        app.setMultiSample(8);
        app.setAlwaysRender(true);
        app.setUpdateOnlyWhenVisible(false);
        app.setShowFPS(false);
        app.setVSync(true);
        app.setSmoothDeltas(true);
        app.setTargetFrameRate(240);
        paused = false;
        running = true;
        
        gc = app;
        app.start();
    }
    
    /**
     * Exits the game by removing all of the GameStates
     * 
     * @throws SlickException
     */
    public void exit () throws SlickException
    {
        while (!states.isEmpty())
        {
            states.get(states.size() - 1).cleanUp();
            states.remove(states.size() - 1);
        }
        
        if (gc.isFullscreen())
        {
            gc.setFullscreen(false);
        }
        gc.exit();
    }
    
    /**
     * Changes from one state to another by popping the current state and
     * pushing a new one.
     * 
     * Changes the stack laterally
     * 
     * @param state
     */
    public void changeState (GameState state)
    {
        if (!states.isEmpty())
        {
            states.get(states.size() - 1).cleanUp();
            states.remove(states.size() - 1);
        }
        pushState(state);
    }
    
    /**
     * Pushes a new state on to the stack.
     * 
     * Changes the stack vertically
     * 
     * @param state
     */
    public void pushState (GameState state)
    {
        if (!states.isEmpty())
        {
            states.get(states.size() - 1).pause();
        }
        states.add(state);
        states.get(states.size() - 1).init();
    }
    
    /**
     * Pops a state from the stack.
     * 
     * Changes the stack vertically
     */
    public void popState ()
    {
        if (!states.isEmpty())
        {
            states.get(states.size() - 1).cleanUp();
            states.remove(states.size() - 1);
        }
        
        if (!states.isEmpty())
        {
            states.get(states.size() - 1).resume();
        }
    }
    
    /**
     * Returns the top element of the stack.
     * 
     * Doesn't modify the stack
     * 
     * @return
     */
    public GameState peek ()
    {
        return states.get(states.size() - 1);
    }
    
    /**
     * The main update method which calls almost all updates in the game.
     * 
     * @param keyEvent
     * @param mouseEvent
     * @param delta
     */
    public void update (KeyEvent keyEvent, MouseEvent mouseEvent, double delta)
    {
        if (switching == true)
        {
            keyEvent = null;
            mouseEvent = null;
            if (myTransition.isComplete())
            {
                switching = false;
            }
            else
            {
                myTransition.update(delta);
            }
        }
        
        states.get(states.size() - 1).update(keyEvent, mouseEvent, delta);
    }
    
    /**
     * The main draw method which calls almost all draws in the game.
     * 
     * @param gc
     * @param g
     */
    public void draw (GameContainer gc, Graphics g)
    {
        states.get(states.size() - 1).draw(gc, g);
        
        if (switching == true)
        {
            if (myTransition.isComplete())
            {
                switching = false;
            }
            else
            {
                myTransition.draw(g);
            }
        }
    }
    
    /**
     * Pauses the current state.
     */
    public void pause ()
    {
        if (paused == false)
        {
            paused = true;
            pushState(new PauseState(states.get(states.size() - 1)));
        }
    }
    
    /**
     * Resumes the current state.
     */
    public void resume ()
    {
        if (paused == true)
        {
            paused = false;
            popState();
        }
    }
    
    /**
     * Switches between states through the use of a Transition.
     * 
     * @param trans
     */
    public void transition (Transition trans)
    {
        switching = true;
        myTransition = trans;
    }
    
    public boolean paused ()
    {
        return paused;
    }
    
    public boolean running ()
    {
        return running;
    }
}

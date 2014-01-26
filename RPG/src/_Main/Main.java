/**
 * Final Project - CS 113
 * Professor - Kevin Lewis
 * Author - Michael LaPlante
 * 
 * This is a final project continuation from CS 112. Features added from 
 * V0.4 to V1.0 include a particle engine, game states, z axis computing, 
 * better gameplay, basic pathfinding algorithm for NPCs, squads, title screen, 
 * options menu, improved pause menu, transitions, camera zoom, and more minor 
 * code clean up.
 * 
 * ============================WALKTHROUGH=================================
 * 
 * When you start the game, you will be prompted with the title menu, which 
 * has three buttons. Here, you can configure game options, start the game,
 * or exit. 
 * 
 * When you click on options you are shown an options menu which allows
 * you to change the resolution, change whether it's fullscreen, change whether 
 * it utilizes V-sync, and change how many particles are desired in the game.
 * 
 * When you click on start, you enter the game itself, which shows your character
 * along with four NPCs that are standing idle, waiting for a command. You are 
 * able to move with WASD in order to navigate. You are able to left click + drag 
 * to select the squad members, then you can right click to order them to move 
 * to a location. If you want to kill things, then you can press LShift, which 
 * will change your gameplay mode from commanding to attacking, as you can see 
 * in the top-middle of the screen. When attacking, you can left click to shoot 
 * a fireball, and you can right click to create an aim line to help aim if desired.
 * 
 * You can pause the game by pressing ESC. When paused, you are given the option
 * to resume the game, return to the menu (title screen), configure options, 
 * or exit the game.
 * 
 * ========================================================================
 *                               Controls:
 * 
 * Movement: WASD
 * Change Control Mode: LShift
 * Pause: ESC
 * 
 * Command Mode
 * Select Squad Members: Left Click + Drag or Left Click
 * Move Command: Right Click
 * 
 * Combat Mode
 * Shoot: Left Click
 * Aim: Right Click + Hold
 * 
 */

package _Main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import _Actions.Action;
import _Actors.Actor;
import _Actors.Camera;
import _GameStateManager.GameStateManager;
import _GameStates.TitleState;
import _Misc.KeyEvent;
import _Misc.MouseEvent;
import _Vectors.Position;

public class Main extends BasicGame implements MouseListener, KeyListener
{
    /**
     * The setting enumerator used when dealing with the options menu.
     * 
     * @author Michael
     * 
     */
    public static enum Setting
    {
        None, Low, Medium, High;
    }
    
    private static UnicodeFont      bodyFont;
    private static UnicodeFont      smallFont;
    private static UnicodeFont      titleFont;
    
    // Options
    public static Dimension         dim             = new Dimension(2600, 1500);
    public static Dimension         screen          = Toolkit.getDefaultToolkit().getScreenSize();
    public static boolean           fullscreen      = false;
    public static Setting           particleSetting = Setting.High;
    // Options
    
    public static Camera            camera          = new Camera(dim);
    public static float             scale           = (float) 1;
    public static Position          userPosition    = new Position(0, 0, 0);
    
    public static GameStateManager  gsm             = new GameStateManager();
    public static ArrayList<Actor>  allActors       = new ArrayList<Actor>();
    public static ArrayList<Action> universal       = new ArrayList<Action>();
    
    private double                  previousTime    = getCurrentTime();
    private double                  timeSinceUpdate = 1;
    private double                  delta;
    
    /**
     * The main class that initializes the game and controls the game loop.
     */
    public Main ()
    {
        super("Concept");
    }
    
    /**
     * Initializes the GameStateManager
     * 
     * @param args
     * @throws SlickException
     */
    public static void main (String[] args) throws SlickException
    {
        gsm.init("RPG", screen.width, screen.height, fullscreen);
    }
    
    /**
     * Initializes the first GameState and fonts.
     */
    public void init (GameContainer gc) throws SlickException
    {
        gsm.pushState(new TitleState());
        
        Input userInput = new Input(screen.height);
        userInput.addKeyListener(this);
        userInput.addMouseListener(this);
        
        smallFont = new UnicodeFont(new Font("Calibria", Font.BOLD, 12));
        bodyFont = new UnicodeFont(new Font("Calibria", Font.BOLD, 18));
        titleFont = new UnicodeFont(new Font("Calibria", Font.BOLD, 24));
        smallFont.addAsciiGlyphs();
        bodyFont.addAsciiGlyphs();
        titleFont.addAsciiGlyphs();
        ColorEffect e = new ColorEffect();
        e.setColor(java.awt.Color.white);
        smallFont.getEffects().add(e);
        bodyFont.getEffects().add(e);
        titleFont.getEffects().add(e);
        try
        {
            smallFont.loadGlyphs();
            bodyFont.loadGlyphs();
            titleFont.loadGlyphs();
        }
        catch (SlickException e1)
        {
            e1.printStackTrace();
        }
        
    }
    
    /**
     * An unused update method needed to implement BasicGame. I use keyEvents
     * and mouseEvents instead. This simply updates the game without any events.
     */
    public void update (GameContainer gc, int delta) throws SlickException
    {
        update(null, null);
    }
    
    // ==============================UPDATE====================================
    
    /**
     * The universal update method. It's called every cycle of the game loop and
     * handles events.
     * 
     * @param keyEvent
     * @param mouseEvent
     */
    public void update (KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        delta = 0;
        delta = calculateElapsedTime();
        
        gsm.update(keyEvent, mouseEvent, delta);
        sortActors(allActors);
    }
    
    // =============================RENDER======================================
    
    /**
     * The universal drawing method. It's called every cycle of the game loop.
     */
    public void render (GameContainer gc, Graphics g) throws SlickException
    {
        gsm.draw(gc, g);
    }
    
    /**
     * Calculates the delta.
     * 
     * @return
     */
    public double calculateElapsedTime ()
    {
        double current = getCurrentTime();
        timeSinceUpdate = current - previousTime;
        previousTime = current;
        
        return timeSinceUpdate;
    }
    
    /**
     * Returns the current system time in seconds
     * 
     * @return
     */
    private double getCurrentTime ()
    {
        return System.currentTimeMillis() / 1000.0;
    }
    
    public static UnicodeFont getSmallFont ()
    {
        return smallFont;
    }
    
    public static UnicodeFont getBodyFont ()
    {
        return bodyFont;
    }
    
    public static UnicodeFont getTitleFont ()
    {
        return titleFont;
    }
    
    /**
     * A merge sort algorithm that sorts all of the actors in the arraylist
     * based on their y-axis position. I could have used a linked list, but
     * decided against it since an arraylist was already implemented and I had
     * to work on game features more.
     * 
     * I haven't noticed many issues with an arraylist thus far.
     * 
     * @param array
     * @return
     */
    public ArrayList<Actor> sortActors (ArrayList<Actor> array)
    {
        if (array.size() > 1)
        {
            int elementsInA1 = array.size() / 2;
            int elementsInA2 = elementsInA1;
            
            if ((array.size() % 2) == 1)
                elementsInA2 += 1;
            
            ArrayList<Actor> arr1 = new ArrayList<Actor>(elementsInA1);
            ArrayList<Actor> arr2 = new ArrayList<Actor>(elementsInA2);
            
            for (int i = 0; i < elementsInA1; i++)
                arr1.add(i, array.get(i));
            for (int i = elementsInA1; i < elementsInA1 + elementsInA2; i++)
                arr2.add(i - elementsInA1, array.get(i));
            
            arr1 = sortActors(arr1);
            arr2 = sortActors(arr2);
            
            int i = 0, j = 0, k = 0;
            while (arr1.size() != j && arr2.size() != k)
            {
                if (checkLocation(arr1, arr2, j, k))
                {
                    array.set(i, arr1.get(j));
                    i++;
                    j++;
                }
                else
                {
                    array.set(i, arr2.get(k));
                    i++;
                    k++;
                }
            }
            while (arr1.size() != j)
            {
                array.set(i, arr1.get(j));
                i++;
                j++;
            }
            while (arr2.size() != k)
            {
                array.set(i, arr2.get(k));
                i++;
                k++;
            }
        }
        return array;
    }
    
    /**
     * Checks if one actor is above another. This is used when sorting the main
     * array.
     * 
     * @param arr1
     * @param arr2
     * @param a
     * @param b
     * @return
     */
    public boolean checkLocation (ArrayList<Actor> arr1, ArrayList<Actor> arr2, int a, int b)
    {
        if (arr1.get(a).getDrawBox().y + arr1.get(a).getDrawBox().height < arr2.get(b).getDrawBox().y + arr2.get(b).getDrawBox().height)
            return true;
        else
            return false;
    }
    
    /**
     * Updates the game with a key pressed event
     */
    public void keyPressed (int key, char c)
    {
        KeyEvent temp = new KeyEvent(KeyEvent.KeyEventType.down, key, c);
        update(temp, null);
    }
    
    /**
     * Updates the game with a key released event
     */
    public void keyReleased (int key, char c)
    {
        KeyEvent temp = new KeyEvent(KeyEvent.KeyEventType.up, key, c);
        update(temp, null);
    }
    
    /**
     * Updates the game with a mouse clicked event
     */
    public void mouseClicked (int button, int x, int y, int clickCount)
    {
        MouseEvent temp = new MouseEvent(MouseEvent.MouseEventType.clicked, button, (int) (x / scale), (int) (y / scale), 0, 0, clickCount, 0);
        update(null, temp);
    }
    
    /**
     * Updates the game with a mouse dragged event
     */
    public void mouseDragged (int oldx, int oldy, int newx, int newy)
    {
        MouseEvent temp = new MouseEvent(MouseEvent.MouseEventType.dragged, 0, (int) (newx / scale), (int) (newy / scale), oldx, oldy, 0, 0);
        update(null, temp);
    }
    
    /**
     * Updates the game with a mouse moved event
     */
    public void mouseMoved (int oldx, int oldy, int newx, int newy)
    {
        MouseEvent temp = new MouseEvent(MouseEvent.MouseEventType.moved, 0, (int) (newx / scale), (int) (newy / scale), oldx, oldy, 0, 0);
        update(null, temp);
    }
    
    /**
     * Updates the game with a mouse pressed event
     */
    public void mousePressed (int button, int x, int y)
    {
        MouseEvent temp = new MouseEvent(MouseEvent.MouseEventType.pressed, button, (int) (x / scale), (int) (y / scale), 0, 0, 0, 0);
        update(null, temp);
    }
    
    /**
     * Updates the game with a mouse released event
     */
    public void mouseReleased (int button, int x, int y)
    {
        MouseEvent temp = new MouseEvent(MouseEvent.MouseEventType.released, button, (int) (x / scale), (int) (y / scale), 0, 0, 0, 0);
        update(null, temp);
    }
    
    /**
     * Updates the game with a mouse wheel moved event
     */
    public void mouseWheelMoved (int change)
    {
        MouseEvent temp = new MouseEvent(MouseEvent.MouseEventType.wheelMoved, 0, 0, 0, 0, 0, 0, change);
        update(null, temp);
    }
}

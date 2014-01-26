package _GameStates;

import java.awt.Dimension;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import _Actions.StatePushAction;
import _Actions.StateSwitchAction;
import _Actors.Actor;
import _Actors.Interface.Button;
import _Actors.Interface.Text;
import _Actors.Interface.Window;
import _Appearances.Appearance;
import _Appearances.RectangleAppearance;
import _GameStates.Transitions.FadeTransition;
import _Inputs.ButtonInput;
import _Main.Main;
import _Misc.KeyEvent;
import _Misc.MouseEvent;
import _Vectors.Position;

public class TitleState extends GameState
{
    private Window  win;
    private boolean paused;
    
    /**
     * The title state for the game, this state lets the player to choose to go
     * into the game or go into the options to configure settings.
     */
    @Override
    public void init ()
    {
        Main.scale = 1;
        actors = new ArrayList<Actor>();
        Main.camera.reset();
        
        Dimension startDim = new Dimension(120, 50);
        Appearance normal = new RectangleAppearance(new Color(255, 255, 255, 100), startDim);
        Appearance hover = new RectangleAppearance(new Color(255, 255, 255, 150), startDim);
        Appearance clicked = new RectangleAppearance(new Color(255, 255, 255, 50), startDim);
        
        FadeTransition fadeGame = new FadeTransition(this, new LevelState(), Color.black, (float) .5);
        FadeTransition fadeExit = new FadeTransition(this, new ExitState(Color.black), Color.black, (float) 1.5);
        StateSwitchAction action1 = new StateSwitchAction(fadeGame, new ButtonInput());
        StatePushAction action2 = new StatePushAction(new OptionsState(this), new ButtonInput());
        StateSwitchAction action3 = new StateSwitchAction(fadeExit, new ButtonInput());
        
        win = new Window(new Dimension(Main.screen.width, Main.screen.height), new Position(0, 0, 0), new Color(0, 70, 150, 155));
        
        Text title = new Text("Particle RPG", win, new Position(win.getDrawBox().width / 2 - 70, win.getDrawBox().height / 3, 0), true, true);
        Button start = new Button("Start", win, new Dimension((int) (startDim.width * 2 + 20), startDim.height), new Position(Main.screen.width / 2 - startDim.width - 10, Main.screen.height / 1.5
                - startDim.height / 2, 0), action1, normal, hover, clicked, 100, 8, true, true);
        Button options = new Button("Options", win, startDim, new Position(Main.screen.width / 2 - startDim.width - 10, Main.screen.height / 1.5 + startDim.height, 0), action2, normal, hover,
                clicked, 15, 8, true, true);
        Button exit = new Button("Exit", win, startDim, new Position(Main.screen.width / 2 + 10, Main.screen.height / 1.5 + startDim.height, 0), action3, normal, hover, clicked, 36, 8, true, true);
        actors.add(win);
        
        win.add(title);
        win.add(start);
        win.add(options);
        win.add(exit);
        
        paused = false;
        Main.allActors = actors;
    }
    
    @Override
    public void cleanUp ()
    {
        actors = Main.allActors;
        Main.allActors.clear();
    }
    
    @Override
    public void pause ()
    {
        paused = true;
        actors = Main.allActors;
    }
    
    @Override
    public void resume ()
    {
        paused = false;
        Main.allActors = actors;
    }
    
    @Override
    public void update (KeyEvent keyEvent, MouseEvent mouseEvent, double delta)
    {
        win.resize(Main.screen);
        if (paused == false)
        {
            for (int i = 0; i < Main.allActors.size(); i++)
            {
                Main.allActors.get(i).update(delta, keyEvent, mouseEvent);
            }
        }
    }
    
    @Override
    public void draw (GameContainer gc, Graphics g)
    {
        for (int i = 0; i < actors.size(); i++)
        {
            actors.get(i).draw(g);
        }
    }
    
}

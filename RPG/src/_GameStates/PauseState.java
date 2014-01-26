package _GameStates;

import java.awt.Dimension;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import _Actions.ButtonResumeAction;
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

public class PauseState extends GameState
{
    private Dimension dim;
    private Position  pos;
    private GameState prev;
    private Window    win;
    private boolean   pause;
    
    /**
     * A GameState that shows when the LevelState is paused. It acts as a way to
     * switch between several states.
     * 
     * @param previous
     */
    public PauseState (GameState previous)
    {
        dim = new Dimension(240, 300);
        pos = new Position((int) Main.camera.getPosition().x + Main.screen.width / 2 / Main.scale - dim.width / 2 / Main.scale, (int) Main.camera.getPosition().y + Main.screen.height / 2 / Main.scale
                - dim.height / 2 / Main.scale, 0);
        prev = previous;
        actors = new ArrayList<Actor>();
    }
    
    @Override
    public void init ()
    {
        Dimension startDim = new Dimension(200, 40);
        Position pos = new Position(dim.width / 2 - startDim.width / 2, 0, 0);
        Appearance normal = new RectangleAppearance(new Color(80, 80, 80, 255), startDim);
        Appearance hover = new RectangleAppearance(new Color(150, 150, 150, 255), startDim);
        Appearance clicked = new RectangleAppearance(new Color(30, 30, 30, 255), startDim);
        
        FadeTransition transMenu = new FadeTransition(this, new TitleState(), Color.black, (float) .5);
        OptionsState optionState = new OptionsState(this);
        FadeTransition transExit = new FadeTransition(this, new ExitState(Color.black), Color.black, (float) 1.5);
        ButtonResumeAction action1 = new ButtonResumeAction(new ButtonInput());
        StateSwitchAction action2 = new StateSwitchAction(transMenu, new ButtonInput());
        StatePushAction action3 = new StatePushAction(optionState, new ButtonInput());
        StateSwitchAction action4 = new StateSwitchAction(transExit, new ButtonInput());
        
        win = new Window(dim, pos, new Color(0, 0, 0, 240));
        Text paused = new Text("Paused", win, new Position(win.getDrawBox().width / 2 - 45, 10, 0), true, true);
        win.add(paused);
        Button resume = new Button("Resume", win, startDim, new Position(pos.x, pos.y + startDim.height * 1.5, 0), action1, normal, hover, clicked, 50, 3, true, true);
        win.add(resume);
        Button menu = new Button("Menu", win, startDim, new Position(pos.x, pos.y + startDim.height * 3, 0), action2, normal, hover, clicked, 68, 3, true, true);
        win.add(menu);
        Button options = new Button("Options", win, startDim, new Position(pos.x, pos.y + startDim.height * 4.5, 0), action3, normal, hover, clicked, 55, 3, true, true);
        win.add(options);
        Button exit = new Button("Exit", win, startDim, new Position(pos.x, pos.y + startDim.height * 6, 0), action4, normal, hover, clicked, 75, 3, true, true);
        win.add(exit);
        actors.add(win);
        pause = false;
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
        pause = true;
    }
    
    @Override
    public void resume ()
    {
        pause = false;
        Main.allActors = actors;
    }
    
    @Override
    public void update (KeyEvent keyEvent, MouseEvent mouseEvent, double delta)
    {
        win.setDrawPosition(new Position((int) Main.camera.getPosition().x + Main.screen.width / 2 / Main.scale - dim.width / 2 / Main.scale, (int) Main.camera.getPosition().y + Main.screen.height
                / 2 / Main.scale - dim.height / 2 / Main.scale, 0));
        win.updatePos();
        
        if (pause == false)
        {
            prev.update(keyEvent, mouseEvent, delta);
            for (int i = 0; i < Main.allActors.size(); i++)
            {
                Main.allActors.get(i).update(delta, keyEvent, mouseEvent);
            }
        }
    }
    
    @Override
    public void draw (GameContainer gc, Graphics g)
    {
        prev.draw(gc, g);
        for (int i = 0; i < actors.size(); i++)
        {
            actors.get(i).draw(g);
        }
    }
    
}

package _GameStates;

import java.awt.Dimension;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import _Actions.SetParticlesAction;
import _Actions.SetResolutionAction;
import _Actions.StatePopAction;
import _Actions.StatePopSwitchAction;
import _Actions.ToggleFullscreenAction;
import _Actions.ToggleVSyncAction;
import _Actors.Actor;
import _Actors.Interface.Button;
import _Actors.Interface.CheckButton;
import _Actors.Interface.DropDownMenu;
import _Actors.Interface.Text;
import _Actors.Interface.Window;
import _Appearances.Appearance;
import _Appearances.RectangleAppearance;
import _GameStateManager.GameStateManager;
import _GameStates.Transitions.FadeTransition;
import _Inputs.ButtonInput;
import _Main.Main;
import _Misc.KeyEvent;
import _Misc.MouseEvent;
import _Vectors.Position;

public class OptionsState extends GameState
{
    private Dimension dim;
    private GameState prev;
    private Position  pos;
    private Window    win;
    
    /**
     * This state hold a number of options for the user to change.
     * 
     * @param prev
     */
    public OptionsState (GameState prev)
    {
        this.prev = prev;
    }
    
    @Override
    public void init ()
    {
        prev.pause();
        actors = new ArrayList<Actor>();
        
        dim = new Dimension(720, 300);
        Dimension startDim = new Dimension(120, 50);
        Dimension dropDown = new Dimension(200, 35);
        
        pos = new Position((int) Main.camera.getPosition().x + Main.screen.width / 2 / Main.scale - dim.width / 2 / Main.scale, (int) Main.camera.getPosition().y + Main.screen.height / 2 / Main.scale
                - dim.height / 2 / Main.scale, 0);
        
        Appearance normal1 = new RectangleAppearance(new Color(100, 100, 100, 255), startDim);
        Appearance normal2 = new RectangleAppearance(new Color(80, 80, 80, 255), startDim);
        Appearance hover = new RectangleAppearance(new Color(150, 150, 150, 255), startDim);
        Appearance clicked = new RectangleAppearance(new Color(50, 50, 50, 255), startDim);
        FadeTransition trans = new FadeTransition(this, new TitleState(), Color.black, (float) (.5));
        StatePopAction action1 = new StatePopAction(new ButtonInput());
        StatePopSwitchAction action2 = new StatePopSwitchAction(trans, new ButtonInput(), 1);
        SetParticlesAction action3 = new SetParticlesAction(Main.Setting.None, new ButtonInput());
        SetParticlesAction action4 = new SetParticlesAction(Main.Setting.Low, new ButtonInput());
        SetParticlesAction action5 = new SetParticlesAction(Main.Setting.Medium, new ButtonInput());
        SetParticlesAction action6 = new SetParticlesAction(Main.Setting.High, new ButtonInput());
        
        win = new Window(dim, pos, new Color(0, 0, 0, 245));
        if (prev.getClass().equals(TitleState.class))
        {
            Button back = new Button("Back", win, startDim, new Position(win.getDrawBox().width / 2 - startDim.width / 2, dim.height - startDim.height * 1.5, 0), action1, normal1, hover, clicked, 30,
                    8, true, true);
            win.add(back);
        }
        else
        {
            Button back = new Button("Back", win, startDim, new Position(win.getDrawBox().width / 2 - startDim.width - 10, dim.height - startDim.height * 1.5, 0), action1, normal1, hover, clicked,
                    30, 8, true, true);
            Button menu = new Button("Menu", win, startDim, new Position(win.getDrawBox().width / 2 + 10, dim.height - startDim.height * 1.5, 0), action2, normal1, hover, clicked, 28, 8, true, true);
            win.add(back);
            win.add(menu);
        }
        Text options = new Text("Options", win, new Position(0, 20, 0), true, true);
        win.add(options);
        
        ArrayList<Button> res = new ArrayList<Button>();
        for (int i = 0; i < GameStateManager.displays.size(); i++)
        {
            Dimension dim = GameStateManager.displays.get(i);
            SetResolutionAction action = new SetResolutionAction(dim, new ButtonInput());
            res.add(new Button((dim.width + " x " + dim.height), win, dropDown, new Position(0, 0, 0), action, normal2, hover, clicked, 0, 5, false, true));
        }
        
        Text resolution = new Text("Resolution", win, new Position(103, 70, 0), false, false);
        win.add(resolution);
        
        Dimension currentRes = new Dimension(Main.gsm.gc.getWidth(), Main.gsm.gc.getHeight());
        DropDownMenu resolutions = new DropDownMenu(currentRes.width + " x " + currentRes.height, win, res, dropDown, new Position(50, 100, 0), normal1, hover, clicked, 5, false);
        win.add(resolutions);
        
        ArrayList<Button> parts = new ArrayList<Button>();
        
        Button particles1 = new Button("High", win, dropDown, new Position(0, 0, 0), action6, normal2, hover, clicked, 0, 8, false, true);
        Button particles2 = new Button("Medium", win, dropDown, new Position(0, 0, 0), action5, normal2, hover, clicked, 0, 8, false, true);
        Button particles3 = new Button("Low", win, dropDown, new Position(0, 0, 0), action4, normal2, hover, clicked, 0, 8, false, true);
        Button particles4 = new Button("None", win, dropDown, new Position(0, 0, 0), action3, normal2, hover, clicked, 0, 8, false, true);
        parts.add(particles1);
        parts.add(particles2);
        parts.add(particles3);
        parts.add(particles4);
        
        DropDownMenu particles = new DropDownMenu(Main.particleSetting.toString(), win, parts, dropDown, new Position(win.getDrawBox().width - dropDown.width - 50, 100, 0), normal1, hover, clicked,
                5, false);
        win.add(particles);
        
        Text particleText = new Text("Particles", win, new Position(win.getDrawBox().width - dropDown.width + 13, 70, 0), false, false);
        win.add(particleText);
        
        ToggleFullscreenAction setFullscreen = new ToggleFullscreenAction(new ButtonInput());
        CheckButton fullscreen = new CheckButton("Fullscreen", win, new Dimension(35, 35), new Position(win.getDrawBox().width / 2 - 65, 100, 0), setFullscreen, normal1, hover, clicked, 40, 5, false,
                Main.gsm.gc.isFullscreen());
        win.add(fullscreen);
        
        ToggleVSyncAction setVSync = new ToggleVSyncAction(new ButtonInput());
        CheckButton vSync = new CheckButton("V-Sync", win, new Dimension(35, 35), new Position(win.getDrawBox().width / 2 - 65, 145, 0), setVSync, normal1, hover, clicked, 40, 5, false,
                Main.gsm.gc.isVSyncRequested());
        win.add(vSync);
        
        actors.add(win);
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
        prev.update(keyEvent, mouseEvent, delta);
        
        if (paused == false)
        {
            for (int i = 0; i < Main.allActors.size(); i++)
            {
                Main.allActors.get(i).update(delta, keyEvent, mouseEvent);
            }
        }
        
        pos = new Position((int) Main.camera.getPosition().x + Main.screen.width / 2 / Main.scale - dim.width / 2 / Main.scale, (int) Main.camera.getPosition().y + Main.screen.height / 2 / Main.scale
                - dim.height / 2 / Main.scale, 0);
        win.setDrawPosition(pos);
    }
    
    @Override
    public void draw (GameContainer gc, Graphics g)
    {
        prev.draw(gc, g);
        g.setColor(new Color(0, 0, 0, 245));
        for (int i = 0; i < actors.size(); i++)
        {
            actors.get(i).draw(g);
        }
    }
}

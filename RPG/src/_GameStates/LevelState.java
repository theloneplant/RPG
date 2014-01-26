package _GameStates;

import java.awt.Dimension;
import java.util.ArrayList;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import _Actions.Action;
import _Actions.AimAction;
import _Actions.MoveAction;
import _Actions.MoveSquadToLocation;
import _Actions.PauseAction;
import _Actions.SelectAction;
import _Actions.ShootAction;
import _Actions.SwitchControlMode;
import _Actions.ZoomAction;
import _Actors.Actor;
import _Actors.AimLine;
import _Actors.Camera;
import _Actors.Campfire;
import _Actors.Grass;
import _Actors.PC;
import _Actors.RoamingNPC;
import _Actors.Tree;
import _Actors.Emitters.FireEmitter;
import _Actors.Emitters.SparkField;
import _Actors.Interface.Text;
import _Actors.Interface.Window;
import _Actors.Squad.SelectionBox;
import _Actors.Squad.SquadMember;
import _Appearances.ImageAppearance;
import _Inputs.KeyInput;
import _Inputs.MouseInput;
import _Main.Main;
import _Misc.KeyEvent;
import _Misc.MouseEvent;
import _Vectors.Position;
import _Vectors.Velocity;

public class LevelState extends GameState
{
    private double            delta;
    private Image             background;
    public PC                 user;
    private double            originalHealth;
    private ArrayList<Action> actions;
    private Text              commandText;
    private Window            commandWin;
    
    /**
     * The level state is the level of the game itself.
     * 
     * This state will be universal and be able to load in levels from an XML
     * file in the future.
     */
    @Override
    public void init ()
    {
        actions = new ArrayList<Action>();
        
        try
        {
            background = new Image("images/Backgrounds/grass.png");
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }
        
        Tree tree1 = new Tree(new Position(300, 300, 12));
        Tree tree2 = new Tree(new Position(400, 500, 12));
        Tree tree3 = new Tree(new Position(800, 200, 12));
        Tree tree4 = new Tree(new Position(700, 450, 12));
        actors.add(tree1);
        actors.add(tree2);
        actors.add(tree3);
        actors.add(tree4);
        
        Grass grass1 = new Grass(new Position(1500, 300, 0));
        Grass grass2 = new Grass(new Position(1515, 300, 0));
        Grass grass3 = new Grass(new Position(1530, 300, 0));
        Grass grass4 = new Grass(new Position(1545, 300, 0));
        Grass grass5 = new Grass(new Position(1560, 300, 0));
        actors.add(grass1);
        actors.add(grass2);
        actors.add(grass3);
        actors.add(grass4);
        actors.add(grass5);
        
        Campfire campfire = new Campfire(new Position(1200, 700, 0));
        actors.add(campfire);
        
        FireEmitter emit = new FireEmitter(campfire, 0, 15, 0);
        actors.add(emit);
        
        SparkField field = new SparkField(new Position(1700, 700, 0), new Dimension(300, 300));
        actors.add(field);
        
        String name = "Mr Squad";
        
        SquadMember squad1 = new SquadMember(name);
        squad1.setDrawPosition(new Position((Main.dim.width / 2) + 150, (Main.dim.height / 2), 0));
        actors.add(squad1);
        
        SquadMember squad2 = new SquadMember(name);
        squad2.setDrawPosition(new Position((Main.dim.width / 2) + 300, (Main.dim.height / 2), 0));
        actors.add(squad2);
        
        SquadMember squad3 = new SquadMember(name);
        squad3.setDrawPosition(new Position((Main.dim.width / 2) + 150, (Main.dim.height / 2) + 150, 0));
        actors.add(squad3);
        
        SquadMember squad4 = new SquadMember(name);
        squad4.setDrawPosition(new Position((Main.dim.width / 2) + 300, (Main.dim.height / 2) + 150, 0));
        actors.add(squad4);
        
        int numberofNPCs = 30;
        int count = 0;
        boolean intersects = true;
        
        while (count < numberofNPCs)
        {
            RoamingNPC guy = new RoamingNPC();
            guy.setDrawPosition(new Position((int) (Math.random() * (Main.dim.width)), (int) (Math.random() * Main.dim.height), 0));
            
            while (intersects == true)
            {
                for (int a = actors.size() - 1; a > 0; a--)
                {
                    if (guy.intersects(actors.get(a)) || guy.getDrawBox().x > Main.dim.width - guy.getDrawBox().getWidth() || guy.getDrawBox().y > Main.dim.height - guy.getDrawBox().getHeight())
                    {
                        guy.setDrawPosition(new Position((int) (Math.random() * (Main.dim.width)), (int) (Math.random() * Main.dim.height), 0));
                        intersects = true;
                    }
                    
                    else
                    {
                        intersects = false;
                    }
                }
            }
            actors.add(guy);
            count++;
        }
        
        SelectionBox box = new SelectionBox();
        actors.add(box);
        
        user = new PC("User", box);
        user.setDrawPosition(new Position((Main.dim.width / 2), (Main.dim.height / 2), 0));
        user.addSquadMember(squad1);
        user.addSquadMember(squad2);
        user.addSquadMember(squad3);
        user.addSquadMember(squad4);
        originalHealth = user.getHealth();
        Main.camera = new Camera(user, Main.screen);
        
        Actor arrow = new Actor("Arrow");
        arrow.setDrawPosition(new Position(Main.dim.width / 2, Main.dim.height / 2, 0));
        arrow.setCollides(false);
        arrow.setVisible(false);
        try
        {
            Image img = new Image("images/GUI/moveIcon.png");
            arrow.setAppearance(new ImageAppearance(img, null, 0));
            arrow.setDrawDimension(new Dimension(img.getWidth(), img.getHeight()));
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }
        actors.add(arrow);
        
        AimLine aim = new AimLine(user);
        user.addAction(new MoveAction(new KeyInput(Input.KEY_W, KeyEvent.KeyEventType.down), new KeyInput(Input.KEY_W, KeyEvent.KeyEventType.up), new Velocity(0, -(int) user.getSpeed(), 0)));
        user.addAction(new MoveAction(new KeyInput(Input.KEY_S, KeyEvent.KeyEventType.down), new KeyInput(Input.KEY_S, KeyEvent.KeyEventType.up), new Velocity(0, (int) user.getSpeed(), 0)));
        user.addAction(new MoveAction(new KeyInput(Input.KEY_A, KeyEvent.KeyEventType.down), new KeyInput(Input.KEY_A, KeyEvent.KeyEventType.up), new Velocity(-(int) user.getSpeed(), 0, 0)));
        user.addAction(new MoveAction(new KeyInput(Input.KEY_D, KeyEvent.KeyEventType.down), new KeyInput(Input.KEY_D, KeyEvent.KeyEventType.up), new Velocity((int) user.getSpeed(), 0, 0)));
        user.addAction(new ZoomAction(new MouseInput(Input.MOUSE_MIDDLE_BUTTON, MouseEvent.MouseEventType.wheelMoved)));
        user.addAction(new SwitchControlMode(user, new KeyInput(Input.KEY_LSHIFT, KeyEvent.KeyEventType.down)));
        user.addCombat(new ShootAction(new MouseInput(Input.MOUSE_LEFT_BUTTON, MouseEvent.MouseEventType.pressed), Input.MOUSE_LEFT_BUTTON));
        user.addCombat(new AimAction(aim, new MouseInput(Input.MOUSE_RIGHT_BUTTON, MouseEvent.MouseEventType.pressed), new MouseInput(Input.MOUSE_RIGHT_BUTTON, MouseEvent.MouseEventType.released),
                Input.MOUSE_RIGHT_BUTTON, Color.white));
        user.addCommand(new SelectAction(user, new MouseInput(Input.MOUSE_LEFT_BUTTON, MouseEvent.MouseEventType.pressed), new MouseInput(Input.MOUSE_LEFT_BUTTON, MouseEvent.MouseEventType.released)));
        user.addCommand(new MoveSquadToLocation(user, arrow, new MouseInput(Input.MOUSE_RIGHT_BUTTON, MouseEvent.MouseEventType.pressed)));
        actors.add(user);
        actors.add(aim);
        
        actions.add(new PauseAction(new KeyInput(Input.KEY_ESCAPE, KeyEvent.KeyEventType.down), Input.KEY_ESCAPE));
        
        commandWin = new Window(new Dimension(0, 0), new Position(0, 0, 0), new Color(0, 0, 0, 150));
        commandText = new Text("Commanding", commandWin, new Position(0, 3, 0), true, true);
        commandWin.setDrawDimension(new Dimension(commandText.getWidth() + 6, commandText.getHeight()));
        commandWin.add(commandText);
        
        Main.camera.update(user);
        paused = false;
        Main.allActors = actors;
        Main.universal = actions;
    }
    
    @Override
    public void cleanUp ()
    {
        actors = Main.allActors;
        actions = Main.universal;
        Main.allActors.clear();
        Main.universal.clear();
        Main.camera.reset();
    }
    
    @Override
    public void pause ()
    {
        paused = true;
    }
    
    @Override
    public void resume ()
    {
        Main.allActors = actors;
        Main.universal = actions;
        paused = false;
    }
    
    @Override
    public void update (KeyEvent keyEvent, MouseEvent mouseEvent, double delta)
    {
        this.delta = delta;
        
        for (int idx = 0; idx < Main.universal.size(); idx++)
        {
            Main.universal.get(idx).update(null, delta, keyEvent, mouseEvent);
        }
        
        if (paused == false)
        {
            for (int idx = 0; idx < Main.allActors.size(); idx++)
            {
                if (Main.allActors.get(idx).getAlive() == true)
                    Main.allActors.get(idx).update(delta, keyEvent, mouseEvent);
                else
                    Main.allActors.remove(idx);
            }
            
            if (!Display.isActive())
            {
                Main.gsm.pause();
            }
            
            Main.userPosition.setPosition(user.getPosition().x, user.getPosition().y, user.getPosition().z);
        }
        Main.camera.update(user);
    }
    
    @Override
    public void draw (GameContainer gc, Graphics g)
    {
        GL11.glScaled(Main.scale, Main.scale, 1);
        GL11.glTranslated((float) -Main.camera.getDrawBox().x, (float) -Main.camera.getDrawBox().y, 0);
        
        int TileWidth = background.getWidth();
        int TileHeight = background.getHeight();
        int NumberX = (int) (Main.dim.width / TileWidth);
        int NumberY = (int) (Main.dim.height / TileHeight);
        
        for (int i = 0; i <= NumberY + TileHeight; i++)
        {
            for (int j = 0; j <= NumberX; j++)
            {
                g.drawImage(background, j * TileWidth, i * TileHeight);
            }
        }
        
        for (int i = 0; i <= NumberY + TileHeight; i++)
        {
            for (int j = 0; j <= NumberX; j++)
            {
                g.drawImage(background, j * TileWidth - TileWidth / 2, i * TileHeight - TileHeight / 2);
            }
        }
        
        g.setColor(Color.black);
        
        for (int idx = 0; idx <= actors.size() - 1; idx++)
        {
            if (actors.get(idx).intersectsDraw(Main.camera))
                actors.get(idx).draw(g);
        }
        
        g.scale(1 / Main.scale, 1 / Main.scale);
        
        // =======================GUI=======================
        
        commandWin.setScaledPosition(new Position(Main.camera.getDrawBox().x * Main.scale + Main.camera.getDrawBox().width / 2 * Main.scale - commandWin.getDrawBox().width / 2, Main.camera
                .getDrawBox().y * Main.scale, 0));
        if (user.isCommanding())
        {
            commandText.setText("Commanding");
        }
        else
        {
            commandText.setText("Attacking");
        }
        commandWin.setDrawDimension(new Dimension(commandText.getWidth() + 10, commandText.getHeight() + 6));
        commandText.updatePos();
        commandWin.draw(g);
        commandText.draw(g);
        
        double health = user.getHealth();
        double cooldown = user.getShotCooldown();
        
        int barWidth = 150;
        int barHeight = 10;
        int barGap = 5;
        int barIndent = 50;
        double scaledWidth = (health / originalHealth) * barWidth;
        
        Color frameColor = new Color(0, 0, 0, 150);
        g.setColor(frameColor);
        g.fillRect((int) Main.camera.getDrawBox().x * Main.scale, (int) Main.camera.getDrawBox().y * Main.scale, barWidth + barGap * 2 + barIndent, barHeight * 2 + barGap * 3);
        
        if (!user.getAlive())
        {
            health = 0;
            scaledWidth = 0;
        }
        
        g.setColor(Color.darkGray);
        g.fillRect((int) Main.camera.getDrawBox().x * Main.scale + barGap + barIndent, (int) Main.camera.getDrawBox().y * Main.scale + barGap, barWidth, barHeight);
        g.setColor(Color.red);
        g.fillRect((int) Main.camera.getDrawBox().x * Main.scale + barGap + barIndent, (int) Main.camera.getDrawBox().y * Main.scale + barGap, (int) (scaledWidth), barHeight);
        g.setColor(Color.white);
        Main.getSmallFont().drawString((int) Main.camera.getDrawBox().x * Main.scale + barGap - 2, (int) Main.camera.getDrawBox().y * Main.scale + barGap / 2, "HP - " + (int) health);
        
        double scaledShotWidth = (user.getShotCooldown() / user.getMaxCooldown()) * barWidth;
        if (!user.getAlive())
        {
            cooldown = 0;
            scaledShotWidth = 0;
        }
        
        g.setColor(Color.darkGray);
        g.fillRect((int) Main.camera.getDrawBox().x * Main.scale + barGap + barIndent, (int) Main.camera.getDrawBox().y * Main.scale + barGap * 2 + barHeight, barWidth, barHeight);
        g.setColor(Color.yellow);
        g.fillRect((int) Main.camera.getDrawBox().x * Main.scale + barGap + barIndent, (int) Main.camera.getDrawBox().y * Main.scale + barGap * 2 + barHeight, (int) (scaledShotWidth), barHeight);
        g.setColor(Color.white);
        Main.getSmallFont().drawString((int) Main.camera.getDrawBox().x * Main.scale + barGap - 2, (int) Main.camera.getDrawBox().y * Main.scale + barHeight + barGap, "CD - " + (int) cooldown);
        
        checkWin(g);
        
        // ============FPS Counter=============
        
        g.setColor(frameColor);
        g.fillRect((float) Main.camera.getDrawBox().x * Main.scale, (float) (Main.camera.getDrawBox().y * Main.scale + Main.camera.getDrawBox().height * Main.scale - 30 - barHeight * 2),
                (float) barGap * 2 + 80, (float) barHeight * 4 + barGap * 3);
        
        Main.getSmallFont().drawString((int) Main.camera.getDrawBox().x * Main.scale + barGap + 5,
                (int) Main.camera.getDrawBox().y * Main.scale + (int) Main.camera.getDrawBox().height * Main.scale - 23, "Actors - " + Main.allActors.size());
        Main.getSmallFont().drawString((int) Main.camera.getDrawBox().x * Main.scale + barGap + 5,
                (int) Main.camera.getDrawBox().y * Main.scale + (int) Main.camera.getDrawBox().height * Main.scale - 43, "FPS - " + gc.getFPS());
    }
    
    /**
     * Checks if the player and/or NPCs are dead. It will display a message
     * appropriately.
     * 
     * @param g
     */
    private void checkWin (Graphics g)
    {
        boolean npcCheck = false;
        boolean checkAlive = false;
        
        for (int i = 0; i < actors.size(); i++)
        {
            if (actors.get(i).getClass() == RoamingNPC.class)
            {
                npcCheck = true;
            }
            
            if (actors.get(i).getClass() == PC.class)
            {
                checkAlive = true;
            }
        }
        
        if (npcCheck == false && checkAlive == true)
            Main.getTitleFont().drawString((int) (Main.screen.width / 2 - 235 + Main.camera.getDrawBox().x * Main.scale), (int) (Main.screen.height / 3 + Main.camera.getDrawBox().y * Main.scale),
                    "You have destroyed everybody, satisfied?");
        else if (npcCheck == true && checkAlive == false)
        {
            Main.getTitleFont().drawString((int) (Main.screen.width / 2 - 285 + Main.camera.getDrawBox().x * Main.scale), (int) (Main.screen.height / 3 + Main.camera.getDrawBox().y * Main.scale),
                    "Looks like someone got killed by harmless NPC's");
        }
        else if (npcCheck == false && checkAlive == false)
        {
            Main.getTitleFont().drawString((int) (Main.screen.width / 2 - 190 + Main.camera.getDrawBox().x * Main.scale), (int) (Main.screen.height / 3 + Main.camera.getDrawBox().y * Main.scale),
                    "Nevermind... everyone exploded.");
        }
    }
}

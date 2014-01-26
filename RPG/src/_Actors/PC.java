package _Actors;

import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import _Actions.Action;
import _Actors.Squad.SelectionBox;
import _Actors.Squad.Squad;
import _Actors.Squad.SquadMember;
import _Appearances.ImageAppearance;
import _Main.Main;
import _Misc.KeyEvent;
import _Misc.MouseEvent;
import _Vectors.Acceleration;
import _Vectors.Position;
import _Vectors.Velocity;

public class PC extends Actor
{
    private double            maxCooldown;
    private double            shotCooldown;
    private ArrayList<Action> command, combat;
    private Squad             squad;
    private SelectionBox      selection;
    private boolean           isCommanding;
    private ImageAppearance[] down  = new ImageAppearance[4];
    private ImageAppearance[] up    = new ImageAppearance[4];
    private ImageAppearance[] left  = new ImageAppearance[4];
    private ImageAppearance[] right = new ImageAppearance[4];
    private ImageAppearance[] direction;
    private double            lastFrameChange;
    private double            frameDuration;
    private int               frameNumber;
    
    /**
     * This actor represents the player's character in the game. This class
     * holds a player's squad as well as extra action lists that correspond to
     * the input mode (command or combat) that the user is in.
     * 
     * @param name
     * @param box
     */
    public PC (String name, SelectionBox box)
    {
        super(name);
        setHealth(100);
        setSpeed(200);
        setDamage(30);
        setAlive(true);
        setDrawDimension(new Dimension(27, 32));
        setHitBoxDimension(new Dimension(23, 22));
        setHitBoxOffset(new Dimension(2, 10));
        setCollides(true);
        setScale(1);
        
        command = new ArrayList<Action>();
        combat = new ArrayList<Action>();
        isCommanding = true;
        squad = new Squad(this);
        selection = box;
        
        setVelocity(new Velocity(0, 0, 0));
        setAcceleration(new Acceleration(0, 0, 0));
        
        try
        {
            down[0] = new ImageAppearance(new Image("images/Characters/User/UserDown1.gif"), null, 0);
            down[1] = new ImageAppearance(new Image("images/Characters/User/UserDown2.gif"), null, 0);
            down[2] = new ImageAppearance(new Image("images/Characters/User/UserDown1.gif"), null, 0);
            down[3] = new ImageAppearance(new Image("images/Characters/User/UserDown3.gif"), null, 0);
            
            up[0] = new ImageAppearance(new Image("images/Characters/User/UserUp1.gif"), null, 0);
            up[1] = new ImageAppearance(new Image("images/Characters/User/UserUp2.gif"), null, 0);
            up[2] = new ImageAppearance(new Image("images/Characters/User/UserUp1.gif"), null, 0);
            up[3] = new ImageAppearance(new Image("images/Characters/User/UserUp3.gif"), null, 0);
            
            left[0] = new ImageAppearance(new Image("images/Characters/User/UserLeft1.gif"), null, 0);
            left[1] = new ImageAppearance(new Image("images/Characters/User/UserLeft2.gif"), null, 0);
            left[2] = new ImageAppearance(new Image("images/Characters/User/UserLeft1.gif"), null, 0);
            left[3] = new ImageAppearance(new Image("images/Characters/User/UserLeft3.gif"), null, 0);
            
            right[0] = new ImageAppearance(new Image("images/Characters/User/UserRight1.gif"), null, 0);
            right[1] = new ImageAppearance(new Image("images/Characters/User/UserRight2.gif"), null, 0);
            right[2] = new ImageAppearance(new Image("images/Characters/User/UserRight1.gif"), null, 0);
            right[3] = new ImageAppearance(new Image("images/Characters/User/UserRight3.gif"), null, 0);
            
            setAppearance(new ImageAppearance(new Image("images/Characters/User/UserDown1.gif"), null, 0));
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }
        
        direction = down;
        
        frameDuration = 1 / getSpeed() * getScale() * 15;
        
        maxCooldown = 10;
        shotCooldown = 0;
    }
    
    public void update (double delta, KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        Position prevPosition = new Position(getPosition().x, getPosition().y, getPosition().z);
        super.update(delta, keyEvent, mouseEvent);
        Position newPosition = new Position(getPosition().x, getPosition().y, getPosition().z);
        
        if (isCommanding)
        {
            for (int i = 0; i < command.size(); i++)
            {
                command.get(i).update(this, delta, keyEvent, mouseEvent);
            }
            for (int i = 0; i < combat.size(); i++)
            {
                combat.get(i).update(this, delta, null, null);
            }
        }
        else
        {
            for (int i = 0; i < combat.size(); i++)
            {
                combat.get(i).update(this, delta, keyEvent, mouseEvent);
            }
            for (int i = 0; i < command.size(); i++)
            {
                command.get(i).update(this, delta, null, null);
            }
        }
        
        squad.update(delta, keyEvent, mouseEvent);
        selection.update(delta, keyEvent, mouseEvent);
        
        if (FindIntersectingActors().size() > 0)
        {
            Position xMoveOnlyPosition = new Position(newPosition.x, prevPosition.y, prevPosition.z);
            setDrawPosition(xMoveOnlyPosition);
            
            if (FindIntersectingActors().size() > 0)
            {
                Position yMoveOnlyPosition = new Position(prevPosition.x, newPosition.y, prevPosition.z);
                setDrawPosition(yMoveOnlyPosition);
                
                if (FindIntersectingActors().size() > 0)
                    setDrawPosition(prevPosition);
            }
        }
        
        animationDirection(delta);
        inScreen();
        
        if (shotCooldown < maxCooldown)
        {
            shotCooldown += (60 * delta);
            if (shotCooldown > 10)
                shotCooldown = 10;
        }
        
        if (shotCooldown <= 0)
            shotCooldown = 0;
    }
    
    public void draw (Graphics g)
    {
        super.draw(g);
        squad.draw(g);
        selection.draw(g);
    }
    
    /**
     * Adds a squad member to the player's squad
     * 
     * @param member
     */
    public void addSquadMember (SquadMember member)
    {
        squad.addMember(member);
    }
    
    /**
     * Removes the squad member with a given name
     * 
     * When the game evolves more, these names will always be different
     * 
     * @param name
     */
    public void removeSquadMember (String name)
    {
        squad.removeMember(name);
    }
    
    public Squad getSquad ()
    {
        return squad;
    }
    
    /**
     * Method that returns all actors that the player intersects with.
     * 
     * This code will be replaced by a collision engine in the future.
     * 
     * @return
     */
    private ArrayList<Actor> FindIntersectingActors ()
    {
        Rectangle2D hitBox = getHitBox();
        ArrayList<Actor> actors = new ArrayList<Actor>();
        
        for (int i = 0; i < Main.allActors.size(); i++)
        {
            Actor a = Main.allActors.get(i);
            if (!(a instanceof Projectile) && a.collides() && a != this)
            {
                if (hitBox.intersects(a.getHitBox()))
                    actors.add(a);
            }
        }
        return actors;
    }
    
    /**
     * Chooses which animation loop to play for the player depending on
     * velocity.
     * 
     * This is temporary code and will be replaced by an animation engine in the
     * future.
     * 
     * @param gameTime
     */
    public void animationDirection (double gameTime)
    {
        lastFrameChange += gameTime;
        
        double deltaX = getVelocity().x;
        double deltaY = getVelocity().y;
        boolean xNegative;
        boolean yNegative;
        
        if (deltaX < 0)
            xNegative = true;
        else
            xNegative = false;
        if (deltaY < 0)
            yNegative = true;
        else
            yNegative = false;
        
        deltaX = Math.abs(deltaX);
        deltaY = Math.abs(deltaY);
        
        if (deltaX == 0 && deltaY == 0)
        {
            setAppearance(direction[0]);
        }
        
        else if (deltaX > deltaY)
        {
            if (xNegative)
            {
                if (lastFrameChange > frameDuration)
                {
                    lastFrameChange = 0;
                    
                    frameNumber++;
                    if (frameNumber >= left.length)
                    {
                        frameNumber = 0;
                    }
                    direction = left;
                    setAppearance(left[frameNumber]);
                }
            }
            else if (!xNegative)
            {
                if (lastFrameChange > frameDuration)
                {
                    lastFrameChange = 0;
                    
                    frameNumber++;
                    if (frameNumber >= right.length)
                    {
                        frameNumber = 0;
                    }
                    direction = right;
                    setAppearance(right[frameNumber]);
                }
            }
        }
        else if (deltaY >= deltaX)
        {
            if (yNegative)
            {
                if (lastFrameChange > frameDuration)
                {
                    lastFrameChange = 0;
                    
                    frameNumber++;
                    if (frameNumber >= up.length)
                    {
                        frameNumber = 0;
                    }
                    direction = up;
                    setAppearance(up[frameNumber]);
                }
            }
            else if (!yNegative)
            {
                if (lastFrameChange > frameDuration)
                {
                    lastFrameChange = 0;
                    
                    frameNumber++;
                    if (frameNumber >= down.length)
                    {
                        frameNumber = 0;
                    }
                    direction = down;
                    setAppearance(down[frameNumber]);
                }
            }
        }
    }
    
    /**
     * Searches the array for an actor with a given actor type and returns it
     * 
     * This may be deprecated in the future
     * 
     * @param c
     * @return
     */
    public static Actor findActorType (Class<AimLine> c)
    {
        for (int i = 0; i < Main.allActors.size(); i++)
        {
            Actor actor = Main.allActors.get(i);
            if (actor.getClass() == c)
            {
                return actor;
            }
        }
        return null;
    }
    
    // Getters and setters
    
    public void setShotCooldown (double newShot)
    {
        shotCooldown = (int) newShot;
    }
    
    public void setAlive (boolean newState)
    {
        super.setAlive(newState);
        if (newState == false)
        {
            Actor aim = findActorType(AimLine.class);
            if (aim != null)
                aim.setAlive(false);
        }
    }
    
    public double getShotCooldown ()
    {
        return shotCooldown;
    }
    
    public double getMaxCooldown ()
    {
        return maxCooldown;
    }
    
    public Position getCenter ()
    {
        return new Position(getPosition().x + getDrawBox().width / 2, getPosition().y + getDrawBox().width / 2, getPosition().z);
    }
    
    public boolean isCommanding ()
    {
        return isCommanding;
    }
    
    public void setCommanding (boolean commanding)
    {
        isCommanding = commanding;
    }
    
    public void addCombat (Action combatAction)
    {
        combat.add(combatAction);
    }
    
    public void addCommand (Action commandAction)
    {
        command.add(commandAction);
    }
    
    public ArrayList<Action> getCommand ()
    {
        return command;
    }
    
    public ArrayList<Action> getCombat ()
    {
        return combat;
    }
    
    public SelectionBox getSelection ()
    {
        return selection;
    }
}
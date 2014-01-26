package _Actors;

import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import _Appearances.ImageAppearance;
import _Main.Main;
import _Misc.AStarNode;
import _Misc.KeyEvent;
import _Misc.MouseEvent;
import _Vectors.Position;

public class NPC extends Actor
{
    private ImageAppearance[] down  = new ImageAppearance[4];
    private ImageAppearance[] up    = new ImageAppearance[4];
    private ImageAppearance[] left  = new ImageAppearance[4];
    private ImageAppearance[] right = new ImageAppearance[4];
    private ImageAppearance[] direction;
    private double            lastFrameChange;
    protected double          frameDuration;
    private int               frameNumber;
    
    Position                  dest;
    
    ArrayList<AStarNode>      nodes;
    
    /**
     * This is an actor class that handles universal AI for non-player
     * characters (NPC). Actors inheriting from NPC will be characters in the
     * game that the player can interact with.
     */
    public NPC ()
    {
        super("NPC");
        
        nodes = new ArrayList<AStarNode>();
        
        setHealth(30);
        setDamage(10);
        setDrawDimension(new Dimension(27, 32));
        setDrawPosition(new Position(0, 0, 0));
        setHitBoxDimension(new Dimension(27, 20));
        setHitBoxOffset(new Dimension(0, 12));
        setAlive(true);
        setCollides(true);
        setScale(1);
        setSpeed(100);
        
        int max = 2;
        int npcVersion = (int) (Math.round((max - 1.0) * Math.random()) + 1);
        
        try
        {
            Image down1 = new Image("images/Characters/Citizen/" + npcVersion + "CitizenDown1.gif");
            Image down2 = new Image("images/Characters/Citizen/" + npcVersion + "CitizenDown2.gif");
            Image down3 = new Image("images/Characters/Citizen/" + npcVersion + "CitizenDown3.gif");
            Image up1 = new Image("images/Characters/Citizen/" + npcVersion + "CitizenUp1.gif");
            Image up2 = new Image("images/Characters/Citizen/" + npcVersion + "CitizenUp2.gif");
            Image up3 = new Image("images/Characters/Citizen/" + npcVersion + "CitizenUp3.gif");
            Image left1 = new Image("images/Characters/Citizen/" + npcVersion + "CitizenLeft1.gif");
            Image left2 = new Image("images/Characters/Citizen/" + npcVersion + "CitizenLeft2.gif");
            Image left3 = new Image("images/Characters/Citizen/" + npcVersion + "CitizenLeft3.gif");
            Image right1 = new Image("images/Characters/Citizen/" + npcVersion + "CitizenRight1.gif");
            Image right2 = new Image("images/Characters/Citizen/" + npcVersion + "CitizenRight2.gif");
            Image right3 = new Image("images/Characters/Citizen/" + npcVersion + "CitizenRight3.gif");
            
            down[0] = new ImageAppearance(down1, null, 0);
            down[1] = new ImageAppearance(down2, null, 0);
            down[2] = new ImageAppearance(down1, null, 0);
            down[3] = new ImageAppearance(down3, null, 0);
            
            up[0] = new ImageAppearance(up1, null, 0);
            up[1] = new ImageAppearance(up2, null, 0);
            up[2] = new ImageAppearance(up1, null, 0);
            up[3] = new ImageAppearance(up3, null, 0);
            
            left[0] = new ImageAppearance(left1, null, 0);
            left[1] = new ImageAppearance(left2, null, 0);
            left[2] = new ImageAppearance(left1, null, 0);
            left[3] = new ImageAppearance(left3, null, 0);
            
            right[0] = new ImageAppearance(right1, null, 0);
            right[1] = new ImageAppearance(right2, null, 0);
            right[2] = new ImageAppearance(right1, null, 0);
            right[3] = new ImageAppearance(right3, null, 0);
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }
        
        setAppearance(down[0]);
        direction = down;
        
        setDrawDimension(new Dimension(27, 32));
        setHitBoxDimension(new Dimension(23, 22));
        setHitBoxOffset(new Dimension(2, 10));
        frameDuration = .1;
    }
    
    public void update (double delta, KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        Position prevPosition = new Position(getPosition().x, getPosition().y, getPosition().z);
        super.update(delta, keyEvent, mouseEvent);
        Position newPosition = new Position(getPosition().x, getPosition().y, getPosition().z);
        
        animationDirection(delta);
        
        ArrayList<Actor> intersectionActor = FindIntersectingActor(newPosition);
        
        if (intersectionActor != null)
        {
            for (int i = 0; i < intersectionActor.size(); i++)
            {
                if (intersectionActor.get(i).getClass() != Projectile.class && intersectionActor.get(i).collides())
                {
                    Position xMoveOnlyPosition = new Position(newPosition.x, prevPosition.y, prevPosition.z);
                    
                    if (FindIntersectingActor(xMoveOnlyPosition) != null)
                    {
                        Position yMoveOnlyPosition = new Position(prevPosition.x, newPosition.y, prevPosition.z);
                        
                        if (FindIntersectingActor(yMoveOnlyPosition) != null)
                            this.setDrawPosition(prevPosition);
                        else
                        {
                            this.setDrawPosition(yMoveOnlyPosition);
                        }
                    }
                    else
                    {
                        this.setDrawPosition(xMoveOnlyPosition);
                    }
                }
            }
        }
        inScreen();
    }
    
    /**
     * Universal draw method - uncomment the following lines of code to draw
     * rectangles around NPC's when they use pathfinding.
     */
    public void draw (Graphics g)
    {
        for (int i = 0; i < nodes.size(); i++)
        {
            if (nodes.get(i) != null)
            {
                // g.setColor(Color.black);
                // g.drawRect((float) nodes.get(i).getPosition().x, (float)
                // nodes.get(i).getPosition().y,
                // nodes.get(i).getDimension().width,
                // nodes.get(i).getDimension().height);
            }
        }
        if (dest != null)
        {
            // g.setColor(new Color(30, 255, 30, 255));
            // g.drawRect((float) dest.x, (float) dest.y, (float)
            // (getHitBox().width), (float) (getHitBox().height));
        }
        super.draw(g);
    }
    
    /**
     * Clears the AStarNodes in this NPC
     */
    public void clearNodes ()
    {
        nodes.clear();
    }
    
    /**
     * Calculates the most appropriate position for this NPC to travel to next
     * with a given destination.
     * 
     * This implements a modified AStar algorithm which generates nodes off of a
     * grid by using the NPC's hitbox for adjacent nodes. The grid is generated
     * on the fly every time the NPC tries to move to a new location, while also
     * leaving a trail of nodes where it had already traveled to. The previously
     * traveled nodes cannot be walked by the NPC which prevents it from getting
     * stuck at walls.
     * 
     * @param destination
     * @return
     */
    public Position findNextPath (Position destination)
    {
        for (int i = 0; i < nodes.size(); i++)
        {
            if (!nodes.get(i).isTraveled())
            {
                nodes.remove(i);
            }
        }
        
        getAdjacentNodes(new Rectangle2D.Double(getHitBox().x, getHitBox().y, getHitBox().width, getHitBox().height), destination);
        
        for (int i = 0; i < nodes.size(); i++)
        {
            for (int j = 0; j < Main.allActors.size(); j++)
            {
                if (Main.allActors.get(j) != this && Main.allActors.get(j).collides() && nodes.get(i).intersects(Main.allActors.get(j).getHitBox()))
                {
                    if (!nodes.get(i).isCorner())
                    {
                        if (i == nodes.size() - 1)
                        {
                            nodes.get(0).setWalkable(false);
                        }
                        else
                        {
                            nodes.get(i + 1).setWalkable(false);
                        }
                        
                        if (i == 0)
                        {
                            nodes.get(nodes.size() - 1).setWalkable(false);
                        }
                        else
                        {
                            nodes.get(i - 1).setWalkable(false);
                        }
                    }
                    nodes.get(i).setWalkable(false);
                    break;
                }
            }
        }
        
        // Simple bubble sort since the list is always pretty small
        boolean flag = true;
        AStarNode temp;
        
        while (flag)
        {
            flag = false;
            for (int i = 0; i < nodes.size() - 1; i++)
            {
                if (nodes.get(i).getCost() > nodes.get(i + 1).getCost())
                {
                    temp = nodes.get(i);
                    nodes.set(i, nodes.get(i + 1));
                    nodes.set(i + 1, temp);
                    flag = true;
                }
            }
        }
        
        ArrayList<AStarNode> filtered = new ArrayList<AStarNode>();
        for (int i = 0; i < nodes.size(); i++)
        {
            if (nodes.get(i).isWalkable() && !nodes.get(i).isTraveled())
            {
                filtered.add(nodes.get(i));
            }
        }
        
        try
        {
            dest = filtered.get(0).getPosition();
            filtered.get(0).setTraveled(true);
            return dest;
        }
        catch (IndexOutOfBoundsException e)
        {
            nodes.clear();
            return getPosition();
        }
    }
    
    /**
     * This method generates nearby nodes from the NPC's hitbox and recalculates
     * the F cost of old nodes. Every time the NPC tries to create a new node,
     * it much check to see if a node like that already exists. These nodes are
     * not part of a perfect grid, so nodes don't line up exactly since the NPC
     * estimates its movement from node to node.
     * 
     * @param hitBox
     * @param destination
     */
    public void getAdjacentNodes (Rectangle2D hitBox, Position destination)
    {
        double diagCost = Math.sqrt(Math.pow(hitBox.getWidth(), 2) + Math.pow(hitBox.getHeight(), 2));
        double horizCost = hitBox.getWidth();
        double vertCost = hitBox.getHeight();
        
        for (int i = 0; i < nodes.size(); i++)
        {
            nodes.get(i).recalculate(destination);
        }
        
        // All 8 adjacent nodes in an arraylist in clockwise order starting from
        // top left - this is to calculate corner collisions later by checking
        // if the actor can fit through to that destination
        AStarNode a1 = new AStarNode(new Rectangle2D.Double(hitBox.getX() - hitBox.getWidth(), hitBox.getY() - hitBox.getHeight(), hitBox.getWidth(), hitBox.getHeight()), destination, diagCost, true);
        if (!nodeExists(a1))
        {
            nodes.add(a1);
        }
        AStarNode a2 = new AStarNode(new Rectangle2D.Double(hitBox.getX(), hitBox.getY() - hitBox.getHeight(), hitBox.getWidth(), hitBox.getHeight()), destination, vertCost, false);
        if (!nodeExists(a2))
        {
            nodes.add(a2);
        }
        AStarNode a3 = new AStarNode(new Rectangle2D.Double(hitBox.getX() + hitBox.getWidth(), hitBox.getY() - hitBox.getHeight(), hitBox.getWidth(), hitBox.getHeight()), destination, diagCost, true);
        if (!nodeExists(a3))
        {
            nodes.add(a3);
        }
        AStarNode a4 = new AStarNode(new Rectangle2D.Double(hitBox.getX() + hitBox.getWidth(), hitBox.getY(), hitBox.getWidth(), hitBox.getHeight()), destination, horizCost, false);
        if (!nodeExists(a4))
        {
            nodes.add(a4);
        }
        AStarNode a5 = new AStarNode(new Rectangle2D.Double(hitBox.getX() + hitBox.getWidth(), hitBox.getY() + hitBox.getHeight(), hitBox.getWidth(), hitBox.getHeight()), destination, diagCost, true);
        if (!nodeExists(a5))
        {
            nodes.add(a5);
        }
        AStarNode a6 = new AStarNode(new Rectangle2D.Double(hitBox.getX(), hitBox.getY() + hitBox.getHeight(), hitBox.getWidth(), hitBox.getHeight()), destination, vertCost, false);
        if (!nodeExists(a6))
        {
            nodes.add(a6);
        }
        AStarNode a7 = new AStarNode(new Rectangle2D.Double(hitBox.getX() - hitBox.getWidth(), hitBox.getY() + hitBox.getHeight(), hitBox.getWidth(), hitBox.getHeight()), destination, diagCost, true);
        if (!nodeExists(a7))
        {
            nodes.add(a7);
        }
        AStarNode a8 = new AStarNode(new Rectangle2D.Double(hitBox.getX() - hitBox.getWidth(), hitBox.getY(), hitBox.getWidth(), hitBox.getHeight()), destination, horizCost, false);
        if (!nodeExists(a8))
        {
            nodes.add(a8);
        }
    }
    
    /**
     * A method that checks if a node such as the one passed in exists already
     * for the NPC
     * 
     * @param node
     * @return
     */
    public boolean nodeExists (AStarNode node)
    {
        boolean flag = false;
        
        Rectangle2D.Double temp = new Rectangle2D.Double(node.getRectangle().x + 5, node.getRectangle().y + 5, node.getRectangle().width - 10, node.getRectangle().height - 10);
        
        for (int i = 0; i < nodes.size(); i++)
        {
            if (nodes.get(i).getRectangle().intersects(temp))
            {
                flag = true;
                break;
            }
        }
        
        return flag;
    }
    
    /**
     * Changes the NPC's animation to the direction that it's moving.
     * 
     * This code will be replaced by an animation engine in the future.
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
        
        else if (deltaX >= deltaY)
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
        else if (deltaY > deltaX)
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
     * Finds all actors that intersect with the NPC.
     * 
     * This code will be replaced eventually with a universal collision engine
     * 
     * @param position
     * @return
     */
    protected ArrayList<Actor> FindIntersectingActor (Position position)
    {
        Rectangle2D hitBox = new Rectangle2D.Double((int) position.x + getHitBoxOffset().width, (int) position.y + getHitBoxOffset().height, getHitBox().width, getHitBox().height);
        ArrayList<Actor> actors = new ArrayList<Actor>();
        
        for (int i = 0; i < Main.allActors.size(); i++)
        {
            if (Main.allActors.get(i) != this && (Main.allActors.get(i).collides()))
            {
                if (hitBox.intersects(Main.allActors.get(i).getHitBox()))
                    actors.add(Main.allActors.get(i));
            }
        }
        return actors;
    }
}

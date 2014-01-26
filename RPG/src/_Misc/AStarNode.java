package _Misc;

import java.awt.Dimension;
import java.awt.geom.Rectangle2D;

import _Vectors.Position;

public class AStarNode
{
    private Rectangle2D.Double hitBox;
    private int                cost;
    private double             gCost;
    private boolean            isCorner, walkable, traveled;
    
    /**
     * The node that is saved when calculating an AStar path in NPC.
     * 
     * @param hitBox
     * @param destination
     * @param gCost
     * @param isCorner
     */
    public AStarNode (Rectangle2D.Double hitBox, Position destination, double gCost, boolean isCorner)
    {
        this.hitBox = hitBox;
        this.isCorner = isCorner;
        walkable = true;
        traveled = false;
        this.gCost = gCost;
        
        recalculate(destination);
    }
    
    /**
     * Recalculates the F cost for this node relative to a destination.
     * 
     * @param destination
     */
    public void recalculate (Position destination)
    {
        // Calculate the heuristic for A* computing - Euclidean Method
        double hCost = 8 + Math.sqrt(((hitBox.x - destination.x + hitBox.width / 2) * (hitBox.x - destination.x + hitBox.width / 2)) + (hitBox.y - destination.y + hitBox.height / 2)
                * (hitBox.y - destination.y + hitBox.height / 2));
        
        // gCost is the distance from the origin to this node, hCost is the
        // rough distance from this node to the destination
        cost = (int) (gCost + hCost);
    }
    
    public int getCost ()
    {
        return cost;
    }
    
    /**
     * Checks if the node intersects with another.
     * 
     * @param otherBox
     * @return
     */
    public boolean intersects (Rectangle2D.Double otherBox)
    {
        return hitBox.intersects(otherBox);
    }
    
    public Position getPosition ()
    {
        return new Position(hitBox.x, hitBox.y, 0);
    }
    
    public Dimension getDimension ()
    {
        return new Dimension((int) hitBox.width, (int) hitBox.height);
    }
    
    public Rectangle2D.Double getRectangle ()
    {
        return hitBox;
    }
    
    public boolean isCorner ()
    {
        return isCorner;
    }
    
    public void setWalkable (boolean isWalkable)
    {
        walkable = isWalkable;
    }
    
    public boolean isWalkable ()
    {
        return walkable;
    }
    
    public void setTraveled (boolean isTraveled)
    {
        traveled = isTraveled;
    }
    
    public boolean isTraveled ()
    {
        return traveled;
    }
}

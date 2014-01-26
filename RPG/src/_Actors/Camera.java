package _Actors;

import java.awt.Dimension;

import _Main.Main;
import _Vectors.Position;

public class Camera extends Actor
{
    private Position  oldPosition = getPosition();
    private Dimension oldDimension;
    
    /**
     * The main camera actor which can be locked to an actor. This handles most
     * of the resizing in the game and determines what is rendered in the scene.
     * 
     * @param newActor
     * @param size
     */
    public Camera (Actor newActor, Dimension size)
    {
        super("Camera");
        setDrawDimension(new Dimension(size.width, size.height));
        oldDimension = new Dimension(size.width, size.height);
        setDrawPosition(new Position((int) newActor.getPosition().x, (int) newActor.getPosition().y, 0));
        setHitBoxDimension(new Dimension(0, 0));
        setHitBoxOffset(new Dimension(0, 0));
    }
    
    /**
     * The main camera actor which can be locked to an actor. This handles most
     * of the resizing in the game and determines what is rendered in the scene.
     * 
     * @param newActor
     * @param size
     */
    public Camera (Dimension size)
    {
        super("Camera");
        setDrawDimension(new Dimension(size.width, size.height));
        oldDimension = new Dimension(size.width, size.height);
        setDrawPosition(new Position(0, 0, 0));
        setHitBoxDimension(new Dimension(0, 0));
        setHitBoxOffset(new Dimension(0, 0));
    }
    
    /**
     * Updates the camera around an actor, such as the player
     * 
     * @param myActor
     */
    public void update (PC myActor)
    {
        oldPosition = getPosition();
        setDrawDimension(oldDimension);
        
        if (myActor.getPosition().x >= getDrawBox().width / 2 && myActor.getPosition().x <= Main.dim.width - getDrawBox().width / 2)
            setDrawPosition(new Position(((int) myActor.getPosition().x + myActor.getDrawBox().width / 2 - getDrawBox().width / 2), getPosition().y, getPosition().z));
        if (myActor.getPosition().y >= getDrawBox().height / 2 && myActor.getPosition().y <= Main.dim.height - getDrawBox().height / 2)
            setDrawPosition(new Position(getPosition().x, ((int) myActor.getPosition().y - getDrawBox().height / 2), getPosition().z));
        
        setDrawPosition(new Position(((int) myActor.getPosition().x + myActor.getDrawBox().width / 2 - getDrawBox().width / 2 / Main.scale), ((int) myActor.getPosition().y - getDrawBox().height / 2
                / Main.scale), getPosition().z));
        setDrawDimension(new Dimension((int) (getDrawBox().width / Main.scale), (int) (getDrawBox().height / Main.scale)));
        inScreen();
    }
    
    /**
     * Updates the camera around a position
     * 
     * @param p
     */
    public void update (Position p)
    {
        oldPosition = getPosition();
        setDrawDimension(oldDimension);
        
        if (p.x >= getDrawBox().width / 2 && p.x <= Main.dim.width - getDrawBox().width / 2)
            setDrawPosition(new Position(((int) p.x - getDrawBox().width / 2), getPosition().y, getPosition().z));
        if (p.y >= getDrawBox().height / 2 && p.y <= Main.dim.height - getDrawBox().height / 2)
            setDrawPosition(new Position(getPosition().x, ((int) p.y - getDrawBox().height / 2), getPosition().z));
        setDrawPosition(p);
        setDrawDimension(new Dimension((int) (getDrawBox().width / Main.scale), (int) (getDrawBox().height / Main.scale)));
        inScreen();
    }
    
    /**
     * Attempts to resize the game to a new resolution
     * 
     * @param dim
     */
    public void resize (Dimension dim)
    {
        setDrawPosition(new Position(getPosition().x + getDrawBox().width / 2, getPosition().y + getDrawBox().height / 2, 0));
        oldDimension = dim;
        Position newPos = new Position(getPosition().x - dim.width / 2, getPosition().y - dim.height / 2, 0);
        if (newPos.x < 0)
        {
            newPos.x = 0;
        }
        if (newPos.y < 0)
        {
            newPos.y = 0;
        }
        if (newPos.x + dim.width > Main.dim.width)
        {
            newPos.x = Main.dim.width - dim.width;
        }
        if (newPos.y + dim.height > Main.dim.height)
        {
            newPos.y = Main.dim.height - dim.height;
        }
        setDrawPosition(newPos);
    }
    
    /**
     * Sets the position of the camera to the origin and saves its old position
     * so that it can jump back later
     */
    public void reset ()
    {
        oldPosition = getPosition();
        setDrawPosition(new Position(0, 0, 0));
    }
    
    /**
     * Jumps back to the old position saved by the reset() method
     */
    public void recover ()
    {
        setDrawPosition(oldPosition);
    }
    
    public Dimension getOldDimension ()
    {
        return oldDimension;
    }
    
    public Position getOldPosition ()
    {
        return oldPosition;
    }
}

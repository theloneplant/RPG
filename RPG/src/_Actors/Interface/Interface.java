package _Actors.Interface;

import _Actors.Actor;
import _Vectors.Position;

public abstract class Interface extends Actor
{
    /**
     * All actors that are related to the GUI will inherit from this class
     * 
     * I realize it's a confusing name - I may raname it later
     * 
     * @param name
     */
    public Interface (String name)
    {
        super(name);
    }
    
    public abstract void updatePos ();
    
    public abstract void setOffset (Position p);
    
    public abstract Position getOffset ();
    
    public Position getScaledPosition ()
    {
        return getPosition();
    }
}

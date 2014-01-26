package _Vectors;

public class Position extends Vector
{
    /**
     * A vector with a different name.
     * 
     * @param x
     * @param y
     * @param z
     */
    public Position (double x, double y, double z)
    {
        super(x, y, z);
    }
    
    public Position getPosition ()
    {
        return this;
    }
    
    public void setPosition (double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}

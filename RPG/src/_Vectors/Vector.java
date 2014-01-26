package _Vectors;

public abstract class Vector
{
    public double x;
    public double y;
    public double z;
    
    /**
     * A vector handles data for positions.
     * 
     * @param x
     * @param y
     * @param z
     */
    public Vector (double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /**
     * Adds a value to the vector
     * 
     * @param other
     */
    public void add (Vector other)
    {
        x += other.x;
        y += other.y;
        z += other.z;
    }
    
    /**
     * Subtracts a value from the vector
     * 
     * @param other
     */
    public void subtract (Vector other)
    {
        x -= other.x;
        y -= other.y;
        z -= other.z;
    }
    
    public double getX ()
    {
        return this.x;
    }
    
    public double getY ()
    {
        return this.y;
    }
    
    public double getZ ()
    {
        return this.z;
    }
}

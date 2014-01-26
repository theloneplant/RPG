package _Actors.Particles;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import _Actors.Emitters.Emitter;
import _Actors.Emitters.ParticleManager;
import _Vectors.Acceleration;
import _Vectors.Velocity;

public class Particle_Spark extends Particle
{
    /**
     * A spark particle
     * 
     * @param parent
     * @throws SlickException
     */
    public Particle_Spark (Emitter parent) throws SlickException
    {
        super(parent);
        
        Image spark = new Image("images/Particles/spark.jpg");
        
        setAll(parent, .7, new ParticleManager[]
        {
                new ParticleManager(0, spark, new Color(255, 180, 180, 255), 1, new Velocity(Math.random() * 100 - 50, 0, Math.random() * 250), new Acceleration(0, 0, Math.random() * 50), 0, true,
                        false, false), new ParticleManager(1, spark, new Color(255, 100, 0, 0), 1, null, null, (float) (Math.random() * 120 - 60), true, false, false) }, true, 0, 0, 20);
    }
}

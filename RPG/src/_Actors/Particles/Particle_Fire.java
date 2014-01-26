package _Actors.Particles;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import _Actors.Emitters.Emitter;
import _Actors.Emitters.ParticleManager;
import _Vectors.Acceleration;
import _Vectors.Velocity;

public class Particle_Fire extends Particle
{
    /**
     * A fire particle
     * 
     * @param parent
     * @throws SlickException
     */
    public Particle_Fire (Emitter parent) throws SlickException
    {
        super(parent);
        
        Image firejpg = new Image("images/Particles/fire4.jpg");
        Image firepng = new Image("images/Particles/fire4.png");
        
        setAll(parent, 1, new ParticleManager[]
        {
                new ParticleManager(0, firejpg, new Color(255, 255, 255, 255), 1, new Velocity(Math.random() * 30 - 15, Math.random() * 30 - 25, Math.random() * 100), new Acceleration(0, 0, 60),
                        (float) (Math.random() * 120 - 60), true, false, false),
                new ParticleManager(.2, firejpg, new Color(255, 100, 0, 150), 0, null, null, (float) (Math.random() * 120 - 60), true, false, false),
                new ParticleManager(.5, firepng, new Color(255, 0, 0, 0), 0, null, null, (float) (Math.random() * 120 - 60), false, false, false),
                new ParticleManager(.6, firepng, new Color(60, 60, 60, 180), 0, null, null, (float) (Math.random() * 120 - 60), false, false, false),
                new ParticleManager(1, firepng, new Color(60, 60, 60, 0), 2, null, null, (float) (Math.random() * 120 - 60), false, false, false) }, true, 0, 0, 20);
    }
}

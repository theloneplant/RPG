package _Actors.Particles;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import _Actors.Emitters.Emitter;
import _Actors.Emitters.ParticleManager;
import _Vectors.Acceleration;
import _Vectors.Velocity;

public class SporeParticle extends Particle
{
    /**
     * A spore particle
     * 
     * @param parent
     * @throws SlickException
     */
    public SporeParticle (Emitter parent) throws SlickException
    {
        super(parent);
        
        Image img = new Image("images/Particles/lighting.jpg");
        
        setAll(parent,
                8,
                new ParticleManager[]
                {
                        new ParticleManager(0, img, new Color(255, 255, 255, 0), 1, new Velocity(Math.random() * 30 - 15, 0, Math.random() * 30 - 15), new Acceleration(Math.random() * 10 - 5, 0, Math
                                .random() * 10 - 5), 0, true, false, false), new ParticleManager(.5, img, new Color(255, 255, 255, 200), 1, null, null, 0, true, false, false),
                        new ParticleManager(1, img, new Color(255, 255, 255, 0), 1, null, null, 0, true, false, false) }, true, 0, 0, 0);
    }
}

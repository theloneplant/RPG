package _Actors.Particles;

import java.awt.Dimension;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import _Actors.Actor;
import _Actors.Emitters.Emitter;
import _Actors.Emitters.ParticleManager;
import _Appearances.ImageAppearance;
import _Misc.KeyEvent;
import _Misc.MouseEvent;
import _Vectors.Position;
import _Vectors.Velocity;

public class Particle extends Actor
{
    private ParticleManager[] manager;
    private int               currentManager, xOffset, yOffset, zOffset;
    private double            life, lifeProgress, lifePercent;
    private boolean           additive, aligned, attached, smoothTransition;
    private double            parentX, parentY, parentZ;
    private float             rotation;
    private Emitter           parent;
    private Color             myColor;
    private Image             myImage;
    
    // additive = Additive light blending.
    // aligned = The particle's rotation is determined by the angle of velocity.
    // attached = The particle will move with its emitter.
    
    /**
     * Particles are non collision actors that rely on drawing themselves and
     * transforming to improve the aesthetic of the environment. Each particle
     * holds an array of ParticleManagers which help the particle transform
     * throughout its life.
     * 
     * This is still unfinished, so not all variables are used.
     * 
     * @param parent
     */
    public Particle (Emitter parent)
    {
        super("Particle");
        
        currentManager = 0;
        manager = null;
        
        this.parent = parent;
        parentX = parent.getPosition().x;
        parentY = parent.getPosition().y;
        parentZ = parent.getPosition().z;
        
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;
        
        setHitBoxDimension(new Dimension(0, 0));
        setHitBoxOffset(new Dimension(0, 0));
        setCollides(false);
        this.smoothTransition = smoothTransition;
        
        if (additive == true)
        {
            // Opacity with additive lighting depends on RGB value, not alpha
            double percentOpacity = myColor.getAlpha() / 255.0;
            myColor.r *= percentOpacity;
            myColor.g *= percentOpacity;
            myColor.b *= percentOpacity;
        }
        
        life = 0;
        lifeProgress = 0;
        lifePercent = 0;
    }
    
    public void draw (Graphics g)
    {
        if (manager[currentManager].isAdditive() == true)
            g.setDrawMode(Graphics.MODE_ADD);
        
        super.draw(g);
        
        g.setDrawMode(Graphics.MODE_NORMAL);
    }
    
    public void update (double gameTime, KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        lifeProgress += gameTime;
        lifePercent = lifeProgress / life;
        
        if (lifePercent >= 1)
            setAlive(false);
        
        if (attached == true)
        {
            double deltaX, deltaY, deltaZ;
            deltaX = parent.getPosition().x - parentX;
            deltaY = parent.getPosition().y - parentY;
            deltaZ = parent.getPosition().z - parentZ;
            
            setDrawPosition(new Position(getPosition().x + deltaX + xOffset, getPosition().y + deltaY + yOffset, getPosition().z + deltaZ + zOffset));
            
            parentX = parent.getPosition().x;
            parentY = parent.getPosition().y;
            parentZ = parent.getPosition().z;
        }
        
        super.update(gameTime, keyEvent, mouseEvent);
        setVelocity(new Velocity(getVelocity().x + getAcceleration().x * gameTime, getVelocity().y + getAcceleration().y * gameTime, getVelocity().z + getAcceleration().z * gameTime));
        
        double managerProgress;
        if (manager.length - 1 >= currentManager + 1 && manager[currentManager + 1] != null)
        {
            ParticleManager current = manager[currentManager];
            ParticleManager next = manager[currentManager + 1];
            
            if (smoothTransition)
            {
                managerProgress = (lifePercent - current.getLifePercent()) / (next.getLifePercent() - current.getLifePercent());
                myImage = current.getImage();
                
                Color scaledCurrentColor = current.getColor().scaleCopy(1 - (float) managerProgress);
                Color scaledNextColor = next.getColor().scaleCopy((float) managerProgress);
                myColor = scaledCurrentColor.addToCopy(scaledNextColor);
                
                if (additive == true)
                {
                    // Scale RGB values with alpha
                    myColor.scale((float) (current.getColor().a * (1 - managerProgress) + next.getColor().a * (managerProgress)));
                }
                
                rotation = current.getRotation();
                setAppearance(new ImageAppearance(myImage, myColor, rotation));
                
                if (lifePercent >= next.getLifePercent())
                {
                    currentManager += 1;
                }
            }
            else
            {
                if (lifePercent >= next.getLifePercent())
                {
                    myImage = next.getImage();
                    myColor = next.getColor();
                    rotation = next.getRotation();
                    setAcceleration(next.getAcceleration());
                    setVelocity(next.getVelocity());
                    setAppearance(new ImageAppearance(myImage, myColor, rotation));
                    currentManager += 1;
                }
            }
        }
        
        if (aligned == true)
        {
            float theta = (float) Math.atan2(getVelocity().y, getVelocity().x) * 57;
            
            setAppearance(new ImageAppearance(myImage, myColor, theta));
        }
    }
    
    public void setManagers (ParticleManager[] manager)
    {
        this.manager = manager;
    }
    
    /**
     * Sets all of the parameters in this particle
     * 
     * @param parent
     * @param myLife
     * @param managers
     * @param smoothTransition
     * @param xOffset
     * @param yOffset
     * @param zOffset
     */
    public void setAll (Emitter parent, double myLife, ParticleManager[] managers, boolean smoothTransition, int xOffset, int yOffset, int zOffset)
    {
        currentManager = 0;
        manager = managers;
        
        this.parent = parent;
        parentX = parent.getPosition().x;
        parentY = parent.getPosition().y;
        parentZ = parent.getPosition().z;
        
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;
        
        setDrawDimension(new Dimension(manager[currentManager].getImage().getWidth(), manager[currentManager].getImage().getHeight()));
        setDrawPosition(new Position(parent.getPosition().x - getDrawBox().width / 2 + xOffset, parent.getPosition().y - getDrawBox().height / 2 + yOffset, parent.getPosition().z + zOffset));
        setHitBoxDimension(new Dimension(0, 0));
        setHitBoxOffset(new Dimension(0, 0));
        
        setVelocity(manager[currentManager].getVelocity());
        setAcceleration(manager[currentManager].getAcceleration());
        
        myImage = manager[currentManager].getImage();
        myColor = manager[currentManager].getColor();
        rotation = manager[currentManager].getRotation();
        additive = manager[currentManager].isAdditive();
        aligned = manager[currentManager].isAligned();
        attached = manager[currentManager].isAttached();
        this.smoothTransition = smoothTransition;
        
        if (additive == true)
        {
            // Opacity with additive lighting depends on RGB value, not alpha
            double percentOpacity = myColor.getAlpha() / 255.0;
            myColor.r *= percentOpacity;
            myColor.g *= percentOpacity;
            myColor.b *= percentOpacity;
        }
        setAppearance(new ImageAppearance(myImage, myColor, rotation));
        
        life = myLife;
        lifeProgress = 0;
        lifePercent = 0;
    }
}
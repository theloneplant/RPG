package _Actions;

import _Actors.Actor;
import _Actors.FriendlyProjectile;
import _Actors.PC;
import _Inputs.Input;
import _Main.Main;
import _Misc.KeyEvent;
import _Misc.MouseEvent;
import _Vectors.Position;
import _Vectors.Velocity;

public class ShootAction extends Action
{
    private Input mouse;
    
    /**
     * Finds the vector between the mouse and player, then creates a projectile
     * with a velocity in that vector direction
     * 
     * @param mouse
     * @param keyCode
     */
    public ShootAction (Input mouse, int keyCode)
    {
        this.mouse = mouse;
    }
    
    public void update (Actor parent, double gameTime, KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        PC character = (PC) parent;
        
        if (mouse.Happened(keyEvent, mouseEvent) && !getBusy())
        {
            this.start();
            
            if (character.getShotCooldown() >= 10)
            {
                character.setShotCooldown(character.getShotCooldown() - 10);
                
                FriendlyProjectile shot = new FriendlyProjectile(parent);
                double shotSpeed = shot.getSpeed();
                
                double xDistance = mouseEvent.x - parent.getDrawBox().x - parent.getDrawBox().width / 2 + Main.camera.getDrawBox().x;
                double yDistance = mouseEvent.y - parent.getDrawBox().y - parent.getDrawBox().height / 2 + Main.camera.getDrawBox().y;
                
                shot.setDrawPosition(new Position((int) parent.getDrawBox().x + parent.getDrawBox().width / 2 - shot.getDrawBox().width / 2, (int) parent.getDrawBox().y + parent.getDrawBox().height
                        / 2 - shot.getDrawBox().height / 2, 0));
                
                double speedDistance = Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));
                
                double vectorX = (shotSpeed / speedDistance) * xDistance;
                double vectorY = (shotSpeed / speedDistance) * yDistance;
                
                shot.setVelocity(new Velocity(vectorX, vectorY, 0));
                shot.createEmitter();
                Main.allActors.add(shot);
            }
        }
        
        if (mouse.Happened(keyEvent, mouseEvent) && getBusy())
        {
            this.stop();
        }
    }
}

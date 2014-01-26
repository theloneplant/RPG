package _Actions;

import _Actors.Actor;
import _Actors.PC;
import _Inputs.Input;
import _Main.Main;
import _Misc.KeyEvent;
import _Misc.MouseEvent;
import _Vectors.Position;

public class SelectAction extends Action
{
    private Input start, end;
    private PC    character;
    
    /**
     * Changes the visibility of the player's selection box with Inputs
     * 
     * @param character
     * @param start
     * @param end
     */
    public SelectAction (PC character, Input start, Input end)
    {
        this.start = start;
        this.end = end;
        this.character = character;
    }
    
    public void update (Actor parent, double gameTime, KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        if (!enabled)
        {
            this.stop();
            character.getSelection().setVisible(false);
        }
        
        if (this.start.Happened(keyEvent, mouseEvent) && !getBusy())
        {
            this.start();
            character.getSelection().setOldPos(new Position(mouseEvent.x + Main.camera.getPosition().x, mouseEvent.y + Main.camera.getPosition().y, 0));
            character.getSelection().setVisible(true);
        }
        
        if (this.end.Happened(keyEvent, mouseEvent) && getBusy())
        {
            this.stop();
            character.getSelection().setVisible(false);
        }
    }
}

package _Actions;

import _Actors.Actor;
import _Actors.PC;
import _Inputs.Input;
import _Misc.KeyEvent;
import _Misc.MouseEvent;

public class SwitchControlMode extends Action
{
    private Input start;
    private PC    character;
    
    /**
     * Toggles the control mode for the user between command and combat.
     * 
     * Disables the list of actions not in use and enables the list of actions
     * that will be used
     * 
     * @param character
     * @param start
     */
    public SwitchControlMode (PC character, Input start)
    {
        this.start = start;
        this.character = character;
    }
    
    public void update (Actor parent, double gameTime, KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        if (start.Happened(keyEvent, mouseEvent))
        {
            this.start();
            
            if (character.isCommanding())
            {
                character.setCommanding(false);
                for (int i = 0; i < character.getCommand().size(); i++)
                {
                    if (character.getCommand().size() > 0)
                    {
                        character.getCommand().get(i).setEnabled(false);
                        character.getCommand().get(i).update(parent, gameTime, keyEvent, mouseEvent);
                    }
                }
                for (int i = 0; i < character.getCombat().size(); i++)
                {
                    if (character.getCombat().size() > 0)
                    {
                        character.getCombat().get(i).setEnabled(true);
                        character.getCombat().get(i).update(parent, gameTime, keyEvent, mouseEvent);
                    }
                }
            }
            else
            {
                character.setCommanding(true);
                for (int i = 0; i < character.getCommand().size(); i++)
                {
                    if (character.getCommand().size() > 0)
                    {
                        character.getCommand().get(i).setEnabled(true);
                        character.getCommand().get(i).update(parent, gameTime, keyEvent, mouseEvent);
                    }
                }
                for (int i = 0; i < character.getCombat().size(); i++)
                {
                    if (character.getCombat().size() > 0)
                    {
                        character.getCombat().get(i).setEnabled(false);
                        character.getCombat().get(i).update(parent, gameTime, keyEvent, mouseEvent);
                    }
                }
            }
        }
    }
}

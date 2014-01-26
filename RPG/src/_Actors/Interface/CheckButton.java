package _Actors.Interface;

import java.awt.Dimension;

import org.newdawn.slick.Graphics;

import _Actions.Action;
import _Appearances.Appearance;
import _Misc.KeyEvent;
import _Misc.MouseEvent;
import _Vectors.Position;

public class CheckButton extends Button
{
    private boolean checked;
    
    /**
     * A button that toggles between being clicked
     * 
     * @param text
     * @param parent
     * @param size
     * @param offset
     * @param action
     * @param normal
     * @param hover
     * @param clicked
     * @param textX
     * @param textY
     * @param isTitleFont
     * @param currentState
     */
    public CheckButton (String text, Window parent, Dimension size, Position offset, Action action, Appearance normal, Appearance hover, Appearance clicked, int textX, int textY, boolean isTitleFont,
            boolean currentState)
    {
        super(text, parent, size, offset, action, normal, hover, clicked, textX, textY, isTitleFont, false);
        checked = currentState;
    }
    
    public void update (double delta, KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        text.updatePos();
        if (isClicked(mouseEvent))
        {
            if (checked == false)
            {
                checked = true;
                if (action != null)
                {
                    action.start();
                    action.update(parent, delta, keyEvent, mouseEvent);
                    action.stop();
                }
            }
            else
            {
                checked = false;
                if (action != null)
                {
                    action.start();
                    action.update(parent, delta, keyEvent, mouseEvent);
                    action.stop();
                }
            }
        }
    }
    
    public void draw (Graphics g)
    {
        if (checked == true)
        {
            setAppearance(clicked);
        }
        super.draw(g);
    }
}

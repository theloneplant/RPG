package _Actors.Interface;

import java.awt.Dimension;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import _Appearances.Appearance;
import _Main.Main;
import _Misc.KeyEvent;
import _Misc.MouseEvent;
import _Vectors.Position;

public class DropDownMenu extends Interface
{
    protected ArrayList<Button> buttons;
    private Window              win;
    private Button              mainButton;
    
    /**
     * A button that creates a window filled with an Arraylist of buttons
     * 
     * @param text
     * @param parent
     * @param buttons
     * @param dim
     * @param offset
     * @param normal
     * @param hover
     * @param clicked
     * @param textY
     * @param title
     */
    public DropDownMenu (String text, Window parent, ArrayList<Button> buttons, Dimension dim, Position offset, Appearance normal, Appearance hover, Appearance clicked, int textY, boolean title)
    {
        super(text);
        this.buttons = buttons;
        int height = 0;
        int width = 0;
        mainButton = new Button(text, parent, dim, offset, null, normal, hover, clicked, 0, textY, title, true);
        setDrawPosition(new Position(parent.getPosition().x + mainButton.getPosition().x, parent.getPosition().y + mainButton.getPosition().y + mainButton.getDrawBox().height, 0));
        win = new Window(new Dimension(width, height), new Position(getPosition().x + mainButton.getPosition().x, getPosition().y + mainButton.getPosition().y + mainButton.getDrawBox().height, 0),
                new Color(0, 0, 0, 0));
        
        for (int i = 0; i < buttons.size(); i++)
        {
            win.add(buttons.get(i));
            height += buttons.get(i).getDrawBox().height;
            if (buttons.get(i).getDrawBox().width > width)
            {
                width = (int) buttons.get(i).getDrawBox().width;
                buttons.get(i).setDrawPosition(
                        new Position(getPosition().x + mainButton.getPosition().x, getPosition().y + mainButton.getPosition().y + mainButton.getDrawBox().height
                                + (buttons.get(i).getDrawBox().height * i), 0));
            }
        }
    }
    
    public void update (double delta, KeyEvent keyEvent, MouseEvent mouseEvent)
    {
        updatePos();
        if (mainButton.isFocused())
        {
            win.setDrawPosition(getPosition());
            win.update(delta, keyEvent, mouseEvent);
            for (int i = 0; i < buttons.size(); i++)
            {
                buttons.get(i).update(delta, keyEvent, mouseEvent);
                if (buttons.get(i).isFocused())
                {
                    mainButton.setText(buttons.get(i).getText().toString());
                }
            }
        }
        mainButton.update(delta, keyEvent, mouseEvent);
    }
    
    public void draw (Graphics g)
    {
        mainButton.draw(g);
        if (mainButton.isFocused())
        {
            win.draw(g);
            for (int i = 0; i < buttons.size(); i++)
            {
                buttons.get(i).draw(g);
            }
        }
    }
    
    /**
     * Updates the position relative to a parent actor
     */
    @Override
    public void updatePos ()
    {
        mainButton.updatePos();
        setDrawPosition(new Position(mainButton.getPosition().x, mainButton.getPosition().y + mainButton.getDrawBox().height, 0));
        for (int i = 0; i < buttons.size(); i++)
        {
            buttons.get(i).setOffset(new Position(mainButton.getOffset().x, mainButton.getOffset().y + mainButton.getDrawBox().height + (buttons.get(i).getDrawBox().height * i), 0));
        }
    }
    
    @Override
    public void setOffset (Position p)
    {
    }
    
    @Override
    public Position getOffset ()
    {
        return null;
    }
    
    @Override
    public Position getScaledPosition ()
    {
        return new Position(getPosition().x * Main.scale, getPosition().y * Main.scale, getPosition().z * Main.scale);
    }
    
}

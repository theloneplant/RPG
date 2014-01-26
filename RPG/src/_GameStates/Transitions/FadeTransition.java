package _GameStates.Transitions;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import _GameStates.GameState;
import _Main.Main;

public class FadeTransition extends Transition
{
    private Color color;
    private float time, life, loadTime, loading;
    private float alpha;
    private boolean halfway, scaledTime, loaded;
    
    /**
     * Fades from one state to another. This doesnt change the height of the
     * stack at all, just the current element.
     * 
     * @param gs1
     * @param gs2
     * @param c
     * @param duration
     */
    public FadeTransition (GameState gs1, GameState gs2, Color c, float duration)
    {
        super(gs1, gs2, false);
        color = new Color((float) c.r, (float) c.g, (float) c.b, 0);
        time = 0;
        life = duration;
        loaded = true;
    }
    
    public void update (double delta)
    {
        time += delta;
        float halfLife = life / 2;
        
        if (time < halfLife)
        {
            alpha = (float) (time / halfLife);
            color.a = alpha;
        }
        else if (halfway == false)
        {
            halfway = true;
            color.a = 1;
            loadTime = time;
            Main.gsm.resume();
            Main.gsm.changeState(state2);
            scaledTime = false;
        }
        else if (time >= halfLife)
        {
            if (scaledTime == false)
            {
                loadTime = time - loadTime;
                life += loadTime;
                scaledTime = true;
                loaded = false;
            }
            if (loading <= loadTime)
            {
                loading += delta;
                color.a = 1;
            }
            else
            {
                loaded = true;
            }
            if (loaded == true)
            {
                alpha = (float) 1 - (time - halfLife) / halfLife;
                color.a = alpha;
            }
        }
        
        if (time >= life)
        {
            switched = true;
        }
    }
    
    public void draw (Graphics g)
    {
        g.setColor(color);
        g.fillRect((float) Main.camera.getPosition().x * Main.scale, (float) Main.camera.getPosition().y * Main.scale, Main.screen.width, Main.screen.height);
    }
}

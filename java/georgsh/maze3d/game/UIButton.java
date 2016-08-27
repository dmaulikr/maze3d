package georgsh.maze3d.game;

import android.graphics.Rect;

/**
 * Created by yura on 27.08.16.
 */
interface UIListener
    {
    void activate();
    }

public class UIButton
    {
    Rect coords;
    String text;
    TextElement drawabletext;
    UIListener callback;

    UIButton(String text, Rect coords, UIListener callback)
        {
        this.coords = new Rect(coords);
        this.text = text;
        this.callback = callback;
        }
    void enable(TextRenderer renderer)
        {
        drawabletext = renderer.addText(text, coords);
        }
    void disable(TextRenderer renderer)
        {
        renderer.removeText(drawabletext);
        }
    boolean Click(float x, float y)
        {
        if(coords.contains((int)x,(int)y))
            {
            callback.activate();
            }
        return false;
        }
    }

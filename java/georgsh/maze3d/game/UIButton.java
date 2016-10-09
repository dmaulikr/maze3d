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

    UIButton(String text, TextBuffer buffer, Rect coords, UIListener callback)
        {
        buffer.addText(text, coords);
        this.coords = new Rect(coords);
        this.text = text;
        this.callback = callback;
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

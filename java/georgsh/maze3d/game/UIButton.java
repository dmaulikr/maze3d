package georgsh.maze3d.game;

import android.graphics.Rect;

/**
 * Created by yura on 27.08.16.
 */
public class UIButton
    {
    Rect coords;
    String text;
    TextElement drawabletext;

    UIButton(String text, Rect coords)
        {
        this.coords = new Rect(coords);
        this.text = text;
        }
    void enable(TextRenderer renderer)
        {
        drawabletext = renderer.addText(text, coords);
        }
    void disable(TextRenderer renderer)
        {
        renderer.removeText(drawabletext);
        }
    }

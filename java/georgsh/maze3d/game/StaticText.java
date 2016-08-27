package georgsh.maze3d.game;

import android.graphics.Rect;

/**
 * Created by yura on 27.08.16.
 */
public class StaticText extends TextElement
    {
    StaticText(TextRenderer textrender, String text, Rect boundbox)
        {
        super(textrender, text, boundbox);
        alpha = 1;
        }
    @Override
    public boolean update()
        {
        return true;
        }
    }

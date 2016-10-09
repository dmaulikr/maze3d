package georgsh.maze3d.game;

import android.graphics.Rect;
import android.util.Log;

import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by yura on 09.10.16.
 */
public class TextBuffer
    {
    Vector<TextElement> alltexts = new Vector<TextElement>();
    TextRenderer renderer;

    TextBuffer(TextRenderer renderer)
        {
        this.renderer = renderer;
        }

    public void removeText(TextElement text)
        {
        alltexts.remove(text);
        }
    public void removeAll()
        {
        alltexts.removeAllElements();
        }

    public void draw(GL10 gl)
        {
        renderer.draw(gl, alltexts);
        }

    public TextElement addText(String text, Rect size)
        {
        TextElement newobj = new StaticText(renderer, text, size);
        newobj.setPos(size.centerX(), size.centerY());
        alltexts.add(newobj);
        return newobj;
        }
    public void AddTextNotify(String text, float scrw, float scrh)
        {
        Log.d("maze3d", "Add text notify");
        TextElement newobj = new TextElement(renderer, text, new Rect(0,0,(int)(scrw*0.8),(int)(scrh*0.25))); // TODO
        newobj.setPos(scrw/2, scrh/2);
        alltexts.add(newobj);
        }
    }

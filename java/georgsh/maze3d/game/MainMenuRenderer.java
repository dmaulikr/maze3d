package georgsh.maze3d.game;

import android.graphics.Rect;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

import java.util.Iterator;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by yura on 29.08.16.
 */
public class MainMenuRenderer implements Renderer
    {
    UIButton startgame;
    MyGlSurface game;
    TextBuffer menutext;
    class UIStart implements UIListener
        {
        @Override
        public void activate()
            {
            game.SwitchToGame();
            }
        }
    MainMenuRenderer(MyGlSurface game)
        {
        this.game = game;
        if (game.textrender == null)
            {
            Log.e("maze3d","error: initialization menu render before textrender");
            }
        menutext = new TextBuffer(game.textrender);

        int width = (int)game.scrw;
        int height = (int)game.scrh;
        int buttonw = width/2;
        int buttonh = height/10;
        Rect coords = new Rect(width/2-buttonw/2,height/2-buttonh/2,
                width/2+buttonw/2,height/2+buttonh/2);
        startgame = new UIButton("START", menutext, coords, new UIStart());
        }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig)
        {

        }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1)
        {

        }

    void drawmenu(GL10 gl)
        {
        game.init2dsufrace(gl);

        menutext.draw(gl);

        game.end2dsurface(gl);
        }

    @Override
    public void onDrawFrame(GL10 gl)
        {
        Iterator<TouchEvent> touches = game.touch_buffer.iterator();
        while(touches.hasNext())
            {
            TouchEvent touch = touches.next();
            startgame.Click(touch.x, touch.y);
            touches.remove();
            }
        drawmenu(gl);
        }
    }

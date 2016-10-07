package georgsh.maze3d.game;

import android.opengl.GLSurfaceView.Renderer;

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
    MainMenuRenderer()
        {

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

        //textrender.draw(gl);

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

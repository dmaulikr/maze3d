package georgsh.maze3d.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by yura on 07.07.16.
 */
public class LeverObj
    {
    BlockMesh pad;
    BlockMesh stick;

    LeverObj(Bitmap padtex, Bitmap levertex)
        {
        float size = 1.5f;
        pad = new BlockMesh(0.15f,0.2f,0.05f, padtex);
        stick = new BlockMesh(0.03f,0.03f,0.15f, levertex);
        }
    public void drawAllPads(GL10 gl, Vector<LeverLogic> levers)
        {
        pad.beginDraw(gl);
        for (LeverLogic lever: levers)
            {
            if(!lever.isVisible()) { continue; }
            float curx = lever.posx;
            float cury = lever.posy;
            gl.glPushMatrix();
            gl.glTranslatef(curx,cury,0);
            gl.glRotatef(lever.zrot, 0, 0, 1);
            gl.glRotatef(90, 1, 0, 0);
            gl.glRotatef(90, 0, 1, 0);
            pad.drawBody(gl);
            gl.glPopMatrix();
            }
        pad.endDraw(gl);

        }
    public void drawAllSticks(GL10 gl, Vector<LeverLogic> levers)
        {
        stick.beginDraw(gl);
        for (LeverLogic lever: levers)
            {
            if(!lever.isVisible()) { continue; }
            float curx = lever.posx;
            float cury = lever.posy;
            gl.glPushMatrix();
            gl.glTranslatef(curx,cury,0);
            gl.glRotatef(lever.zrot, 0, 0, 1);
            gl.glRotatef(90, 1, 0, 0);
            gl.glRotatef(90, 0, 1, 0);

            gl.glRotatef(lever.stickangle, 1, 0, 0);
            gl.glTranslatef(0, 0, stick.zs/2);
            stick.drawBody(gl);
            gl.glPopMatrix();
            }
        stick.endDraw(gl);
        }
    public void loadGLTexture(GL10 gl)
        {
        pad.loadGLTexture(gl);
        stick.loadGLTexture(gl);
        }
    }

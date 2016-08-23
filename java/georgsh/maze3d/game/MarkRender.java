package georgsh.maze3d.game;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by yura on 22.08.16.
 */
public class MarkRender extends Mesh
    {
    Vector<MarkObject> marks = new Vector<MarkObject>();
    Vector<MarkObject> allmarks = new Vector<MarkObject>();

    MyGlSurface game;
    MarkRender(MyGlSurface game, Bitmap texture)
        {
        this.game = game;
        float size = game.blocksize/2;
        float textureCoordinates[] =
                { 0.0f, 1.0f,
                        1.0f, 1.0f,
                        0.0f, 0.0f,
                        1.0f, 0.0f, };

        short[] indices = new short[] { 0, 1, 2, 1, 3, 2 };
        //float z = size/2;
        float[] vertices = new float[] {
                -size/2, -size/2, 0,
                size/2, -size/2, 0,
                -size/2, size/2, 0,
                size/2, size/2, 0 };

        setIndices(indices);
        setVertices(vertices);
        setTextureCoordinates(textureCoordinates);
        loadBitmap(texture);
        }
    void addMark(int x, int y, int z_rot)
        {
        MarkObject newmark = new MarkObject(x,y,z_rot,game);
        marks.add(newmark);
        allmarks.add(newmark);
        }
    void hideMarksInBlock()
        {
        // TODO
        }
    void showMarksInBlock()
        {
        // TODO
        }
    void startRender(GL10 gl)
        {
        beginDraw(gl);
        }
    void drawAllMarks(GL10 gl)
        {
        //Log.d("maze3d", "Render all marks");
        startRender(gl);
        for(MarkObject mark: marks)
            {
            gl.glPushMatrix();
            //Log.d("maze3d", "mark : "+mark.posx+", "+mark.posy);
            gl.glTranslatef(mark.posx,mark.posy,0);
            gl.glRotatef(mark.zrot, 0, 0, 1);
            gl.glRotatef(-90, 1, 0, 0);
            gl.glRotatef(90, 0, 1, 0);
            gl.glDrawElements(GL10.GL_TRIANGLES, mNumOfIndices,
                    GL10.GL_UNSIGNED_SHORT, mIndicesBuffer);
            gl.glPopMatrix();
            }
        endRender(gl);
        }
    void endRender(GL10 gl)
        {
        endDraw(gl);
        }
    }

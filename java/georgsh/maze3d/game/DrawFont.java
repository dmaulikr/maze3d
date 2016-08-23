package georgsh.maze3d.game;

import android.graphics.Bitmap;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by yura on 25.07.16.
 */
public class DrawFont extends Mesh
    {
    public DrawFont()
        {
        float textureCoordinates[] =
                { 0.0f, 1.0f,
                        1.0f, 1.0f,
                        0.0f, 0.0f,
                        1.0f, 0.0f, };

        short[] indices = new short[] { 0, 1, 2, 1, 3, 2 };
        float size = 100f;
        float z = 0;
        float[] vertices = new float[] {
                -size/2, -size/2, z,
                size/2, -size/2, z,
                -size/2, size/2, z,
                size/2, size/2, z };

        setIndices(indices);
        setVertices(vertices);
        setTextureCoordinates(textureCoordinates);
        }

    }

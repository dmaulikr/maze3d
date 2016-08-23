package georgsh.maze3d.game;

import android.graphics.Bitmap;

public class Cube extends Mesh {


    public Cube(float width, float height, float px, float py, float pz, Bitmap tex) {
        float textureCoordinates[] =
                { 0.0f, 1.0f,
                        1.0f, 1.0f,
                        0.0f, 0.0f,
                        1.0f, 0.0f,

                        0.0f, 0.0f,
                        0.0f, 1.0f,
                        1.0f, 0.0f,
                        1.0f, 1.0f,
                };

        short[] indices = new short[]
                { 0, 2, 1, 3, 1, 2,
                        4, 5, 6, 7, 6, 5,
                        4, 0, 5, 1, 5, 0,
                        3, 2, 7, 6, 7, 2,
                        3, 7, 1, 5, 1, 7,
                        0, 4, 2, 6, 2, 4,
                };

        float[] vertices = new float[]
                { -0.5f + px, -0.5f + py, -0.5f + pz,
                    0.5f + px, -0.5f + py, -0.5f + pz,
                    -0.5f + px,  0.5f + py, -0.5f + pz,
                    0.5f + px,  0.5f + py, -0.5f + pz,
                    -0.5f + px, -0.5f + py,  0.5f + pz,
                    0.5f + px, -0.5f + py,  0.5f + pz,
                    -0.5f + px,  0.5f + py,  0.5f + pz,
                    0.5f + px,  0.5f + py,  0.5f + pz};

        setIndices(indices);
        setVertices(vertices);
        setTextureCoordinates(textureCoordinates);
        loadBitmap(tex);
    }
}
package georgsh.maze3d.game;

import android.graphics.Bitmap;

public class BlockMesh extends Mesh
    {
    float xs, ys, zs;
    public BlockMesh(float xs, float ys, float zs, Bitmap tex)
        {
        this.xs = xs;
        this.ys = ys;
        this.zs = zs;
        float[] alltexcoords = new float[8 * 6];
        float[] texpos = new float[]
                {
                        0f, 0f,           // bottom
                        1f / 4f, 0f,      // top
                        2f / 4f, 0f,      // back(on map)
                        3f / 4f, 0f,      // front(on map)
                        0f, 1f / 4f,      // right(on map)
                        1f / 4f, 1f / 4f, // left(on map)
                };

        int[] texturemap = new int[]{
                2, 3, 0, 1, // bottom
                4, 5, 6, 7, // top
                6, 7, 2, 3, // back(on map)
                5, 4, 1, 0, // front(on map)
                7, 5, 3, 1, // right(on map)
                4, 6, 0, 2, // left(on map)
        };
        short[] indices = new short[]{ 0, 1, 3, 3, 2, 0 };
        short[] allindices = new short[6 * texturemap.length/4];

        for (int i = 0; i < texturemap.length/4; i++)
            {
            alltexcoords[i * 8 + 0] = texpos[i*2];
            alltexcoords[i * 8 + 1] = texpos[i*2 + 1];

            alltexcoords[i * 8 + 2] = texpos[i*2] + 1 / 4f;
            alltexcoords[i * 8 + 3] = texpos[i*2 + 1];

            alltexcoords[i * 8 + 4] = texpos[i*2];
            alltexcoords[i * 8 + 5] = texpos[i*2 + 1] + 1 / 4f;

            alltexcoords[i * 8 + 6] = texpos[i*2] + 1 / 4f;
            alltexcoords[i * 8 + 7] = texpos[i*2 + 1] + 1 / 4f;
            }
        for (int i = 0; i < texturemap.length/4; i++)
            {
            for (int p = 0; p < 6; p++)
                {
                allindices[i * 6 + p] = (short) (i * 4 + indices[p]);
                }
            }
        float[] vertices = new float[]{
                -xs / 2, -ys / 2, -zs / 2,
                xs / 2, -ys / 2, -zs / 2,
                -xs / 2,  ys / 2, -zs / 2,
                xs / 2, ys / 2, -zs / 2,
                -xs / 2, -ys / 2, zs / 2,
                xs / 2, -ys / 2, zs / 2,
                -xs / 2,  ys / 2, zs / 2,
                xs / 2, ys / 2, zs / 2,};
        float[] allvertices = new float[12 * texturemap.length/4];
        //String vertics = "";
        for (int i = 0; i < texturemap.length/4; i++)
            {
            for (int p = 0; p < 4; p++)
                {
                int cur_ver = texturemap[i * 4 + p];
                for (int c = 0; c < 3; c++)
                    {
                    allvertices[i * 12 + p * 3 + c] = vertices[cur_ver * 3 + c];
                    //vertics += ((Float)vertices[cur_ver * 3 + c]).toString()+", ";
                    }
                }
            }
        /*
        String allcoordss = "";
        for (int i = 0; i < 6 * 8; i++)
            {
            allcoordss += ((Float) alltexcoords[i]).toString() + ", ";
            }
        */
        //Log.d("maze3d", "tex coords = " + allcoordss);
        //Log.d("maze3d", "vertices = "+vertics);
        setIndices(allindices);
        setVertices(allvertices);
        //setColors(new float[]{});
        setTextureCoordinates(alltexcoords);
        loadBitmap(tex);
        }
    }

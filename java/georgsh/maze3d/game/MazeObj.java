package georgsh.maze3d.game;

import android.util.Log;

import java.util.Vector;

/**
 * Created by yura on 02.07.16.
 */
public class MazeObj extends Mesh
    {
    private float wallsize;
    public MazeObj(float size, Block[][] planesatpoint)
        {
        wallsize = size;
        genVertexArray(planesatpoint);
        //genSimple();
        }

    public void genVertexArray(Block[][] planesatpoint)
        {
        Vector<Float> vbb = new Vector<Float>();
        Vector<Float> tex = new Vector<Float>();
        float[] wallcrds = new float[]  {0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f};
        float[] floorcrds = new float[] {0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f};
        float[] ceilcrds = new float[]  {0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f};
        float[] fincrds = new float[]   {0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f};


        for(int i = 0; i < 8; i++) { wallcrds[i] = wallcrds[i]/2.0f; }
        for(int i = 0; i < 8; i++) { floorcrds[i] = wallcrds[i]; if(i%2 == 0) {floorcrds[i]+=0.5f;} } // x-shift
        for(int i = 0; i < 8; i++) { ceilcrds[i]  = wallcrds[i]; if(i%2 == 1) {ceilcrds[i] +=0.5f;} } // y-shift
        for(int i = 0; i < 8; i++) { fincrds[i]  = wallcrds[i];  fincrds[i] +=0.5f; } // x-y-shift
        //float[] wallcrds = new float[] {0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f};

        Vector<Integer> ind = new Vector<Integer>();
        int counter = 0;
        for (int y = 0; y < planesatpoint.length; y++)
            for (int x = 0; x < planesatpoint[y].length; x++)
                {
                Block cur_block = planesatpoint[y][x];

                if(cur_block.isEmpty())
                    {
                    for(int i = 0; i < 8; i++) { tex.add(ceilcrds[i]); }
                    if(cur_block.isFinish())
                        for(int i = 0; i < 8; i++) { tex.add(fincrds[i]); }
                    else
                        for(int i = 0; i < 8; i++) { tex.add(floorcrds[i]); }


                    ind.add(0+counter); ind.add(1+counter); ind.add(2+counter);
                    ind.add(1+counter); ind.add(3+counter); ind.add(2+counter);
                    counter+=4;
                    ind.add(0+counter); ind.add(1+counter); ind.add(2+counter);
                    ind.add(1+counter); ind.add(3+counter); ind.add(2+counter);
                    counter+=4;
                    // ceil
                    vbb.add((float) x * wallsize - wallsize/2 );
                    vbb.add((float) y * wallsize - wallsize/2 );
                    vbb.add((float) -wallsize/2 );

                    vbb.add((float) x * wallsize + wallsize/2 );
                    vbb.add((float) y * wallsize - wallsize/2 );
                    vbb.add((float) -wallsize/2 );

                    vbb.add((float) x * wallsize - wallsize/2 );
                    vbb.add((float) y * wallsize + wallsize/2 );
                    vbb.add((float) -wallsize/2 );

                    vbb.add((float) x * wallsize + wallsize/2 );
                    vbb.add((float) y * wallsize + wallsize/2 );
                    vbb.add((float) -wallsize/2 );
                    // floor
                    vbb.add((float) x * wallsize + wallsize/2 );
                    vbb.add((float) y * wallsize - wallsize/2 );
                    vbb.add((float) +wallsize/2 );

                    vbb.add((float) x * wallsize - wallsize/2 );
                    vbb.add((float) y * wallsize - wallsize/2 );
                    vbb.add((float) +wallsize/2 );

                    vbb.add((float) x * wallsize + wallsize/2 );
                    vbb.add((float) y * wallsize + wallsize/2 );
                    vbb.add((float) +wallsize/2 );

                    vbb.add((float) x * wallsize - wallsize/2 );
                    vbb.add((float) y * wallsize + wallsize/2 );
                    vbb.add((float) +wallsize/2 );
                    }

                if (cur_block.haveLeft())
                    {
                    /*
                    tex.add(0.0f); tex.add(0.0f);
                    tex.add(1.0f); tex.add(0.0f);
                    tex.add(0.0f); tex.add(1.0f);
                    tex.add(1.0f); tex.add(1.0f);
                    */
                    for(int i = 0; i < 8; i++) { tex.add(wallcrds[i]); }

                    //ind.add(0+counter); ind.add(1+counter); ind.add(3+counter);
                    //ind.add(3+counter); ind.add(2+counter); ind.add(0+counter);
                    ind.add(0+counter); ind.add(1+counter); ind.add(2+counter);
                    ind.add(1+counter); ind.add(3+counter); ind.add(2+counter);
                    counter+=4;
                    vbb.add((float) x * wallsize - wallsize/2 );
                    vbb.add((float) y * wallsize + wallsize/2 );
                    vbb.add((float) -wallsize/2 );

                    vbb.add((float) x * wallsize - wallsize/2 );
                    vbb.add((float) y * wallsize - wallsize/2 );
                    vbb.add((float) -wallsize/2 );

                    vbb.add((float) x * wallsize - wallsize/2 );
                    vbb.add((float) y * wallsize + wallsize/2 );
                    vbb.add((float) +wallsize/2 );

                    vbb.add((float) x * wallsize - wallsize/2 );
                    vbb.add((float) y * wallsize - wallsize/2 );
                    vbb.add((float) +wallsize/2 );
                    }
                if (cur_block.haveRight())
                    {
                    for(int i = 0; i < 8; i++) { tex.add(wallcrds[i]); }

                    ind.add(0+counter); ind.add(1+counter); ind.add(2+counter);
                    ind.add(1+counter); ind.add(3+counter); ind.add(2+counter);
                    counter+=4;
                    vbb.add((float) x * wallsize + wallsize/2 );
                    vbb.add((float) y * wallsize - wallsize/2 );
                    vbb.add((float) -wallsize/2 );

                    vbb.add((float) x * wallsize + wallsize/2 );
                    vbb.add((float) y * wallsize + wallsize/2 );
                    vbb.add((float) -wallsize/2 );

                    vbb.add((float) x * wallsize + wallsize/2 );
                    vbb.add((float) y * wallsize - wallsize/2 );
                    vbb.add((float) +wallsize/2 );

                    vbb.add((float) x * wallsize + wallsize/2 );
                    vbb.add((float) y * wallsize + wallsize/2 );
                    vbb.add((float) +wallsize/2 );
                    }
                if (cur_block.haveFront())
                    {
                    for(int i = 0; i < 8; i++) { tex.add(wallcrds[i]); }

                    ind.add(0+counter); ind.add(1+counter); ind.add(2+counter);
                    ind.add(1+counter); ind.add(3+counter); ind.add(2+counter);
                    counter+=4;
                    vbb.add((float) x * wallsize - wallsize/2 );
                    vbb.add((float) y * wallsize - wallsize/2 );
                    vbb.add((float) -wallsize/2 );

                    vbb.add((float) x * wallsize + wallsize/2 );
                    vbb.add((float) y * wallsize - wallsize/2 );
                    vbb.add((float) -wallsize/2 );

                    vbb.add((float) x * wallsize - wallsize/2 );
                    vbb.add((float) y * wallsize - wallsize/2 );
                    vbb.add((float) +wallsize/2 );

                    vbb.add((float) x * wallsize + wallsize/2 );
                    vbb.add((float) y * wallsize - wallsize/2 );
                    vbb.add((float) +wallsize/2 );
                    }
                if (cur_block.haveBack())
                    {
                    for(int i = 0; i < 8; i++) { tex.add(wallcrds[i]); }

                    ind.add(0+counter); ind.add(1+counter); ind.add(2+counter);
                    ind.add(1+counter); ind.add(3+counter); ind.add(2+counter);
                    counter+=4;
                    vbb.add((float) x * wallsize + wallsize/2 );
                    vbb.add((float) y * wallsize + wallsize/2 );
                    vbb.add((float) -wallsize/2 );

                    vbb.add((float) x * wallsize - wallsize/2 );
                    vbb.add((float) y * wallsize + wallsize/2 );
                    vbb.add((float) -wallsize/2 );

                    vbb.add((float) x * wallsize + wallsize/2 );
                    vbb.add((float) y * wallsize + wallsize/2 );
                    vbb.add((float) +wallsize/2 );

                    vbb.add((float) x * wallsize - wallsize/2 );
                    vbb.add((float) y * wallsize + wallsize/2 );
                    vbb.add((float) +wallsize/2 );
                    }
                }



        //String vbbs = "";


        float[] vbbArray = new float[vbb.size()];
        float[] texArray = new float[tex.size()];
        short[] indArray = new short[ind.size()];
        int i = 0;
        for (Float f : vbb) { vbbArray[i++] = f; /*vbbs+=f.toString()+" ";*/ }
        i = 0;
        for (Float f : tex) { texArray[i++] = f; }
        i = 0;
        for (Integer f : ind) { indArray[i++] = (short)(int)f; }
        //Log.d("maze3d", "VBB: " + vbbs);
        setIndices(indArray);
        setVertices(vbbArray);
        setTextureCoordinates(texArray);
        }
    }

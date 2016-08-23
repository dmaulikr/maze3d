package georgsh.maze3d.game;

import java.util.Vector;

/**
 * Created by yura on 15.07.16.
 */
public class BlockMoveMesh extends Mesh
    {
    private float wallsize;
    public BlockMoveMesh(float size)
        {
        wallsize = size;
        genVertexArray();
        //genSimple();
        }

    public void genVertexArray()
        {
        Vector<Float> vbb = new Vector<Float>();
        Vector<Float> tex = new Vector<Float>();
        float[] wallcrds = new float[] {0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f};
        float[] floorcrds = new float[] {0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f};
        float[] ceilcrds = new float[] {0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f};
        for(int i = 0; i < 8; i++) { wallcrds[i] = wallcrds[i]/2.0f; }
        for(int i = 0; i < 8; i++) { floorcrds[i] = wallcrds[i]; if(i%2 == 0) {floorcrds[i]+=0.5f;} } // x-shift
        for(int i = 0; i < 8; i++) { ceilcrds[i]  = wallcrds[i]; if(i%2 == 1) {ceilcrds[i] +=0.5f;} } // y-shift
        //float[] wallcrds = new float[] {0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f};
        Vector<Integer> ind = new Vector<Integer>();
        int counter = 0;
                    for(int i = 0; i < 8; i++) { tex.add(ceilcrds[i]); }
                    for(int i = 0; i < 8; i++) { tex.add(floorcrds[i]); }

                    ind.add(0+counter); ind.add(1+counter); ind.add(2+counter);
                    ind.add(1+counter); ind.add(3+counter); ind.add(2+counter);
                    counter+=4;
                    ind.add(0+counter); ind.add(1+counter); ind.add(2+counter);
                    ind.add(1+counter); ind.add(3+counter); ind.add(2+counter);
                    counter+=4;
                    // ceil
                    vbb.add( - wallsize/2 );
                    vbb.add( - wallsize/2 );
                    vbb.add( + wallsize/2 );

                    vbb.add( + wallsize/2 );
                    vbb.add( - wallsize/2 );
                    vbb.add( + wallsize/2 );

                    vbb.add( - wallsize/2 );
                    vbb.add( + wallsize/2 );
                    vbb.add( + wallsize/2 );

                    vbb.add( + wallsize/2 );
                    vbb.add( + wallsize/2 );
                    vbb.add( + wallsize/2 );
                    // floor
                    vbb.add( + wallsize/2 );
                    vbb.add( - wallsize/2 );
                    vbb.add( - wallsize/2 );

                    vbb.add( - wallsize/2 );
                    vbb.add( - wallsize/2 );
                    vbb.add( - wallsize/2 );

                    vbb.add( + wallsize/2 );
                    vbb.add( + wallsize/2 );
                    vbb.add( - wallsize/2 );

                    vbb.add( - wallsize/2 );
                    vbb.add( + wallsize/2 );
                    vbb.add( - wallsize/2 );


                    for(int i = 0; i < 8; i++) { tex.add(wallcrds[i]); }
                    ind.add(0+counter); ind.add(1+counter); ind.add(2+counter);
                    ind.add(1+counter); ind.add(3+counter); ind.add(2+counter);
                    counter+=4;
                    vbb.add( - wallsize/2 );
                    vbb.add( + wallsize/2 );
                    vbb.add( - wallsize/2 );

                    vbb.add( - wallsize/2 );
                    vbb.add( - wallsize/2 );
                    vbb.add( - wallsize/2 );

                    vbb.add( - wallsize/2 );
                    vbb.add( + wallsize/2 );
                    vbb.add( + wallsize/2 );

                    vbb.add( - wallsize/2 );
                    vbb.add( - wallsize/2 );
                    vbb.add( + wallsize/2 );


                    for(int i = 0; i < 8; i++) { tex.add(wallcrds[i]); }

                    ind.add(0+counter); ind.add(1+counter); ind.add(2+counter);
                    ind.add(1+counter); ind.add(3+counter); ind.add(2+counter);
                    counter+=4;
                    vbb.add( + wallsize/2 );
                    vbb.add( - wallsize/2 );
                    vbb.add( - wallsize/2 );

                    vbb.add( + wallsize/2 );
                    vbb.add( + wallsize/2 );
                    vbb.add( - wallsize/2 );

                    vbb.add( + wallsize/2 );
                    vbb.add( - wallsize/2 );
                    vbb.add( + wallsize/2 );

                    vbb.add( + wallsize/2 );
                    vbb.add( + wallsize/2 );
                    vbb.add( + wallsize/2 );



                    for(int i = 0; i < 8; i++) { tex.add(wallcrds[i]); }

                    ind.add(0+counter); ind.add(1+counter); ind.add(2+counter);
                    ind.add(1+counter); ind.add(3+counter); ind.add(2+counter);
                    counter+=4;
                    vbb.add( - wallsize/2 );
                    vbb.add( - wallsize/2 );
                    vbb.add( - wallsize/2 );

                    vbb.add( + wallsize/2 );
                    vbb.add( - wallsize/2 );
                    vbb.add( - wallsize/2 );

                    vbb.add( - wallsize/2 );
                    vbb.add( - wallsize/2 );
                    vbb.add( + wallsize/2 );

                    vbb.add( + wallsize/2 );
                    vbb.add( - wallsize/2 );
                    vbb.add( + wallsize/2 );


                    for(int i = 0; i < 8; i++) { tex.add(wallcrds[i]); }

                    ind.add(0+counter); ind.add(1+counter); ind.add(2+counter);
                    ind.add(1+counter); ind.add(3+counter); ind.add(2+counter);
                    counter+=4;
                    vbb.add( + wallsize/2 );
                    vbb.add( + wallsize/2 );
                    vbb.add( - wallsize/2 );

                    vbb.add( - wallsize/2 );
                    vbb.add( + wallsize/2 );
                    vbb.add( - wallsize/2 );

                    vbb.add( + wallsize/2 );
                    vbb.add( + wallsize/2 );
                    vbb.add( + wallsize/2 );

                    vbb.add( - wallsize/2 );
                    vbb.add( + wallsize/2 );
                    vbb.add( + wallsize/2 );


        float[] vbbArray = new float[vbb.size()];
        float[] texArray = new float[tex.size()];
        short[] indArray = new short[ind.size()];
        int i = 0;
        for (Float f : vbb) { vbbArray[i++] = f; }
        i = 0;
        for (Float f : tex) { texArray[i++] = f; }
        i = 0;
        for (Integer f : ind) { indArray[i++] = (short)(int)f; }
        setIndices(indArray);
        setVertices(vbbArray);
        setTextureCoordinates(texArray);
        }
    }

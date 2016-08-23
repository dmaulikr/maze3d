package georgsh.maze3d.game;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES11Ext;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;
import android.view.MotionEvent;
import android.opengl.GLSurfaceView.Renderer;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;


public class MyGlSurface extends GLSurfaceView implements Renderer
    {
    MazeObj maze;
    LeverObj leverpad;
    Block[][] mazeobj;
    Bitmap wallbitmap;
    MapLoader loader;

    //SimplePlane test = new SimplePlane(1);
    Vector<LeverLogic> levers;
    Vector<LeverWallsSystem> leversystems;

    MarkRender markrender;
    CoinRender coinrender;

    TextRenderer textrender;
    BlocksMoveRender animblocksrender;

    class TouchEvent
        {
        float x, y;
        TouchEvent(float x, float y)
            {
            this.x = x;
            this.y = y;
            }
        }
    Vector<TouchEvent> touch_buffer = new Vector<TouchEvent>();
    TouchController controller;
    Context appcont;
    AssetManager mgr;

    String glver = "1.0";
    boolean OES_matrix_get = false;
    boolean surface_ready = false;
    // ===================================
    final Group root;

    // player position, camera view, and speed

    float xsp = 0;
    float ysp = 0;

    float rotxsp = 0;
    float rotysp = 0;

    float rotx = 90;
    float roty = 0;
    float rotz = -90;

    float posx = 10.0f / 4.0f;
    float posy = 15.0f / 4.0f;
    float posz = 0.0f;
    // ===================================


    float scrw = 0;
    float scrh = 0;
    int curr_level = 0;
    char[][] maze2d;
    float blocksize = 1f;

    int finish_x = 0;
    int finish_y = 0;

    void findFinishPos()
        {
        for (int y = 0; y < maze2d.length; y++)
            {
            for (int x = 0; x < maze2d[y].length; x++)
                {
                if (maze2d[y][x] == 'F')
                    {
                    finish_x = x;
                    finish_y = y;
                    return;
                    }
                }
            }
        }

    void rotMov(float dx, float dy)
        {
        //Log.d("maze3d", "Rotate (" + rotx + ", " + rotz + ")");
        rotz -= dx;
        if (rotx + dy < 30 + 90 && rotx + dy > -30 + 90)
            {
            rotx += dy;
            }
        }


    float BlockTorealX(int x)
        {
        return x * blocksize;
        }

    float BlockTorealY(int y)
        {
        return y * blocksize;
        }

    int RealXToBlock(float x)
        {
        return Math.round(x / blocksize);
        }

    int RealYToBlock(float y)
        {
        return Math.round(y / blocksize);
        }

    boolean movePlayerToStart()
        {
        for (int y = 0; y < maze2d.length; y++)
            {
            int blockposx = -1;
            for (int x = 0; x < maze2d[y].length; x++)
                {
                if (maze2d[y][x] == 'S')
                    {
                    blockposx = x;
                    break;
                    }
                }
            if (blockposx != -1)
                {
                Log.d("maze3d", "Set pos start: " + BlockTorealX(blockposx) + ", " + BlockTorealY(y));
                posx = BlockTorealX(blockposx);
                posy = BlockTorealY(y);
                return true;
                }
            }
        return false;
        }

    void initMazeBlocks()
        {
        Log.d("maze3d", "InitMazeBlocks ("+maze2d.length+", "+maze2d[0].length+")");
        mazeobj = new Block[maze2d.length][];
        for (int y = 0; y < maze2d.length; y++)
            {
            mazeobj[y] = new Block[maze2d[y].length];
            for (int x = 0; x < maze2d[y].length; x++)
                {
                mazeobj[y][x] = new Block();
                }
            }
        }

    void UpdateLeversVisibility()
        {
        for(LeverLogic lever: levers)
            {
            if(maze2d[lever.wally][lever.wallx] == '#')
                {
                lever.setVisible();
                }
            else
                {
                lever.setInvisible();
                }
            }
        }

    void ReloadMazeBlocks()
        {
        Log.d("maze3d", "ReloadMazeBlocks");
        for (int y = 0; y < maze2d.length; y++)
            {
            //Log.d("maze3d", new String(maze2d[y]));
            for (int x = 0; x < maze2d[y].length; x++)
                {
                char block = maze2d[y][x];
                mazeobj[y][x].ResetFaces();
                if (block == '#')
                    {
                    mazeobj[y][x].setTopFace();
                    if (y > 0 && maze2d[y - 1][x] != '#')
                        {
                        mazeobj[y][x].setFrontFace();
                        }
                    if (x > 0 && maze2d[y][x - 1] != '#')
                        {
                        mazeobj[y][x].setLeftFace();
                        }
                    if (y < maze2d.length - 1 && maze2d[y + 1][x] != '#')
                        {
                        mazeobj[y][x].setBackFace();
                        }
                    if (x < maze2d[y].length - 1 && maze2d[y][x + 1] != '#')
                        {
                        mazeobj[y][x].setRightFace();
                        }
                    }
                if(block == 'F')
                    {
                    mazeobj[y][x].setFinish();
                    }
                }
            }
        }

    public void addMesh(Mesh mesh)
        {
        root.add(mesh);
        }
    boolean isSolid(char block)
        {
        return block == '#' || block == 'a';
        }
    boolean SolidBlockAtCoords(float x, float y, float delta)
        {
        int blockx = RealXToBlock(x);
        int blocky = RealYToBlock(y);
        if (blockx < 0)
            {
            return true;
            }
        if (blocky < 0)
            {
            return true;
            }
        if (blocky >= maze2d.length)
            {
            return true;
            }
        if (blockx >= maze2d[blocky].length)
            {
            return true;
            }
        if (isSolid(maze2d[blocky][blockx]))
            {
            return true;
            }
        float localx = x - BlockTorealX(blockx);
        float localy = y - BlockTorealY(blocky);
        int deltablockx = 0;
        int deltablocky = 0;
        if (localx < -blocksize / 2f + delta)
            {
            if (blockx == 0 || isSolid(maze2d[blocky][blockx - 1]))
                {
                //Log.d("maze3d", "collision x(<delta),y: ("+localx+", "+localy+")");
                return true;
                }
            deltablockx = -1;
            }
        if (localy < -blocksize / 2f + delta)
            {
            if (blocky == 0 || isSolid(maze2d[blocky - 1][blockx]))
                {
                //Log.d("maze3d", "collision x,y(<delta): ("+localx+", "+localy+")");
                return true;
                }
            deltablocky = -1;
            }
        if (localx > blocksize / 2f - delta)
            {
            if (blockx == maze2d[blocky].length - 1 ||
                    isSolid(maze2d[blocky][blockx + 1]))
                {
                //Log.d("maze3d", "collision x(>delta),y: ("+localx+", "+localy+")");
                return true;
                }
            deltablockx = 1;
            }
        if (localy > blocksize / 2f - delta)
            {
            if (blocky == maze2d.length - 1 ||
                    isSolid(maze2d[blocky + 1][blockx]))
                {
                //Log.d("maze3d", "collision x,y(>delta): ("+localx+", "+localy+")");
                return true;
                }
            deltablocky = 1;
            }
        if (isSolid(maze2d[blocky + deltablocky][blockx + deltablockx]))
            {
            //Log.d("maze3d", "collision corner case: ("+localx+", "+localy+")");
            return true;
            }
        return false;
        }

    boolean CheckIfFinish()
        {
        return (RealXToBlock(posx) == finish_x &&
                RealYToBlock(posy) == finish_y);
        }
    public void UpdateWalls()
        {
        Log.d("maze3d", "UpdateWalls");
        ReloadMazeBlocks();
        maze.genVertexArray(mazeobj);
        UpdateLeversVisibility();
        }

    public MyGlSurface(Context context, AssetManager mgr)
        {
        super(context);
        this.mgr = mgr;
        appcont = context;
        controller = new TouchController(this);

        wallbitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.walls);
        Bitmap pad = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.lever);
        Bitmap stick = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.stick);
        Bitmap mark = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.graffity);
        Bitmap coin = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.coin);
        root = new Group();

        animblocksrender = new BlocksMoveRender(wallbitmap, this, blocksize);
        markrender = new MarkRender(this, mark);
        coinrender = new CoinRender(this, coin);
        //test.loadBitmap(mark);
        leverpad = new LeverObj(pad, stick);

        markrender.addMark(0,1,1);

        markrender.addMark(3,1,0);
        markrender.addMark(3,1,1);
        //markrender.addMark(0,3,2);
        markrender.addMark(3,1,3);

        coinrender.addCoin(0,0);
        coinrender.addCoin(1,0);
        coinrender.addCoin(2,0);
        setRenderer(this);
        }

    public void LoadNextLevel()
        {
        curr_level = curr_level%4;
        try
            {
            LoadLevel("Map"+(curr_level+1)+".txt");
            }
        catch (IOException e)
            {
            curr_level++;
            LoadNextLevel();
            Log.w("maze3d", "cannot open Map"+(curr_level+1)+".txt: "+e.getMessage());
            }
        curr_level++;
        }

    public void LoadLevel(String mapfile) throws IOException
        {
        loader = new MapLoader(this, mgr.open(mapfile));

        maze2d = loader.LoadWalls();
        textrender.AddTextNotify(loader.getHeader(), scrw, scrh);
        Log.d("maze3d", "Start init blocks");
        initMazeBlocks();
        ReloadMazeBlocks();
        Log.d("maze3d", "Blocks inited");
        levers = new Vector<LeverLogic>();
        leversystems = loader.LoadLevers();
        Log.d("maze3d", "Levers loaded");
        UpdateLeversVisibility();

        root.clear();
        maze = new MazeObj(blocksize, mazeobj);
        maze.loadBitmap(wallbitmap);
        //MazeMesh maze = new MazeMesh(maze2d, plane, blocksize);
        addMesh(maze);
        findFinishPos();
        movePlayerToStart();
        }

    public void AddLever(int x, int y, LeverLogic lever, int z_direction)
        {
        Log.d("maze3d", "Add Lever(glsurface)");
        levers.add(lever);
        switch (z_direction)
            {
            case 0:
                mazeobj[y][x].setRightLever(lever);
                break;
            case 1:
                mazeobj[y][x].setFrontLever(lever);
                break;
            case 2:
                mazeobj[y][x].setLeftLever(lever);
                break;
            case 3:
                mazeobj[y][x].setBackLever(lever);
                break;
            }
        }

    public void InitTextures(GL10 gl)
        {
        Log.d("maze3d", "Init Textures");
        leverpad.loadGLTexture(gl);
        maze.loadGLTexture(gl);
        animblocksrender.loadGLTexture(gl);
        //fontrender.loadGLTexture(gl);
        }

    public float[] getScreenCoordsOES(float px,float py, float pz, GL10 gl)
        {
        if(!OES_matrix_get)
            {
            return new float[]{-1,-1,-1};
            }
        int view[] = new int[]{0,0,(int)scrw,(int)scrh};
        float screen_coords[] = new float[3];
        float [] modelMatrix = new float[16];
        float [] projMatrix = new float[16];

        int [] modelMatrix_i = new int[16];
        int [] projMatrix_i = new int[16];
        gl.glGetIntegerv(GLES11Ext.GL_MODELVIEW_MATRIX_FLOAT_AS_INT_BITS_OES, modelMatrix_i, 0);
        //if(gl.glGetError() != 0) { Log.d("maze3d", "gl matrix err1: "+gl.glGetError()); }
        gl.glGetIntegerv(GLES11Ext.GL_PROJECTION_MATRIX_FLOAT_AS_INT_BITS_OES, projMatrix_i, 0);

        for(int i = 0; i < 16; i++)
            {
            modelMatrix[i] = Float.intBitsToFloat(modelMatrix_i[i]);
            projMatrix[i] = Float.intBitsToFloat(projMatrix_i[i]);
            }

        GLU.gluProject(px,py,pz,modelMatrix,0,projMatrix,0,view,0,screen_coords,0);

        return screen_coords;
        }
    public float[] getScreenCoords(float px,float py, float pz, GL10 gl)
        {
        if(glver.equals("1.0") || !glver.equals("1.1"))
            {
            return getScreenCoordsOES(px, py, pz, gl);
            }
        GL11 gl11 = (GL11)gl; // not safe
        int view[] = new int[]{0,0,(int)scrw,(int)scrh};
        float screen_coords[] = new float[3];
        float [] modelMatrix = new float[16];
        float [] projMatrix = new float[16];

        gl11.glGetFloatv(GL11.GL_MODELVIEW_MATRIX, modelMatrix, 0);
        //if(gl.glGetError() != 0) { Log.d("maze3d", "gl matrix err1: "+gl.glGetError()); }
        gl11.glGetFloatv(GL11.GL_PROJECTION_MATRIX, projMatrix, 0);
        //if(gl.glGetError() != 0) { Log.d("maze3d", "gl matrix err2: "+gl.glGetError()); }
        gl11.glGetIntegerv(GL11.GL_VIEWPORT, view, 0);
        //if(gl.glGetError() != 0) { Log.d("maze3d", "gl matrix err2: "+gl.glGetError()); }

        GLU.gluProject(px,py,pz,modelMatrix,0,projMatrix,0,view,0,screen_coords,0);

        return screen_coords;
        }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height)
        {
            Log.d("maze3d", "Surface Change "+width+"x"+height);
            scrw = width;
            scrh = height;

        if(!surface_ready)
            {
            surface_ready = true;
            textrender = new TextRenderer(256, mgr);
            LoadNextLevel();
            InitTextures(gl);
            }

            gl.glViewport(0, 0, width, height);
            Log.d("maze3d", "err1: " + gl.glGetError());
            gl.glMatrixMode(GL10.GL_PROJECTION);
            Log.d("maze3d", "err2: " + gl.glGetError());
            gl.glLoadIdentity();
            Log.d("maze3d", "err2: " + gl.glGetError());
            GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f,
                    1000.0f);
            Log.d("maze3d", "err3: " + gl.glGetError());
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            Log.d("maze3d", "err4: " + gl.glGetError());
            gl.glLoadIdentity();
            Log.d("maze3d", "err5: " + gl.glGetError());

        }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config)
        {
        Log.d("maze3d", "Create Surface");
        Log.d("maze3d", "opengl1.0 extensions:"+gl.glGetString(GL10.GL_EXTENSIONS));
        // something like
        glver = gl.glGetString(GL10.GL_VERSION).split(" ")[2]; // OpemGL ES-CM 1.1
        Log.d("maze3d","glver: "+glver);                       // OpenGL ES 1.0
        if(gl.glGetString(GL10.GL_EXTENSIONS).toLowerCase().contains("gl_oes_matrix_get"))
            {
            OES_matrix_get = true;
            }
        Log.d("maze3d","OES_matrix_get: "+OES_matrix_get);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Log.d("maze3d", "err1: " + gl.glGetError());
        gl.glShadeModel(GL10.GL_FLAT);
        Log.d("maze3d", "err2: " + gl.glGetError());
        gl.glClearDepthf(1.0f);
        Log.d("maze3d", "err3: " + gl.glGetError());
        gl.glEnable(GL10.GL_DEPTH_TEST);
        Log.d("maze3d", "err4: " + gl.glGetError());
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        Log.d("maze3d", "err5: " + gl.glGetError());
        gl.glDepthFunc(GL10.GL_LEQUAL);
        Log.d("maze3d", "err6: " + gl.glGetError());
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
        Log.d("maze3d", "err7: " + gl.glGetError());
        }
    public void DrawLevers(GL10 gl)
        {
        leverpad.drawAllPads(gl, levers);
        leverpad.drawAllSticks(gl, levers);
        for(LeverWallsSystem leversandwalls: leversystems)
            {
            leversandwalls.draw(gl, animblocksrender);
            }
        }

    void UpdateScene(GL10 gl)
        {
        for (LeverWallsSystem leversystem : leversystems)
            {
            leversystem.update();
            }

        float absdelta = blocksize * 0.2f;

        float nextstepx = posx + xsp;
        if (!SolidBlockAtCoords(nextstepx, posy, absdelta))
            {
            posx += xsp;
            }
        float nextstepy = posy + ysp;
        if (!SolidBlockAtCoords(posx, nextstepy, absdelta))
            {
            posy += ysp;
            }
        rotMov(rotxsp, rotysp);
        }
    void AddTouchEvent(float x, float y)
        {
        Log.d("maze3d", "AddTouchEvent");
        touch_buffer.add(new TouchEvent(x,y));
        }
    void PerformTouchEvents(GL10 gl)
        {
        Log.d("maze3d", "PerformTouchEvent");
        Iterator<TouchEvent> touches = touch_buffer.iterator();
        while(touches.hasNext())
            {
            TouchEvent touch = touches.next();
            touchLever(gl, touch.x, touch.y);
            touches.remove();
            }
        }

    boolean touchLever(GL10 gl, float x, float y)
        {
        Log.d("maze3d", "TouchLever");
        int blockx = RealXToBlock(posx);
        int blocky = RealYToBlock(posy);
        int shiftx[] = {0,-1,0,+1};
        int shifty[] = {-1,0,+1,0};
        for(int i = 0; i < 4; i++)
            {
            int curx = blockx+shiftx[i];
            int cury = blocky+shifty[i];
            if(curx < 0 || cury < 0) { continue; }
            if(cury >= mazeobj.length || curx >= mazeobj[cury].length) { continue; }

            LeverLogic lever = mazeobj[cury][curx].getLeverByShift(shiftx[i], shifty[i]);
            if(lever == null) { continue; }
            float lever2dcoords[] = getScreenCoords(lever.posx, lever.posy, 0, gl);
            float lev2d_x = lever2dcoords[0];
            float lev2d_y = scrh - lever2dcoords[1];
            Log.d("maze3d", "lever coords: "+lev2d_x+", "+lev2d_y);
            float distance = (float)Math.sqrt((lev2d_x-x)*(lev2d_x-x)+(lev2d_y-y)*(lev2d_y-y));
            Log.d("maze3d", "distance to lever = "+ distance);
            if (distance < scrh/5)
                {
                lever.SwitchAllSystem();
                return true;
                }
            }
        return false;
        }

    @Override
    synchronized public void onDrawFrame(GL10 gl)
        {
        UpdateScene(gl);
        if(CheckIfFinish()) { LoadNextLevel(); return; }

        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();


        gl.glRotatef(rotx, 1, 0, 0);
        gl.glRotatef(roty, 0, 1, 0);
        gl.glRotatef(rotz, 0, 0, 1);

        gl.glTranslatef(-posx, -posy, -posz);
        root.draw(gl);
        DrawLevers(gl);
        markrender.drawAllMarks(gl);
        coinrender.DrawAll(gl);
        //test.draw(gl);
        if(!touch_buffer.isEmpty())
            {
            PerformTouchEvents(gl);
            }

        // Draw 2d stuff
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glPushMatrix();
        gl.glLoadIdentity();
        gl.glOrthof(0f,scrw,scrh,0f,-1f,10.0f);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glDisable(GL10.GL_CULL_FACE);

        gl.glClear(GL10.GL_DEPTH_BUFFER_BIT);

        textrender.draw(gl);

        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glPopMatrix();
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        }

    @Override
    synchronized public boolean onTouchEvent(MotionEvent event)
        {
        return controller.onTouchEvent(event);
        }
    }

package georgsh.maze3d.game;

import android.util.Log;

/**
 * Created by yura on 15.07.16.
 */
public class BlockAnimLogic
    {
    int posx;
    int posy;
    float curz = 0;
    float sz, fz;
    float step = 0;
    boolean closewall;
    MyGlSurface game;

    BlockAnimLogic(int x, int y, MyGlSurface game)
        {
        Log.d("maze3d", "Initblockmaze: "+x+", "+y+"; z(from-to): "+sz+", "+fz);
        this.game = game;
        posx = x;
        posy = y;
        this.sz = 0;
        this.fz = game.blocksize;
        this.closewall = false;
        }
    private void ChangeDirection()
        {
        closewall = !closewall;
        step = 50-step;

        float tmp = sz;
        sz = fz;
        fz = tmp;
        }
    boolean isWallBlocked()
        {
        return game.maze2d[posy][posx] == 'a';
        }
    void Switch()
        {
        Log.d("maze3d", "StartAnimation");
        char wallstate = game.maze2d[posy][posx];
        if (wallstate == 'a')
            {
            ChangeDirection();
            }
        else if (wallstate == '_')
            {
            if (!closewall) { ChangeDirection(); step = 0; }
            }
        else if (wallstate == '#')
            {
            if (closewall) { ChangeDirection(); step = 0; }
            }
        game.maze2d[posy][posx] = 'a';
        }
    void EndAnim(MyGlSurface game)
        {
        Log.d("maze3d", "EndAnimation");
        if (closewall)
            {
            Log.d("maze3d", "set wall back");
            game.maze2d[posy][posx] = '#';
            }
        else
            {
            Log.d("maze3d", "disable wall back");
            game.maze2d[posy][posx] = '_';
            }
        }

    boolean Step() // true if animation still do
        {
        //Log.d("maze3d", "Step: "+curz+" of "+fz);
        step+=1;
        curz=sz+(fz-sz)*step/50f;
        if(step >= 50) { curz = fz; return false; }
        return true;
        }
    }

package georgsh.maze3d.game;

/**
 * Created by yura on 23.08.16.
 */
public class Crate
    {
    int x;
    int y;

    float posx;
    float posy;
    int curangle = 0;

    Crate(int x, int y, MyGlSurface game)
        {
        this.x = x;
        this.y = y;
        posx = game.BlockTorealX(x);
        posy = game.BlockTorealY(y);
        }

    boolean update()
        {
        curangle+=2;
        if(curangle >= 360) { curangle-=360; }
        return true;
        }
    }

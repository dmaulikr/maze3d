package georgsh.maze3d.game;

import android.util.Log;

/**
 * Created by yura on 22.08.16.
 */
public class MarkObject
    {
    int wallx;
    int wally;
    int z_direction;

    float posx = 0;
    float posy = 0;
    float zrot = 0;

    MyGlSurface game;

    MarkObject(int wallx, int wally, int z_direction, MyGlSurface game)
        {
        this.game = game;
        this.wallx = wallx;
        this.wally = wally;
        this.z_direction = z_direction;
        setAbsoluteCoords();
        }

    void setAbsoluteCoords()
        {
        float realx = game.BlockTorealX(wallx);
        float realy = game.BlockTorealY(wally);
        float halfblock = game.blocksize*0.51f;

        switch (z_direction)
            {
            case 0: // right
                {
                posx = realx+halfblock;
                posy = realy;
                zrot = 0;
                break;
                }
            case 1: // top
                {
                posx = realx;
                posy = realy-halfblock;
                zrot = -90;
                break;
                }
            case 2: // left
                {
                posx = realx-halfblock;
                posy = realy;
                zrot = 180;
                break;
                }
            case 3: // bottom
                {
                posx = realx;
                posy = realy+halfblock;
                zrot = 90;
                break;
                }
            default:
                {
                Log.e("maze3d", "invalid lever direction parameter: " + z_direction);
                throw new IllegalStateException();
                }
            }
        }
    }

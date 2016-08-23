package georgsh.maze3d.game;

import android.util.Log;

/**
 * Created by yura on 07.07.16.
 */
public class LeverLogic
    {
    float posx = 0;
    float posy = 0;
    float zrot = 0;
    float stickangle = -45f;
    boolean changing = false;
    boolean state = true;
    LeverWallsSystem subsystem;
    boolean visible = true;

    int wallx;
    int wally;
    int z_direction;
    boolean isVisible() { return visible; }
    void setVisible()   { visible = true; }
    void setInvisible() { visible = false; }

    LeverLogic(int wallx, int wally, int z_direction, LeverWallsSystem subsystem)
        {
        this.wallx = wallx;
        this.wally = wally;
        this.z_direction = z_direction;
        this.subsystem = subsystem;
        setAbsoluteCoords();
        }
    void setAbsoluteCoords()
        {
        float realx = subsystem.game.BlockTorealX(wallx);
        float realy = subsystem.game.BlockTorealY(wally);
        float halfblock = subsystem.game.blocksize/2;

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
    void SwitchAllSystem()
        {
        if(!isVisible()) { return; }
        subsystem.Switch();
        }
    void SwitchOnlyThis()
        {
        state = !state;
        changing = true;
        }
    boolean update()
        {
        if(changing)
            {
            if (state) { stickangle-=2f; }
            else { stickangle += 2f; }
            if (stickangle < -45f) { stickangle = -45f; changing = false; }
            if (stickangle > 45f) { stickangle = 45f; changing = false; }
            }
        return changing;
        }
    }

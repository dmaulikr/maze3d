package georgsh.maze3d.game;

import android.util.Log;
import android.util.Pair;

import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by yura on 14.08.16.
 */
public class LeverWallsSystem
    {
    Vector<LeverLogic> levers = new Vector<LeverLogic>();
    Vector<BlockAnimLogic> walls = new Vector<BlockAnimLogic>();
    MyGlSurface game;
    boolean isanimating = false;
    public LeverWallsSystem(MyGlSurface game)
        {
        this.game = game;
        }

    public void AddLever(int x, int y, int z_direction)
        {
        Log.d("maze3d", "Add Lever");
        LeverLogic newlever = new LeverLogic(x, y, z_direction, this);
        game.AddLever(x,y,newlever,z_direction);
        levers.add(newlever);
        }
    public void AddWall(int x, int y)
        {
        Log.d("maze3d", "Add Wall");
        float zstart = 0;
        float zfinish = game.blocksize;
        boolean closewall = false;
        if (!game.mazeobj[y][x].isEmpty())
            {
            zstart = game.blocksize;
            zfinish = 0;
            closewall = true;
            }
        walls.add(new BlockAnimLogic(x,y,game));
        }

    boolean isWallsBlocked() // by over levers-walls system intersection
        {
        if(isanimating) { return false; }
        for(BlockAnimLogic blockanimation: walls)
            {
            if(blockanimation.isWallBlocked()) { return true; }
            }
        return false;
        }

    public void Switch()
        {
        Log.d("maze3d", "Switch system");
        if (isWallsBlocked()) { Log.d("maze3d", "can't switch while other animation play"); return; }
        isanimating = true;
        for(LeverLogic lever: levers)
            {
            lever.SwitchOnlyThis();
            }
        for(BlockAnimLogic blockanimation: walls)
            {
            blockanimation.Switch();
            }
        game.UpdateWalls();
        }
    // TODO draw-update behavior
    public void draw(GL10 gl, BlocksMoveRender blockrender)
        {
        if (isanimating)
            {
            Log.d("maze3d", "animating walls");
            blockrender.drawAllBlocks(gl, walls);
            }
        }
    public boolean update()
        {
        boolean result = false;
        result = UpdateWalls() || result;
        result = UpdateLevers() || result;
        return result;
        }

    private boolean UpdateWalls()
        {
        if(!isanimating) { return false; }
        boolean result = false;

        for(BlockAnimLogic blockanimation: walls)
            {
            result = blockanimation.Step() || result;
            }
        if (!result)
            {
            EndWallsAnimation();
            }
        return result;
        }
    private void EndWallsAnimation()
        {
        Log.d("maze3d", "End walls animation");
        for(BlockAnimLogic blockanimation: walls)
            {
            blockanimation.EndAnim(game);
            }
        game.UpdateWalls();
        isanimating = false;
        }
    private boolean UpdateLevers()
        {
        boolean result = false;
        for(LeverLogic lever: levers)
            {
            result = lever.update() || result;
            }
        return result;
        }
    }

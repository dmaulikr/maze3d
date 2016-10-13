package georgsh.maze3d.game;

import android.graphics.Bitmap;

import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by yura on 23.08.16.
 */
public class CrateRender extends EntityBatch
    {
    static final float cratesize = 0.3f;
    CrateRender(MyGlSurface game, Bitmap texture)
        {
        super(game, new CrateMesh(cratesize, cratesize, cratesize, texture), texture);
        }
    void addCrate(int x, int y)
        {
        addEntity(new Crate(x,y,game));
        }
    }

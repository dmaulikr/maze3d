package georgsh.maze3d.game;

import android.graphics.Bitmap;

import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by yura on 23.08.16.
 */
public class CoinRender extends EntityBatch
    {
    static final float coinsize = 0.2f;

    CoinRender(MyGlSurface game, Bitmap texture)
        {
        super(game, new BlockMesh(coinsize*0.2f, coinsize, coinsize, texture), texture);
        }

    void addCoin(int x, int y)
        {
        addEntity(new Coin(x,y,game));
        }
    }

package georgsh.maze3d.game;

import android.graphics.Bitmap;

import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by yura on 23.08.16.
 */
public class CoinRender
    {
    Vector<Coin> coins = new Vector<Coin>();
    BlockMesh coinmesh;
    static final float coinsize = 0.2f;
    MyGlSurface game;

    CoinRender(MyGlSurface game, Bitmap texture)
        {
        this.game = game;
        coinmesh = new BlockMesh(coinsize*0.2f, coinsize, coinsize, texture);
        }

    void DrawAll(GL10 gl)
        {
        updateAll();
        coinmesh.beginDraw(gl);
        for(Coin coin : coins)
            {
            gl.glPushMatrix();
            gl.glTranslatef(coin.posx, coin.posy, game.blocksize/2-coinsize);
            gl.glRotatef(coin.curangle, 0, 0, 1);
            coinmesh.drawBody(gl);
            gl.glPopMatrix();
            }
        coinmesh.endDraw(gl);
        }
    void updateAll()
        {
        for(Coin coin : coins)
            {
            coin.update();
            }
        }
    void addCoin(int x, int y)
        {
        coins.add(new Coin(x,y,game));
        }
    }

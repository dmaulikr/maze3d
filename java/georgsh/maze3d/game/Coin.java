package georgsh.maze3d.game;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by yura on 23.08.16.
 */
public class Coin implements GameEntity
    {
    int x;
    int y;

    float posx;
    float posy;
    int curangle = 0;

    Coin(int x, int y, MyGlSurface game)
        {
        this.x = x;
        this.y = y;
        posx = game.BlockTorealX(x);
        posy = game.BlockTorealY(y);
        }

    @Override
    public void translate(GL10 gl, MyGlSurface game)
        {
        gl.glTranslatef(posx, posy, game.blocksize/2-CoinRender.coinsize);
        gl.glRotatef(curangle, 0, 0, 1);
        }
    @Override
    public void update()
        {
        curangle+=5;
        if(curangle >= 360) { curangle-=360; }
        }
    }

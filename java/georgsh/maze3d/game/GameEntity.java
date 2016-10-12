package georgsh.maze3d.game;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by yura on 12.10.16.
 */
interface GameEntity
    {
    void translate(GL10 gl, MyGlSurface game);
    void update();
    }

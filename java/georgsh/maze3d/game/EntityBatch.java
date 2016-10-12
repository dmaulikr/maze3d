package georgsh.maze3d.game;

import android.graphics.Bitmap;

import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by yura on 12.10.16.
 */

public class EntityBatch
    {
    private Vector<GameEntity> entities = new Vector<>();
    private Mesh drawmesh;
    MyGlSurface game;

    EntityBatch(MyGlSurface game, Mesh drawmesh, Bitmap texture)
        {
        this.game = game;
        this.drawmesh = drawmesh;
        drawmesh.loadBitmap(texture);
        }
    void DrawAll(GL10 gl)
        {
        updateAll();
        drawmesh.beginDraw(gl);
        for(GameEntity entity : entities)
            {
            gl.glPushMatrix();
            entity.translate(gl);
            drawmesh.drawBody(gl);
            gl.glPopMatrix();
            }
        drawmesh.endDraw(gl);
        }
    void updateAll()
        {
        for(GameEntity entity : entities)
            {
            entity.update();
            }
        }
    }

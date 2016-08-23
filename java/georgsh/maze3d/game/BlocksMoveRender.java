package georgsh.maze3d.game;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by yura on 15.07.16.
 */
public class BlocksMoveRender
    {
    BlockMoveMesh block;
    MyGlSurface game;
    BlocksMoveRender(Bitmap wallstex, MyGlSurface game, float size)
        {
        this.game = game;
        block = new BlockMoveMesh(size);
        block.loadBitmap(wallstex);
        }
    public void drawAllBlocks(GL10 gl, Vector<BlockAnimLogic> logicblocks)
        {
        //Log.d("maze3d", "[render] draw walls animation...");
        block.beginDraw(gl);
        for (BlockAnimLogic logicblock: logicblocks)
            {
            gl.glPushMatrix();
            gl.glTranslatef(game.BlockTorealX(logicblock.posx),
                            game.BlockTorealY(logicblock.posy),logicblock.curz);
            block.drawBody(gl);
            gl.glPopMatrix();
            }
        block.endDraw(gl);
        }
    public void loadGLTexture(GL10 gl)
        {
        block.loadGLTexture(gl);
        }
    }

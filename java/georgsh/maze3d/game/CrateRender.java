package georgsh.maze3d.game;

import android.graphics.Bitmap;

import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by yura on 23.08.16.
 */
public class CrateRender
    {
    Vector<Crate> crates = new Vector<Crate>();
    CrateMesh cratemesh;
    static final float cratesize = 0.3f;
    MyGlSurface game;

    CrateRender(MyGlSurface game, Bitmap texture)
        {
        this.game = game;
        cratemesh = new CrateMesh(cratesize, cratesize, cratesize, texture);
        }

    void DrawAll(GL10 gl)
        {
        updateAll();
        cratemesh.beginDraw(gl);
        for(Crate crate : crates)
            {
            gl.glPushMatrix();
            gl.glTranslatef(crate.posx, crate.posy, game.blocksize/2-cratesize);
            gl.glRotatef(crate.curangle, 0, 0, 1);
            cratemesh.drawBody(gl);
            gl.glPopMatrix();
            }
        cratemesh.endDraw(gl);
        }
    void updateAll()
        {
        for(Crate crate : crates)
            {
            crate.update();
            }
        }
    void addCrate(int x, int y)
        {
        crates.add(new Crate(x,y,game));
        }
    }

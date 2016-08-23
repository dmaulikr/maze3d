package georgsh.maze3d.game;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.opengl.GLUtils;
import android.util.Log;

public class Mesh
    {
    FloatBuffer mVerticesBuffer = null;
    ShortBuffer mIndicesBuffer = null;
    FloatBuffer mTextureBuffer = null;
    int mTextureId = -1;
    Bitmap mBitmap;
    boolean mShouldLoadTexture = false;
    int mNumOfIndices = -1;
    private final float[] mRGBA = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
    private FloatBuffer mColorBuffer = null;

    public void beginDraw(GL10 gl)
        {
        gl.glFrontFace(GL10.GL_CCW);
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glCullFace(GL10.GL_BACK);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVerticesBuffer);
        if (gl.glGetError() != 0)
            {
            Log.d("maze3d", "gl vertices err: " + gl.glGetError());
            }
        gl.glColor4f(mRGBA[0], mRGBA[1], mRGBA[2], mRGBA[3]);
        if (mColorBuffer != null)
            {
            gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
            gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer);
            }

        if (mShouldLoadTexture) { loadGLTexture(gl); }
        if (mTextureId != -1 && mTextureBuffer != null)
            {
            gl.glEnable(GL10.GL_TEXTURE_2D);
            if (gl.glGetError() != 0)
                {
                Log.d("maze3d", "gl tex enable err: " + gl.glGetError());
                }
            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

            gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
            if (gl.glGetError() != 0)
                {
                Log.d("maze3d", "gl tex coord err: " + gl.glGetError());
                }
            gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureId);
            if (gl.glGetError() != 0)
                {
                Log.d("maze3d", "gl bind texture err: " + gl.glGetError());
                }
            }
        }

    public void endDraw(GL10 gl)
        {
        if (gl.glGetError() != 0)
            {
            Log.d("maze3d", "gl draw err: " + gl.glGetError());
            }
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

        if (mTextureId != -1 && mTextureBuffer != null)
            {
            gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
            }

        gl.glDisable(GL10.GL_CULL_FACE);
        }

    public void drawBody(GL10 gl)
        {
        gl.glDrawElements(GL10.GL_TRIANGLES, mNumOfIndices,
                GL10.GL_UNSIGNED_SHORT, mIndicesBuffer);
        }
    public void draw(GL10 gl)
        {
        beginDraw(gl);
        drawBody(gl);
        endDraw(gl);
        }

    protected void setVertices(float[] vertices)
        {
        if(mVerticesBuffer != null) { mVerticesBuffer = null; }
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        mVerticesBuffer = vbb.asFloatBuffer();
        mVerticesBuffer.put(vertices);
        mVerticesBuffer.position(0);
        }

    protected void setIndices(short[] indices)
        {
        if(mIndicesBuffer != null) { mIndicesBuffer = null; }
        ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
        ibb.order(ByteOrder.nativeOrder());
        mIndicesBuffer = ibb.asShortBuffer();
        mIndicesBuffer.put(indices);
        mIndicesBuffer.position(0);
        mNumOfIndices = indices.length;
        }

    protected void setTextureCoordinates(float[] textureCoords)
        {
        if(mTextureBuffer != null) { mTextureBuffer = null; }
        ByteBuffer byteBuf = ByteBuffer
                .allocateDirect(textureCoords.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        mTextureBuffer = byteBuf.asFloatBuffer();
        mTextureBuffer.put(textureCoords);
        mTextureBuffer.position(0);
        }

    protected void setColor(float red, float green, float blue, float alpha)
        {
        mRGBA[0] = red;
        mRGBA[1] = green;
        mRGBA[2] = blue;
        mRGBA[3] = alpha;
        }

    public void loadBitmap(Bitmap bitmap)
        {
        if(bitmap == null) { Log.w("maze3d", "Null bitmap"); }
        this.mBitmap = bitmap;
        mShouldLoadTexture = true;
        }

    public void loadGLTexture(GL10 gl)
        {
        if (!mShouldLoadTexture) { return; }
        int[] textures = new int[1];
        gl.glGenTextures(1, textures, 0);
        if(gl.glGetError() != 0) { Log.d("maze3d", "gl tex err1: "+gl.glGetError()); }
        mTextureId = textures[0];

        gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureId);
        if(gl.glGetError() != 0) { Log.d("maze3d", "gl tex err2: "+gl.glGetError()); }
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
                GL10.GL_NEAREST);
        //        GL10.GL_NEAREST_MIPMAP_LINEAR);
        if(gl.glGetError() != 0) { Log.d("maze3d", "gl tex err3: "+gl.glGetError()); }
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
                GL10.GL_NEAREST);
        if(gl.glGetError() != 0) { Log.d("maze3d", "gl tex err4: "+gl.glGetError()); }
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
                GL10.GL_CLAMP_TO_EDGE);
        if(gl.glGetError() != 0) { Log.d("maze3d", "gl tex err5: "+gl.glGetError()); }
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
                GL10.GL_CLAMP_TO_EDGE);
        if(gl.glGetError() != 0) { Log.d("maze3d", "gl tex err6: "+gl.glGetError()); }
        //gl.glTexImage2D();
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, mBitmap, 0);
        if(gl.glGetError() != 0) { Log.d("maze3d", "gl tex err7: "+gl.glGetError()); }
        mShouldLoadTexture = false;
        }
    }

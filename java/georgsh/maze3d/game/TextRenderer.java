package georgsh.maze3d.game;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.opengl.GLUtils;
import android.util.Log;

import org.w3c.dom.Text;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by yura on 26.07.16.
 */
public class TextRenderer
    {

    private FloatBuffer mVerticesBuffer = null;
    private ShortBuffer mIndicesBuffer = null;
    private FloatBuffer mTextureBuffer;
    private int mNumOfIndices = -1;
    private int mTextureId = -1;
    private boolean mShouldLoadTexture = false;

    static String alphabet =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ"+
                    "abcdefghijklmnopqrstuvwxyz"+
                    " .,;:!?()[]#*-+="+
                        "1234567890";


    AssetManager mgr;
    private Bitmap mBitmap;
    float texsize;
    Map<Character,RectF> symmap = new HashMap<Character, RectF>();
    Vector<TextElement> alltexts = new Vector<TextElement>();


    public TextElement addText(String text, Rect size)
        {
        TextElement newobj = new TextElement(this, text, size);
        alltexts.add(newobj);
        return newobj;
        }
    public void AddTextNotify(String text, float scrw, float scrh)
        {
        Log.d("maze3d", "Add text notify");
        TextElement newobj = new TextElement(this, text, new Rect(0,0,(int)(scrw*0.8),(int)(scrh*0.25))); // TODO
        newobj.setPos(scrw/2, scrh/2);
        alltexts.add(newobj);
        }
    public void removeText(TextElement text)
        {
        alltexts.remove(text);
        }
    public void removeAll()
        {
        alltexts.removeAllElements();
        }

    public void InitFontMap()
        {
        float text_height = 15;
        mBitmap = Bitmap.createBitmap( (int)texsize, (int)texsize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mBitmap);
        mBitmap.eraseColor(Color.TRANSPARENT);

        Paint tpaint = new Paint();
        tpaint.setColor(Color.WHITE);
        tpaint.setTextSize(text_height);
        Typeface textfont = Typeface.createFromAsset(mgr, "fff-forward.regular.ttf");
        tpaint.setTypeface(textfont);

        Rect textbounds = new Rect();
        tpaint.getTextBounds(alphabet, 0, alphabet.length(), textbounds);
        text_height = textbounds.height()+1;

        String curr_text = alphabet;
        float[] measuredWidth = new float[1];
        float cur_height = text_height;

        while(!curr_text.equals(""))
            {
            int symbols = tpaint.breakText(curr_text, true, (int)texsize, measuredWidth);
            String curr_line = curr_text.substring(0, symbols);
            float widths[] = new float[curr_line.length()];
            float curr_x = 0;
            tpaint.getTextWidths(curr_line, widths);
            for (int i = 0; i < symbols; i++)
                {
                symmap.put(curr_line.charAt(i),
                        new RectF(curr_x/texsize, (cur_height-text_height)/texsize,
                                (curr_x+widths[i])/texsize, cur_height/texsize));
                curr_x+=widths[i];
                }

            canvas.drawText(curr_line, 0, cur_height-text_height-textbounds.top, tpaint);
            curr_text = curr_text.substring(symbols);
            cur_height+=text_height;
            }
        mShouldLoadTexture = true;
        }

    public TextRenderer(float size, AssetManager mgr)
        {
        texsize = size;
        this.mgr = mgr;
        float textureCoordinates[] = { 0.0f, 0.0f,
                1.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 1.0f,
        };
        short[] indices = new short[] { 0, 3, 1, 3, 0, 2 };

        float[] vertices = new float[] { -size/2, -size/2, 0.0f,
                size/2, -size/2, 0.0f,
                -size/2,  size/2, 0.0f,
                size/2, size/2, 0.0f };

        setIndices(indices);
        setVertices(vertices);
        setTextureCoordinates(textureCoordinates);
        InitFontMap();
        }
    public void draw(GL10 gl)
        {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_MODULATE);

        if (mShouldLoadTexture)
            {
            loadGLTexture(gl);
            mShouldLoadTexture = false;
            }

        gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureId);
        Iterator<TextElement> texts = alltexts.iterator();
        while(texts.hasNext())
            {
            TextElement text = texts.next();
            gl.glColor4f(text.rgb[0], text.rgb[1], text.rgb[2], text.alpha);
            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, text.mVerticesBuffer);
            gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, text.mTextureBuffer);
            gl.glPushMatrix();

            text.translate(gl);

            gl.glDrawElements(GL10.GL_TRIANGLES, text.mNumOfIndices,
                    GL10.GL_UNSIGNED_SHORT, text.mIndicesBuffer);
            gl.glPopMatrix();

            if(!text.update())
                {
                Log.d("maze3d", "end text render");
                texts.remove();
                }
            }

        gl.glDisable(GL10.GL_TEXTURE_2D);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        }

    private void loadGLTexture(GL10 gl)
        {
        int[] textures = new int[1];
        gl.glGenTextures(1, textures, 0);
        mTextureId = textures[0];

        gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureId);

        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
                GL10.GL_LINEAR);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
                GL10.GL_NEAREST);

        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
                GL10.GL_CLAMP_TO_EDGE);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
                GL10.GL_CLAMP_TO_EDGE);

        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, mBitmap, 0);
        }
    protected void setIndices(short[] indices) {
    ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
    ibb.order(ByteOrder.nativeOrder());
    mIndicesBuffer = ibb.asShortBuffer();
    mIndicesBuffer.put(indices);
    mIndicesBuffer.position(0);
    mNumOfIndices = indices.length;
    }
    protected void setVertices(float[] vertices) {
    ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
    vbb.order(ByteOrder.nativeOrder());
    mVerticesBuffer = vbb.asFloatBuffer();
    mVerticesBuffer.put(vertices);
    mVerticesBuffer.position(0);
    }
    protected void setTextureCoordinates(float[] textureCoords) { // New
    ByteBuffer byteBuf = ByteBuffer
            .allocateDirect(textureCoords.length * 4);
    byteBuf.order(ByteOrder.nativeOrder());
    mTextureBuffer = byteBuf.asFloatBuffer();
    mTextureBuffer.put(textureCoords);
    mTextureBuffer.position(0);
    }

    }

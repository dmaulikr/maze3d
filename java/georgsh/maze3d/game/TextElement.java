package georgsh.maze3d.game;

import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import org.w3c.dom.Text;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by yura on 01.08.16.
 */
public class TextElement
    {
    FloatBuffer mVerticesBuffer = null;
    ShortBuffer mIndicesBuffer = null;
    FloatBuffer mTextureBuffer;
    int mNumOfIndices = -1;
    float alpha = 0;
    float animstep = (float) -Math.PI / 2;
    float rgb[] = {1.0f, 1.0f, 1.0f};
    float width = 0;
    float height = 0;
    float curx = 0;
    float cury = 0;

    public void setPos(float x, float y)
        {
        curx = x;
        cury = y;
        }

    public void translate(GL10 gl)
        {
        gl.glTranslatef(curx, cury, 0);
        gl.glTranslatef(-width / 2, -height / 2, 0);
        }

    public boolean update()
        {
        alpha = Math.min((float) (Math.sin(animstep) + 1), 1);
        animstep += 0.02;
        if (animstep >= 3 * Math.PI / 2)
            {
            return false;
            }
        return true;
        }

    TextElement(TextRenderer textrender, String text, Rect boundbox)
        {
        float textwidth = 0;
        String[] textlines = text.split("\n");
        for(String curr_line : textlines)
            {
            float sumsize = 0;
            for (int i = 0; i < curr_line.length(); i++)
                {
                sumsize += textrender.symmap.get(curr_line.charAt(i)).width();
                }
            textwidth = Math.max(textwidth, sumsize);
            }

        float sizemultiplier = boundbox.width() / textwidth / textrender.texsize;
        addTextElement(textrender, textlines, sizemultiplier);
        }

    void addTextElement(TextRenderer textrender, String[] textlines, float size)
        {
        size *= textrender.texsize;
        short[] indicesbase = new short[]{0, 3, 1, 3, 0, 2};
        int textlength = 0;
        for (String line : textlines)
            {
            textlength += line.length();
            }
        float textureCoordinates[] = new float[textlength * 8];
        short indices[] = new short[textlength * indicesbase.length];
        float vertices[] = new float[textlength * 12];
        int cur_index = 0;
        float cur_vshift = 0;
        float letterheight = textrender.symmap.get('A').height() * size;
        for (String line : textlines)
            {
            addTextLine(textrender, line, size, cur_vshift, textureCoordinates, indices, vertices, cur_index);
            cur_index += line.length();
            cur_vshift += letterheight;
            }
        height = cur_vshift;
        setIndices(indices);
        setVertices(vertices);
        setTextureCoordinates(textureCoordinates);
        }

    private void addTextLine(TextRenderer textrender,
                             String text,
                             float size,
                             float vertical_shift,
                             float textureCoordinates[],
                             short indices[],
                             float vertices[],
                             int last_element)
        {
        short[] indicesbase = new short[]{0, 3, 1, 3, 0, 2};
        float cur_x = 0;
        for (int i = last_element; i < last_element+text.length(); i++)
            {
            RectF sym_rect = textrender.symmap.get(text.charAt(i-last_element));
            textureCoordinates[i * 8 + 0] = sym_rect.left;
            textureCoordinates[i * 8 + 1] = sym_rect.top;

            textureCoordinates[i * 8 + 2] = sym_rect.right;
            textureCoordinates[i * 8 + 3] = sym_rect.top;

            textureCoordinates[i * 8 + 4] = sym_rect.left;
            textureCoordinates[i * 8 + 5] = sym_rect.bottom;

            textureCoordinates[i * 8 + 6] = sym_rect.right;
            textureCoordinates[i * 8 + 7] = sym_rect.bottom;
            for (int x = 0; x < indicesbase.length; x++)
                {
                indices[i * 6 + x] = (short) (i * 4 + indicesbase[x]);
                }

            vertices[i * 12 + 0] = cur_x;
            vertices[i * 12 + 1] = vertical_shift;
            vertices[i * 12 + 2] = 0;

            vertices[i * 12 + 3] = cur_x + sym_rect.width() * size;
            vertices[i * 12 + 4] = vertical_shift;
            vertices[i * 12 + 5] = 0;

            vertices[i * 12 + 6] = cur_x;
            vertices[i * 12 + 7] = sym_rect.height() * size+vertical_shift;
            vertices[i * 12 + 8] = 0;

            vertices[i * 12 + 9] = cur_x + sym_rect.width() * size;
            vertices[i * 12 + 10] = sym_rect.height() * size+vertical_shift;
            vertices[i * 12 + 11] = 0;

            cur_x += sym_rect.width() * size;
            }
        width = Math.max(cur_x, width);
        }
    protected void setIndices(short[] indices)
        {
        ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
        ibb.order(ByteOrder.nativeOrder());
        mIndicesBuffer = ibb.asShortBuffer();
        mIndicesBuffer.put(indices);
        mIndicesBuffer.position(0);
        mNumOfIndices = indices.length;
        }
    protected void setVertices(float[] vertices)
        {
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        mVerticesBuffer = vbb.asFloatBuffer();
        mVerticesBuffer.put(vertices);
        mVerticesBuffer.position(0);
        }
    protected void setTextureCoordinates(float[] textureCoords)
        {
        ByteBuffer byteBuf = ByteBuffer
                .allocateDirect(textureCoords.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        mTextureBuffer = byteBuf.asFloatBuffer();
        mTextureBuffer.put(textureCoords);
        mTextureBuffer.position(0);
        }
    }


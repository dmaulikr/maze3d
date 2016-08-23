package georgsh.maze3d.game;

public class SimplePlane extends Mesh {

    public SimplePlane() {
        this(1);
    }

    public SimplePlane(float size) {
        float textureCoordinates[] =
                { 0.0f, 1.0f,
                        1.0f, 1.0f,
                        0.0f, 0.0f,
                        1.0f, 0.0f, };

        short[] indices = new short[] { 0, 1, 2, 1, 3, 2 };
        float z = size/2;
        float[] vertices = new float[] {
                -size/2, -size/2, z,
                size/2, -size/2, z,
                -size/2, size/2, z,
                size/2, size/2, z };

        setIndices(indices);
        setVertices(vertices);
        setTextureCoordinates(textureCoordinates);
    }
}
package rwth.lab.android.androidsensors.accelerometer;

import android.content.Context;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import rwth.lab.android.androidsensors.sensor.IFigure;

/**
 * Created by ekaterina on 10.05.2015.
 */
public class Triangle implements IFigure {
    private FloatBuffer vertexBuffer;  // Buffer for vertex-array
    private FloatBuffer colorBuffer;   // Buffer for color-array
    private ByteBuffer indexBuffer;    // Buffer for index-array

    private float max = -1.0f;

    private float[] vertices = {  // Vertices of the triangle
            0.0f, 1.0f, 0.0f, // 0. top
            -1.0f, -1.0f, 0.0f, // 1. left-bottom
            1.0f, -1.0f, 0.0f  // 2. right-bottom
    };
    private byte[] indices = {0, 1, 2}; // Indices to above vertices (in CCW)
    private float[] colors = { // Colors for the vertices
            0.0f, 0.0f, 1.0f, 1.0f, //Blue
            1.0f, 0.0f, 0.0f, 1.0f, // Red
            1.0f, 1.0f, 1.0f, 1.0f // White
    };

    public Triangle() {
        setupVertexArrayBuffer(this.vertices);
        setupColorArrayBuffer(this.colors);

        // Setup index-array buffer. Indices in byte.
        indexBuffer = ByteBuffer.allocateDirect(indices.length);
        indexBuffer.put(indices);
        indexBuffer.position(0);
    }

    private void setupColorArrayBuffer(float[] colors) {
        // Setup color-array buffer. Colors in float. A float has 4 bytes
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
        cbb.order(ByteOrder.nativeOrder()); // Use native byte order
        colorBuffer = cbb.asFloatBuffer();  // Convert byte buffer to float
        colorBuffer.put(colors);            // Copy data into buffer
        colorBuffer.position(0);            // Rewind
    }

    private void setupVertexArrayBuffer(float[] vertices) {
        // Setup vertex-array buffer. Vertices in float. A float has 4 bytes
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder()); // Use native byte order
        vertexBuffer = vbb.asFloatBuffer(); // Convert byte buffer to float
        vertexBuffer.put(vertices);         // Copy data into buffer
        vertexBuffer.position(0);           // Rewind
    }

    @Override
    public void draw(GL10 gl) {
        // Enable arrays and define the buffers
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);          // Enable color-array
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);  // Define color-array buffer

        // Draw the primitives via index-array
        gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_BYTE, indexBuffer);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);   // Disable color-array
    }

    @Override
    public void setValues(float[] values) {
        if (this.max != -1.0f && this.max != 0.0f) {
            float[] vertices = {  // Vertices of the triangle
                    values[0] / this.max, values[1] / this.max, values[2] / this.max, // 0. top
                    -1.0f, -1.0f, 0.0f, // 1. left-bottom
                    1.0f, -1.0f, 0.0f  // 2. right-bottom
            };
            setupVertexArrayBuffer(vertices);
        }
    }

    @Override
    public void loadGLTexture(GL10 gl, Context context, int id) {

    }

    @Override
    public void setMax(float max) {
        this.max = max;
    }
}
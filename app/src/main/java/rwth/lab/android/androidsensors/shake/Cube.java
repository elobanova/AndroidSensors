package rwth.lab.android.androidsensors.shake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import javax.microedition.khronos.opengles.GL10;

import rwth.lab.android.androidsensors.sensor.IFigure;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by ekaterina on 12.05.2015.
 */
public class Cube implements IFigure {
    private FloatBuffer vertexBuffer;
    private float vertices[] = {
            -1.0f, -1.0f, 0.0f,        // bottom left
            -1.0f, 1.0f, 0.0f,        // top left
            1.0f, -1.0f, 0.0f,        // bottom right
            1.0f, 1.0f, 0.0f            // top right
    };

    private FloatBuffer textureBuffer;
    private float texture[] = {
            // Mapping coordinates for the vertices
            0.0f, 1.0f,        // top left
            0.0f, 0.0f,        // bottom left
            1.0f, 1.0f,        // top right
            1.0f, 0.0f        // bottom right
    };

    private int[] textures = new int[1];
    private float max = -1.0f;

    public Cube() {
        setupVertexArrayBuffer(this.vertices);

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(texture.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        textureBuffer = byteBuffer.asFloatBuffer();
        textureBuffer.put(texture);
        textureBuffer.position(0);
    }

    private void setupVertexArrayBuffer(float[] vertices) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        vertexBuffer = byteBuffer.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
    }

    @Override
    public void loadGLTexture(GL10 gl, Context context, int id) {
        // loading texture
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                id);

        // generate one texture pointer
        gl.glGenTextures(1, textures, 0);
        // ...and bind it to our array
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

        // create nearest filtered texture
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

        // Use Android GLUtils to specify a two-dimensional texture image from our bitmap
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

        bitmap.recycle();
    }

    public void draw(GL10 gl) {
        // bind the previously generated texture
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

        // Point to our buffers
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        // Set the face rotation
        gl.glFrontFace(GL10.GL_CW);

        // Point to our vertex buffer
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);

        // Draw the vertices as triangle strip
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);

        //Disable the client state before leaving
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    }

    @Override
    public void setMax(float max) {
        this.max = max;
    }

    @Override
    public void setValues(float[] values) {
        if (this.max != -1.0f && this.max != 0.0f) {
            float xOffset = values[0] / this.max;
            float yOffset = values[1] / this.max;
            float zOffset = values[2] / this.max;
            float[] vertices = {
                    -1.0f + xOffset, -1.0f + yOffset, 0.0f + zOffset,        // V1 - bottom left
                    -1.0f + xOffset, 1.0f + yOffset, 0.0f + zOffset,        // V2 - top left
                    1.0f + xOffset, -1.0f + yOffset, 0.0f + zOffset,        // V3 - bottom right
                    1.0f + xOffset, 1.0f + yOffset, 0.0f + zOffset            // V4 - top right
            };
            setupVertexArrayBuffer(vertices);
        }
    }
}

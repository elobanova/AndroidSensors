package rwth.lab.android.androidsensors.shake;

import android.content.Context;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rwth.lab.android.androidsensors.R;
import rwth.lab.android.androidsensors.sensor.IFigure;
import rwth.lab.android.androidsensors.sensor.OpenGLRenderer;

/**
 * Created by ekaterina on 16.05.2015.
 */
public class ShakeImageOpenGLRenderer extends OpenGLRenderer {
    private Context context;

    public ShakeImageOpenGLRenderer(Context context, IFigure figure) {
        super(context, figure);
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Load the texture for the square
        figure.loadGLTexture(gl, this.context, R.drawable.android_shake);

        gl.glEnable(GL10.GL_TEXTURE_2D);            //Enable Texture Mapping
        gl.glShadeModel(GL10.GL_SMOOTH);            //Enable Smooth Shading
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);    //Black Background
        gl.glClearDepthf(1.0f);                    //Depth Buffer Setup
        gl.glEnable(GL10.GL_DEPTH_TEST);            //Enables Depth Testing
        gl.glDepthFunc(GL10.GL_LEQUAL);            //The Type Of Depth Testing To Do

        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        if (this.textureId != -1) {
            figure.loadGLTexture(gl, this.context, this.textureId);
        }
        super.onDrawFrame(gl);
    }
}
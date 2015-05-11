package rwth.lab.android.androidsensors.sensor;

import android.opengl.GLSurfaceView;

/**
 * Created by ekaterina on 11.05.2015.
 */
public interface IFigureOpenGLRenderer extends GLSurfaceView.Renderer {
    void setValues(float[] values);

    void setMax(float max);
}

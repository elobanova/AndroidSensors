package rwth.lab.android.androidsensors.sensor;

import android.content.Context;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by ekaterina on 11.05.2015.
 */
public interface IFigure {

    /**
     * Performs the drawing procedure for this figure,
     * including its rendering and painting of primitives
     *
     * @param gl a GL object
     */
    void draw(GL10 gl);

    /**
     * Sets the maximum range of the coordinate changes
     * in order for the vertices not to leave the screen
     *
     * @param max a float maximum range value
     */
    void setMax(float max);

    /**
     * Sets the x, y, z values of the corresponding axis offsets to the figure
     *
     * @param values a float array consisting of x, y and z coordinates for an offset
     */
    void setValues(float[] values);

    /**
     * Loads the texture for this OpenGL figure edges
     *
     * @param gl      a GL object
     * @param context an activity context
     * @param id      an id of the drawable representing the texture
     */
    void loadGLTexture(GL10 gl, Context context, int id);
}

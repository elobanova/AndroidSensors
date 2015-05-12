package rwth.lab.android.androidsensors.sensor;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by ekaterina on 11.05.2015.
 */
public interface IFigure {
    void draw(GL10 gl);

    void setMax(float max);

    void setValues(float[] values);

    void setColor(float[] colors);
}

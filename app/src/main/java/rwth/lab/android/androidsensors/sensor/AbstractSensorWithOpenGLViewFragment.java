package rwth.lab.android.androidsensors.sensor;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.ViewGroup;

/**
 * Created by ekaterina on 11.05.2015.
 */
public abstract class AbstractSensorWithOpenGLViewFragment extends AbstractSensorFragment {

    protected GLSurfaceView drawableView;
    protected OpenGLRenderer renderer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawableView = new GLSurfaceView(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        drawableView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        drawableView.onPause();
    }

    /**
     * Updates the OpenGL renderer with the up-to-date information
     * about the coordinates changes
     *
     * @param values the up-to-date coordinates
     */
    protected void updateRenderer(float[] values) {
        renderer.setValues(values);
        float maxXY = Math.max(Math.abs(values[0]), Math.abs(values[1]));
        float maxXYZ = Math.max(maxXY, Math.abs(values[2]));
        renderer.setMax(maxXYZ);
        drawableView.invalidate();
    }

    @Override
    protected void addViewForSensorData(ViewGroup container) {
        container.addView(this.drawableView);
    }
}
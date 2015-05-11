package rwth.lab.android.androidsensors.gyroscope;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import rwth.lab.android.androidsensors.accelerometer.OpenGLTriangleRenderer;
import rwth.lab.android.androidsensors.sensor.AbstractSensorWithOpenGLViewFragment;

/**
 * Created by ekaterina on 11.05.2015.
 */
public class GyroscopeFragment extends AbstractSensorWithOpenGLViewFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        renderer = new OpenGLTriangleRenderer(getActivity());
        drawableView.setRenderer(renderer);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float[] values = event.values;
            updateRenderer(values);

            //update label
            sensorValue.setText("Gyroscope: X = " + values[0] + ", Y = " + values[1] + ", Z = " + values[2]);
        }
    }
}
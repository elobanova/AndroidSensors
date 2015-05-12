package rwth.lab.android.androidsensors.accelerometer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.Bundle;

import rwth.lab.android.androidsensors.sensor.AbstractSensorWithOpenGLViewFragment;
import rwth.lab.android.androidsensors.sensor.OpenGLRenderer;

/**
 * Created by ekaterina on 10.05.2015.
 */
public class AccelerometerFragment extends AbstractSensorWithOpenGLViewFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        renderer = new OpenGLRenderer(getActivity(), new Triangle());
        drawableView.setRenderer(renderer);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float[] values = event.values;
            updateRenderer(values);

            //update label
            sensorValue.setText("Accelerometer: X = " + values[0] + ", Y = " + values[1] + ", Z = " + values[2]);
        }
    }
}
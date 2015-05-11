package rwth.lab.android.androidsensors.magnetometer;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.Bundle;

import rwth.lab.android.androidsensors.AnySingleValueMeterDrawableView;
import rwth.lab.android.androidsensors.sensor.AbstractSensorWith2DViewFragment;

/**
 * Created by ekaterina on 10.05.2015.
 */
public class MagnetometerFragment extends AbstractSensorWith2DViewFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        drawableView = new AnySingleValueMeterDrawableView(getActivity(), Color.RED, Color.BLUE);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            float[] values = event.values;
            float magnetX = values[0];
            float magnetY = values[1];
            float magnetZ = values[2];
            double teslaXYZ = Math.sqrt((magnetX * magnetX) + (magnetY * magnetY) + (magnetZ * magnetZ));
            drawableView.setValue(teslaXYZ);
            drawableView.invalidate();

            //update label
            sensorValue.setText("Tesla: " + teslaXYZ);
        }
    }
}
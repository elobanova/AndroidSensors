package rwth.lab.android.androidsensors.barometer;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.Bundle;

import rwth.lab.android.androidsensors.AnySingleValueMeterDrawableView;
import rwth.lab.android.androidsensors.sensor.AbstractSensorWith2DViewFragment;

/**
 * Created by ekaterina on 09.05.2015.
 */
public class BarometerFragment extends AbstractSensorWith2DViewFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        drawableView = new AnySingleValueMeterDrawableView(getActivity(), Color.YELLOW, Color.CYAN);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
            float[] values = event.values;
            float barometerValue = values[0];
            drawableView.setValue(barometerValue);
            drawableView.invalidate();

            //update label
            sensorValue.setText("Pressure: " + barometerValue);
        }
    }
}

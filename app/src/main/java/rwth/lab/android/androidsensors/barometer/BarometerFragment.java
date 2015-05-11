package rwth.lab.android.androidsensors.barometer;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.Bundle;

import rwth.lab.android.androidsensors.AnyMeterDrawableView;
import rwth.lab.android.androidsensors.sensor.AbstractSensorFragment;

/**
 * Created by ekaterina on 09.05.2015.
 */
public class BarometerFragment extends AbstractSensorFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        drawableView = new AnyMeterDrawableView(getActivity(), Color.YELLOW, Color.CYAN);
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

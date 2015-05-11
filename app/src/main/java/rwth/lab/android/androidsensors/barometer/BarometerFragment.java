package rwth.lab.android.androidsensors.barometer;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import rwth.lab.android.androidsensors.AnyMeterDrawableView;
import rwth.lab.android.androidsensors.R;

/**
 * Created by ekaterina on 09.05.2015.
 */
public class BarometerFragment extends Fragment implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor barometer;

    private AnyMeterDrawableView drawableView;
    private TextView barometerText;
    private TextView sensorValue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        barometer = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        drawableView = new AnyMeterDrawableView(getActivity(), Color.YELLOW, Color.CYAN);
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, barometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.barometer_fragment, container, false);
        barometerText = (TextView) view.findViewById(R.id.barometer_text);
        sensorValue = (TextView) view.findViewById(R.id.sensor_value);
        if (barometer == null) {
            barometerText.setText(R.string.not_supported_message);
        } else {
            container.addView(this.drawableView);
        }
        return view;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

package rwth.lab.android.androidsensors.magnetometer;

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

import rwth.lab.android.androidsensors.AnyMeterDrawableView;
import rwth.lab.android.androidsensors.R;

/**
 * Created by ekaterina on 10.05.2015.
 */
public class MagnetometerFragment extends Fragment implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor magnetometer;

    private AnyMeterDrawableView drawableView;
    private TextView magnetometerText;
    private TextView sensorValue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        drawableView = new AnyMeterDrawableView(getActivity(), Color.RED, Color.BLUE);
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.magnetometer_fragment, container, false);
        magnetometerText = (TextView) view.findViewById(R.id.magnetometer_text);
        sensorValue = (TextView) view.findViewById(R.id.tesla_value);
        if (magnetometer == null) {
            magnetometerText.setText(R.string.not_supported_message);
        } else {
            container.addView(this.drawableView);
        }
        return view;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
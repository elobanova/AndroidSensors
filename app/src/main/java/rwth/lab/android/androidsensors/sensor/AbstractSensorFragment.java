package rwth.lab.android.androidsensors.sensor;

import android.app.Fragment;
import android.content.Context;
import android.hardware.Sensor;
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
 * Created by ekaterina on 11.05.2015.
 */
public abstract class AbstractSensorFragment extends Fragment implements SensorEventListener {
    protected SensorManager sensorManager;
    protected Sensor sensor;

    protected AnyMeterDrawableView drawableView;
    private TextView sensorNotSupportedText;
    protected TextView sensorValue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.abstract_sensor_fragment, container, false);
        sensorNotSupportedText = (TextView) view.findViewById(R.id.sensor_not_supported_text);
        sensorValue = (TextView) view.findViewById(R.id.sensor_value);
        if (sensor == null) {
            sensorNotSupportedText.setText(R.string.not_supported_message);
        } else {
            container.addView(this.drawableView);
        }
        return view;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
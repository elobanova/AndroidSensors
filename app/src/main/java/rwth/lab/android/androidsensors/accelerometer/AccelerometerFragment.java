package rwth.lab.android.androidsensors.accelerometer;

import android.app.Fragment;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import rwth.lab.android.androidsensors.R;

/**
 * Created by ekaterina on 10.05.2015.
 */
public class AccelerometerFragment extends Fragment implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer;

    private GLSurfaceView drawableView;

    private TextView accelerometerText;
    private OpenGLTriangleRenderer renderer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        drawableView = new GLSurfaceView(getActivity());
        renderer = new OpenGLTriangleRenderer(getActivity());
        drawableView.setRenderer(renderer);
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float[] values = event.values;
            renderer.setValues(values);
            float maxXY = Math.max(Math.abs(values[0]), Math.abs(values[1]));
            float maxXYZ = Math.max(maxXY, Math.abs(values[2]));
            renderer.setMax(maxXYZ);
            drawableView.invalidate();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.accelerometer_fragment, container, false);
        accelerometerText = (TextView) view.findViewById(R.id.accelerometer_text);
        if (accelerometer == null) {
            accelerometerText.setText(R.string.not_supported_message);
        } else {
            container.addView(this.drawableView);
        }
        return view;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
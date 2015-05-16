package rwth.lab.android.androidsensors.shake;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.view.View;

import rwth.lab.android.androidsensors.R;
import rwth.lab.android.androidsensors.sensor.AbstractSensorWithOpenGLViewFragment;
import rwth.lab.android.androidsensors.sensor.OpenGLRenderer;

/**
 * Created by ekaterina on 12.05.2015.
 */
public class ShakeFragment extends AbstractSensorWithOpenGLViewFragment {
    private ShakeDetector shakeDetector = new ShakeDetector();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        renderer = new ShakeImageOpenGLRenderer(getActivity(), new Cube());
        drawableView.setRenderer(renderer);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float[] values = event.values;
            if (shakeDetector.isShakeDetected(values)) {
                /*
                    Please, notice that we decided to change the image only once
                    due to the low performance of openGL. In order to see the shake again,
                    a user will need to come back to this activity once again.
                */
                renderer.setTexture(R.drawable.android_female);
            }
            updateRenderer(values);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sensorValue.setText("Shake it off to see the girl.");
    }
}

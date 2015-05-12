package rwth.lab.android.androidsensors.shake;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.Bundle;

import rwth.lab.android.androidsensors.sensor.AbstractSensorWithOpenGLViewFragment;
import rwth.lab.android.androidsensors.sensor.OpenGLRenderer;

/**
 * Created by ekaterina on 12.05.2015.
 */
public class ShakeFragment extends AbstractSensorWithOpenGLViewFragment {
    private long timeOfLastUpdate = 0;
    private float lastX, lastY, lastZ;
    private static final int SHAKE_THRESHOLD = 600;
    private static final int DELAY_THRESHOLD = 100;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        renderer = new OpenGLRenderer(getActivity(), new Circle());
        drawableView.setRenderer(renderer);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float[] values = event.values;

            long currentTime = System.currentTimeMillis();
            long timeSlot = (currentTime - timeOfLastUpdate);
            if (timeSlot > DELAY_THRESHOLD) {
                timeOfLastUpdate = currentTime;
                float x = values[0];
                float y = values[1];
                float z = values[2];
                float speed = Math.abs(x + y + z - lastX - lastY - lastZ) / timeSlot * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    float[] colors = generateRandomColors();
                    renderer.setColor(colors);

                    //update label
                    sensorValue.setText("Shaking it off!");
                }

                lastX = x;
                lastY = y;
                lastZ = z;
            }
            updateRenderer(values);
        }
    }

    private float[] generateRandomColors() {
        return null;
    }
}

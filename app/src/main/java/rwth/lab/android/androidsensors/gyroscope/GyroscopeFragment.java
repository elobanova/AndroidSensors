package rwth.lab.android.androidsensors.gyroscope;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import rwth.lab.android.androidsensors.sensor.AbstractSensorFragment;

/**
 * Created by ekaterina on 11.05.2015.
 */
public class GyroscopeFragment extends AbstractSensorFragment {
    public static final int PLOT_DEFAULT_HEIGHT = 500;
    private GyroscopeGraphDrawableView drawableViewTrackX;
    private GyroscopeGraphDrawableView drawableViewTrackY;
    private GyroscopeGraphDrawableView drawableViewTrackZ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        drawableViewTrackX = new GyroscopeGraphDrawableView(getActivity(), Color.RED, "X value");
        drawableViewTrackY = new GyroscopeGraphDrawableView(getActivity(), Color.BLUE, "Y value");
        drawableViewTrackZ = new GyroscopeGraphDrawableView(getActivity(), Color.GREEN, "Z value");
    }

    @Override
    protected void addViewForSensorData(ViewGroup container) {
        if (container instanceof FrameLayout) {
            FrameLayout frame = (FrameLayout) container;

            this.drawableViewTrackX.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    PLOT_DEFAULT_HEIGHT, Gravity.TOP));
            frame.addView(this.drawableViewTrackX, 0);

            this.drawableViewTrackY.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    PLOT_DEFAULT_HEIGHT, Gravity.CENTER));
            frame.addView(this.drawableViewTrackY, 1);

            this.drawableViewTrackZ.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    PLOT_DEFAULT_HEIGHT, Gravity.BOTTOM));
            frame.addView(this.drawableViewTrackZ, 2);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float[] values = event.values;

            drawableViewTrackX.addValueToTrack(values[0]);
            drawableViewTrackX.invalidate();

            drawableViewTrackY.addValueToTrack(values[1]);
            drawableViewTrackY.invalidate();

            drawableViewTrackZ.addValueToTrack(values[2]);
            drawableViewTrackZ.invalidate();

            //update label
            sensorValue.setText("Gyroscope: X = " + values[0] + ", Y = " + values[1] + ", Z = " + values[2]);
        }
    }
}
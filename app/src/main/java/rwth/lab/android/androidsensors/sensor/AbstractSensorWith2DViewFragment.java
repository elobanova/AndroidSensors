package rwth.lab.android.androidsensors.sensor;

import android.view.ViewGroup;

import rwth.lab.android.androidsensors.AnySingleValueMeterDrawableView;

/**
 * Created by ekaterina on 11.05.2015.
 */
public abstract class AbstractSensorWith2DViewFragment extends AbstractSensorFragment {

    protected AnySingleValueMeterDrawableView drawableView;

    @Override
    public void addViewForSensorData(ViewGroup container) {
        container.addView(this.drawableView);
    }
}
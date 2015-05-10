package rwth.lab.android.androidsensors.accelerometer;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import rwth.lab.android.androidsensors.R;

/**
 * Created by ekaterina on 10.05.2015.
 */
public class AccelerometerActivity extends FragmentActivity {
    private final static String FRAGMENT_TAG = "accelerometer";
    private AccelerometerFragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accelerometer_activity);

        FragmentManager fragmentManager = getFragmentManager();
        //fetch the fragment if it was saved (e.g. during orientation change)
        this.fragment = (AccelerometerFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (this.fragment == null) {
            // add the fragment
            this.fragment = new AccelerometerFragment();
            fragmentManager.beginTransaction().add(R.id.accelerometer_fragment_container, this.fragment, FRAGMENT_TAG).commit();
        }
    }
}
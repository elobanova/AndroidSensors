package rwth.lab.android.androidsensors.magnetometer;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import rwth.lab.android.androidsensors.R;

/**
 * Created by ekaterina on 10.05.2015.
 */
public class MagnetometerActivity extends FragmentActivity {
    private final static String FRAGMENT_TAG = "magnetometer";
    private MagnetometerFragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.magnetometer_activity);

        FragmentManager fragmentManager = getFragmentManager();
        //fetch the fragment if it was saved (e.g. during orientation change)
        this.fragment = (MagnetometerFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (this.fragment == null) {
            // add the fragment
            this.fragment = new MagnetometerFragment();
            fragmentManager.beginTransaction().add(R.id.magnetometer_fragment_container, this.fragment, FRAGMENT_TAG).commit();
        }
    }
}

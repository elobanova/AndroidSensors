package rwth.lab.android.androidsensors.gyroscope;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import rwth.lab.android.androidsensors.R;

/**
 * Created by ekaterina on 10.05.2015.
 */
public class GyroscopeActivity extends FragmentActivity {
    private final static String FRAGMENT_TAG = "gyroscope";
    private GyroscopeFragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gyroscope_activity);

        FragmentManager fragmentManager = getFragmentManager();
        //fetch the fragment if it was saved (e.g. during orientation change)
        this.fragment = (GyroscopeFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (this.fragment == null) {
            // add the fragment
            this.fragment = new GyroscopeFragment();
            fragmentManager.beginTransaction().add(R.id.gyroscope_fragment_container, this.fragment, FRAGMENT_TAG).commit();
        }
    }
}

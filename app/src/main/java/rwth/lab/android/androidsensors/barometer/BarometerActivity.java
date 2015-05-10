package rwth.lab.android.androidsensors.barometer;

import android.app.FragmentManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;

import rwth.lab.android.androidsensors.R;

/**
 * Created by ekaterina on 09.05.2015.
 */
public class BarometerActivity extends FragmentActivity {
    private final static String FRAGMENT_TAG = "barometer";
    private BarometerFragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barometer_activity);

        FragmentManager fragmentManager = getFragmentManager();
        //fetch the fragment if it was saved (e.g. during orientation change)
        this.fragment = (BarometerFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (this.fragment == null) {
            // add the fragment
            this.fragment = new BarometerFragment();
            fragmentManager.beginTransaction().add(R.id.fragment_container, this.fragment, FRAGMENT_TAG).commit();
        }
    }
}

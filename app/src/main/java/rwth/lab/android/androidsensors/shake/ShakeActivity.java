package rwth.lab.android.androidsensors.shake;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import rwth.lab.android.androidsensors.R;

/**
 * Created by ekaterina on 12.05.2015.
 */
public class ShakeActivity extends FragmentActivity {
    private final static String FRAGMENT_TAG = "shake";
    private ShakeFragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shake_activity);

        FragmentManager fragmentManager = getFragmentManager();
        //fetch the fragment if it was saved (e.g. during orientation change)
        this.fragment = (ShakeFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (this.fragment == null) {
            // add the fragment
            this.fragment = new ShakeFragment();
            fragmentManager.beginTransaction().add(R.id.shake_fragment_container, this.fragment, FRAGMENT_TAG).commit();
        }
    }
}

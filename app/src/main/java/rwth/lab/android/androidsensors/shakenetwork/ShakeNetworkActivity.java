package rwth.lab.android.androidsensors.shakenetwork;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import rwth.lab.android.androidsensors.R;

public class ShakeNetworkActivity extends FragmentActivity {
    private final static String FRAGMENT_TAG = "network";
    private ShakeNetworkFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shakenetwork_activity);

        FragmentManager fragmentManager = getFragmentManager();
        //fetch the fragment if it was saved (e.g. during orientation change)
        this.fragment = (ShakeNetworkFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (this.fragment == null) {
            // add the fragment
            this.fragment = new ShakeNetworkFragment();
            fragmentManager.beginTransaction().add(R.id.shake_network_fragment_container, this.fragment, FRAGMENT_TAG).commit();
        }
    }

}

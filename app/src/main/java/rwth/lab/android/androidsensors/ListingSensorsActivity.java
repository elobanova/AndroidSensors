package rwth.lab.android.androidsensors;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rwth.lab.android.androidsensors.accelerometer.AccelerometerActivity;
import rwth.lab.android.androidsensors.barometer.BarometerActivity;
import rwth.lab.android.androidsensors.gyroscope.GyroscopeActivity;
import rwth.lab.android.androidsensors.magnetometer.MagnetometerActivity;
import rwth.lab.android.androidsensors.shake.ShakeActivity;
import rwth.lab.android.androidsensors.shakenetwork.ShakeNetworkActivity;

/**
 * Created by ekaterina on 09.05.2015.
 */
public class ListingSensorsActivity extends Activity {
    private List<Integer> imageIds = new ArrayList<Integer>(
            Arrays.asList(R.drawable.accelerometer, R.drawable.barometer,
                    R.drawable.gyroscope, R.drawable.magnetometer, R.drawable.shaker, R.drawable.network_shake));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_sensors);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this, imageIds));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Class sensorActivity = null;
                switch (position) {
                    case 0:
                        sensorActivity = AccelerometerActivity.class;
                        break;
                    case 1:
                        sensorActivity = BarometerActivity.class;
                        break;
                    case 2:
                        sensorActivity = GyroscopeActivity.class;
                        break;
                    case 3:
                        sensorActivity = MagnetometerActivity.class;
                        break;
                    case 4:
                        sensorActivity = ShakeActivity.class;
                        break;
                    case 5:
                        sensorActivity= ShakeNetworkActivity.class;
                        break;
                }

                Intent intent = new Intent(ListingSensorsActivity.this,
                        sensorActivity);
                startActivity(intent);
            }
        });
    }
}

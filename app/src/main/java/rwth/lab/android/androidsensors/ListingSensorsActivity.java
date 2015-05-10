package rwth.lab.android.androidsensors;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ekaterina on 09.05.2015.
 */
public class ListingSensorsActivity extends Activity {
    private List<Integer> imageIds = new ArrayList<Integer>(
            Arrays.asList(R.drawable.accelerometer, R.drawable.barometer,
                    R.drawable.gyroscope, R.drawable.magnetometer));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_sensors);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this, imageIds));
    }
}

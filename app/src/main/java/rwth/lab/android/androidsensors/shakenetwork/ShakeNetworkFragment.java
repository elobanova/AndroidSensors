package rwth.lab.android.androidsensors.shakenetwork;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import rwth.lab.android.androidsensors.R;
import rwth.lab.android.androidsensors.sensor.AbstractSensorFragment;
import rwth.lab.android.androidsensors.shake.ShakeDetector;
import rwth.lab.android.androidsensors.shakenetwork.packet.Shake;
import rwth.lab.android.androidsensors.shakenetwork.helper.Validator;


/**
 * Created by ekaterina on 16.05.2015.
 */
public class ShakeNetworkFragment extends AbstractSensorFragment {
    private ShakeDetector shakeDetector = new ShakeDetector();
    private Button registerButton;
    private Button unregisterButtton;
    private SensorClient sensorClient;
    private ListView shakeEventListView;
    private EditText ipAdressField;
    private EditText portEditText;
    private EditText nameEditText;
    private ImageView imageView;
    private List<Shake> shakeEventList = new ArrayList<>();
    boolean isImageShaked = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorClient = SensorClient.newInstance();
    }

    @Override
    protected void addViewForSensorData(ViewGroup container) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float[] values = event.values;
            if (shakeDetector.isShakeDetected(values) && sensorClient.isRegistered()) {
                //send event
                sensorClient.sendEvent();
                if (imageView != null) {
                    if (isImageShaked) {
                        imageView.setImageResource(R.drawable.eating);
                        isImageShaked = false;
                    } else {
                        imageView.setImageResource(R.drawable.hungry);
                        isImageShaked = true;
                    }
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.networking_sensor_fragment, container, false);
        sensorNotSupportedText = (TextView) view.findViewById(R.id.sensor_not_supported_text);
        if (sensor == null) {
            sensorNotSupportedText.setText(R.string.not_supported_message);
        } else {
            this.container = container;
            ipAdressField = (EditText) view.findViewById(R.id.ipAdressField);
            portEditText = (EditText) view.findViewById(R.id.portField);
            nameEditText = (EditText) view.findViewById(R.id.nameField);
            registerButton = (Button) view.findViewById(R.id.registerButton);
            unregisterButtton = (Button) view.findViewById(R.id.unregisterButton);
            shakeEventListView = (ListView) view.findViewById(R.id.shakeEventList);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            ipAdressField.setText("87.106.23.235");
            portEditText.setText("3000");
            ipAdressField.addTextChangedListener(new Validator(ipAdressField,portEditText,registerButton));

            portEditText.addTextChangedListener(new Validator(ipAdressField,portEditText, registerButton));
            final ShakeListviewAdapter adapter = new ShakeListviewAdapter(shakeEventList, this.getActivity());
            shakeEventListView.setAdapter(adapter);
            sensorClient.setOnShakeListener(new SensorClient.OnShakeFromNetworkListener() {
                @Override
                public void onShakeReceived(Shake shake) {
                    shakeEventList.add(shake);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onError(String message) {
                    Toast.makeText(getActivity(), "An error occured:" + message, Toast.LENGTH_SHORT).show();
                }
            });
            registerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sensorClient.setSensorServerIp(ipAdressField.getText().toString());
                    sensorClient.setSensorServerPort(Integer.parseInt(portEditText.getText().toString()));
                    sensorClient.register(nameEditText.getText().toString());
                    registerButton.setEnabled(false);
                }
            });
            unregisterButtton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    unregisterButtton.setEnabled(false);
                    sensorClient.unregister();
                    registerButton.setEnabled(true);
                    unregisterButtton.setEnabled(true);
                }
            });
            addViewForSensorData(container);
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(sensorClient.isRegistered())
            sensorClient.unregister();//maybe to be removed
    }
}

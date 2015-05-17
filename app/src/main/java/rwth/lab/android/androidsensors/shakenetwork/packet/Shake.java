package rwth.lab.android.androidsensors.shakenetwork.packet;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import rwth.lab.android.androidsensors.shakenetwork.SensorClient;

/**
 * Created by evgenijavstein on 10/05/15.
 */
public class Shake implements Serializable {
    private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
    byte type;
    long timestamp;
    byte nameLength;
    String name;

    public Shake(byte[] shakeBuffer, int shakeBufferLenght) {
        type = SensorClient.TYPE_SHAKE;
        byte[] recTimeStampBE = Arrays.copyOfRange(shakeBuffer, 1, 9);
        this.timestamp = Utils.bytesToLong(recTimeStampBE);
        this.nameLength = shakeBuffer[9];
        byte[] name = Arrays.copyOfRange(shakeBuffer, 10, shakeBufferLenght);
        this.name = new String(name);
    }

    public String getName() {
        return name;
    }

    public String getHumanTimeShaken() {
        Date time = new Date((long) timestamp * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(time);
    }
}

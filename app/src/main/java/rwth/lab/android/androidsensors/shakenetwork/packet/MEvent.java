package rwth.lab.android.androidsensors.shakenetwork.packet;


import java.nio.ByteBuffer;

import rwth.lab.android.androidsensors.shakenetwork.SensorClient;

/**
 * Created by evgenijavstein on 10/05/15.
 */
public class MEvent implements Packet {
    long timestamp;
    byte type;

    public MEvent() {
        timestamp = Utils.unixTimeStamp();
        type = SensorClient.TYPE_EVENT;
    }

    public byte[] getBytes() {
        byte[] timestampBE = Utils.longToBytesBE(timestamp);
        ByteBuffer byteBuffer = ByteBuffer.allocate(timestampBE.length + 1);//one byte more for type
        byteBuffer.put(type);
        byteBuffer.put(timestampBE);
        return byteBuffer.array();
    }
}

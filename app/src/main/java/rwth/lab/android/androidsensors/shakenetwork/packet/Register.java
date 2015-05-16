package rwth.lab.android.androidsensors.shakenetwork.packet;


import java.nio.ByteBuffer;

import rwth.lab.android.androidsensors.shakenetwork.SensorClient;

/**
 * Created by evgenijavstein on 11/05/15.
 */
public class Register implements Packet{
    private byte type= SensorClient.TYPE_REGISTER;//1
    private byte nameLenght;
    private String name;

    public Register(String name){
        this.name=name;
        this.nameLenght=(byte)name.length();

    }

    public byte [] getBytes(){
        ByteBuffer byteBuffer=ByteBuffer.allocate(2+nameLenght);
        byteBuffer.put(type);
        byteBuffer.put(nameLenght);
        byteBuffer.put(name.getBytes());
        return byteBuffer.array();
    }
}

package rwth.lab.android.androidsensors.shakenetwork.packet;


import android.util.StringBuilderPrinter;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import rwth.lab.android.androidsensors.shakenetwork.SensorClient;

/**
 * Created by evgenijavstein on 11/05/15.
 */
public class Register implements Packet {
    private byte type = SensorClient.TYPE_REGISTER;//1
    private byte nameLenght;
    private String name;

    public Register(String name) {
        this.name = name;
        try {//name lenght will be bigger if non-utf8 character is present
            this.nameLenght = (byte) name.getBytes("UTF-8").length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public byte[] getBytes() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(2 + nameLenght);
        byteBuffer.put(type);
        byteBuffer.put(nameLenght);

        try {
            byteBuffer.put(name.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return byteBuffer.array();
    }
}

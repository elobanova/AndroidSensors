package rwth.lab.android.androidsensors.shakenetwork.packet;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by evgenijavstein on 10/05/15.
 */
public class Utils {
    private static final int BYTES_LONG = 8;

    /**
     * Converts <code>x</code> too a byte buffer with Big Endian byte order
     *
     * @param x
     * @return
     */
    public static byte[] longToBytesBE(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(BYTES_LONG);
        buffer.putLong(0, x);
        buffer.order(ByteOrder.BIG_ENDIAN);
        return buffer.array();
    }

    /**
     * Converts <code>bytes</code> to long after fliping them
     *
     * @param bytes
     * @return
     */
    public static long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(BYTES_LONG);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();//need flip
        return buffer.getLong();
    }

    public static long unixTimeStamp() {
        return System.currentTimeMillis() / 1000L;//get seconds since Jan 1, 1970.
    }
}

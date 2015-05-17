package rwth.lab.android.androidsensors.shake;

/**
 * Created by ekaterina on 16.05.2015.
 */
public class ShakeDetector {
    private long timeOfLastUpdate = 0;
    private float lastX, lastY, lastZ;
    private static final int SHAKE_THRESHOLD = 900;
    private static final int DELAY_THRESHOLD = 200;

    /**
     * Detects if the shake event has been performed on a device based on
     * the time slot tracked and the updates for the coordinates
     *
     * @param values the current coordinates (e.g. taken from accelerometer)
     * @return true if the shake event occurred
     */
    public boolean isShakeDetected(float[] values) {
        long currentTime = System.currentTimeMillis();
        long timeSlot = (currentTime - timeOfLastUpdate);
        if (timeSlot > DELAY_THRESHOLD) {
            timeOfLastUpdate = currentTime;
            float x = values[0];
            float y = values[1];
            float z = values[2];
            float speed = Math.abs(x + y + z - lastX - lastY - lastZ) / timeSlot * 10000;

            lastX = x;
            lastY = y;
            lastZ = z;
            if (speed > SHAKE_THRESHOLD) {
                return true;
            }
        }
        return false;
    }
}

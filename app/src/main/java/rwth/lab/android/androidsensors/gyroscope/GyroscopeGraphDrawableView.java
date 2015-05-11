package rwth.lab.android.androidsensors.gyroscope;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ekaterina on 11.05.2015.
 */
public class GyroscopeGraphDrawableView extends View {
    public static final int STROKE_WIDTH = 10;
    private static final int LINE_STROKE_WIDTH = 2;

    public final String title;
    private Paint paint;
    private List<String> timeValues = new ArrayList<>();
    private List<String> trackedValues = new ArrayList<>();
    private float maximalTrackValue, minimalTrackValue;
    private boolean isInitializing = true;
    private int tStep = 1, numberOfTValues;
    private final int color;

    public GyroscopeGraphDrawableView(Context context, int color, String title) {
        super(context);
        paint = new Paint();
        paint.setStrokeWidth(STROKE_WIDTH);
        paint.setAntiAlias(true);
        this.trackedValues.add("0");
        this.color = color;
        this.title = title;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float canvasHeight = getHeight();
        float canvasWidth = getWidth();

        if (isInitializing) {
            timeValues.add("" + 0.1 * canvasWidth);
            numberOfTValues = (int) (0.9 * canvasWidth / tStep);
            isInitializing = false;
        }

        float[] trackedFloatValues = getFloat(trackedValues);
        maximalTrackValue = getMax(trackedFloatValues);
        minimalTrackValue = getMin(trackedFloatValues);

        int[] trackedValuesInPixels = toPixel(canvasHeight, minimalTrackValue, maximalTrackValue, trackedFloatValues);

        canvas.drawARGB(0, 0, 0, 0);

        paint.setStrokeWidth(LINE_STROKE_WIDTH);
        paint.setColor(Color.LTGRAY);
        paint.setTextSize(40.0f);
        canvas.drawText(title, (float) 0.7 * canvasWidth, (float) 0.1 * canvasHeight, paint);

        canvas.drawLine(0, canvasHeight / 2, canvasWidth, canvasHeight / 2, paint);
        canvas.drawLine((float) 0.1 * canvasWidth, 0, (float) 0.1 * canvasWidth, canvasHeight, paint);

        paint.setColor(color);

        if (timeValues.size() == 1) {
            canvas.drawCircle(fromObjectToFloat(timeValues.get(0)), canvasHeight - trackedValuesInPixels[0], 2, paint);
        } else {
            for (int i = 0; i < timeValues.size() - 1; i++) {
                canvas.drawCircle(fromObjectToFloat(timeValues.get(i)), canvasHeight - trackedValuesInPixels[i], 2, paint);
                canvas.drawCircle(fromObjectToFloat(timeValues.get(i + 1)), canvasHeight - trackedValuesInPixels[i + 1], 2, paint);
                canvas.drawLine(fromObjectToFloat(timeValues.get(i)), canvasHeight - trackedValuesInPixels[i], fromObjectToFloat(timeValues.get(i + 1)), canvasHeight - trackedValuesInPixels[i + 1], paint);
            }
        }
    }

    private Float fromObjectToFloat(Object o) {
        String tempS = o.toString();
        Float f = new Float(tempS);
        return f;
    }

    public void addValueToTrack(float s) {
        trackedValues.add("" + s);

        if (trackedValues.size() > numberOfTValues) {
            trackedValues.remove(0);
        } else {
            float tt = fromObjectToFloat(timeValues.get(timeValues.size() - 1)) + tStep;
            timeValues.add("" + tt);
        }
    }

    public void clearData() {
        Object temp = timeValues.get(0);
        timeValues.clear();
        timeValues.add("" + temp);
        temp = trackedValues.get(0);
        trackedValues.clear();
        trackedValues.add("" + temp);
    }

    private float[] getFloat(List<String> value) {
        float[] v = new float[value.size()];
        Object[] tempO = value.toArray();
        String[] temp = Arrays.copyOf(tempO, tempO.length, String[].class);
        for (int i = 0; i < value.size(); i++) {
            Float f = new Float(temp[i]);
            v[i] = f.floatValue();
        }

        return (v);
    }

    private int[] toPixel(float pixels, float min, float max, float[] value) {
        double[] p = new double[value.length];
        int[] pint = new int[value.length];

        for (int i = 0; i < value.length; i++) {
            if (value[i] > 0) {
                p[i] = pixels / 2 + ((value[i]) / (max)) * .9 * pixels / 2;
                pint[i] = (int) p[i];
            } else if (value[i] < 0) {
                p[i] = pixels / 2 - ((value[i]) / (min)) * .9 * pixels / 2;
                pint[i] = (int) p[i];
            } else {
                pint[i] = (int) pixels / 2;
            }
        }
        return (pint);
    }

    private float getMax(float[] v) {
        float largest = v[0];
        for (int i = 0; i < v.length; i++)
            if (v[i] > largest)
                largest = v[i];
        return largest;
    }

    private float getMin(float[] v) {
        float smallest = v[0];
        for (int i = 0; i < v.length; i++)
            if (v[i] < smallest)
                smallest = v[i];
        return smallest;
    }
}

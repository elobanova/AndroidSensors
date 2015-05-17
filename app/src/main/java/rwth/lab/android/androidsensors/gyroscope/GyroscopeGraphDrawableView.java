package rwth.lab.android.androidsensors.gyroscope;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ekaterina on 11.05.2015.
 */
public class GyroscopeGraphDrawableView extends View {
    public static final int STROKE_WIDTH = 10;
    private static final int LINE_STROKE_WIDTH = 2;
    public static final double FIELD_RIGHT_TO_AXIS_WEIGHT = 0.9;

    public final String title;
    private Paint paint;
    private List<Float> timeValues = new ArrayList<>();
    private List<Float> trackedValues = new ArrayList<>();
    private boolean isInitializing = true;
    private int tStep = 1, numberOfTValues;
    private final int color;

    public GyroscopeGraphDrawableView(Context context, int color, String title) {
        super(context);
        paint = new Paint();
        paint.setStrokeWidth(STROKE_WIDTH);
        paint.setAntiAlias(true);
        this.trackedValues.add(0.0f);
        this.color = color;
        this.title = title;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float canvasHeight = getHeight();
        float canvasWidth = getWidth();

        if (isInitializing) {
            timeValues.add(0.1f * canvasWidth);
            numberOfTValues = (int) (FIELD_RIGHT_TO_AXIS_WEIGHT * canvasWidth / tStep);
            isInitializing = false;
        }

        int[] trackedValuesInPixels = toPixel(canvasHeight);

        canvas.drawARGB(0, 0, 0, 0);

        paint.setStrokeWidth(LINE_STROKE_WIDTH);
        paint.setColor(Color.LTGRAY);
        paint.setTextSize(40.0f);
        canvas.drawText(title, (float) 0.7 * canvasWidth, (float) 0.1 * canvasHeight, paint);

        canvas.drawLine(0, canvasHeight / 2, canvasWidth, canvasHeight / 2, paint);
        canvas.drawLine((float) (1 - FIELD_RIGHT_TO_AXIS_WEIGHT) * canvasWidth, 0, (float) (1 - FIELD_RIGHT_TO_AXIS_WEIGHT) * canvasWidth, canvasHeight, paint);

        paint.setColor(color);

        int timeValuesSize = timeValues.size();
        if (timeValuesSize == 1) {
            canvas.drawCircle(timeValues.get(0), canvasHeight - trackedValuesInPixels[0], 2, paint);
        } else {
            for (int i = 0; i < timeValuesSize - 1; i++) {
                canvas.drawCircle(timeValues.get(i), canvasHeight - trackedValuesInPixels[i], 2, paint);
                canvas.drawCircle(timeValues.get(i + 1), canvasHeight - trackedValuesInPixels[i + 1], 2, paint);
                canvas.drawLine(timeValues.get(i), canvasHeight - trackedValuesInPixels[i], timeValues.get(i + 1), canvasHeight - trackedValuesInPixels[i + 1], paint);
            }
        }
    }

    /**
     * Adds the float tracked value to the tail of the list
     * in order to be displayed on the graph. If there's not enough space in the list,
     * the head of it will be removed.
     *
     * @param s a value to be tracked
     */
    public void addValueToTrack(float s) {
        trackedValues.add(s);

        if (trackedValues.size() > numberOfTValues) {
            trackedValues.remove(0);
        } else {
            timeValues.add(timeValues.get(timeValues.size() - 1) + tStep);
        }
    }

    private int[] toPixel(float pixels) {
        float max = Collections.max(trackedValues);
        float min = Collections.min(trackedValues);
        int trackedValuesSize = trackedValues.size();
        int[] dataInPixels = new int[trackedValuesSize];

        for (int i = 0; i < trackedValuesSize; i++) {
            Float trackedValue = trackedValues.get(i);
            if (trackedValue > 0) {
                double currentValue = pixels / 2 + (trackedValue / (max)) * FIELD_RIGHT_TO_AXIS_WEIGHT * pixels / 2;
                dataInPixels[i] = (int) currentValue;
            } else if (trackedValue < 0) {
                double currentValue = pixels / 2 - (trackedValue / (min)) * FIELD_RIGHT_TO_AXIS_WEIGHT * pixels / 2;
                dataInPixels[i] = (int) currentValue;
            } else {
                dataInPixels[i] = (int) pixels / 2;
            }
        }
        return dataInPixels;
    }
}

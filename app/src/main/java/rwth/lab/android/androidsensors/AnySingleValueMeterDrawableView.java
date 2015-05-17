package rwth.lab.android.androidsensors;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.view.View;

/**
 * Created by ekaterina on 10.05.2015.
 */
public class AnySingleValueMeterDrawableView extends View {
    public static final int RECTANGLE_WIDTH = 400;
    public static final int RECTANGLE_HEIGHT = 800;
    public static final int STROKE_WIDTH = 10;
    public static final int ARC_SIZE = 30;

    private double value;
    private double max = -1;
    private int gradientStartColor;
    private int gradientEndColor;
    private Paint paint = new Paint();

    /**
     * Creates an instance of the drawable view with the bar
     * for the single-valued sensors (like barometer or magnetometer)
     *
     * @param context            an activity context
     * @param gradientStartColor a starting color for the gradient of the bar
     * @param gradientEndColor   an ending color for the gradient of the bar
     */
    public AnySingleValueMeterDrawableView(Context context, int gradientStartColor, int gradientEndColor) {
        super(context);
        this.gradientStartColor = gradientStartColor;
        this.gradientEndColor = gradientEndColor;
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(STROKE_WIDTH);
        paint.setAntiAlias(true);
    }

    public void setValue(double value) {
        this.value = value;
        if (this.max < this.value) {
            this.max = this.value;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int left = (width - RECTANGLE_WIDTH) / 2;
        int right = (width + RECTANGLE_WIDTH) / 2;
        int height = getHeight();
        int top = (height + RECTANGLE_HEIGHT) / 2;
        int bottom = (height - RECTANGLE_HEIGHT) / 2;

        // fill
        paint.setStyle(Paint.Style.FILL);
        Shader shader = new LinearGradient(left, top, right, top, Color.WHITE, Color.GRAY, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        canvas.drawRoundRect(left - STROKE_WIDTH, bottom - STROKE_WIDTH, right + STROKE_WIDTH, top + STROKE_WIDTH, ARC_SIZE, ARC_SIZE, paint);

        // border
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.DKGRAY);
        canvas.drawRoundRect(left - STROKE_WIDTH, bottom - STROKE_WIDTH, right + STROKE_WIDTH, top + STROKE_WIDTH, ARC_SIZE, ARC_SIZE, paint);

        int difference = (int) (RECTANGLE_HEIGHT * (1 - (value / this.max)));
        // fill
        paint.setStyle(Paint.Style.FILL);
        Shader fillingShader = new LinearGradient(right, top, left, top, gradientStartColor, gradientEndColor, Shader.TileMode.CLAMP);
        paint.setShader(fillingShader);
        canvas.drawRoundRect(left, bottom + difference, right, top, ARC_SIZE, ARC_SIZE, paint);
    }
}
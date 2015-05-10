package rwth.lab.android.androidsensors.magnetometer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by ekaterina on 10.05.2015.
 */
public class MagnetometerDrawableView extends View {
    public static final int RECTANGLE_WIDTH = 400;
    public static final int RECTANGLE_HEIGHT = 800;
    public static final int STROKE_WIDTH = 10;

    private double teslaXYZ;
    private double max = -1;
    private Paint paint = new Paint();

    public MagnetometerDrawableView(Context context) {
        super(context);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(STROKE_WIDTH);
        paint.setAntiAlias(true);
    }

    public void setTesla(double teslaXYZ) {
        this.teslaXYZ = teslaXYZ;
        if (this.max < this.teslaXYZ) {
            this.max = this.teslaXYZ;
        }
    }

    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int left = (width - RECTANGLE_WIDTH) / 2;
        int right = (width + RECTANGLE_WIDTH) / 2;
        int height = getHeight();
        int top = (height + RECTANGLE_HEIGHT) / 2;
        int bottom = (height - RECTANGLE_HEIGHT) / 2;

        Rect wholeRectangle = new Rect(left - STROKE_WIDTH, bottom - STROKE_WIDTH, right + STROKE_WIDTH, top + STROKE_WIDTH);
        // fill
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawRect(wholeRectangle, paint);

        // border
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.DKGRAY);
        canvas.drawRect(wholeRectangle, paint);


        int difference = (int) (RECTANGLE_HEIGHT * (1 - (teslaXYZ / this.max)));
        Rect teslaRectangle = new Rect(left, bottom + difference, right, top);
        // fill
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.MAGENTA);
        canvas.drawRect(teslaRectangle, paint);
    }
}
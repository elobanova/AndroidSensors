package rwth.lab.android.androidsensors;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by ekaterina on 09.05.2015.
 */
public class ImageAdapter extends BaseAdapter {
    private static final int PADDING = 4;
    private static final int HEIGHT = 600;

    private List<Integer> imageIds;
    private Context context;

    public ImageAdapter(Context context, List<Integer> imageIds) {
        this.context = context;
        this.imageIds = imageIds;
    }

    @Override
    public int getCount() {
        return imageIds.size();
    }

    @Override
    public Object getItem(int position) {
        return imageIds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return imageIds.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = (ImageView) convertView;

        if (imageView == null && parent instanceof GridView) {
            GridView grid = (GridView) parent;
            imageView = new ImageView(this.context);
            imageView.setLayoutParams(new GridView.LayoutParams(grid.getColumnWidth(), HEIGHT));
            imageView.setPadding(PADDING, PADDING, PADDING, PADDING);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        imageView.setImageResource(imageIds.get(position));
        return imageView;
    }
}

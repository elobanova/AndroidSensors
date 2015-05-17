package rwth.lab.android.androidsensors.shakenetwork;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rwth.lab.android.androidsensors.R;
import rwth.lab.android.androidsensors.shakenetwork.packet.Shake;

/**
 * Created by evgenijavstein on 16/05/15.
 */
public class ShakeListviewAdapter extends BaseAdapter {
    private List<Shake> shakeEventList;
    private Context context;
    private List<String> senders = new ArrayList<>();
    private List<Integer> colors = new ArrayList<>();

    public ShakeListviewAdapter(List<Shake> shakeEventList, Context context) {
        this.shakeEventList = shakeEventList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return shakeEventList.size();
    }

    @Override
    public Object getItem(int position) {
        return shakeEventList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        CompleteListViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.shake_listview_item, null);
            viewHolder = new CompleteListViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (CompleteListViewHolder) v.getTag();
        }

        //mark every new sensor client with a random color
        int color;
        int index = senders.indexOf(shakeEventList.get(position).getName());
        if (index == -1) {
            senders.add(shakeEventList.get(position).getName());
            color = randomColor();
            colors.add(color);
        } else {
            color = colors.get(index);
        }

        String nNameText = String.format(context.getString(R.string.just_shaken), shakeEventList.get(position).getName());
        viewHolder.mName.setText(nNameText);
        viewHolder.mTime.setText(shakeEventList.get(position).getHumanTimeShaken());

        Drawable dr = context.getDrawable(R.drawable.circle);
        dr.mutate().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        viewHolder.circleView.setBackground(dr);

        return v;
    }

    /**
     * Best practice pattern to increase ListView performance
     */
    class CompleteListViewHolder {
        public TextView mName;
        public TextView mTime;
        public View circleView;

        public CompleteListViewHolder(View base) {
            mName = (TextView) base.findViewById(R.id.eventOccuredField);
            circleView = base.findViewById(R.id.idIcon);
            mTime = (TextView) base.findViewById(R.id.timeShaken);
        }
    }

    private int randomColor() {
        Random random = new Random();
        String[] colorsArr = context.getResources().getStringArray(R.array.colors);
        return Color.parseColor(colorsArr[random.nextInt(colorsArr.length)]);
    }
}

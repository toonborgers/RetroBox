package be.cegeka.retrobox.presentation.newretro.selectactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import be.cegeka.retrobox.R;
import be.cegeka.retrobox.domain.Activity;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class ActivityOverviewAdapter extends ArrayAdapter<Activity> {
    private final LayoutInflater layoutInflater;
    private List<Activity> activities;

    public ActivityOverviewAdapter(Context ctx, List<Activity> activities) {
        super(ctx, R.layout.activity_overview_list_item, activities);
        this.activities = activities;
        this.layoutInflater = LayoutInflater.from(ctx);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            rowView = layoutInflater.inflate(R.layout.activity_overview_list_item, null);
            rowView.setTag(new ViewHolder(rowView));
        }
        Activity currentItem = activities.get(position);
        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.name.setText(currentItem.getName());
        holder.duration.setText(String.valueOf(currentItem.getDurationMinutes()));
        return rowView;
    }

    class ViewHolder {
        @InjectView(R.id.activity_overview_item_name)
        TextView name;
        @InjectView(R.id.activity_overview_item_duration)
        TextView duration;

        ViewHolder(View v) {
            ButterKnife.inject(this, v);
        }
    }
}

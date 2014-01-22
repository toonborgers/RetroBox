package be.cegeka.retrobox.newretro;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
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

import static be.cegeka.retrobox.BeanProvider.activityOverviewHelper;

public class NewRetroActivitiesAdapter extends ArrayAdapter<Activity> {
    private static final int DEFAULT_TITLE = R.string.activities_select_activity;
    private Context ctx;
    private List<Activity> activities;
    private LayoutInflater layoutInflater;

    public NewRetroActivitiesAdapter(Context ctx, List<Activity> activities) {
        super(ctx, R.layout.activity_list_item, activities);
        this.ctx = ctx;
        this.activities = activities;
        layoutInflater = LayoutInflater.from(ctx);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View resultView = convertView;

        if (resultView == null) {
            resultView = layoutInflater.inflate(R.layout.activity_list_item, null);
            resultView.setTag(new ViewHolder(resultView));
        }

        Activity activity = activities.get(position);
        Activity.ActivityType activityType = activity.getActivityType();
        int color = ctx.getResources().getColor(activityType.getColorResource());

        ViewHolder holder = (ViewHolder) resultView.getTag();
        setSidebarColor(holder, color);
        setProgressbarColor(holder, color);
        holder.itemType.setText(ctx.getResources().getString(activityType.getTitleResource()));
        holder.itemTitle.setTextColor(color);
        if (activity.isEmpty()) {
            holder.itemTitle.setText(DEFAULT_TITLE);
        } else {
            holder.itemTitle.setText(activity.getName());
        }
        holder.startTime.setText(activityOverviewHelper().determineActivityStartTimeText(activity));
        holder.endTime.setText(activityOverviewHelper().determineActivityEndTimeText(activity));

        return resultView;
    }

    private void setSidebarColor(ViewHolder holder, int color) {
        GradientDrawable drawable = (GradientDrawable) ctx.getResources().getDrawable(R.drawable.activity_list_item_sidebar_background);
        drawable.setColor(color);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            holder.leftBar.setBackground(drawable);
        } else {
            holder.leftBar.setBackgroundDrawable(drawable);
        }
    }

    private void setProgressbarColor(ViewHolder holder, int color) {
        GradientDrawable drawable = (GradientDrawable) ctx.getResources().getDrawable(R.drawable.activity_list_item_progressbar_background);
        drawable.setColor(color);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            holder.progressbar.setBackground(drawable);
        } else {
            holder.progressbar.setBackgroundDrawable(drawable);
        }
    }

    class ViewHolder {
        @InjectView(R.id.activity_item_leftbar)
        View leftBar;
        @InjectView(R.id.activity_item_type)
        TextView itemType;
        @InjectView(R.id.activity_item_title)
        TextView itemTitle;
        @InjectView(R.id.activity_item_progressbar)
        View progressbar;
        @InjectView(R.id.activity_item_start)
        TextView startTime;
        @InjectView(R.id.activity_item_end)
        TextView endTime;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}

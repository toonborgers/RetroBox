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
import java.util.Random;

import be.cegeka.retrobox.R;
import be.cegeka.retrobox.domain.ActivityExecution;
import be.cegeka.retrobox.domain.ActivityType;
import be.cegeka.retrobox.ui.ActivityProgressBar;
import butterknife.ButterKnife;
import butterknife.InjectView;

import static be.cegeka.retrobox.BeanProvider.activityOverviewHelper;
import static be.cegeka.retrobox.domain.ActivityType.forTypeCode;

public class NewRetroActivitiesAdapter extends ArrayAdapter<ActivityExecution> {
    private static final int DEFAULT_TITLE = R.string.activities_select_activity;
    private Context ctx;
    private List<ActivityExecution> activities;
    private LayoutInflater layoutInflater;

    public NewRetroActivitiesAdapter(Context ctx, List<ActivityExecution> activities) {
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

        ActivityExecution execution = activities.get(position);
        ActivityType activityType = forTypeCode(execution.getActivityTypeCode());
        int color = ctx.getResources().getColor(activityType.getColorResource());

        ViewHolder holder = (ViewHolder) resultView.getTag();
        setSidebarColor(holder, color);
        setProgressbarColor(holder, color);
        holder.itemType.setText(ctx.getResources().getString(activityType.getTitleResource()));
        holder.itemTitle.setTextColor(color);
        if (execution.isEmpty()) {
            holder.itemTitle.setText(DEFAULT_TITLE);
        } else {
            holder.itemTitle.setText(execution.getActivity().getName());
        }
        holder.startTime.setText(activityOverviewHelper().determineActivityStartTimeText(execution));
        holder.endTime.setText(activityOverviewHelper().determineActivityEndTimeText(execution));

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

    private static Random random = new Random(System.currentTimeMillis());

    private void setProgressbarColor(ViewHolder holder, int color) {
        holder.progressbar.setBarColor(color);
        holder.progressbar.setProgress(random.nextFloat() * 100f);
    }

    class ViewHolder {
        @InjectView(R.id.activity_item_leftbar)
        View leftBar;
        @InjectView(R.id.activity_item_type)
        TextView itemType;
        @InjectView(R.id.activity_item_title)
        TextView itemTitle;
        @InjectView(R.id.activity_item_progressbar)
        ActivityProgressBar progressbar;
        @InjectView(R.id.activity_item_start)
        TextView startTime;
        @InjectView(R.id.activity_item_end)
        TextView endTime;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}

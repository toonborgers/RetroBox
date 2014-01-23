package be.cegeka.retrobox.domain;

import org.joda.time.DateTime;

public class ActivityExecution {
    private int id;
    private int activityTypeCode;
    private DateTime start;
    private DateTime end;
    private Activity activity;

    private ActivityExecution() {

    }

    public boolean isEmpty() {
        return activity == null;
    }

    public Activity getActivity() {
        return activity;
    }

    public int getId() {
        return id;
    }

    public int getActivityTypeCode() {
        return activityTypeCode;
    }

    public DateTime getStart() {
        return start;
    }

    public DateTime getEnd() {
        return end;
    }

    public static ActivityExecution forActivity(Activity activity) {
        ActivityExecution result = new ActivityExecution();
        result.activity = activity;
        result.activityTypeCode = activity.getActivityTypeCode();
        return result;
    }

    public static ActivityExecution emptyInstance(int activityTypeCode) {
        ActivityExecution execution = new ActivityExecution();
        execution.activityTypeCode = activityTypeCode;
        return execution;
    }
}

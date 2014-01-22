package be.cegeka.retrobox.domain;

import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

public class RetroFactory {
    public static Retro.Builder newRetroBuilder() {
        Map<Integer, Activity> activities = new HashMap<Integer, Activity>();
        for (Activity.ActivityType activityType : Activity.ActivityType.values()) {
            activities.put(activityType.getTypeCode(), ActivityFactory.newEmptyActivity(activityType.getTypeCode()));
        }

        return new Retro.Builder()
                .withTime(DateTime.now())
                .withActivities(activities);
    }
}

package be.cegeka.retrobox.domain;

import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

import static be.cegeka.retrobox.domain.ActivityExecution.emptyInstance;

public class RetroFactory {
    public static Retro.Builder newRetroBuilder() {
        Map<Integer, ActivityExecution> activities = new HashMap<Integer, ActivityExecution>();
        for (ActivityType activityType : ActivityType.values()) {
            activities.put(activityType.getTypeCode(), emptyInstance(activityType.getTypeCode()));
        }

        return new Retro.Builder()
                .withTime(DateTime.now())
                .withActivities(activities);
    }
}

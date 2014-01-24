package be.cegeka.retrobox.util;

import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import be.cegeka.retrobox.domain.ActivityExecution;
import be.cegeka.retrobox.domain.Retro;
import be.cegeka.retrobox.presentation.newretro.RetroCreationContext;

import static be.cegeka.retrobox.domain.ActivityType.SETTING_THE_STAGE;
import static org.joda.time.Duration.standardMinutes;

public class ActivityOverviewHelper {
    private static final String EMPTY_STRING = "";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormat.forPattern("HH:mm");
    private static final int MILLIS_IN_SECOND = 1000;
    private static final int MILLIS_IN_MINUTE = MILLIS_IN_SECOND * 60;
    private static final int MILLIS_IN_HOUR = MILLIS_IN_MINUTE * 60;

    private RetroCreationContext retroCreationContext;

    public ActivityOverviewHelper(RetroCreationContext retroCreationContext) {
        this.retroCreationContext = retroCreationContext;
    }

    public String determineActivityStartTimeText(ActivityExecution execution) {
        if (execution.isEmpty()) {
            if (execution.getActivityTypeCode() == SETTING_THE_STAGE.getTypeCode()) {
                return TIME_FORMATTER.print(currentRetro().getTime());
            }
        }
        return EMPTY_STRING;
    }

    public String determineActivityEndTimeText(ActivityExecution activity) {
        if (activity.isEmpty()) {
            return EMPTY_STRING;
        }
        return null;
    }

    public String totalRetroTime() {
        long durationMillis = currentRetroDuration().getMillis();
        if (durationMillis > 0l) {
            long hours = durationMillis / MILLIS_IN_HOUR;
            durationMillis %= MILLIS_IN_HOUR;
            long minutes = durationMillis / MILLIS_IN_MINUTE;
            durationMillis %= MILLIS_IN_MINUTE;
            long seconds = durationMillis / MILLIS_IN_SECOND;

            return partToString(hours) + ":" + partToString(minutes) + ":" + partToString(seconds);
        } else {
            return "0";
        }
    }

    private String partToString(long part) {
        String partString = String.valueOf(part);
        if (part >= 10) {
            return partString;
        }
        return "0" + partString;
    }

    private Duration currentRetroDuration() {
        Duration duration = new Duration(0l);
        for (ActivityExecution execution : retroCreationContext.currentActivities()) {
            if (!execution.isEmpty()) {
                duration = duration.plus(standardMinutes(execution.getActivity().getDurationMinutes()));
            }
        }
        return duration;
    }

    private Retro currentRetro() {
        return retroCreationContext.currentRetro();
    }
}

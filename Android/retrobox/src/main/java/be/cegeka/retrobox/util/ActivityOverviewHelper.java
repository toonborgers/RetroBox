package be.cegeka.retrobox.util;

import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import be.cegeka.retrobox.domain.ActivityExecution;
import be.cegeka.retrobox.domain.Retro;
import be.cegeka.retrobox.newretro.RetroCreationContext;

import static be.cegeka.retrobox.domain.ActivityType.SETTING_THE_STAGE;
import static org.joda.time.Duration.standardMinutes;

public class ActivityOverviewHelper {
    private static final String EMPTY_STRING = "";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormat.forPattern("HH:mm");

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
        Duration duration = new Duration(0l);
        for (ActivityExecution execution : retroCreationContext.currentActivities()) {
            if (!execution.isEmpty()) {
                duration = duration.plus(standardMinutes(execution.getActivity().getDurationMinutes()));
            }
        }
        if (duration.getMillis() > 0l) {
            return new PeriodFormatterBuilder()
                    .appendHours()
                    .appendSuffix(":")
                    .appendMinutes()
                    .appendSuffix(":")
                    .appendSeconds()
                    .toFormatter()
                    .print(duration.toPeriod());
        } else {
            return "0";
        }

    }

    private Retro currentRetro() {
        return retroCreationContext.currentRetro();
    }
}

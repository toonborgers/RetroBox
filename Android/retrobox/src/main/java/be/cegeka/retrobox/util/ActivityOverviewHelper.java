package be.cegeka.retrobox.util;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import be.cegeka.retrobox.domain.Activity;
import be.cegeka.retrobox.domain.Retro;
import be.cegeka.retrobox.newretro.NewRetroController;

public class ActivityOverviewHelper {
    private static final String EMPTY_STRING = "";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormat.forPattern("HH:mm");

    private NewRetroController newRetroController;

    public ActivityOverviewHelper(NewRetroController newRetroController) {
        this.newRetroController = newRetroController;
    }

    public String determineActivityStartTimeText(Activity activity) {
        if (activity.isEmpty()) {
            if (activity.getActivityType() == Activity.ActivityType.SETTING_THE_STAGE) {
                return TIME_FORMATTER.print(currentRetro().getTime());
            }
        }
        return EMPTY_STRING;
    }

    public String determineActivityEndTimeText(Activity activity) {
        if (activity.isEmpty()) {
            return EMPTY_STRING;
        }
        return null;
    }

    private Retro currentRetro() {
        return newRetroController.currentRetro();
    }
}

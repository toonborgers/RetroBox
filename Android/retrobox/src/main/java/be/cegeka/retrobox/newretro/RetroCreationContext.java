package be.cegeka.retrobox.newretro;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

import be.cegeka.retrobox.db.RetroRepository;
import be.cegeka.retrobox.domain.Activity;
import be.cegeka.retrobox.domain.ActivityExecution;
import be.cegeka.retrobox.domain.ActivityType;
import be.cegeka.retrobox.domain.Retro;
import be.cegeka.retrobox.domain.RetroFactory;

import static be.cegeka.retrobox.domain.ActivityExecution.forActivity;

public class RetroCreationContext {
    private RetroRepository retroRepository;
    private Retro.Builder currentRetroBuilder;
    private RetroActivitiesScreen retroActivitiesScreen;

    public RetroCreationContext(RetroRepository retroRepository) {
        this.retroRepository = retroRepository;
    }

    public void startCreatingNewRetro() {
        currentRetroBuilder = RetroFactory.newRetroBuilder();
    }

    public void retroDateSet(LocalDate newDate) {
        currentRetroBuilder.withTime(currentRetro()
                .getTime()
                .withDate(newDate.getYear(), newDate.getMonthOfYear(), newDate.getDayOfMonth())
        );
    }

    public void retroTimeSet(LocalTime newTime) {
        currentRetroBuilder.withTime(currentRetro()
                .getTime()
                .withTime(newTime.getHourOfDay(), newTime.getMinuteOfHour(), 0, 0)
        );
        if (retroActivitiesScreen != null) {
            retroActivitiesScreen.activitiesInfoChanged();
        }
    }

    public void retroNameSet(String newName) {
        if (currentRetroBuilder != null) {
            currentRetroBuilder.withName(newName);
        }
    }

    public void retroPlaceSet(String newPlace) {
        if (currentRetroBuilder != null) {
            currentRetroBuilder.withLocation(newPlace);
        }
    }

    public boolean currentRetroIsValid() {
        return !currentRetro().getName().isEmpty();
    }

    public boolean storeCurrentRetro() {
        return retroRepository.insert(currentRetro());
    }

    public void doneCreatingNewRetro() {
        currentRetroBuilder = null;
    }

    public Retro currentRetro() {
        return currentRetroBuilder.build();
    }

    public List<ActivityExecution> currentActivities() {
        List<ActivityExecution> result = new ArrayList<ActivityExecution>();
        for (ActivityType type : ActivityType.values()) {
            result.add(currentRetro().getActivities().get(type.getTypeCode()));
        }
        return result;
    }

    public void setRetroActivitiesScreen(RetroActivitiesScreen retroActivitiesScreen) {
        this.retroActivitiesScreen = retroActivitiesScreen;
    }

    public void unsetScreen() {
        this.retroActivitiesScreen = null;
    }

    public void activitySelected(Activity activity) {
        currentRetro().getActivities().put(activity.getActivityTypeCode(), forActivity(activity));
        if (retroActivitiesScreen != null) {
            retroActivitiesScreen.activitiesInfoChanged();
        }
    }

    public void moveToPage(int position) {
        if (retroActivitiesScreen != null) {
            if (position == 1) {
                retroActivitiesScreen.activate();
            } else {
                retroActivitiesScreen.deactivate();
            }
        }
    }

    public interface RetroActivitiesScreen {
        void activitiesInfoChanged();

        void activate();

        void deactivate();
    }
}

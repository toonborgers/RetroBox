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
    private Retro currentRetro;
    private RetroActivitiesScreen retroActivitiesScreen;

    public RetroCreationContext(RetroRepository retroRepository) {
        this.retroRepository = retroRepository;
    }

    public void startCreatingNewRetro() {
        currentRetro = RetroFactory.newRetroBuilder().build();
    }

    public void retroDateSet(LocalDate newDate) {
        currentRetro.setTime(currentRetro()
                .getTime()
                .withDate(newDate.getYear(), newDate.getMonthOfYear(), newDate.getDayOfMonth())
        );
    }

    public void retroTimeSet(LocalTime newTime) {
        currentRetro.setTime(currentRetro()
                .getTime()
                .withTime(newTime.getHourOfDay(), newTime.getMinuteOfHour(), 0, 0)
        );
        if (retroActivitiesScreen != null) {
            retroActivitiesScreen.activitiesInfoChanged();
        }
    }

    public void retroNameSet(String newName) {
        if (currentRetro != null) {
            currentRetro.setName(newName);
        }
    }

    public void retroPlaceSet(String newPlace) {
        if (currentRetro != null) {
            currentRetro.setLocation(newPlace);
        }
    }

    public boolean currentRetroIsValid() {
        return !currentRetro().getName().isEmpty();
    }

    public boolean storeCurrentRetro() {
        return retroRepository.insert(currentRetro());
    }

    public void doneCreatingNewRetro() {
        currentRetro = null;
    }

    public Retro currentRetro() {
        return currentRetro;
    }

    public List<ActivityExecution> currentActivities() {
        List<ActivityExecution> result = new ArrayList<ActivityExecution>();
        for (ActivityType type : ActivityType.values()) {
            result.add(currentRetro.getActivities().get(type.getTypeCode()));
        }
        return result;
    }

    public void setRetroActivitiesScreen(RetroActivitiesScreen retroActivitiesScreen) {
        this.retroActivitiesScreen = retroActivitiesScreen;
    }

    public void unsetRetroActivitiesScreen() {
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

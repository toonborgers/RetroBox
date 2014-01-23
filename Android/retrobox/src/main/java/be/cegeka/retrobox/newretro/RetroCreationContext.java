package be.cegeka.retrobox.newretro;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

import be.cegeka.retrobox.db.RetroRepository;
import be.cegeka.retrobox.domain.Activity;
import be.cegeka.retrobox.domain.Retro;
import be.cegeka.retrobox.domain.RetroFactory;

public class RetroCreationContext {
    private RetroRepository retroRepository;
    private Retro.Builder currentRetroBuilder;
    private TimeSetListener listener;

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
        if (listener != null) {
            listener.timeSet();
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
        return retroRepository.store(currentRetro());
    }

    public void doneCreatingNewRetro() {
        currentRetroBuilder = null;
    }

    public Retro currentRetro() {
        return currentRetroBuilder.build();
    }

    public List<Activity> currentActivities() {
        List<Activity> result = new ArrayList<Activity>();
        for (Activity.ActivityType type : Activity.ActivityType.values()) {
            result.add(currentRetro().getActivities().get(type.getTypeCode()));
        }
        return result;
    }

    public void setTimeSetListener(TimeSetListener listener) {
        this.listener = listener;
    }

    public void clearTimeSetListener() {
        this.listener = null;
    }

    public interface TimeSetListener {
        public void timeSet();
    }
}

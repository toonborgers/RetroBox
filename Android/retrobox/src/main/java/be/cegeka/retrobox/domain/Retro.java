package be.cegeka.retrobox.domain;

import org.joda.time.LocalDateTime;

import java.util.List;

public class Retro {
    private String name;
    private LocalDateTime time;
    private String location;
    private List<Activity> activities;

    public Retro(String name, LocalDateTime time, String location, List<Activity> activities) {
        this.name = name;
        this.time = time;
        this.location = location;
        this.activities = activities;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public List<Activity> getActivities() {
        return activities;
    }
}

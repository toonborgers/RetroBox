package be.cegeka.retrobox.domain;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class Retro {
    private int id;
    private String name;
    private DateTime time;
    private String location;
    private List<Activity> activities = new ArrayList<Activity>();

    private Retro() {
    }

    public String getName() {
        return name;
    }

    public DateTime getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public long getId() {
        return id;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public static class Builder {
        private Retro instance;

        public Builder() {
            instance = new Retro();
        }

        public Builder withId(int id) {
            instance.id = id;
            return this;
        }

        public Builder withName(String name) {
            instance.name = name;
            return this;
        }

        public Builder withTime(DateTime time) {
            instance.time = time;
            return this;
        }

        public Builder withLocation(String location) {
            instance.location = location;
            return this;
        }

        public Builder withActivities(List<Activity> activities) {
            instance.activities = activities;
            return this;
        }

        public Retro build() {
            return instance;
        }
    }
}

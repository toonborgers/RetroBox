package be.cegeka.retrobox.domain;

import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

public class Retro {
    private int id;
    private String name;
    private DateTime time;
    private String location;
    private Map<Integer, ActivityExecution> activities = new HashMap<Integer, ActivityExecution>();

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

    public Map<Integer, ActivityExecution> getActivities() {
        return activities;
    }

    public void setTime(DateTime time) {
        this.time = time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
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

        public Builder withActivities(Map<Integer, ActivityExecution> activities) {
            instance.activities = activities;
            return this;
        }

        public Retro build() {
            return instance;
        }
    }
}

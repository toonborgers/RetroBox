package be.cegeka.retrobox.domain;

public class Activity {

    private int id;
    private int activityTypeCode;
    private String name;
    private String description;
    private String howto;
    private String materials;
    private int durationMinutes;

    private Activity() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getHowto() {
        return howto;
    }

    public String getMaterials() {
        return materials;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public int getActivityTypeCode() {
        return activityTypeCode;
    }


    public void setId(int id) {
        this.id = id;
    }

    public static class Builder {
        private Activity instance;

        public Builder() {
            instance = new Activity();
        }

        public Builder withId(int id) {
            instance.id = id;
            return this;
        }

        public Builder withName(String name) {
            instance.name = name;
            return this;
        }

        public Builder withDescription(String description) {
            instance.description = description;
            return this;
        }


        public Builder withHowto(String howto) {
            instance.howto = howto;
            return this;
        }

        public Builder withMaterials(String materials) {
            instance.materials = materials;
            return this;
        }

        public Builder withDurationMinutes(int durationMinutes) {
            instance.durationMinutes = durationMinutes;
            return this;
        }

        public Builder withActivityTypeCode(int activityTypeCode) {
            instance.activityTypeCode = activityTypeCode;
            return this;
        }

        public Activity build() {
            return instance;
        }
    }
}

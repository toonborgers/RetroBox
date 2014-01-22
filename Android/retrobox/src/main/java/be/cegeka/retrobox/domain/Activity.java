package be.cegeka.retrobox.domain;

import be.cegeka.retrobox.R;

import static be.cegeka.retrobox.domain.Activity.ActivityType.forTypeCode;

public class Activity {
    public enum ActivityType {
        SETTING_THE_STAGE(1, R.string.activities_set_stage, R.color.activity_type_setting_stage),
        GATHERING_DATA(2, R.string.activities_gathering_data, R.color.activity_type_gathering_data),
        ANALYZING_DATA(3, R.string.activities_analyzing_data, R.color.activity_type_analyzing_data),
        FORMULATING_ACTIONS(4, R.string.activities_formulating_actions, R.color.activity_type_formulate_actions),
        RETRO_RETRO(5, R.string.activities_retro_of_retro, R.color.activity_type_retro_retro);

        private int typeCode;
        private int titleResource;
        private int colorResource;

        private ActivityType(int typeCode, int titleResource, int colorResource) {
            this.typeCode = typeCode;
            this.titleResource = titleResource;
            this.colorResource = colorResource;
        }

        public int getTypeCode() {
            return typeCode;
        }

        public int getTitleResource() {
            return titleResource;
        }

        public int getColorResource() {
            return colorResource;
        }

        public static ActivityType forTypeCode(int typeCode) {
            for (ActivityType type : values()) {
                if (type.typeCode == typeCode) {
                    return type;
                }
            }
            return null;
        }
    }

    private int id;
    private int activityTypeCode;
    private String name;
    private String description;
    private String howto;
    private String materials;
    private int durationMinutes;
    private boolean isEmpty;

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

    public boolean isEmpty() {
        return isEmpty;
    }

    public ActivityType getActivityType() {
        return forTypeCode(activityTypeCode);
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

        public Builder withIsEmpty(boolean isEmpty) {
            instance.isEmpty = isEmpty;
            return this;
        }

        public Activity build() {
            return instance;
        }
    }
}

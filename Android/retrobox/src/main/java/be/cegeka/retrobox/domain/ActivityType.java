package be.cegeka.retrobox.domain;

import be.cegeka.retrobox.R;

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

    public static be.cegeka.retrobox.domain.ActivityType forTypeCode(int typeCode) {
        for (be.cegeka.retrobox.domain.ActivityType type : values()) {
            if (type.typeCode == typeCode) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown activity type " + typeCode);
    }
}

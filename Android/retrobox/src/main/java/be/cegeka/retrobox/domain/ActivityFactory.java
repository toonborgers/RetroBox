package be.cegeka.retrobox.domain;

public class ActivityFactory {
    public static Activity newEmptyActivity(int typeCode) {
        return new Activity.Builder()
                .withIsEmpty(true)
                .withActivityTypeCode(typeCode)
                .build();
    }
}

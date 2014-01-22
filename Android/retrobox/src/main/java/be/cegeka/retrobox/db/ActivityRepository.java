package be.cegeka.retrobox.db;

import android.content.ContentValues;

import be.cegeka.retrobox.domain.Activity;

public class ActivityRepository {
    private RetroBoxDBHelper retroBoxDBHelper;

    public ActivityRepository(RetroBoxDBHelper retroBoxDBHelper) {
        this.retroBoxDBHelper = retroBoxDBHelper;
    }

    public void storeActivityType(int typeId, String description) {
        ContentValues values = new ContentValues();
        values.put(RetroBoxContract.ActivityTypes.COL_TYPE_ID, typeId);
        values.put(RetroBoxContract.ActivityTypes.COL_DESCRIPTION, description);
        retroBoxDBHelper.getWritableDatabase()
                .insert(RetroBoxContract.ActivityTypes.TABLE_NAME, null, values);
    }

    public void storeActivity(Activity activity) {
        ContentValues values = new ContentValues();
        values.put(RetroBoxContract.Activities.COL_NAME, activity.getName());
        values.put(RetroBoxContract.Activities.COL_ACTIVITY_TYPE_ID, activity.getActivityTypeCode());
        values.put(RetroBoxContract.Activities.COL_DESCRIPTION, activity.getDescription());
        values.put(RetroBoxContract.Activities.COL_DURATION, activity.getDurationMinutes());
        values.put(RetroBoxContract.Activities.COL_HOWTO, activity.getHowto());
        values.put(RetroBoxContract.Activities.COL_MATERIALS, activity.getMaterials());
        retroBoxDBHelper.getWritableDatabase()
                .insert(RetroBoxContract.Activities.TABLE_NAME, null, values);
    }
}

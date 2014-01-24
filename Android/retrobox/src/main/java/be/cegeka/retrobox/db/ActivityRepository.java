package be.cegeka.retrobox.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

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

    public long storeActivity(Activity activity) {
        ContentValues values = new ContentValues();
        values.put(RetroBoxContract.Activities.COL_NAME, activity.getName());
        values.put(RetroBoxContract.Activities.COL_ACTIVITY_TYPE_ID, activity.getActivityTypeCode());
        values.put(RetroBoxContract.Activities.COL_DESCRIPTION, activity.getDescription());
        values.put(RetroBoxContract.Activities.COL_DURATION, activity.getDurationMinutes());
        values.put(RetroBoxContract.Activities.COL_HOWTO, activity.getHowto());
        values.put(RetroBoxContract.Activities.COL_MATERIALS, activity.getMaterials());
        return retroBoxDBHelper.getWritableDatabase()
                .insert(RetroBoxContract.Activities.TABLE_NAME, null, values);
    }

    public void storeActivities(List<Activity> activities) {
        String sql = new StringBuilder()
                .append("INSERT INTO " + RetroBoxContract.Activities.TABLE_NAME)
                .append(" (")
                .append(RetroBoxContract.Activities.COL_NAME + ",")
                .append(RetroBoxContract.Activities.COL_ACTIVITY_TYPE_ID + ",")
                .append(RetroBoxContract.Activities.COL_DESCRIPTION + ",")
                .append(RetroBoxContract.Activities.COL_DURATION + ",")
                .append(RetroBoxContract.Activities.COL_HOWTO + ",")
                .append(RetroBoxContract.Activities.COL_MATERIALS + ")")
                .append(" VALUES (?,?,?,?,?,?);")
                .toString();
        SQLiteDatabase db = retroBoxDBHelper.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement(sql);
        db.beginTransaction();
        for (Activity act : activities) {
            stmt.clearBindings();
            stmt.bindString(1, act.getName());
            stmt.bindLong(2, act.getActivityTypeCode());
            stmt.bindString(3, act.getDescription());
            stmt.bindLong(4, act.getDurationMinutes());
            stmt.bindString(5, act.getHowto());
            stmt.bindString(6, act.getMaterials());
            stmt.execute();
        }
        db.setTransactionSuccessful();
        db.endTransaction();

    }

    public List<Activity> getActivitiesOfType(int activityTypeCode) {
        Cursor cursor = retroBoxDBHelper
                .getReadableDatabase()
                .query(RetroBoxContract.Activities.TABLE_NAME,
                        null,
                        RetroBoxContract.Activities.COL_ACTIVITY_TYPE_ID + "=?",
                        new String[]{String.valueOf(activityTypeCode)},
                        null,
                        null,
                        RetroBoxContract.Activities.COL_DESCRIPTION + " ASC");

        List<Activity> result = new ArrayList<Activity>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            result.add(mapActivity(cursor));
            cursor.moveToNext();
        }
        cursor.close();

        return result;
    }

    public Activity getActivity(int activityId) {
        Cursor cursor = retroBoxDBHelper
                .getReadableDatabase()
                .query(RetroBoxContract.Activities.TABLE_NAME,
                        null,
                        RetroBoxContract.Activities._ID + "=?",
                        new String[]{String.valueOf(activityId)},
                        null,
                        null,
                        null);
        cursor.moveToFirst();
        Activity activity = mapActivity(cursor);
        cursor.close();
        return activity;
    }

    private Activity mapActivity(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(RetroBoxContract.Activities._ID));
        String name = cursor.getString(cursor.getColumnIndex(RetroBoxContract.Activities.COL_NAME));
        String description = cursor.getString(cursor.getColumnIndex(RetroBoxContract.Activities.COL_DESCRIPTION));
        int duration = cursor.getInt(cursor.getColumnIndex(RetroBoxContract.Activities.COL_DURATION));
        String howto = cursor.getString(cursor.getColumnIndex(RetroBoxContract.Activities.COL_HOWTO));
        String materials = cursor.getString(cursor.getColumnIndex(RetroBoxContract.Activities.COL_MATERIALS));
        int typeCode = cursor.getInt(cursor.getColumnIndex(RetroBoxContract.Activities.COL_ACTIVITY_TYPE_ID));

        return new Activity.Builder()
                .withId(id)
                .withDescription(description)
                .withName(name)
                .withHowto(howto)
                .withDurationMinutes(duration)
                .withMaterials(materials)
                .withActivityTypeCode(typeCode)
                .build();
    }
}

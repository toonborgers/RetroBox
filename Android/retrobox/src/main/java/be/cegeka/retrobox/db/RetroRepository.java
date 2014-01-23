package be.cegeka.retrobox.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import be.cegeka.retrobox.domain.ActivityExecution;
import be.cegeka.retrobox.domain.Retro;

import static be.cegeka.retrobox.db.RetroBoxContract.ActivityExecutions;
import static be.cegeka.retrobox.db.RetroBoxContract.Retros;

public class RetroRepository {
    private RetroBoxDBHelper retroPilotDBHelper;

    public RetroRepository(RetroBoxDBHelper retroPilotDBHelper) {
        this.retroPilotDBHelper = retroPilotDBHelper;
    }

    public boolean insert(Retro retro) {
        SQLiteDatabase db = retroPilotDBHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            long id = db.insert(Retros.TABLE_NAME, null, toContentValues(retro));

            for (ActivityExecution execution : retro.getActivities().values()) {
                if (!execution.isEmpty()) {
                    db.insert(ActivityExecutions.TABLE_NAME, null, toContentValues(execution, id));
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(RetroRepository.class.getSimpleName(), "Error when inserting retro", e);
            return false;
        } finally {
            db.endTransaction();
        }

        return true;
    }

    public void remove(Retro retro) {
        retroPilotDBHelper.getWritableDatabase().delete(Retros.TABLE_NAME,
                Retros._ID + "=?", new String[]{String.valueOf(retro.getId())});
    }

    public List<Retro> getRetros() {
        Cursor cursor = retroPilotDBHelper.getReadableDatabase().query(Retros.TABLE_NAME, null, null, null, null, null, null);
        List<Retro> result = new ArrayList<Retro>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor.getColumnIndex(Retros._ID));
            String location = cursor.getString(cursor.getColumnIndex(Retros.COL_LOCATION));
            String name = cursor.getString(cursor.getColumnIndex(Retros.COL_NAME));
            long time = cursor.getLong(cursor.getColumnIndex(Retros.COL_TIME));

            result.add(new Retro.Builder()
                    .withId(id)
                    .withLocation(location)
                    .withName(name)
                    .withTime(new DateTime(time))
                    .build());
            cursor.moveToNext();
        }
        cursor.close();
        return result;
    }

    public ContentValues toContentValues(Retro retro) {
        ContentValues values = new ContentValues();
        if (retro.getId() > 0) {
            values.put(Retros._ID, retro.getId());
        }
        values.put(Retros.COL_NAME, retro.getName());
        values.put(Retros.COL_LOCATION, retro.getLocation());
        values.put(Retros.COL_TIME, toMillis(retro.getTime()));
        return values;
    }

    public ContentValues toContentValues(ActivityExecution execution, long retroId) {
        ContentValues values = new ContentValues();
        if (execution.getId() > 0) {
            values.put(ActivityExecutions._ID, execution.getId());
        }
        values.put(ActivityExecutions.COL_RETRO_ID, retroId);
        values.put(ActivityExecutions.COL_ACTIVITY_ID, execution.getActivity().getId());
        values.put(ActivityExecutions.COL_START, toMillis(execution.getStart()));
        values.put(ActivityExecutions.COL_END, toMillis(execution.getEnd()));
        return values;
    }

    private Long toMillis(DateTime dateTime) {
        return dateTime == null ? null : dateTime.getMillis();
    }
}

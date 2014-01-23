package be.cegeka.retrobox.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import be.cegeka.retrobox.R;
import be.cegeka.retrobox.domain.Activity;

import static be.cegeka.retrobox.BeanProvider.activityRepository;

public class ActivityImporter {
    private static final String TAG = ActivityImporter.class.getSimpleName();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("dd-MM-yyyy");
    private static final String SHARED_PREFERENCES_NAME = "prefs";
    private static final String KEY_VERSION_DATE = "import.version.date";

    private Context ctx;

    public ActivityImporter(Context ctx) {
        this.ctx = ctx;
    }

    public void doImport() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                importActivities();
            }
        }).run();
    }

    private void importActivities() {
        Long start = System.currentTimeMillis();
        Gson gson = new Gson();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(ctx.getResources().openRawResource(R.raw.dummydata)));
            JSONData data = gson.fromJson(reader, JSONData.class);
            importData(data);
        } catch (Exception e) {
            Log.e(TAG, "Error when parsing input", e);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                Log.e(TAG, "Error when closing reader", e);
            }
        }
        Log.d(TAG, "Total import time: " + (System.currentTimeMillis() - start));
    }

    private void importData(JSONData data) {
        LocalDate versionDate = DATE_FORMATTER.parseLocalDate(data.versionDate);
        Log.d(TAG, "Version date from json: " + versionDate.toString());
        storeActivityType(data);
        if (areNewActivities(versionDate)) {
            Log.d(TAG, "Storing " + data.activities.size() + " new activities");
            sharedPreferences()
                    .edit()
                    .putLong(KEY_VERSION_DATE, versionDate.toDateTimeAtStartOfDay().getMillis())
                    .commit();
            List<Activity> activities = new ArrayList<Activity>();
            for (JSONActivity act : data.activities) {
                Activity activity = new Activity.Builder()
                        .withName(act.name)
                        .withActivityTypeCode(act.activityType)
                        .withDescription(act.description)
                        .withDurationMinutes(act.durationMinutes)
                        .withHowto(act.howto)
                        .withMaterials(act.materials)
                        .build();
                activities.add(activity);
            }
            activityRepository().storeActivities(activities);
        }
    }

    private void storeActivityType(JSONData data) {
        if (!thereHasAlreadyBeenAnImport()) {
            for (String activityTypeId : data.activityTypes.keySet()) {
                activityRepository()
                        .storeActivityType(Integer.parseInt(activityTypeId), data.activityTypes.get(activityTypeId));
            }
        }
    }

    private boolean areNewActivities(LocalDate versionDate) {
        if (thereHasAlreadyBeenAnImport()) {
            return versionDate.toDateTimeAtStartOfDay().getMillis()
                    > sharedPreferences().getLong(KEY_VERSION_DATE, -1l);
        }
        return true;
    }

    private boolean thereHasAlreadyBeenAnImport() {
        return sharedPreferences().contains(KEY_VERSION_DATE);
    }

    private SharedPreferences sharedPreferences() {
        return ctx.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    private static class JSONData {
        String versionDate;
        Map<String, String> activityTypes;
        List<JSONActivity> activities;

        @Override
        public String toString() {
            return "Data{" +
                    "activityTypes=" + activityTypes +
                    ", activities=" + activities +
                    '}';
        }
    }

    private static class JSONActivity {
        int activityType;
        String name;
        String description;
        String howto;
        String materials;
        int durationMinutes;

        @Override
        public String toString() {
            return "JSONActivity{" +
                    "activityType='" + activityType + '\'' +
                    ", name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    ", howto='" + howto + '\'' +
                    ", materials='" + materials + '\'' +
                    ", durationMinutes=" + durationMinutes +
                    '}';
        }
    }
}

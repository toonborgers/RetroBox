package be.cegeka.retrobox.infrastructure;

import android.content.Context;

import be.cegeka.retrobox.db.ActivityRepository;
import be.cegeka.retrobox.db.RetroBoxDBHelper;
import be.cegeka.retrobox.db.RetroRepository;
import be.cegeka.retrobox.presentation.newretro.RetroCreationContext;
import be.cegeka.retrobox.util.ActivityImporter;
import be.cegeka.retrobox.util.ActivityOverviewHelper;

public class BeanProvider {
    private static Context applicationContext;

    public static void initBeanProvider(Context applicationContext) {
        BeanProvider.applicationContext = applicationContext;
    }

    private static RetroRepository retroRepository;
    private static RetroCreationContext retroCreationContext;
    private static RetroBoxDBHelper retroBoxDBHelper;
    private static ActivityRepository activityRepository;
    private static ActivityImporter activityImporter;
    private static ActivityOverviewHelper activityOverviewHelper;

    private static RetroBoxDBHelper retroBoxDBHelper() {
        if (retroBoxDBHelper == null) {
            retroBoxDBHelper = new RetroBoxDBHelper(applicationContext);
        }
        return retroBoxDBHelper;
    }

    public static RetroRepository retroRepository() {
        if (retroRepository == null) {
            retroRepository = new RetroRepository(retroBoxDBHelper());
        }
        return retroRepository;
    }

    public static ActivityRepository activityRepository() {
        if (activityRepository == null) {
            activityRepository = new ActivityRepository(retroBoxDBHelper());
        }
        return activityRepository;
    }

    public static RetroCreationContext retroCreationContext() {
        if (retroCreationContext == null) {
            retroCreationContext = new RetroCreationContext(retroRepository());
        }
        return retroCreationContext;
    }

    public static ActivityImporter activityImporter() {
        if (activityImporter == null) {
            activityImporter = new ActivityImporter(applicationContext);
        }
        return activityImporter;
    }

    public static ActivityOverviewHelper activityOverviewHelper() {
        if (activityOverviewHelper == null) {
            activityOverviewHelper = new ActivityOverviewHelper(retroCreationContext());
        }
        return activityOverviewHelper;
    }
}

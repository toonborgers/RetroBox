package be.cegeka.retrobox;

import android.app.Application;

public class RetroBoxApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BeanProvider.initBeanProvider(getApplicationContext());
        BeanProvider.activityImporter().importActivities();
    }
}

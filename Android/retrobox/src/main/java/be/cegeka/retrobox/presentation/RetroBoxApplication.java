package be.cegeka.retrobox.presentation;

import android.app.Application;

import be.cegeka.retrobox.infrastructure.BeanProvider;

public class RetroBoxApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BeanProvider.initBeanProvider(getApplicationContext());
        BeanProvider.activityImporter().doImport();
    }
}

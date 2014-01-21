package be.cegeka.retrobox;

import android.content.Context;

import be.cegeka.retrobox.db.RetroRepository;
import be.cegeka.retrobox.newretro.NewRetroController;

public class BeanProvider {
    private static Context applicationContext;

    public static void initBeanProvider(Context applicationContext) {
        BeanProvider.applicationContext = applicationContext;
    }

    private static RetroRepository retroRepository;
    private static NewRetroController newRetroController;

    public static RetroRepository getRetroRepository() {
        if (retroRepository == null) {
            retroRepository = new RetroRepository(applicationContext);
        }
        return retroRepository;
    }

    public static NewRetroController newRetroController() {
        if (newRetroController == null) {
            newRetroController = new NewRetroController(getRetroRepository());
        }
        return newRetroController;
    }
}

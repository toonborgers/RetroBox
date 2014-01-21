package be.cegeka.retrobox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class SplashActivity extends Activity {
    private static final String ALREADY_OPENED = "HAS_ALREADY_OPENED_APP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isFirstTimeOpening()) {
            startActivity(new Intent(this, InformationActivity.class));
            PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(ALREADY_OPENED, true).commit();
        } else {
            startActivity(new Intent(this, OverviewActivity.class));
        }
    }

    private boolean isFirstTimeOpening() {
        return !PreferenceManager.getDefaultSharedPreferences(this).getBoolean(ALREADY_OPENED, false);
    }
}

package be.cegeka.retrobox.newretro.selectactivity;

import android.app.Activity;
import android.os.Bundle;

import be.cegeka.retrobox.R;
import be.cegeka.retrobox.newretro.NewRetroActivitiesFragment;

public class SelectActivityActivity extends Activity implements ActivityOverviewFragment.ActivitySelectedListener {
    private int activityType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_activity);
        activityType = getIntent().getIntExtra(NewRetroActivitiesFragment.KEY_ACTIVITY_TYPE, 1);

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_select_container, ActivityOverviewFragment.newInstance(activityType))
                    .commit();
        }
    }

    @Override
    public void activitySelected(int activityId) {
        getFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.activity_select_container, ActivityDetailFragment.newInstance(activityId))
                .commit();
    }
}
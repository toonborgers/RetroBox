package be.cegeka.retrobox.presentation.newretro.newactivity;

import android.app.Activity;
import android.os.Bundle;

import be.cegeka.retrobox.R;
import be.cegeka.retrobox.presentation.newretro.selectactivity.SelectActivityActivity;

public class NewActivityActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_activity);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, NewActivityFragment.newInstance(getIntent().getIntExtra(SelectActivityActivity.ACTIVITY_TYPE, 1)))
                    .commit();
        }
    }
}

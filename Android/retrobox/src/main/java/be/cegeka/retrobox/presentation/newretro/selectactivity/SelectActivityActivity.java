package be.cegeka.retrobox.presentation.newretro.selectactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import be.cegeka.retrobox.R;
import be.cegeka.retrobox.presentation.AboutActivity;
import be.cegeka.retrobox.presentation.newretro.activityoverview.NewRetroActivitiesFragment;

public class SelectActivityActivity extends Activity implements ActivityOverviewFragment.ActivitySelectedListener {
    public static final String DETAIL_FRAGMENT = "detailfragment";
    private int activityType;
    private Menu menu;
    private MenuItem addNewActivityItem;

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
                .setCustomAnimations(
                        R.anim.activity_detail_in,//Detail scherm roteert binnen
                        R.anim.activity_detail_out, //Detail scherm roteert weg
                        R.anim.activity_list_in, //Lijst roteert binnen
                        R.anim.activity_list_out //Lijst roteert weg
                )
                .replace(R.id.activity_select_container, ActivityDetailFragment.newInstance(activityId), DETAIL_FRAGMENT)
                .addToBackStack(null)
                .commit();
        menu.removeItem(addNewActivityItem.getItemId());
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().findFragmentByTag(DETAIL_FRAGMENT) instanceof ActivityDetailFragment) {
            menu.add(addNewActivityItem.getGroupId(),
                    addNewActivityItem.getItemId(),
                    addNewActivityItem.getOrder(),
                    addNewActivityItem.getTitle())
                    .setIcon(R.drawable.add_icon)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.selectactivity, menu);
        this.menu = menu;
        addNewActivityItem = menu.findItem(R.id.activity_overview_new);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.select_activity_info) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

package be.cegeka.retrobox.newretro;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import be.cegeka.retrobox.R;
import be.cegeka.retrobox.newretro.selectactivity.SelectActivityActivity;
import butterknife.ButterKnife;
import butterknife.InjectView;

import static be.cegeka.retrobox.BeanProvider.activityOverviewHelper;
import static be.cegeka.retrobox.BeanProvider.retroCreationContext;

public class NewRetroActivitiesFragment extends Fragment implements RetroCreationContext.RetroActivitiesScreen {
    public static final String KEY_ACTIVITY_TYPE = "activity_type";
    public static final int REQUEST_CODE_SELECT_ACTIVITY = 1;

    @InjectView(R.id.activities_list)
    ListView activitiesList;
    @InjectView(R.id.activities_total_retro_time)
    TextView totalTimeView;

    private NewRetroActivitiesAdapter adapter;
    private boolean isActive = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_new_activities, container, false);
        ButterKnife.inject(this, mainView);

        adapter = new NewRetroActivitiesAdapter(getActivity(), retroCreationContext().currentActivities());
        activitiesList.setAdapter(adapter);
        totalTimeView.setText(String.format(getString(R.string.activities_total_retro), "0"));
        activitiesList.setOnItemClickListener(new ActivitiesListClickListener());
        return mainView;
    }

    @Override
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
        retroCreationContext().setRetroActivitiesScreen(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        retroCreationContext().unsetScreen();
    }

    @Override
    public void activitiesInfoChanged() {
        adapter.clear();
        adapter.addAll(retroCreationContext().currentActivities());
        adapter.notifyDataSetChanged();
        totalTimeView.setText(String.format(getString(R.string.activities_total_retro), activityOverviewHelper().totalRetroTime()));
    }

    @Override
    public void activate() {
        isActive = true;
    }

    @Override
    public void deactivate() {
        isActive = false;
    }

    private class ActivitiesListClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            if (isActive) {
                Intent intent = new Intent(getActivity(), SelectActivityActivity.class);
                intent.putExtra(KEY_ACTIVITY_TYPE, adapter.getItem(position).getActivityTypeCode());
                startActivityForResult(intent, REQUEST_CODE_SELECT_ACTIVITY);
            }
        }
    }
}

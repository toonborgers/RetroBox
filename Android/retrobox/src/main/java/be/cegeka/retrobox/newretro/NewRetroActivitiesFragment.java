package be.cegeka.retrobox.newretro;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import be.cegeka.retrobox.R;
import butterknife.ButterKnife;
import butterknife.InjectView;

import static be.cegeka.retrobox.BeanProvider.newRetroController;

public class NewRetroActivitiesFragment extends Fragment implements NewRetroController.TimeSetListener {
    @InjectView(R.id.activities_list)
    ListView activitiesList;
    @InjectView(R.id.activities_total_retro_time)
    TextView totalTimeView;

    private NewRetroActivitiesAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_new_activities, container, false);
        ButterKnife.inject(this, mainView);

        adapter = new NewRetroActivitiesAdapter(getActivity(), newRetroController().currentActivities());
        activitiesList.setAdapter(adapter);
        totalTimeView.setText("0");
        return mainView;
    }

    @Override
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
        newRetroController().setTimeSetListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        newRetroController().clearTimeSetListener();
    }

    @Override
    public void timeSet() {
        adapter.clear();
        adapter.addAll(newRetroController().currentActivities());
        adapter.notifyDataSetChanged();
    }
}

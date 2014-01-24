package be.cegeka.retrobox.presentation.newretro.selectactivity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import be.cegeka.retrobox.R;
import be.cegeka.retrobox.domain.Activity;
import butterknife.ButterKnife;
import butterknife.InjectView;

import static be.cegeka.retrobox.infrastructure.BeanProvider.activityRepository;
import static be.cegeka.retrobox.infrastructure.BeanProvider.retroCreationContext;

public class ActivityDetailFragment extends Fragment implements View.OnClickListener {
    public static final String ACTIVITY_ID = "activityId";

    @InjectView(R.id.activity_detail_title)
    TextView title;
    @InjectView(R.id.activity_detail_description)
    TextView description;
    @InjectView(R.id.activity_detail_materials)
    TextView materials;
    @InjectView(R.id.activity_detail_duration)
    TextView duration;
    @InjectView(R.id.activity_detail_select)
    Button selectActivityButton;

    private ActivityDetailFragment() {

    }

    public static ActivityDetailFragment newInstance(int activityId) {
        Bundle args = new Bundle();
        args.putInt(ACTIVITY_ID, activityId);
        ActivityDetailFragment fragment = new ActivityDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_activity_detail, container, false);
        ButterKnife.inject(this, layout);
        Activity activity = activityRepository().getActivity(getArguments().getInt(ACTIVITY_ID));
        title.setText(activity.getName());
        description.setText(activity.getDescription() + "\n\n" + activity.getHowto());
        materials.setText(activity.getMaterials());
        duration.setText(String.format(getString(R.string.activity_detail_duration_value), activity.getDurationMinutes()));
        selectActivityButton.setOnClickListener(this);
        return layout;
    }

    @Override
    public void onClick(View view) {
        retroCreationContext().activitySelected(activityRepository().getActivity(getArguments().getInt(ACTIVITY_ID)));
        getActivity().finish();
    }
}

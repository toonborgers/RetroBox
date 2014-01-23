package be.cegeka.retrobox.newretro.selectactivity;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import be.cegeka.retrobox.domain.ActivityType;

import static be.cegeka.retrobox.BeanProvider.activityRepository;

public class ActivityOverviewFragment extends ListFragment {
    public static final String KEY_TYPE = "type";
    private ActivitySelectedListener listener;
    private ActivityOverviewAdapter adapter;

    private ActivityOverviewFragment() {

    }

    public static ActivityOverviewFragment newInstance(int activityType) {
        Bundle args = new Bundle();
        args.putInt(KEY_TYPE, activityType);
        ActivityOverviewFragment fragment = new ActivityOverviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ActivitySelectedListener) {
            this.listener = (ActivitySelectedListener) activity;
        }
    }

    @Override
    public void onDetach() {
        this.listener = null;
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int activityTypeCode = getArguments().getInt(KEY_TYPE, 1);
        getActivity().setTitle(ActivityType.forTypeCode(activityTypeCode).getTitleResource());
        adapter = new ActivityOverviewAdapter(getActivity(), activityRepository().getActivitiesOfType(activityTypeCode));
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (listener != null) {
            listener.activitySelected(adapter.getItem(position).getId());
        }
    }

    public interface ActivitySelectedListener {
        void activitySelected(int activityId);
    }
}

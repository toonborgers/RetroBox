package be.cegeka.retrobox.presentation.newretro.newactivity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import be.cegeka.retrobox.R;
import be.cegeka.retrobox.domain.Activity;
import butterknife.ButterKnife;
import butterknife.InjectView;

import static be.cegeka.retrobox.infrastructure.BeanProvider.activityRepository;
import static be.cegeka.retrobox.infrastructure.BeanProvider.retroCreationContext;

public class NewActivityFragment extends Fragment {
    public static final String ACTIVITY_TYPE = "activity type";
    @InjectView(R.id.new_activity_duration_toprevious)
    ImageButton previousButton;
    @InjectView(R.id.new_activity_duration_tonext)
    ImageButton nextButton;
    @InjectView(R.id.new_activity_duration_next)
    TextView tvNext;
    @InjectView(R.id.new_activity_duration_current)
    TextView tvCurrent;
    @InjectView(R.id.new_activity_duration_previous)
    TextView tvPrevious;
    @InjectView(R.id.new_activity_name)
    EditText etName;
    @InjectView(R.id.new_activity_description)
    EditText etDescription;
    @InjectView(R.id.new_activity_howto)
    EditText etHowto;
    @InjectView(R.id.new_activity_materials)
    EditText etMaterials;
    @InjectView(R.id.new_activity_done)
    Button btDone;

    private int currentDuration = 30;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_activity, container, false);
        ButterKnife.inject(this, view);
        setUpButtonListeners();
        return view;
    }

    private void setUpButtonListeners() {
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentDuration > 5) {
                    setDurationBack();
                }
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentDuration < 55) {
                    setDurationForward();
                }
            }
        });

        btDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (allFieldsFilledIn()) {
                    storeNewActivityAndFinish();
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.new_activity_error), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void storeNewActivityAndFinish() {
        Activity newActivity = new Activity.Builder()
                .withName(etName.getText().toString())
                .withDescription(etDescription.getText().toString())
                .withMaterials(etMaterials.getText().toString())
                .withHowto(etHowto.getText().toString())
                .withDurationMinutes(currentDuration)
                .withActivityTypeCode(getArguments().getInt(ACTIVITY_TYPE, 1))
                .build();
        Long id = activityRepository().storeActivity(newActivity);
        newActivity.setId(id.intValue());
        retroCreationContext().activitySelected(newActivity);
        getActivity().setResult(android.app.Activity.RESULT_OK);
        getActivity().finish();
    }

    private boolean allFieldsFilledIn() {
        return fieldFilledIn(etDescription)
                && fieldFilledIn(etHowto)
                && fieldFilledIn(etMaterials)
                && fieldFilledIn(etName);
    }

    private boolean fieldFilledIn(EditText view) {
        return !view.getText().toString().trim().isEmpty();
    }

    private void setDurationForward() {
        tvPrevious.setText(String.valueOf(currentDuration));
        currentDuration += 5;
        tvCurrent.setText(String.valueOf(currentDuration));
        if (currentDuration == 55) {
            tvNext.setText("");
        } else {
            tvNext.setText(String.valueOf(currentDuration + 5));
        }
    }

    private void setDurationBack() {
        tvNext.setText(String.valueOf(currentDuration));
        currentDuration -= 5;
        tvCurrent.setText(String.valueOf(currentDuration));
        if (currentDuration == 5) {
            tvPrevious.setText("");
        } else {
            tvPrevious.setText(String.valueOf(currentDuration - 5));
        }
    }

    public static NewActivityFragment newInstance(int type) {
        NewActivityFragment fragment = new NewActivityFragment();
        Bundle args = new Bundle();
        args.putInt(ACTIVITY_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }
}

package be.cegeka.retrobox;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NewRetroActivity extends FragmentActivity {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("dd-MM-yyyy");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormat.forPattern("HH:mm");

    @InjectView(R.id.new_retro_name)
    EditText etName;

    @InjectView(R.id.new_retro_date)
    TextView tvDate;

    @InjectView(R.id.new_retro_time)
    TextView tvTime;

    @InjectView(R.id.new_retro_place)
    EditText etPlace;

    @InjectView(R.id.new_retro_done)
    Button btDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_retro);
        ButterKnife.inject(this);

        setupDateAndTimeFields();
    }

    private void setupDateAndTimeFields() {
        LocalDateTime now = LocalDateTime.now();
        tvDate.setText(DATE_FORMAT.print(now));
        tvTime.setText(TIME_FORMAT.print(now));
    }


}

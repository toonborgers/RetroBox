package be.cegeka.retropilot;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;

import com.doomonafireball.betterpickers.calendardatepicker.CalendarDatePickerDialog;

public class NewRetroActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_retro);

        Button knop = (Button) findViewById(R.id.neknop);
        knop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                CalendarDatePickerDialog timePickerDialog = CalendarDatePickerDialog
                        .newInstance(new CalendarDatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {

                            }
                        }, 2014, 1, 21);
                timePickerDialog.show(fm, "fragment_time_picker_name");
            }
        });

    }


}

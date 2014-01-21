package be.cegeka.retrobox;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AboutActivity extends Activity {
    @InjectView(R.id.about_description)
    TextView tvAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.inject(this);

        Spanned spanned = Html.fromHtml(getString(R.string.about_description));
        tvAbout.setText(spanned);
    }

}

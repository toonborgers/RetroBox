package be.cegeka.retropilot.overview;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;

import be.cegeka.retropilot.R;

public class OverviewPagerAdapter extends FragmentPagerAdapter {

    private Context ctx;

    public OverviewPagerAdapter(FragmentManager fm, Context ctx) {
        super(fm);
        this.ctx = ctx;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new PlannedRetroFragment();
            case 1:
                return new PastRetroFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return ctx.getResources().getString(R.string.overview_title_planned);
            case 1:
                return ctx.getResources().getString(R.string.overview_title_passed);
        }
        return null;
    }
}

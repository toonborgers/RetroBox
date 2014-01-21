package be.cegeka.retrobox.newretro;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;

import be.cegeka.retrobox.R;

public class NewRetroPagerAdapter extends FragmentPagerAdapter {
    private Context ctx;

    public NewRetroPagerAdapter(FragmentManager fm, Context ctx) {
        super(fm);
        this.ctx = ctx;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NewRetroDetailFragment();
            case 1:
                return new NewRetroActivitiesFragment();
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
                return ctx.getString(R.string.new_retro_tab_title_details);
            case 1:
                return ctx.getString(R.string.new_retro_tab_title_activities);
        }
        return null;
    }
}

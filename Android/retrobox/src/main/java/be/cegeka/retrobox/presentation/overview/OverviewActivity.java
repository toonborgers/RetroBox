package be.cegeka.retrobox.presentation.overview;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import be.cegeka.retrobox.R;
import be.cegeka.retrobox.presentation.AboutActivity;
import be.cegeka.retrobox.presentation.newretro.NewRetroActivity;
import be.cegeka.retrobox.util.DepthPageTransformer;

import static be.cegeka.retrobox.infrastructure.BeanProvider.retroCreationContext;

public class OverviewActivity extends Activity implements ActionBar.TabListener {
    private static final int CREATE_NEW_RETRO = 1;
    private OverviewPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private PlannedRetrosChangeListener plannedRetrosChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview_screen);

        setUpTabs();
    }

    private void setUpTabs() {
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mSectionsPagerAdapter = new OverviewPagerAdapter(getFragmentManager(), getApplicationContext());

        mViewPager = (ViewPager) findViewById(R.id.overwiew_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overview, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.overview_new:
                retroCreationContext().startCreatingNewRetro();
                startActivityForResult(new Intent(this, NewRetroActivity.class), CREATE_NEW_RETRO);
                return true;
            case R.id.overview_info:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_NEW_RETRO) {
            retroCreationContext().doneCreatingNewRetro();
            if (resultCode == RESULT_OK) {
                if (plannedRetrosChangeListener != null) {
                    plannedRetrosChangeListener.changedRetros();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void setPlannedRetrosChangeListener(PlannedRetrosChangeListener listener) {
        this.plannedRetrosChangeListener = listener;
    }

    public interface PlannedRetrosChangeListener {
        public void changedRetros();
    }
}

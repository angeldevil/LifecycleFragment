package me.angeldevil.lifecyclefragment.app;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * <p>Created by AngelDevil on 2018/5/28.</p>
 */
public class ThirdPagerAdapter extends FragmentPagerAdapter {

    private String parentLabel;

    public ThirdPagerAdapter(FragmentManager fm, String tag) {
        super(fm);
        parentLabel = tag;
    }

    @Override
    public Fragment getItem(int position) {
        return ThirdFragment.newInstance(parentLabel + "--" + "Third-" + position);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "Third- " + position;
    }
}

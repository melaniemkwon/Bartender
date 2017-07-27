package com.example.android.absolutmixr;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by melaniekwon on 7/27/17.
 */

// SOURCE: http://www.ekiras.com/2015/12/how-to-implement-material-design-tab-layout-in-android.html
public class AdapterViewPager extends FragmentPagerAdapter {
    private final List<Fragment> fragmentList = new ArrayList<Fragment>();
    private final List<String> titles = new ArrayList<String>();

    public AdapterViewPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    public void addFragment(Fragment fragment, String title){
        fragmentList.add(fragment);
        titles.add(title);
    }
}

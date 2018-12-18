package ve.com.abicelis.cryptomaster.ui.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abicelis on 30/5/2018.
 */
public class CustomViewPagerAdapter extends SmartFragmentStatePagerAdapter {
    private final List<Fragment> fragments = new ArrayList<>();

    public CustomViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }


    // Our custom method that populates this Adapter with Fragments
    public void addFragment(Fragment fragment) {
        fragments.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
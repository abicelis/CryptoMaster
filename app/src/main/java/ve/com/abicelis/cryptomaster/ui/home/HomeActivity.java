package ve.com.abicelis.cryptomaster.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;
import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.application.Constants;
import ve.com.abicelis.cryptomaster.application.Message;
import ve.com.abicelis.cryptomaster.data.model.CoinsFragmentType;
import ve.com.abicelis.cryptomaster.data.model.StartFragment;
import ve.com.abicelis.cryptomaster.ui.alarm.AlarmFragment;
import ve.com.abicelis.cryptomaster.ui.base.BaseActivity;
import ve.com.abicelis.cryptomaster.ui.coins.CoinsFragment;
import ve.com.abicelis.cryptomaster.ui.market.MarketFragment;
import ve.com.abicelis.cryptomaster.ui.preference.PreferenceFragment;
import ve.com.abicelis.cryptomaster.util.AttrUtil;
import ve.com.abicelis.cryptomaster.util.SnackbarUtil;

/**
 * Created by abicelis on 4/9/2017.
 */

public class HomeActivity extends BaseActivity implements HomeMvpView {

    @Inject
    HomePresenter mPresenter;

    @BindView(R.id.activity_home_container)
    CoordinatorLayout mContainer;
    @BindView(R.id.activity_home_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.activity_home_toolbar_title)
    TextView mToolbarTitle;

    @BindView(R.id.activity_home_viewpager)
    NoSwipePager mViewPager;
    CustomViewPagerAdapter mViewpagerAdapter;

    @BindView(R.id.activity_home_bottom_navigation)
    AHBottomNavigation mBottomNavigation;

    @BindView(R.id.activity_home_fab)
    FloatingActionButton mFab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");

        getPresenterComponent().inject(this);
        mPresenter.attachView(this);
        mPresenter.init();

        mFab.setOnClickListener(v -> {
            Fragment fragment = mViewpagerAdapter.getRegisteredFragment(0);
            if (fragment instanceof AlarmFragment)
                ((AlarmFragment)fragment).fabClicked();
        });

    }

    public Toolbar getToolbar() {
        return mToolbar;
    }
    public void setToolbarText(String text) {
        mToolbarTitle.setText(text);
    }

    public void hideFab() {
        mFab.hide();
    }
    public void showFab() {
        mFab.show();
    }

    public int getCurrentFragmentIndex() {
        return mViewPager.getCurrentItem();
    }



//        private void createFakeNotification() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                AHNotification notification = new AHNotification.Builder()
//                        .setText("1")
//                        .setBackgroundColor(AttrUtil.getAttributeColor(HomeActivity.this, R.attr.bottom_nav_notification_background))
//                        .setTextColor(AttrUtil.getAttributeColor(HomeActivity.this, R.attr.bottom_nav_notification_text))
//                        .build();
//                // Adding notification to last item.
//
//                mBottomNavigation.setNotification(notification, mBottomNavigation.getItemsCount() - 1);
//            }
//        }, 1000);
//    }





    /* HomeMvpView implementation */

    @Override
    public void setupViewPager(StartFragment startFragment) {
        mViewPager.setPagingEnabled(false);
        mViewpagerAdapter = new CustomViewPagerAdapter(getSupportFragmentManager());

        //ALARM FRAGMENT
        AlarmFragment fragment = new AlarmFragment();
        mViewpagerAdapter.addFragment(fragment);

        //MARKET FRAGMENT
        MarketFragment mFragment = new MarketFragment();
        mViewpagerAdapter.addFragment(mFragment);

        //COINS FRAGMENT
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.COINS_FRAGMENT_TYPE, CoinsFragmentType.NORMAL);
        CoinsFragment coinsFragment = new CoinsFragment();
        coinsFragment.setArguments(bundle);
        mViewpagerAdapter.addFragment(coinsFragment);

        //FAV COINS FRAGMENT
        bundle = new Bundle();
        bundle.putSerializable(Constants.COINS_FRAGMENT_TYPE, CoinsFragmentType.FAVORITES);
        coinsFragment = new CoinsFragment();
        coinsFragment.setArguments(bundle);
        mViewpagerAdapter.addFragment(coinsFragment);

        //PREFERENCE FRAGMENT
        PreferenceFragment preferenceFragment = new PreferenceFragment();
        mViewpagerAdapter.addFragment(preferenceFragment);

        mViewPager.setAdapter(mViewpagerAdapter);
        mViewPager.setCurrentItem(startFragment.getFragmentIndex());
        setToolbarTitle(startFragment.getFragmentIndex());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                setToolbarTitle(position);
                getToolbar().getMenu().clear();
                if(position == 0)
                    showFab();
                else {
                    hideFab();

                    //Notify Alarm Fragment no longer focused
                    Fragment fragment = mViewpagerAdapter.getRegisteredFragment(0);
                    if (fragment instanceof AlarmFragment)
                        ((AlarmFragment)fragment).onLostFocus();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });
    }

    private void setToolbarTitle(int position) {
        switch (position) {
            case 0:
                mToolbarTitle.setText(getString(R.string.title_alarms));
                break;

            case 1:
                mToolbarTitle.setText(getString(R.string.title_market));
                break;

            case 2:
                mToolbarTitle.setText(getString(R.string.title_coins));
                break;

            case 3:
                mToolbarTitle.setText(getString(R.string.title_favorites));
                break;

            case 4:
                mToolbarTitle.setText(getString(R.string.title_settings));
                break;
        }
    }

    @Override
    public void setupBottomNavigation(StartFragment startFragment) {
        mBottomNavigation.addItem(new AHBottomNavigationItem(getString(R.string.title_alarms), R.drawable.ic_nav_bottom_alarm));
        mBottomNavigation.addItem(new AHBottomNavigationItem(getString(R.string.title_market), R.drawable.ic_nav_bottom_market));
        mBottomNavigation.addItem(new AHBottomNavigationItem(getString(R.string.title_coins), R.drawable.ic_nav_bottom_coin));
        mBottomNavigation.addItem(new AHBottomNavigationItem(getString(R.string.title_favorites), R.drawable.ic_nav_bottom_favorite));
        mBottomNavigation.addItem(new AHBottomNavigationItem(getString(R.string.title_settings), R.drawable.ic_nav_bottom_settings));
        mBottomNavigation.setCurrentItem(startFragment.getFragmentIndex());


        mBottomNavigation.setOnTabSelectedListener((position, wasSelected) -> {

            if(!wasSelected)
                mViewPager.setCurrentItem(position);
            return true;
        });

        mBottomNavigation.setDefaultBackgroundColor(AttrUtil.getAttributeColor(this, R.attr.bottom_nav_background));
        mBottomNavigation.setAccentColor(AttrUtil.getAttributeColor(this, R.attr.bottom_nav_icon_selected));
        mBottomNavigation.setInactiveColor(AttrUtil.getAttributeColor(this, R.attr.bottom_nav_icon_unselected));
        mBottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);

    }

    @Override
    public void showMessage(Message message, @Nullable BaseTransientBottomBar.BaseCallback<Snackbar> callback) {
        SnackbarUtil.showSnackbar(mContainer, message.getMessageType(), message.getFriendlyNameRes(), SnackbarUtil.SnackbarDuration.SHORT, callback);
    }

}

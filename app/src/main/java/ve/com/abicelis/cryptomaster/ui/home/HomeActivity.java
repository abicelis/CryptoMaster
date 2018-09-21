package ve.com.abicelis.cryptomaster.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.application.Constants;
import ve.com.abicelis.cryptomaster.application.Message;
import ve.com.abicelis.cryptomaster.data.DataManager;
import ve.com.abicelis.cryptomaster.data.local.SharedPreferenceHelper;
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
    RelativeLayout mContainer;

    @BindView(R.id.activity_home_viewpager)
    NoSwipePager mViewPager;
    private BottomNavigationAdapter mViewpagerAdapter;

    @BindView(R.id.activity_home_bottom_navigation)
    AHBottomNavigation mBottomNavigation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)              //Enable fancy custom activity transitions
        //    getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        getPresenterComponent().inject(this);
        mPresenter.attachView(this);
        mPresenter.init();


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
        mViewpagerAdapter = new BottomNavigationAdapter(getSupportFragmentManager());

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

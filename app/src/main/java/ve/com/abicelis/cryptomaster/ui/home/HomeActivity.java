package ve.com.abicelis.cryptomaster.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;

import butterknife.BindView;
import butterknife.ButterKnife;
import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.application.Constants;
import ve.com.abicelis.cryptomaster.application.Message;
import ve.com.abicelis.cryptomaster.data.model.CoinsFragmentType;
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


    @BindView(R.id.activity_home_container)
    RelativeLayout mContainer;

    @BindView(R.id.activity_home_viewpager)
    NoSwipePager mViewPager;
    private BottomNavigationAdapter mViewpagerAdapter;

    @BindView(R.id.activity_home_bottom_navigation)
    AHBottomNavigation mBottomNavigation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setupBottomNavigation();
        createFakeNotification();
        setupViewpager();
    }

    private void setupViewpager() {
        mViewPager.setPagingEnabled(false);
        mViewpagerAdapter = new BottomNavigationAdapter(getSupportFragmentManager());

        Bundle bundle = new Bundle();
        bundle.putInt(ColorFragment.COLOR_OF_FRAGMENT, Color.RED);
        ColorFragment fragment = new ColorFragment();
        fragment.setArguments(bundle);
        mViewpagerAdapter.addFragment(fragment);

        //bundle = new Bundle();
        //bundle.putInt(ColorFragment.COLOR_OF_FRAGMENT, Color.GREEN);
        MarketFragment mFragment = new MarketFragment();
        //fragment.setArguments(bundle);
        mViewpagerAdapter.addFragment(mFragment);

        bundle = new Bundle();
        bundle.putSerializable(Constants.COINS_FRAGMENT_TYPE, CoinsFragmentType.NORMAL);
        CoinsFragment coinsFragment = new CoinsFragment();
        coinsFragment.setArguments(bundle);
        mViewpagerAdapter.addFragment(coinsFragment);

        bundle = new Bundle();
        bundle.putSerializable(Constants.COINS_FRAGMENT_TYPE, CoinsFragmentType.FAVORITES);
        coinsFragment = new CoinsFragment();
        coinsFragment.setArguments(bundle);
        mViewpagerAdapter.addFragment(coinsFragment);

        PreferenceFragment preferenceFragment = new PreferenceFragment();
        mViewpagerAdapter.addFragment(preferenceFragment);

        mViewPager.setAdapter(mViewpagerAdapter);
        mViewPager.setCurrentItem(Constants.MISC_START_HOME_PAGE);
    }

    private void setupBottomNavigation() {
        mBottomNavigation.addItem(new AHBottomNavigationItem(getString(R.string.title_alarms), R.drawable.ic_nav_bottom_alarm));
        mBottomNavigation.addItem(new AHBottomNavigationItem(getString(R.string.title_market), R.drawable.ic_nav_bottom_market));
        mBottomNavigation.addItem(new AHBottomNavigationItem(getString(R.string.title_coins), R.drawable.ic_nav_bottom_coin));
        mBottomNavigation.addItem(new AHBottomNavigationItem(getString(R.string.title_favorites), R.drawable.ic_nav_bottom_favorite));
        mBottomNavigation.addItem(new AHBottomNavigationItem(getString(R.string.title_settings), R.drawable.ic_nav_bottom_settings));
        mBottomNavigation.setCurrentItem(Constants.MISC_START_HOME_PAGE);


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

    private void createFakeNotification() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AHNotification notification = new AHNotification.Builder()
                        .setText("1")
                        .setBackgroundColor(AttrUtil.getAttributeColor(HomeActivity.this, R.attr.bottom_nav_notification_background))
                        .setTextColor(AttrUtil.getAttributeColor(HomeActivity.this, R.attr.bottom_nav_notification_text))
                        .build();
                // Adding notification to last item.

                mBottomNavigation.setNotification(notification, mBottomNavigation.getItemsCount() - 1);
            }
        }, 1000);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //mHomePresenter.refreshTripList(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_activity_home, menu);

        //MenuItem item = menu.findItem(R.id.menu_home_search);
        //mSearchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        switch (id) {
////            case R.id.menu_home_settings:
////                Toast.makeText(this, "Under construction", Toast.LENGTH_SHORT).show();
////                break;
//
//            case R.id.menu_home_theme:
//                new SharedPreferenceHelper().toggleAppThemeType();
//                recreate();
//                break;
//
//            case R.id.menu_home_about:
//                Intent aboutIntent = new Intent(this, AboutActivity.class);
//                startActivity(aboutIntent);
//                break;
//        }


        return super.onOptionsItemSelected(item);
    }
//
//    private void setUpToolbar() {
//        //Setup toolbar
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setTitle(R.string.app_name);
//        getSupportActionBar().setLogo(R.drawable.ic_plane);
//    }
//
//    private void setUpRecyclerView() {
//
//        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        mTripAdapter = new TripAdapter(this);
//        mTripAdapter.setTripDeletedListener(trip -> {
//            mHomePresenter.deleteTrip(trip);
//        });
//
//        mRecycler.setLayoutManager(mLayoutManager);
//        mRecycler.setAdapter(mTripAdapter);
//
//        mSwipeRefresh.setColorSchemeResources(R.color.swipe_refresh_green, R.color.swipe_refresh_red, R.color.swipe_refresh_yellow);
//        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//                                               @Override
//                                               public void onRefresh() {
//                                                   mHomePresenter.refreshTripList(null);
//                                               }
//                                           }
//        );
//    }
//
//    private void setupSearchView() {
//        mSearchView.setVoiceSearch(true);
//        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                mHomePresenter.refreshTripList(query.trim());
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                newText = newText.trim();
//                if(newText.length() > 0)
//                    mHomePresenter.refreshTripList(newText);
//                if(newText.length() == 0)
//                    mHomePresenter.refreshTripList(null);
//                return true;
//            }
//        });
//
//        mSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
//            @Override
//            public void onSearchViewShown() {
//                mAddTrip.hide();
//            }
//
//            @Override
//            public void onSearchViewClosed() {
//                mAddTrip.show();
//                mHomePresenter.refreshTripList(null);
//            }
//        });
//    }


    /* HomeMvpView implementation */

    @Override
    public void showLoading() {
        //mSwipeRefresh.setRefreshing(true);
    }


    @Override
    public void showMessage(Message message, @Nullable BaseTransientBottomBar.BaseCallback<Snackbar> callback) {
        SnackbarUtil.showSnackbar(mContainer, message.getMessageType(), message.getFriendlyNameRes(), SnackbarUtil.SnackbarDuration.SHORT, callback);
    }

//    @Override
//    public void showTrips(List<TripViewModel> trips) {
//        mSwipeRefresh.setRefreshing(false);
//        mTripAdapter.getItems().clear();
//        mTripAdapter.getItems().addAll(trips);
//        mTripAdapter.notifyDataSetChanged();
//
//        if(mTripAdapter.getItems().size() == 0) {
//            mNoItemsContainer.setVisibility(View.VISIBLE);
//            mRecycler.setVisibility(View.GONE);
//        } else {
//            mNoItemsContainer.setVisibility(View.GONE);
//            mRecycler.setVisibility(View.VISIBLE);
//        }
//    }
}

package ve.com.abicelis.cryptomaster.ui.coins;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;
import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.application.Constants;
import ve.com.abicelis.cryptomaster.application.Message;
import ve.com.abicelis.cryptomaster.data.local.SharedPreferenceHelper;
import ve.com.abicelis.cryptomaster.data.model.CoinsFragmentType;
import ve.com.abicelis.cryptomaster.data.model.CoinsToFetch;
import ve.com.abicelis.cryptomaster.ui.base.BaseFragment;
import ve.com.abicelis.cryptomaster.ui.coindetail.CoinDetailActivity;
import ve.com.abicelis.cryptomaster.ui.common.CoinsHeader;
import ve.com.abicelis.cryptomaster.ui.home.HomeActivity;
import ve.com.abicelis.cryptomaster.util.AttrUtil;
import ve.com.abicelis.cryptomaster.util.SnackbarUtil;

/**
 * Created by abicelis on 14/6/2018.
 */
public class CoinsFragment extends BaseFragment implements CoinsMvpView {

    //DATA
    @Inject
    SharedPreferenceHelper mSharedPreferenceHelper;
    CoinsFragmentType mCoinFragmentType;


    //UI
    HomeActivity mHomeActivity;

    @BindView(R.id.fragment_coins_container)
    FrameLayout mContainer;
    @BindView(R.id.fragment_coin_header)
    CoinsHeader mCoinsHeader;
    @BindView(R.id.fragment_coins_swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.fragment_coins_recycler)
    RecyclerView mRecycler;
    private LinearLayoutManager mLayoutManager;
    private CoinsListAdapter mCoinsListAdapter;


    public CoinsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPresenterComponent().inject(this);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(Constants.COINS_FRAGMENT_TYPE)) {
            mCoinFragmentType = ((CoinsFragmentType) getArguments().getSerializable(Constants.COINS_FRAGMENT_TYPE));
        } else {
            Timber.i(Message.COIN_FRAGMENT_TYPE_MISSING.getFriendlyName(getContext()));
            showMessage(Message.COIN_FRAGMENT_TYPE_MISSING, null);
            mCoinFragmentType = CoinsFragmentType.NORMAL;
        }
        setTitle();

    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_coins, container, false);
        ButterKnife.bind(this, rootView);

        mHomeActivity.hideFab();



        if(mCoinsHeader != null){
            mCoinsHeader.setCoinsHeaderSortListener(coinsSortType -> {
                mCoinsListAdapter.changeSortingType(coinsSortType);
            });
        }

        setupRecycler();
        setUpToolbar();

        mCoinsListAdapter.fetchNewData();


        return rootView;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (mCoinsListAdapter != null) {
                final Handler h = new Handler();
                h.postDelayed(mCoinsListAdapter::fetchNewData, Constants.UI_LOAD_DELAY);
            }
        }
    }

        private void setTitle() {
//        if (mCoinFragmentType != null && mHomeActivity != null) {
//            switch (mCoinFragmentType) {
//                case NORMAL:
//                    mHomeActivity.setToolbarText(mSharedPreferenceHelper.getCoinsToFetch().getFriendlyName(mHomeActivity));
//                    break;
//                case FAVORITES:
//                    mHomeActivity.setToolbarText(getResources().getString(R.string.title_favorites));
//                    break;
//            }
//        }
        }


        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            if(context instanceof HomeActivity)
                mHomeActivity = (HomeActivity) context;
        }

        @Override
        public void onDetach() {
            super.onDetach();
            mHomeActivity = null;
        }

        private void setupRecycler() {
            mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            mCoinsListAdapter = new CoinsListAdapter(getActivity(), mCoinFragmentType, Constants.EXTRA_DEFAULT_COINS_SORT_TYPE);
            mCoinsListAdapter.setListener(new CoinsListAdapter.CoinsListAdapterListener() {
                @Override
                public void showLoading() {
                    mSwipeRefresh.setRefreshing(true);
                }

                @Override
                public void hideLoading() {
                    mSwipeRefresh.setRefreshing(false);
                }

                @Override
                public void openCoinDetail(long coinId) {
                    Intent goToCoinDetailIntent = new Intent(mHomeActivity, CoinDetailActivity.class);
                    goToCoinDetailIntent.putExtra(Constants.EXTRA_COIN_DETAIL_COIN_ID, coinId);
                    mHomeActivity.startActivity(goToCoinDetailIntent);
                }

                @Override
                public void showMessage(Message message, @Nullable BaseTransientBottomBar.BaseCallback<Snackbar> callback) {
                    CoinsFragment.this.showMessage(message, callback);
                }
            });

            mRecycler.setLayoutManager(mLayoutManager);
            mRecycler.setAdapter(mCoinsListAdapter);

            mSwipeRefresh.setColorSchemeResources(
                    AttrUtil.getAttributeColorResource(mHomeActivity, R.attr.bottom_nav_icon_unselected),
                    AttrUtil.getAttributeColorResource(mHomeActivity, R.attr.bottom_nav_icon_selected),
                    AttrUtil.getAttributeColorResource(mHomeActivity, R.attr.bottom_nav_icon_unselected));
            mSwipeRefresh.setProgressBackgroundColorSchemeResource(AttrUtil.getAttributeColorResource(mHomeActivity, R.attr.bottom_nav_background));
            mSwipeRefresh.setOnRefreshListener(() -> mCoinsListAdapter.fetchNewData());
        }

        private void setUpToolbar() {
            if(mCoinFragmentType == CoinsFragmentType.NORMAL) {

                mHomeActivity.getToolbar().getMenu().clear();
                mHomeActivity.getToolbar().inflateMenu(R.menu.menu_fragment_coins);
                mHomeActivity.getToolbar().setOnMenuItemClickListener(item -> {
                    item.setChecked(true);

                    switch (item.getItemId()) {
                        case R.id.menu_fragment_coins_top_50:
                            mSharedPreferenceHelper.setCoinsToFetch(CoinsToFetch.TOP_50);
                            break;

                        case R.id.menu_fragment_coins_top_100:
                            mSharedPreferenceHelper.setCoinsToFetch(CoinsToFetch.TOP_100);
                            break;

                        case R.id.menu_fragment_coins_top_500:
                            mSharedPreferenceHelper.setCoinsToFetch(CoinsToFetch.TOP_500);
                            break;

                        case R.id.menu_fragment_coins_all:
                            mSharedPreferenceHelper.setCoinsToFetch(CoinsToFetch.ALL);
                            break;
                    }
                    mCoinsListAdapter.fetchNewData();
                    return true;
                });

                switch (mSharedPreferenceHelper.getCoinsToFetch()) {
                    case TOP_50:
                        mHomeActivity.getToolbar().getMenu().findItem(R.id.menu_fragment_coins_top_50).setChecked(true);
                        break;
                    case TOP_100:
                        mHomeActivity.getToolbar().getMenu().findItem(R.id.menu_fragment_coins_top_100).setChecked(true);
                        break;
                    case TOP_500:
                        mHomeActivity.getToolbar().getMenu().findItem(R.id.menu_fragment_coins_top_500).setChecked(true);
                        break;
                    case ALL:
                        mHomeActivity.getToolbar().getMenu().findItem(R.id.menu_fragment_coins_all).setChecked(true);
                        break;
                }
            }
        }

        @Override
        public void showMessage(Message message, @Nullable BaseTransientBottomBar.BaseCallback<Snackbar> callback) {
            SnackbarUtil.showSnackbar(mContainer, message.getMessageType(), message.getFriendlyNameRes(), SnackbarUtil.SnackbarDuration.SHORT, callback);
        }

    }

package ve.com.abicelis.cryptomaster.ui.coins;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import ve.com.abicelis.cryptomaster.util.AttrUtil;
import ve.com.abicelis.cryptomaster.util.SnackbarUtil;

/**
 * Created by abicelis on 14/6/2018.
 */
public class CoinsFragment extends BaseFragment implements CoinsMvpView {

    @Inject
    SharedPreferenceHelper mSharedPreferenceHelper;

    //DATA
    Context mContext;
    CoinsFragmentType mCoinFragmentType;

    @BindView(R.id.fragment_coins_container)
    CoordinatorLayout mContainer;
    @BindView(R.id.fragment_coin_toolbar_title)
    TextView mToolbarTitle;

    @BindView(R.id.fragment_coin_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.fragment_coins_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.fragment_coins_swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    private LinearLayoutManager mLayoutManager;
    private CoinsListAdapter mCoinsListAdapter;

    @BindView(R.id.fragment_coin_header)
    CoinsHeader mCoinsHeader;

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

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_coins, container, false);
        ButterKnife.bind(this, rootView);

        switch(mCoinFragmentType) {
            case NORMAL:
                mToolbarTitle.setText(mSharedPreferenceHelper.getCoinsToFetch().getFriendlyName(mContext));
                break;
            case FAVORITES:
                mToolbarTitle.setText(getResources().getString(R.string.title_favorites));
                break;
        }

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
        if(isVisibleToUser && mCoinsListAdapter != null)
            mCoinsListAdapter.fetchNewData();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
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
                Intent goToCoinDetailIntent = new Intent(mContext, CoinDetailActivity.class);
                goToCoinDetailIntent.putExtra(Constants.EXTRA_COIN_DETAIL_COIN_ID, coinId);
                mContext.startActivity(goToCoinDetailIntent);
            }

            @Override
            public void showMessage(Message message, @Nullable BaseTransientBottomBar.BaseCallback<Snackbar> callback) {
                CoinsFragment.this.showMessage(message, callback);
            }
        });

        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mCoinsListAdapter);

        mSwipeRefresh.setColorSchemeResources(
                AttrUtil.getAttributeColorResource(mContext, R.attr.bottom_nav_icon_unselected),
                AttrUtil.getAttributeColorResource(mContext, R.attr.bottom_nav_icon_selected),
                AttrUtil.getAttributeColorResource(mContext, R.attr.bottom_nav_icon_unselected));
        mSwipeRefresh.setProgressBackgroundColorSchemeResource(AttrUtil.getAttributeColorResource(mContext, R.attr.bottom_nav_background));
        mSwipeRefresh.setOnRefreshListener(() -> mCoinsListAdapter.fetchNewData());
    }

    private void setUpToolbar() {
        if(mCoinFragmentType == CoinsFragmentType.NORMAL) {

            mToolbar.inflateMenu(R.menu.menu_fragment_coins);
            mToolbar.setOnMenuItemClickListener(item -> {
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
                mToolbarTitle.setText(mSharedPreferenceHelper.getCoinsToFetch().getFriendlyName(mContext));
                mCoinsListAdapter.fetchNewData();
                return true;
            });

            switch (mSharedPreferenceHelper.getCoinsToFetch()) {
                case TOP_50:
                    mToolbar.getMenu().findItem(R.id.menu_fragment_coins_top_50).setChecked(true);
                    break;
                case TOP_100:
                    mToolbar.getMenu().findItem(R.id.menu_fragment_coins_top_100).setChecked(true);
                    break;
                case TOP_500:
                    mToolbar.getMenu().findItem(R.id.menu_fragment_coins_top_500).setChecked(true);
                    break;
                case ALL:
                    mToolbar.getMenu().findItem(R.id.menu_fragment_coins_all).setChecked(true);
                    break;
            }
        }
    }

    @Override
    public void showMessage(Message message, @Nullable BaseTransientBottomBar.BaseCallback<Snackbar> callback) {
        SnackbarUtil.showSnackbar(mContainer, message.getMessageType(), message.getFriendlyNameRes(), SnackbarUtil.SnackbarDuration.SHORT, callback);
    }

}

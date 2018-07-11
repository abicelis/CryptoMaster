package ve.com.abicelis.cryptomaster.ui.coins;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;
import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.application.Constants;
import ve.com.abicelis.cryptomaster.application.Message;
import ve.com.abicelis.cryptomaster.data.model.CoinsFragmentType;
import ve.com.abicelis.cryptomaster.ui.base.BaseFragment;
import ve.com.abicelis.cryptomaster.ui.common.CoinsHeader;
import ve.com.abicelis.cryptomaster.util.SnackbarUtil;

/**
 * Created by abicelis on 14/6/2018.
 */
public class CoinsFragment extends BaseFragment implements CoinsMvpView {

    //DATA
    Context mContext;
    CoinsFragmentType mCoinFragmentType;
    boolean mIsVisibleToUser;

    @BindView(R.id.fragment_coins_container)
    CoordinatorLayout mContainer;
    @BindView(R.id.fragment_coin_toolbar_title)
    TextView mToolbarTitle;
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

        mIsVisibleToUser = false;
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && mCoinsListAdapter != null)
            mCoinsListAdapter.fetchNewData();

    }

    @Override
    public void onResume() {
        super.onResume();
        //if(mIsVisibleToUser) {
        //mCoinsListAdapter.fetchNewData();
        //mIsVisibleToUser = false;
        //}
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_coins, container, false);
        ButterKnife.bind(this, rootView);

        switch(mCoinFragmentType) {
            case NORMAL:
                mToolbarTitle.setText(getResources().getString(R.string.activity_home_bottom_navigation_title_coins));
                break;
            case FAVORITES:
                mToolbarTitle.setText(getResources().getString(R.string.activity_home_bottom_navigation_title_favorites));
                break;
        }

        setupRecycler();
        //if(mIsVisibleToUser) {
        mCoinsListAdapter.fetchNewData();
        //mIsVisibleToUser = false;
        //}

        if(mCoinsHeader != null){
            mCoinsHeader.setCoinsHeaderSortListener(coinsSortType -> {
                Timber.d(coinsSortType.name());
            });
        }

        return rootView;
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
        mCoinsListAdapter = new CoinsListAdapter(getActivity(), mCoinFragmentType);
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
            public void showMessage(Message message, @Nullable BaseTransientBottomBar.BaseCallback<Snackbar> callback) {
                CoinsFragment.this.showMessage(message, callback);
            }
        });

        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mCoinsListAdapter);

        mSwipeRefresh.setColorSchemeResources(R.color.dark_bottom_nav_icon_unselected, R.color.dark_bottom_nav_icon_selected, R.color.dark_bottom_nav_icon_unselected);
        mSwipeRefresh.setProgressBackgroundColorSchemeResource(R.color.dark_bottom_nav_background);
        mSwipeRefresh.setOnRefreshListener(() -> mCoinsListAdapter.fetchNewData());
    }


    @Override
    public void showMessage(Message message, @Nullable BaseTransientBottomBar.BaseCallback<Snackbar> callback) {
        SnackbarUtil.showSnackbar(mContainer, message.getMessageType(), message.getFriendlyNameRes(), SnackbarUtil.SnackbarDuration.SHORT, callback);
    }

}

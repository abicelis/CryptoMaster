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
import android.widget.ProgressBar;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.application.Message;
import ve.com.abicelis.cryptomaster.data.model.Coin;
import ve.com.abicelis.cryptomaster.ui.base.BaseFragment;
import ve.com.abicelis.cryptomaster.util.AttrUtil;
import ve.com.abicelis.cryptomaster.util.SnackbarUtil;

/**
 * Created by abicelis on 14/6/2018.
 */
public class CoinsFragment extends BaseFragment implements CoinsMvpView {

    Context mContext;

    @Inject
    CoinsPresenter mCoinPresenter;

    @BindView(R.id.fragment_coins_container)
    CoordinatorLayout mContainer;
    @BindView(R.id.fragment_coins_progressbar)
    ProgressBar mProgressBar;
    @BindView(R.id.fragment_coins_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.fragment_coins_swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    private LinearLayoutManager mLayoutManager;
    private CoinsAdapter mCoinsAdapter;

    public CoinsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPresenterComponent().inject(this);
        mCoinPresenter.attachView(this);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_coins, container, false);
        ButterKnife.bind(this, rootView);

        setupRecycler();

        mCoinPresenter.getCoinsData();

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
        mCoinsAdapter = new CoinsAdapter(getActivity());
//            mFlightAdapter.setFlightClickedListener(new FlightAdapter.FlightClickedListener() {
//                @Override
//                public void onFlightClicked(Flight flight) {
//                    //Relay event to activity
//                    mListener.onFlightSelected(flight);
//                }
//
//                @Override
//                public void onFlightLongClicked(Flight flight) {
//                    //Relay event to activity
//                    mListener.onFlightSelected(flight);
//                }
//            });

        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mCoinsAdapter);

        mSwipeRefresh.setColorSchemeResources(R.color.dark_bottom_nav_icon_unselected, R.color.dark_bottom_nav_icon_selected, R.color.dark_bottom_nav_icon_unselected);
        mSwipeRefresh.setProgressBackgroundColorSchemeResource(R.color.dark_bottom_nav_background);
        mSwipeRefresh.setOnRefreshListener(() -> mCoinPresenter.getCoinsData());
    }


    @Override
    public void showMessage(Message message, @Nullable BaseTransientBottomBar.BaseCallback<Snackbar> callback) {
        SnackbarUtil.showSnackbar(mContainer, message.getMessageType(), message.getFriendlyNameRes(), SnackbarUtil.SnackbarDuration.SHORT, callback);
    }

    @Override
    public void showLoading() {
        //mProgressBar.setVisibility(View.VISIBLE);
        mSwipeRefresh.setRefreshing(true);
    }

    @Override
    public void showCoins(List<Coin> coins) {
        mCoinsAdapter.getItems().clear();
        mCoinsAdapter.getItems().addAll(coins);
        mCoinsAdapter.notifyDataSetChanged();
    }

    @Override
    public void hideLoading() {
        //mProgressBar.setVisibility(View.INVISIBLE);
        mSwipeRefresh.setRefreshing(false);
    }
}

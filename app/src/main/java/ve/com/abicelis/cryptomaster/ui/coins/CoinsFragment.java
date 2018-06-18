package ve.com.abicelis.cryptomaster.ui.coins;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.application.Message;
import ve.com.abicelis.cryptomaster.data.model.Coin;
import ve.com.abicelis.cryptomaster.ui.base.BaseFragment;
import ve.com.abicelis.cryptomaster.util.SnackbarUtil;

/**
 * Created by abicelis on 14/6/2018.
 */
public class CoinsFragment extends BaseFragment implements CoinsMvpView {

    @Inject
    CoinsPresenter mCoinPresenter;

    @BindView(R.id.fragment_market_container)
    CoordinatorLayout mContainer;
    @BindView(R.id.fragment_coins_recycler)
    RecyclerView mRecycler;
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
    }


    @Override
    public void showMessage(Message message, @Nullable BaseTransientBottomBar.BaseCallback<Snackbar> callback) {
        SnackbarUtil.showSnackbar(mContainer, message.getMessageType(), message.getFriendlyNameRes(), SnackbarUtil.SnackbarDuration.SHORT, callback);
    }

    @Override
    public void showCoins(List<Coin> coins) {
        mCoinsAdapter.getItems().clear();
        mCoinsAdapter.getItems().addAll(coins);
        mCoinsAdapter.notifyDataSetChanged();
    }
}

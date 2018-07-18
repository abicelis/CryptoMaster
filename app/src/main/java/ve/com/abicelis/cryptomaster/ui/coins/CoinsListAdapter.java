package ve.com.abicelis.cryptomaster.ui.coins;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import javax.inject.Inject;

import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.application.CryptoMasterApplication;
import ve.com.abicelis.cryptomaster.application.Message;
import ve.com.abicelis.cryptomaster.data.model.CoinsFragmentType;
import ve.com.abicelis.cryptomaster.data.model.CoinsSortType;
import ve.com.abicelis.cryptomaster.injection.presenter.PresenterModule;

/**
 * Created by abicelis on 17/9/2017.
 */

public class CoinsListAdapter extends RecyclerView.Adapter<CoinsListViewHolder> implements CoinsListMvpView {

    //DATA
    @Inject
    CoinsListPresenter mCoinsListPresenter;
    CoinsListAdapterListener mListener;
    private LayoutInflater mInflater;


    public CoinsListAdapter(Activity activity, CoinsFragmentType coinsFragmentType, CoinsSortType coinsSortType) {
        mInflater = LayoutInflater.from(activity);

        //Injection!
        ((CryptoMasterApplication)activity.getApplication())
                .getApplicationComponent()
                .newPresenterComponent(new PresenterModule(activity)).inject(this);

        mCoinsListPresenter.attachView(this);
        mCoinsListPresenter.setCoinFragmentType(coinsFragmentType);
        mCoinsListPresenter.setCoinsSortType(coinsSortType);
    }


    @Override
    public CoinsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CoinsListViewHolder(mInflater.inflate(R.layout.list_item_coin , parent, false));
    }


    @Override
    public void onBindViewHolder(CoinsListViewHolder holder, int position) {
        mCoinsListPresenter.onBindRepositoryRowViewAtPosition(position, holder);
    }

    @Override
    public int getItemCount() {
        return mCoinsListPresenter.getItemCount();
    }



    public void fetchNewData() {
        mCoinsListPresenter.fetchNewData();
    }


    public void changeSortingType(CoinsSortType coinsSortType) {
        mCoinsListPresenter.changeSortingType(coinsSortType);
    }




    /*CoinsListMvpView interface implementation */
    @Override
    public void showMessage(Message message, @Nullable BaseTransientBottomBar.BaseCallback<Snackbar> callback) { mListener.showMessage(message, callback); }

    @Override
    public void showLoading() { mListener.showLoading(); }

    @Override
    public void hideLoading() { mListener.hideLoading(); }

    @Override
    public void refreshCoinsList() {
        notifyDataSetChanged();
    }

    @Override
    public void refreshAfterItemRemoved(int position, int total) {
        notifyItemRemoved(position);
    }

    @Override
    public void onOpenCoinDetail(long coinId) {
        mListener.openCoinDetail(coinId);
    }



    /* CoinsListAdapterListener interface */

    public void setListener(CoinsListAdapterListener listener) {
        mListener = listener;
    }



    public interface CoinsListAdapterListener {
        void showLoading();
        void hideLoading();
        void openCoinDetail(long coinId);
        void showMessage(Message message, @Nullable BaseTransientBottomBar.BaseCallback<Snackbar> callback);

    }
}

package ve.com.abicelis.cryptomaster.ui.coins;

import ve.com.abicelis.cryptomaster.ui.base.MvpView;

/**
 * Created by abicelis on 24/6/2018.
 */
public interface CoinsListMvpView extends MvpView {
    void showLoading();
    void hideLoading();
    void refreshCoinsList();
    void refreshAfterItemRemoved(int position, int total);
}

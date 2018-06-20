package ve.com.abicelis.cryptomaster.ui.coins;

import java.util.List;

import ve.com.abicelis.cryptomaster.data.model.Coin;
import ve.com.abicelis.cryptomaster.ui.base.MvpView;

/**
 * Created by abicelis on 14/6/2018.
 */
public interface CoinsMvpView extends MvpView {

    void showLoading();
    void showCoins(List<Coin> coins);
    void hideLoading();
}

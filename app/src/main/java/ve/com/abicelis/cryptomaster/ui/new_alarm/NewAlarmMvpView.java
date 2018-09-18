package ve.com.abicelis.cryptomaster.ui.new_alarm;

import java.util.List;

import ve.com.abicelis.cryptomaster.data.model.CachedCoin;
import ve.com.abicelis.cryptomaster.data.model.Coin;
import ve.com.abicelis.cryptomaster.data.model.Currency;
import ve.com.abicelis.cryptomaster.ui.base.MvpView;

/**
 * Created by abicelis on 14/9/2018.
 */
interface NewAlarmMvpView extends MvpView {
    void displayCachedCoins(List<CachedCoin> cachedCoins);
    void displayQuote(double quote);
    void toggleBaseBtc();
    void toggleBaseDefaultCurrency(Currency defaultCurrency);

    void handlePriceBelowToggled();
    void handlePriceAboveToggled();
}

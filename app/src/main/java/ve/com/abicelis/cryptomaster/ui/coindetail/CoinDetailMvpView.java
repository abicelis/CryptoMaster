package ve.com.abicelis.cryptomaster.ui.coindetail;

import ve.com.abicelis.cryptomaster.data.model.ChartTimeSpan;
import ve.com.abicelis.cryptomaster.data.model.Coin;
import ve.com.abicelis.cryptomaster.data.model.Currency;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcapgraph.MarketCapPriceAndVolumeChartData;
import ve.com.abicelis.cryptomaster.ui.base.MvpView;

/**
 * Created by abicelis on 17/7/2018.
 */
public interface CoinDetailMvpView extends MvpView {
    void mainChartShowLoading();
    void mainChartShowError();
    void mainChartHideLoading();

    void showChart(MarketCapPriceAndVolumeChartData data, boolean showBtcChart, Currency defaultCurrency);
    void chartActivateButton(ChartTimeSpan chartTimeSpan);

    void showBasicCoinData(Coin coin, Currency defaultCurrency);
}

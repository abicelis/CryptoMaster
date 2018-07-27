package ve.com.abicelis.cryptomaster.ui.coindetail;

import java.util.List;

import ve.com.abicelis.cryptomaster.data.model.ChartTimeSpan;
import ve.com.abicelis.cryptomaster.data.model.Coin;
import ve.com.abicelis.cryptomaster.data.model.Currency;
import ve.com.abicelis.cryptomaster.data.model.Exchange;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcapgraph.MarketCapPriceAndVolumeChartData;
import ve.com.abicelis.cryptomaster.ui.base.MvpView;

/**
 * Created by abicelis on 17/7/2018.
 */
public interface CoinDetailMvpView extends MvpView {
    void showBasicCoinData(Coin coin, Currency defaultCurrency);
    void couldNotFetchBasicCoinDataShowErrorAndFinish();

    //Chart
    void chartShowLoading();
    void chartShowError();
    void chartHideLoading();
    void showChart(MarketCapPriceAndVolumeChartData data, boolean showBtcChart, Currency defaultCurrency);
    void chartActivateButton(ChartTimeSpan chartTimeSpan);

    //Exchanges
    void exchangesShowLoading();
    void exchangesShowError();
    void exchangesHideLoading();
    void showExchanges(List<Exchange> exchangeList);


    //Common (Chart and Exchanges)
    void setupChartAndExchangesButtons(Currency defaultCurrency);
    void toggleBtcButtons();
    void toggleDefaultCurrencyButtons();
}

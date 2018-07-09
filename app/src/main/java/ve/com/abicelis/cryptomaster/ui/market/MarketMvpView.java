package ve.com.abicelis.cryptomaster.ui.market;

import ve.com.abicelis.cryptomaster.data.model.ChartTimeSpan;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcapgraph.DominanceChartData;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcapgraph.MarketCapAndVolumeChartData;
import ve.com.abicelis.cryptomaster.ui.base.MvpView;

/**
 * Created by abicelis on 30/5/2018.
 */
public interface MarketMvpView extends MvpView {

    void marketCapAndVolumeShowGraph(MarketCapAndVolumeChartData data);
    void marketCapSetLast(String text);
    void marketCapActivateButton(ChartTimeSpan chartTimeSpan);
    void marketCapShowError();
    void marketCapShowLoading();
    void marketCapHideLoading();

    void dominanceShowGraph(DominanceChartData data);
    void dominanceActivateButton(ChartTimeSpan chartTimeSpan);
    void dominanceShowError();
    void dominanceShowLoading();
    void dominanceHideLoading();
}

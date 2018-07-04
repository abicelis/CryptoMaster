package ve.com.abicelis.cryptomaster.ui.market;

import ve.com.abicelis.cryptomaster.data.model.ChartTimespan;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcapgraph.MarketCapAndVolumeChartData;
import ve.com.abicelis.cryptomaster.ui.base.MvpView;

/**
 * Created by abicelis on 30/5/2018.
 */
public interface MarketMvpView extends MvpView {

    void marketCapAndVolumeShowGraph(MarketCapAndVolumeChartData data);
    void marketCapSetLast(String text);
    void marketCapActivateButton(ChartTimespan chartTimespan);
    void marketCapShowLoading();
    void marketCapHideLoading();
}

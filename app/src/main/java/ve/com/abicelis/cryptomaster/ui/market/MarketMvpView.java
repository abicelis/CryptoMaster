package ve.com.abicelis.cryptomaster.ui.market;

import java.util.List;

import lecho.lib.hellocharts.model.PointValue;
import ve.com.abicelis.cryptomaster.ui.base.MvpView;

/**
 * Created by abicelis on 30/5/2018.
 */
public interface MarketMvpView extends MvpView {

    void showMarketCapGraph(List<PointValue> values);

}

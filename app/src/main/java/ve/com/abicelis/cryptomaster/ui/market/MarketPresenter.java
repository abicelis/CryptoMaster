package ve.com.abicelis.cryptomaster.ui.market;

import ve.com.abicelis.cryptomaster.data.DataManager;
import ve.com.abicelis.cryptomaster.ui.base.BasePresenter;

/**
 * Created by abicelis on 30/5/2018.
 */
public class MarketPresenter extends BasePresenter<MarketMvpView> {


    private DataManager mDataManager;

    public MarketPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

}

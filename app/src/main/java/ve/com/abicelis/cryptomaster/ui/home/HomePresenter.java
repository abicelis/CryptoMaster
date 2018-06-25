package ve.com.abicelis.cryptomaster.ui.home;

import ve.com.abicelis.cryptomaster.data.DataManager;
import ve.com.abicelis.cryptomaster.ui.base.BasePresenter;

/**
 * Created by abicelis on 4/9/2017.
 */

public class HomePresenter extends BasePresenter<HomeMvpView> {

    private DataManager mDataManager;

    public HomePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

}

package ve.com.abicelis.cryptomaster.ui.alarm;

import ve.com.abicelis.cryptomaster.data.DataManager;
import ve.com.abicelis.cryptomaster.ui.base.BasePresenter;

/**
 * Created by abicelis on 2/9/2018.
 */
public class AlarmPresenter extends BasePresenter<AlarmMvpView> {

    DataManager mDataManager;

    public AlarmPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }



}

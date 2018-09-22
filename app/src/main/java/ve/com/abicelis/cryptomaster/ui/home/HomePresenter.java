package ve.com.abicelis.cryptomaster.ui.home;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import ve.com.abicelis.cryptomaster.data.DataManager;
import ve.com.abicelis.cryptomaster.data.model.StartFragment;
import ve.com.abicelis.cryptomaster.ui.base.BasePresenter;
import ve.com.abicelis.cryptomaster.util.AlarmWorkerUtil;

/**
 * Created by abicelis on 4/9/2017.
 */

public class HomePresenter extends BasePresenter<HomeMvpView> {

    private DataManager mDataManager;

    public HomePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }


    public void init() {

        //Grab Cached Currencies
        addDisposable(mDataManager.refreshCachedCoins()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> { },
                        throwable -> Timber.e(throwable, "Unable to refresh cached currencies" )
                ));


        //Handle AlarmWorker
        AlarmWorkerUtil.resetAlarmWorker(mDataManager.getSharedPreferenceHelper().getAlarmFrequency());



        getMvpView().setupViewPager(mDataManager.getSharedPreferenceHelper().getStartFragment());
        getMvpView().setupBottomNavigation(mDataManager.getSharedPreferenceHelper().getStartFragment());
    }

    public StartFragment getStartFragment() {
        return mDataManager.getSharedPreferenceHelper().getStartFragment();
    }

}

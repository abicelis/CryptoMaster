package ve.com.abicelis.cryptomaster.ui.home;

import java.util.concurrent.TimeUnit;

import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import ve.com.abicelis.cryptomaster.application.Constants;
import ve.com.abicelis.cryptomaster.data.DataManager;
import ve.com.abicelis.cryptomaster.data.model.StartFragment;
import ve.com.abicelis.cryptomaster.service.AlarmWorker;
import ve.com.abicelis.cryptomaster.ui.base.BasePresenter;

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
        WorkManager.getInstance().cancelAllWorkByTag(Constants.ALARM_WORKER_TAG);
        PeriodicWorkRequest.Builder alarmWorkerBuilder = new PeriodicWorkRequest.Builder(AlarmWorker.class, 15, TimeUnit.MINUTES)
                .addTag(Constants.ALARM_WORKER_TAG);
        PeriodicWorkRequest alarmWork = alarmWorkerBuilder.build();
        WorkManager.getInstance().enqueue(alarmWork);



        getMvpView().setupViewPager(mDataManager.getSharedPreferenceHelper().getStartFragment());
        getMvpView().setupBottomNavigation(mDataManager.getSharedPreferenceHelper().getStartFragment());
    }

    public StartFragment getStartFragment() {
        return mDataManager.getSharedPreferenceHelper().getStartFragment();
    }

}

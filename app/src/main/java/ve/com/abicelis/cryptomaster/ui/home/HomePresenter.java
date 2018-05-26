package ve.com.abicelis.cryptomaster.ui.home;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ve.com.abicelis.cryptomaster.data.DataManager;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcap.TickerResult;
import ve.com.abicelis.cryptomaster.ui.base.BasePresenter;

/**
 * Created by abicelis on 4/9/2017.
 */

public class HomePresenter extends BasePresenter<HomeMvpView> {

    private DataManager mDataManager;

    public HomePresenter(DataManager dataManager) {
        mDataManager = dataManager;
//
//        addDisposable(mDataManager.getTicker()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(tickerResult -> {
//                    System.out.println("onSuccess");
//                    for (TickerResult.TickerData data : tickerResult.getData()) {
//                        System.out.println(data.getId());
//                    }
//                }, throwable -> System.out.println("onError")
//                ));

    }

}

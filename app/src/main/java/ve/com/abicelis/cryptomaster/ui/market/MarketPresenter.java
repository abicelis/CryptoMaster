package ve.com.abicelis.cryptomaster.ui.market;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ve.com.abicelis.cryptomaster.data.DataManager;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcap.GlobalResult;
import ve.com.abicelis.cryptomaster.ui.base.BasePresenter;

/**
 * Created by abicelis on 30/5/2018.
 */
public class MarketPresenter extends BasePresenter<MarketMvpView> {


    private DataManager mDataManager;

    public MarketPresenter(DataManager dataManager) {
        mDataManager = dataManager;
        getGlobal();
    }

    public void getGlobal() {

//        addDisposable(mDataManager.getCoinMarketCapGlobal()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(globalResult -> {
//                    System.out.println("onSuccess");
//                    GlobalResult globalResult11 = globalResult;
//
//                }, throwable ->
//                        System.out.println("onError")
//                ));


        addDisposable(mDataManager.getTotalMaketCap()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    System.out.println("onSuccess");

                }, throwable ->
                        System.out.println("onError")
                ));
    }

}

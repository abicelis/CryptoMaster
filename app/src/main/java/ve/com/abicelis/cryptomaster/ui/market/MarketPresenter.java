package ve.com.abicelis.cryptomaster.ui.market;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import ve.com.abicelis.cryptomaster.application.Message;
import ve.com.abicelis.cryptomaster.data.DataManager;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcaps2.CurrencyResult;
import ve.com.abicelis.cryptomaster.ui.base.BasePresenter;

/**
 * Created by abicelis on 30/5/2018.
 */
public class MarketPresenter extends BasePresenter<MarketMvpView> {


    private DataManager mDataManager;

    public MarketPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    public void getMarketCapGraphData() {

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


        addDisposable(mDataManager.getMaketCapGraphData(1520873220000L,1520879220000L)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    getMvpView().showMarketCapGraph(result);
                }, throwable -> {
                    getMvpView().showMessage(Message.ERROR_UNEXPECTED, null);
                }));

//        addDisposable(mDataManager.getCurrencies()
//        .subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribe(currencyResult -> {
//            for (CurrencyResult c : currencyResult ) {
//                //Timber.i(c.getName());
//            }
//        }, throwable -> {
//            getMvpView().showMessage(Message.ERROR_UNEXPECTED, null);
//        }));
    }

}

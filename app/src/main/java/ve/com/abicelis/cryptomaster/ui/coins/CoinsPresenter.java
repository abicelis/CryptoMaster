package ve.com.abicelis.cryptomaster.ui.coins;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ve.com.abicelis.cryptomaster.application.Message;
import ve.com.abicelis.cryptomaster.data.DataManager;
import ve.com.abicelis.cryptomaster.data.local.SharedPreferenceHelper;
import ve.com.abicelis.cryptomaster.ui.base.BasePresenter;

/**
 * Created by abicelis on 14/6/2018.
 */
public class CoinsPresenter extends BasePresenter<CoinsMvpView> {

    private DataManager mDataManager;

    public CoinsPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }


    public void getCoinsData() {

//        getMvpView().showLoading();
//        addDisposable(mDataManager.getCoins(new SharedPreferenceHelper().getFavoriteCoins(), "USD")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(coins -> {
//                    getMvpView().hideLoading();
//                    getMvpView().showCoins(coins);
//                }, throwable -> {
//                    getMvpView().hideLoading();
//                    getMvpView().showMessage(Message.ERROR_UNEXPECTED, null);
//                }));


        getMvpView().showLoading();
        addDisposable(mDataManager.getCoins(1, 100, "USD")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(coins -> {
            getMvpView().hideLoading();
            getMvpView().showCoins(coins);
        }, throwable -> {
            getMvpView().hideLoading();
            getMvpView().showMessage(Message.ERROR_UNEXPECTED, null);
        }));
    }


}

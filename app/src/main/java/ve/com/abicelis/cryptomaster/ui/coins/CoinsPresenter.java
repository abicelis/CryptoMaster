package ve.com.abicelis.cryptomaster.ui.coins;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ve.com.abicelis.cryptomaster.application.Constants;
import ve.com.abicelis.cryptomaster.application.Message;
import ve.com.abicelis.cryptomaster.data.DataManager;
import ve.com.abicelis.cryptomaster.data.local.SharedPreferenceHelper;
import ve.com.abicelis.cryptomaster.data.model.CoinsFragmentType;
import ve.com.abicelis.cryptomaster.ui.base.BasePresenter;

/**
 * Created by abicelis on 14/6/2018.
 */
public class CoinsPresenter extends BasePresenter<CoinsMvpView> {

    private DataManager mDataManager;
    private CoinsFragmentType mFragmentType = null;


    public CoinsPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    public void setCoinFragmentType(CoinsFragmentType fragmentType){
        mFragmentType = fragmentType;
    }
    public CoinsFragmentType getCoinFragmentType() {
        return mFragmentType;
    }

    public void refreshCoinsData() {
        switch (mFragmentType) {
            case NORMAL:
                getCoinsData();
                break;
            case FAVORITES:
                getFavoriteCoinsData();
                break;
        }
    }


    private void getCoinsData() {


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


    private void getFavoriteCoinsData() {
        getMvpView().showLoading();
        addDisposable(mDataManager.getCoins(new SharedPreferenceHelper().getFavoriteCoins(), "USD")
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
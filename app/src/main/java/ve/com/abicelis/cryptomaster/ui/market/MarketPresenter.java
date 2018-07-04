package ve.com.abicelis.cryptomaster.ui.market;

import android.support.annotation.NonNull;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ve.com.abicelis.cryptomaster.application.Message;
import ve.com.abicelis.cryptomaster.data.DataManager;
import ve.com.abicelis.cryptomaster.data.model.ChartTimespan;
import ve.com.abicelis.cryptomaster.ui.base.BasePresenter;

/**
 * Created by abicelis on 30/5/2018.
 */
public class MarketPresenter extends BasePresenter<MarketMvpView> {


    private DataManager mDataManager;
    private ChartTimespan mChartTimespan;
    private boolean mLoadingMarketCapAndVolumeChart;

    public MarketPresenter(DataManager dataManager) {
        mDataManager = dataManager;
        mChartTimespan = null;
        mLoadingMarketCapAndVolumeChart = false;
    }

    public void getMarketCapAndVolumeGraphData(@NonNull ChartTimespan chartTimespan) {

        if(!mLoadingMarketCapAndVolumeChart && (mChartTimespan == null || mChartTimespan.compareTo(chartTimespan) != 0)) {
            mChartTimespan = chartTimespan;
            mLoadingMarketCapAndVolumeChart = true;

            //Get last week's data
            Calendar cal = Calendar.getInstance();
            long timeEnd = cal.getTimeInMillis();
            long timeStart = 0L;

            switch (chartTimespan) {
                case _24H:
                    cal.add(Calendar.DAY_OF_YEAR, -1);
                    timeStart = cal.getTimeInMillis();
                    break;
                case _7D:
                    cal.add(Calendar.DAY_OF_YEAR, -7);
                    timeStart = cal.getTimeInMillis();
                    break;
                case _1M:
                    cal.add(Calendar.MONTH, -1);
                    timeStart = cal.getTimeInMillis();
                    break;
                case _3M:
                    cal.add(Calendar.MONTH, -3);
                    timeStart = cal.getTimeInMillis();
                    break;
                case _1Y:
                    cal.add(Calendar.YEAR, -1);
                    timeStart = cal.getTimeInMillis();
                    break;
            }

            getMvpView().marketCapShowLoading();
            addDisposable(mDataManager.getMaketCapAndVolumeGraphData(timeStart, timeEnd, chartTimespan)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(data -> {
                        getMvpView().marketCapHideLoading();

                        getMvpView().marketCapSetLast("$ " + NumberFormat.getNumberInstance(Locale.getDefault()).format(data.getLastMarketCap()));
                        getMvpView().marketCapActivateButton(mChartTimespan);
                        getMvpView().marketCapAndVolumeShowGraph(data);

                        mLoadingMarketCapAndVolumeChart = false;
                        getMvpView().marketCapHideLoading();
                    }, throwable -> {
                        getMvpView().showMessage(Message.ERROR_UNEXPECTED, null);

                        mLoadingMarketCapAndVolumeChart = false;
                        getMvpView().marketCapHideLoading();
                    }));
        }

    }
}




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
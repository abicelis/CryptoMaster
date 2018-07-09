package ve.com.abicelis.cryptomaster.ui.market;

import android.support.annotation.NonNull;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ve.com.abicelis.cryptomaster.application.Message;
import ve.com.abicelis.cryptomaster.data.DataManager;
import ve.com.abicelis.cryptomaster.data.model.ChartTimeSpan;
import ve.com.abicelis.cryptomaster.ui.base.BasePresenter;

/**
 * Created by abicelis on 30/5/2018.
 */
public class MarketPresenter extends BasePresenter<MarketMvpView> {


    private DataManager mDataManager;

    private ChartTimeSpan mMarketCapAndVolumeChartTimeSpan;
    private boolean mLoadingMarketCapAndVolumeChart;

    private ChartTimeSpan mDominanceChartTimeSpan;
    private boolean mLoadingDominanceChart;

    public MarketPresenter(DataManager dataManager) {
        mDataManager = dataManager;
        mMarketCapAndVolumeChartTimeSpan = null;
        mLoadingMarketCapAndVolumeChart = false;
        mLoadingDominanceChart = false;
    }

    public void getMarketCapAndVolumeGraphData(@NonNull ChartTimeSpan chartTimeSpan) {

        if(!mLoadingMarketCapAndVolumeChart && (mMarketCapAndVolumeChartTimeSpan == null || mMarketCapAndVolumeChartTimeSpan.compareTo(chartTimeSpan) != 0)) {
            mMarketCapAndVolumeChartTimeSpan = chartTimeSpan;
            mLoadingMarketCapAndVolumeChart = true;

            //Calculate timeStart based on chartTimeSpan
            long timeStart = calculateTimeStart(chartTimeSpan);
            long timeEnd = new GregorianCalendar().getTimeInMillis();

            getMvpView().marketCapShowLoading();
            addDisposable(mDataManager.getMaketCapAndVolumeGraphData(timeStart, timeEnd, chartTimeSpan)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(data -> {
                        getMvpView().marketCapSetLast("$ " + NumberFormat.getNumberInstance(Locale.getDefault()).format(data.getLastMarketCap()));
                        getMvpView().marketCapActivateButton(mMarketCapAndVolumeChartTimeSpan);
                        getMvpView().marketCapAndVolumeShowGraph(data);

                        mLoadingMarketCapAndVolumeChart = false;
                        getMvpView().marketCapHideLoading();
                    }, throwable -> {
                        getMvpView().showMessage(Message.ERROR_UNEXPECTED, null);

                        mLoadingMarketCapAndVolumeChart = false;
                        getMvpView().marketCapHideLoading();
                        getMvpView().marketCapShowError();
                    }));
        }

    }

    public void getDominanceGraphData(@NonNull ChartTimeSpan chartTimeSpan) {

        if(!mLoadingDominanceChart && (mDominanceChartTimeSpan == null || mDominanceChartTimeSpan.compareTo(chartTimeSpan) != 0)) {
            mDominanceChartTimeSpan = chartTimeSpan;
            mLoadingDominanceChart = true;

            //Calculate timeStart based on chartTimeSpan
            long timeStart = calculateTimeStart(chartTimeSpan);
            long timeEnd = new GregorianCalendar().getTimeInMillis();

            getMvpView().dominanceShowLoading();
            addDisposable(mDataManager.getDominanceGraphData(timeStart, timeEnd, chartTimeSpan)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(data -> {
                        getMvpView().dominanceActivateButton(mDominanceChartTimeSpan);
                        getMvpView().dominanceShowGraph(data);

                        mLoadingDominanceChart = false;
                        getMvpView().dominanceHideLoading();
                    }, throwable -> {
                        getMvpView().showMessage(Message.ERROR_UNEXPECTED, null);
                        mLoadingDominanceChart = false;
                        getMvpView().dominanceHideLoading();
                        getMvpView().dominanceShowError();
                    }));
        }

    }

    private long calculateTimeStart(@NonNull ChartTimeSpan chartTimeSpan) {
        Calendar cal = Calendar.getInstance();
        long timeStart = 0L;

        switch (chartTimeSpan) {
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
        return timeStart;
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
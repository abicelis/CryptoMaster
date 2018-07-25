package ve.com.abicelis.cryptomaster.ui.coindetail;

import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.GregorianCalendar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ve.com.abicelis.cryptomaster.application.Message;
import ve.com.abicelis.cryptomaster.data.DataManager;
import ve.com.abicelis.cryptomaster.data.model.ChartTimeSpan;
import ve.com.abicelis.cryptomaster.data.model.Coin;
import ve.com.abicelis.cryptomaster.data.model.Currency;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcapgraph.MarketCapPriceAndVolumeChartData;
import ve.com.abicelis.cryptomaster.ui.base.BasePresenter;

/**
 * Created by abicelis on 17/7/2018.
 */
public class CoinDetailPresenter extends BasePresenter<CoinDetailActivity> {

    //DATA
    private DataManager mDataManager;
    private long mCoinId;
    private Coin mCoin;

    private boolean usingBtcForChartData = true;    //If false, displaying chart data using DefaultCurrency.
    private ChartTimeSpan mMainChartTimeSpan;
    private boolean mLoadingMainChart;
    private MarketCapPriceAndVolumeChartData mChartData;
    private Currency mDefaultCurrency;

    public CoinDetailPresenter(DataManager dataManager) {
        mDataManager = dataManager;
        mMainChartTimeSpan = null;
        mLoadingMainChart = false;
    }

    public void setCoinId(long coinId) {
        mCoinId = coinId;
    }


    public void getBasicCoinData() {
        mDefaultCurrency = mDataManager.getSharedPreferenceHelper().getDefaultCurrency();

        addDisposable(mDataManager.getLocalCoin(mCoinId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(coin -> {
                    mCoin = coin;
                    getMvpView().showBasicCoinData(coin, mDefaultCurrency);
                }, throwable -> {
                    getMvpView().showMessage(Message.ERROR_UNEXPECTED, null);
                }));
    }

    public Coin getCoin(){
        return mCoin;
    }

    public void getMainChartData(@NonNull ChartTimeSpan chartTimeSpan) {
        if(!mLoadingMainChart && (mMainChartTimeSpan == null || mMainChartTimeSpan.compareTo(chartTimeSpan) != 0)) {
            mMainChartTimeSpan = chartTimeSpan;
            mLoadingMainChart = true;

            //Calculate timeStart based on chartTimeSpan
            long timeStart = calculateTimeStart(chartTimeSpan);
            long timeEnd = new GregorianCalendar().getTimeInMillis();

            getMvpView().mainChartShowLoading();
            addDisposable(mDataManager.getCurrencyMarketCapPriceAndVolumeGraphData(mCoin.getWebsiteSlug(), timeStart, timeEnd, chartTimeSpan)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(data -> {
                        mChartData = data;
                        getMvpView().chartActivateButton(mMainChartTimeSpan);

                        usingBtcForChartData = true;
                        getMvpView().showChart(mChartData, usingBtcForChartData, mDefaultCurrency);
                        getMvpView().toggleBtcButton();
                        mLoadingMainChart = false;
                        getMvpView().mainChartHideLoading();
                    }, throwable -> {
                        getMvpView().showMessage(Message.ERROR_UNEXPECTED, null);

                        mLoadingMainChart = false;
                        getMvpView().mainChartHideLoading();
                        getMvpView().mainChartShowError();
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

    public void onBtcClickedForChart() {
        if(!usingBtcForChartData && !mLoadingMainChart && mChartData != null) {
            usingBtcForChartData = true;
            getMvpView().showChart(mChartData, usingBtcForChartData, mDefaultCurrency);
            getMvpView().toggleBtcButton();
        }

    }

    public void onDefaultCurrencyClickedForChart() {
        if(usingBtcForChartData && !mLoadingMainChart && mChartData != null) {
            usingBtcForChartData = false;
            getMvpView().showChart(mChartData, usingBtcForChartData, mDefaultCurrency);
            getMvpView().toggleDefaultCurrencyButton();
        }
    }
}

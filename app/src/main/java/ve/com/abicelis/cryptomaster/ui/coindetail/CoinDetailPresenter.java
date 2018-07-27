package ve.com.abicelis.cryptomaster.ui.coindetail;

import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import ve.com.abicelis.cryptomaster.application.Constants;
import ve.com.abicelis.cryptomaster.application.Message;
import ve.com.abicelis.cryptomaster.data.DataManager;
import ve.com.abicelis.cryptomaster.data.model.ChartTimeSpan;
import ve.com.abicelis.cryptomaster.data.model.Coin;
import ve.com.abicelis.cryptomaster.data.model.Currency;
import ve.com.abicelis.cryptomaster.data.model.Exchange;
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
    private Currency mDefaultCurrency;
    private boolean mIsBtcSelected;      //If false, displaying chart data using DefaultCurrency.

    //Chart
    private ChartTimeSpan mChartTimeSpan;
    private boolean mLoadingChart;
    private MarketCapPriceAndVolumeChartData mChartData;

    //Exchanges
    private boolean exchangesDataLoaded = false;
    private List<Exchange> mExchangesDataBtc;
    private List<Exchange> mExchangesDataDefaultCurrency;

    public CoinDetailPresenter(DataManager dataManager) {
        mDataManager = dataManager;
        mChartTimeSpan = null;
        mLoadingChart = false;
    }

    public void setCoinId(long coinId) {
        mCoinId = coinId;
    }

    public void getBasicCoinData() {
        mDefaultCurrency = mDataManager.getSharedPreferenceHelper().getDefaultCurrency();
        mIsBtcSelected = mDataManager.getSharedPreferenceHelper().isBtcDefaultAtCoinDetailActivity();

        //Set up views
        getMvpView().setupChartAndExchangesButtons(mDefaultCurrency);
        if(mIsBtcSelected) getMvpView().toggleBtcButtons();
        else getMvpView().toggleDefaultCurrencyButtons();


        addDisposable(mDataManager.getLocalCoin(mCoinId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(coin -> {
                    mCoin = coin;
                    getMvpView().showBasicCoinData(coin, mDefaultCurrency);

                    //Request rest of the data (chart, exchanges)
                    getChartData(ChartTimeSpan._3M);
                    getExchangesData();

                }, throwable -> {
                    Timber.e(throwable);
                    getMvpView().couldNotFetchBasicCoinDataShowErrorAndFinish();
                }));
    }


    public void getChartData(@NonNull ChartTimeSpan chartTimeSpan) {
        if(!mLoadingChart && (mChartTimeSpan == null || mChartTimeSpan.compareTo(chartTimeSpan) != 0)) {
            mChartTimeSpan = chartTimeSpan;
            mLoadingChart = true;

            //Calculate timeStart based on chartTimeSpan
            long timeStart = calculateTimeStart(chartTimeSpan);
            long timeEnd = new GregorianCalendar().getTimeInMillis();

            getMvpView().chartShowLoading();
            addDisposable(mDataManager.getCurrencyMarketCapPriceAndVolumeGraphData(mCoin.getWebsiteSlug(), timeStart, timeEnd, chartTimeSpan)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(data -> {
                        mChartData = data;
                        getMvpView().chartActivateButton(mChartTimeSpan);

                        getMvpView().showChart(mChartData, mIsBtcSelected, mDefaultCurrency);
                        mLoadingChart = false;
                        getMvpView().chartHideLoading();
                    }, throwable -> {
                        getMvpView().showMessage(Message.COULD_NOT_FETCH_CHART_DATA, null);

                        mLoadingChart = false;
                        getMvpView().chartHideLoading();
                        getMvpView().chartShowError();
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


    public void getExchangesData() {

        String toCode, toCode2;
        if(mIsBtcSelected) {
            toCode = Currency.BTC.getCode();
            toCode2 = mDefaultCurrency.getCode();
        } else {
            toCode = mDefaultCurrency.getCode();
            toCode2 = Currency.BTC.getCode();
        }

        getMvpView().exchangesShowLoading();
        addDisposable(mDataManager.getTopExchangesByPair(mCoin.getCode(), toCode, Constants.MISC_MAX_EXCHANGES_ENTRIES)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {

                    if(mIsBtcSelected)
                        mExchangesDataBtc = result;
                    else
                        mExchangesDataDefaultCurrency = result;

                    getMvpView().showExchanges(result);
                    getMvpView().exchangesHideLoading();

                    addDisposable(mDataManager.getTopExchangesByPair(mCoin.getCode(), toCode2, Constants.MISC_MAX_EXCHANGES_ENTRIES)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(result2 -> {
                                        exchangesDataLoaded = true;

                                        if(mIsBtcSelected)
                                            mExchangesDataDefaultCurrency = result2;
                                        else
                                            mExchangesDataBtc = result2;
                                    },
                                    throwable2 ->{
                                        Timber.i(throwable2);
                                    } ));

                }, throwable ->  {
                    Timber.i(throwable);
                    getMvpView().showMessage(Message.COULD_NOT_FETCH_EXCHANGE_DATA, null);

                    getMvpView().exchangesHideLoading();
                    getMvpView().exchangesShowError();
                }));
    }


    public void onBtcClicked() {
        if(!mIsBtcSelected && !mLoadingChart && mChartData != null && exchangesDataLoaded) {
            mDataManager.getSharedPreferenceHelper().setBtcDefaultAtCoinDetailActivity(true);
            mIsBtcSelected = true;
            getMvpView().showChart(mChartData, mIsBtcSelected, mDefaultCurrency);
            getMvpView().showExchanges(mExchangesDataBtc);
            getMvpView().toggleBtcButtons();
        }

        if(!mIsBtcSelected && !exchangesDataLoaded) {  //Try again?
            mDataManager.getSharedPreferenceHelper().setBtcDefaultAtCoinDetailActivity(true);
            mIsBtcSelected = true;
            getExchangesData();
        }

    }

    public void onDefaultCurrencyClicked() {
        if(mIsBtcSelected && !mLoadingChart && mChartData != null && exchangesDataLoaded) {
            mDataManager.getSharedPreferenceHelper().setBtcDefaultAtCoinDetailActivity(false);
            mIsBtcSelected = false;
            getMvpView().showChart(mChartData, mIsBtcSelected, mDefaultCurrency);
            getMvpView().showExchanges(mExchangesDataDefaultCurrency);
            getMvpView().toggleDefaultCurrencyButtons();
        }

        if(mIsBtcSelected && !exchangesDataLoaded) {  //Try again?
            mDataManager.getSharedPreferenceHelper().setBtcDefaultAtCoinDetailActivity(false);
            mIsBtcSelected = false;
            getExchangesData();
        }
    }



}

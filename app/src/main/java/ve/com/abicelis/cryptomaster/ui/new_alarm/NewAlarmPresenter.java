package ve.com.abicelis.cryptomaster.ui.new_alarm;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import ve.com.abicelis.cryptomaster.application.Constants;
import ve.com.abicelis.cryptomaster.application.Message;
import ve.com.abicelis.cryptomaster.data.DataManager;
import ve.com.abicelis.cryptomaster.data.model.Alarm;
import ve.com.abicelis.cryptomaster.data.model.AlarmColor;
import ve.com.abicelis.cryptomaster.data.model.AlarmType;
import ve.com.abicelis.cryptomaster.data.model.CachedCoin;
import ve.com.abicelis.cryptomaster.data.model.Currency;
import ve.com.abicelis.cryptomaster.ui.base.BasePresenter;

/**
 * Created by abicelis on 14/9/2018.
 */
public class NewAlarmPresenter extends BasePresenter<NewAlarmActivity> {


    DataManager dataManager;

    //DATA
    CachedCoin mQuoteCoin;
    Currency mBaseCurrency;
    double mQuote;
    AlarmType mAlarmType;
    double mAlarmPrice;
    AlarmColor alarmColor;
    boolean editingExistingAlarm;
    long existingAlarmId;

    public NewAlarmPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }



    public void newAlarm() {
        baseCurrencyDefCurrToggled();
        priceBelowButtonToggled();
        alarmColorChanged(AlarmColor.COLOR_1);
    }

    public void editingExistingAlarm(long alarmId) {
        editingExistingAlarm = true;
        addDisposable(dataManager.getAlarm(alarmId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(alarm -> {
                    existingAlarmId = alarm.getId();
                    mBaseCurrency = alarm.getFromCurrency();
                    mAlarmType = alarm.getAlarmType();
                    //mAlarmPrice = alarm.getTriggerValue();
                    alarmColor = alarm.getAlarmColor();
                    getMvpView().setOptionalNote(alarm.getNote());

                    alarmColorChanged(alarmColor);
                    if(mBaseCurrency.equals(Currency.BTC))
                        baseCurrencyBtcToggled();
                    else
                        baseCurrencyDefCurrToggled();

                    if(mAlarmType.equals(AlarmType.ABOVE))
                        priceAboveButtonToggled();
                    else
                        priceBelowButtonToggled();

                    dataManager.getCachedCoin(alarm.getToCoinId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(coin -> {
                                getMvpView().setQuoteCoin(coin);
                            }, throwable -> {
                                Timber.e(throwable, "Unexpected error fetching cached quote coin. ID=%d", alarm.getToCoinId());
                                getMvpView().showMessage(Message.ERROR_UNEXPECTED, null);
                            });

                }, throwable -> {
                    getMvpView().showMessage(Message.ERROR_UNEXPECTED, null);
                    Timber.e(throwable, "Error getting alarm. Alarm ID=%d", alarmId);
                }));

    }



    public void requestCachedCoins(String query) {

        addDisposable(dataManager.cachedCoinsInDatabase()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(cachedCoinsInDatabase -> {

                    if (cachedCoinsInDatabase) {
                        if(query != null && !query.equals("")) {

                            addDisposable(dataManager.getCachedCoins(query)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(cachedCoins -> {
                                        getMvpView().displayCachedCoins(cachedCoins);

                                    }, throwable -> {
                                        getMvpView().showMessage(Message.COULD_NOT_FETCH_COIN_DATA, null);
                                        Timber.e(throwable, "Error getting cached coins");
                                    }));

                        } else {

                            addDisposable(dataManager.getRankedCachedCoins(Constants.NEW_ALARM_ACTIVITY_RANKED_CACHED_COINS_COUNT)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(cachedCoins -> {
                                        getMvpView().displayCachedCoins(cachedCoins);

                                    }, throwable -> {
                                        getMvpView().showMessage(Message.COULD_NOT_FETCH_COIN_DATA, null);
                                        Timber.e(throwable, "Error getting cached coins");
                                    }));

                        }

                    } else {
                        addDisposable(dataManager.refreshCachedCoins()
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.io())
                                .subscribe( () -> {
                                    requestCachedCoins(query);
                                }, throwable -> {
                                    getMvpView().showMessage(Message.COULD_NOT_FETCH_COIN_DATA, null);
                                    Timber.e(throwable, "Error refreshing cached coins");
                                }));
                    }
                }, throwable -> {
                    Timber.e(throwable, "Error checking cached coins");
                }));

    }

    public void baseCurrencyBtcToggled() {
        if (mQuoteCoin == null || !Currency.BTC.getCode().equals(mQuoteCoin.getCode())) {
            mBaseCurrency = Currency.BTC;
            getMvpView().toggleBaseBtc();
        } else {
            getMvpView().showMessage(Message.DUPLICATE_BASE_QUOTE_COIN, null);
        }
    }

    public void baseCurrencyDefCurrToggled() {
        if (mQuoteCoin == null || !getDefaultCurrency().getCode().equals(mQuoteCoin.getCode())) {
            mBaseCurrency = getDefaultCurrency();
            getMvpView().toggleBaseDefaultCurrency(getDefaultCurrency());
        } else {
            getMvpView().showMessage(Message.DUPLICATE_BASE_QUOTE_COIN, null);
        }
    }

    public Currency getDefaultCurrency() {
        return dataManager.getSharedPreferenceHelper().getDefaultCurrency();
    }


    public void getPairQuote(CachedCoin quoteCoin) {
        mQuoteCoin = quoteCoin;
        addDisposable(dataManager.getCoinMarketCapTicker(mQuoteCoin.getId(), mBaseCurrency)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(coin -> {
                    mQuote = coin.getQuoteDefaultPrice();
                    getMvpView().displayQuote(mBaseCurrency, mQuote);
                }, throwable -> {
                    getMvpView().showMessage(Message.COULD_NOT_FETCH_QUOTE, null);
                    Timber.e(throwable, "Unable to get ticker for " + mQuoteCoin.getCode());
                }));
    }



    public void priceBelowButtonToggled() {
        mAlarmType = AlarmType.BELOW;
        getMvpView().handlePriceBelowToggled();
    }
    public void priceAboveButtonToggled() {
        mAlarmType = AlarmType.ABOVE;
        getMvpView().handlePriceAboveToggled();
    }

    public void alarmPriceChanged(double price) {
        if (price == -1) {
            getMvpView().hidePriceDiffsAndPercentages();
            return;
        }

        mAlarmPrice = price;
        if(mAlarmType == AlarmType.BELOW) {
            if(mAlarmPrice < mQuote) {
                double diff = mQuote - mAlarmPrice;
                double percent = (diff*100)/mQuote;
                getMvpView().showBelowDiffAndPercent(mBaseCurrency, diff, percent);
            } else
                getMvpView().hidePriceDiffsAndPercentages();

        } else if (mAlarmType == AlarmType.ABOVE) {
            if(mAlarmPrice > mQuote) {
                double diff = mAlarmPrice - mQuote;
                double percent = (diff*100)/mQuote;
                getMvpView().showAboveDiffAndPercent(mBaseCurrency, diff, percent);
            } else
                getMvpView().hidePriceDiffsAndPercentages();
        }
    }

    public void alarmColorChanged(AlarmColor color) {
        alarmColor = color;
        getMvpView().changeAlarmColorTint(alarmColor);
    }

    public void saveAlarmClicked() {

        //TODO improve these messages
        if(mQuoteCoin == null) {
            getMvpView().showMessage(Message.COULD_NOT_INSERT_ALARM, null);
            return;
        }

        if(mQuote == 0f) {
            getMvpView().showMessage(Message.COULD_NOT_INSERT_ALARM, null);
            return;
        }

        if(mAlarmPrice == 0f) {
            getMvpView().showMessage(Message.COULD_NOT_INSERT_ALARM, null);
            return;
        }

        if(mAlarmType == AlarmType.ABOVE && mAlarmPrice <= mQuote) {
            getMvpView().showMessage(Message.COULD_NOT_INSERT_ALARM, null);
            return;
        }

        if(mAlarmType == AlarmType.BELOW && mAlarmPrice >= mQuote) {
            getMvpView().showMessage(Message.COULD_NOT_INSERT_ALARM, null);
            return;
        }


        Alarm alarm = new Alarm(mBaseCurrency, mQuoteCoin.getId(), mQuoteCoin.getCode(), mAlarmPrice, mAlarmType, alarmColor, getMvpView().getOptionalNote(), true);

        if(editingExistingAlarm) {
            alarm.setId(existingAlarmId);
            dataManager.updateAlarm(alarm)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe( () -> {
                        getMvpView().alarmSuccessfullySaved(true);
                    }, throwable -> {
                        Timber.e(throwable, "Error Updating alarm");
                        getMvpView().showMessage(Message.COULD_NOT_UPDATE_ALARM, null);
                    });
        } else {
            dataManager.insertAlarm(alarm)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe( () -> {
                        getMvpView().alarmSuccessfullySaved(false);
                    }, throwable -> {
                        Timber.e(throwable, "Error Inserting alarm");
                        getMvpView().showMessage(Message.COULD_NOT_INSERT_ALARM, null);
                    });
        }


    }

}

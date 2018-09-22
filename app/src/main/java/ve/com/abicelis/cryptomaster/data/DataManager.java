package ve.com.abicelis.cryptomaster.data;

import android.support.annotation.Nullable;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import ve.com.abicelis.cryptomaster.application.Constants;
import ve.com.abicelis.cryptomaster.data.local.AppDatabase;
import ve.com.abicelis.cryptomaster.data.local.SharedPreferenceHelper;
import ve.com.abicelis.cryptomaster.data.model.Alarm;
import ve.com.abicelis.cryptomaster.data.model.CachedCoin;
import ve.com.abicelis.cryptomaster.data.model.ChartTimeSpan;
import ve.com.abicelis.cryptomaster.data.model.Coin;
import ve.com.abicelis.cryptomaster.data.model.Currency;
import ve.com.abicelis.cryptomaster.data.model.Exchange;
import ve.com.abicelis.cryptomaster.data.model.FavoriteCoin;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcap.GlobalResult;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcap.TickerResult;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcapgraph.DominanceChartData;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcapgraph.MarketCapAndVolumeChartData;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcapgraph.MarketCapPriceAndVolumeChartData;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcaps2.CurrencyResult;
import ve.com.abicelis.cryptomaster.data.model.cryptocomparemin.TopExchangesResult;
import ve.com.abicelis.cryptomaster.data.remote.CoinMarketCapApi;
import ve.com.abicelis.cryptomaster.data.remote.CoinMarketCapGraphApi;
import ve.com.abicelis.cryptomaster.data.remote.CoinMarketCapS2Api;
import ve.com.abicelis.cryptomaster.data.remote.CryptoCompareApi;
import ve.com.abicelis.cryptomaster.data.remote.CryptoCompareMinApi;

/**
 * Created by abicelis on 29/8/2017.
 */

public class DataManager {

    private AppDatabase mAppDatabase;
    private SharedPreferenceHelper mSharedPreferenceHelper;
    private CoinMarketCapApi mCoinMarketCapApi;
    private CoinMarketCapGraphApi mCoinMarketCapGraphApi;
    private CoinMarketCapS2Api mCoinMarketCapS2Api;
    private CryptoCompareApi mCryptoCompareApi;
    private CryptoCompareMinApi mCryptoCompareMinApi;

    @Inject
    public DataManager(AppDatabase appDatabase,
                       SharedPreferenceHelper sharedPreferenceHelper,
                       CoinMarketCapApi coinMarketCapApi,
                       CoinMarketCapGraphApi coinMarketCapGraphApi,
                       CoinMarketCapS2Api coinMarketCapS2Api,
                       CryptoCompareApi cryptoCompareApi,
                       CryptoCompareMinApi cryptoCompareMinApi) {

        mAppDatabase = appDatabase;
        mSharedPreferenceHelper = sharedPreferenceHelper;
        mCoinMarketCapApi = coinMarketCapApi;
        mCoinMarketCapGraphApi = coinMarketCapGraphApi;
        mCoinMarketCapS2Api = coinMarketCapS2Api;
        mCryptoCompareApi = cryptoCompareApi;
        mCryptoCompareMinApi = cryptoCompareMinApi;
    }


    public SharedPreferenceHelper getSharedPreferenceHelper() {
        return mSharedPreferenceHelper;
    }

    /**
     * Fetches remote ticker data, returns a List of Coins
     */
    public Single<List<Coin>> getRemoteCoins() {
        switch (mSharedPreferenceHelper.getCoinsToFetch()) {
            case TOP_50:
                return getRemoteCoinsSingleRequest(50, mSharedPreferenceHelper.getDefaultCurrency());
            case TOP_100:
                return getRemoteCoinsSingleRequest(100, mSharedPreferenceHelper.getDefaultCurrency());
            case TOP_500:
                return getRemoteCoinsMultipleRequests(500, mSharedPreferenceHelper.getDefaultCurrency());
            case ALL:
            default:
                return getRemoteCoinsMultipleRequests(-1, mSharedPreferenceHelper.getDefaultCurrency());
        }
    }

    private Single<List<Coin>> getRemoteCoinsSingleRequest(int limit, Currency defaultCurrency) {

        return mCoinMarketCapApi.getTicker(1, limit, defaultCurrency.getCode())
                .map(tickerResult -> {
                    List<Coin> coins = new ArrayList<>();
                    for (TickerResult.TickerData data : tickerResult.getData()) {

                        coins.add(buildCoinFromTickerData(data, defaultCurrency));
                    }

                    Collections.sort(coins);

                    //Save to DB
                    //mAppDatabase.coinDao().deleteCoinsAndInsertNewOnes(coins);
                    mAppDatabase.coinDao().insert(coins);
                    return coins;
                });
    }


    private Single<List<Coin>> getRemoteCoinsMultipleRequests(int limit, Currency defaultCurrency) {
        if(limit != -1) {

            return mCoinMarketCapApi.getTicker(1,1, defaultCurrency.getCode())
                    .map(tickerResult -> {
                        final int numCryptoCurrencies = tickerResult.getMetadata().getNumCryptocurrencies();
                        return doGetRemoteCoinsMultipleRequests(numCryptoCurrencies, defaultCurrency).blockingGet();
                    });
        } else
            return doGetRemoteCoinsMultipleRequests(limit, defaultCurrency);
    }

    private Single<List<Coin>> doGetRemoteCoinsMultipleRequests(int limit, Currency defaultCurrency) {
        List<Single<TickerResult>> list = new ArrayList<>();
        for (int i = 1; (i - 1) < limit; i += 100)
            list.add(mCoinMarketCapApi.getTicker(i, 100, defaultCurrency.getCode()));

        return Single.zip(list, tickerResults -> {
            List<Coin> coins = new ArrayList<>();

            for (Object o : tickerResults)
                if (o instanceof TickerResult)
                    for (TickerResult.TickerData data : ((TickerResult) o).getData())
                        coins.add(buildCoinFromTickerData(data, defaultCurrency));


            //Save to DB
            //mAppDatabase.coinDao().deleteCoinsAndInsertNewOnes(coins);
            mAppDatabase.coinDao().insert(coins);

            return coins;

        });
    }


    private Coin buildCoinFromTickerData(TickerResult.TickerData data, Currency defaultCurrency) {

        TickerResult.TickerData.QuoteData usdQuoteData = data.getQuotes().get(Currency.USD.getCode());
        if(defaultCurrency == Currency.USD) {
            return new Coin(data.getId(),
                    data.getName(),
                    data.getSymbol(),
                    data.getWebsiteSlug(),
                    data.getRank(),
                    data.getCirculatingSupply(),
                    data.getTotalSupply(),
                    data.getMaxSupply(),
                    (new Date().getTime()/1000),
                    usdQuoteData.getPrice(),
                    usdQuoteData.getVolume24h(),
                    usdQuoteData.getMarketCap(),
                    usdQuoteData.getPrice(),
                    usdQuoteData.getVolume24h(),
                    usdQuoteData.getMarketCap(),
                    usdQuoteData.getPercentChange1h(),
                    usdQuoteData.getPercentChange24h(),
                    usdQuoteData.getPercentChange7d());
        } else {

            TickerResult.TickerData.QuoteData defaultQuoteData = data.getQuotes().get(defaultCurrency.getCode());
            return new Coin(data.getId(),
                    data.getName(),
                    data.getSymbol(),
                    data.getWebsiteSlug(),
                    data.getRank(),
                    data.getCirculatingSupply(),
                    data.getTotalSupply(),
                    data.getMaxSupply(),
                    (new Date().getTime()/1000),
                    usdQuoteData.getPrice(),
                    usdQuoteData.getVolume24h(),
                    usdQuoteData.getMarketCap(),
                    defaultQuoteData.getPrice(),
                    defaultQuoteData.getVolume24h(),
                    defaultQuoteData.getMarketCap(),
                    usdQuoteData.getPercentChange1h(),
                    usdQuoteData.getPercentChange24h(),
                    usdQuoteData.getPercentChange7d());
        }
    }







    /**
     * Fetches locally saved Coins
     */
    public Single<List<Coin>> getLocalCoins() {

        switch (mSharedPreferenceHelper.getCoinsToFetch()) {
            case TOP_50:
                return mAppDatabase.coinDao().getTopByMarketCap(50);
            case TOP_100:
                return mAppDatabase.coinDao().getTopByMarketCap(100);
            case TOP_500:
                return mAppDatabase.coinDao().getTopByMarketCap(500);
            case ALL:
            default:
                return mAppDatabase.coinDao().getAllByMarketCap();
        }
    }

    public Maybe<Coin> getLocalCoin(long coinId) {
        return  mAppDatabase.coinDao().getById(coinId)
                .map(coin -> {
                    if(mSharedPreferenceHelper.getDefaultCurrency() == Currency.BTC) {
                        coin.setQuoteBtcPrice(coin.getQuoteDefaultPrice());
                    } else {
                        Coin btc = mAppDatabase.coinDao().getBySymbol(Currency.BTC.getCode()).blockingGet();
                        double btcPrice = coin.getQuoteUsdPrice() / btc.getQuoteUsdPrice();
                        coin.setQuoteBtcPrice(btcPrice);
                    }

                    return coin;
                });
    }

    /**
     * Fetches remote ticker data, filtered by favorites, returns a List of Coins
     */
    public Single<List<Coin>> getRemoteFavoriteCoins() {
        final Currency currency = mSharedPreferenceHelper.getDefaultCurrency();
        return mAppDatabase.favoriteCoinDao().getAll()
                .map(new Function<List<Coin>, List<Coin>>() {
                    @Override
                    public List<Coin> apply(List<Coin> coins) {

                        List<Single<TickerResult>> list = new ArrayList<>();
                        for (Coin c: coins)
                            list.add(mCoinMarketCapApi.getTickerSingleCurrency(c.getId(), currency.getCode()));

                        return Single.zip(list, new Function<Object[], List<Coin>>() {
                            @Override
                            public List<Coin> apply(Object[] objects) {
                                List<Coin> coins = new ArrayList<>();

                                for(Object o : objects)
                                    if(o instanceof TickerResult)
                                        for (TickerResult.TickerData data : ((TickerResult)o).getData())
                                            coins.add(buildCoinFromTickerData(data, currency));

                                //Save to DB
                                mAppDatabase.coinDao().insert(coins);

                                Collections.sort(coins);
                                return coins;
                            }
                        }).blockingGet();
                    }
                });
    }

    public Single<Coin> getCoinMarketCapTicker(long coinMarketCapCoinId, Currency currency) {
        return mCoinMarketCapApi.getTickerSingleCurrency(coinMarketCapCoinId, currency.getCode())
                .map(tickerResult -> buildCoinFromTickerData(tickerResult.getData().get(0), currency));
    }

    /**
     * Fetches locally saved Coins, filtered by favorites
     */
    public Single<List<Coin>> getLocalFavoriteCoins() {
        return mAppDatabase.favoriteCoinDao().getAll()
                .map(coins -> {
                    Collections.sort(coins);
                    return coins;
                });
    }


    /**
     * Gets the oldest lastUpdated stored in db
     */
    public Single<Long> getOldestCoinLastUpdated() {
        return mAppDatabase.coinDao().getOldestLastUpdated();
    }


    /**
     * Gets the oldest lastUpdated stored in db, filtered by favorites
     */
    public Single<Long> getOldestFavoriteCoinLastUpdated() {
        return mAppDatabase.favoriteCoinDao().getOldestLastUpdated();
    }


    /**
     * Checks if coinId is saved as favorite
     */
    public Single<Integer> isFavoriteCoin(long coinId) {
        return mAppDatabase.favoriteCoinDao().coinIsFavorite(coinId);
    }

    /**
     * Insert coinId into favorites
     */
    public Completable setCoinAsFavorite(long coinId) {
        return Completable.fromCallable(() -> {
            mAppDatabase.favoriteCoinDao().insert(new FavoriteCoin(coinId));
            return null;
        });
    }

    /**
     * Remove coinId from favorites
     */
    public Completable removeCoinFromFavorites(long coinId) {
        return Completable.fromCallable(() -> {
            mAppDatabase.favoriteCoinDao().delete(new FavoriteCoin(coinId));
            return null;
        });
    }

    /**
     * Pulls Global Market data from CoinMarketCap:
     * - Active Cryptocurrencies
     * - Active Markets
     * - Bitcoin dominance (%)
     * - Market cap and 24h vol
     */
    public Single<GlobalResult> getMarketGlobalData() {
        return mCoinMarketCapApi.getGlobal(Currency.USD.name());
    }





    public Single<MarketCapAndVolumeChartData> getMaketCapAndVolumeGraphData(long timeStart, long timeEnd, ChartTimeSpan chartTimeSpan) {
        return mCoinMarketCapGraphApi.getTotalMarketCapAndVolumeGraphData(timeStart, timeEnd)
                .map(result -> {


                    List<Entry> marketCapEntries = new ArrayList<>();
                    List<Entry> volumeEntries = new ArrayList<>();
                    List<Long> timestamps = new ArrayList<>();

                    double[][] mcapVals = result.getMarketCapByAvailableSupply();
                    double[][] volumeVals = result.getVolumeUsd();

                    int increment = calculateIncrementFor(result.getMarketCapByAvailableSupply().length);
                    int count = 0;
                    for (int i = 0; i < result.getMarketCapByAvailableSupply().length; i+=increment) {

                        long x = (long)mcapVals[i][0];
                        float yMcap = ((float) mcapVals[i][1]) / Constants.MISC_BILLION_DIVIDER;
                        float yVol = ((float) volumeVals[i][1]) / Constants.MISC_BILLION_DIVIDER;

                        marketCapEntries.add(new Entry(count, yMcap));
                        volumeEntries.add(new Entry(count, yVol));
                        timestamps.add(x);
                        count++;
                    }

                    //Collections.sort(entries, new EntryXComparator());
                    return new MarketCapAndVolumeChartData(marketCapEntries, (long)mcapVals[mcapVals.length-1][1], volumeEntries, (long)volumeVals[volumeVals.length-1][1], timestamps, chartTimeSpan);
                });
    }


    public Single<MarketCapPriceAndVolumeChartData> getCurrencyMarketCapPriceAndVolumeGraphData(String websiteSlug, long timeStart, long timeEnd, ChartTimeSpan chartTimeSpan) {
        return mCoinMarketCapGraphApi.getCurrencyMarketCapPriceAndVolumeGraphData(websiteSlug, timeStart, timeEnd)
                .map(result -> {
                    long[][] mcapVals = result.getMarketCapByAvailableSupply();
                    long[][] volumeVals = result.getVolumeUsd();
                    double[][] priceBtcVals = result.getPriceBtc();
                    double[][] priceUsdVals = result.getPriceUsd();

                    //TODO: get BTC / DefCurr or USD / DefCurr exchange here, unless DefCurr is BTC or USD.. in which case just use available result.getPriceBtc or result.getPriceUsd

                    List<Entry> marketCapEntries = new ArrayList<>();
                    List<Entry> volumeEntries = new ArrayList<>();
                    List<Entry> priceBtcEntries = new ArrayList<>();
                    List<Entry> priceDefaultCurrencyEntries = new ArrayList<>();
                    List<Long> timestamps = new ArrayList<>();

                    double priceBtcMin = Double.MAX_VALUE;
                    double priceBtcMax = 0;
                    double priceDefaultCurrencyMin = Double.MAX_VALUE;
                    double priceDefaultCurrencyMax = 0;

                    int length = result.getMarketCapByAvailableSupply().length;
                    int increment = calculateIncrementFor(length);
                    int count = 0;
                    for (int i = 0; i < length; i+=increment) {

                        long x = mcapVals[i][0];
                        float yMcap = ((float) mcapVals[i][1]) / Constants.MISC_BILLION_DIVIDER;
                        float yVol = ((float) volumeVals[i][1]) / Constants.MISC_BILLION_DIVIDER;
                        marketCapEntries.add(new Entry(count, yMcap));
                        volumeEntries.add(new Entry(count, yVol));

                        //Get min and max
                        if(priceBtcVals[i][1] < priceBtcMin) priceBtcMin = priceBtcVals[i][1];
                        if(priceBtcVals[i][1] > priceBtcMax) priceBtcMax = priceBtcVals[i][1];
                        priceBtcEntries.add(new Entry(count, (float)priceBtcVals[i][1]));


                        double priceDefaultCurrencyVal = priceUsdVals[i][1];                                    //TODO NEED TO CONVERT USD TO DEFAULT CURRENCY PROPERLY!!
                        //Get min and max
                        if(priceDefaultCurrencyVal < priceDefaultCurrencyMin) priceDefaultCurrencyMin = priceDefaultCurrencyVal;
                        if(priceDefaultCurrencyVal > priceDefaultCurrencyMax) priceDefaultCurrencyMax = priceDefaultCurrencyVal;
                        priceDefaultCurrencyEntries.add(new Entry(count, (float)priceDefaultCurrencyVal));

                        timestamps.add(x);
                        count++;
                    }

                    //Calculate start, end, price and percentage variations
                    double priceBtcStart = priceBtcVals[0][1];
                    double priceBtcEnd = priceBtcVals[length-1][1];
                    double priceBtcVariation = priceBtcEnd - priceBtcStart;
                    double percentageBtcVariation = (priceBtcVariation*100)/Math.min(priceBtcStart, priceBtcEnd);

                    double priceDefaultCurrencyStart = priceUsdVals[0][1];                                              //TODO NEED TO CONVERT USD TO DEFAULT CURRENCY PROPERLY!!
                    double priceDefaultCurrencyEnd = priceUsdVals[length-1][1];                                           //TODO NEED TO CONVERT USD TO DEFAULT CURRENCY PROPERLY!!
                    double priceDefaultCurrencyVariation = priceDefaultCurrencyEnd - priceDefaultCurrencyStart;
                    double percentageDefaultCurrencyVariation = (priceDefaultCurrencyVariation*100)/Math.min(priceDefaultCurrencyStart, priceDefaultCurrencyEnd);


                    return new MarketCapPriceAndVolumeChartData(marketCapEntries, mcapVals[mcapVals.length-1][1],
                            priceDefaultCurrencyEntries, priceDefaultCurrencyEnd, priceBtcEntries, priceBtcEnd,
                            volumeEntries, volumeVals[volumeVals.length-1][1], timestamps, chartTimeSpan,
                            priceBtcMin, priceBtcMax, priceBtcVariation, percentageBtcVariation,
                            priceDefaultCurrencyMin, priceDefaultCurrencyMax, priceDefaultCurrencyVariation, percentageDefaultCurrencyVariation);
                });
    }



    public Single<DominanceChartData> getDominanceGraphData(long timeStart, long timeEnd, ChartTimeSpan chartTimeSpan) {
        return mCoinMarketCapGraphApi.getDominanceGraphData(timeStart, timeEnd)
                .map(result -> {
                    List<Entry> mostDominantCoinEntries = new ArrayList<>();
                    List<Entry> lessDominantCoinEntries = new ArrayList<>();
                    List<Entry> leastDominantCoinEntries = new ArrayList<>();
                    List<Entry> otherCoinEntries = new ArrayList<>();
                    List<Long> timestamps = new ArrayList<>();

                    double[][] btcVals = result.getBitcoin();
                    double[][] ethVals = result.getEthereum();
                    double[][] xrpVals = result.getRipple();

                    int count = 0;
                    int increment = calculateIncrementFor(result.getBitcoin().length);
                    for (int i = 0; i < result.getBitcoin().length; i+=increment) {
                        mostDominantCoinEntries.add(    new Entry(count,     (float)btcVals[i][1])                                                             );
                        lessDominantCoinEntries.add(    new Entry(count,  (float)ethVals[i][1] + (float)btcVals[i][1])                                      );
                        leastDominantCoinEntries.add(   new Entry(count,  (float)xrpVals[i][1] + (float)ethVals[i][1] + (float)btcVals[i][1])               );
                        //otherCoinEntries.add(           new Entry(count,     (100f - (float)btcVals[i][1] - (float)ethVals[i][1] - (float)xrpVals[i][1]))      );
                        otherCoinEntries.add(           new Entry(count,     100f)                                                                          );
                        timestamps.add((long)btcVals[i][0]);
                        count++;
                    }

                    //Collections.sort(entries, new EntryXComparator());
                    return new DominanceChartData(mostDominantCoinEntries,
                            (float)btcVals[btcVals.length-1][1],
                            "Bitcoin",
                            lessDominantCoinEntries,
                            (float)ethVals[ethVals.length-1][1],
                            "Ethereum",
                            leastDominantCoinEntries,
                            (float)xrpVals[ethVals.length-1][1],
                            "Ripple",
                            otherCoinEntries,
                            (100f - (float)btcVals[btcVals.length-1][1] - (float)ethVals[btcVals.length-1][1] - (float)xrpVals[btcVals.length-1][1]),
                            timestamps, chartTimeSpan);
                });
    }

    private int calculateIncrementFor(int length) {
        if(length < Constants.MISC_MAX_CHART_ENTRIES)
            return length;
        else {
            return Math.round(length/Constants.MISC_MAX_CHART_ENTRIES);
        }
    }


    public Completable refreshCachedCoins() {
        return mCoinMarketCapS2Api.getCurrencies()
                .map(result -> {
                    List<CachedCoin> cachedCoins = new ArrayList<>();
                    for (CurrencyResult r : result)
                        cachedCoins.add(new CachedCoin(r.getId(), r.getName(), r.getSymbol(), r.getSlug(), r.getRank()));
                    mAppDatabase.cachedCoinDao().deleteCachedCoinsAndInsertNewOnes(cachedCoins);
                    return true;

                }).toCompletable();
    }

    public Single<Boolean> cachedCoinsInDatabase() {
        return mAppDatabase.cachedCoinDao().count()
                .map(count -> {
                    return count > 0;
                });
    }

    public Single<List<CachedCoin>> getRankedCachedCoins(int amount) {
        return mAppDatabase.cachedCoinDao().getByRank(amount);
    }

    public Single<List<CachedCoin>> getCachedCoins(String query) {
        return mAppDatabase.cachedCoinDao().find("%"+query+"%");
    }


    public Single<List<Exchange>> getTopExchangesByPair(String fromCode, String toCode, int limit) {
        return mCryptoCompareMinApi.getTopExchangesByPair(fromCode, toCode, limit)
                .map(result -> {
                    List<Exchange> exchanges = new ArrayList<>();

                    if (result.getData() instanceof TopExchangesResult.Data) {
                        TopExchangesResult.Data data = (TopExchangesResult.Data)result.getData();

                        if(data.getExchangeList() != null) {
                            for (int i = 0; i < data.getExchangeList().size(); i++) {
                                TopExchangesResult.Exchange exchangeData = data.getExchangeList().get(i);
                                exchanges.add(new Exchange(i + 1, exchangeData.getName(), exchangeData.getFromCode(),
                                        exchangeData.getToCode(), exchangeData.getPrice(), exchangeData.getVolume24Hour()));
                            }
                        }
                    }
                    return exchanges;
                });
    }


    public Single<List<Alarm>> getAlarms() {
        return mAppDatabase.alarmDao().getAll();
    }


    public int deleteAlarm(Alarm alarm) {
        int result = mAppDatabase.alarmDao().delete(alarm);
        return result;
    }


    public Completable insertAlarm(Alarm alarm) {
        return Completable.fromAction(() -> mAppDatabase.alarmDao().insert(alarm));
    }
}

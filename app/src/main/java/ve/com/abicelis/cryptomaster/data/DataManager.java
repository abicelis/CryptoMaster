package ve.com.abicelis.cryptomaster.data;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import ve.com.abicelis.cryptomaster.application.Constants;
import ve.com.abicelis.cryptomaster.data.local.AppDatabase;
import ve.com.abicelis.cryptomaster.data.local.SharedPreferenceHelper;
import ve.com.abicelis.cryptomaster.data.model.ChartTimeSpan;
import ve.com.abicelis.cryptomaster.data.model.Coin;
import ve.com.abicelis.cryptomaster.data.model.Currency;
import ve.com.abicelis.cryptomaster.data.model.FavoriteCoin;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcap.GlobalResult;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcap.TickerResult;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcapgraph.DominanceChartData;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcapgraph.MarketCapAndVolumeChartData;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcapgraph.MarketCapPriceAndVolumeChartData;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcaps2.CurrencyResult;
import ve.com.abicelis.cryptomaster.data.remote.CoinMarketCapApi;
import ve.com.abicelis.cryptomaster.data.remote.CoinMarketCapGraphApi;
import ve.com.abicelis.cryptomaster.data.remote.CoinMarketCapS2Api;
import ve.com.abicelis.cryptomaster.data.remote.CryptoCompareApi;

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

    @Inject
    public DataManager(AppDatabase appDatabase,
                       SharedPreferenceHelper sharedPreferenceHelper,
                       CoinMarketCapApi coinMarketCapApi,
                       CoinMarketCapGraphApi coinMarketCapGraphApi,
                       CoinMarketCapS2Api coinMarketCapS2Api,
                       CryptoCompareApi cryptoCompareApi) {

        mAppDatabase = appDatabase;
        mSharedPreferenceHelper = sharedPreferenceHelper;
        mCoinMarketCapApi = coinMarketCapApi;
        mCoinMarketCapGraphApi = coinMarketCapGraphApi;
        mCoinMarketCapS2Api = coinMarketCapS2Api;
        mCryptoCompareApi = cryptoCompareApi;
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
        return  mAppDatabase.coinDao().getById(coinId);
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

                    long[][] mcapVals = result.getMarketCapByAvailableSupply();
                    long[][] volumeVals = result.getVolumeUsd();

                    int increment = calculateIncrementFor(result.getMarketCapByAvailableSupply().length);
                    int count = 0;
                    for (int i = 0; i < result.getMarketCapByAvailableSupply().length; i+=increment) {

                        long x = mcapVals[i][0];
                        float yMcap = ((float) mcapVals[i][1]) / Constants.MISC_BILLION_DIVIDER;
                        float yVol = ((float) volumeVals[i][1]) / Constants.MISC_BILLION_DIVIDER;

                        marketCapEntries.add(new Entry(count, yMcap));
                        volumeEntries.add(new Entry(count, yVol));
                        timestamps.add(x);
                        count++;
                    }

                    //Collections.sort(entries, new EntryXComparator());
                    return new MarketCapAndVolumeChartData(marketCapEntries, mcapVals[mcapVals.length-1][1], volumeEntries, volumeVals[volumeVals.length-1][1], timestamps, chartTimeSpan);
                });
    }


    public Single<MarketCapPriceAndVolumeChartData> getCurrencyMarketCapPriceAndVolumeGraphData(String websiteSlug, long timeStart, long timeEnd, ChartTimeSpan chartTimeSpan) {
        return mCoinMarketCapGraphApi.getCurrencyMarketCapPriceAndVolumeGraphData(websiteSlug, timeStart, timeEnd)
                .map(result -> {


                    List<Entry> marketCapEntries = new ArrayList<>();
                    List<Entry> priceUsdEntries = new ArrayList<>();
                    List<Entry> priceBtcEntries = new ArrayList<>();
                    List<Entry> volumeEntries = new ArrayList<>();
                    List<Long> timestamps = new ArrayList<>();

                    long[][] mcapVals = result.getMarketCapByAvailableSupply();
                    double[][] priceUsdVals = result.getPriceUsd();
                    double[][] priceBtcVals = result.getPriceBtc();
                    long[][] volumeVals = result.getVolumeUsd();

                    int increment = calculateIncrementFor(result.getMarketCapByAvailableSupply().length);
                    int count = 0;
                    for (int i = 0; i < result.getMarketCapByAvailableSupply().length; i+=increment) {

                        long x = mcapVals[i][0];
                        float yMcap = ((float) mcapVals[i][1]) / Constants.MISC_BILLION_DIVIDER;
                        float yVol = ((float) volumeVals[i][1]) / Constants.MISC_BILLION_DIVIDER;

                        marketCapEntries.add(new Entry(count, yMcap));
                        priceUsdEntries.add(new Entry(count, (float)priceUsdVals[i][1]));
                        priceBtcEntries.add(new Entry(count, (float)priceBtcVals[i][1]));
                        volumeEntries.add(new Entry(count, yVol));
                        timestamps.add(x);
                        count++;
                    }

                    //Collections.sort(entries, new EntryXComparator());
                    return new MarketCapPriceAndVolumeChartData(marketCapEntries, mcapVals[mcapVals.length-1][1], priceUsdEntries, priceUsdVals[priceUsdVals.length-1][1], priceBtcEntries, priceBtcVals[priceBtcVals.length-1][1],  volumeEntries, volumeVals[volumeVals.length-1][1], timestamps, chartTimeSpan);
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


    public Single<CurrencyResult[]> getCurrencies() {
        return mCoinMarketCapS2Api.getCurrencies();
    }



}

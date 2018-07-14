package ve.com.abicelis.cryptomaster.data;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
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


    /**
     * Fetches remote ticker data, returns a List of Coins
     */
    public Single<List<Coin>> getRemoteCoins(String currency) {

        return mCoinMarketCapApi.getTicker(1,1, currency)
                .map(tickerResult -> {
                    //final int numCryptoCurrencies = tickerResult.getMetadata().getNumCryptocurrencies();
                    final int numCryptoCurrencies = 100;

                    List<Single<TickerResult>> list = new ArrayList<>();
                    for (int i = 1; (i - 1) < numCryptoCurrencies; i += 100)
                        list.add(mCoinMarketCapApi.getTicker(i, 100, currency));


                    return Single.zip(list, tickerResults -> {
                            List<Coin> coins = new ArrayList<>();

                            for (Object o : tickerResults) {
                                if (o instanceof TickerResult) {
                                    for (TickerResult.TickerData data : ((TickerResult) o).getData()) {

                                        TickerResult.TickerData.QuoteData quoteData = data.getQuotes().get(currency);
                                        coins.add(new Coin(data.getId(),
                                                data.getName(),
                                                data.getSymbol(),
                                                data.getWebsiteSlug(),
                                                data.getRank(),
                                                data.getCirculatingSupply(),
                                                data.getTotalSupply(),
                                                data.getMaxSupply(),
                                                (new Date().getTime()/1000),
                                                currency,
                                                quoteData.getPrice(),
                                                quoteData.getVolume24h(),
                                                quoteData.getMarketCap(),
                                                quoteData.getPercentChange1h(),
                                                quoteData.getPercentChange24h(),
                                                quoteData.getPercentChange7d()));
                                    }
                                }
                            }

                            //Save to DB
                            mAppDatabase.coinDao().deleteCoinsAndInsertNewOnes(coins);

                            return coins;

                    }).blockingGet();
                });

//        return mCoinMarketCapApi.getTicker(start, limit, currency)
//                .map(tickerResult -> {
//                    List<Coin> coins = new ArrayList<>();
//                    //List<FavoriteCoin> favCoins = new ArrayList<>();
//                    for (TickerResult.TickerData data : tickerResult.getData()) {
//                        TickerResult.TickerData.QuoteData quoteData = data.getQuotes().get(currency);
//                        coins.add(new Coin(data.getId(),
//                                data.getName(),
//                                data.getSymbol(),
//                                data.getWebsiteSlug(),
//                                data.getRank(),
//                                data.getCirculatingSupply(),
//                                data.getTotalSupply(),
//                                data.getMaxSupply(),
//                                (new Date().getTime()/1000),
//                                currency,
//                                quoteData.getPrice(),
//                                quoteData.getVolume24h(),
//                                quoteData.getMarketCap(),
//                                quoteData.getPercentChange1h(),
//                                quoteData.getPercentChange24h(),
//                                quoteData.getPercentChange7d()));
//
//                        //favCoins.add(new FavoriteCoin(data.getId()));
//                    }
//
//                    Collections.sort(coins);
//
//                    //Save to DB
//                    mAppDatabase.coinDao().deleteCoinsAndInsertNewOnes(coins);
//                    return coins;
//                });
    }

    /**
     * Fetches locally saved Coins
     */
    public Single<List<Coin>> getLocalCoins() {
        return mAppDatabase.coinDao().getAll()
                .map(coins -> {
                    Collections.sort(coins);
                    return coins;

                });
    }

    /**
     * Fetches remote ticker data, filtered by favorites, returns a List of Coins
     */
    public Single<List<Coin>> getRemoteFavoriteCoins(String currency) {

        return mAppDatabase.favoriteCoinDao().getAll()
                .map(new Function<List<Coin>, List<Coin>>() {
                    @Override
                    public List<Coin> apply(List<Coin> coins) {

                        List<Single<TickerResult>> list = new ArrayList<>();
                        for (Coin c: coins)
                            list.add(mCoinMarketCapApi.getTickerSingleCurrency(c.getId(), currency));

                        return Single.zip(list, new Function<Object[], List<Coin>>() {
                            @Override
                            public List<Coin> apply(Object[] objects) {
                                List<Coin> coins = new ArrayList<>();
                                for(int i = 0; i < objects.length; i++) {
                                    if(objects[i] instanceof TickerResult) {
                                        for (TickerResult.TickerData data : ((TickerResult)objects[i]).getData()) {
                                            TickerResult.TickerData.QuoteData quoteData = data.getQuotes().get(currency);
                                            coins.add(new Coin(data.getId(),
                                                    data.getName(),
                                                    data.getSymbol(),
                                                    data.getWebsiteSlug(),
                                                    data.getRank(),
                                                    data.getCirculatingSupply(),
                                                    data.getTotalSupply(),
                                                    data.getMaxSupply(),
                                                    (new Date().getTime()/1000),
                                                    currency,
                                                    quoteData.getPrice(),
                                                    quoteData.getVolume24h(),
                                                    quoteData.getMarketCap(),
                                                    quoteData.getPercentChange1h(),
                                                    quoteData.getPercentChange24h(),
                                                    quoteData.getPercentChange7d()));
                                        }
                                    }
                                }

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
                        float yMcap = ((float) mcapVals[i][1]) / 1000000000;
                        float yVol = ((float) volumeVals[i][1]) / 1000000000;

                        marketCapEntries.add(new Entry(count, yMcap));
                        volumeEntries.add(new Entry(count, yVol));
                        timestamps.add(x);
                        count++;
                    }

                    //Collections.sort(entries, new EntryXComparator());
                    return new MarketCapAndVolumeChartData(marketCapEntries, mcapVals[mcapVals.length-1][1], volumeEntries, volumeVals[volumeVals.length-1][1], timestamps, chartTimeSpan);
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

package ve.com.abicelis.cryptomaster.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import lecho.lib.hellocharts.model.PointValue;
import ve.com.abicelis.cryptomaster.data.local.AppDatabase;
import ve.com.abicelis.cryptomaster.data.local.SharedPreferenceHelper;
import ve.com.abicelis.cryptomaster.data.model.Coin;
import ve.com.abicelis.cryptomaster.data.model.Currency;
import ve.com.abicelis.cryptomaster.data.model.FavoriteCoin;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcap.GlobalResult;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcap.TickerResult;
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
     * Grabs ticker data from CoinMarketCap
     */
    public Single<List<Coin>> getRemoteCoins(int start, int limit, String currency) {
        return mCoinMarketCapApi.getTicker(start, limit, currency)
                .map(tickerResult -> {
                    List<Coin> coins = new ArrayList<>();
                    List<FavoriteCoin> favCoins = new ArrayList<>();
                    for (TickerResult.TickerData data : tickerResult.getData()) {
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

                        favCoins.add(new FavoriteCoin(data.getId()));
                    }

                    Collections.sort(coins);

                    //Save to DB
                    mAppDatabase.coinDao().insert(coins);
                    return coins;
                });
    }

    public Single<List<Coin>> getLocalCoins() {
        return mAppDatabase.coinDao().getAll()
                .map(coins -> {
                    Collections.sort(coins);
                    return coins;

                });
    }

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

    public Single<List<Coin>> getLocalFavoriteCoins() {
        return mAppDatabase.favoriteCoinDao().getAll()
                .map(coins -> {
                    Collections.sort(coins);
                    return coins;

                });
    }




    public Single<Long> getOldestCoinLastUpdated() {
        return mAppDatabase.favoriteCoinDao().getOldestLastUpdated();
    }

    public Single<Integer> isFavoriteCoin(long coinId) {
        return mAppDatabase.favoriteCoinDao().coinIsFavorite(coinId);
    }
    public Completable setCoinAsFavorite(long coinId) {
        return Completable.fromCallable(() -> {
            mAppDatabase.favoriteCoinDao().insert(new FavoriteCoin(coinId));
            return null;
        });
    }
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
    public Single<GlobalResult> getCoinMarketCapGlobal() {
        return mCoinMarketCapApi.getGlobal(Currency.USD.name());
    }





    public Single<List<PointValue>> getMaketCapGraphData(long timeStart, long timeEnd) {
        return mCoinMarketCapGraphApi.getTotalMarketCapAndVolumeGraphData(timeStart, timeEnd)
                .map(result -> {
                    List<PointValue> values = new ArrayList<>();
                    long[][] vals = result.getMarketCapByAvailableSupply();
                    for (int i = 0; i < result.getMarketCapByAvailableSupply().length; i++) {
                        values.add(new PointValue(vals[i][0], vals[i][1]));
                    }
                    return values;
                });
    }


    public Single<CurrencyResult[]> getCurrencies() {
        return mCoinMarketCapS2Api.getCurrencies();
    }




    //TODO kill these, are only here for testing purposes

    //@Deprecated
    //public AppDatabase getDatabase() {
    //    return mAppDatabase;
    //}

}

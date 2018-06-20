package ve.com.abicelis.cryptomaster.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import lecho.lib.hellocharts.model.PointValue;
import ve.com.abicelis.cryptomaster.data.local.SharedPreferenceHelper;
import ve.com.abicelis.cryptomaster.data.model.Coin;
import ve.com.abicelis.cryptomaster.data.model.Currency;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcap.GlobalResult;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcap.TickerResult;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcaps2.CurrencyResult;
import ve.com.abicelis.cryptomaster.data.remote.CoinMarketCapApi;
import ve.com.abicelis.cryptomaster.data.remote.CoinMarketCapGraphApi;
import ve.com.abicelis.cryptomaster.data.remote.CoinMarketCapS2Api;
import ve.com.abicelis.cryptomaster.data.remote.CryptoCompareApi;
import ve.com.abicelis.cryptomaster.util.StringUtil;

/**
 * Created by abicelis on 29/8/2017.
 */

public class DataManager {

    //private AppDatabase mAppDatabase;
    private SharedPreferenceHelper mSharedPreferenceHelper;
    private CoinMarketCapApi mCoinMarketCapApi;
    private CoinMarketCapGraphApi mCoinMarketCapGraphApi;
    private CoinMarketCapS2Api mCoinMarketCapS2Api;
    private CryptoCompareApi mCryptoCompareApi;

    @Inject
    public DataManager(//AppDatabase appDatabase,
                       SharedPreferenceHelper sharedPreferenceHelper,
                       CoinMarketCapApi coinMarketCapApi,
                       CoinMarketCapGraphApi coinMarketCapGraphApi,
                       CoinMarketCapS2Api coinMarketCapS2Api,
                       CryptoCompareApi cryptoCompareApi) {

        //mAppDatabase = appDatabase;
        mSharedPreferenceHelper = sharedPreferenceHelper;
        mCoinMarketCapApi = coinMarketCapApi;
        mCoinMarketCapGraphApi = coinMarketCapGraphApi;
        mCoinMarketCapS2Api = coinMarketCapS2Api;
        mCryptoCompareApi = cryptoCompareApi;
    }


    /**
     * Grabs ticker data from CoinMarketCap
     */
    public Single<List<Coin>> getCoins(int start, int limit, String currency) {
        return mCoinMarketCapApi.getTicker(start, limit, "id", currency)
                .map(tickerResult -> {
                    List<Coin> coins = new ArrayList<>();
                    for (TickerResult.TickerData data : tickerResult.getData()) {
                        TickerResult.TickerData.QuoteData quoteData = data.getQuotes().get(currency);
                        coins.add(new Coin(data.getId(),
                                data.getName(),
                                data.getSymbol(),
                                quoteData.getPrice(),
                                quoteData.getVolume24h(),
                                quoteData.getMarketCap(),
                                quoteData.getPercentChange1h(),
                                quoteData.getPercentChange24h(),
                                quoteData.getPercentChange7d()));
                    }

                    Collections.sort(coins);
                    return coins;
                });
    }

    public Single<List<Coin>> getCoins(int[] coinIds, String currency) {
        List<Single<TickerResult>> list = new ArrayList<>();

        if(coinIds.length == 0)
            return Single.just(new ArrayList<>());

        for (int i = 0; i < coinIds.length; i++)
            list.add(mCoinMarketCapApi.getTickerSingleCurrency(coinIds[i], currency));

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
                                        quoteData.getPrice(),
                                        quoteData.getVolume24h(),
                                        quoteData.getMarketCap(),
                                        quoteData.getPercentChange1h(),
                                        quoteData.getPercentChange24h(),
                                        quoteData.getPercentChange7d()));
                            }
                        }
                    }

                    Collections.sort(coins);
                    return coins;
                }
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

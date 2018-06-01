package ve.com.abicelis.cryptomaster.data;

import javax.inject.Inject;

import io.reactivex.Maybe;
import ve.com.abicelis.cryptomaster.data.local.SharedPreferenceHelper;
import ve.com.abicelis.cryptomaster.data.model.Currency;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcap.GlobalResult;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcap.TickerResult;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcapgraph.TotalMarketCapResult;
import ve.com.abicelis.cryptomaster.data.remote.CoinMarketCapApi;
import ve.com.abicelis.cryptomaster.data.remote.CoinMarketCapGraphApi;
import ve.com.abicelis.cryptomaster.data.remote.CryptoCompareApi;

/**
 * Created by abicelis on 29/8/2017.
 */

public class DataManager {

    //private AppDatabase mAppDatabase;
    private SharedPreferenceHelper mSharedPreferenceHelper;
    private CoinMarketCapApi mCoinMarketCapApi;
    private CoinMarketCapGraphApi mCoinMarketCapGraphApi;
    private CryptoCompareApi mCryptoCompareApi;

    @Inject
    public DataManager(//AppDatabase appDatabase,
                       SharedPreferenceHelper sharedPreferenceHelper,
                       CoinMarketCapApi coinMarketCapApi, CoinMarketCapGraphApi coinMarketCapGraphApi, CryptoCompareApi cryptoCompareApi) {
        //mAppDatabase = appDatabase;
        mSharedPreferenceHelper = sharedPreferenceHelper;
        mCoinMarketCapApi = coinMarketCapApi;
        mCoinMarketCapGraphApi = coinMarketCapGraphApi;
        mCryptoCompareApi = cryptoCompareApi;
    }


    /**
     * Grabs ticker data from CoinMarketCap
     */
    public Maybe<TickerResult> getTicker() {
        return mCoinMarketCapApi.getTicker(1, 100, "id", "USD");
    }

    /**
     * Pulls Global Market data from CoinMarketCap:
     * - Active Cryptocurrencies
     * - Active Markets
     * - Bitcoin dominance (%)
     * - Market cap and 24h vol
     */
    public Maybe<GlobalResult> getCoinMarketCapGlobal() {
        return mCoinMarketCapApi.getGlobal(Currency.USD.name());
    }


    /**
     */
    public Maybe<TotalMarketCapResult> getTotalMaketCap() {
        return mCoinMarketCapGraphApi.getTotalMaketCap(1520873220000L,1520874220000L);
    }





    //TODO kill these, are only here for testing purposes

    //@Deprecated
    //public AppDatabase getDatabase() {
    //    return mAppDatabase;
    //}

}

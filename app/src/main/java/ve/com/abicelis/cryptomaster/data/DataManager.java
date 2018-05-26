package ve.com.abicelis.cryptomaster.data;

import javax.inject.Inject;

import io.reactivex.Maybe;
import ve.com.abicelis.cryptomaster.data.local.SharedPreferenceHelper;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcap.TickerResult;
import ve.com.abicelis.cryptomaster.data.remote.CoinMarketCapApi;
import ve.com.abicelis.cryptomaster.data.remote.CryptoCompareApi;

/**
 * Created by abicelis on 29/8/2017.
 */

public class DataManager {

    //private AppDatabase mAppDatabase;
    private SharedPreferenceHelper mSharedPreferenceHelper;
    private CoinMarketCapApi mCoinMarketCapApi;
    private CryptoCompareApi mCryptoCompareApi;

    @Inject
    public DataManager(//AppDatabase appDatabase,
                       SharedPreferenceHelper sharedPreferenceHelper,
                       CoinMarketCapApi coinMarketCapApi, CryptoCompareApi cryptoCompareApi) {
        //mAppDatabase = appDatabase;
        mSharedPreferenceHelper = sharedPreferenceHelper;
        mCoinMarketCapApi = coinMarketCapApi;
        mCryptoCompareApi = cryptoCompareApi;
    }



    public Maybe<TickerResult> getTicker() {
        return mCoinMarketCapApi.getTicker(1, 100, "id", "USD");
    }





    //TODO kill these, are only here for testing purposes

    //@Deprecated
    //public AppDatabase getDatabase() {
    //    return mAppDatabase;
    //}

}

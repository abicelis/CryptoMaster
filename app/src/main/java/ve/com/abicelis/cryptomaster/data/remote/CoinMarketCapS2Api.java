package ve.com.abicelis.cryptomaster.data.remote;

import io.reactivex.Single;
import retrofit2.http.GET;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcaps2.CurrencyResult;

/**
 * Created by abicelis on 24/5/2018.
 */
public interface CoinMarketCapS2Api {

    @GET("generated/search/quick_search.json")
    Single<CurrencyResult[]> getCurrencies();

}

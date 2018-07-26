package ve.com.abicelis.cryptomaster.data.remote;


import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ve.com.abicelis.cryptomaster.data.model.cryptocomparemin.TopExchangesResult;

/**
 * Created by abicelis on 24/5/2018.
 */
public interface CryptoCompareMinApi {


    @GET("top/exchanges/full")
    Single<TopExchangesResult> getTopExchangesByPair(@Query("fsym") String fromSymbol,
                                                     @Query("tsym") String toSymbol,
                                                     @Query("limit") int limit);
}

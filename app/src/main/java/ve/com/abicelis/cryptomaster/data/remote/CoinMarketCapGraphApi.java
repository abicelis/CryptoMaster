package ve.com.abicelis.cryptomaster.data.remote;

import io.reactivex.Maybe;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcap.GlobalResult;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcap.TickerResult;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcapgraph.TotalMarketCapResult;

/**
 * Created by abicelis on 24/5/2018.
 */
public interface CoinMarketCapGraphApi {

    @GET("global/marketcap-total/{time_start}/{time_end}/")
    Maybe<TotalMarketCapResult> getTotalMaketCap(@Path("time_start") long timestart,   //Start timestamp (in millis)
                                                 @Path("time_end") long timeEnd);    //End timestamp (in millis)

}

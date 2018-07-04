package ve.com.abicelis.cryptomaster.data.remote;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcapgraph.MarketCapAndVolumeResult;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcapgraph.MarketCapPriceAndVolumeResult;

/**
 * Created by abicelis on 24/5/2018.
 */
public interface CoinMarketCapGraphApi {

    //https://graphs2.coinmarketcap.com/global/dominance/1520873220000/1520874220000/

    @GET("global/marketcap-total/{time_start}/{time_end}/")
    Single<MarketCapAndVolumeResult> getTotalMarketCapAndVolumeGraphData(@Path("time_start") long timestart,        //Start timestamp (in millis)
                                                                         @Path("time_end") long timeEnd);           //End timestamp (in millis)


    @GET("global/marketcap-altcoin/{time_start}/{time_end}/")
    Single<MarketCapAndVolumeResult> getAltcoinMarketCapAndVolumeGraphData(@Path("time_start") long timestart,      //Start timestamp (in millis)
                                                                           @Path("time_end") long timeEnd);           //End timestamp (in millis)



    @GET("currencies/{slug}/{time_start}/{time_end}/")
    Single<MarketCapPriceAndVolumeResult> getCurrencyMarketCapPriceAndVolumeGraphData(@Path("slug") String slug,         //Slug, name of coin
                                                                                      @Path("time_start") long timestart,      //Start timestamp (in millis)
                                                                                      @Path("time_end") long timeEnd);         //End timestamp (in millis)

}

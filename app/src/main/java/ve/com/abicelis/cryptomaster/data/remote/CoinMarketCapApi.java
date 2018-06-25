package ve.com.abicelis.cryptomaster.data.remote;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcap.GlobalResult;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcap.TickerResult;

/**
 * Created by abicelis on 24/5/2018.
 */
public interface CoinMarketCapApi {
    //https://api.coinmarketcap.com/v2/ticker/?sort=rank&structure=array
    @GET("ticker/?sort=rank&structure=array")
    Single<TickerResult> getTicker (@Query("start") int start,           //Default = 1
                                    @Query("limit") int limit,           //Default = 100, Max = 100
                                    //@Query("sort") String sort,          //Default = rank. Options = id, rank, volume_24h, and percent_change_24h. Note: It is strongly recommended to use id to sort if paginating through all available results since id is the only sort option guaranteed to return in a consistent order.
                                    @Query("convert") String currency ); //Return pricing info in terms of another currency. Valid fiat currency values are: "AUD", "BRL", "CAD", "CHF", "CLP", "CNY", "CZK", "DKK", "EUR", "GBP", "HKD", "HUF", "IDR", "ILS", "INR", "JPY", "KRW", "MXN", "MYR", "NOK", "NZD", "PHP", "PKR", "PLN", "RUB", "SEK", "SGD", "THB", "TRY", "TWD", "ZAR". Valid crypto currency values are: "BTC", "ETH" "XRP", "LTC", and "BCH"

    @GET("global")
    Single<GlobalResult> getGlobal (@Query("convert") String convert);   //Return pricing info in terms of another currency. Valid fiat currency values are: "AUD", "BRL", "CAD", "CHF", "CLP", "CNY", "CZK", "DKK", "EUR", "GBP", "HKD", "HUF", "IDR", "ILS", "INR", "JPY", "KRW", "MXN", "MYR", "NOK", "NZD", "PHP", "PKR", "PLN", "RUB", "SEK", "SGD", "THB", "TRY", "TWD", "ZAR". Valid crypto currency values are: "BTC", "ETH" "XRP", "LTC", and "BCH"

    @GET("ticker/{id}/?structure=array")
    Single<TickerResult> getTickerSingleCurrency (@Path("id") long id,
                                                  @Query("convert") String currency ); //Return pricing info in terms of another currency. Valid fiat currency values are: "AUD", "BRL", "CAD", "CHF", "CLP", "CNY", "CZK", "DKK", "EUR", "GBP", "HKD", "HUF", "IDR", "ILS", "INR", "JPY", "KRW", "MXN", "MYR", "NOK", "NZD", "PHP", "PKR", "PLN", "RUB", "SEK", "SGD", "THB", "TRY", "TWD", "ZAR". Valid crypto currency values are: "BTC", "ETH" "XRP", "LTC", and "BCH"

}

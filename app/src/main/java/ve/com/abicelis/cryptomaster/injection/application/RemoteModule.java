package ve.com.abicelis.cryptomaster.injection.application;


import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import ve.com.abicelis.cryptomaster.application.Constants;
import ve.com.abicelis.cryptomaster.data.remote.CoinMarketCapApi;
import ve.com.abicelis.cryptomaster.data.remote.CryptoCompareApi;

/**
 * Created by abicelis on 25/5/2018.
 */

@Module
@ApplicationScope
public class RemoteModule {

    private static final String CONVERTER_SCALAR = "CONVERTER_SCALAR";
    private static final String CONVERTER_GSON = "CONVERTER_GSON";

    private static final String COINMARKETCAP_BASE_URL = "COINMARKETCAP_BASE_URL";
    private static final String COINMARKETCAP_RETROFIT = "COINMARKETCAP_RETROFIT";

    private static final String CRYPTOCOMPARE_BASE_URL = "CRYPTOCOMPARE_BASE_URL";
    private static final String CRYPTOCOMPARE_RETROFIT = "CRYPTOCOMPARE_RETROFIT";



    /**
     * Common
     */
    @Provides
    @Named(CONVERTER_SCALAR)    //Scalars Converter so Retrofit can return Strings
    Converter.Factory provideScalarConverter() {return ScalarsConverterFactory.create();}

    @Provides
    @Named(CONVERTER_GSON)
    Converter.Factory provideGsonConverter() {
        return GsonConverterFactory.create();
    }

    @Provides
    RxJava2CallAdapterFactory provideRxJavaFactory() {
        return RxJava2CallAdapterFactory.create();
    }





    /**
     * INJECTION GRAPH FOR COIN MARKET CAP API
     */
    @Provides
    @Named(COINMARKETCAP_BASE_URL)
    String provideFlightawareBaseUrlString() {return Constants.COINMARKETCAP_BASE_URL;}

    @Provides
    @Named(COINMARKETCAP_RETROFIT)
    Retrofit provideCoinMarketCapRetrofit(@Named(CONVERTER_GSON) Converter.Factory converter,
                                        RxJava2CallAdapterFactory factory,
                                        @Named(COINMARKETCAP_BASE_URL) String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(converter)
                .addCallAdapterFactory(factory)
                .build();
    }

    @Provides
    CoinMarketCapApi provideCoinMarkerCapApi(@Named(COINMARKETCAP_RETROFIT) Retrofit retrofit) {
        return retrofit.create(CoinMarketCapApi.class);
    }





    /**
     * INJECTION GRAPH FOR CRYPTO COMPARE API
     */
    @Provides
    @Named(CRYPTOCOMPARE_BASE_URL)
    String provideOpenflgihtsBaseUrlString() {return Constants.CRYPTOCOMPARE_BASE_URL;}

    @Provides
    @Named(CRYPTOCOMPARE_RETROFIT)
    Retrofit provideOpenflightsRetrofit(@Named(CONVERTER_GSON) Converter.Factory converter,
                                        RxJava2CallAdapterFactory factory,
                                        @Named(CRYPTOCOMPARE_BASE_URL) String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(converter)
                .addCallAdapterFactory(factory)
                .build();
    }

    @Provides
    CryptoCompareApi providesCryptoCompareApi(@Named(CRYPTOCOMPARE_RETROFIT) Retrofit retrofit) {
        return retrofit.create(CryptoCompareApi.class);
    }






}

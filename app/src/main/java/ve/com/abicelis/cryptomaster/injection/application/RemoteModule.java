package ve.com.abicelis.cryptomaster.injection.application;


import java.util.concurrent.TimeUnit;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import ve.com.abicelis.cryptomaster.application.Constants;
import ve.com.abicelis.cryptomaster.data.remote.CoinMarketCapApi;
import ve.com.abicelis.cryptomaster.data.remote.CoinMarketCapGraphApi;
import ve.com.abicelis.cryptomaster.data.remote.CoinMarketCapS2Api;
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

    private static final String COINMARKETCAP_GRAPHS_BASE_URL = "COINMARKETCAP_GRAPHS_BASE_URL";
    private static final String COINMARKETCAP_GRAPHS_RETROFIT = "COINMARKETCAP_GRAPHS_RETROFIT";

    private static final String COINMARKETCAP_S2_BASE_URL = "COINMARKETCAP_S2_BASE_URL";
    private static final String COINMARKETCAP_S2_RETROFIT = "COINMARKETCAP_S2_RETROFIT";

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

    @Provides
    OkHttpClient provideOkHttpClientWithCustomTimeouts() {
        return new OkHttpClient.Builder()
                .readTimeout(Constants.MISC_OK_HTTP_READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .connectTimeout(Constants.MISC_OK_HTTP_CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .build();
    }



    /**
     * INJECTION GRAPH FOR COIN MARKET CAP API
     */
    @Provides
    @Named(COINMARKETCAP_BASE_URL)
    String provideCMCBaseUrl() {return Constants.COINMARKETCAP_BASE_URL;}

    @Provides
    @Named(COINMARKETCAP_RETROFIT)
    Retrofit provideCoinMarketCapRetrofit(@Named(CONVERTER_GSON) Converter.Factory converter,
                                          RxJava2CallAdapterFactory factory,
                                          @Named(COINMARKETCAP_BASE_URL) String baseUrl,
                                          OkHttpClient okHttpClient) {



        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(converter)
                .addCallAdapterFactory(factory)
                .client(okHttpClient)
                .build();
    }

    @Provides
    CoinMarketCapApi provideCoinMarkerCapApi(@Named(COINMARKETCAP_RETROFIT) Retrofit retrofit) {
        return retrofit.create(CoinMarketCapApi.class);
    }



    /**
     * INJECTION GRAPH FOR COIN MARKET CAP GRAPH API
     */
    @Provides
    @Named(COINMARKETCAP_GRAPHS_BASE_URL)
    String provideCMCGBaseUrl() {return Constants.COINMARKETCAP_GRAPHS_BASE_URL;}

    @Provides
    @Named(COINMARKETCAP_GRAPHS_RETROFIT)
    Retrofit provideCMCGRetrofit(@Named(CONVERTER_GSON) Converter.Factory converter,
                                 RxJava2CallAdapterFactory factory,
                                 @Named(COINMARKETCAP_GRAPHS_BASE_URL) String baseUrl,
                                 OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(converter)
                .addCallAdapterFactory(factory)
                .client(okHttpClient)
                .build();
    }

    @Provides
    CoinMarketCapGraphApi provideCMCGApi(@Named(COINMARKETCAP_GRAPHS_RETROFIT) Retrofit retrofit) {
        return retrofit.create(CoinMarketCapGraphApi.class);
    }


    /**
     * INJECTION GRAPH FOR COIN MARKET CAP S2 API
     */
    @Provides
    @Named(COINMARKETCAP_S2_BASE_URL)
    String provideCMCS2BaseUrl() {return Constants.COINMARKETCAP_S2_BASE_URL;}

    @Provides
    @Named(COINMARKETCAP_S2_RETROFIT)
    Retrofit provideCMCS2Retrofit(@Named(CONVERTER_GSON) Converter.Factory converter,
                                  RxJava2CallAdapterFactory factory,
                                  @Named(COINMARKETCAP_S2_BASE_URL) String baseUrl,
                                  OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(converter)
                .addCallAdapterFactory(factory)
                .client(okHttpClient)
                .build();
    }

    @Provides
    CoinMarketCapS2Api provideCMCS2Api(@Named(COINMARKETCAP_S2_RETROFIT) Retrofit retrofit) {
        return retrofit.create(CoinMarketCapS2Api.class);
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
                                        @Named(CRYPTOCOMPARE_BASE_URL) String baseUrl,
                                        OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(converter)
                .addCallAdapterFactory(factory)
                .client(okHttpClient)
                .build();
    }

    @Provides
    CryptoCompareApi providesCryptoCompareApi(@Named(CRYPTOCOMPARE_RETROFIT) Retrofit retrofit) {
        return retrofit.create(CryptoCompareApi.class);
    }






}

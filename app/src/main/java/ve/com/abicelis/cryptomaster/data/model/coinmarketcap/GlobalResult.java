package ve.com.abicelis.cryptomaster.data.model.coinmarketcap;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abicelis on 24/5/2018.
 */
public class GlobalResult {

    private GlobalData data;

    public GlobalData getData() { return data; }

    public class GlobalData {

        @SerializedName("active_cryptocurrencies")
        private int activeCryptocurrencies;

        @SerializedName("active_markets")
        private int activeMarkets;

        @SerializedName("bitcoin_percentage_of_market_cap")
        private float bitcoinPercentageOfMarketCap;

        @SerializedName("last_updated")
        private long lastUpdated;

        @SerializedName("quotes")
        QuoteData quoteData;

        public int getActiveCryptocurrencies() { return activeCryptocurrencies; }
        public int getActiveMarkets() { return activeMarkets; }
        public float getBitcoinPercentageOfMarketCap() { return bitcoinPercentageOfMarketCap; }
        public long getLastUpdated() { return lastUpdated; }
        public QuoteData getQuoteData() { return quoteData; }



        public class QuoteData {

            @SerializedName("USD")
            QuoteDataUSD quoteDataUSD;

            public QuoteDataUSD getQuoteDataUSD() { return quoteDataUSD; }



            public class QuoteDataUSD {

                @SerializedName("total_market_cap")
                private double totalMarketCap;
                @SerializedName("total_volume_24h")
                private double totalVolume24h;

                public double getTotalMarketCap() { return totalMarketCap; }
                public double getTotalVolume24h() { return totalVolume24h; }
            }
        }

    }
}

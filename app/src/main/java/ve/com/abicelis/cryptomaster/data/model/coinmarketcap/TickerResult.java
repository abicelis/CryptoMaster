package ve.com.abicelis.cryptomaster.data.model.coinmarketcap;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * Created by abicelis on 24/5/2018.
 */
public class TickerResult {

    private List<TickerData> data;
    private MetaData metadata;

    public List<TickerData> getData() { return data; }
    public MetaData getMetadata() { return metadata; }

    public class TickerData {

        private int id;
        private String name;
        private String symbol;

        @SerializedName("website_slug")
        private String websiteSlug;

        private int rank;

        @SerializedName("circulating_supply")
        private long circulatingSupply;

        @SerializedName("total_supply")
        private long totalSupply;

        @SerializedName("max_supply")
        private long maxSupply;

        private Map<String, QuoteData> quotes;

        @SerializedName("last_updated")
        private long lastUpdated;

        public int getId() {
            return id;
        }
        public String getName() {
            return name;
        }
        public String getSymbol() {
            return symbol;
        }
        public String getWebsiteSlug() {
            return websiteSlug;
        }
        public int getRank() {
            return rank;
        }
        public long getCirculatingSupply() {
            return circulatingSupply;
        }
        public long getTotalSupply() {
            return totalSupply;
        }
        public long getMaxSupply() {
            return maxSupply;
        }
        public Map<String, QuoteData> getQuotes() { return quotes; }
        public long getLastUpdated() {
            return lastUpdated;
        }

        public class QuoteData {
            private double price;

            @SerializedName("volume_24h")
            private double volume24h;

            @SerializedName("market_cap")
            private double marketCap;

            @SerializedName("percent_change_1h")
            private double percentChange1h;

            @SerializedName("percent_change_24h")
            private double percentChange24h;

            @SerializedName("percent_change_7d")
            private double percentChange7d;


            public double getPrice() { return price; }
            public double getVolume24h() { return volume24h; }
            public double getMarketCap() { return marketCap; }
            public double getPercentChange1h() { return percentChange1h; }
            public double getPercentChange24h() { return percentChange24h; }
            public double getPercentChange7d() { return percentChange7d; }
        }

    }

    public class MetaData {

        private long timestamp;
        @SerializedName("num_cryptocurrencies")
        private int numCryptocurrencies;
        private String error;

        public long getTimestamp() { return timestamp; }
        public int getNumCryptocurrencies() { return numCryptocurrencies; }
        public String getError() { return error; }
    }
}

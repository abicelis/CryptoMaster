package ve.com.abicelis.cryptomaster.model.coinmarketcap;

import java.util.List;

/**
 * Created by abicelis on 24/5/2018.
 */
public class TickerResult {

    private List<TickerData> data;

    public List<TickerData> getData() { return data; }

    public class TickerData {

        private int id;
        private String name;
        private String symbol;
        private String website_slug;
        private int rank;
        private long circulating_supply;
        private long total_supply;
        private long max_supply;
        private long last_updated;

        public int getId() {
            return id;
        }
        public String getName() {
            return name;
        }
        public String getSymbol() {
            return symbol;
        }
        public String getWebsite_slug() {
            return website_slug;
        }
        public int getRank() {
            return rank;
        }
        public long getCirculating_supply() {
            return circulating_supply;
        }
        public long getTotal_supply() {
            return total_supply;
        }
        public long getMax_supply() {
            return max_supply;
        }
        public long getLast_updated() {
            return last_updated;
        }
    }
}

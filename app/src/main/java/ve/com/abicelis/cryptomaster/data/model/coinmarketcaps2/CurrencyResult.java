package ve.com.abicelis.cryptomaster.data.model.coinmarketcaps2;

import java.util.List;

/**
 * Created by abicelis on 14/6/2018.
 */
public class CurrencyResult {

        private String name;
        private String symbol;
        private int rank;
        private String slug;
        private List<String> tokens;
        private int id;

        public String getName() { return name; }
        public String getSymbol() { return symbol; }
        public int getRank() { return rank; }
        public String getSlug() { return slug; }
        public List<String> getTokens() { return tokens; }
        public int getId() { return id; }
}

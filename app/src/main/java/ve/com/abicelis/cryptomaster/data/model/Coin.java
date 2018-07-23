package ve.com.abicelis.cryptomaster.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by abicelis on 16/6/2018.
 */

@Entity(tableName = "coin",
        indices = {@Index("name"), @Index("symbol")})
public class Coin implements Comparable<Coin> {

    @PrimaryKey
    @ColumnInfo(name = "coin_id")
    private long id;                                //CoinMarketCap ID
    private String name;
    private String symbol;
    @ColumnInfo(name = "website_slug")
    private String websiteSlug;
    private int rank;
    @ColumnInfo(name = "circulating_supply")
    private long circulatingSupply;
    @ColumnInfo(name = "total_supply")
    private long totalSupply;
    @ColumnInfo(name = "max_supply")
    private long maxSupply;
    @ColumnInfo(name = "last_updated")
    private long lastUpdated;

    //USD Quote
    private double quoteUsdPrice;
    @ColumnInfo(name = "quote_usd_volume_24h")
    private double quoteUsdVolume;
    @ColumnInfo(name = "quote_usd_market_cap")
    private double quoteUsdMarketCap;

    //Default currency Quote
    private double quoteDefaultPrice;
    @ColumnInfo(name = "quote_default_volume_24h")
    private double quoteDefaultVolume;
    @ColumnInfo(name = "quote_default_market_cap")
    private double quoteDefaultMarketCap;


    @ColumnInfo(name = "percent_change_1h")
    private double percentChange1h;
    @ColumnInfo(name = "percent_change_24h")
    private double percentChange24h;
    @ColumnInfo(name = "percent_change_7d")
    private double percentChange7d;

    @Ignore
    public CoinsListViewHolderState coinsListViewHolderState;

    @Ignore
    private double quoteBtcPrice;


    public Coin (long id, String name, String symbol, String websiteSlug, int rank, long circulatingSupply, long totalSupply, long maxSupply, long lastUpdated,
                 double quoteUsdPrice, double quoteUsdVolume, double quoteUsdMarketCap, double quoteDefaultPrice, double quoteDefaultVolume, double quoteDefaultMarketCap,
                 double percentChange1h, double percentChange24h, double percentChange7d) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.websiteSlug = websiteSlug;
        this.rank = rank;
        this.circulatingSupply = circulatingSupply;
        this.totalSupply = totalSupply;
        this.maxSupply = maxSupply;
        this.lastUpdated = lastUpdated;

        this.quoteUsdPrice = quoteUsdPrice;
        this.quoteUsdVolume = quoteUsdVolume;
        this.quoteUsdMarketCap = quoteUsdMarketCap;

        this.quoteDefaultPrice = quoteDefaultPrice;
        this.quoteDefaultVolume = quoteDefaultVolume;
        this.quoteDefaultMarketCap = quoteDefaultMarketCap;

        this.percentChange1h = percentChange1h;
        this.percentChange24h = percentChange24h;
        this.percentChange7d = percentChange7d;

        coinsListViewHolderState  = CoinsListViewHolderState.NORMAL;
    }


    public long getId() { return id; }
    public String getName() { return name; }
    public String getSymbol() { return symbol; }
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
    public long getLastUpdated() { return lastUpdated; }
    public double getQuoteUsdPrice() { return quoteUsdPrice; }
    public double getQuoteUsdVolume() { return quoteUsdVolume; }
    public double getQuoteUsdMarketCap() { return quoteUsdMarketCap; }
    public double getQuoteDefaultPrice() { return quoteDefaultPrice; }
    public double getQuoteDefaultVolume() { return quoteDefaultVolume; }
    public double getQuoteDefaultMarketCap() { return quoteDefaultMarketCap; }
    public double getPercentChange1h() { return percentChange1h; }
    public double getPercentChange24h() { return percentChange24h; }
    public double getPercentChange7d() { return percentChange7d; }
    public double getQuoteBtcPrice() { return quoteBtcPrice; }

    public void setId(long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    public void setWebsiteSlug(String websiteSlug) { this.websiteSlug = websiteSlug; }
    public void setRank(int rank) { this.rank = rank; }
    public void setCirculatingSupply(long circulatingSupply) { this.circulatingSupply = circulatingSupply; }
    public void setTotalSupply(long totalSupply) { this.totalSupply = totalSupply; }
    public void setMaxSupply(long maxSupply) { this.maxSupply = maxSupply; }
    public void setLastUpdated(long lastUpdated) { this.lastUpdated = lastUpdated; }
    public void setQuoteUsdPrice(double quoteUsdPrice) { this.quoteUsdPrice = quoteUsdPrice; }
    public void setQuoteUsdVolume(double quoteUsdVolume) { this.quoteUsdVolume = quoteUsdVolume; }
    public void setQuoteUsdMarketCap(double quoteUsdMarketCap) { this.quoteUsdMarketCap = quoteUsdMarketCap; }
    public void setQuoteDefaultPrice(double quoteDefaultPrice) { this.quoteDefaultPrice = quoteDefaultPrice; }
    public void setQuoteDefaultVolume(double quoteDefaultVolume) { this.quoteDefaultVolume = quoteDefaultVolume; }
    public void setQuoteDefaultMarketCap(double quoteDefaultMarketCap) { this.quoteDefaultMarketCap = quoteDefaultMarketCap; }
    public void setPercentChange1h(double percentChange1h) { this.percentChange1h = percentChange1h; }
    public void setPercentChange24h(double percentChange24h) { this.percentChange24h = percentChange24h; }
    public void setPercentChange7d(double percentChange7d) { this.percentChange7d = percentChange7d; }
    public void setQuoteBtcPrice(double quoteBtcPrice) { this.quoteBtcPrice = quoteBtcPrice; }

    @Override
    public int compareTo(@NonNull Coin o) {
        return Double.compare(o.quoteUsdMarketCap, this.quoteUsdMarketCap);
    }
}

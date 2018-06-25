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
    @ColumnInfo(name = "quote_currency_symbol")
    private String quoteCurrencySymbol;             //E.g: USD
    private double price;
    @ColumnInfo(name = "volume_24h")
    private double volume24h;
    @ColumnInfo(name = "market_cap")
    private double marketCap;
    @ColumnInfo(name = "percent_change_1h")
    private double percentChange1h;
    @ColumnInfo(name = "percent_change_24h")
    private double percentChange24h;
    @ColumnInfo(name = "percent_change_7d")
    private double percentChange7d;

    @Ignore
    public CoinsListViewHolderState coinsListViewHolderState;


    public Coin (long id, String name, String symbol, String websiteSlug, int rank, long circulatingSupply, long totalSupply, long maxSupply, long lastUpdated, String quoteCurrencySymbol, double price, double volume24h, double marketCap, double percentChange1h, double percentChange24h, double percentChange7d) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.websiteSlug = websiteSlug;
        this.rank = rank;
        this.circulatingSupply = circulatingSupply;
        this.totalSupply = totalSupply;
        this.maxSupply = maxSupply;
        this.lastUpdated = lastUpdated;
        this.quoteCurrencySymbol = quoteCurrencySymbol;
        this.price = price;
        this.volume24h = volume24h;
        this.marketCap = marketCap;
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
    public String getQuoteCurrencySymbol() { return quoteCurrencySymbol; }
    public double getPrice() { return price; }
    public double getVolume24h() { return volume24h; }
    public double getMarketCap() { return marketCap; }
    public double getPercentChange1h() { return percentChange1h; }
    public double getPercentChange24h() { return percentChange24h; }
    public double getPercentChange7d() { return percentChange7d; }


    public void setId(long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    public void setWebsiteSlug(String websiteSlug) { this.websiteSlug = websiteSlug; }
    public void setRank(int rank) { this.rank = rank; }
    public void setCirculatingSupply(long circulatingSupply) { this.circulatingSupply = circulatingSupply; }
    public void setTotalSupply(long totalSupply) { this.totalSupply = totalSupply; }
    public void setMaxSupply(long maxSupply) { this.maxSupply = maxSupply; }
    public void setLastUpdated(long lastUpdated) { this.lastUpdated = lastUpdated; }
    public void setQuoteCurrencySymbol(String quoteCurrencySymbol) { this.quoteCurrencySymbol = quoteCurrencySymbol; }
    public void setPrice(double price) { this.price = price; }
    public void setVolume24h(double volume24h) { this.volume24h = volume24h; }
    public void setMarketCap(double marketCap) { this.marketCap = marketCap; }
    public void setPercentChange1h(double percentChange1h) { this.percentChange1h = percentChange1h; }
    public void setPercentChange24h(double percentChange24h) { this.percentChange24h = percentChange24h; }
    public void setPercentChange7d(double percentChange7d) { this.percentChange7d = percentChange7d; }

    @Override
    public int compareTo(@NonNull Coin o) {
        return Double.compare(o.marketCap, this.marketCap);
    }
}

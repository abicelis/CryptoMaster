package ve.com.abicelis.cryptomaster.data.model;

import android.support.annotation.NonNull;

/**
 * Created by abicelis on 16/6/2018.
 */
public class Coin implements Comparable<Coin> {

    private int id;
    private String name;
    private String symbol;
    private double price;
    private double volume24h;
    private double marketCap;
    private double percentChange1h;
    private double percentChange24h;
    private double percentChange7d;


    public Coin (int id, String name, String symbol, double price, double volume24h, double marketCap, double percentChange1h, double percentChange24h, double percentChange7d) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.price = price;
        this.volume24h = volume24h;
        this.marketCap = marketCap;
        this.percentChange1h = percentChange1h;
        this.percentChange24h = percentChange24h;
        this.percentChange7d = percentChange7d;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getSymbol() { return symbol; }
    public double getPrice() { return price; }
    public double getVolume24h() { return volume24h; }
    public double getMarketCap() { return marketCap; }
    public double getPercentChange1h() { return percentChange1h; }
    public double getPercentChange24h() { return percentChange24h; }
    public double getPercentChange7d() { return percentChange7d; }

    @Override
    public int compareTo(@NonNull Coin o) {
        return Double.compare(o.marketCap, this.marketCap);
    }
}

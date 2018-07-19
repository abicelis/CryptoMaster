package ve.com.abicelis.cryptomaster.data.model.coinmarketcapgraph;

import com.github.mikephil.charting.data.Entry;

import java.util.List;

import ve.com.abicelis.cryptomaster.data.model.ChartTimeSpan;

/**
 * Created by abicelis on 18/7/2018.
 */
public class MarketCapPriceAndVolumeChartData {

    private List<Entry> marketCapEntries;
    private long lastMarketCap;
    private List<Entry> priceUsdEntries;
    private double lastPriceUsd;
    private List<Entry> priceBtcEntries;
    private double lastPriceBtc;
    private List<Entry> volumeEntries;
    private long lastVolume;
    private List<Long> timestamps;
    private ChartTimeSpan chartTimeSpan;

    public MarketCapPriceAndVolumeChartData(List<Entry> marketCapEntries, long lastMarketCap, List<Entry> priceUsdEntries, double lastPriceUsd,
                                            List<Entry> priceBtcEntries, double lastPriceBtc, List<Entry> volumeEntries, long lastVolume,
                                            List<Long> timestamps, ChartTimeSpan chartTimeSpan) {
        this.marketCapEntries = marketCapEntries;
        this.lastMarketCap = lastMarketCap;
        this.priceUsdEntries = priceUsdEntries;
        this.lastPriceUsd = lastPriceUsd;
        this.priceBtcEntries = priceBtcEntries;
        this.lastPriceBtc = lastPriceBtc;
        this.volumeEntries = volumeEntries;
        this.lastVolume = lastVolume;
        this.timestamps = timestamps;
        this.chartTimeSpan = chartTimeSpan;
    }

    public List<Entry> getMarketCapEntries() { return marketCapEntries; }
    public long getLastMarketCap() { return lastMarketCap; }
    public List<Entry> getPriceUsdEntries() { return priceUsdEntries; }
    public double getLastPriceUsd() { return lastPriceUsd; }
    public List<Entry> getPriceBtcEntries() { return priceBtcEntries; }
    public double getLastPriceBtc() { return lastPriceBtc; }
    public List<Entry> getVolumeEntries() { return volumeEntries; }
    public long getLastVolume() { return lastVolume; }
    public List<Long> getTimestamps() { return timestamps; }
    public ChartTimeSpan getChartTimeSpan() { return chartTimeSpan; }

}

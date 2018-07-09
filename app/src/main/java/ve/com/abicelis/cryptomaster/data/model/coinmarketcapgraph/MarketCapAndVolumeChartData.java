package ve.com.abicelis.cryptomaster.data.model.coinmarketcapgraph;

import com.github.mikephil.charting.data.Entry;

import java.util.List;

import ve.com.abicelis.cryptomaster.data.model.ChartTimeSpan;

/**
 * Created by abicelis on 3/7/2018.
 */
public class MarketCapAndVolumeChartData {

    private List<Entry> marketCapEntries;
    private long lastMarketCap;
    private List<Entry> volumeEntries;
    private long lastVolume;
    private List<Long> timestamps;
    private ChartTimeSpan chartTimeSpan;

    public MarketCapAndVolumeChartData(List<Entry> marketCapEntries, long lastMarketCap, List<Entry> volumeEntries, long lastVolume, List<Long> timestamps, ChartTimeSpan chartTimeSpan) {
        this.marketCapEntries = marketCapEntries;
        this.lastMarketCap = lastMarketCap;
        this.volumeEntries = volumeEntries;
        this.lastVolume = lastVolume;
        this.timestamps = timestamps;
        this.chartTimeSpan = chartTimeSpan;
    }

    public List<Entry> getMarketCapEntries() { return marketCapEntries; }
    public void setMarketCapEntries(List<Entry> marketCapEntries) { this.marketCapEntries = marketCapEntries; }

    public long getLastMarketCap() { return lastMarketCap; }
    public void setLastMarketCap(long lastMarketCap) { this.lastMarketCap = lastMarketCap; }

    public List<Entry> getVolumeEntries() { return volumeEntries; }
    public void setVolumeEntries(List<Entry> volumeEntries) { this.volumeEntries = volumeEntries; }

    public long getLastVolume() { return lastVolume; }
    public void setLastVolume(long lastVolume) { this.lastVolume = lastVolume; }

    public List<Long> getTimestamps() { return timestamps; }
    public void setTimestamps(List<Long> timestamps) { this.timestamps = timestamps; }

    public ChartTimeSpan getChartTimeSpan() { return chartTimeSpan; }
    public void setChartTimeSpan(ChartTimeSpan chartTimeSpan) { this.chartTimeSpan = chartTimeSpan; }

}

package ve.com.abicelis.cryptomaster.data.model.coinmarketcapgraph;

import com.github.mikephil.charting.data.Entry;

import java.math.BigDecimal;
import java.util.List;

import ve.com.abicelis.cryptomaster.data.model.ChartTimespan;

/**
 * Created by abicelis on 3/7/2018.
 */
public class MarketCapAndVolumeChartData {

    private List<Entry> marketCapEntries;
    private long lastMarketCap;
    private List<Entry> volumeEntries;
    private long lastVolume;
    private List<Long> timestamps;
    private ChartTimespan chartTimespan;

    public MarketCapAndVolumeChartData(List<Entry> marketCapEntries, long lastMarketCap, List<Entry> volumeEntries, long lastVolume, List<Long> timestamps, ChartTimespan chartTimespan) {
        this.marketCapEntries = marketCapEntries;
        this.lastMarketCap = lastMarketCap;
        this.volumeEntries = volumeEntries;
        this.lastVolume = lastVolume;
        this.timestamps = timestamps;
        this.chartTimespan = chartTimespan;
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

    public ChartTimespan getChartTimespan() { return chartTimespan; }
    public void setChartTimespan(ChartTimespan chartTimespan) { this.chartTimespan = chartTimespan; }

}

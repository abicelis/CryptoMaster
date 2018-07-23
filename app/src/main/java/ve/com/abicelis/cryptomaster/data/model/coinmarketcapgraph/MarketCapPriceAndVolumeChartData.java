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
    private List<Entry> priceDefaultCurrencyEntries;
    private double lastPriceDefaultCurrency;
    private List<Entry> volumeEntries;
    private long lastVolume;
    private List<Long> timestamps;
    private ChartTimeSpan chartTimeSpan;

    //Extra chart data
    private double priceBtcMin;
    private double priceBtcMax;
    private double priceBtcVariation;
    private double percentageBtcVariation;

    private double priceDefaultCurrencyMin;
    private double priceDefaultCurrencyMax;
    private double priceDefaultCurrencyVariation;
    private double percentageDefaultCurrencyVariation;


    public MarketCapPriceAndVolumeChartData(List<Entry> marketCapEntries, long lastMarketCap, List<Entry> priceUsdEntries, double lastPriceUsd,
                                            List<Entry> priceDefaultCurrencyEntries, double lastPriceDefaultCurrency, List<Entry> volumeEntries, long lastVolume,
                                            List<Long> timestamps, ChartTimeSpan chartTimeSpan, double priceBtcMin, double priceBtcMax, double priceBtcVariation, double percentageBtcVariation,
                                            double priceDefaultCurrencyMin, double priceDefaultCurrencyMax, double priceDefaultCurrencyVariation, double percentageDefaultCurrencyVariation) {
        this.marketCapEntries = marketCapEntries;
        this.lastMarketCap = lastMarketCap;
        this.priceUsdEntries = priceUsdEntries;
        this.lastPriceUsd = lastPriceUsd;
        this.priceDefaultCurrencyEntries = priceDefaultCurrencyEntries;
        this.lastPriceDefaultCurrency = lastPriceDefaultCurrency;
        this.volumeEntries = volumeEntries;
        this.lastVolume = lastVolume;
        this.timestamps = timestamps;
        this.chartTimeSpan = chartTimeSpan;

        this.priceBtcMin = priceBtcMin;
        this.priceBtcMax = priceBtcMax;
        this.priceBtcVariation = priceBtcVariation;
        this.percentageBtcVariation = percentageBtcVariation;

        this.priceDefaultCurrencyMin = priceDefaultCurrencyMin;
        this.priceDefaultCurrencyMax = priceDefaultCurrencyMax;
        this.priceDefaultCurrencyVariation = priceDefaultCurrencyVariation;
        this.percentageDefaultCurrencyVariation = percentageDefaultCurrencyVariation;
    }

    public List<Entry> getMarketCapEntries() { return marketCapEntries; }
    public long getLastMarketCap() { return lastMarketCap; }
    public List<Entry> getPriceUsdEntries() { return priceUsdEntries; }
    public double getLastPriceUsd() { return lastPriceUsd; }
    public List<Entry> getPriceDefaultCurrencyEntries() { return priceDefaultCurrencyEntries; }
    public double getLastPriceDefaultCurrency() { return lastPriceDefaultCurrency; }
    public List<Entry> getVolumeEntries() { return volumeEntries; }
    public long getLastVolume() { return lastVolume; }
    public List<Long> getTimestamps() { return timestamps; }
    public ChartTimeSpan getChartTimeSpan() { return chartTimeSpan; }

    public double getPriceBtcMin() { return priceBtcMin; }
    public double getPriceBtcMax() { return priceBtcMax; }
    public double getPriceBtcVariation() { return priceBtcVariation; }
    public double getPercentageBtcVariation() { return percentageBtcVariation; }

    public double getPriceDefaultCurrencyMin() { return priceDefaultCurrencyMin; }
    public double getPriceDefaultCurrencyMax() { return priceDefaultCurrencyMax; }
    public double getPriceDefaultCurrencyVariation() { return priceDefaultCurrencyVariation; }
    public double getPercentageDefaultCurrencyVariation() { return percentageDefaultCurrencyVariation; }
}

package ve.com.abicelis.cryptomaster.data.model.coinmarketcapgraph;

import com.github.mikephil.charting.data.Entry;

import java.util.List;

import ve.com.abicelis.cryptomaster.data.model.ChartTimeSpan;

/**
 * Created by abicelis on 3/7/2018.
 */
public class DominanceChartData {

    private List<Entry> mostDominantCoinEntries;
    private float mostDominantCoinLastEntry;
    private List<Entry> lessDominantCoinEntries;
    private float lessDominantCoinLastEntry;
    private List<Entry> leastDominantCoinEntries;
    private float leastDominantCoinLastEntry;
    private List<Entry> otherCoinsEntries;
    private float otherCoinsLastEntry;
    private List<Long> timestamps;
    private ChartTimeSpan chartTimeSpan;

    public DominanceChartData(List<Entry> mostDominantCoinEntries,
                              float mostDominantCoinLastEntry,
                              List<Entry> lessDominantCoinEntries,
                              float lessDominantCoinLastEntry,
                              List<Entry> leastDominantCoinEntries,
                              float leastDominantCoinLastEntry,
                              List<Entry> otherCoinsEntries,
                              float otherCoinsLastEntry,
                              List<Long> timestamps,
                              ChartTimeSpan chartTimeSpan) {
        this.mostDominantCoinEntries = mostDominantCoinEntries;
        this.mostDominantCoinLastEntry = mostDominantCoinLastEntry;
        this.lessDominantCoinEntries = lessDominantCoinEntries;
        this.lessDominantCoinLastEntry = lessDominantCoinLastEntry;
        this.leastDominantCoinEntries = leastDominantCoinEntries;
        this.leastDominantCoinLastEntry = leastDominantCoinLastEntry;
        this.otherCoinsEntries = otherCoinsEntries;
        this.otherCoinsLastEntry = otherCoinsLastEntry;
        this.timestamps = timestamps;
        this.chartTimeSpan = chartTimeSpan;
    }


    public List<Entry> getMostDominantCoinEntries() {
        return mostDominantCoinEntries;
    }

    public void setMostDominantCoinEntries(List<Entry> mostDominantCoinEntries) {
        this.mostDominantCoinEntries = mostDominantCoinEntries;
    }

    public float getMostDominantCoinLastEntry() {
        return mostDominantCoinLastEntry;
    }

    public void setMostDominantCoinLastEntry(long mostDominantCoinLastEntry) {
        this.mostDominantCoinLastEntry = mostDominantCoinLastEntry;
    }

    public List<Entry> getLessDominantCoinEntries() {
        return lessDominantCoinEntries;
    }

    public void setLessDominantCoinEntries(List<Entry> lessDominantCoinEntries) {
        this.lessDominantCoinEntries = lessDominantCoinEntries;
    }

    public float getLessDominantCoinLastEntry() {
        return lessDominantCoinLastEntry;
    }

    public void setLessDominantCoinLastEntry(long lessDominantCoinLastEntry) {
        this.lessDominantCoinLastEntry = lessDominantCoinLastEntry;
    }

    public List<Entry> getLeastDominantCoinEntries() {
        return leastDominantCoinEntries;
    }

    public void setLeastDominantCoinEntries(List<Entry> leastDominantCoinEntries) {
        this.leastDominantCoinEntries = leastDominantCoinEntries;
    }

    public float getLeastDominantCoinLastEntry() {
        return leastDominantCoinLastEntry;
    }

    public void setLeastDominantCoinLastEntry(long leastDominantCoinLastEntry) {
        this.leastDominantCoinLastEntry = leastDominantCoinLastEntry;
    }

    public List<Entry> getOtherCoinsEntries() {
        return otherCoinsEntries;
    }

    public void setOtherCoinsEntries(List<Entry> otherCoinsEntries) {
        this.otherCoinsEntries = otherCoinsEntries;
    }

    public float getOtherCoinsLastEntry() {
        return otherCoinsLastEntry;
    }

    public void setOtherCoinsLastEntry(long otherCoinsLastEntry) {
        this.otherCoinsLastEntry = otherCoinsLastEntry;
    }

    public List<Long> getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(List<Long> timestamps) {
        this.timestamps = timestamps;
    }

    public ChartTimeSpan getChartTimeSpan() {
        return chartTimeSpan;
    }

    public void setChartTimeSpan(ChartTimeSpan chartTimeSpan) {
        this.chartTimeSpan = chartTimeSpan;
    }
}

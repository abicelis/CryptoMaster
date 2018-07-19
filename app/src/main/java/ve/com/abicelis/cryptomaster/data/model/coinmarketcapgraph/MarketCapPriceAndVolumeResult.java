package ve.com.abicelis.cryptomaster.data.model.coinmarketcapgraph;

import com.google.gson.annotations.SerializedName;

/**
 * Created by abicelis on 31/5/2018.
 */
public class MarketCapPriceAndVolumeResult {

    @SerializedName("market_cap_by_available_supply")
    private long[][] marketCapByAvailableSupply;

    @SerializedName("price_btc")
    private double[][] priceBtc;

    @SerializedName("price_usd")
    private double[][] priceUsd;

    @SerializedName("volume_usd")
    private long[][] volumeUsd;

    public long[][] getMarketCapByAvailableSupply() { return marketCapByAvailableSupply; }
    public double[][] getPriceBtc() { return priceBtc; }
    public double[][] getPriceUsd() { return priceUsd; }
    public long[][] getVolumeUsd() { return volumeUsd; }
}

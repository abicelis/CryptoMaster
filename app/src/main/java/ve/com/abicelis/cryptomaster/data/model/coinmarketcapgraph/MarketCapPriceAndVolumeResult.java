package ve.com.abicelis.cryptomaster.data.model.coinmarketcapgraph;

import com.google.gson.annotations.SerializedName;

/**
 * Created by abicelis on 31/5/2018.
 */
public class MarketCapPriceAndVolumeResult {

    @SerializedName("market_cap_by_available_supply")
    private long[][] marketCapByAvailableSupply;

    @SerializedName("price_btc")
    private long[][] priceBtc;

    @SerializedName("price_usd")
    private long[][] priceUsd;

    @SerializedName("volume_usd")
    private long[][] volumeUsd;

    public long[][] getMarketCapByAvailableSupply() { return marketCapByAvailableSupply; }
    public long[][] getPriceBtc() { return priceBtc; }
    public long[][] getPriceUsd() { return priceUsd; }
    public long[][] getVolumeUsd() { return volumeUsd; }
}

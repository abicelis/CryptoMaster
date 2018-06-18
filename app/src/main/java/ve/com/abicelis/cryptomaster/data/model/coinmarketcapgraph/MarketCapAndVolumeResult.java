package ve.com.abicelis.cryptomaster.data.model.coinmarketcapgraph;

import com.google.gson.annotations.SerializedName;

/**
 * Created by abicelis on 31/5/2018.
 */
public class MarketCapAndVolumeResult {

    @SerializedName("market_cap_by_available_supply")
    private long[][] marketCapByAvailableSupply;

    @SerializedName("volume_usd")
    private long[][] volumeUsd;

    public long[][] getMarketCapByAvailableSupply() { return marketCapByAvailableSupply; }
    public long[][] getVolumeUsd() { return volumeUsd; }
}

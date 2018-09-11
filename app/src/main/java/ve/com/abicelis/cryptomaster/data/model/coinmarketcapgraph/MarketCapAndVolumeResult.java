package ve.com.abicelis.cryptomaster.data.model.coinmarketcapgraph;

import com.google.gson.annotations.SerializedName;

/**
 * Created by abicelis on 31/5/2018.
 */
public class MarketCapAndVolumeResult {

    @SerializedName("market_cap_by_available_supply")
    private double[][] marketCapByAvailableSupply;

    @SerializedName("volume_usd")
    private double[][] volumeUsd;

    public double[][] getMarketCapByAvailableSupply() { return marketCapByAvailableSupply; }
    public double[][] getVolumeUsd() { return volumeUsd; }
}

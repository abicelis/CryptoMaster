package ve.com.abicelis.cryptomaster.data.model.coinmarketcapgraph;

import com.google.gson.annotations.SerializedName;

/**
 * Created by abicelis on 31/5/2018.
 */
public class TotalMarketCapResult {

    @SerializedName("market_cap_by_available_supply")
    private long[][] marketCapByAvailableSupply;

    public long[][] getMarketCapByAvailableSupply() {
        return marketCapByAvailableSupply;
    }

}

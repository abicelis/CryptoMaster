package ve.com.abicelis.cryptomaster.data.model.cryptocomparemin;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abicelis on 25/7/2018.
 */
public class TopExchangesResult {

    @SerializedName("Response")
    private String response;
    @SerializedName("Message")
    private String message;
    @SerializedName("Data")
    private Data data;

    public String getResponse() { return response; }
    public String getMessage() { return message; }
    public Object getData() { return data; }


    public class Data {
        @SerializedName("Exchanges")
        private List<Exchange> exchangeList;
        @SerializedName("CoinInfo")
        private CoinInfo coinInfo;

        public List<Exchange> getExchangeList() { return exchangeList; }
        public CoinInfo getCoinInfo() { return coinInfo; }
    }

    public class CoinInfo {
        @SerializedName("Name")
        private String name;
        @SerializedName("FullName")
        private String fullName;

        public String getName() { return name; }
        public String getFullName() { return fullName; }
    }

    public class Exchange {
        @SerializedName("MARKET")
        private String name;
        @SerializedName("FROMSYMBOL")
        private String fromCode;
        @SerializedName("TOSYMBOL")
        private String toCode;
        @SerializedName("PRICE")
        private double price;
        @SerializedName("VOLUME24HOUR")
        private double volume24Hour;

        public String getName() { return name; }
        public String getFromCode() { return fromCode; }
        public String getToCode() { return toCode; }
        public double getPrice() { return price; }
        public double getVolume24Hour() { return volume24Hour; }
    }
}

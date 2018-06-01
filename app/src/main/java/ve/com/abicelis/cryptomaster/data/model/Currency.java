package ve.com.abicelis.cryptomaster.data.model;

/**
 * Created by abicelis on 31/5/2018.
 */
public enum Currency {
    AUD("Australian Dollar"),
    BRL("Brazilian Real"),
    CAD("Canadian Dollar"),
    CHF("Swiss Franc"),
    CLP("Chilean Peso"),
    CNY("Chinese Yuan"),
    CZK("Czech Koruna"),
    DKK("Danish Krone"),
    EUR("Euro"),
    GBP("Pound Sterling"),
    HKD("Hong Kong Dollar"),
    HUF("Hungarian Forint"),
    IDR("Indonesian Rupiah"),
    ILS("Israeli Shekel"),
    INR("Indian Rupee"),
    JPY("Japanese Yen"),
    KRW("Korean Won"),
    MXN("Mexican Peso"),
    MYR("Malaysian Ringgit"),
    NOK("Norwegian Krone"),
    NZD("New Zealand Dollar"),
    PHP("Philippine Piso"),
    PKR("Pakistani Rupee"),
    PLN("Polish Zloty"),
    RUB("Russian Ruble"),
    SEK("Swedish Krona"),
    SGD("Singapore Dollar"),
    THB("Thai Baht"),
    TRY("Turkish lira"),
    TWD("Taiwan Dollar"),
    USD("United States Dollar"),
    ZAR("South African Rand"),
    BTC("Bitcoin"),
    ETH("Ethereum"),
    XRP("Ripple"),
    LTC("Litecoin"),
    BCH("Bitcoin Cash")
    ;

    private String friendlyName;

    Currency(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getFriendlyName() { return friendlyName; }

}

package ve.com.abicelis.cryptomaster.data.model;

/**
 * Created by abicelis on 31/5/2018.
 */
public enum Currency {
    AUD("Australia Dollar",         "$",        "AUD"),
    BRL("Brazil Real",              "R$",       "BRL"),
    CAD("Canada Dollar",            "$",        "CAD"),
    CHF("Swiss Franc",              "CHF",      "CHF"),
    CLP("Chile Peso",               "$",        "CLP"),
    CNY("China Yuan",               "¥",        "CNY"),
    CZK("Czech Koruna",             "Kč",       "CZK"),
    DKK("Denmark Krone",            "kr",       "DKK"),
    EUR("Euro",                     "€",        "EUR"),
    GBP("United Kingdom Pound",     "£",        "GBP"),
    HKD("Hong Kong Dollar",         "$",        "HKD"),
    HUF("Hungary Forint",           "Ft",       "HUF"),
    IDR("Indonesia Rupiah",         "Rp",       "IDR"),
    ILS("Israel Shekel",            "₪",        "ILS"),
    INR("India Rupee",              "INR",      "INR"),
    JPY("Japan Yen",                "¥",        "JPY"),
    KRW("Korea Won",                "₩",        "KRW"),
    MXN("Mexico Peso",              "$",        "MXN"),
    MYR("Malaysia Ringgit",         "RM",       "MYR"),
    NOK("Norway Krone",             "kr",       "NOK"),
    NZD("New Zealand Dollar",       "$",        "NZD"),
    PHP("Philippines Piso",         "₱",        "PHP"),
    PKR("Pakistan Rupee",           "₨",        "PKR"),
    PLN("Poland Zloty",             "zł",       "PLN"),
    RUB("Russia Ruble",             "\u20BD",   "RUB"),
    SEK("Sweden Krona",             "kr",       "SEK"),
    SGD("Singapore Dollar",         "$",        "SGD"),
    THB("Thai Baht",                "฿",        "THB"),
    TRY("Turkey lira",              "TRY",      "TRY"),
    TWD("Taiwan New Dollar",        "$",        "TWD"),
    USD("United States Dollar",     "$",        "USD"),
    ZAR("South Africa Rand",        "ZAR",      "ZAR"),
    BTC("Bitcoin",                  "฿",        "BTC"),
    ETH("Ethereum",                 "",         "ETH"),
    XRP("Ripple",                   "",         "XRP"),
    LTC("Litecoin",                 "",         "LTC"),
    BCH("Bitcoin Cash",             "",         "BCH")
    ;

    private String friendlyName;
    private String symbol;
    private String code;

    Currency(String friendlyName, String symbol, String code) {
        this.friendlyName = friendlyName;
        this.symbol = symbol;
        this.code = code;
    }

    public String getFriendlyName() { return friendlyName; }
    public String getSymbol() { return symbol; }
    public String getCode() { return code; }
}

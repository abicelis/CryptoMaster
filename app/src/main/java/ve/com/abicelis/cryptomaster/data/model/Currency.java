package ve.com.abicelis.cryptomaster.data.model;

/**
 * Created by abicelis on 31/5/2018.
 */
public enum Currency {    USD("United States Dollar",     "$",        true,      "USD"),
    AUD("Australian Dollar",        "$",        true,      "AUD"),
    BRL("Brazilian Real",           "R$",       true,      "BRL"),
    CAD("Canadian Dollar",          "$",        true,      "CAD"),
    CLP("Chilean Peso",             "$",        true,      "CLP"),
    CNY("Chinese Yuan",             "¥",        true,      "CNY"),
    CZK("Czech Koruna",             "Kč",       true,      "CZK"),
    DKK("Danish Krone",             "kr",       true,      "DKK"),
    EUR("Euro",                     "€",        true,      "EUR"),
    HKD("Hong Kong Dollar",         "$",        true,      "HKD"),
    HUF("Hungarian Forint",         "Ft",       true,      "HUF"),
    INR("Indian Rupee",             "₹",        true,      "INR"),
    IDR("Indonesian Rupiah",        "Rp",       true,      "IDR"),
    ILS("Israeli Shekel",           "₪",        true,      "ILS"),
    JPY("Japanese Yen",             "¥",        true,      "JPY"),
    KRW("Korean Won",               "₩",        true,      "KRW"),
    MYR("Malaysian Ringgit",        "RM",       true,      "MYR"),
    MXN("Mexican Peso",             "$",        true,      "MXN"),
    NZD("New Zealand Dollar",       "$",        true,      "NZD"),
    NOK("Norwegian Krone",          "kr",       true,      "NOK"),
    PKR("Pakistani Rupee",          "₨",        true,      "PKR"),
    PHP("Philippine Piso",          "₱",        true,      "PHP"),
    PLN("Polish Zloty",             "zł",       true,      "PLN"),
    RUB("Russian Ruble",            "\u20BD",   true,      "RUB"),
    SGD("Singaporian Dollar",       "$",        true,      "SGD"),
    ZAR("South African Rand",       "R",        true,      "ZAR"),
    SEK("Swedish Krona",            "kr",       true,      "SEK"),
    CHF("Swiss Franc",              "F",        true,      "CHF"),
    TWD("Taiwanese New Dollar",     "$",        true,      "TWD"),
    THB("Thai Baht",                "฿",        true,      "THB"),
    TRY("Turkish lira",             "₺",        true,      "TRY"),
    GBP("United Kingdom Pound",     "£",        true,      "GBP"),
    BTC("Bitcoin",                  "฿",        true,      "BTC"),
    BCH("Bitcoin Cash",             "",         false,     "BCH"),
    ETH("Ethereum",                 "Ξ",        true,      "ETH"),
    LTC("Litecoin",                 "Ł",        true,      "LTC"),
    XRP("Ripple",                   "",         false,      "XRP")
    ;

    private String friendlyName;
    private String symbol;
    private String code;
    private boolean hasSymbol;

    Currency(String friendlyName, String symbol, boolean hasSymbol, String code) {
        this.friendlyName = friendlyName;
        this.symbol = symbol;
        this.code = code;
        this.hasSymbol = hasSymbol;
    }

    public String getFriendlyName() { return friendlyName; }
    public String getSymbol() { return symbol; }
    public String getCode() { return code; }
    public boolean hasSymbol() { return hasSymbol; }

    public static CharSequence[] getEntries() {
        CharSequence[] array = new CharSequence[values().length];
        for (int i = 0; i < values().length; i++)
            array[i] = values()[i].getFriendlyName();

        return array;
    }

    public static CharSequence[] getEntryValues() {
        CharSequence[] array = new CharSequence[values().length];
        for (int i = 0; i < values().length; i++)
            array[i] = values()[i].getCode();

        return array;
    }
}

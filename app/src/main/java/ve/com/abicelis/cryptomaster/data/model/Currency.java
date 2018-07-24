package ve.com.abicelis.cryptomaster.data.model;

/**
 * Created by abicelis on 31/5/2018.
 */
public enum Currency {
    USD("United States Dollar",     "$",        true,      "USD", false),
    AUD("Australian Dollar",        "$",        true,      "AUD", false),
    BRL("Brazilian Real",           "R$",       true,      "BRL", false),
    CAD("Canadian Dollar",          "$",        true,      "CAD", false),
    CLP("Chilean Peso",             "$",        true,      "CLP", false),
    CNY("Chinese Yuan",             "¥",        true,      "CNY", false),
    CZK("Czech Koruna",             "Kč",       true,      "CZK", false),
    DKK("Danish Krone",             "kr",       true,      "DKK", false),
    EUR("Euro",                     "€",        true,      "EUR", false),
    HKD("Hong Kong Dollar",         "$",        true,      "HKD", false),
    HUF("Hungarian Forint",         "Ft",       true,      "HUF", false),
    INR("Indian Rupee",             "₹",        true,      "INR", false),
    IDR("Indonesian Rupiah",        "Rp",       true,      "IDR", false),
    ILS("Israeli Shekel",           "₪",        true,      "ILS", false),
    JPY("Japanese Yen",             "¥",        true,      "JPY", false),
    KRW("Korean Won",               "₩",        true,      "KRW", false),
    MYR("Malaysian Ringgit",        "RM",       true,      "MYR", false),
    MXN("Mexican Peso",             "$",        true,      "MXN", false),
    NZD("New Zealand Dollar",       "$",        true,      "NZD", false),
    NOK("Norwegian Krone",          "kr",       true,      "NOK", false),
    PKR("Pakistani Rupee",          "₨",        true,      "PKR", false),
    PHP("Philippine Piso",          "₱",        true,      "PHP", false),
    PLN("Polish Zloty",             "zł",       true,      "PLN", false),
    RUB("Russian Ruble",            "\u20BD",   true,      "RUB", false),
    SGD("Singaporian Dollar",       "$",        true,      "SGD", false),
    ZAR("South African Rand",       "R",        true,      "ZAR", false),
    SEK("Swedish Krona",            "kr",       true,      "SEK", false),
    CHF("Swiss Franc",              "F",        true,      "CHF", false),
    TWD("Taiwanese New Dollar",     "$",        true,      "TWD", false),
    THB("Thai Baht",                "฿",        true,      "THB", false),
    TRY("Turkish lira",             "₺",        true,      "TRY", false),
    GBP("United Kingdom Pound",     "£",        true,      "GBP", false),
    BTC("Bitcoin",                  "฿",        true,      "BTC", true),
    BCH("Bitcoin Cash",             "",         false,     "BCH", true),
    ETH("Ethereum",                 "Ξ",        true,      "ETH", true),
    LTC("Litecoin",                 "Ł",        true,      "LTC", true),
    XRP("Ripple",                   "",         false,     "XRP", true)
    ;

    private String friendlyName;
    private String symbol;
    private String code;
    private boolean hasSymbol;
    private boolean isCrypto;

    Currency(String friendlyName, String symbol, boolean hasSymbol, String code, boolean isCrypto) {
        this.friendlyName = friendlyName;
        this.symbol = symbol;
        this.code = code;
        this.hasSymbol = hasSymbol;
        this.isCrypto = isCrypto;
    }

    public String getFriendlyName() { return friendlyName; }
    public String getSymbol() { return symbol; }
    public String getCode() { return code; }
    public boolean hasSymbol() { return hasSymbol; }
    public boolean isCrypto() { return isCrypto; }
    public boolean isBitcoin() {
        boolean res1 =  (this.equals(Currency.BTC));
        boolean res2 =  (this ==  Currency.BTC);
        return res1;
    }

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

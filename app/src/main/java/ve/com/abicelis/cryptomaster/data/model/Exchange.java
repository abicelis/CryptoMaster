package ve.com.abicelis.cryptomaster.data.model;

/**
 * Created by abicelis on 26/7/2018.
 */
public class Exchange {

    private int number;
    private String name;
    private Currency currencyFrom;
    private Currency currencyTo;
    private double price;
    private double volume;

    public Exchange(int number, String name, Currency currencyFrom, Currency currencyTo, double price, double volume) {
        this.number = number;
        this.name = name;
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.price = price;
        this.volume = volume;
    }

    public int getNumber() { return number; }
    public String getName() { return name; }
    public Currency getCurrencyFrom() { return currencyFrom; }
    public Currency getCurrencyTo() { return currencyTo; }
    public double getPrice() { return price; }
    public double getVolume() { return volume; }
}

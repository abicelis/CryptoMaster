package ve.com.abicelis.cryptomaster.data.model;

/**
 * Created by abicelis on 26/7/2018.
 */
public class Exchange {

    private int number;
    private String name;
    private String codeFrom;
    private String codeTo;
    private double price;
    private double volume;

    public Exchange(int number, String name, String codeFrom, String codeTo, double price, double volume) {
        this.number = number;
        this.name = name;
        this.codeFrom = codeFrom;
        this.codeTo = codeTo;
        this.price = price;
        this.volume = volume;
    }

    public int getNumber() { return number; }
    public String getName() { return name; }
    public String getCodeFrom() { return codeFrom; }
    public String getCodeTo() { return codeTo; }
    public double getPrice() { return price; }
    public double getVolume() { return volume; }
}

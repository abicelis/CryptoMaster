package ve.com.abicelis.cryptomaster.data.model.coinmarketcapgraph;

import com.google.gson.annotations.SerializedName;

/**
 * Created by abicelis on 31/5/2018.
 */
public class DominanceResult {

    private double[][] bitcoin;

    @SerializedName("bitcoin-cash")
    private double[][] bitcoinCash;

    private double[][] dash;
    private double[][] ethereum;
    private double[][] iota;
    private double[][] litecoin;
    private double[][] monero;
    private double[][] nem;
    private double[][] neo;
    private double[][] others;
    private double[][] ripple;

    public double[][] getBitcoin() {
        return bitcoin;
    }

    public void setBitcoin(double[][] bitcoin) {
        this.bitcoin = bitcoin;
    }

    public double[][] getBitcoinCash() {
        return bitcoinCash;
    }

    public void setBitcoinCash(double[][] bitcoinCash) {
        this.bitcoinCash = bitcoinCash;
    }

    public double[][] getDash() {
        return dash;
    }

    public void setDash(double[][] dash) {
        this.dash = dash;
    }

    public double[][] getEthereum() {
        return ethereum;
    }

    public void setEthereum(double[][] ethereum) {
        this.ethereum = ethereum;
    }

    public double[][] getIota() {
        return iota;
    }

    public void setIota(double[][] iota) {
        this.iota = iota;
    }

    public double[][] getLitecoin() {
        return litecoin;
    }

    public void setLitecoin(double[][] litecoin) {
        this.litecoin = litecoin;
    }

    public double[][] getMonero() {
        return monero;
    }

    public void setMonero(double[][] monero) {
        this.monero = monero;
    }

    public double[][] getNem() {
        return nem;
    }

    public void setNem(double[][] nem) {
        this.nem = nem;
    }

    public double[][] getNeo() {
        return neo;
    }

    public void setNeo(double[][] neo) {
        this.neo = neo;
    }

    public double[][] getOthers() {
        return others;
    }

    public void setOthers(double[][] others) {
        this.others = others;
    }

    public double[][] getRipple() {
        return ripple;
    }

    public void setRipple(double[][] ripple) {
        this.ripple = ripple;
    }
}

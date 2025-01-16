package controllers;

public class WaterData {
    private String datum;
    private int TDSdata;
    private int troebelheid;
    private boolean kwaliteit;

    public WaterData(String datum, int TDSdata, int troebelheid, boolean kwaliteit) {
        this.datum = datum;
        this.TDSdata = TDSdata;
        this.troebelheid = troebelheid;
        this.kwaliteit = kwaliteit;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public int getTDSdata() {
        return TDSdata;
    }

    public void setTDSdata(int TDSdata) {
        this.TDSdata = TDSdata;
    }

    public int getTroebelheid() {
        return troebelheid;
    }

    public void setTroebelheid(int troebelheid) {
        this.troebelheid = troebelheid;
    }

    public boolean isKwaliteit() {
        return kwaliteit;
    }

    public void setKwaliteit(boolean kwaliteit) {
        this.kwaliteit = kwaliteit;
    }
}

package controllers;

public class WaterData {
    private String datum;
    private float TDSdata;
    private float troebelheid;
    private boolean kwaliteit;

    public WaterData(String datum, float TDSdata, float troebelheid, boolean kwaliteit) {
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

    public float getTDSdata() {
        return TDSdata;
    }

    public void setTDSdata(float TDSdata) {
        this.TDSdata = TDSdata;
    }

    public float getTroebelheid() {
        return troebelheid;
    }

    public void setTroebelheid(float troebelheid) {
        this.troebelheid = troebelheid;
    }

    public boolean isKwaliteit() {
        return kwaliteit;
    }

    public void setKwaliteit(boolean kwaliteit) {
        this.kwaliteit = kwaliteit;
    }
}

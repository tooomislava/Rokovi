package tzilic_20.rokovi.model;

import java.io.Serializable;
import java.util.Date;

public class Rok implements Serializable {

    private int sifra;
    private Date datum;
    private String kolegij;
    private int brojPrijavljenih;

    public Rok() {
    }

    public Rok(int sifra, Date datum, String kolegij, int brojPrijavljenih) {
        this.sifra = sifra;
        this.datum = datum;
        this.kolegij = kolegij;
        this.brojPrijavljenih = brojPrijavljenih;
    }

    public int getSifra() {
        return sifra;
    }

    public void setSifra(int sifra) {
        this.sifra = sifra;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getKolegij() {
        return kolegij;
    }

    public void setKolegij(String kolegij) {
        this.kolegij = kolegij;
    }

    public int getBrojPrijavljenih() {
        return brojPrijavljenih;
    }

    public void setBrojPrijavljenih(int brojPrijavljenih) {
        this.brojPrijavljenih = brojPrijavljenih;
    }
}

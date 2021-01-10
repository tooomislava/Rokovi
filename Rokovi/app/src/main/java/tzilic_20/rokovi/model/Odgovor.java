package tzilic_20.rokovi.model;

import java.util.List;

public class Odgovor {

    private Poruka poruka;
    private List<Rok> podaci;

    public Poruka getPoruka() {
        return poruka;
    }

    public void setPoruka(Poruka poruka) {
        this.poruka = poruka;
    }

    public List<Rok> getPodaci() {
        return podaci;
    }

    public void setPodaci(List<Rok> podaci) {
        this.podaci = podaci;
    }
}

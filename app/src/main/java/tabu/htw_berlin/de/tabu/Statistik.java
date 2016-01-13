package tabu.htw_berlin.de.tabu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by philipp on 13.01.16.
 */
public class Statistik implements Serializable {
    private List gezogeneBegriffe;
    private List korrekteBegriffe;
    private int punktzahl;



    public Statistik() {
        gezogeneBegriffe = new ArrayList();
        korrekteBegriffe = new ArrayList();
        punktzahl = 0;
    }


    public void addGezogenenBegriff(String begriff) {
        gezogeneBegriffe.add(begriff);
    }

    public void addKorrektenBegriff(String begriff) {
        korrekteBegriffe.add(begriff);
    }

    public void erhoehePunktzahl() {
        punktzahl++;
    }

    public List getGezogeneBegriffe() {
        return gezogeneBegriffe;
    }

    public List getKorrekteBegriffe() {
        return korrekteBegriffe;
    }

    public int getPunktzahl() {
        return punktzahl;
    }
}

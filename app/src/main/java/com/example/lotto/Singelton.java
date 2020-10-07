package com.example.lotto;

public class Singelton {

    public static final Singelton instance = new Singelton();

    private String datum_text = "Keine Verbindung";
    private String zahlen_text = "Keine Verbindung";
    private String superzahl_text = "Keine Verbindung";

    private Integer[] zahlenListe = new Integer[7];
    private int gezogeneSuperzahl;


    public static Singelton getInstance() {
        return instance;
    }

    private Singelton() {}

    public void setGezogeneZahlen(Integer[] gezogeneZahlenWeb, int superzahl) {

        zahlenListe[1] = gezogeneZahlenWeb[1];
        zahlenListe[2] = gezogeneZahlenWeb[2];
        zahlenListe[3] = gezogeneZahlenWeb[3];
        zahlenListe[4] = gezogeneZahlenWeb[4];
        zahlenListe[5] = gezogeneZahlenWeb[5];
        zahlenListe[6] = gezogeneZahlenWeb[6];
        gezogeneSuperzahl = superzahl;

    }

    public Integer[] getZahlenListe() {
        return zahlenListe;
    }

    public int getGezogeneSuperzahl() {
        return gezogeneSuperzahl;
    }

    public String getDatum_text() {
        return datum_text;
    }

    public void setDatum_text(String datum_text) {
        this.datum_text = datum_text;
    }

    public String getZahlen_text() {
        return zahlen_text;
    }

    public void setZahlen_text(String zahlen_text) {
        this.zahlen_text = zahlen_text;
    }

    public String getSuperzahl_text() {
        return superzahl_text;
    }

    public void setSuperzahl_text(String superzahl_text) {
        this.superzahl_text = superzahl_text;
    }

}

package com.example.lotto;

import java.util.ArrayList;

public class TipsUebersicht {

    // Alle Zahlen, die getippt wurden
    private int nummer1;
    private int nummer2;
    private int nummer3;
    private int nummer4;
    private int nummer5;
    private int nummer6;
    private int nummerSuperzahl;

    // Alle Booleans, ob die jeweilige Zahl richtig ist
    private boolean boolnummer1;
    private boolean boolnummer2;
    private boolean boolnummer3;
    private boolean boolnummer4;
    private boolean boolnummer5;
    private boolean boolnummer6;
    private boolean boolnummerSuperzahl;

    private int countRichtige;

    public TipsUebersicht (Integer[] listeDerTipps, Boolean[] richtigeZahlen, int superzahl, boolean boolSuperzahl, int countRichtige) {

        nummer1 = listeDerTipps[0];
        nummer2 = listeDerTipps[1];
        nummer3 = listeDerTipps[2];
        nummer4 = listeDerTipps[3];
        nummer5 = listeDerTipps[4];
        nummer6 = listeDerTipps[5];
        nummerSuperzahl = superzahl;

        boolnummer1 = richtigeZahlen[0];
        boolnummer2 = richtigeZahlen[1];
        boolnummer3 = richtigeZahlen[2];
        boolnummer4 = richtigeZahlen[3];
        boolnummer5 = richtigeZahlen[4];
        boolnummer6 = richtigeZahlen[5];
        boolnummerSuperzahl = boolSuperzahl;

        this.countRichtige = countRichtige;

    }

    public int getCountRichtige(){
        return countRichtige;
    }

    public void setCountRichtige(int countrichtige) {
        this.countRichtige = countrichtige;
    }

    public int getNummer1() {
        return nummer1;
    }

    public void setNummer1(int nummer1) {
        this.nummer1 = nummer1;
    }

    public int getNummer2() {
        return nummer2;
    }

    public void setNummer2(int nummer2) {
        this.nummer2 = nummer2;
    }

    public int getNummer3() {
        return nummer3;
    }

    public void setNummer3(int nummer3) {
        this.nummer3 = nummer3;
    }

    public int getNummer4() {
        return nummer4;
    }

    public void setNummer4(int nummer4) {
        this.nummer4 = nummer4;
    }

    public int getNummer5() {
        return nummer5;
    }

    public void setNummer5(int nummer5) {
        this.nummer5 = nummer5;
    }

    public int getNummer6() {
        return nummer6;
    }

    public void setNummer6(int nummer6) {
        this.nummer6 = nummer6;
    }

    public int getNummerSuperzahl() {
        return nummerSuperzahl;
    }

    public void setNummerSuperzahl(int nummerSuperzahl) {
        this.nummerSuperzahl = nummerSuperzahl;
    }

    public boolean isBoolnummer1() {
        return boolnummer1;
    }

    public void setBoolnummer1(boolean boolnummer1) {
        this.boolnummer1 = boolnummer1;
    }

    public boolean isBoolnummer2() {
        return boolnummer2;
    }

    public void setBoolnummer2(boolean boolnummer2) {
        this.boolnummer2 = boolnummer2;
    }

    public boolean isBoolnummer3() {
        return boolnummer3;
    }

    public void setBoolnummer3(boolean boolnummer3) {
        this.boolnummer3 = boolnummer3;
    }

    public boolean isBoolnummer4() {
        return boolnummer4;
    }

    public void setBoolnummer4(boolean boolnummer4) {
        this.boolnummer4 = boolnummer4;
    }

    public boolean isBoolnummer5() {
        return boolnummer5;
    }

    public void setBoolnummer5(boolean boolnummer5) {
        this.boolnummer5 = boolnummer5;
    }

    public boolean isBoolnummer6() {
        return boolnummer6;
    }

    public void setBoolnummer6(boolean boolnummer6) {
        this.boolnummer6 = boolnummer6;
    }

    public boolean isBoolnummerSuperzahl() {
        return boolnummerSuperzahl;
    }

    public void setBoolnummerSuperzahl(boolean boolnummerSuperzahl) {
        this.boolnummerSuperzahl = boolnummerSuperzahl;
    }
}

package com.example.lotto;

import com.google.gson.annotations.SerializedName;

public class Tips {

    @SerializedName("id")
    private int id;
    @SerializedName("number1")
    private int number1;
    @SerializedName("number2")
    private int number2;
    @SerializedName("number3")
    private int number3;
    @SerializedName("number4")
    private int number4;
    @SerializedName("number5")
    private int number5;
    @SerializedName("number6")
    private int number6;
    @SerializedName("numberSuperzahl")
    private int numberSuperzahl;

    public Tips(int id, int number1, int number2, int number3, int number4, int number5, int number6, int numberSuperzahl) {
        this.id = id;
        this.number1 = number1;
        this.number2 = number2;
        this.number3 = number3;
        this.number4 = number4;
        this.number5 = number5;
        this.number6 = number6;
        this.numberSuperzahl = numberSuperzahl;


    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public int getNumber1(){
        return number1;
    }

    public int getNumber2(){
        return number2;
    }

    public int getNumber3(){
        return number3;
    }

    public int getNumber4(){
        return number4;
    }

    public int getNumber5() {
        return number5;
    }

    public int getNumber6(){
        return number6;
    }

    public int getNumberSuperzahl(){
        return numberSuperzahl;
    }
}

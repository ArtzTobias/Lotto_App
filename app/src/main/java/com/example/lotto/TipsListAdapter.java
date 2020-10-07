package com.example.lotto;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class TipsListAdapter extends ArrayAdapter<TipsUebersicht> {

    private Context mContext;
    private int mResource;

    // Zählvariable, die die Anzahl der richtigen Zahlen zählt
    private int countRichtige = 0;
    private boolean boolnummerSuperzahl;

    private String textSuperzahlRichtig = "falsch";

    private int superzahl;


    public TipsListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<TipsUebersicht> objects, int superzahl, boolean boolnummerSuperzahl) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;

        this.superzahl = superzahl;
        this.boolnummerSuperzahl = boolnummerSuperzahl;

    }

    public View getView(int position, View convertView, ViewGroup parent){
        //Alle Informationen holen

        int nummer1 = getItem(position).getNummer1();
        int nummer2 = getItem(position).getNummer2();
        int nummer3 = getItem(position).getNummer3();
        int nummer4 = getItem(position).getNummer4();
        int nummer5 = getItem(position).getNummer5();
        int nummer6 = getItem(position).getNummer6();
        int nummerSuperzahl = getItem(position).getNummerSuperzahl();

        boolean boolnummer1 = getItem(position).isBoolnummer1();
        boolean boolnummer2 = getItem(position).isBoolnummer2();
        boolean boolnummer3 = getItem(position).isBoolnummer3();
        boolean boolnummer4 = getItem(position).isBoolnummer4();
        boolean boolnummer5 = getItem(position).isBoolnummer5();
        boolean boolnummer6 = getItem(position).isBoolnummer6();
        boolean boolnummerSuperzahl = getItem(position).isBoolnummerSuperzahl();

        int countRichtige = getItem(position).getCountRichtige();




        // Objekt mit den Informationen erstellen
        //Tips tips = new Tips(id, nummer1, nummer2, nummer3, nummer4, nummer5, nummer6, nummerSuperzahl);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);


        // TextViews erstellen

        TextView textView1 = (TextView) convertView.findViewById(R.id.rowNumber1);
        if (boolnummer1 == true) {
            textView1.setBackgroundResource(R.drawable.text_style_for_numbers_right);
        } else {
            textView1.setBackgroundResource(R.drawable.text_style_for_numbers_wrong);
        }
        textView1.setText(nummer1 + "");
        textView1.setTextColor(Color.BLACK);

        TextView textView2 = (TextView) convertView.findViewById(R.id.rowNumber2);
        if (boolnummer2 == true) {
            textView2.setBackgroundResource(R.drawable.text_style_for_numbers_right);
        } else {
            textView2.setBackgroundResource(R.drawable.text_style_for_numbers_wrong);
        }
        textView2.setText(nummer2 + "");
        textView2.setTextColor(Color.BLACK);

        TextView textView3 = (TextView) convertView.findViewById(R.id.rowNumber3);
        if (boolnummer3 == true) {
            textView3.setBackgroundResource(R.drawable.text_style_for_numbers_right);
        } else {
            textView3.setBackgroundResource(R.drawable.text_style_for_numbers_wrong);
        }
        textView3.setText(nummer3 + "");
        textView3.setTextColor(Color.BLACK);

        TextView textView4 = (TextView) convertView.findViewById(R.id.rowNumber4);
        if (boolnummer4 == true) {
            textView4.setBackgroundResource(R.drawable.text_style_for_numbers_right);
        } else {
            textView4.setBackgroundResource(R.drawable.text_style_for_numbers_wrong);
        }
        textView4.setText(nummer4 + "");
        textView4.setTextColor(Color.BLACK);

        TextView textView5 = (TextView) convertView.findViewById(R.id.rowNumber5);
        if (boolnummer5 == true) {
            textView5.setBackgroundResource(R.drawable.text_style_for_numbers_right);
        } else {
            textView5.setBackgroundResource(R.drawable.text_style_for_numbers_wrong);
        }
        textView5.setText(nummer5 + "");
        textView5.setTextColor(Color.BLACK);

        TextView textView6 = (TextView) convertView.findViewById(R.id.rowNumber6);
        if (boolnummer6 == true) {
            textView6.setBackgroundResource(R.drawable.text_style_for_numbers_right);
        } else {
            textView6.setBackgroundResource(R.drawable.text_style_for_numbers_wrong);
        }
        textView6.setText(nummer6 + "");
        textView6.setTextColor(Color.BLACK);

        TextView textViewSuperzahl = (TextView) convertView.findViewById(R.id.rowNumberS);
        if (boolnummerSuperzahl == true) {
            textViewSuperzahl.setBackgroundResource(R.drawable.text_style_for_superzahl_right);
        } else {
            textViewSuperzahl.setBackgroundResource(R.drawable.text_style_for_superzahl_wrong);
        }
        textViewSuperzahl.setText(nummerSuperzahl + "");
        textViewSuperzahl.setTextColor(Color.BLACK);

        TextView richtigGetippt = convertView.findViewById(R.id.textRichtige);
        richtigGetippt.setText("Richtige: "+ countRichtige);

        if (boolnummerSuperzahl) {
            textSuperzahlRichtig = "richtig";
        }
        TextView richtigeSuperzahl = convertView.findViewById(R.id.textSuperzahl);
        richtigeSuperzahl.setText("Superzahl: " + textSuperzahlRichtig);


        return convertView;


    }
}

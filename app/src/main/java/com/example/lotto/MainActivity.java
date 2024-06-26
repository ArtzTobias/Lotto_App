package com.example.lotto;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.Parser;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.example.lotto.R.id.coordinator;
import static com.example.lotto.R.id.listviewMyTips;
import static com.example.lotto.R.id.view_offset_helper;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private final LoadingDialog loadingDialog = new LoadingDialog(MainActivity.this);

    private Integer[] gezogeneZahlenWEB;
    private String textfromWeb = null;
    private int superzahl = -1;

    private String inhaltButton = null;
    private int counterRichtige = 0;
    private int counterSuperzahl = 0;
    private int gefundeneSuperzahl = -1;

    // Strings, die die Daten aus dem Internet enthalten

    private String inhaltWEBZahlen = null;

    private String datum_und_Tag_WEB = null;
    private String gezogeneZahlenFürTextView = null;
    private String gezogeneSuperzahlFürTextView = null;



    private ArrayList<TextView> tvList = new ArrayList<>();
    private TextView textView;

    private ArrayList<TextView> tvListSuperzahl = new ArrayList<>();
    private TextView textViewSuperzahl;

    private ListView listviewMyTips;
    private ArrayAdapter adapter;

    private MyTotalListView myList = new MyTotalListView();


    // Alert damit die App nicht abstürzt falls keine Internetverbindung besteht

    private AlertDialog alert;



    //nach oben verschoben

    private File file;
    private File file2;
    private File fileForDeletedButton;
    private File fileWebgeladen;
    private final String filname = "myfile";
    private final String secondFile = "mysecondfile";
    private final String junkFile = "junkFile";
    private final String webloadedFile = "WebloadedFile";


    private String contents;

    private int counterforDelete = 0;


    private LinearLayout mainLayout;
    private LayoutInflater inflater;
    private View custom_row;
    private Button button;
    private ArrayList<Button> buttons;

    private boolean firstThreadFinished = false;
    private boolean secondThreadFinished = false;

    // ListView die alle Einträge (Tips) enthält
    private ListView listView;

    // deaktivierte Buttons der Row in der ListView
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button buttonSuperzahl;

    private String textSuperzahlRichtig;
    private int countRichtige;

    // Variablen die die getippten Zahlen speichern
    private int id = 0;
    private String nameDesTipps;
    private int nummer1;
    private int nummer2;
    private int nummer3;
    private int nummer4;
    private int nummer5;
    private int nummer6;
    private int nummerSuperzahl;

    private Integer[] getippteZahlen = new Integer[6];
    private Integer[] gezogeneZahlenSingleton = new Integer[7];
    private int superzahlSingleton;
    // Booleans um zu prüfen, ob diese Zahl gezogen wurde oder nicht

    private boolean boolnummer1 = false;
    private boolean boolnummer2 = false;
    private boolean boolnummer3 = false;
    private boolean boolnummer4 = false;
    private boolean boolnummer5 = false;
    private boolean boolnummer6 = false;
    private boolean boolnummerSuperzahl = false;

    private Boolean[] getippteZahlenBewertung = new Boolean[6];

    // TextViews, die die Informationen der gezogenen Zahlen enthalten
    private TextView datumTextView;
    private TextView gezogeneZahlen;
    private TextView gezogeneSuperzahl;

    // Wird benutzt, um zu überprüfen, ob Singleton bereits die Daten enthält oder nicht
    String text_von_datum;

    // TextViews für die Row
    private TextView richtigGetippt;
    private TextView richtigeSuperzahl;

    // Diese ArrayList beinhaltet alle vom User getippten Zahlen
    private ArrayList<TipsUebersicht> alleTips = new ArrayList<>();

    // Zahlen, die beim Klicken eines ListViewButtons gespeichert werden müssen

    private String nameOnClick;
    private int zahlOnClick1;
    private int zahlOnClick2;
    private int zahlOnClick3;
    private int zahlOnClick4;
    private int zahlOnClick5;
    private int zahlOnClick6;
    private int zahlOnClickSuperzahl;

    // adapter für die ListView
    private TipsListAdapter tipsListAdapter;

    private int buttonNumber = 0;

    private TextView gezogeneZahl1TextView;
    private TextView gezogeneZahl2TextView;
    private TextView gezogeneZahl3TextView;
    private TextView gezogeneZahl4TextView;
    private TextView gezogeneZahl5TextView;
    private TextView gezogeneZahl6TextView;
    private TextView gezogeneZahlSuperzahlTextView;

    private ThreadsAbgeschlossen threadsAbgeschlossen;

    public MainActivity() {
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // SDK initialisieren
        MobileAds.initialize(this);

        // Werbungsbanner initialisieren
        AdView adView = (AdView) findViewById(R.id.adView_mainScreen);

        AdRequest adRequest = new AdRequest.Builder().build();

        adView.loadAd(adRequest);



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, Pop.class));


                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                */
            }
        });



        //Connecting to Website

        /*
        Vorgehensweise

        1. --Variablen von gezogenen Zahlen oben mit private schreiben
        2. --TextView Array erstellen, um die Textviews in den einzelnen rows zu identifizieren
        3. --Zahlen von getippten zahlen (Buttons) ermitteln mit contains (Zahlen einzeln und "Superzahl: i" , dann Menge vergleichen einzeln == 7?)
        4. --TextView.setText(#richtige zahlen) Es fehlt eine TextView für die Superzahl

        5. --Button zuende implementieren in class Pop

        6. Design
         */

        // Loading Dialog initialisieren und ausführen





        // Context für die Files herstellen

        final Context context = getApplicationContext();


        final WebItems webItems = new WebItems();

        webItems.execute("https://www.google.com/search?q=Lotto+6+aus+49&sca_esv=453f36eb1da0db3c&sca_upv=1&biw=1920&bih=927&sxsrf=ADLYWIIit8-4vzH3m_Z5Dlaf9MeBfLn0EQ%3A1718892851633&ei=Mzl0ZpGgJr6Wxc8P5rCV8Ac&ved=0ahUKEwjRhdDuruqGAxU-S_EDHWZYBX4Q4dUDCBA&uact=5&oq=Lotto+6+aus+49&gs_lp=Egxnd3Mtd2l6LXNlcnAiDkxvdHRvIDYgYXVzIDQ5MgoQIxiABBgnGIoFMhAQABiABBixAxiDARgUGIcCMgsQABiABBixAxiDATILEAAYgAQYsQMYgwEyCxAAGIAEGLEDGIMBMggQABiABBixAzILEAAYgAQYsQMYgwEyBRAAGIAEMgUQABiABDIFEAAYgARItxFQhQdYhBBwAXgBkAEAmAFioAHfBaoBATm4AQPIAQD4AQGYAgqgAoUGwgIKEAAYsAMY1gQYR8ICDRAAGIAEGLADGEMYigXCAgoQABiABBgUGIcCwgIIEAAYgAQYywGYAwCIBgGQBgqSBwM4LjKgB5hB&sclient=gws-wiz-serp");


        //Thread hinzufügen, der die Daten ausliest



        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                while(textfromWeb == null) {

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    textfromWeb = webItems.getTextfromWeb();

                }

                //System.out.println("WebText von MainActivity: " + textfromWeb);
                System.out.println("WebText von MainActivity (Länge): " + textfromWeb.length());
                //System.out.println(textfromWeb);

                String regex1 = "\\d{2}" + "\\p{Punct}" + "\\d{2}" + "\\p{Punct}" + "\\d{4}";

                Matcher m1 = Pattern.compile(regex1).matcher(textfromWeb);

                if (m1.find()) {
                    System.out.println("Das ist das gefundene Datum in WEB 1: " + m1.group(0));
                } else {
                    System.out.println("Kein Datum gefunden");
                }

                String datumZiehung = m1.group(0);


                // Den Tag aus dem Datum bekommen

                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                Date date = null;

                try {
                    date = format.parse(datumZiehung);
                    //System.out.println("Hat das geklappt mit dem Datum? " + date);
                } catch (ParseException e) {
                    System.out.println("Kein datum in Activity main gefunden");
                }


                String dayOfTheWeek = (String) android.text.format.DateFormat.format("EEEE", date);

                //System.out.println("Das ist der Tag aus dem WEB: " + dayOfTheWeek);

                // String Nr1. für die File4

                dayOfTheWeek = translateDay(dayOfTheWeek);

                datum_und_Tag_WEB = "Ziehung vom " + dayOfTheWeek + ", " + datumZiehung;

                // Datum im Singleton speichern
                Singelton.instance.setDatum_text(datum_und_Tag_WEB);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        datumTextView = findViewById(R.id.Datum);
                        datumTextView.setText(datum_und_Tag_WEB);
                    }
                });



                //Gezogene Zahlen herausfiltern

                gezogeneZahlenWEB = new Integer[7];

                Boolean[] storage = new Boolean[50];
                Arrays.fill(storage, Boolean.FALSE);


                String textGezogeneZahlen = getGezogeneZahlenPart(textfromWeb);
                for (int i = 0; i < 50; i++) {
                    if (textGezogeneZahlen.contains(">" + i + "<")) {
                        System.out.println("Die gezogene Zahl ist: " + i);
                        storage[i] = true;

                    }
                }
                String textSuperzahl = getSuperzahlPart(textfromWeb);
                for (int i = 0; i < 10; i++) {
                    if (textSuperzahl.contains(">" + i + "<")) {
                        System.out.println("Die Superzahl ist: " + i);
                        superzahl = i;
                    }
                }

                int zählt_true_in_storage = 0;

                int eintragFürGezogene = 0;

                if (superzahl == -1) {
                    System.out.println("Daten konnten nicht ermittelt werden");
                } else {
                    for (int i = 0; i < 50; i++) {
                        if (storage[i] == true) {
                            ++zählt_true_in_storage;
                        }
                    }
                    if (zählt_true_in_storage == 7) {
                        for (int i = 0; i < 50; i++) {
                            if (storage[i] == true && (i != superzahl)) {
                                gezogeneZahlenWEB[++eintragFürGezogene] = i;
                            }
                        }
                    } else if (zählt_true_in_storage == 6) {
                        for (int i = 0; i < 50; i++) {
                            if (storage[i]) {
                                gezogeneZahlenWEB[++eintragFürGezogene] = i;
                            }
                        }
                    } else {
                        System.out.println("Mehr bzw. weniger trues im Storage als angenommen. Fehler beim Laden");
                    }

                }


                for (int i = 1; i < 7; i++) {
                    System.out.println("Einträge in gezogeneZahlenWeb Nr. " + i + ": " + gezogeneZahlenWEB[i]);
                }



                gezogeneZahlenFürTextView = "Zahlen: " + gezogeneZahlenWEB[1] + " " + gezogeneZahlenWEB[2] + " " + gezogeneZahlenWEB[3] + " " + gezogeneZahlenWEB[4] + " " + gezogeneZahlenWEB[5] + " " + gezogeneZahlenWEB[6];
                gezogeneSuperzahlFürTextView = "Superzahl: " + superzahl;


                // Die gezogenen Zahlen in Singleton abspeichern
                Singelton.instance.setGezogeneZahlen(gezogeneZahlenWEB, superzahl);

                // Die Strings im Singleton speichern
                Singelton.instance.setZahlen_text(gezogeneZahlenFürTextView);
                Singelton.instance.setSuperzahl_text(gezogeneSuperzahlFürTextView);

                // Daten in die Textviews schreiben

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        // Hier werden die Zahlen zugewiesen

                        gezogeneZahl1TextView = findViewById(R.id.zahlnummer1);
                        gezogeneZahl2TextView = findViewById(R.id.zahlnummer2);
                        gezogeneZahl3TextView = findViewById(R.id.zahlnummer3);
                        gezogeneZahl4TextView = findViewById(R.id.zahlnummer4);
                        gezogeneZahl5TextView = findViewById(R.id.zahlnummer5);
                        gezogeneZahl6TextView = findViewById(R.id.zahlnummer6);
                        gezogeneZahlSuperzahlTextView = findViewById(R.id.zahlnummerSuperzahl);

                        gezogeneZahl1TextView.setText(gezogeneZahlenWEB[1] + "");
                        gezogeneZahl2TextView.setText(gezogeneZahlenWEB[2] + "");
                        gezogeneZahl3TextView.setText(gezogeneZahlenWEB[3] + "");
                        gezogeneZahl4TextView.setText(gezogeneZahlenWEB[4] + "");
                        gezogeneZahl5TextView.setText(gezogeneZahlenWEB[5] + "");
                        gezogeneZahl6TextView.setText(gezogeneZahlenWEB[6] + "");
                        gezogeneZahlSuperzahlTextView.setText(superzahl + "");

                        /*
                        gezogeneZahlen = findViewById(R.id.gezogeneZahlen);
                        gezogeneZahlen.setText(gezogeneZahlenFürTextView);

                        gezogeneSuperzahl = findViewById(R.id.gezogeneSuperzahl);
                        gezogeneSuperzahl.setText(gezogeneSuperzahlFürTextView);

                         */

                    }
                });

                //Wenn der erste Thread durchgelaufen ist (alle Zahlen wurden erfolgreich aus der Website geladen)
                //dann wird dem zweiten Thread signalisiert, dass alles vorbereitet ist

                firstThreadFinished = true;

                Singelton.instance.setThread1_fertig(firstThreadFinished);

                // Mit JSON Liste erstellen und anschließend Ergebnisse abgleichen

            }
        };

        // TextViews für das Singleton vorbereiten
        datumTextView = findViewById(R.id.Datum);

        gezogeneZahl1TextView = findViewById(R.id.zahlnummer1);
        gezogeneZahl2TextView = findViewById(R.id.zahlnummer2);
        gezogeneZahl3TextView = findViewById(R.id.zahlnummer3);
        gezogeneZahl4TextView = findViewById(R.id.zahlnummer4);
        gezogeneZahl5TextView = findViewById(R.id.zahlnummer5);
        gezogeneZahl6TextView = findViewById(R.id.zahlnummer6);
        gezogeneZahlSuperzahlTextView = findViewById(R.id.zahlnummerSuperzahl);

        /*
        gezogeneZahlen = findViewById(R.id.gezogeneZahlen);
        gezogeneSuperzahl = findViewById(R.id.gezogeneSuperzahl);

         */


        // Die Funktion füllt die oben erstellen TextViews
        displayState();

        // Überprüft, ob Thread1 schon mal gelaufen ist, da Singleton dauerhaft speichern kann, solange die App offen bleibt
        text_von_datum = (String) datumTextView.getText();

        if (text_von_datum.equals("Keine Verbindung")) {
            Thread thread = new Thread(runnable);
            thread.start();
        }



        listView = findViewById(R.id.listviewMyTips);
        listView.setOnItemClickListener(this);


        Runnable createList = new Runnable() {
            @Override
            public void run() {

                // Die Bedingung in der while ist true, wenn Singleton noch keine Daten hat und der Thread1 noch nicht abgeschlossen ist
                while (firstThreadFinished == false && text_von_datum.equals("Keine Verbindung")) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        System.out.println("Thread2 wurde unterbrochen");
                    }
                }

                // JSON File wird gelesen und zurückgegeben

                Gson gson = new Gson();


                String json = jsonFileLesen("mytips.json");
                Tips[] listeAllerTips = gson.fromJson(json, Tips[].class);

                // Falls die Liste leer ist kann nichts verglichen werden

                if (listeAllerTips != null) {

                if (listeAllerTips.length != 0) {


                    for (int i = 0; i < listeAllerTips.length; i++) {       // Es wird durch die gesamte Liste iteriert, um alle Tipps mit den gezogenen Zahlen zu vergleichen

                        id = listeAllerTips[i].getId();
                        nameDesTipps = listeAllerTips[i].getName();
                        nummer1 = listeAllerTips[i].getNumber1();
                        nummer2 = listeAllerTips[i].getNumber2();
                        nummer3 = listeAllerTips[i].getNumber3();
                        nummer4 = listeAllerTips[i].getNumber4();
                        nummer5 = listeAllerTips[i].getNumber5();
                        nummer6 = listeAllerTips[i].getNumber6();
                        nummerSuperzahl = listeAllerTips[i].getNumberSuperzahl();


                        gezogeneZahlenSingleton = Singelton.instance.getZahlenListe(); // Die gezogenen Zahlen wurden beim Thread 1 ins Singleton gespeichert


                        // Die gezogenen Zahlen mit den getippten Zahlen vergleichen
                        countRichtige = 0;
                        boolnummer1 = false;
                        boolnummer2 = false;
                        boolnummer3 = false;
                        boolnummer4 = false;
                        boolnummer5 = false;
                        boolnummer6 = false;
                        boolnummerSuperzahl = false;

                        for (int j = 1; j < 7; j++) {

                            if (nummer1 == gezogeneZahlenSingleton[j]) {
                                ++countRichtige;
                                boolnummer1 = true;
                            }
                            if (nummer2 == gezogeneZahlenSingleton[j]) {
                                ++countRichtige;
                                boolnummer2 = true;
                            }
                            if (nummer3 == gezogeneZahlenSingleton[j]) {
                                ++countRichtige;
                                boolnummer3 = true;
                            }
                            if (nummer4 == gezogeneZahlenSingleton[j]) {
                                ++countRichtige;
                                boolnummer4 = true;
                            }
                            if (nummer5 == gezogeneZahlenSingleton[j]) {
                                ++countRichtige;
                                boolnummer5 = true;
                            }
                            if (nummer6 == gezogeneZahlenSingleton[j]) {
                                ++countRichtige;
                                boolnummer6 = true;
                            }
                        }

                        // Superzahl abgleichen

                        superzahlSingleton = Singelton.instance.getGezogeneSuperzahl();

                        textSuperzahlRichtig = "falsch";
                        boolnummerSuperzahl = false;
                        if (nummerSuperzahl == superzahlSingleton) {
                            textSuperzahlRichtig = "richtig";
                            boolnummerSuperzahl = true;
                        }


                        // Mithilfe eines Adapters die Inhalte der JSON in eine ListView übertragen

                        // Listen befüllen, um diese dann der "TipsÜbersicht" zu übergeben
                        getippteZahlen[0] = nummer1;
                        getippteZahlen[1] = nummer2;
                        getippteZahlen[2] = nummer3;
                        getippteZahlen[3] = nummer4;
                        getippteZahlen[4] = nummer5;
                        getippteZahlen[5] = nummer6;

                        getippteZahlenBewertung[0] = boolnummer1;
                        getippteZahlenBewertung[1] = boolnummer2;
                        getippteZahlenBewertung[2] = boolnummer3;
                        getippteZahlenBewertung[3] = boolnummer4;
                        getippteZahlenBewertung[4] = boolnummer5;
                        getippteZahlenBewertung[5] = boolnummer6;


                        TipsUebersicht listeübersicht = new TipsUebersicht(getippteZahlen, getippteZahlenBewertung, nummerSuperzahl, boolnummerSuperzahl, countRichtige, nameDesTipps);

                        // Wir befinden uns immer noch in der for-Schleife...jeder getippter Tipp wird also in die ArrayList gespeichert
                        alleTips.add(listeübersicht);










               /*

                String jsonText = jsonFileLesen("mytips.json");

                if (jsonText != null) {
                    String id = null;
                    String nummer1 = null;
                    String nummer2 = null;
                    String nummer3 = null;
                    String nummer4 = null;
                    String nummer5 = null;
                    String nummer6 = null;
                    String nummerSuperzahl = null;
                    try {
                        JSONObject jsonObject = new JSONObject(jsonText);

                        id = jsonObject.get("ID").toString();
                        nummer1 = jsonObject.get("Zahl1").toString();
                        nummer2 = jsonObject.get("Zahl2").toString();
                        nummer3 = jsonObject.get("Zahl3").toString();
                        nummer4 = jsonObject.get("Zahl4").toString();
                        nummer5 = jsonObject.get("Zahl5").toString();
                        nummer6 = jsonObject.get("Zahl6").toString();
                        nummerSuperzahl = jsonObject.get("Superzahl").toString();

                    } catch (JSONException e) {
                        System.out.println("Fehler beim Laden der Daten aus der JSON (in Main Zeile 411): " + e.toString());
                    }

                    System.out.println("ID in JSON: " + id);
                    System.out.println("Nummer 1 in JSON: " + nummer1);
                    System.out.println("Nummer 2 in JSON: " + nummer2);
                    System.out.println("Nummer 3 in JSON: " + nummer3);
                    System.out.println("Nummer 4 in JSON: " + nummer4);
                    System.out.println("Nummer 5 in JSON: " + nummer5);
                    System.out.println("Nummer 6 in JSON: " + nummer6);
                    System.out.println("Superzahl in JSON: " + nummerSuperzahl);


                }

                /*
                inflater = getLayoutInflater();
                custom_row = inflater.inflate(R.layout.custom_row, listView, false);
                button = (Button) custom_row.findViewById(R.id.ListButton);
                */
                    }   // Ende der for-Schleife

                    // Hier wird dem adapter die Liste übergeben, die die alle Tipps beinhaltet, sowie das Ergebnis, ob der Tipp richtig ist

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            tipsListAdapter = new TipsListAdapter(context, R.layout.custom_row, alleTips);
                            listView.setAdapter(tipsListAdapter);

                        }
                    });

                }
                }
                    System.out.println("Thread 2 ist durchgelaufen! Yippy Yay Yey!");
                    secondThreadFinished = true;
                    Singelton.instance.setThread2_fertig(secondThreadFinished);

            }
        };

        Thread thread2 = new Thread(createList);
        thread2.start();


        // Navigation Bar implementieren
        BottomNavigationView bottomNavigationView = findViewById(R.id.NavigationbarBottom);

        // Home Nav Button aktivieren
        bottomNavigationView.setSelectedItemId(R.id.home_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        if (text_von_datum.equals("Keine Verbindung")) {

            startActivity(new Intent(getApplicationContext(), LadeBalkenActivity.class));
            overridePendingTransition(0, 0);
        }

        System.out.println("Die Main ist durchgelaufen! Hurray!");
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch(menuItem.getItemId()) {

                        case R.id.home_nav:
                            return true;
                        case R.id.simulator_nav:
                            startActivity(new Intent(getApplicationContext(), Simulator.class));
                            overridePendingTransition(0, 0);
                            return true;
                    }
                    return false;
                }
            };


    public String removeWords(String word, String remove) {
        return word.replace(remove, "");
    }

    private void displayState(){

        String datum_string = Singelton.instance.getDatum_text();

        /* veraltet
        String zahlen_string = Singelton.instance.getZahlen_text();
        String superzahl_string = Singelton.instance.getSuperzahl_text();
        */

        Integer[] gezogeneZahlenListe = new Integer[7];
        gezogeneZahlenListe = Singelton.instance.getZahlenListe();

        int gezSuperzahl = Singelton.instance.getGezogeneSuperzahl();

        // Zuweisung
        datumTextView.setText(datum_string);

        gezogeneZahl1TextView.setText(gezogeneZahlenListe[1] + "");
        gezogeneZahl2TextView.setText(gezogeneZahlenListe[2] + "");
        gezogeneZahl3TextView.setText(gezogeneZahlenListe[3] + "");
        gezogeneZahl4TextView.setText(gezogeneZahlenListe[4] + "");
        gezogeneZahl5TextView.setText(gezogeneZahlenListe[5] + "");
        gezogeneZahl6TextView.setText(gezogeneZahlenListe[6] + "");
        gezogeneZahlSuperzahlTextView.setText(gezSuperzahl + "");



        /* veraltet
        gezogeneZahlen.setText(zahlen_string);
        gezogeneSuperzahl.setText(superzahl_string);

         */
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            startActivity(new Intent(MainActivity.this, Uebersicht.class));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String jsonFileLesen(String name) {
        Context context = getApplicationContext();

        File jsonFile = new File(context.getFilesDir(), name);

        if (!jsonFile.exists())
            jsonFile = new File(name);
        try {

            FileReader fileReader = new FileReader(jsonFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();

            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();

            String response = stringBuilder.toString();

            return response;

        }catch(FileNotFoundException e) {
            System.out.println("Die JSON File konnte nicht gefunden werden (Main Zeile 400): " + e.toString());
        }catch(IOException e) {
            System.out.println("Die JSON File konnte nicht gelesen werden (Main Zeile 402): " + e.toString());
        }

        return null;

    }

    @Override
    public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {

        System.out.println("Hallo, ich wurde angeklickt");
        final TipsUebersicht item = (TipsUebersicht) parent.getItemAtPosition(position);
        String nameVomTipp = item.getTippName();

        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Message")
                .setMessage("Sind sie sicher, dass Sie diesen Tipp " + nameVomTipp + " löschen wollen?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        nameOnClick = item.getTippName();
                        zahlOnClick1 = item.getNummer1();
                        zahlOnClick2 = item.getNummer2();
                        zahlOnClick3 = item.getNummer3();
                        zahlOnClick4 = item.getNummer4();
                        zahlOnClick5 = item.getNummer5();
                        zahlOnClick6 = item.getNummer6();
                        zahlOnClickSuperzahl = item.getNummerSuperzahl();

                        // JSON öffnen und entsprechende Zahlen löschen
                        Gson gson = new Gson();

                        String json = jsonFileLesen("mytips.json");
                        Tips[] listeAllerTips = gson.fromJson(json, Tips[].class);

                        int indexZumÜberspringen = -1;

                        for (int i = 0; i < listeAllerTips.length; i++) {

                            if (nameOnClick.equals(listeAllerTips[i].getName()))
                                if (zahlOnClick1 == listeAllerTips[i].getNumber1())
                                    if (zahlOnClick2 == listeAllerTips[i].getNumber2())
                                        if (zahlOnClick3 == listeAllerTips[i].getNumber3())
                                            if (zahlOnClick4 == listeAllerTips[i].getNumber4())
                                                if (zahlOnClick5 == listeAllerTips[i].getNumber5())
                                                    if (zahlOnClick6 == listeAllerTips[i].getNumber6())
                                                        if (zahlOnClickSuperzahl == listeAllerTips[i].getNumberSuperzahl()) {
                                                            indexZumÜberspringen = i;
                                                            break;
                                                    }
                        }

                        // Neue Liste erstellen, die alle Tipps enthält außer der, die gelöscht werden soll (nicht löschen, sondern überspringen)
                        ArrayList<Tips> neueListeAllerTipps = new ArrayList<>();

                        for (int i = 0; i < listeAllerTips.length; i++) {
                            if (i == indexZumÜberspringen)
                                continue;
                            neueListeAllerTipps.add(listeAllerTips[i]);
                        }

                        String jsonText = gson.toJson(neueListeAllerTipps);

                        // geupdatete Liste in Json speichern
                        add_to_json(jsonText);

                        // ListView informieren, dass das Item gelöscht wurde
                        tipsListAdapter.remove(item);
                        tipsListAdapter.notifyDataSetChanged();

                    }})
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();






                        // Zum neustarten der MAIN activity
        //Intent intent = getIntent();
        //finish();
        //startActivity(intent);
    }

    public void add_to_json(String jsonText) {

        Context context = getApplicationContext();


        try {


            File file = new File(context.getFilesDir(), "mytips.json");
            FileWriter fileWriter = new FileWriter(file, false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(jsonText);
            bufferedWriter.close();

            // Um die File zu leeren
            /*

            FileWriter fileWriter2 = new FileWriter(file, false);
            BufferedWriter bufferedWriter2 = new BufferedWriter(fileWriter2);
            bufferedWriter2.write("");
            bufferedWriter2.close();
            */



            System.out.println("Alles wurde erfolgreich gespeichert (in Main)");

        }catch(IOException e) {
            System.out.println("IOException bei der JSON (in Main (1466)): " + e.toString());
        }


    }

    @Override
    public  void onBackPressed() {
        moveTaskToBack(true);
    }

    public String translateDay(String day){
        if (day.equals("Saturday"))
            return "Samstag";
        if (day.equals("Wednesday"))
            return "Mittwoch";
        return day;
    }

    public String cutText(String text, int index){
        StringBuilder newText = new StringBuilder();

        for(int j = index; j < text.length(); j++){
            newText.append(text.charAt(j));
        }

        return newText.toString();
    }

    public String cutTextStartEnd(String text, int indexStart, int indexEnd){

        StringBuilder textCutted = new StringBuilder();

        for(int j = indexStart; j < indexEnd; j++){
            textCutted.append(text.charAt(j));
        }

        return  textCutted.toString();
    }

    public String getGezogeneZahlenPart(String text){
        int indexStart = text.indexOf("<div class=\"MDTDab\">");
        int indexEnd = text.indexOf("<span class=\"zSMazd ZVkP3\"");
        return cutTextStartEnd(text, indexStart, indexEnd);
    }

    public String getSuperzahlPart(String text){
        int indexStart = text.indexOf("<span class=\"zSMazd ZVkP3\"");
        int indexEnd = text.indexOf("<svg focusable=\"false\"");
        return cutTextStartEnd(text, indexStart, indexEnd);
    }
}

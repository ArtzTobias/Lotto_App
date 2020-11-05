package com.example.lotto;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Simulator extends Activity {

    private Button modusButton;
    private String[] alleModi = {"1 Runde", "2 Richtige & Superzahl", "3 Richtige", "3 Richtige & Superzahl", "4 Richtige", "4 Richtige & Superzahl","5 Richtige", "5 Richtige & Superzahl","6 Richtige", "6 Richtige & Superzahl"};

    private int modusNummer = -1;
    // -1 = Kein Modus ausgewählt
    //  0 = 1 Runde
    //  1 = 2 Richtige & Superzahl
    //  2 = 3 Richtige
    //  3 = 3 Richtige & Superzahl
    //  4 = 4 Richtige
    //  5 = 4 Richtige & Superzahl
    //  6 = 5 Richtige
    //  7 = 5 Richtige & Superzahl
    //  8 = 6 Richtige
    //  9 = 6 Richtige & Superzahl

    double[] alleGewinne = {0.00, 5.00, 10.48, 20.95, 42.42, 190.90, 3340.68, 10022.03, 574596.53, 8949642.24};


    private TextView modusTextView;
    private Button playButton;
    private ArrayList<Integer> zufallsZahlen = new ArrayList<>();
    private int zufallsSuperzahl;

    // Boolean, der den Thread pausiert, falls der Nutzer auf Pause drückt
    private boolean killThread = false;

    private Random zufall = new Random();

    // Bei diesem Thread wird die Simulation gestartet
    private Thread simulationsThread;
    private Runnable simulateNumbers;
    private boolean simulationsThreadGestartet = false;



    private TextView nummer1TextView;
    private TextView nummer2TextView;
    private TextView nummer3TextView;
    private TextView nummer4TextView;
    private TextView nummer5TextView;
    private TextView nummer6TextView;
    private TextView nummerSuperzahlTextView;

    private ListView listView;
    private TipsListAdapter tipsListAdapter;

    private int durchgänge = 0;

    private int countRichtigeZahlen = 0;
    private int countRichtigeSuperzahl = 0;

    private Tips[] listeAllerTips;

    // Zahlen aus der getippten Liste nehmen
    private int nummer1;
    private int nummer2;
    private int nummer3;
    private int nummer4;
    private int nummer5;
    private int nummer6;
    private int nummerSuperzahl;

    // Boolean, um den Tipp in die Liste hinzuzufügen
    private boolean tippHinzufügen = false;

    // Listen zur Darstellung der Tipps in der Liste
    private Integer[] getippteZahlen = new Integer[6];
    private Boolean[] richtigeGetippteZahlen = new Boolean[6];
    private boolean richtigNummer1;
    private boolean richtigNummer2;
    private boolean richtigNummer3;
    private boolean richtigNummer4;
    private boolean richtigNummer5;
    private boolean richtigNummer6;
    private boolean richtigNummerSuperzahl;
    private String nameDesTipps;

    private ArrayList<TipsUebersicht> alleTips = new ArrayList<>();

    private String gewinner;

    private boolean mainloopCondition = true;

    // Gewinne, Kosten und Summe
    private TextView gewinneTextView;
    private TextView kostenTextView;
    private TextView summeTextView;

    // Versuche, Wochen und Jahre
    private TextView anzahlVersucheTextView;
    private TextView anzahlWochenTextView;
    private TextView anzahlJahreTextView;

    private int gewinn = 0;
    private double kostenGesamt = 0.0;
    private double summe = 0.0;

    private int anzahlWochen = 0;
    private double anzahlJahre = 0.0;

    // Anzahl aller Tipps
    private TextView anzahlTippsTextView;
    private int anzahlAllerTipps;
    private double kostenProTipp = 1.20;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.simulator_window);

        final Context context = getApplicationContext();

        // Alle Buttons und TextViews miteinander verknüpfen
        modusTextView = findViewById(R.id.modus_Vorschau_textView);
        modusTextView.setText("Kein Modus ausgewählt!");

        // Anzahl aller Tipps implementieren
        anzahlTippsTextView = findViewById(R.id.anzahlTipps);

        // Gewinn, Kosten und Summe implementieren
        gewinneTextView = findViewById(R.id.summeGewinn);
        kostenTextView = findViewById(R.id.summeKosten);
        summeTextView = findViewById(R.id.summeSumme);

        // ModusButton implementieren
        modusButton = findViewById(R.id.modus_button);

        modusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Simulator.this);

                builder.setTitle("Wähle einen Modus")
                        .setItems(alleModi, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                modusNummer = which;
                                modusTextView.setText(alleModi[which]);
                                gewinneTextView.setText(doubletoEuro(alleGewinne[which]));
                            }
                        })
                .show();
                builder.create();

            }
        });

        // TextView Wochen und Jahre
        anzahlWochenTextView = findViewById(R.id.wochen_counter_display);
        anzahlJahreTextView = findViewById(R.id.jahre_counter_display);

        // TextView für Anzahl der Versuche implementieren
        anzahlVersucheTextView = findViewById(R.id.versuche_counter_display);

        // TextViews für die Zahlen im Simulator implementieren

        nummer1TextView = findViewById(R.id.simulation_nummer1);
        nummer2TextView = findViewById(R.id.simulation_nummer2);
        nummer3TextView = findViewById(R.id.simulation_nummer3);
        nummer4TextView = findViewById(R.id.simulation_nummer4);
        nummer5TextView = findViewById(R.id.simulation_nummer5);
        nummer6TextView = findViewById(R.id.simulation_nummer6);
        nummerSuperzahlTextView = findViewById(R.id.simulation_nummerSuperzahl);



        // Alle getippten Zahlen auslesen
        Gson gson = new Gson();

        String json = jsonFileLesen("mytips.json");
        listeAllerTips = gson.fromJson(json, Tips[].class);

        // Anzahl aller Tipps errechnen
        anzahlAllerTipps = listeAllerTips.length;
        anzahlTippsTextView.setText(anzahlAllerTipps);

        // Gesamtkosten berechnen
        kostenGesamt = anzahlAllerTipps * kostenProTipp;
        kostenTextView.setText(doubletoEuro(kostenGesamt));



        // PlayButton implementieren
        playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listeAllerTips.length == 0) {

                    AlertDialog.Builder alertDialogKeineTipps = new AlertDialog.Builder(Simulator.this);
                    alertDialogKeineTipps
                            .setTitle("Simulation kann nicht gestartet werden!")
                            .setCancelable(false)
                            .setMessage("Sie haben noch keine Tipps erstellt, die bei dieser Simulation verglichen werden sollen.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Keine Funktion notwendig

                                }
                            });

                    AlertDialog alertDialogfürKeineTipps = alertDialogKeineTipps.create();
                    alertDialogfürKeineTipps.show();

                }
                else if (modusNummer == -1) {
                    AlertDialog.Builder alertDialogKeinModus = new AlertDialog.Builder(Simulator.this);
                    alertDialogKeinModus
                            .setTitle("Simulation kann nicht gestartet werden!")
                            .setCancelable(false)
                            .setMessage("Sie haben noch keinen Modus ausgewählt.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Keine Funktion notwendig

                                }
                            });

                    AlertDialog alertDialogfürKeinModus = alertDialogKeinModus.create();
                    alertDialogfürKeinModus.show();
                }

                else if (simulationsThreadGestartet == false) {
                    playButton.setActivated(true);
                    killThread = false;
                    simulationsThread = new Thread(simulateNumbers);
                    simulationsThread.start();
                    playButton.setText("PAUSE");
                } else {
                    playButton.setActivated(false);
                    killThread = true;
                    simulationsThread.interrupt();
                    playButton.setText("START");
                }

            }
        });

        // ListView implementieren
        listView = findViewById(R.id.listviewMyTips);




        simulateNumbers = new Runnable() {
            @Override
            public void run() {

                simulationsThreadGestartet = true;  // Simulationsthread wurde gestartet

                mainloopCondition = true;


                // Alles zurücksetzen, falls nochmal gestartet wird
                alleTips.clear();
                durchgänge = 0;

                    while (mainloopCondition) {

                        // Zufallszahlen werden generiert
                        zufallsZahlen = gezogeneZahlen();
                        zufallsSuperzahl = zufall.nextInt(10);


                        // Zahlen auf dem User Interface erscheinen lassen

                        if ((durchgänge % 2000) == 0) {
                            nummer1TextView.setText(zufallsZahlen.get(0) + "");
                            nummer2TextView.setText(zufallsZahlen.get(1) + "");
                            nummer3TextView.setText(zufallsZahlen.get(2) + "");
                            nummer4TextView.setText(zufallsZahlen.get(3) + "");
                            nummer5TextView.setText(zufallsZahlen.get(4) + "");
                            nummer6TextView.setText(zufallsZahlen.get(5) + "");
                            nummerSuperzahlTextView.setText(zufallsSuperzahl + "");
                        }

                        // Versuche anzeigen

                        anzahlVersucheTextView.setText(String.format("%,d", ++durchgänge) + "");
                        System.out.println("Anzahl der Durchgänge: " + durchgänge);
                        System.out.println("Ich befinde mich in der Hauptschleife");


                        // Zufallszahlen werden mit getippten Zahlen verglichen

                        for (int i=0; i < listeAllerTips.length; i++) {

                            System.out.println("Ich befinde mich in der 1. inneren Schleife");
                            // Alle Tipps lesen (1 Schleifendurchlauf ist ein Tipp)
                            nameDesTipps = listeAllerTips[i].getName();
                            nummer1 = listeAllerTips[i].getNumber1();
                            nummer2 = listeAllerTips[i].getNumber2();
                            nummer3 = listeAllerTips[i].getNumber3();
                            nummer4 = listeAllerTips[i].getNumber4();
                            nummer5 = listeAllerTips[i].getNumber5();
                            nummer6 = listeAllerTips[i].getNumber6();
                            nummerSuperzahl = listeAllerTips[i].getNumberSuperzahl();

                            countRichtigeZahlen = 0;
                            countRichtigeSuperzahl = 0;

                            richtigNummer1 = false;
                            richtigNummer2 = false;
                            richtigNummer3 = false;
                            richtigNummer4 = false;
                            richtigNummer5 = false;
                            richtigNummer6 = false;
                            richtigNummerSuperzahl = false;


                            // Prüfen, ob die jeweiligen Zahlen richtig sind
                            for (int j = 0; j < 6; j++) {

                                System.out.println("Ich befinde mich in der 2. inneren Schleife");

                                if (nummer1 == zufallsZahlen.get(j)) {
                                    ++countRichtigeZahlen;
                                    richtigNummer1 = true;
                                }
                                if (nummer2 == zufallsZahlen.get(j)) {
                                    ++countRichtigeZahlen;
                                    richtigNummer2 = true;
                                }
                                if (nummer3 == zufallsZahlen.get(j)) {
                                    ++countRichtigeZahlen;
                                    richtigNummer3 = true;
                                }
                                if (nummer4 == zufallsZahlen.get(j)) {
                                    ++countRichtigeZahlen;
                                    richtigNummer4 = true;
                                }
                                if (nummer5 == zufallsZahlen.get(j)) {
                                    ++countRichtigeZahlen;
                                    richtigNummer5 = true;
                                }
                                if (nummer6 == zufallsZahlen.get(j)) {
                                    ++countRichtigeZahlen;
                                    richtigNummer6 = true;
                                }
                            }

                            if (nummerSuperzahl == zufallsSuperzahl) {
                                ++countRichtigeSuperzahl;
                                richtigNummerSuperzahl = true;
                            }

                            // -1 = Kein Modus ausgewählt
                            //  0 = 1 Runde
                            //  1 = 2 Richtige & Superzahl
                            //  2 = 3 Richtige
                            //  3 = 3 Richtige & Superzahl
                            //  4 = 4 Richtige
                            //  5 = 4 Richtige & Superzahl
                            //  6 = 5 Richtige
                            //  7 = 5 Richtige & Superzahl
                            //  8 = 6 Richtige
                            //  9 = 6 Richtige & Superzahl

                            tippHinzufügen = false;


                            switch(modusNummer) {

                                case 0:
                                    tippHinzufügen = true;
                                    break;
                                case 1:
                                    if (countRichtigeZahlen >= 2 && countRichtigeSuperzahl == 1)
                                        tippHinzufügen = true;
                                    break;
                                case 2:
                                    if (countRichtigeZahlen >= 3)
                                        tippHinzufügen = true;
                                    break;
                                case 3:
                                    if (countRichtigeZahlen >= 3 && countRichtigeSuperzahl == 1)
                                        tippHinzufügen = true;
                                    break;
                                case 4:
                                    if (countRichtigeZahlen >= 4)
                                        tippHinzufügen = true;
                                    break;
                                case 5:
                                    if (countRichtigeZahlen >= 4 && countRichtigeSuperzahl == 1)
                                        tippHinzufügen = true;
                                    break;
                                case 6:
                                    if (countRichtigeZahlen >= 5)
                                        tippHinzufügen = true;
                                    break;
                                case 7:
                                    if (countRichtigeZahlen >= 5 && countRichtigeSuperzahl == 1)
                                        tippHinzufügen = true;
                                    break;
                                case 8:
                                    if (countRichtigeZahlen == 6)
                                        tippHinzufügen = true;
                                    break;
                                case 9:
                                    if (countRichtigeZahlen == 6 && countRichtigeSuperzahl == 1)
                                        tippHinzufügen = true;
                                    break;
                                default:
                                    System.out.println("Das kann nicht sein. Modusnummer: " + modusNummer);
                                    break;
                            }

                            System.out.println("Ich bin nicht mehr in Switch Case, aber immer noch in der Schleife");

                            if (tippHinzufügen == true){

                                getippteZahlen[0] = nummer1;
                                getippteZahlen[1] = nummer2;
                                getippteZahlen[2] = nummer3;
                                getippteZahlen[3] = nummer4;
                                getippteZahlen[4] = nummer5;
                                getippteZahlen[5] = nummer6;

                                richtigeGetippteZahlen[0] = richtigNummer1;
                                richtigeGetippteZahlen[1] = richtigNummer2;
                                richtigeGetippteZahlen[2] = richtigNummer3;
                                richtigeGetippteZahlen[3] = richtigNummer4;
                                richtigeGetippteZahlen[4] = richtigNummer5;
                                richtigeGetippteZahlen[5] = richtigNummer6;

                                TipsUebersicht listeUebersicht = new TipsUebersicht(getippteZahlen, richtigeGetippteZahlen, nummerSuperzahl, richtigNummerSuperzahl, countRichtigeZahlen, nameDesTipps);
                                alleTips.add(listeUebersicht);
                            }

                        }

                        // Es kommt nur dann etwas in die Liste, wenn mind. 1 Tipp den Anforderungen(Modus) entspricht
                        if (!(alleTips.isEmpty())) {

                            // Hier die zufallsZahlen ausgeben, da nicht bei jedem Tipp die Zahl geändert werden kann
                            nummer1TextView.setText(zufallsZahlen.get(0) + "");
                            nummer2TextView.setText(zufallsZahlen.get(1) + "");
                            nummer3TextView.setText(zufallsZahlen.get(2) + "");
                            nummer4TextView.setText(zufallsZahlen.get(3) + "");
                            nummer5TextView.setText(zufallsZahlen.get(4) + "");
                            nummer6TextView.setText(zufallsZahlen.get(5) + "");
                            nummerSuperzahlTextView.setText(zufallsSuperzahl + "");

                            System.out.println("Ich habe einen Gewinner gefunden!");

                           // Alle richtigen Tipps der Liste übergeben
                            tipsListAdapter = new TipsListAdapter(context, R.layout.custom_row, alleTips);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    listView.setAdapter(tipsListAdapter);
                                }
                            });



                            /*

                            // Namen für den Alertdialog herausfiltern
                            for (int i = 0; i < alleTips.size(); i++) {
                                gewinner = alleTips.get(i).getTippName() + "\n";
                            }

                            // Anzeigen, wer alles gewonnen hat
                            AlertDialog.Builder alertDialogGewinner = new AlertDialog.Builder(context);
                            alertDialogGewinner
                                    .setTitle("Gewonnen!")
                                    .setCancelable(false)
                                    .setMessage("Die Gewinner sind: \n" + gewinner)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Keine Funktion notwendig
                                        }
                                    });

                            AlertDialog alertDialogfürGewinner = alertDialogGewinner.create();
                            alertDialogfürGewinner.show();


                             */
                            // Aus der Hauptschleife gehen, da richtige Tipps gefunden wurden
                            simulationsThreadGestartet = false;
                            mainloopCondition = false;
                            playButton.setText("START");
                        }

                        if (killThread) {
                            System.out.println("Der Nutzer hat die Simulation gestoppt --> Abbruch der Mainschleife");
                            simulationsThreadGestartet = false;
                            mainloopCondition = false;
                        }
                    }

                System.out.println("Ich bin aus der Mainschleife gekommen");
                }
        };








        // Navigation Bar implementieren
        BottomNavigationView bottomNavigationView = findViewById(R.id.NavigationbarBottom);

        // Simulator Nav Button aktivieren
        bottomNavigationView.setSelectedItemId(R.id.simulator_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
    }

    public ArrayList<Integer> gezogeneZahlen() {

        ArrayList<Integer> liste = new ArrayList<>();
        Random random = new Random();
        int gezogeneZahl;

        while (true) {

            gezogeneZahl = random.nextInt(49) + 1;  // 0 inclusive, bound (49) exclusive --> solution: +1

            if (liste.contains(gezogeneZahl)) {     // Neue Zahl generieren, falls bereits enthalten
                continue;
            } else {                                // Falls noch nicht vorhanden, dann hinzufügen
                liste.add(gezogeneZahl);
            }

            if (liste.size() == 6) {                // Falls die Liste 6 Zahlen enthält, dann hat die Funktion ihre Aufgabe erfüllt
                break;
            }
        }
        // Zahlen der Größe nach sortieren
        Collections.sort(liste);

        return liste;

    }

    // Variable für die NavBar
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch(menuItem.getItemId()) {

                        case R.id.home_nav:
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            overridePendingTransition(0, 0);
                            return true;
                        case R.id.simulator_nav:
                            return true;
                    }
                    return false;
                }
            };

    public String jsonFileLesen(String name) {
        Context context = getApplicationContext();

        File jsonFile = new File(context.getFilesDir(), name);
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

    public String doubletoEuro (double wert) {
        String country = "DE";
        String language = "de";
        String euroWert = NumberFormat.getCurrencyInstance(Locale.GERMANY).format(wert);

        return euroWert;
    }

}

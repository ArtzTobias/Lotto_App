package com.example.lotto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.VideoView;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pop extends Activity {

    //VideoView videoviewbutton1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popwindow);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout(width, height);



        //videoviewbutton1 = (VideoView) findViewById(R.id.videoViewbutton1);

        /*
        ScrollView scrollview = (ScrollView) findViewById(R.id.scrollView);
        LinearLayout container = (LinearLayout) scrollview.findViewById(R.id.container);
        container.removeAllViews();
        */
        final boolean[] activation = new boolean[50];
        Arrays.fill(activation, Boolean.FALSE);

        final boolean[] activationSuperzahl = new boolean[10];
        Arrays.fill(activationSuperzahl, Boolean.FALSE);

        final int[] counterZahl = {6};
        final int[] counterSuperzahl = {1};

        final ArrayList<Integer> chosenNumbers = new ArrayList<>();
        final int[] supernumber = {0};

        String[] mythologie = new String[100];

        final EditText tippname = findViewById(R.id.tippName);
        tippname.setText("Medusa");

        /* Initialisierung von
                Cancel Button
                Speichern Button
         */

        final Button speichern = findViewById(R.id.buttonSpeichern);
        speichern.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if(counterZahl[0] != 0 && counterSuperzahl[0] == 1) {
                    Snackbar.make(v, "Wähle noch " + counterZahl[0] + " Zahlen und eine Superzahl aus", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else if (counterZahl[0] != 0 && counterSuperzahl[0] == 0) {
                    Snackbar.make(v, "Wähle noch " + counterZahl[0] + " Zahlen aus", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else if (counterZahl[0] == 0 && counterSuperzahl[0] == 1) {
                    Snackbar.make(v, "Wähle noch eine Superzahl aus", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else if (tippname.getText().equals("")) {
                    Snackbar.make(v, "Wähle einen Namen", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else if (tippname.getText().length() > 20) {
                    Snackbar.make(v, "Der Name darf nur 20 Zeichen lang sein", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else {

                    for (int i = 1; i < 50; i++) {
                        if (activation[i] == true) {
                            chosenNumbers.add(i);
                        }
                    }

                    for (int i = 0; i < 10; i++) {
                        if (activationSuperzahl[i] == true) {
                            supernumber[0] = i;
                        }
                    }

                    // File3 (Junkfile) inhalt löschen, da ein neuer tipp abgegeben wird
                    /*
                    final Context context = getApplicationContext();
                    final String junkFile = "junkFile";
                    File fileForDeletedButton = new File(context.getFilesDir(), junkFile);

                    try {
                        //Leeren der File3

                        PrintWriter printwriter = new PrintWriter(fileForDeletedButton);
                        printwriter.print("");
                        printwriter.close();

                        System.out.println("ICH HABE DEN INHALT DER FILE3 GELÖSCHT (in class POP)");

                    } catch (FileNotFoundException e) {
                        System.out.println("File3 zum leeren des Inhalts nicht gefunden (in class POP)");
                    }

                     */

                    // Mit diesem Intent kann man Daten an die andere Klasse übertrgen (bisher unnötig, da alles in Singleton)

                    Intent myIntent = new Intent(Pop.this, MainActivity.class);



                    // JSON

                    // Name vorbereiten

                    String nameTipp = tippname.getText().toString();
                    // Alle Zahlen vorbereiten

                    int zahl1 = chosenNumbers.get(0);
                    int zahl2 = chosenNumbers.get(1);
                    int zahl3 = chosenNumbers.get(2);
                    int zahl4 = chosenNumbers.get(3);
                    int zahl5 = chosenNumbers.get(4);
                    int zahl6 = chosenNumbers.get(5);
                    int superzahl = supernumber[0];

                    // ID erstellen

                    int size = 0;
                    int id = 0;

                    // JSON File mithilfe von Gson lesen und schreiben
                    /*
                    try {


                        File file = new File(context.getFilesDir(), "mytips.json");
                        // Um die File zu leeren


                         FileWriter fileWriter2 = new FileWriter(file, false);
                            BufferedWriter bufferedWriter2 = new BufferedWriter(fileWriter2);
                            bufferedWriter2.write("");
                        bufferedWriter2.close();






                        System.out.println("Alles wurde erfolgreich gespeichert");

                    }catch(IOException e) {
                        System.out.println("IOException bei der JSON (in Pop (1628)): " + e.toString());
                    }


                     */


                    Gson gson = new Gson();

                    File jsonFile = getFileStreamPath("mytips.json");

                    if (jsonFile.length() == 0) {
                        // JSON File ist leer oder existiert nicht

                        List<Tips> alleTips = new ArrayList<>();
                        alleTips.add(new Tips(0, nameTipp, zahl1, zahl2, zahl3, zahl4, zahl5, zahl6, superzahl));

                        String jsonText = gson.toJson(alleTips);

                        System.out.println("Bei leerer File wird das hinzugefügt: " + jsonText);

                        add_to_json(jsonText);

                    } else {
                        // File existiert und ist nicht leer
                        String json = jsonFileLesen("mytips.json");
                        Tips[] listeAllerTips = gson.fromJson(json, Tips[].class);

                        List<Tips> alleTips = new ArrayList<>();

                        for (int i = 0; i < listeAllerTips.length; i++) {
                            alleTips.add(listeAllerTips[i]);
                        }

                        alleTips.add(new Tips(listeAllerTips.length, nameTipp,zahl1, zahl2, zahl3, zahl4, zahl5, zahl6, superzahl));

                        String jsonText = gson.toJson(alleTips);

                        System.out.println("Das soll in die JSON geschrieben werden: " + jsonText);

                        add_to_json(jsonText);


                    }



                    myIntent.putExtra("key", "Zahlen: " + chosenNumbers.get(0) + "," + chosenNumbers.get(1) + "," + chosenNumbers.get(2) + "," + chosenNumbers.get(3) + "," + chosenNumbers.get(4) + "," + chosenNumbers.get(5) + "   Superzahl: " + supernumber[0]);
                    Pop.this.startActivity(myIntent);

                    /*

                    MyTotalListView myList = new MyTotalListView();

                    MainActivity.LottoAdapter lottoadapter = new MainActivity.LottoAdapter(getApplicationContext(), R.layout.row, chosenNumbers);

                    LinearLayout linearLayout = myList.getCustomLayout();

                    View view = lottoadapter.getView(0, null, linearLayout);

                    myList.addtolist(view);



                    //myView.addtolist(listView);

                    */








                    //myList.addtolist(view);

                    //chosenNumbers.clear();
                }
            }
        });


        final Button cancel = findViewById(R.id.buttonCancel);
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*
            Initialisierung von
                        1 - 49 Button
         */

        final Button one = findViewById(R.id.button1);
        one.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[1] == false && counterZahl[0] > 0) {
                    //one.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[1] = true;
                    one.setActivated(true);
                } else if (activation[1] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //one.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[1] = false;
                    one.setActivated(false);
                }
            }
        });

        final Button two = findViewById(R.id.button2);
        two.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[2] == false && counterZahl[0] > 0) {
                    //two.setBackgroundResource(R.drawable.goldmini);;
                    counterZahl[0]--;
                    activation[2] = true;
                    two.setActivated(true);
                } else if (activation[2] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //two.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[2] = false;
                    two.setActivated(false);
                }
            }
        });

        final Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[3] == false && counterZahl[0] > 0) {
                    //button3.setBackgroundResource(R.drawable.goldmini);;
                    counterZahl[0]--;
                    activation[3] = true;
                    button3.setActivated(true);
                } else if (activation[3] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button3.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[3] = false;
                    button3.setActivated(false);
                }
            }
        });

        final Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[4] == false && counterZahl[0] > 0) {
                    //button4.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[4] = true;
                    button4.setActivated(true);
                } else if (activation[4] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button4.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[4] = false;
                    button4.setActivated(false);
                }
            }
        });

        final Button button5 = findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[5] == false && counterZahl[0] > 0) {
                    //button5.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[5] = true;
                    button5.setActivated(true);
                } else if (activation[5] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button5.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[5] = false;
                    button5.setActivated(false);
                }
            }
        });

        final Button button6 = findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[6] == false && counterZahl[0] > 0) {
                    //button6.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[6] = true;
                    button6.setActivated(true);
                } else if (activation[6] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button6.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[6] = false;
                    button6.setActivated(false);
                }
            }
        });

        final Button button7 = findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[7] == false && counterZahl[0] > 0) {
                    //button7.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[7] = true;
                    button7.setActivated(true);
                } else if (activation[7] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button7.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[7] = false;
                    button7.setActivated(false);
                }
            }
        });

        final Button button8 = findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[8] == false && counterZahl[0] > 0) {
                    //button8.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[8] = true;
                    button8.setActivated(true);
                } else if (activation[8] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button8.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[8] = false;
                    button8.setActivated(false);
                }
            }
        });

        final Button button9 = findViewById(R.id.button9);
        button9.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[9] == false && counterZahl[0] > 0) {
                    //button9.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[9] = true;
                    button9.setActivated(true);
                } else if (activation[9] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button9.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[9] = false;
                    button9.setActivated(false);
                }
            }
        });

        final Button button10 = findViewById(R.id.button10);
        button10.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[10] == false && counterZahl[0] > 0) {
                    //button10.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[10] = true;
                    button10.setActivated(true);
                } else if (activation[10] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button10.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[10] = false;
                    button10.setActivated(false);
                }
            }
        });


        final Button button11 = findViewById(R.id.button11);
        button11.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[11] == false && counterZahl[0] > 0) {
                    //button11.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[11] = true;
                    button11.setActivated(true);
                } else if (activation[11] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button11.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[11] = false;
                    button11.setActivated(false);
                }
            }
        });


        final Button button12 = findViewById(R.id.button12);
        button12.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[12] == false && counterZahl[0] > 0) {
                    //button12.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[12] = true;
                    button12.setActivated(true);
                } else if (activation[12] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button12.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[12] = false;
                    button12.setActivated(false);
                }
            }
        });

        final Button button13 = findViewById(R.id.button13);
        button13.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[13] == false && counterZahl[0] > 0) {
                    //button13.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[13] = true;
                    button13.setActivated(true);
                } else if (activation[13] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button13.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[13] = false;
                    button13.setActivated(false);
                }
            }
        });

        final Button button14 = findViewById(R.id.button14);
        button14.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[14] == false && counterZahl[0] > 0) {
                    //button14.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[14] = true;
                    button14.setActivated(true);
                } else if (activation[14] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button14.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[14] = false;
                    button14.setActivated(false);
                }
            }
        });

        final Button button15 = findViewById(R.id.button15);
        button15.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[15] == false && counterZahl[0] > 0) {
                    //button15.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[15] = true;
                    button15.setActivated(true);
                } else if (activation[15] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button15.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[15] = false;
                    button15.setActivated(false);
                }
            }
        });

        final Button button16 = findViewById(R.id.button16);
        button16.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[16] == false && counterZahl[0] > 0) {
                    //button16.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[16] = true;
                    button16.setActivated(true);
                } else if (activation[16] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button16.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[16] = false;
                    button16.setActivated(false);
                }
            }
        });

        final Button button17 = findViewById(R.id.button17);
        button17.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[17] == false && counterZahl[0] > 0) {
                    //button17.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[17] = true;
                    button17.setActivated(true);
                } else if (activation[17] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button17.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[17] = false;
                    button17.setActivated(false);
                }
            }
        });

        final Button button18 = findViewById(R.id.button18);
        button18.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[18] == false && counterZahl[0] > 0) {
                    //button18.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[18] = true;
                    button18.setActivated(true);
                } else if (activation[18] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button18.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[18] = false;
                    button18.setActivated(false);
                }
            }
        });

        final Button button19 = findViewById(R.id.button19);
        button19.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[19] == false && counterZahl[0] > 0) {
                    //button19.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[19] = true;
                    button19.setActivated(true);
                } else if (activation[19] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button19.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[19] = false;
                    button19.setActivated(false);
                }
            }
        });

        final Button button20 = findViewById(R.id.button20);
        button20.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[20] == false && counterZahl[0] > 0) {
                    //button20.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[20] = true;
                    button20.setActivated(true);
                } else if (activation[20] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button20.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[20] = false;
                    button20.setActivated(false);
                }
            }
        });

        final Button button21 = findViewById(R.id.button21);
        button21.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[21] == false && counterZahl[0] > 0) {
                    //button21.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[21] = true;
                    button21.setActivated(true);
                } else if (activation[21] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button21.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[21] = false;
                    button21.setActivated(false);
                }
            }
        });

        final Button button22 = findViewById(R.id.button22);
        button22.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[22] == false && counterZahl[0] > 0) {
                    //button22.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[22] = true;
                    button22.setActivated(true);
                } else if (activation[22] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button22.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[22] = false;
                    button22.setActivated(false);
                }
            }
        });

        final Button button23 = findViewById(R.id.button23);
        button23.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[23] == false && counterZahl[0] > 0) {
                    //button23.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[23] = true;
                    button23.setActivated(true);
                } else if (activation[23] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button23.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[23] = false;
                    button23.setActivated(false);
                }
            }
        });

        final Button button24 = findViewById(R.id.button24);
        button24.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[24] == false && counterZahl[0] > 0) {
                    //button24.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[24] = true;
                    button24.setActivated(true);
                } else if (activation[24] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button24.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[24] = false;
                    button24.setActivated(false);
                }
            }
        });

        final Button button25 = findViewById(R.id.button25);
        button25.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[25] == false && counterZahl[0] > 0) {
                    //button25.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[25] = true;
                    button25.setActivated(true);
                } else if (activation[25] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button25.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[25] = false;
                    button25.setActivated(false);
                }
            }
        });

        final Button button26 = findViewById(R.id.button26);
        button26.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[26] == false && counterZahl[0] > 0) {
                    //button26.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[26] = true;
                    button26.setActivated(true);
                } else if (activation[26] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button26.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[26] = false;
                    button26.setActivated(false);
                }
            }
        });

        final Button button27 = findViewById(R.id.button27);
        button27.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[27] == false && counterZahl[0] > 0) {
                    //button27.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[27] = true;
                    button27.setActivated(true);
                } else if (activation[27] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button27.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[27] = false;
                    button27.setActivated(false);
                }
            }
        });

        final Button button28 = findViewById(R.id.button28);
        button28.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[28] == false && counterZahl[0] > 0) {
                    //button28.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[28] = true;
                    button28.setActivated(true);
                } else if (activation[28] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button28.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[28] = false;
                    button28.setActivated(false);
                }
            }
        });

        final Button button29 = findViewById(R.id.button29);
        button29.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[29] == false && counterZahl[0] > 0) {
                    //button29.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[29] = true;
                    button29.setActivated(true);
                } else if (activation[29] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button29.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[29] = false;
                    button29.setActivated(false);
                }
            }
        });

        final Button button30 = findViewById(R.id.button30);
        button30.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[30] == false && counterZahl[0] > 0) {
                    //button30.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[30] = true;
                    button30.setActivated(true);
                } else if (activation[30] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button30.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[30] = false;
                    button30.setActivated(false);
                }
            }
        });

        final Button button31 = findViewById(R.id.button31);
        button31.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[31] == false && counterZahl[0] > 0) {
                    //button31.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[31] = true;
                    button31.setActivated(true);
                } else if (activation[31] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button31.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[31] = false;
                    button31.setActivated(false);
                }
            }
        });

        final Button button32 = findViewById(R.id.button32);
        button32.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[32] == false && counterZahl[0] > 0) {
                    //button32.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[32] = true;
                    button32.setActivated(true);
                } else if (activation[32] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button32.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[32] = false;
                    button32.setActivated(false);
                }
            }
        });

        final Button button33 = findViewById(R.id.button33);
        button33.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[33] == false && counterZahl[0] > 0) {
                    //button33.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[33] = true;
                    button33.setActivated(true);
                } else if (activation[33] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button33.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[33] = false;
                    button33.setActivated(false);
                }
            }
        });

        final Button button34 = findViewById(R.id.button34);
        button34.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[34] == false && counterZahl[0] > 0) {
                    //button34.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[34] = true;
                    button34.setActivated(true);
                } else if (activation[34] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button34.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[34] = false;
                    button34.setActivated(false);
                }
            }
        });

        final Button button35 = findViewById(R.id.button35);
        button35.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[35] == false && counterZahl[0] > 0) {
                    //button35.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[35] = true;
                    button35.setActivated(true);
                } else if (activation[35] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button35.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[35] = false;
                    button35.setActivated(false);
                }
            }
        });

        final Button button36 = findViewById(R.id.button36);
        button36.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[36] == false && counterZahl[0] > 0) {
                    //button36.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[36] = true;
                    button36.setActivated(true);
                } else if (activation[36] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button36.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[36] = false;
                    button36.setActivated(false);
                }
            }
        });

        final Button button37 = findViewById(R.id.button37);
        button37.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[37] == false && counterZahl[0] > 0) {
                    //button37.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[37] = true;
                    button37.setActivated(true);
                } else if (activation[37] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button37.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[37] = false;
                    button37.setActivated(false);
                }
            }
        });

        final Button button38 = findViewById(R.id.button38);
        button38.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[38] == false && counterZahl[0] > 0) {
                    //button38.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[38] = true;
                    button38.setActivated(true);
                } else if (activation[38] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button38.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[38] = false;
                    button38.setActivated(false);
                }
            }
        });

        final Button button39 = findViewById(R.id.button39);
        button39.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[39] == false && counterZahl[0] > 0) {
                    //button39.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[39] = true;
                    button39.setActivated(true);
                } else if (activation[39] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button39.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[39] = false;
                    button39.setActivated(false);
                }
            }
        });

        final Button button40 = findViewById(R.id.button40);
        button40.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[40] == false && counterZahl[0] > 0) {
                    //button40.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[40] = true;
                    button40.setActivated(true);
                } else if (activation[40] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button40.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[40] = false;
                    button40.setActivated(false);
                }
            }
        });

        final Button button41 = findViewById(R.id.button41);
        button41.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[41] == false && counterZahl[0] > 0) {
                    //button41.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[41] = true;
                    button41.setActivated(true);
                } else if (activation[41] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button41.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[41] = false;
                    button41.setActivated(false);
                }
            }
        });

        final Button button42 = findViewById(R.id.button42);
        button42.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[42] == false && counterZahl[0] > 0) {
                    //button42.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[42] = true;
                    button42.setActivated(true);
                } else if (activation[42] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button42.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[42] = false;
                    button42.setActivated(false);
                }
            }
        });

        final Button button43 = findViewById(R.id.button43);
        button43.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[43] == false && counterZahl[0] > 0) {
                    //button43.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[43] = true;
                    button43.setActivated(true);
                } else if (activation[43] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button43.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[43] = false;
                    button43.setActivated(false);
                }
            }
        });

        final Button button44 = findViewById(R.id.button44);
        button44.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[44] == false && counterZahl[0] > 0) {
                    //button44.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[44] = true;
                    button44.setActivated(true);
                } else if (activation[44] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button44.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[44] = false;
                    button44.setActivated(false);
                }
            }
        });

        final Button button45 = findViewById(R.id.button45);
        button45.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[45] == false && counterZahl[0] > 0) {
                    //button45.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[45] = true;
                    button45.setActivated(true);
                } else if (activation[45] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button45.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[45] = false;
                    button45.setActivated(false);
                }
            }
        });

        final Button button46 = findViewById(R.id.button46);
        button46.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[46] == false && counterZahl[0] > 0) {
                    //button46.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[46] = true;
                    button46.setActivated(true);
                } else if (activation[46] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button46.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[46] = false;
                    button46.setActivated(false);
                }
            }
        });

        final Button button47 = findViewById(R.id.button47);
        button47.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[47] == false && counterZahl[0] > 0) {
                    //button47.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[47] = true;
                    button47.setActivated(true);
                } else if (activation[47] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button47.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[47] = false;
                    button47.setActivated(false);
                }
            }
        });

        final Button button48 = findViewById(R.id.button48);
        button48.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[48] == false && counterZahl[0] > 0) {
                    //button48.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[48] = true;
                    button48.setActivated(true);
                } else if (activation[48] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button48.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[48] = false;
                    button48.setActivated(false);
                }
            }
        });

        final Button button49 = findViewById(R.id.button49);
        button49.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activation[49] == false && counterZahl[0] > 0) {
                    //button49.setBackgroundResource(R.drawable.goldmini);
                    counterZahl[0]--;
                    activation[49] = true;
                    button49.setActivated(true);
                } else if (activation[49] == false && counterZahl[0] == 0) {
                    Snackbar.make(v, "Es wurden bereits 6 Zahlen gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button49.setBackgroundResource(android.R.drawable.btn_default);
                    counterZahl[0]++;
                    activation[49] = false;
                    button49.setActivated(false);
                }
            }
        });



        //Superzahlen 0-9

        final Button button0s = findViewById(R.id.button0s);
        button0s.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activationSuperzahl[0] == false && counterSuperzahl[0] > 0) {
                    //button0s.setBackgroundResource(R.drawable.diamondmini);
                    counterSuperzahl[0]--;
                    activationSuperzahl[0] = true;
                    button0s.setActivated(true);
                } else if (activationSuperzahl[0] == false && counterSuperzahl[0] == 0) {
                    Snackbar.make(v, "Es wurde bereits eine Superzahl gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button0s.setBackgroundResource(android.R.drawable.btn_default);
                    counterSuperzahl[0]++;
                    activationSuperzahl[0] = false;
                    button0s.setActivated(false);
                }
            }
        });

        final Button button1s = findViewById(R.id.button1s);
        button1s.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activationSuperzahl[1] == false && counterSuperzahl[0] > 0) {
                    //button1s.setBackgroundResource(R.drawable.diamondmini);
                    counterSuperzahl[0]--;
                    activationSuperzahl[1] = true;
                    button1s.setActivated(true);
                } else if (activationSuperzahl[1] == false && counterSuperzahl[0] == 0) {
                    Snackbar.make(v, "Es wurde bereits eine Superzahl gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button1s.setBackgroundResource(android.R.drawable.btn_default);
                    counterSuperzahl[0]++;
                    activationSuperzahl[1] = false;
                    button1s.setActivated(false);
                }
            }
        });

        final Button button2s = findViewById(R.id.button2s);
        button2s.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activationSuperzahl[2] == false && counterSuperzahl[0] > 0) {
                    //button2s.setBackgroundResource(R.drawable.diamondmini);
                    counterSuperzahl[0]--;
                    activationSuperzahl[2] = true;
                    button2s.setActivated(true);
                } else if (activationSuperzahl[2] == false && counterSuperzahl[0] == 0) {
                    Snackbar.make(v, "Es wurde bereits eine Superzahl gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button2s.setBackgroundResource(android.R.drawable.btn_default);
                    counterSuperzahl[0]++;
                    activationSuperzahl[2] = false;
                    button2s.setActivated(false);
                }
            }
        });

        final Button button3s = findViewById(R.id.button3s);
        button3s.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activationSuperzahl[3] == false && counterSuperzahl[0] > 0) {
                    //button3s.setBackgroundResource(R.drawable.diamondmini);
                    counterSuperzahl[0]--;
                    activationSuperzahl[3] = true;
                    button3s.setActivated(true);
                } else if (activationSuperzahl[3] == false && counterSuperzahl[0] == 0) {
                    Snackbar.make(v, "Es wurde bereits eine Superzahl gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button3s.setBackgroundResource(android.R.drawable.btn_default);
                    counterSuperzahl[0]++;
                    activationSuperzahl[3] = false;
                    button3s.setActivated(false);
                }
            }
        });

        final Button button4s = findViewById(R.id.button4s);
        button4s.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activationSuperzahl[4] == false && counterSuperzahl[0] > 0) {
                    //button4s.setBackgroundResource(R.drawable.diamondmini);
                    counterSuperzahl[0]--;
                    activationSuperzahl[4] = true;
                    button4s.setActivated(true);
                } else if (activationSuperzahl[4] == false && counterSuperzahl[0] == 0) {
                    Snackbar.make(v, "Es wurde bereits eine Superzahl gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button4s.setBackgroundResource(android.R.drawable.btn_default);
                    counterSuperzahl[0]++;
                    activationSuperzahl[4] = false;
                    button4s.setActivated(false);
                }
            }
        });

        final Button button5s = findViewById(R.id.button5s);
        button5s.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activationSuperzahl[5] == false && counterSuperzahl[0] > 0) {
                    //button5s.setBackgroundResource(R.drawable.diamondmini);
                    counterSuperzahl[0]--;
                    activationSuperzahl[5] = true;
                    button5s.setActivated(true);
                } else if (activationSuperzahl[5] == false && counterSuperzahl[0] == 0) {
                    Snackbar.make(v, "Es wurde bereits eine Superzahl gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button5s.setBackgroundResource(android.R.drawable.btn_default);
                    counterSuperzahl[0]++;
                    activationSuperzahl[5] = false;
                    button5s.setActivated(false);
                }
            }
        });

        final Button button6s = findViewById(R.id.button6s);
        button6s.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activationSuperzahl[6] == false && counterSuperzahl[0] > 0) {
                    //button6s.setBackgroundResource(R.drawable.diamondmini);
                    counterSuperzahl[0]--;
                    activationSuperzahl[6] = true;
                    button6s.setActivated(true);
                } else if (activationSuperzahl[6] == false && counterSuperzahl[0] == 0) {
                    Snackbar.make(v, "Es wurde bereits eine Superzahl gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button6s.setBackgroundResource(android.R.drawable.btn_default);
                    counterSuperzahl[0]++;
                    activationSuperzahl[6] = false;
                    button6s.setActivated(false);
                }
            }
        });

        final Button button7s = findViewById(R.id.button7s);
        button7s.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activationSuperzahl[7] == false && counterSuperzahl[0] > 0) {
                    //button7s.setBackgroundResource(R.drawable.diamondmini);
                    counterSuperzahl[0]--;
                    activationSuperzahl[7] = true;
                    button7s.setActivated(true);
                } else if (activationSuperzahl[7] == false && counterSuperzahl[0] == 0) {
                    Snackbar.make(v, "Es wurde bereits eine Superzahl gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button7s.setBackgroundResource(android.R.drawable.btn_default);
                    counterSuperzahl[0]++;
                    activationSuperzahl[7] = false;
                    button7s.setActivated(false);
                }
            }
        });

        final Button button8s = findViewById(R.id.button8s);
        button8s.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activationSuperzahl[8] == false && counterSuperzahl[0] > 0) {
                    //button8s.setBackgroundResource(R.drawable.diamondmini);
                    counterSuperzahl[0]--;
                    activationSuperzahl[8] = true;
                    button8s.setActivated(true);
                } else if (activationSuperzahl[8] == false && counterSuperzahl[0] == 0) {
                    Snackbar.make(v, "Es wurde bereits eine Superzahl gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button8s.setBackgroundResource(android.R.drawable.btn_default);
                    counterSuperzahl[0]++;
                    activationSuperzahl[8] = false;
                    button8s.setActivated(false);
                }
            }
        });

        final Button button9s = findViewById(R.id.button9s);
        button9s.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activationSuperzahl[9] == false && counterSuperzahl[0] > 0) {
                    //button9s.setBackgroundResource(R.drawable.diamondmini);
                    counterSuperzahl[0]--;
                    activationSuperzahl[9] = true;
                    button9s.setActivated(true);
                } else if (activationSuperzahl[9] == false && counterSuperzahl[0] == 0) {
                    Snackbar.make(v, "Es wurde bereits eine Superzahl gewählt", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //button9s.setBackgroundResource(android.R.drawable.btn_default);
                    counterSuperzahl[0]++;
                    activationSuperzahl[9] = false;
                    button9s.setActivated(false);
                }
            }
        });






        // --Restlichen Buttons (Superzahlen) hinzufügen. Es müssen jeweils 9 Änderungen vorgenommen werden
        // Design Hintergrund für den Hauptbildschirm hinzufügen
        // Design der Buttons auf der Hauptseite aussuchen
        // --Schnelleres Laden ermöglichen durch File4 (Beim Beenden der App wird der Inhalt der File4 gelöscht) --geht nicht
        // Loading Screen hinzufügen (siehe Video) --ich weiß bisher nicht wie das funktioniert
        // --Falls keine Verbindung: app schließen
        // FERTIG




    }

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



            System.out.println("Alles wurde erfolgreich gespeichert");

        }catch(IOException e) {
            System.out.println("IOException bei der JSON (in Pop (1628)): " + e.toString());
        }


    }


}
package com.example.lotto;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LadeBalkenActivity extends Activity {

    private ProgressBar ladebalken;
    private TextView prozentZahl;
    private TextView information;
    private int anzahlVersuche = 0;


    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.loading_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ladebalken = findViewById(R.id.ladebalken);
        prozentZahl = findViewById(R.id.prozentZahl_TextView);
        information = findViewById(R.id.wasGeladenWird_TextView);

        ladebalken.setMax(100);
        ladebalken.setScaleY(3f);

        // Start anzeigen -- es gibt kein start mehr, da animationen nicht gestoppt werden können

        // Hier eine While-Schleife, die auf die Antwort der Threads wartet
        // Thread1
        Runnable forThread1 = new Runnable() {
            @Override
            public void run() {

                while (true) {
                    if (Singelton.instance.getThread1_fertig()) {
                        //progressAnimation_Thread1();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                information.setText("Die gezogenen Zahlen konnten erfolgreich geladen werden.");
                            }
                        });

                        break;
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            information.setText("Versuche Verbindung herzustellen. (" + (++anzahlVersuche) + ")");
                        }
                    });

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread balkenphase2 = new Thread(forThread1);
        balkenphase2.start();

        // Thread2
        Runnable forThread2 = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (Singelton.instance.getThread2_fertig()) {
                        progressAnimation_Thread2();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                information.setText("Die Tippliste wurde erfolgreich geladen.");
                            }
                        });

                        break;
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };

        Thread balkenphase3 = new Thread(forThread2);
        balkenphase3.start();

    }


    public void progressAnimation_Thread1() {
        LadebalkenAnimation animation2 = new LadebalkenAnimation(this, ladebalken, prozentZahl, 0f, 50f);
        animation2.setDuration(2000);
        ladebalken.setAnimation(animation2);
    }

    public void progressAnimation_Thread2() {
        LadebalkenAnimation animation3 = new LadebalkenAnimation(this, ladebalken, prozentZahl, 0f, 100f);
        animation3.setDuration(2000);
        ladebalken.setAnimation(animation3);

    }
}

package com.example.lotto;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MyTotalListView extends Activity {

    LinearLayout mainLayout;
    LayoutInflater inflater;
    View row;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);




        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.listviewMyTips);


        inflater = getLayoutInflater();

        row = inflater.inflate(R.layout.row, mainLayout, false);

        System.out.println("ich habe alles initialisiert");



    }


    public View getCustomView() {
        return row;
    }
    public LinearLayout getCustomLayout() {
        return mainLayout;
    }

    public void addtolist(View inhalt) {

        mainLayout.addView(inhalt);
    }
}

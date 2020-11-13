package com.example.lotto;

import android.content.Context;
import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LadebalkenAnimation extends Animation {

    private Context context;
    private ProgressBar ladebalken;
    private TextView prozentAnzeige;
    private float from;
    private float to;
    private float value = 0f;

    public LadebalkenAnimation (Context context, ProgressBar ladebalken, TextView prozentAnzeige, float from, float to) {
        this.context = context;
        this.ladebalken = ladebalken;
        this.prozentAnzeige = prozentAnzeige;
        this.from = from;
        this.to = to;
    }

    public void applyTransformation (float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);


        value = from + (to - from) * interpolatedTime;
        ladebalken.setProgress((int) value);
        prozentAnzeige.setText((int) value + " %");


        if (value == to) {
            context.startActivity(new Intent(context, MainActivity.class));
        }


    }
}

package com.example.myui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

//linking Activity Pages
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    //Timer class object
    Timer timer;

    //animations
    Animation text_animation;

    //Hooks
    TextView txtView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Auto switch to next activity

        timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,MyUI2.class);
                startActivity(intent);
                finish();
            }
        },3000);



        //Animation
        text_animation = AnimationUtils.loadAnimation(this,R.anim.txt_animation);

        //Hooks

        txtView = findViewById(R.id.textView);

        txtView.setAnimation(text_animation);
    }

}
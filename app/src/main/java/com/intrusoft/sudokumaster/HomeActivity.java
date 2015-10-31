package com.intrusoft.sudokumaster;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class HomeActivity extends AppCompatActivity {

    Animation bounce;
    Button play,solve;
    LinearLayout logo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        play = (Button) findViewById(R.id.play);
        solve = (Button) findViewById(R.id.solve);
        logo = (LinearLayout) findViewById(R.id.icon_lay);
        bounce = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        Toolbar toolbar = (Toolbar) findViewById(R.id.bar);
        setSupportActionBar(toolbar);
        play.startAnimation(bounce);
        solve.startAnimation(bounce);
        logo.startAnimation(bounce);
    }

    public void game(View v){
        Intent i = new Intent(HomeActivity.this,PlayActivity.class);
        startActivity(i);
    }

    public void solver(View v){
        Intent i = new Intent(HomeActivity.this,SolveActivity.class);
        startActivity(i);
    }

}

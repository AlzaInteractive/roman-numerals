package com.alza.android.quiz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.alza.quiz.romans.R;


public class QuizDirectionScreenActivity extends AppCompatActivity {

    private static final int  TIMEOUT = 5000;
    Handler handler;
    Runnable runnable;
    long timeRemaining;
    CountDownTimer timer;
    TextView tvTimer;
    boolean skipDirection=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_quiz);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                startQuizActivity();
                finish();
            }
        };
        handler.postDelayed(runnable, TIMEOUT);
        tvTimer = (TextView) findViewById(R.id.directiontimerquiz);
        Button b = (Button) findViewById(R.id.directionstartquiz);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.removeCallbacks(runnable);
                startQuizActivity();
            }
        });
        countDownTimer();
    }

    private void startQuizActivity(){
        Intent i = new Intent(QuizDirectionScreenActivity.this,QuizActivity.class);
        startActivity(i);
    }
    @Override
    public void onBackPressed(){
        timer.cancel();
        handler.removeCallbacks(runnable);
        Intent i = new Intent(this,HomeActivity.class);
        startActivity(i);
    }

    private void countDownTimer(){
        timer = new CountDownTimer(TIMEOUT,100){

            @Override
            public void onTick(long l) {
                timeRemaining = l/1000;
                tvTimer.setText(String.valueOf(timeRemaining+1));
            }

            @Override
            public void onFinish() {

            }
        };
        timer.start();
    }
}

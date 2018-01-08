package com.alza.android.quiz.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.TextView;

import com.alza.android.quiz.App;
import com.alza.android.quiz.view.QuizStatsSummaryAdapter;
import com.alza.android.util.UtilGameFX;



import com.alza.android.util.UtilSharedPreferences;
import com.alza.quiz.romans.R;
import com.alza.quiz.model.QuizStats;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class ExerciseStatsActivity extends AppCompatActivity implements Animation.AnimationListener{
    InterstitialAd ads;
    QuizStats stats;
    boolean fromQuiz=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_stats);
        loadInterstitialAd();
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            stats = (QuizStats) extras.get("stats");
            fromQuiz = extras.getBoolean("fromQuiz");
            //Log.d("DEBUG","Stat sum list size "+stats.getStatByCategory().size());
            if (stats!=null){
                displayStats();
            }
        }
    }
    private void loadInterstitialAd(){
        ads = App.getInstance().getInterstitialAd();
        ads.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                goHome();
            }
        });
    }
    private void displayStats(){
        TextView tvScore = (TextView) findViewById(R.id.exstat_score);
        TextView tvTotQue = (TextView) findViewById(R.id.exstat_totquiz);
        TextView tvTotCorr = (TextView) findViewById(R.id.exstat_totcorrect);
        TextView tvTotTime = (TextView) findViewById(R.id.exstat_tottime);
        TextView tvHiScore = (TextView) findViewById(R.id.exstat_newhiscore);
        TextView tvUsername = (TextView) findViewById(R.id.username);
        ListView lvStatSum = (ListView) findViewById(R.id.statslist);
        Typeface typeface= Typeface.createFromAsset(getAssets(), "fonts/handwritingbyca.ttf");
        tvUsername.setTypeface(typeface);
        tvScore.setText(String.valueOf(stats.getScore()));
        tvTotQue.setText(String.valueOf(stats.getNumOfQuestion()));
        tvTotCorr.setText(String.valueOf(stats.getNumOfCorrectAnswer()));
        tvTotTime.setText(String.valueOf(stats.getTime()+""));
        QuizStatsSummaryAdapter adapter = new QuizStatsSummaryAdapter(
                this, R.layout.list_stats_summary);
        adapter.addAll(stats.getStatByCategory());
        lvStatSum.setAdapter(adapter);
        int hiScore = UtilSharedPreferences.readHiScore(this);
        if (stats.getScore() > hiScore){
            UtilSharedPreferences.saveHiScore(this,stats.getScore());
            Animation anim = AnimationUtils.loadAnimation(this, R.anim.fade_in);
            UtilGameFX.playSoundFXTada(this);
            anim.setAnimationListener(this);
            tvHiScore.setVisibility(View.VISIBLE);
            tvHiScore.setAnimation(anim);

        }
    }

    private void goHome(){
        Intent i = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(i);
    }
    @Override
    public void onBackPressed(){
        if (fromQuiz){
            if (ads.isLoaded()){
                ads.show();
                App.getInstance().reloadInterstitialAd();
            } else {
                goHome();
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

}

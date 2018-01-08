package com.alza.android.quiz.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.widget.TextView;


import com.alza.android.util.UtilSharedPreferences;
import com.alza.quiz.romans.R;
import com.alza.quiz.model.QuizStats;

public class ProfileActivity extends AppCompatActivity implements Animation.AnimationListener{
    QuizStats stats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        displayStats();
    }

    private void displayStats(){
        TextView tvHiScore = (TextView) findViewById(R.id.profile_hiscore);
        TextView tvUsername = (TextView) findViewById(R.id.profile_username);
        TextView tvAffiliation = (TextView) findViewById(R.id.profile_affiliation);
        TextView tvUserLocation = (TextView) findViewById(R.id.profile_location);
        Typeface typeface= Typeface.createFromAsset(getAssets(), "fonts/handwritingbyca.ttf");
        tvUsername.setTypeface(typeface);

        String username = UtilSharedPreferences.readUsername(this);
        String affiliation = UtilSharedPreferences.readSchool(this);
        String userLocation = UtilSharedPreferences.readUserLocation(this);
        int hiScore = UtilSharedPreferences.readHiScore(this);
        tvHiScore.setText("Skor tertinggi: " + hiScore);
        tvUsername.setText(username);
        tvAffiliation.setText(affiliation);
        tvUserLocation.setText(userLocation);
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

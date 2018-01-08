package com.alza.android.quiz.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alza.quiz.romans.R;


public class AboutActivity extends AppCompatActivity implements Animation.AnimationListener{
    Animation tdLogo,tdInfo,tdCredit,tdHelp,tdContrib;
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        animate();
    }

    private void animate(){
        LinearLayout lyInfo = (LinearLayout) findViewById(R.id.about_info_layout);
        LinearLayout lyCredit = (LinearLayout) findViewById(R.id.about_credit_layout);
        LinearLayout lyHelp = (LinearLayout) findViewById(R.id.about_help_layout);
        LinearLayout lyContrib = (LinearLayout) findViewById(R.id.about_contrib_layout);
        ImageView logo = (ImageView) findViewById(R.id.about_logo);
        title = (TextView) findViewById(R.id.about_title);

        //title.setVisibility(View.INVISIBLE);
        tdInfo = AnimationUtils.loadAnimation(this,R.anim.slide_down);
        tdCredit = AnimationUtils.loadAnimation(this,R.anim.slide_down);
        tdHelp = AnimationUtils.loadAnimation(this,R.anim.slide_down);
        tdContrib = AnimationUtils.loadAnimation(this,R.anim.slide_down);
        tdLogo = AnimationUtils.loadAnimation(this, R.anim.slide_down);

        int cnt = 250;
        tdLogo.setStartOffset(0);
        tdInfo.setStartOffset(1*cnt);
        tdCredit.setStartOffset(2*cnt);
        tdHelp.setStartOffset(3*cnt);
        tdContrib.setStartOffset(4*cnt);


        logo.setAnimation(tdLogo);
        logo.getAnimation().setAnimationListener(this);
        lyInfo.setAnimation(tdInfo);
        lyCredit.setAnimation(tdCredit);
        lyHelp.setAnimation(tdHelp);
        lyContrib.setAnimation(tdContrib);


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

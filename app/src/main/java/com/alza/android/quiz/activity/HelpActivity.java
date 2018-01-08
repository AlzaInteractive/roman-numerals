package com.alza.android.quiz.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alza.quiz.romans.R;


public class HelpActivity extends AppCompatActivity implements Animation.AnimationListener{
    Animation fadeIn, contactSlideDown, contributeSlideDown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helps);
        animate();
    }

    private void animate(){

        ImageView title = (ImageView) findViewById(R.id.help_title);
        LinearLayout contact = (LinearLayout) findViewById(R.id.help_contact);
        LinearLayout contribute = (LinearLayout) findViewById(R.id.help_contribute);

        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        contactSlideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        contributeSlideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);


        int cnt = 500;
        fadeIn.setStartOffset(0);
        contactSlideDown.setStartOffset(1 * cnt);
        contributeSlideDown.setStartOffset(2 * cnt);


        title.setAnimation(fadeIn);
        contact.setAnimation(contactSlideDown);
        contribute.setAnimation(contributeSlideDown);

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

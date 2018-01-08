package com.alza.android.quiz.activity;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alza.android.quiz.QuizManager;
import com.alza.android.util.UtilAds;
import com.alza.android.util.UtilGameFX;
import com.alza.android.util.UtilSharedPreferences;

import com.alza.quiz.romans.R;
import com.google.android.flexbox.FlexboxLayout;

import com.alza.quiz.model.MultipleChoiceQuiz;
import com.alza.quiz.model.Quiz;

import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private QuizManager qm;
    Button btnEndTest;
    TextView textSoal;
    TextView textSkor;
    TextView textCorr;
    TextView textNum;
    TextView textTimer;
    TextView textLevel;
    TextView textGrade;
    TextView textSubcategory;
    LinearLayout lyStat;
    CountDownTimer timer;
    long timeRemaining;
    FlexboxLayout answerArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        textSoal = (TextView) findViewById(R.id.soal);
        textSkor = (TextView) findViewById(R.id.quizskor);
        textCorr = (TextView) findViewById(R.id.quizcorr);
        textNum = (TextView) findViewById(R.id.quiznum);
        textTimer = (TextView) findViewById(R.id.timer);
        textLevel = (TextView) findViewById(R.id.quizdifflevel);
        textGrade = (TextView) findViewById(R.id.quizgrade);
        textSubcategory = (TextView) findViewById(R.id.quizsubcateg);
        btnEndTest = (Button) findViewById(R.id.endQuiz);
        btnEndTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        answerArea = (FlexboxLayout) findViewById(R.id.answerarea);
        lyStat = (LinearLayout) findViewById(R.id.content_quiz_stat);
        int i = UtilSharedPreferences.getMaxGradeForExercise(this);
        qm = new QuizManager(i);
        prepareQuiz();
    }
    private void prepareQuiz(){
        answerArea.removeAllViews();
        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        Quiz q = qm.getCurrentQuiz();
        if (q != null){
            if (q instanceof MultipleChoiceQuiz){
                MultipleChoiceQuiz mq = (MultipleChoiceQuiz) q;
                List<String> choices = mq.getChoices();
                for (String c:choices) {
                    final Button b = new Button(this.getApplicationContext());
                    params.setMargins(5,5,5,5);
                    b.setLayoutParams(params);
                    b.setTextColor(ContextCompat.getColor(this.getApplicationContext(),R.color.colorWhite));
                    b.setBackground(ContextCompat.getDrawable(this.getApplicationContext(),R.drawable.button_roundcon));
                    b.setText(c);
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            answer(b, view);
                        }
                    });
                    answerArea.addView(b);
                }
            }
            textSoal.setText(q.getQuestion());
            try {
                //System.out.println("teksSkor "+qm.getAccumScore());
                textSkor.setText("Skor sementara = "+String.valueOf(qm.getAccumScore()));
            } catch (Exception e){
                System.out.println(e.toString());
            }
            textLevel.setText(qm.getCurrentQuiz().getQuizLevel().toString());
            textSubcategory.setText(qm.getCurrentQuiz().getLessonSubcategory());
            textGrade.setText("Materi kelas : "+qm.getCurrentQuiz().getLessonGrade());
            textCorr.setText("Benar "+qm.getStats().getNumOfCorrectAnswer()+" " +
                    "dari "+qm.getStats().getNumOfQuestion()+" soal");
            textNum.setText("Soal ke-"+qm.getCurQuizPos()+" dari "+qm.getQuizListSize());
            countDownTimer();
        } else {
            endQuiz();
        }
    }
    private void endQuiz(){
        if (qm.getStats().getNumOfQuestion()>0){
            UtilSharedPreferences.addStat(this,qm.getStats());
        }
        Intent i = new Intent(QuizActivity.this,ExerciseStatsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("stats",qm.getStats());
        bundle.putBoolean("fromQuiz",true);
        i.putExtras(bundle);
        startActivity(i);
    }
    private void answer(Button btn, View view){
        timer.cancel();
        boolean b = qm.answer(btn.getText().toString(),timeRemaining);
        animateAnswer(b);
        prepareQuiz();
    }
    private void countDownTimer(){
        timer = new CountDownTimer(qm.getTimeSlotInMilis(),1000){

            @Override
            public void onTick(long l) {
                timeRemaining = l/1000;
                textTimer.setText(String.valueOf(timeRemaining));
            }

            @Override
            public void onFinish() {
                textTimer.setText(String.valueOf(0));
            }
        };
        timer.start();
    }

    @Override
    public void onBackPressed(){
        confirm();
    }

    private void confirm(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setMessage("Hentikan sesi latihan ini?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                        //startActivity(intent);
                        endQuiz();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                })
                .show();
    }
    private void animateAnswer(boolean b){
        int colorTo, colorFrom;
        colorFrom = ContextCompat.getColor(this, R.color.colorappbackground);
        final ValueAnimator forth,back;
        if (b){
            UtilGameFX.playSoundFXRight(this);
            colorTo = ContextCompat.getColor(this, R.color.pastelgreen4);
        } else {
            UtilGameFX.playSoundFXWrong(this);
            UtilGameFX.vibrate(this);
            colorTo = ContextCompat.getColor(this, R.color.colorErrorRed);
        }
        forth = new ValueAnimator();
        back = new ValueAnimator();
        forth.setIntValues(colorFrom,colorTo);
        forth.setEvaluator(new ArgbEvaluator());
        forth.setDuration(500);
        forth.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                lyStat.setBackgroundColor((int)forth.getAnimatedValue());
            }
        });

        back.setIntValues(colorTo,colorFrom);
        back.setEvaluator(new ArgbEvaluator());
        back.setDuration(500);
        back.setStartDelay(500);
        back.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                lyStat.setBackgroundColor((int)back.getAnimatedValue());
            }
        });
        forth.start();
        back.start();
    }

}

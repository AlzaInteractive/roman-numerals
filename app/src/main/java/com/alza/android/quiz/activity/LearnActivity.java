package com.alza.android.quiz.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.alza.android.util.UtilGameFX;

import com.alza.quiz.romans.R;
import com.google.android.flexbox.FlexboxLayout;

import com.alza.quiz.util.CommonFunctionAndValues;
import com.alza.common.math.MathUtils;

public class LearnActivity extends AppCompatActivity implements Animation.AnimationListener{

    private final int PHASE_FIND_1ST_MULTIPLE = 1;
    private final int PHASE_FIND_2ND_MULTIPLE = 2;
    private final int PHASE_FIND_LCM = 3;
    private final int PHASE_COMPLETED = 4;
    private int numberCount = 30;
    private int currentPhase = 0;
    private int firstNumber, secondNumber, lcm;
    private boolean lcmIsFound = false;
    private TextView tvTitle,tvPhase1,tvPhase2,tvPhase3,tvConclusion;
    private FlexboxLayout answerArea;
    private float scale;
    private Animation animFadeIn;
    private Animation animFadeOut;
    private Animation animBlink;
    private Animation animFadeOutToHalfAlpha;
    private Animation animSlideDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scale = this.getResources().getDisplayMetrics().density;
        setContentView(R.layout.activity_learn);
        tvTitle = (TextView) findViewById(R.id.learn_title);
        tvPhase1 = (TextView) findViewById(R.id.learn_phase1);
        tvPhase2 = (TextView) findViewById(R.id.learn_phase2);
        tvPhase3 = (TextView) findViewById(R.id.learn_phase3);
        tvConclusion = (TextView) findViewById(R.id.learn_conclusion);
        answerArea = (FlexboxLayout) findViewById(R.id.picknumberparent);
        animFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        animFadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        animBlink = AnimationUtils.loadAnimation(this, R.anim.blink);
        animFadeOutToHalfAlpha = AnimationUtils.loadAnimation(this, R.anim.fade_out_to_half_alpha);
        animSlideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        animFadeIn.setAnimationListener(this);
        animFadeOut.setAnimationListener(this);
        animBlink.setAnimationListener(this);
        animFadeOutToHalfAlpha.setAnimationListener(this);
        animSlideDown.setAnimationListener(this);
        do{
            int[] a = CommonFunctionAndValues.getPairOfIntSimple();
            firstNumber = a[0];
            secondNumber = a[1];
            lcm = MathUtils.findLCM(firstNumber,secondNumber);
        } while (lcm > numberCount);

        tvTitle.setText("Mencari Kelipatan Persekutuan Terkecil (KPK) antara" +
                " bilangan "+firstNumber+" & "+secondNumber);
        tvTitle.setAnimation(animSlideDown);
        prepareButton();
        initiateFirstPhase();

    }
    private void prepareButton(){
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 55, getResources().getDisplayMetrics());
        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(width,height);
        for (int i=1; i<=numberCount; i++){
            final Button b = new Button(this.getApplicationContext());
            params.setMargins(5,5,5,5);
            b.setLayoutParams(params);
            b.setWidth(10);
            b.setTextColor(ContextCompat.getColor(this.getApplicationContext(),R.color.colorWhite));
            b.setBackgroundColor(ContextCompat.getColor(this.getApplicationContext(),R.color.pastelvioletblue4));
            b.setText(String.valueOf(i));
            b.setTag(false);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pickMultiple(b, view);
                }
            });
            answerArea.addView(b);
        }
    }
    private void initiateFirstPhase(){
        currentPhase = PHASE_FIND_1ST_MULTIPLE;
        String instruction = "Tahap 1. Pilih semua angka kelipatan dari bilangan "+firstNumber;
        tvPhase1.setText(instruction);
        tvPhase1.setVisibility(View.VISIBLE);
        tvPhase1.setAnimation(animBlink);

    }

    private void pickMultiple(Button b, View view){
        System.out.println("masuk");
        int num = Integer.valueOf(b.getText().toString());
        int divisor = 0;
        int color = 0;
        switch (currentPhase){
            case PHASE_FIND_1ST_MULTIPLE:
                if(num % firstNumber == 0){
                    b.setBackgroundColor(ContextCompat.getColor
                            (this.getApplicationContext(),R.color.pastelpink4));
                    b.setTag(true);
                    makeThatRightKnown();
                    checkPhaseCompletionAdvanceIfPossible();
                } else {
                    makeThatWrongKnown();
                }
                System.out.println("in");
                break;
            case PHASE_FIND_2ND_MULTIPLE:
                if(num % secondNumber == 0){
                    b.setTag(true);
                    makeThatRightKnown();
                    b.setBackgroundColor(ContextCompat.getColor
                            (this.getApplicationContext(),R.color.pastelskyblue4));
                    if (num % firstNumber == 0){
                        b.setTag(true);
                        b.setBackground(ContextCompat.getDrawable
                                (this.getApplicationContext(),R.drawable.gradpinktoskyblue));
                    }
                    checkPhaseCompletionAdvanceIfPossible();
                } else {
                    makeThatWrongKnown();
                }
                break;
            case PHASE_FIND_LCM:
                if (num == lcm){
                    lcmIsFound = true;
                    UtilGameFX.playSoundFXTada(this);
                    advanceToNextPhase();
                } else {
                    makeThatWrongKnown();
                }
            default:
                break;
        }

    }

    private void makeThatRightKnown(){
        UtilGameFX.playSoundFXSelection(this);
    }

    private void makeThatWrongKnown(){
        //System.out.println("wrong!!!");
        UtilGameFX.playSoundFXWrong(this);
    }

    private void makeThatAdvanceKnown(){
       // System.out.println("advance");
        UtilGameFX.playSoundFXRight(this);
    }

    private void checkPhaseCompletionAdvanceIfPossible(){
        boolean phaseCompleted = true;
        boolean status = true;
        for(int i=0; i< numberCount && currentPhase < PHASE_FIND_LCM; i++){
            Button b = (Button) answerArea.getChildAt(i);
            int num = i + 1;
            switch (currentPhase){
                case PHASE_FIND_1ST_MULTIPLE:
                    status = (boolean) b.getTag();
                    if (num % firstNumber == 0 && !status){
                        phaseCompleted = false;
                    }
                    break;
                case PHASE_FIND_2ND_MULTIPLE:
                    status = (boolean) b.getTag();
                    if (num % secondNumber == 0 && !status){
                        phaseCompleted = false;
                    }
                    break;
                default:
                    break;

            }
        }
        if (currentPhase == PHASE_FIND_LCM && lcmIsFound){

        }
        if (phaseCompleted){
            advanceToNextPhase();
        }

    }
    private void advanceToNextPhase(){
        if(currentPhase == PHASE_FIND_1ST_MULTIPLE){
            makeThatAdvanceKnown();
            currentPhase = PHASE_FIND_2ND_MULTIPLE;
            String instruction = "Tahap 2. Pilih semua angka kelipatan dari bilangan "+secondNumber;
            tvPhase1.setAnimation(animFadeOutToHalfAlpha);
            tvPhase2.setVisibility(View.VISIBLE);
            tvPhase2.setText(instruction);
            tvPhase2.setAnimation(animBlink);
        } else if (currentPhase == PHASE_FIND_2ND_MULTIPLE){
            makeThatAdvanceKnown();
            currentPhase = PHASE_FIND_LCM;
            String instruction = "Tahap 3. Pilih bilangan kelipatan  terkecil dari "+firstNumber+" dan "+secondNumber;

            tvPhase2.setAnimation(animFadeOutToHalfAlpha);
            tvPhase3.setVisibility(View.VISIBLE);
            tvPhase3.setText(instruction);
            tvPhase3.setAnimation(animBlink);
        } else if (currentPhase == PHASE_FIND_LCM){
            fadeIrrelevantButton();
            currentPhase = PHASE_COMPLETED;
            tvPhase3.setAnimation(animFadeOutToHalfAlpha);
            tvConclusion.setVisibility(View.VISIBLE);
            String conclusion ="Kesimpulan : KPK dari bilangan " + firstNumber+ " dan "+ secondNumber
                    +" adalah "+lcm;
            tvConclusion.setText(conclusion);
            tvConclusion.setAnimation(animSlideDown);
        }
        for(int i=0; i< numberCount ; i++) {
            Button b = (Button) answerArea.getChildAt(i);
            b.setTag(false);
            if (currentPhase == PHASE_FIND_LCM) {

                boolean isMultipleOfFirstNumber = ((i + 1) % firstNumber == 0);
                boolean isMultipleOfSecondNumber = ((i + 1) % secondNumber == 0);
                if (isMultipleOfFirstNumber && isMultipleOfSecondNumber) {
                    System.out.println("Someone is tagged as shoud be grow");
                    b.setTag("grow");
                    System.out.println("grow " + i);
                }
            }
            else if (currentPhase == PHASE_COMPLETED){
                if((i+1) == lcm){
                    b.setTag("lcm");
                    System.out.println("lcm "+i);
                }
            }
        }
        if (currentPhase >= PHASE_FIND_LCM){
            fadeIrrelevantButton();
        }
    }
    private void fadeIrrelevantButton(){
        //System.out.println("Whos to fade");
        if (currentPhase == PHASE_FIND_LCM){
            for(int i=0; i< numberCount ; i++) {
                Button b = (Button) answerArea.getChildAt(i);
                Object tag = b.getTag();
                System.out.println("fading "+tag+" "+i);
                if (tag instanceof String){
                    b.setBackground(ContextCompat.getDrawable
                            (this.getApplicationContext(),R.drawable.gradpinktoskyblue));
                } else {
                    //System.out.println("Whos to fade : button "+i);
                    b.setBackground(ContextCompat.getDrawable
                            (this.getApplicationContext(),R.color.colorWhite));
                }

            }
        } else if (currentPhase == PHASE_COMPLETED){
            for(int i=0; i< numberCount ; i++) {
                Button b = (Button) answerArea.getChildAt(i);
                Object tag = b.getTag();
                System.out.println("fading "+tag+" "+i);
                if (tag instanceof String){
                    if (((String) tag).equalsIgnoreCase("lcm"))
                        b.setBackground(ContextCompat.getDrawable
                            (this.getApplicationContext(),R.drawable.gradpinktoskyblue));
                } else {
                    b.setBackground(ContextCompat.getDrawable
                            (this.getApplicationContext(),R.color.colorWhite));
                }

            }
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

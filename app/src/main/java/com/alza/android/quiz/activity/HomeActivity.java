package com.alza.android.quiz.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.alza.android.quiz.App;
import com.alza.quiz.romans.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;


public class HomeActivity extends AppCompatActivity {
    private static final int RESULT_SETTINGS = 1;
    GoogleApiClient client;
    private static final String TAG = HomeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //ImageButton bLearn = (ImageButton) findViewById(R.id.learn);
        ImageButton bQuiz = (ImageButton) findViewById(R.id.quiz);
        //ImageButton bProfile = (ImageButton) findViewById(R.id.profile);
        ImageButton bShare = (ImageButton) findViewById(R.id.share);
        ImageButton bSetting = (ImageButton) findViewById(R.id.settings);
        ImageButton bInfo = (ImageButton) findViewById(R.id.info);
        //ImageButton bHelp = (ImageButton) findViewById(R.id.help);
        //ImageButton bRank = (ImageButton) findViewById(R.id.rank);
        ImageButton bCompletion = (ImageButton) findViewById(R.id.grad);
        bQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQuizActivity();
            }
        });

        bShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startShareActivity();
            }
        });
        bSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSettingActivity();
            }
        });
        bCompletion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCompletionActivity();
            }
        });
        bInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startInfoActivity();
            }
        });
    }

    private void startLearningActivity(){
        //LearnActivity l = new LearnActivity();
        Intent i = new Intent(this.getApplicationContext(),LearnActivity.class);
        this.startActivity(i);
    }

    private void startQuizActivity() {
        //QuizActivity q = new QuizActivity();
        Intent i = new Intent(this.getApplicationContext(),QuizDirectionScreenActivity.class);
        this.startActivity(i);
    }

    private void startProfileActivity(){
        Intent i = new Intent(this.getApplicationContext(),ProfileActivity.class);
        this.startActivity(i);
    }

    private void startSettingActivity(){
        Intent i = new Intent(this, UserSettingActivity.class);
        startActivityForResult(i, RESULT_SETTINGS);
    }

    private void startCompletionActivity(){
        Intent i = new Intent(this, ExerciseAllStatsActivity.class);
        this.startActivity(i);
    }

    private void startInfoActivity(){
        Intent i = new Intent(this, AboutActivity.class);
        this.startActivity(i);
    }

    private void startHelpActivity(){
        Intent i = new Intent(this, HelpActivity.class);
        this.startActivity(i);
    }

    private void startLeaderboardActivity(){


    }

    private void startActivity(Class c){
        Intent i = new Intent(this, c.getClass());
        this.startActivity(i);
    }

    private void startShareActivity(){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = getApplicationContext().getResources().getString(R.string.app_sharer_desc);
        String shareSub = getApplicationContext().getResources().getString(R.string.app_sharer_title);
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, UserSettingActivity.class);
            startActivityForResult(i, RESULT_SETTINGS);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode) {
            case KeyEvent.KEYCODE_BACK:
                minimizeApp();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void minimizeApp() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}

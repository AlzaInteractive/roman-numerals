package com.alza.android.quiz.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alza.android.quiz.view.QuizStatsAdapter;
import com.alza.android.util.UtilSharedPreferences;
import com.alza.quiz.romans.R;
import com.alza.quiz.model.QuizStats;

import java.util.List;

public class ExerciseAllStatsActivity extends AppCompatActivity implements Animation.AnimationListener{
    QuizStats stats;
    List<QuizStats> statses;
    QuizStats selectedStats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_stats_all);
        Bundle extras = getIntent().getExtras();
        statses = UtilSharedPreferences.readStatses(this);
        if (statses.size()>0){
                displayStats();
        }
    }

    private void displayStats(){

        TextView tvUsername = (TextView) findViewById(R.id.username);
        ListView lvStatSum = (ListView) findViewById(R.id.statslist);
        Typeface typeface= Typeface.createFromAsset(getAssets(), "fonts/handwritingbyca.ttf");
        tvUsername.setTypeface(typeface);
        QuizStatsAdapter adapter = new QuizStatsAdapter(
                this, R.layout.list_stats);
        adapter.addAll(statses);
        lvStatSum.setAdapter(adapter);
        lvStatSum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedStats = statses.get(i);
                Intent intent = new Intent(getApplicationContext(),ExerciseStatsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("stats",selectedStats);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
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

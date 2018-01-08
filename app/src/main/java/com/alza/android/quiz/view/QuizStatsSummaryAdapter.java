package com.alza.android.quiz.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.alza.common.math.Fraction;

import com.alza.quiz.romans.R;
import com.alza.quiz.model.QuizStats;

/**
 * Created by ewin.sutriandi@gmail.com on 08/02/17.
 */

public class QuizStatsSummaryAdapter extends ArrayAdapter<QuizStats.QuizSummary>{

    private Integer colorMax,colorHalf,colorMin;
    public QuizStatsSummaryAdapter(Context context, int resource) {
        super(context, resource);
        colorMin = ContextCompat.getColor(context, R.color.colorErrorRed);
        colorHalf = ContextCompat.getColor(context, R.color.pastelyellow4);
        colorMax = ContextCompat.getColor(context, R.color.pastelgreen4);
    }
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_stats_summary, null);
        }
        QuizStats.QuizSummary i = getItem(position);
        if (i != null) {
            TextView tvsubcat = (TextView) v.findViewById(R.id.statsubcategory);
            tvsubcat.setText(i.subCategory+". Materi kelas "+i.grade);
            TextView tvCorrOfTotal = (TextView) v.findViewById(R.id.statscorrectoftotal);
            tvCorrOfTotal.setText("Benar "+i.correct+" dari "+i.total+" soal");
            TextView tvPercentage = (TextView) v.findViewById(R.id.statscorrectpct);
            Fraction pct = new Fraction(i.correct,i.total);
            tvPercentage.setText(i.getPercentage());
            float fraction = (float)i.correct/(float)i.total;
            int buttonColor;
            tvPercentage.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.rounded_corner_transparent));
            if (fraction>=0.5){
                fraction = (fraction - 0.5f) * 2f;
                buttonColor = ColorUtils.blendARGB(colorHalf,colorMax,fraction);
            }
            else {
                fraction = fraction * 2f;
                buttonColor =ColorUtils.blendARGB(colorMin,colorHalf,fraction);
            }
            tvPercentage.setBackgroundColor(buttonColor);
            //tvPercentage.setBackground(v.getResources().getDrawable(R.drawable.rounded_corner_red,null));

        }
        return v;
    }


}

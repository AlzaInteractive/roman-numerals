package com.alza.android.quiz.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.alza.quiz.romans.R;


/**
 * Created by ewin.sutriandi@gmail.com on 13/01/17.
 */

public class UserSettingActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);

    }
}

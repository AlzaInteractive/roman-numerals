package com.alza.android.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.alza.quiz.model.QuizStats;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ewin.sutriandi@gmail.com on 11/01/17.
 */

public class UtilSharedPreferences {
    private static final String STORE_FILE_NAME = "com.alza.quiz.fraction";
    private static SharedPreferences getSharedPreferences(Context c){
        return c.getSharedPreferences(
                STORE_FILE_NAME, Context.MODE_PRIVATE);
    }
    public static int readHiScore(Context c){
        SharedPreferences sharedPreferences = getSharedPreferences(c);
        int hiScore= sharedPreferences.getInt("hiscore",0);
        return hiScore;
    }
    public static String readUsername(Context c){
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(c);
        String username = sharedPrefs.getString("username","");
        return username;
    }
    public static String readSchool(Context c){
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(c);
        String school = sharedPrefs.getString("school","");
        String kelas = sharedPrefs.getString("grade","");
        String s="";
        if (!kelas.isEmpty()){
            s = "Kelas "+kelas+" ";
        }
        s+=school;
        return s;
    }
    public static String readUserLocation(Context c){
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(c);
        String userLocation ="";
        String provinsi = sharedPrefs.getString("provinsi","");
        if (!provinsi.isEmpty()){
            userLocation = "Provinsi "+provinsi+ " ";
        }
        String kabupaten = sharedPrefs.getString("kabupaten","");
        if (!kabupaten.isEmpty()){
            userLocation += "Kabupaten "+kabupaten+" ";
        }
        String kecamatan = sharedPrefs.getString("kecamatan","");
        if (!kecamatan.isEmpty()){
            userLocation += "Kecamatan "+kecamatan;
        }
        return userLocation;
    }
    public static void saveUsername(Context c, String userName){
        SharedPreferences sharedPreferences = getSharedPreferences(c);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", userName).apply();
    }
    public static void saveAffiliation(Context c, String affiliation){
        SharedPreferences sharedPreferences = getSharedPreferences(c);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("affiliation", affiliation).apply();
    }
    public static void saveHiScore(Context c, int hiScore){
        SharedPreferences sharedPreferences = getSharedPreferences(c);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("hiscore", hiScore).apply();
    }
    public static List<QuizStats> readStatses(Context c){
        List<QuizStats> statses;
        SharedPreferences sharedPreferences = getSharedPreferences(c);
        String jsonStats = sharedPreferences.getString("statses",null);
        Log.d("DEBUG","read "+jsonStats);
        Gson gson = new Gson();
        Type type = new TypeToken<List<QuizStats>>(){}.getType();
        statses = gson.fromJson(jsonStats,type);
        if (statses == null){
            statses = new ArrayList<>();
        }
        return  statses;
    }
    public static void addStat(Context c, QuizStats stats){

        List<QuizStats> statses = readStatses(c);
        statses.add(stats);
        if (statses.size()>10){
            statses.remove(0);
        }
        Gson gson = new Gson();
        String jsonStats = gson.toJson(statses);
        Log.d("DEBUG","add "+jsonStats);
        SharedPreferences sharedPreferences = getSharedPreferences(c);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("statses", jsonStats).apply();
    }
    public static int getMaxGradeForExercise(Context c){
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(c);
        String kelas =  sharedPrefs.getString("prefMaxGrade","6");

        return Integer.valueOf(kelas);

    }
    public static boolean isAllowSound(Context c){
        boolean allow = true;
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(c);
        allow = sharedPrefs.getBoolean("prefAudio",true);
        return allow;
    }
    public static boolean isAllowVibrate(Context c){
        boolean allow = true;
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(c);
        allow = sharedPrefs.getBoolean("prefVibrate",true);
        return allow;
    }
}

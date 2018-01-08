package com.alza.android.util;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;

import com.alza.quiz.romans.R;

/**
 * Created by ewin.sutriandi@gmail.com on 02/01/17.
 */

public class UtilGameFX {
    private static MediaPlayer mp;
    public static void playSound (Context c, int sound){
        if (!UtilSharedPreferences.isAllowSound(c)){
            return;
        }
        if (mp!=null){
            if (mp.isPlaying()){
                mp.stop();
            }
            mp.release();
        }
        mp = MediaPlayer.create(c,sound);
        mp.start();
    }

    public static void playSoundFXWrong(Context c){
        playSound(c, R.raw.lucario__error);
    }
    public static void vibrate(Context c){
        if(UtilSharedPreferences.isAllowVibrate(c)){
            Vibrator v = (Vibrator) c.getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(500);
        }
    }

    public static void playSoundFXRight(Context c){
        playSound(c,R.raw.bertrof__correct);
    }

    public static void playSoundFXSelection(Context c){
        playSound(c,R.raw.bertrof__selection);
    }

    public static void playSoundFXTada(Context c){
        playSound(c,R.raw.jobro__tada2);
    }
}

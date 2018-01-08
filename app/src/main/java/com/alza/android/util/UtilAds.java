package com.alza.android.util;

import android.content.Context;

import com.alza.quiz.romans.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by ewin.sutriandi@gmail.com on 08/03/17.
 */

public class UtilAds {
    public static InterstitialAd loadInterstitialAd(Context context){
        InterstitialAd ads = new InterstitialAd(context);
        String adId= context.getResources().getString(R.string.ad_interstitial_unit_id);
        ads.setAdUnitId(adId);
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();

        ads.loadAd(adRequest);
        return ads;
    }
}

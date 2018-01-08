package com.alza.android.quiz;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.alza.android.GoogleApiHelper;
import com.alza.android.util.UtilAds;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * Created by ewin.sutriandi@gmail.com on 05/03/17.
 */

public class App extends Application {
    /**
    GoogleApiHelper googleApiHelper;
    private static App appInstance;

    public void onCreate(){
        super.onCreate();
        //appInstance = this;
        //googleApiHelper = new GoogleApiHelper(appInstance);
    }

    public static synchronized App getInstance(){
        return appInstance;
    }

    public GoogleApiHelper getGoogleApiHelperInstance(){
        return this.googleApiHelper;
    }

    public static GoogleApiHelper getGoogleApiHelper(){
        return getInstance().getGoogleApiHelperInstance();
    }
     **/

    private InterstitialAd interstitialAd;
    private static App appInstance;
    public void onCreate(){
        super.onCreate();
        appInstance = this;
        interstitialAd = UtilAds.loadInterstitialAd(this);
    }
    public InterstitialAd getInterstitialAd(){
        return interstitialAd;
    }
    public static synchronized App getInstance(){
        return appInstance;
    }
    public void reloadInterstitialAd(){
        interstitialAd = UtilAds.loadInterstitialAd(this);
    }
}

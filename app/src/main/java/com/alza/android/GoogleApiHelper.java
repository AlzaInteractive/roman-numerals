package com.alza.android;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.IntentSender;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

/**
 * Created by galuh on 05/03/17.
 */

public class GoogleApiHelper implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = GoogleApiHelper.class.getSimpleName();
    private static final int REQUEST_CODE_RESULT_ERR = 999;
    public boolean connectHasResolution = false;
    boolean alreadyResolving=false;
    Context context;
    GoogleApiClient googleApiClient;
    private ConnectionResult connectionResult;

    public GoogleApiHelper(Context context){
        this.context = context;
        buildGoogleApiClient();
        connect();

    }
    public GoogleApiClient getGoogleApiClient(){
        return googleApiClient;
    }
    public void connect(){
        Log.d(TAG,"Initiate connection");
        if (googleApiClient!= null){
            googleApiClient.connect();
        }
    }
    public void disconnect(){
        if (googleApiClient != null && googleApiClient.isConnected()){
            googleApiClient.disconnect();
        }
    }
    public boolean isConnected(){
        if (googleApiClient!= null) return googleApiClient.isConnected();
        return false;
    }
    private void buildGoogleApiClient(){
        Log.d(TAG,"Begin building");
        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();

        Log.d(TAG,"Google API client "+googleApiClient.toString()+googleApiClient.isConnected());
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "OnConnectionSuspended: GoogleApiClient.connect()");
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "OnConnectionFailed: ConnectionResult = "+ connectionResult.toString()
            +", has resolution = "+connectionResult.hasResolution());
        if (alreadyResolving){
            return;
        }
        else if (connectionResult.hasResolution()) {
            this.connectHasResolution = true;
            this.connectionResult = connectionResult;
        }
    }

    public void startResolution(Activity activity){
        try {
            alreadyResolving = true;
            connectionResult.startResolutionForResult(activity, REQUEST_CODE_RESULT_ERR);
        } catch (IntentSender.SendIntentException e) {
            Log.d(TAG, e.getMessage());
            googleApiClient.connect();
        }
    }
}

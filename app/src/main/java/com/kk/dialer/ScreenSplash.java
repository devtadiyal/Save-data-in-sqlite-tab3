package com.kk.dialer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;



public class ScreenSplash extends Activity {
    private static final String TAG = ScreenSplash.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.screen_splash);
            screenSplash();

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    void screenSplash() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ConnectivityManager connMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {
                    // fetch data

                    startActivity(new Intent(ScreenSplash.this, LoginActivity.class));
                    finish();
                } else {
                    showNetworkAlert();
                }

            }
        }, 500);


    }


    void showNetworkAlert() {
        new AlertDialog.Builder(this)
                .setTitle("Alert")
                .setMessage(
                        "Please make sure you have Network Enabled")
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
						/*Intent siphome = new Intent(getApplicationContext(),SipHome.class);
						startActivity(siphome);*/

                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        startActivity(intent);
                        finish();
                        System.exit(0);

                    }
                }).show();

    }

}

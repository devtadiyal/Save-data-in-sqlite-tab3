package com.kk.dialer.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class UnbindService extends IntentService {


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public UnbindService() {
        super("UNBIND SERVICE");
    }

    @Override
    public IBinder onBind(Intent intent) {


        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Thread.sleep(5000);
            Toast.makeText(this, "service o handled intent", Toast.LENGTH_SHORT).show();
            System.out.println("service o handled intent");
        } catch (InterruptedException e) {
            // Restore interrupt status.
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
            Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
        System.out.println("service starting");
        return START_NOT_STICKY;
    }
}

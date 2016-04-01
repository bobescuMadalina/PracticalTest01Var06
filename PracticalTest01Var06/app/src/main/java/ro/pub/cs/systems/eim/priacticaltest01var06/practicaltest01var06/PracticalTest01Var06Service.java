package ro.pub.cs.systems.eim.priacticaltest01var06.practicaltest01var06;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by mada on 01.04.2016.
 */
public class PracticalTest01Var06Service extends Service {
    PracticalTest01Var06Thread thread;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String web = intent.getStringExtra(Constants.WEB_VALUE);
        thread = new PracticalTest01Var06Thread(getApplicationContext(), web);
        thread.start();
        Log.d("APP_TAG", "Service started");
        return Service.START_REDELIVER_INTENT;

    }


    @Override
    public void onDestroy() {
        thread.stopThread();
        super.onDestroy();
    }
}

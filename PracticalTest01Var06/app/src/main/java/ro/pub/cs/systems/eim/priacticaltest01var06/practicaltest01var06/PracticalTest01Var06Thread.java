package ro.pub.cs.systems.eim.priacticaltest01var06.practicaltest01var06;

import android.content.Context;
import android.content.Intent;

import java.util.Date;

/**
 * Created by mada on 01.04.2016.
 */
public class PracticalTest01Var06Thread extends  Thread {

    Context context;
    String webString;
    boolean canRun = true;


    public PracticalTest01Var06Thread(Context context, String webString) {
        this.context = context;
        this.webString = webString;
    }

    @Override
    public void run() {
        while (canRun) {
            sendMessage(Constants.FIRST_ACTION);
            sleep();
            sendMessage(Constants.SECOND_ACTION);
            sleep();
        }
    }


    private void sendMessage(String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        intent.putExtra(Constants.MESSAGE, new Date(System.currentTimeMillis()) + " " + webString);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void stopThread() {
        this.canRun = false;
    }
}

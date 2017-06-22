package com.demo.wang.floatingwindow;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by zhiwei.wang on 2017/6/22.
 */

public class FloatingService extends Service {

    public FloatingService() {

    }
    private static FloatingService instance;
    public static FloatingService getInstance(){
        synchronized (FloatingService.class){
            if(instance==null){
                instance = new FloatingService();
            }
        }
        return instance;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        FloatingView.getInstance(FloatingService.this).createFloatView();
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

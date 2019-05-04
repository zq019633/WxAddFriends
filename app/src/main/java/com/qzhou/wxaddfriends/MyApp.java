package com.qzhou.wxaddfriends;

import android.app.Application;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
      //  CrashHandler.getInstance().init(this);
    }
}

package com.qzhou.wxaddfriends.service;

import android.accessibilityservice.AccessibilityService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import java.util.List;

public class AccessibilitySampleService extends AccessibilityService {
    public static final String TAG = "zqTest";
    private TextToSpeech mTts;

//    @Override
//    protected void onServiceConnected() {
//        super.onServiceConnected();
//        Toast.makeText(this, "服务已开启", Toast.LENGTH_SHORT).show();
//        mTts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
//            @Override
//            public void onInit(int status) {
//                if (status == TextToSpeech.SUCCESS) {
//                    mTts.setLanguage(Locale.CHINESE);
//                }
//            }
//        });
//    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        Log.e("zq ", "界面:" + event.getClassName().toString());
  //      int eventType = event.getEventType();
//        boolean isNew=false;
//        if (true) {
//            Log.e("zq ","我是真的");
//            if(true) {
//                openFx("发现", isNew);
//                openDelay(2000, "附近的人");
//            }
//            if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && event.getClassName().equals("com.tencent.mm.ui.LauncherUI")) {
//
//                Log.e("zq ","点击发现");
//                //当前在微信聊天页就点开发现
//
//                openFx("发现",isNew);
//
//                //然后跳转到附近的人
//                //
//            }
//        }

    }

    private void openDelay(final int delaytime, final String text) {
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void run() {
                try {
                    Thread.sleep(delaytime);
                } catch (InterruptedException mE) {
                    mE.printStackTrace();
                }
                openFx(text, true);
            }
        }).start();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void openFx(String str, boolean isNew) {
        AccessibilityNodeInfo rootInActiveWindow = getRootInActiveWindow();
        if(isNew){
            rootInActiveWindow.recycle();
            rootInActiveWindow = getRootInActiveWindow();
        }

        Log.e(TAG,"我等于空");
        if (rootInActiveWindow != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {

                List<AccessibilityNodeInfo> nodeInfos = rootInActiveWindow.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/d9a");
                for (AccessibilityNodeInfo info : nodeInfos) {
                    if (info.getText().toString().equals("发现")) {
                        info.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        Toast.makeText(getApplicationContext(), "我呗点击了", Toast.LENGTH_SHORT).show();

                    }
                }

                List<AccessibilityNodeInfo> fjdr = rootInActiveWindow.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/l5");
                for (AccessibilityNodeInfo info : fjdr) {
                    if (info.getText().toString().equals("附近的人")) {
                        info.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        Toast.makeText(getApplicationContext(), "我呗点击了", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }


    }

    public static void jumpToWx(Context context) {
        // jump to wechat
        ComponentName name = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
        try {
            Intent intent = new Intent();
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(name);
            context.startActivity(intent);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public void onInterrupt() {

    }
}

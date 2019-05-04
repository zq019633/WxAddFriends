package com.qzhou.wxaddfriends.service;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityEvent;

import com.qzhou.wxaddfriends.MainActivity;
import com.qzhou.wxaddfriends.R;
import com.qzhou.wxaddfriends.manager.JobManager;


/**
 * Created by zhou
 */
public class CoreService extends AccessibilityService {


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate() {
        super.onCreate();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification noti = new Notification.Builder(this)
                .setContentTitle("辅助服务器置成前台")
                .setContentText("辅助服务器置成前台")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(12311, noti);
    }


    /**
     * 全局返回事件
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void performBackClick() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        performGlobalAction(GLOBAL_ACTION_HOME);
    }

    /**
     * 返回上一级
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void BackClick() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        performGlobalAction(GLOBAL_ACTION_BACK);
    }

    /**
     * 滑动左到右
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void SWIPE_LEFT_AND_RIGHTClick() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        performGlobalAction(GESTURE_SWIPE_RIGHT);
    }

    /**
     * 滑动下到上
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void SWIPE_DOWN_AND_UPClick() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        performGlobalAction(GESTURE_SWIPE_UP);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        JobManager.getInstance().getJobProcessor().onEvent(getApplicationContext(),this, event);

//
//        Log.d("zq", "界面:" + event.getClassName().toString());
// //       int eventType = event.getEventType();
//
//
////         if (true) {
////            //打开通讯录TXL
////            if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && event.getClassName().equals("com.tencent.mm.ui.LauncherUI")) {
////               openTXL("通讯录");
////                opentxlDelay(2000, "新的朋友");
////              //  opentxlAdd(4000, "微信号/手机号");
////
////                Log.e("zq","我是主页面");
////            }else if(eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED &&event.getClassName().equals("com.tencent.mm.plugin.subapp.ui.friend.FMessageConversationUI")){
////               Log.e("zq","我是添加好友页面");
////                openAdd("添加手机联系人");
////
////
////            }
////
////        }
//
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//    private void opentxlAdd(final int delaytime, final String s) {
////        new Thread(new Runnable() {
////            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
////            @Override
////            public void run() {
////                try {
////                    Thread.sleep(delaytime);
////                } catch (InterruptedException mE) {
////                    mE.printStackTrace();
////                }
////                openAdd(s);
////            }
////        }).start();
//        openAdd(s);
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//    private void openAdd(String text) {
//        AccessibilityNodeInfo rootInActiveWindow = getRootInActiveWindow();
//        if (rootInActiveWindow == null) {
//            Toast.makeText(this, "rootWindow为空", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        List<AccessibilityNodeInfo> list = rootInActiveWindow.findAccessibilityNodeInfosByText(text);
//        AccessibilityNodeInfo clickNode = null;
//        for (AccessibilityNodeInfo nodeInfo : list) {
//            if (nodeInfo.getText() != null && text.equals(nodeInfo.getText().toString())) {
//                clickNode = nodeInfo;
//                Log.i(TAG, "text= " + nodeInfo.getText());
//
//            }
//        }
//        while (clickNode != null && !clickNode.isClickable()) {
//            clickNode = clickNode.getParent();
//        }
//        clickNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//
//
//    }
//
//
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//    private void openTXL(String text) {
//
//        AccessibilityNodeInfo rootInActiveWindow = getRootInActiveWindow();
//
//        if (rootInActiveWindow == null) {
//            Toast.makeText(this, "rootWindow为空", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        List<AccessibilityNodeInfo> list = rootInActiveWindow.findAccessibilityNodeInfosByText(text);
//        AccessibilityNodeInfo clickNode = null;
//        for (AccessibilityNodeInfo nodeInfo : list) {
//            if (nodeInfo.getText() != null && text.equals(nodeInfo.getText().toString())) {
//                clickNode = nodeInfo;
//                Log.i(TAG, "text= " + nodeInfo.getText());
//
//            }
//        }
//        while (clickNode != null && !clickNode.isClickable()) {
//            clickNode = clickNode.getParent();
//        }
//        clickNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//
//
//    }
//
//    //自动输入打招呼内容
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//    private void inputHello(String hello) {
//        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
//        //找到当前获取焦点的view
//        AccessibilityNodeInfo target = nodeInfo.findFocus(AccessibilityNodeInfo.FOCUS_INPUT);
//        if (target == null) {
//            Log.d(TAG, "inputHello: null");
//            return;
//        }
//        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
//        ClipData clip = ClipData.newPlainText("label", hello);
//        clipboard.setPrimaryClip(clip);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
//            target.performAction(AccessibilityNodeInfo.ACTION_PASTE);
//        }
//    }
//
//    private void opentxlDelay(final int delaytime, final String str) {
//        new Thread(new Runnable() {
//            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(delaytime);
//                } catch (InterruptedException mE) {
//                    mE.printStackTrace();
//                }
//                openTXL(str);
//            }
//        }).start();
//    }
//
//
//    private void openDelay(final int delaytime, final String str) {
//        new Thread(new Runnable() {
//            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(delaytime);
//                } catch (InterruptedException mE) {
//                    mE.printStackTrace();
//                }
//                openFx(str, true);
//            }
//        }).start();
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//    private void openFx(String str, boolean isNew) {
//        AccessibilityNodeInfo rootInActiveWindow = getRootInActiveWindow();
////        if (rootInActiveWindow != null) {
////            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
////                List<AccessibilityNodeInfo> nodeInfos = rootInActiveWindow.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/d9a");
////                for (AccessibilityNodeInfo info : nodeInfos) {
////                    if (info.getText().toString().equals("发现")) {
////                        Log.e("zq","我有节点");
////                        info.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
////                        Toast.makeText(getApplicationContext(), "我呗点击了", Toast.LENGTH_SHORT).show();
////                    }
////                }
////            }
////        }
//
//        if (rootInActiveWindow == null) {
//            Toast.makeText(this, "rootWindow为空", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        List<AccessibilityNodeInfo> list = rootInActiveWindow.findAccessibilityNodeInfosByText(str);
//        if (list != null && list.size() > 0) {
//            list.get(list.size() - 1).performAction(AccessibilityNodeInfo.ACTION_CLICK);
//            list.get(list.size() - 1).getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
//        } else {
//            Toast.makeText(this, "找不到有效的节点", Toast.LENGTH_SHORT).show();
//        }
    }


    @Override
    public void onInterrupt() {

    }


}

package com.qzhou.wxaddfriends.manager;

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.qzhou.wxaddfriends.Utils.Constant;
import com.qzhou.wxaddfriends.Utils.NodeUtil;

import java.util.List;

public class TestProcessor extends JobProcessor {
    private AccessibilityService testService;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onEvent(Context context, AccessibilityService service, AccessibilityEvent event) {
        super.onEvent(context, service, event);
        this.testService=service;
        Log.d("zq TestProcessor ", "界面:" + event.getClassName().toString());
        int eventType = event.getEventType();
        if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && event.getClassName().equals("com.tencent.mm.ui.LauncherUI")) {
//            FindAccessibilityNodeInfosByText(Constant.CONTACTS);
//            openDelay(1000, Constant.NEW_FRIEND);
            Log.e("zq111", "我点击新的朋友");
        } else if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && event.getClassName().equals("com.tencent.mm.plugin.subapp.ui.friend.FMessageConversationUI")) {
            //点击搜索框
            Log.e("zq111", "我要点击搜索框");
            FindAccessibilityNodeInfosByTextInTest("微信号/手机号");

        } else if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && event.getClassName().equals("com.tencent.mm.plugin.fts.ui.FTSAddFriendUI")) {
            Log.e("zq111", "我进入搜索框页面了");
            //输入要搜索的微信号 手机号
            //进入个人信息页面
            //点击打招呼按钮
            //输入打的招呼
            //点击完成
            //返回新的朋友页面  再次输入微信号手机号

        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void FindAccessibilityNodeInfosByTextInTest(String text) {
        AccessibilityNodeInfo rootInActiveWindow = testService.getRootInActiveWindow();
        if (rootInActiveWindow == null) {
            Toast.makeText(context, "rootWindow为空", Toast.LENGTH_SHORT).show();
            return;
        }
        List<AccessibilityNodeInfo> list = rootInActiveWindow.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/bva");
        if (list != null && list.size() > 0) {
            NodeUtil.performClick(list.get(0));
            NodeUtil.performClick(list.get(0).getParent());
        } else {
            Toast.makeText(context, "找不到有效的节点", Toast.LENGTH_SHORT).show();
        }}

}

package com.qzhou.wxaddfriends.manager;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.qzhou.wxaddfriends.Utils.Constant;
import com.qzhou.wxaddfriends.service.CoreService;

public class AddFriendsProcessor extends JobProcessor {
    private static final String TAG = "zq add";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onEvent(Context context, AccessibilityService service, AccessibilityEvent event) {
        super.onEvent(context, service, event);
        this.context = context;
        this.service = service;

        Log.d("zqNAddFriendsProcessor", "界面:" + event.getClassName().toString());
        int eventType = event.getEventType();
        if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && event.getClassName().equals("com.tencent.mm.ui.LauncherUI")) {
            FindAccessibilityNodeInfosByText(Constant.CONTACTS);
            openDelay(1000, Constant.NEW_FRIEND);
        } else if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && event.getClassName().equals("com.tencent.mm.plugin.subapp.ui.friend.FMessageConversationUI")) {
            //点击搜索框
            Log.e("zq111", "我要点击搜索框");

        } else if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && event.getClassName().equals("com.tencent.mm.plugin.fts.ui.FTSAddFriendUI")) {
            Log.e("zq111", "我进入搜索框页面了");
        }
    }

}

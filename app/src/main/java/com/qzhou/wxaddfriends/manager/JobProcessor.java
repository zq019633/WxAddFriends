package com.qzhou.wxaddfriends.manager;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.qzhou.wxaddfriends.Utils.NodeUtil;

import java.util.List;

public abstract class JobProcessor {

    private final JobManager jobManager;
    public AccessibilityService service;
    public Context context;

    public JobProcessor() {
        jobManager = JobManager.getInstance();
    }

    public void onEvent(Context context, AccessibilityService service, AccessibilityEvent event) {

    }

    /**
     * 根据文字模糊查询 找到节点 并点击
     *
     * @param text
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void FindAccessibilityNodeInfosByText(String text) {
        AccessibilityNodeInfo rootInActiveWindow = service.getRootInActiveWindow();
        if (rootInActiveWindow == null) {
            Toast.makeText(context, "rootWindow为空", Toast.LENGTH_SHORT).show();
            return;
        }
        List<AccessibilityNodeInfo> list = rootInActiveWindow.findAccessibilityNodeInfosByText(text);
        if (list != null && list.size() > 0) {
//            list.get(list.size() - 1).performAction(AccessibilityNodeInfo.ACTION_CLICK);
//            list.get(list.size() - 1).getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
            NodeUtil.performClick(list.get(list.size() - 1));
            NodeUtil.performClick(list.get(list.size() - 1).getParent());
        } else {
            Toast.makeText(context, "找不到有效的节点", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 延时点击
     * @param delaytime
     * @param text
     */
    public void openDelay(final int delaytime, final String text) {
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void run() {
                try {
                    Thread.sleep(delaytime);
                } catch (InterruptedException mE) {
                    mE.printStackTrace();
                }
                FindAccessibilityNodeInfosByText(text);
            }
        }).start();
    }

}

package com.qzhou.wxaddfriends.manager;

import android.accessibilityservice.AccessibilityService;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.qzhou.wxaddfriends.Utils.Constant;

import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;

public class NearbyPeopleProcessor extends JobProcessor {
    private static final String TAG = "zq_NearbyPeopleProcessor";
    private int i = 0;//记录已打招呼的人数
    private int page = 1;//记录附近的人列表页码,初始页码为1
    private int prepos = -1;//记录页面跳转来源，0--从附近的人页面跳转到详细资料页，1--从打招呼页面跳转到详细资料页
    private String hello = "你好";


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onEvent(Context context, final AccessibilityService service, AccessibilityEvent event) {
        super.onEvent(context, service, event);
        this.context=context;
        this.service=service;
        Log.d("zqNearbyPeopleProcessor", "界面:" + event.getClassName().toString());
        int eventType = event.getEventType();
        if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && event.getClassName().equals("com.tencent.mm.ui.LauncherUI")) {
            //记录打招呼人数置零
            i = 0;
            //当前在微信聊天页就点开发现
            FindAccessibilityNodeInfosByText(Constant.FIND);
            openDelay(2000, Constant.PEOPLE_NEARBY);
        } else if (event.getClassName().equals("com.tencent.mm.plugin.nearby.ui.NearbyFriendsUI") && eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            prepos = 0;
            //当前在附近的人界面就点选人打招呼
            AccessibilityNodeInfo nodeInfo = service.getRootInActiveWindow();
            List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText("米以内");
            Log.d("zq 1name", "附近的人列表人数: " + list.size());
            if (i < (list.size() * page)) {
                list.get(i % list.size()).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                list.get(i % list.size()).getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
            } else if (i == list.size() * page) {
                //本页已全部打招呼，所以下滑列表加载下一页，每次下滑的距离是一屏
                for (int i = 0; i < nodeInfo.getChild(0).getChildCount(); i++) {
                    if (nodeInfo.getChild(0).getChild(i).getClassName().equals("android.widget.ListView")) {
                        AccessibilityNodeInfo node_lsv = nodeInfo.getChild(0).getChild(i);
                        node_lsv.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
                        page++;
                        new Thread(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException mE) {
                                    mE.printStackTrace();
                                }
                                AccessibilityNodeInfo nodeInfo_ = service.getRootInActiveWindow();
                                List<AccessibilityNodeInfo> list_ = nodeInfo_.findAccessibilityNodeInfosByText("米以内");
                                Log.d("zq 2name", "列表人数: " + list_.size());
                                //滑动之后，上一页的最后一个item为当前的第一个item，所以从第二个开始打招呼
                                list_.get(1).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                list_.get(1).getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            }
                        }).start();
                    }
                }
            }
        } else if (event.getClassName().equals("com.tencent.mm.plugin.profile.ui.ContactInfoUI") && eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            if (prepos == 1) {
                //从打招呼界面跳转来的，则点击返回到附近的人页面
                service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);

                i++;
            } else if (prepos == 0) {
                //从附近的人跳转来的，则点击打招呼按钮
                AccessibilityNodeInfo nodeInfo = service.getRootInActiveWindow();
                if (nodeInfo == null) {
                    Log.d(TAG, "rootWindow为空");
                    return;
                }
                List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText("打招呼");
                if (list.size() > 0) {
                    list.get(list.size() - 1).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    list.get(list.size() - 1).getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                } else {
                    //如果遇到已加为好友的则界面的“打招呼”变为“发消息"，所以直接返回上一个界面并记录打招呼人数+1
                    service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                    i++;
                }
            }
        } else if (event.getClassName().equals("com.tencent.mm.ui.contact.SayHiEditUI") && eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            //当前在打招呼页面
            prepos = 1;
            //输入打招呼的内容并发送
            inputHello(hello);
            FindAccessibilityNodeInfosByText("发送");
        }
    }
    //自动输入打招呼内容
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void inputHello(String hello) {
        AccessibilityNodeInfo nodeInfo = service.getRootInActiveWindow();
        //找到当前获取焦点的view
        AccessibilityNodeInfo target = nodeInfo.findFocus(AccessibilityNodeInfo.FOCUS_INPUT);
        if (target == null) {
            Log.d(TAG, "inputHello: null");
            return;
        }
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", hello);
        clipboard.setPrimaryClip(clip);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            target.performAction(AccessibilityNodeInfo.ACTION_PASTE);
        }
    }


}

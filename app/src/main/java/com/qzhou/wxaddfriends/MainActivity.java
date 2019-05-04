package com.qzhou.wxaddfriends;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.qzhou.wxaddfriends.manager.AddFriendsProcessor;
import com.qzhou.wxaddfriends.manager.JobManager;
import com.qzhou.wxaddfriends.manager.NearbyPeopleProcessor;
import com.qzhou.wxaddfriends.manager.TestProcessor;
import com.yanzhenjie.permission.AndPermission;


public class MainActivity extends AppCompatActivity {
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private Button add;
    private Button openWx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        perssionAbout();
        initView();
        initData();
    }

    private void initData() {

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestAssistPermission();

            }
        });
        openWx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpToWx(MainActivity.this);

            }
        });

        NearbyPeopleProcessor nearbyPeopleProcessor = new NearbyPeopleProcessor();
        AddFriendsProcessor addFriendsProcessor=new AddFriendsProcessor();

        JobManager.getInstance().startWorking(this,addFriendsProcessor);

        JobManager.getInstance().startWorking(this,new TestProcessor());
    }
    private void perssionAbout() {
        // 先判断是否有权限。WRITE_EXTERNAL_STORAGE
        if (AndPermission.hasPermission(this,
                Manifest.permission_group.CALL_LOG

              )){

        } else {
            // 申请权限。
            AndPermission.with(this)
                    .requestCode(100)
                    .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                    .callback(this)
                    .start();
        }

    }




    /**
     * 申请辅助功能权限
     */
    private void requestAssistPermission() {
        try {
            //打开系统设置中辅助功能
            Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static  void jumpToWx(Context context) {
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




    private void initView() {
        add = (Button) findViewById(R.id.add);
        openWx = findViewById(R.id.openWx);


    }
}

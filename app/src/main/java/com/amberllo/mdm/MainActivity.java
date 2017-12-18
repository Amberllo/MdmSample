package com.amberllo.mdm;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView btn_resetpsw;
    private TextView btn_changepsw;
    private TextView btn_lock;
    private TextView btn_disableCamera;
    private TextView btn_openCamera;


    ComponentName componentName;
    DevicePolicyManager devicePolicyManager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



        btn_lock = (TextView) findViewById(R.id.btn_lock);
        btn_resetpsw = (TextView) findViewById(R.id.btn_resetpsw);
        btn_changepsw = (TextView) findViewById(R.id.btn_changepsw);
        btn_openCamera= (TextView) findViewById(R.id.btn_openCamera);
        btn_disableCamera = (TextView) findViewById(R.id.btn_disableCamera);


        btn_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                devicePolicyManager.lockNow();
            }
        });

        btn_changepsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                devicePolicyManager.resetPassword("123456",1);
                Toast.makeText(MainActivity.this," 锁屏密码已修改为 123456 ", Toast.LENGTH_SHORT).show();
            }
        });

        btn_resetpsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                devicePolicyManager.resetPassword("",0);
                Toast.makeText(MainActivity.this," 锁屏密码已清除", Toast.LENGTH_SHORT).show();
            }
        });

        btn_openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                devicePolicyManager.setCameraDisabled(componentName,false);
                Toast.makeText(MainActivity.this,"已恢复拍照", Toast.LENGTH_SHORT).show();
            }
        });

        btn_disableCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                devicePolicyManager.setCameraDisabled(componentName,true);
                Toast.makeText(MainActivity.this,"已屏蔽拍照", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void init(){
        componentName = new ComponentName(this, MyAdmin.class);
        devicePolicyManager = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
        if(!devicePolicyManager.isAdminActive(componentName)){
            Intent intent = new  Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "----这是一键锁屏激活界面-----");
            startActivityForResult(intent, 0);
        }
    }

}

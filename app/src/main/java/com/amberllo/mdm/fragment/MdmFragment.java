package com.amberllo.mdm.fragment;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amberllo.mdm.MyAdmin;
import com.amberllo.mdm.R;

/**
 * Created by Administrator on 2017/12/26 0026.
 */

public class MdmFragment extends Fragment {



    private ComponentName componentName;
    private DevicePolicyManager devicePolicyManager;
    private TextView btn_resetpsw;
    private TextView btn_changepsw;
    private TextView btn_lock;
    private TextView btn_disableCamera;
    private TextView btn_openCamera;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mdm,container,false);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        btn_lock = (TextView) view.findViewById(R.id.btn_lock);
        btn_resetpsw = (TextView) view.findViewById(R.id.btn_resetpsw);
        btn_changepsw = (TextView) view.findViewById(R.id.btn_changepsw);
        btn_openCamera= (TextView) view.findViewById(R.id.btn_openCamera);
        btn_disableCamera = (TextView) view.findViewById(R.id.btn_disableCamera);


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
                Toast.makeText(getContext()," 锁屏密码已修改为 123456 ", Toast.LENGTH_SHORT).show();
            }
        });

        btn_resetpsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                devicePolicyManager.resetPassword("",0);
                Toast.makeText(getContext()," 锁屏密码已清除", Toast.LENGTH_SHORT).show();
            }
        });

        btn_openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                devicePolicyManager.setCameraDisabled(componentName,false);
                Toast.makeText(getContext(),"已恢复拍照", Toast.LENGTH_SHORT).show();
            }
        });

        btn_disableCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                devicePolicyManager.setCameraDisabled(componentName,true);
                Toast.makeText(getContext(),"已屏蔽拍照", Toast.LENGTH_SHORT).show();
            }
        });
    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void init(){
        componentName = new ComponentName(getContext(), MyAdmin.class);
        devicePolicyManager = (DevicePolicyManager)getContext().getSystemService(Context.DEVICE_POLICY_SERVICE);
        if(!devicePolicyManager.isAdminActive(componentName)){
            Intent intent = new  Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "----这是一键锁屏激活界面-----");
            startActivityForResult(intent, 0);
        }
//        boolean isOwner = false;
//        UserManager um = (UserManager) getSystemService(Context.USER_SERVICE);
//        if (um != null) {
//            UserHandle userHandle = android.os.Process.myUserHandle();
//            isOwner = um.getSerialNumberForUser(userHandle) == 0;
//            System.out.println(isOwner);
//        }

    }
}

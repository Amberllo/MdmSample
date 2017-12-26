package com.amberllo.mdm;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.amberllo.mdm.fragment.HostFragment;
import com.amberllo.mdm.fragment.MdmFragment;
import com.amberllo.mdm.fragment.SettingFragment;

public class MainActivity extends AppCompatActivity {

    Fragment mMdmFragment;
    Fragment mHostFragment;
    Fragment mSettingFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    changeFragment(mHostFragment);
                    return true;
                case R.id.navigation_dashboard:
                    changeFragment(mMdmFragment);
                    return true;
                case R.id.navigation_notifications:
                    changeFragment(mSettingFragment);
                    return true;
            }
            return false;
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mHostFragment = new HostFragment();
        mMdmFragment = new MdmFragment();
        mSettingFragment = new SettingFragment();
        changeFragment(mHostFragment);

    }

    void changeFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction  = fragmentManager.beginTransaction();
        transaction.replace(R.id.mainContainer,fragment);
        transaction.commit();
    }

}

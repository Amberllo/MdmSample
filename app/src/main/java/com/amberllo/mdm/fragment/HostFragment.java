package com.amberllo.mdm.fragment;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.amberllo.mdm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/26 0026.
 */

public class HostFragment extends Fragment {
    ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_host,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.applist);
        PackageManager pm = getContext().getPackageManager();
        List<String> applist = new ArrayList<>();
        List<ApplicationInfo> applicationInfoList = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        for(ApplicationInfo app:applicationInfoList){
            applist.add(app.packageName);
        }
        listView.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,applist));
    }
}

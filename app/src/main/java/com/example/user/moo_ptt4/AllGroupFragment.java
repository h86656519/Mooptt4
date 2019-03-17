package com.example.user.moo_ptt4;


import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nex3z.flowlayout.FlowLayout;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;


public class AllGroupFragment extends Fragment implements DialogInterface.OnCancelListener {
    Application app;
    private List<Integer> names = new ArrayList<>();
    private FLayout mFlowLayout;
    MyReceiver mBroadcastReciver;

    public AllGroupFragment() {
        // Required empty public constructor
    }

    public void setApp(Application app) {
        this.app = app;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_group, container, false);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setData();

        /**
         * 查找控件
         */
        //   mFlowLayout = ((FlowLayout) findViewById(R.id.fl));
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFlowLayout = (FLayout) view.findViewById(R.id.fl);
        /**
         * 添加数据
         */
        mFlowLayout.addData(names);
        /**
         * 设置点击事件
         */
        mFlowLayout.setFlowLayoutListener(new FLayout.FLayoutListener() {
            @Override
            public void onItemClick(View view, int poition) {
               // Log.i("onItemClick", "poition : " + poition);
                switch (poition) {
                    case 0:
                        Intent intent = new Intent();
                        intent.setClass(getContext(), DelayedMessageService.class);
                        intent.putExtra(DelayedMessageService.EXTRA_MESSAGE,
                                getResources().getString(R.string.question));
                        getActivity().startService(intent);
                        Toast service_toast = Toast.makeText(getContext().getApplicationContext(), "發送service ", Toast.LENGTH_SHORT);
                        service_toast.show();
                        break;
                    case 1:
                        Intent intent1 = new Intent();
                        intent1.setClass(getContext(), FAQActivity.class);
                        getActivity().startActivity(intent1);
                        Toast db_toast = Toast.makeText(getContext().getApplicationContext(), "sqlite 讀取 + 刪除 ", Toast.LENGTH_SHORT);
                        db_toast.show();
                        break;
                    case 2:
                        Toast toast = Toast.makeText(getContext().getApplicationContext(), "開始註冊監聽", Toast.LENGTH_SHORT);
                        toast.show();
                        mBroadcastReciver = new MyReceiver();
                        IntentFilter filter = new IntentFilter();
                        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
                        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
                        getContext().registerReceiver(mBroadcastReciver, filter);
                        break;
                    case 3:
                        if (mBroadcastReciver == null) {
                            Toast isregister_toast = Toast.makeText(getContext().getApplicationContext(), "還未註冊", Toast.LENGTH_SHORT);
                            isregister_toast.show();
                        } else {
                            getContext().unregisterReceiver(mBroadcastReciver);
                            Toast stopregister_toast = Toast.makeText(getContext().getApplicationContext(), "停止註冊", Toast.LENGTH_SHORT);
                            stopregister_toast.show();
                        }
                        break;
                }
            }
        });


    }

    private void setData() {

        //names.add(R.drawable.main_menu_item1);
        names.add(R.drawable.allgroup1);
        names.add(R.drawable.allgroup2);
        names.add(R.drawable.allgroup4);
        names.add(R.drawable.allgroup5);
        names.add(R.drawable.allgroup6);
        names.add(R.drawable.allgroup7);

//        names.add(R.drawable.mainmenu_grid_item_4);
//        names.add(R.drawable.mainmenu_grid_item_2);
//        names.add(R.drawable.main_menu_item4);
//        names.add(R.drawable.main_menu_item5);
//        names.add(R.drawable.main_menu_item6);
    }


    @Override
    public void onCancel(DialogInterface dialogInterface) {

    }


    @Override
    public void onPause() {
        super.onPause();
        if (mBroadcastReciver != null) {
            Toast isregister_toast = Toast.makeText(getContext().getApplicationContext(), "已經停止註冊，請從新再註冊", Toast.LENGTH_SHORT);
            isregister_toast.show();
            getContext().unregisterReceiver(mBroadcastReciver);
        }
    }
}

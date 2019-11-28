package com.example.user.moo_ptt4;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

import user.Moo_ptt.R;

public class SettingTheme extends AppCompatActivity {
    private TabLayout mTablayout,tab;
    private ViewPager mViewPager;
    GridView gridView;
    LayoutInflater layoutInflater;


    public void SettingTheme(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.activity_main, null);
        View layout2 = inflater.inflate(R.layout.fragment_mainmenu, null);

        mTablayout = (TabLayout) layout.findViewById(R.id.tabs);
        //   Log.i("json","mTablayout :" + mTablayout);
        gridView = (GridView) layout2.findViewById(R.id.mainmenu_gridview);
//        Log.i("themeName", "gridView_setting: " + gridView);

        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(context);
        String themeName = pref.getString("LP", "Theme1");
        Log.i("themeName", "themeName : " + themeName);
//        if (themeName.equals("黑色")) {
////            Log.i("json", "AppTheme");
//            setTheme(R.style.blackTheme);
//        } else if (themeName.equals("白色")) {
////            Log.i("json", "AppTheme2");
//            setTheme(R.style.whitTheme);
//        }

//        if (themeName.equals("黑色")) {
//            Log.i("json", "黑色setting");
//          //  mTablayout.setBackgroundColor(getResources().getColor(R.color.black));
//            mTablayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#060606"));
//            //  mTablayout.setTabTextColors(123);
//        } else if (themeName.equals("白色")) {
//            Log.i("json", "白色setting");
////            Log.i("json", "getResources987 : " + getResources().getColor(R.color.white));
//           // mTablayout.setBackgroundColor(R.color.white);
//            mTablayout.setTabTextColors(Color.parseColor("#FFFF0000"), Color.parseColor("#FFFF0000"));
//            //  mTablayout.setTabTextColors(getResources().getColor(R.color.black));
//        }
//

//        if (themeName.equals("黑色")) {
//            gridView.setBackgroundColor(context.getResources().getColor(R.color.black));
//            Log.i("json", "黑色setting");
//           // gridView.setBackgroundColor(Color.BLACK);
//        } else if (themeName.equals("白色")) {
//            gridView.setBackgroundColor(context.getResources().getColor(R.color.white));
//            Log.i("json", "白色setting");
//          //  gridView.setBackgroundColor(Color.WHITE);
//        }

    }
    public String themeName(Context context) {
        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(context);
        String themeName = pref.getString("LP", "Theme1");
        Log.i("themeName", "themeName : " + themeName);
        return themeName;
    }
}

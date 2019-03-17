package com.example.user.moo_ptt4;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ViewPager pager;
    Application app;
    String themeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SectionPagerAdapter pagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
        app = getApplication();

//      判斷主題顏色
        String returnthemeName = themeName();
        if (returnthemeName.equals("黑色")) {
            //  Log.i("json", "AppTheme");
            setTheme(R.style.AppTheme);
        } else if (returnthemeName.equals("藍色")) {
            // Log.i("json", "AppTheme2");
            setTheme(R.style.AppTheme2);
        }


    }

    private class SectionPagerAdapter extends FragmentPagerAdapter {

        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new MyflavorFragment();
                case 1:
                    return new MainMenuFragment();
                case 2:
                    AllGroupFragment allgroup = new AllGroupFragment();
                    allgroup.setApp(app);
                    return allgroup;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getText(R.string.Myflovor_tab);
                case 1:
                    return getResources().getText(R.string.Main_Menu_tab);
                case 2:
                    return getResources().getText(R.string.All_group);
            }
            return null;
        }
    }

    public String themeName() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        themeName = pref.getString("LP", "Theme1");
        // Log.i("themeName", "themeName : " + themeName);
        return themeName;
    }

    @Override
    protected void onResume() {
        super.onResume();
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                switch (i) {
                    case 0:
                        Log.i("suvini", ":" + i);
                        Toast description_toast = Toast.makeText(MainActivity.this, "DEMO : \n 1.基本fragment + TabLayout + Viewpager \n 2. 串接 firebase", Toast.LENGTH_SHORT);
                        View myflavorview = description_toast.getView();
                        myflavorview.setBackgroundResource(android.R.color.holo_green_light);
                        description_toast.setView(myflavorview);
                        description_toast.show();
                        break;
                    case 1:
                        Log.i("suvini", ":" + i);
                        Toast Mainmenu_toast = Toast.makeText(MainActivity.this, "DEMO : \n 1.gridviewlayout \n 2.Preference 設定 ", Toast.LENGTH_LONG);
                        View mainmenuview = Mainmenu_toast.getView();
                        mainmenuview.setBackgroundResource(android.R.color.holo_green_light);
                        Mainmenu_toast.setView(mainmenuview);
                        Mainmenu_toast.show();
                        break;
                    case 2:
                        Log.i("suvini", ":" + i);
                        Toast allgroup_toast = Toast.makeText(MainActivity.this,
                                "DEMO : \n 1.flowlayout 根據每張圖片原本大小做自動排列 \n 2.service 接發收 \n 3.sql 增刪查 \n 4.廣播註冊監聽 wifi "
                                , Toast.LENGTH_LONG);
                        View allgroupview = allgroup_toast.getView();
                        allgroupview.setBackgroundResource(android.R.color.holo_green_light);
                        allgroup_toast.setView(allgroupview);
                        allgroup_toast.show();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
}

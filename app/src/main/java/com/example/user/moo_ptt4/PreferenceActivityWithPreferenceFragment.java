package com.example.user.moo_ptt4;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.util.AttributeSet;

import java.util.List;

public class PreferenceActivityWithPreferenceFragment extends AppCompatPreferenceActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<PreferenceActivity.Header> target) {
        //加载选项列表布局
        loadHeadersFromResource(R.xml.pref_headers, target);


    }

    /**
     * 验证Preference是否有效
     *
     * @param fragmentName
     * @return
     */
    protected boolean isValidFragment(String fragmentName) {
        return true;
    }

    /**
     * GeneralPreferenceFragment
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class GeneralPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
        }
    }

    public class MyPreferenceCategory extends PreferenceCategory {

        public MyPreferenceCategory(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);

        }

        public MyPreferenceCategory(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr  );
        }

        public MyPreferenceCategory(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public MyPreferenceCategory(Context context) {
            super(context);
        }

//        @Override
//        protected void onBindView(View view) {
//            super.onBindView(view);
//            if (view instanceof TextView) {
//                TextView tv = (TextView) view;
//                tv.getResources().getColor(R.color.green);
//            }
//        }
    }
}

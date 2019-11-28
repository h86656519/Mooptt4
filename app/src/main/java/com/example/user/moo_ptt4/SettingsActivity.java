package com.example.user.moo_ptt4;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import user.Moo_ptt.R;

public class SettingsActivity extends PreferenceActivity implements View.OnClickListener {
    CheckBox cb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   setContentView(R.layout.activity_settings);

        addPreferencesFromResource(R.xml.preference);
        //暫先不用Fragment
//        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.setting_activity, new SettingsFragment()).commit();
        final CheckBoxPreference checkboxPref = (CheckBoxPreference) getPreferenceManager().findPreference("CB");
        checkboxPref.setTitle("自動登錄");
        checkboxPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Log.d("MyApp", "Pref " + preference.getKey() + " changed to " + newValue.toString());
                return true;
            }
        });
        ListPreference listPreference = (ListPreference)getPreferenceManager().findPreference("LP");
        listPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                int title = preference.getOrder();
                Log.i("suvini","title : " + title);
                return false;
            }
        });
    }
    //xml裡面有 Headers 才能用，沒有硬用會出 RuntimeException
//    @Override
//    public void onBuildHeaders(List<Header> target) {
//        super.onBuildHeaders(target);
//     //   loadHeadersFromResource(R.xml.preference, target);
//    }

    /**
     * API 19以上的安全机制,需要重写isValidFragment()
     * <p>
     * 1.直接return true
     * <p>
     * 2.return [YOUR_FRAGMENT_NAME].class.getName().equals(fragmentName);
     *
     * @param fragmentName
     * @return
     */
    @Override
    protected boolean isValidFragment(String fragmentName) {
        return true;
    }

    @Override
    public void onClick(View v) {

    }


}
